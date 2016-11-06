package flyt.v2;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

/**
 * @author Fredrik
 */
public class WelcomeView extends CustomComponent {

    public WelcomeView() {
        Label label = new Label( "This application is used to view and manage customer information and server installations." );
        setCompositionRoot( label );
    }
}
