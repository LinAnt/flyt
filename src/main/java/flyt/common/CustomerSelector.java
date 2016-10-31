package flyt.common;


import com.google.common.collect.Lists;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import flyt.backend.Backend;



import java.util.List;

/**
 * Created by acl on 10/31/16.
 */
public class CustomerSelector extends CustomComponent {

    public CustomerSelector(MainView mainView) throws FlytException {

        Backend backend = Backend.getInstance();

        List<Customer> CustomerList= backend.getCustomers();
        List<Button> customerButtons = Lists.newArrayList();




    }

}
