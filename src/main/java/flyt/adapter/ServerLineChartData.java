package flyt.adapter;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import flyt.backend.Backend;
import flyt.common.Data;
import flyt.common.FlytException;
import flyt.common.Hotel;
import flyt.common.Network;

import java.util.List;

/**
 * @author Fredrik
 */
public class ServerLineChartData implements LineChartDataAdapter {

    private String verticalName;
    private String horizontalName;
    private final List<Data> allData;

    public ServerLineChartData(Backend backend, String serverId, String verticalName, String horizontalName ) throws FlytException {
        allData = backend.getDataForServerId( serverId );
        this.verticalName = verticalName;
        this.horizontalName = horizontalName;
    }

    @Override
    public String getTitle() {
        return "Server data";
    }

    @Override
    public List<Line> getLines() {
        Multimap<String, Integer> linesMap = ArrayListMultimap.create();
        for ( Data data : allData ) {
            for ( Network network : data.networks ) {
                linesMap.put( network.name, network.devies );
            }
            for ( Hotel hotel : data.hotels ) {
                linesMap.put( hotel.name, hotel.rooms );
            }
        }
        List<Line> allLines = Lists.newArrayList();
        for ( String key : linesMap.keys() ) {
            Line line = new Line( key );
            for ( Integer value : linesMap.get( key ) ) {
                line.addValue( value );
            }
            allLines.add( line );
        }
        return allLines;
    }

    @Override
    public String getVerticalAxisName() {
        return verticalName;
    }

    @Override
    public String getHorizontalAxisName() {
        return horizontalName;
    }

    @Override
    public String getDataAsJson() {
        String ret = "[";
        for ( Data data : allData ) {
            ret += "[" + i + ",";
            for ( Hotel hotel : data.hotels ) {
                ret += hotel.rooms + ",";
            }
            for ( Network network : data.networks ) {
                ret += network.devies + ",";
            }
            ret += "]";
        }
        ret += "]";
        return ret;
    }
}
