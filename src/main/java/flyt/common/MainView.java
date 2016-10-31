package flyt.common;

import com.google.common.base.Joiner;
import com.vaadin.data.Property;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import flyt.adapter.LatestServerStatePieChartData;
import flyt.adapter.LineChartDataAdapter;
import flyt.adapter.ServerLineChartData;
import flyt.backend.Backend;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by acl on 10/30/2016.
 */
public class MainView extends MainDesign {

    public MainView() {
        super();
        clearContent();
        try {
            customerButton.addClickListener( new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                }
            });
            List<Customer> customerList = Backend.getInstance().getCustomers();
            customerListSelect.setNullSelectionAllowed(false);

            for (Customer C : customerList) {

                customerListSelect.addItem(C.name);
            }
            MenuBar.MenuItem menuItem = serverBar.getItems().get(0);
            for (Customer c : customerList) {
                for (String server : c.servers) {
                    menuItem.addItem(server, (MenuBar.Command) mi -> DisplayServerStats(server));
                }
            }
            customerListSelect.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                    String a = (String)customerListSelect.getValue();
                    String customer = customerListSelect.getContainerDataSource().getItem(a).toString();
                    setContent(new customerFormView(customer));
                }
            });
            logoutButton.addClickListener(e -> {
                System.out.println("User Logged Out");
            });
        } catch (FlytException fe) {
            // fix better error handling
        }
    }


    private void clearContent() {
        Label l = new Label();
        l.setValue("Please select a Customer, a Server or Check out the Statistics! :)");
        content.removeAllComponents();
        content.addComponent(l);
    }
    public void setContent(Component c){

        content.removeAllComponents();
        content.addComponent(c);

    }
    public void setContent(List<Component> c){
        content.removeAllComponents();
        for(Component a : c){
           content.addComponent(a);
        }
    }

    private void DisplayServerStats(String server) {
        try {
            LinkedList<Component> cList = new LinkedList<>();
            LatestServerStatePieChartData latest = new LatestServerStatePieChartData(Backend.getInstance(), server);
            ServerLineChartData latestline = new ServerLineChartData(Backend.getInstance(), server, "Date", "Servers");

            Label pieChart = new Label();
            pieChart.setId("latest-pie");
            Label lineChart = new Label();
            lineChart.setId("server-line");

            PieChartComponent PCS = new PieChartComponent();
            PCS.setPieData(latest.getDataAsJson());
            PCS.setPieOptions(latest.getTitle(),"latest-pie");
            PCS.run();

            LineGraphComponent LGC = new LineGraphComponent();
            LGC.setLineData(latestline.getDataAsJson());
            List<String> lineNames = latest.getLineNames();
            String names = "";
            for ( int i = 0; i < lineNames.size(); i ++ ) {
                names += "'" + lineNames.get( i ) + "'";
                if ( i != lineNames.size() - 1 ) {
                    names += ",";
                }
            }
            LGC.setOptions(names,"server-line", latestline.getHorizontalAxisName(), latestline.getVerticalAxisName());

            LGC.run();

            cList.add(lineChart);
            cList.add(pieChart);
            setContent(cList);




    }
        catch (FlytException fe){
            // Implement ErrorHandling
        }


    }
}