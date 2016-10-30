package flyt.adapter;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Fredrik
 */
public class Line {
    public final String name;
    public final List<Integer> values;
    public Line( String name ) {
        this.name = name;
        values = Lists.newArrayList();
    }
    public void addValue( Integer i ) {
        values.add( i );
    }
}
