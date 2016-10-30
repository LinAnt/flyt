package flyt.adapter;

import java.util.List;

/**
 * @author Fredrik
 */
public interface LineChartDataAdapter {
    /**
     * Get title of line chart
     * @return a title
     */
    public String getTitle();

    /**
     * Get all lines
     * @return a list of all lines
     */
    public List<Line> getLines();

    /**
     * Get name of vertical axis
     * @return the name of vertical axis
     */
    public String getVerticalAxisName();

    /**
     * Get name of horizontal axis
     * @return the name of horizontal axis
     */
    public String getHorizontalAxisName();

    /**
     * Get data as json string
     * @return a json string
     */
    public String getDataAsJson();
}
