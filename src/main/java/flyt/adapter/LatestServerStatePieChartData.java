package flyt.adapter;

import com.google.common.collect.Lists;
import flyt.backend.Backend;
import flyt.common.Data;
import flyt.common.FlytException;
import flyt.common.Hotel;
import flyt.common.Network;

import java.util.List;

/**
 * @author Fredrik
 */
public class LatestServerStatePieChartData implements PieChartDataAdapter {

    private final Data data;

    public LatestServerStatePieChartData( Backend backend, String serverId ) throws FlytException {
        List<Data> allData = backend.getDataForServerId( serverId );
        data = allData.get( allData.size() - 1 );
    }

    @Override
    public String getTitle() {
        return "Server state - " + data.header.date;
    }

    @Override
    public List<PieChartPiece> getData() {
        List<PieChartPiece> pie = Lists.newArrayList();
        for ( Hotel hotel : data.hotels ) {
            pie.add( new PieChartPiece( hotel.name, hotel.rooms ) );
        }
        for (Network network : data.networks ) {
            pie.add( new PieChartPiece( network.name, network.devies ) );
        }
        return pie;
    }

    @Override
    public String getDataAsJson() {
        String ret = "[['Name','Devices']";
        for ( PieChartPiece piece : getData() ) {
            ret += ",['" + piece.name + ',' + piece.value + ']';
        }
        ret += "]";
        return ret;
    }
}
