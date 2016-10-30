package flyt.common;


import com.vaadin.server.Page;
import com.vaadin.ui.CustomComponent;

/**
 * Created by acl on 10/30/2016.
 */
public class PieChartComponent extends CustomComponent implements Chart {

    String pieData;
    String pieOptions;

    public void setPieData(String pieData) {
        this.pieData = pieData;
    }

    public void setPieOptions(String title, String container) {
        this.pieOptions = "{title: '"+title+"',container:'"+container+"'}";
        //  "{title:'Piechart',container:'piechart-container'}";
    }

    public PieChartComponent(){
        this.pieData = null;
        this.pieOptions = null;
    }

    @Override
    public void run() {
        System.out.println(this.pieData);
        System.out.println(this.pieOptions);
        Page.getCurrent().getJavaScript().execute("graph.createPieChart("+this.pieData+","+this.pieOptions+")");
    }
}
