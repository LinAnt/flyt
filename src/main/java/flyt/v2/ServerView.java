package flyt.v2;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

/**
 * @author Fredrik
 */
public class ServerView extends CustomComponent {
    public ServerView( String serverId ) {
        Label label = new Label( serverId );
        setCompositionRoot( label );
    }
}
