package flyt;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import flyt.backend.Backend;
import flyt.common.*;
import flyt.v2.MenuView;
import flyt.v2.WelcomeView;

import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@JavaScript( {"https://www.gstatic.com/charts/loader.js","vaadin://js/graphs.js"} )
public class Flyt extends UI {

    /**
     * Set VERSION = 1 for Vaadin designer version
     * Set VERSION = 2 for hand made version
     */
    public static final int VERSION = 1;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Backend backend = Backend.getInstance();
        Data data = null;
        try {
            List<Customer> customers = backend.getCustomers();
            List<String> files = backend.getDataList();
            for ( String file : files ) {
                System.out.println( "Found file: " + file );
                data = backend.getData( file );
            }
        } catch ( FlytException fe ) {
            System.out.println( fe.getMessage() );
        }

        if ( VERSION == 1 ) {
            MainView layout = new MainView();
            setContent(layout);
        }
        if ( VERSION == 2 ) {
            setBody(new WelcomeView());
        }
    }

    public void setBody( Component body ) {
        MenuView menuView = new MenuView( this );
        HorizontalLayout vl = new HorizontalLayout();
        vl.addComponent( menuView );
        vl.addComponent( body );
        setContent( vl );
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = Flyt.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
