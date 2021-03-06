package flyt.v2;

import com.vaadin.data.Property;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.*;
import flyt.Flyt;
import flyt.backend.Backend;
import flyt.common.Customer;
import flyt.common.FlytException;

import java.util.List;

/**
 * @author Fredrik
 */
public class MenuView extends CustomComponent {
    public static final String UNASSIGNED_SERVERS = "Unassigned servers";
    private final Flyt flyt;
    public MenuView( Flyt flyt ) {
        this.flyt = flyt;
        update();
    }
    public void update() {
        try {
            VerticalLayout vl = new VerticalLayout();
            List<Customer> allCustomers = Backend.getInstance().getCustomers();
            Tree customerTree = new Tree();
            customerTree.setDragMode(Tree.TreeDragMode.NODE);
            customerTree.setDropHandler(new DropHandler() {
                @Override
                public void drop(DragAndDropEvent dragAndDropEvent) {
                    try {
                        Backend backend = Backend.getInstance();
                        Transferable t = dragAndDropEvent.getTransferable();
                        if (t.getSourceComponent() != customerTree) {
                            return;
                        }
                        Tree.TreeTargetDetails target = (Tree.TreeTargetDetails) dragAndDropEvent.getTargetDetails();
                        String sourceName = (String)t.getData( "itemId" );
                        if ( sourceName.equals( UNASSIGNED_SERVERS ) ) {
                            return;
                        }
                        String sourceParent = (String)customerTree.getParent( sourceName);
                        Customer sourceCustomer = backend.getCustomerByServer(sourceName);
                        String targetName = (String)target.getItemIdOver();
                        if ( targetName == null ) { // wtf
                            return;
                        }
                        if ( targetName.equals( UNASSIGNED_SERVERS) || backend.isCustomer( targetName) ) {
                            if (sourceParent != null && sourceCustomer != null) {
                                int i = sourceCustomer.servers.indexOf(sourceName);
                                sourceCustomer.servers.remove(i);
                                backend.updateCustomer(sourceCustomer.name, sourceCustomer);
                            }
                            if (!targetName.equals(UNASSIGNED_SERVERS)) {
                                Customer targetCustomer = backend.getCustomerByName(targetName);
                                if (targetCustomer == null) {
                                    return;
                                }
                                targetCustomer.servers.add(sourceName);
                                backend.updateCustomer(targetCustomer.name, targetCustomer);
                            }
                            update();
                        }
                    }
                    catch ( FlytException fe ) {
                        Notification.show( fe.getMessage(), Notification.Type.ERROR_MESSAGE );
                    }
                }

                @Override
                public AcceptCriterion getAcceptCriterion() {
                    return AcceptAll.get();
                }
            });
            customerTree.addValueChangeListener( new OwnValueChangeListener( flyt ));
            for ( Customer customer : allCustomers ) {
                customerTree.addItem( customer.name );
                customerTree.expandItem( customer.name );
                for ( String server : customer.servers ) {
                    customerTree.addItem( server );
                    customerTree.setParent( server, customer.name );
                    customerTree.setChildrenAllowed( server, false );
                }
            }
            customerTree.addItem( UNASSIGNED_SERVERS );
            customerTree.expandItem( UNASSIGNED_SERVERS );
            for ( String serverId : Backend.getInstance().getUnassignedServers() ) {
                customerTree.addItem( serverId );
                customerTree.setParent( serverId, UNASSIGNED_SERVERS );
                customerTree.setChildrenAllowed( serverId, false );
            }
            vl.addComponent( customerTree );
            Button addCustomer = new Button( "Add customer" );
            addCustomer.addClickListener(new OwnButtonListener( flyt ) );
            vl.addComponent( addCustomer );
            setCompositionRoot( vl );
        } catch ( FlytException fe ) {
            Notification.show( fe.getMessage(), Notification.Type.ERROR_MESSAGE );
        }
    }

    class OwnButtonListener implements Button.ClickListener {

        private final Flyt flyt;
        public OwnButtonListener( Flyt flyt) {
            this.flyt = flyt;
        }
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            flyt.setBody( new CompanyView( flyt) );
        }
    }

    class OwnValueChangeListener implements Property.ValueChangeListener {

        private final Flyt flyt;
        public OwnValueChangeListener( Flyt flyt ) {
            this.flyt = flyt;
        }
        @Override
        public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
            try {
                String clickedValue = (String)valueChangeEvent.getProperty().getValue();
                if ( clickedValue == null ) { // wtf
                    return;
                }
                if ( clickedValue.equals( UNASSIGNED_SERVERS ) ) {
                    flyt.setBody( new WelcomeView() );
                } else if ( Backend.getInstance().isCustomer( clickedValue ) ) {
                    flyt.setBody(new CompanyView(flyt, clickedValue ) );
                } else {
                    flyt.setBody(new ServerView(clickedValue) );
                }
            } catch ( FlytException fe ) {
                Notification.show( fe.getMessage(), Notification.Type.ERROR_MESSAGE );
            }
        }
    }
}

