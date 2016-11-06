package flyt.v2;

import com.vaadin.data.Property;
import com.vaadin.event.Action;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import flyt.Flyt;
import flyt.backend.Backend;
import flyt.common.Customer;
import flyt.common.FlytException;

import java.util.List;

/**
 * @author Fredrik
 */
public class MenuView extends CustomComponent {
    public MenuView( Flyt flyt ) {
        try {
            List<Customer> allCustomers = Backend.getInstance().getCustomers();
            Tree customerTree = new Tree();
            for ( Customer customer : allCustomers ) {
                customerTree.addItem( customer.name );
                customerTree.expandItem( customer.name );
                customerTree.addValueChangeListener( new Property.ValueChangeListener() {
                    @Override
                    public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                        try {
                            String clickedValue = (String)valueChangeEvent.getProperty().getValue();
                            if ( Backend.getInstance().isCustomer( clickedValue ) ) {
                                flyt.setBody(new CompanyView(clickedValue));
                            } else {
                                flyt.setBody(new ServerView(clickedValue) );
                            }
                        } catch ( FlytException fe ) {
                            Notification.show( fe.getMessage(), Notification.Type.ERROR_MESSAGE );
                        }
                    }
                });
                for ( String server : customer.servers ) {
                    customerTree.addItem( server );
                    customerTree.setParent( server, customer.name );
                    customerTree.setChildrenAllowed( server, false );
                }
            }
            setCompositionRoot( customerTree );
        } catch ( FlytException fe ) {
            Notification.show( fe.getMessage(), Notification.Type.ERROR_MESSAGE );
        }
    }
}

