package flyt.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Fredrik
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataHeader {
    public String version = null;
    public String senderId = null;
}
