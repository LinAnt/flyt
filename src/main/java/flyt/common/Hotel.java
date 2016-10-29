package flyt.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Fredrik
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hotel {
    public String name;
    public Integer rooms;
}
