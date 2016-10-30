package flyt.common;

import com.vaadin.data.Property;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import flyt.backend.Backend;

import java.util.List;

/**
 * Created by acl on 10/30/2016.
 */
public class MainView extends MainDesign {

    public MainView() {
        super();
        clearContent();
        try {
            List<Customer> customerList = Backend.getInstance().getCustomers();
            customerListSelect.setNullSelectionAllowed(false);
            for (Customer C : customerList) {
                customerListSelect.addItem(C.name);
            }
            MenuBar.MenuItem menuItem = serverBar.getItems().get(0);
            for (Customer c : customerList) {
                for (String server : c.servers) {
                    menuItem.addItem(server, (MenuBar.Command) mi -> System.out.println(server));
                }
            }
            customerListSelect.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                    System.out.println("Display Customer stats");
                }
            });
            logoutButton.addClickListener(e -> {
                System.out.println("User Logged Out");
            });
        } catch (FlytException fe) {
            // fix better error handling
        }
    }

    public VerticalLayout getContent(){

        return content;

    }
    private void clearContent(){
            Label l = new Label();
            l.setValue("Please select a Customer, a Server or Check out the Statistics! :)");
            content.removeAllComponents();
            content.addComponent(l);
        }
    }
