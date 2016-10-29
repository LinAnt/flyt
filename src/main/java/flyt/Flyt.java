package flyt;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import flyt.backend.Backend;
import flyt.common.AccessDenied;

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

        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        final Label l = new Label();
        l.setId( "linechart-container" );
        final Label p = new Label();
        p.setId( "piechart-container" );
        final Label b = new Label();
        b.setId( "barchart-container" );

        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue()
                    + ", it works!"));
        });
        
        layout.addComponents(name, button, l, p, b);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);

        //how should data be sent
        final String tmpLineData = "[[0, 0, 0],   [1, 10, 5],   [2, 23, 15],  [3, 17, 9],   [4, 18, 10],  [5, 9, 5]," +
                "[6, 11, 3],   [7, 27, 19],  [8, 33, 25],  [9, 40, 32],  [10, 32, 24], [11, 35, 27]," +
                "[12, 30, 22], [13, 40, 32], [14, 42, 34], [15, 47, 39], [16, 44, 36], [17, 48, 40]," +
                "[18, 52, 44], [19, 54, 46], [20, 42, 34], [21, 55, 47], [22, 56, 48], [23, 57, 49]," +
                "[24, 60, 52], [25, 50, 42], [26, 52, 44], [27, 51, 43], [28, 49, 41], [29, 53, 45]," +
                "[30, 55, 47], [31, 60, 52], [32, 61, 53], [33, 59, 51], [34, 62, 54], [35, 65, 57]," +
                "[36, 62, 54], [37, 58, 50], [38, 55, 47], [39, 61, 53], [40, 64, 56], [41, 65, 57]," +
                "[42, 63, 55], [43, 66, 58], [44, 67, 59], [45, 69, 61], [46, 69, 61], [47, 70, 62]," +
                "[48, 72, 64], [49, 68, 60], [50, 66, 58], [51, 65, 57], [52, 67, 59], [53, 70, 62]," +
                "[54, 71, 63], [55, 72, 64], [56, 73, 65], [57, 75, 67], [58, 70, 62], [59, 68, 60]," +
                "[60, 64, 56], [61, 60, 52], [62, 65, 57], [63, 67, 59], [64, 68, 60], [65, 69, 61]," +
                "[66, 70, 62], [67, 72, 64], [68, 75, 67], [69, 80, 72]]";
        final String lineOptions = "{columns:['doges','cates'], " +
                "container: 'linechart-container'," +
                "haxis:'hurrisontal'," +
                "vaxis:'vertikulis'}";
        Page.getCurrent().getJavaScript().execute("graph.createLineChart("+tmpLineData+","+lineOptions+")");

        final String tmpPieData = "[['Task', 'Hours per Day'],['Work',11]," +
                "['Eat',20],['Commute',2]," +
                "['Gaming',91],['Sleep',7]]";
        final String pieOptions = "{title:'Piechart',container:'piechart-container'}";
        Page.getCurrent().getJavaScript().execute("graph.createPieChart("+tmpPieData+","+pieOptions+")");
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = Flyt.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
