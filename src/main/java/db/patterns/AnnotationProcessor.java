package db.patterns;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by StudentDB on 14.04.2015.
 */
public interface AnnotationProcessor<T> {
    void processAnnotation(T object) throws IllegalAccessException, InvocationTargetException;
}
