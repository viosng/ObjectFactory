package db.map.reduce;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by StudentDB on 10.04.2015.
 */
public class BulkReducerImpl<S, V, T, R extends Reducer<T>, E extends Extractor<T, V>> implements BulkReducer<S, V, T, R, E> {
    @Override
    public Map<S, T> reduce(Map<S, Iterable<V>> map, R reducer, E extractor) {
        Map<S, T> result = new HashMap<>();
        for (Map.Entry<S, Iterable<V>> entry : map.entrySet()) {
            T value = result.get(entry.getKey());
            if (value == null) {
                value = reducer.getDefault();
            }
            for (V e : entry.getValue()) {
                value = reducer.reduce(value, extractor.extract(e));
            }
            result.put(entry.getKey(), value);
        }
        return result;
    }
}
