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

        HorizontalLayout layout = new MainView();
        LineGraphComponent lg =new LineGraphComponent();
        String[] col = {"'doges'", "'cates'"};

        lg.setOptions(col,"'linechart_container'","'horror'","'vertikalt'");
        lg.run();
        setContent(layout);




    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = Flyt.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
