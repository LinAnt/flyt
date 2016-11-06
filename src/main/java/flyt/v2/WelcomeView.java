package flyt.v2;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Fredrik
 */
public class WelcomeView extends CustomComponent {

    public WelcomeView() {
        VerticalLayout vl = new VerticalLayout();
        vl.addComponent(new Label( "Flyt" ));
        vl.addComponent(new Label( "This application is used to view and manage customer information and server installations." ));
        vl.addComponent(new Label( "Json serverdata is stored in directory: data/data*.json" ));
        vl.addComponent(new Label( "Servers can be drag-n-dropped between customers" ));
        vl.addComponent(new Label( "Other awesome features" ));
        vl.addComponent(new Label( "- Flyt crew" ));
        setCompositionRoot( vl );
    }
}
