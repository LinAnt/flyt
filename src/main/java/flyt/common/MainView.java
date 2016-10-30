package flyt.common;

import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by acl on 10/30/2016.
 */
public class MainView extends MainDesign {

    public MainView(){
        super();
        setContent();
        serverBar.getItems().get(0).addItem("Server4", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                System.out.println("Server4");
            }
        });
        logoutButton.addClickListener(e -> {
            System.out.println("User Logged Out");
        });
    }
    public VerticalLayout getContent(){

        return content;

    }
    private void setContent(){
        if (content.getComponentCount() == 0){
            Label l = new Label();
            l.setValue("Please select a Server or Check out the Statistics! :)");
            content.addComponent(l);
        }
    }

}
