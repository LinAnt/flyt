package flyt.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Fredrik
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    public DataHeader header = null;
    public List<Network> networks;
    public List<Hotel> hotels;
}
