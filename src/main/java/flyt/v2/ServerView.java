package flyt.v2;

import com.vaadin.ui.*;
import flyt.adapter.LatestServerStatePieChartData;
import flyt.adapter.ServerLineChartData;
import flyt.backend.Backend;
import flyt.common.FlytException;
import flyt.common.LineGraphComponent;
import flyt.common.PieChartComponent;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Fredrik
 */
public class ServerView extends CustomComponent {
    public ServerView( String serverId ) {
        try {
            VerticalLayout vl = new VerticalLayout();
            LatestServerStatePieChartData latest = new LatestServerStatePieChartData(Backend.getInstance(), serverId);
            ServerLineChartData latestline = new ServerLineChartData(Backend.getInstance(), serverId, "Date", "Servers");

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

            vl.addComponent(lineChart);
            vl.addComponent(pieChart);
            setCompositionRoot( vl );
        }
        catch (FlytException fe){
            Notification.show( fe.getMessage(), Notification.Type.ERROR_MESSAGE );
        }


    }
}
