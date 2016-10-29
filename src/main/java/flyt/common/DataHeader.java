package flyt.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.joda.time.DateTime;

/**
 * @author Fredrik
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataHeader {
    public String version = null;
    public String senderId = null;
    public DateTime date = null;
}
