package db.map.reduce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by StudentDB on 10.04.2015.
 */
public class MapperImpl<S, V, K extends Extractor<S, V>> implements Mapper<S, V, K> {
    @Override
    public Map<S, Iterable<V>> map(Iterable<V> iterable, K keyExtractor) {
        Map<S, Iterable<V>> result = new HashMap<>();
        for (V r : iterable) {
            List<V> values = (List<V>) result.get(keyExtractor.extract(r));
            if (values == null) {
                values = new ArrayList<>();
                result.put(keyExtractor.extract(r), values);
            }
            values.add(r);
        }
        return result;
    }
}
