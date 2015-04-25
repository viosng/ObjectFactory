package db.map.reduce;

import java.util.Map;

/**
 * Created by StudentDB on 09.04.2015.
 */
public interface BulkReducer<S, V, T, R extends Reducer<T>, E extends Extractor<T, V>> {
    Map<S, T> reduce(Map<S, Iterable<V>> map, R reducer, E extractor);
}
