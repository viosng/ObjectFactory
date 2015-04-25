package db.map.reduce;

/**
 * Created by StudentDB on 10.04.2015.
 */
public interface Reducer<E> {
    E reduce(E e1, E e2);

    E getDefault();
}
