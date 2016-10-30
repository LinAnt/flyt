package flyt.adapter;

import java.util.List;

/**
 * @author Fredrik
 */
public interface PieChartDataAdapter {

    /**
     * Get the title of the pie chart
     * @return a title
     */
    public String getTitle();

    /**
     * Get a list of pie chart pieces
     * @return a list of pie chart pieces
     */
    public List<PieChartPiece> getData();

    /**
     * Get data as Json string
     * @return a json formated string
     */
    public String getDataAsJson();
}
