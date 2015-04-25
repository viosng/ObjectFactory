package db.map.reduce;

import java.util.Map;

/**
 * Created by StudentDB on 10.04.2015.
 */
public interface Mapper<S, V, K extends Extractor<S, V>> {
    Map<S, Iterable<V>> map(Iterable<V> iterable, K keyExtractor);
}
