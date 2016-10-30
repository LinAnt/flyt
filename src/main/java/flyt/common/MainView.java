package flyt.common;

import com.vaadin.data.Property;
import com.vaadin.ui.*;
import flyt.adapter.LatestServerStatePieChartData;
import flyt.adapter.LineChartDataAdapter;
import flyt.adapter.ServerLineChartData;
import flyt.backend.Backend;
import javafx.scene.chart.PieChart;

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


    private void clearContent() {
        Label l = new Label();
        l.setValue("Please select a Customer, a Server or Check out the Statistics! :)");
        content.removeAllComponents();
        content.addComponent(l);
    }
    private void setContent(List<Component> c){
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
            LGC.setOptions("'doges','cates'","server-line", latestline.getHorizontalAxisName(), latestline.getVerticalAxisName());

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