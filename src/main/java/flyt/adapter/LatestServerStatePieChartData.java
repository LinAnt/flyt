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
    private final String name;
    private final String unit;

    public LatestServerStatePieChartData( Backend backend, String serverId, String name, String unit ) throws FlytException {
        List<Data> allData = backend.getDataForServerId( serverId );
        data = allData.get( allData.size() - 1 );
        this.name = name;
        this.unit = unit;
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
        String ret = "[['" + name + "','" + unit + "']";
        for ( PieChartPiece piece : getData() ) {
            ret += ",['" + piece.name + ',' + piece.value + ']';
        }
        ret += "}";
        return ret;
    }
}
