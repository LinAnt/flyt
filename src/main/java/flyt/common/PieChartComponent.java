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

    public void setPieOptions(String pieOptions) {
        this.pieOptions = pieOptions;
    }

    public PieChartComponent(){
        this.pieData = null;
        this.pieOptions = null;
    }

    @Override
    public void run() {
        Page.getCurrent().getJavaScript().execute("graph.createPieChart("+this.pieData+","+this.pieOptions+")");
    }
}
