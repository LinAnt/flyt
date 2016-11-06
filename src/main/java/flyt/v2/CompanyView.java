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
    public CompanyView( String company ) {
        try {
            List<Customer> allCustomers = Backend.getInstance().getCustomers();
            Customer customer = null;
            for ( Customer c: allCustomers ) {
                if ( c.name.equals( company ) ) {
                    customer = c;
                    break;
                }
            }
            FormLayout form = new FormLayout();
            TextField companyName = new TextField("Comapny name");
            form.addComponent(companyName);
            TextArea companyContact = new TextArea("Contact information");
            form.addComponent(companyContact);
            Button save = new Button("Save");
            form.addComponent(save);
            if ( customer != null ) {
                companyName.setValue( customer.name );
                save.setEnabled( true );
            }
            else {
                save.setEnabled( false );
            }
            setCompositionRoot(form);
        } catch ( FlytException fe ) {

        }

    }
}
