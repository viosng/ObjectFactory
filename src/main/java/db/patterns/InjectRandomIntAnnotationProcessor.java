package db.patterns;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by StudentDB on 14.04.2015.
 */
public class InjectRandomIntAnnotationProcessor<T> implements AnnotationProcessor<T> {
    private final static Random random = new Random();

    @Override
    public void processAnnotation(T object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            InjectRandomInt injectRandomInt = field.getAnnotation(InjectRandomInt.class);
            if (injectRandomInt != null) {
                field.setAccessible(true);
                field.set(object, random.nextInt(injectRandomInt.max() - injectRandomInt.min()) + injectRandomInt.min());
            }
        }
    }
}
