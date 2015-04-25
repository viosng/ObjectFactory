package db.patterns;

/**
 * Created by StudentDB on 15.04.2015.
 */
public interface ProxyAnnotationProcessor<T> {
    T processAnnotation(Class<T> aClass, T object);
}
