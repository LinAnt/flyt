package flyt.common;


import com.vaadin.server.Page;
import com.vaadin.ui.CustomComponent;

/**
 * Created by acl on 10/30/2016.
 */
public class PieChartComponent extends CustomComponent {


    public PieChartComponent(String PieData, String Options){
        Page.getCurrent().getJavaScript().execute("graph.createPieChart("+PieData+","+Options+")");
    }
    public PieChartComponent(){
        final String tmpPieData = "[['Task', 'Hours per Day'],['Work',11]," +
                "['Eat',20],['Commute',2]," +
                "['Gaming',91],['Sleep',7]]";
        final String pieOptions = "{title:'Piechart',container:'piechart-container'}";
        Page.getCurrent().getJavaScript().execute("graph.createPieChart("+tmpPieData+","+pieOptions+")");
    }
}
