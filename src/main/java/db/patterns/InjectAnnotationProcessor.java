package db.patterns;

import java.lang.reflect.Field;

/**
 * Created by StudentDB on 14.04.2015.
 */
public class InjectAnnotationProcessor<T> implements AnnotationProcessor<T> {
    @Override
    public void processAnnotation(T object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            Inject injectRandomInt = field.getAnnotation(Inject.class);
            if (injectRandomInt != null) {
                field.setAccessible(true);
                field.set(object, ObjectFactory.getInstance().createObject(field.getType()));
            }
        }
    }
}
