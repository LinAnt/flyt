package flyt.v2;

import com.vaadin.ui.*;
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
    private final MenuView menu;

    public CompanyView( MenuView menu ) {
        this.menu = menu;
        newCustomer = true;
        customer = null;
        update();
    }
    public CompanyView( MenuView menu, String company ) {
        this.menu = menu;
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
                        Backend.getInstance().addCustomer(c);
                        menu.update();
                    } catch ( FlytException fe ) {
                        Notification.show( fe.getMessage(), Notification.Type.ERROR_MESSAGE );
                    }
                }
            });
        } else {
            Button save = new Button("Save");
            form.addComponent(save);
            if (customer != null) {
                companyName.setValue(customer.name);
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
                        Backend.getInstance().updateCustomer(oldName, customer);
                        menu.update();
                    } catch ( FlytException fe ) {
                        Notification.show( fe.getMessage(), Notification.Type.ERROR_MESSAGE );
                    }

                }
            });
        }
        setCompositionRoot(form);
    }
}
