package flyt;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
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
    public static final int VERSION = 2;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        if ( VERSION == 1 ) {
            MainView layout = new MainView();
            setContent(layout);
        }
        if ( VERSION == 2 ) {
            setBody(new WelcomeView());
        }

        setSizeFull();
    }

    public void setBody( Component body ) {
        HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
        MenuView menuView = new MenuView( this );
        hsplit.setFirstComponent(menuView);
        hsplit.setSecondComponent(body);
        hsplit.setSplitPosition(20, Sizeable.Unit.PERCENTAGE );

        setContent( hsplit );
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = Flyt.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
