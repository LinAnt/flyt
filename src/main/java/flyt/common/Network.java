package flyt.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Fredrik
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Network {
    public String name = null;
    public Integer devices = null;
}
