package db.map.reduce;

/**
 * Created by StudentDB on 10.04.2015.
 */
public interface Extractor<T, E> {
    T extract(E e);
}
