package flyt.common;

import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import flyt.backend.Backend;

import java.util.List;

/**
 * Created by acl on 10/30/2016.
 */
public class MainView extends MainDesign {

    public MainView(){
        super();
        clearContent();
        try {
            List<Customer> customerList = Backend.getInstance().getCustomers();
            for (Customer C : customerList) {
                customerListSelect.addItem(C.name);
            }
        } catch ( FlytException fe ) {
                // fix better error handling
            }
        serverBar.getItems().get(0).addItem("Server4", (MenuBar.Command) menuItem -> System.out.println("Server4"));
        logoutButton.addClickListener(e -> {
            System.out.println("User Logged Out");
        });
    }
    public VerticalLayout getContent(){

        return content;

    }
    private void clearContent(){
            Label l = new Label();
            l.setValue("Please select a Server or Check out the Statistics! :)");
            content.removeAllComponents();
            content.addComponent(l);
        }
    }
