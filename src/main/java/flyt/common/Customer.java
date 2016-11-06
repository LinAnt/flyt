package flyt.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Fredrik
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
    public String name = null;
    public String contact = null;
    public List<String> servers = Lists.newArrayList();
}
