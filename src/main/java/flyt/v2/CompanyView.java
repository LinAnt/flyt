package flyt.v2;

import com.vaadin.ui.*;
import flyt.Flyt;
import flyt.backend.Backend;
import flyt.common.Customer;
import flyt.common.FlytException;

import java.util.List;

/**
 * @author Fredrik
 */
public class CompanyView extends CustomComponent {
    private final boolean newCustomer;
    private Customer customer;
    private final Flyt flyt;

    public CompanyView( Flyt flyt ) {
        this.flyt = flyt ;
        newCustomer = true;
        customer = null;
        update();
    }
    public CompanyView( Flyt flyt, String company ) {
        this.flyt = flyt;
        newCustomer = false;
        try {
            List<Customer> allCustomers = Backend.getInstance().getCustomers();
            for (Customer c : allCustomers) {
                if (c.name.equals(company)) {
                    customer = c;
                    break;
                }
            }
            update();
        } catch ( FlytException fe ) {
            Notification.show( fe.getMessage(), Notification.Type.ERROR_MESSAGE );
        }
    }
    public void update() {
        FormLayout form = new FormLayout();
        TextField companyName = new TextField("Comapny name");
        form.addComponent(companyName);
        TextArea companyContact = new TextArea("Contact information");
        form.addComponent(companyContact);
        if (newCustomer) {
            Button create = new Button("Create");
            form.addComponent(create);
            create.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    try {
                        Customer c = new Customer();
                        c.name = companyName.getValue();
                        c.contact = companyContact.getValue();
                        Backend.getInstance().addCustomer(c);
                        flyt.getMenu().update();
                    } catch ( FlytException fe ) {
                        Notification.show( fe.getMessage(), Notification.Type.ERROR_MESSAGE );
                    }
                }
            });
        } else {
            Button save = new Button("Save");
            form.addComponent(save);
            if (customer != null) {
                if ( customer.name != null ) {
                    companyName.setValue(customer.name);
                }
                if ( customer.contact != null ) {
                    companyContact.setValue(customer.contact);
                }
                save.setEnabled(true);
            } else {
                save.setEnabled(false);
            }
            save.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    try {
                        String oldName = customer.name;
                        customer.name = companyName.getValue();
                        customer.contact = companyContact.getValue();
                        Backend.getInstance().updateCustomer(oldName, customer);
                        flyt.getMenu().update();
                        flyt.setBody( new WelcomeView() );
                        Notification.show( "Customer has been updated", Notification.Type.ASSISTIVE_NOTIFICATION );
                    } catch ( FlytException fe ) {
                        Notification.show( fe.getMessage(), Notification.Type.ERROR_MESSAGE );
                    }

                }
            });
            Button remove = new Button( "Remove" );
            form.addComponent( remove );
            remove.addClickListener( new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    try {
                        Backend.getInstance().removeCustomer( customer.name );
                        flyt.getMenu().update();
                        flyt.setBody( new WelcomeView() );
                        Notification.show( "CUstomer has been removed", Notification.Type.WARNING_MESSAGE );
                    } catch (FlytException fe) {
                        Notification.show( fe.getMessage(), Notification.Type.ERROR_MESSAGE );
                    }
                }
            });
        }
        setCompositionRoot(form);
    }
}
