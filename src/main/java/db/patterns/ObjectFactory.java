package db.patterns;

import org.reflections.Reflections;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by StudentDB on 13.04.2015.
 */
public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    static {
        Reflections reflections = new Reflections("db.patterns");
        ourInstance.createAnnotationProcessors(reflections);
        ourInstance.createProxyProcessors(reflections);
        ourInstance.createSingletons(reflections);
    }

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    private Set<AnnotationProcessor> annotationProcessors;

    private Set<ProxyAnnotationProcessor> proxyAnnotationProcessors;

    private Map<Class<?>, Object> singletons;

    private ObjectFactory() {}

    private void createAnnotationProcessors(Reflections reflections) {
        annotationProcessors = new HashSet<>();
        for (Class<? extends AnnotationProcessor> aClass : reflections.getSubTypesOf(AnnotationProcessor.class)) {
            try {
                annotationProcessors.add(aClass.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createProxyProcessors(Reflections reflections) {
        proxyAnnotationProcessors = new HashSet<>();
        for (Class<? extends ProxyAnnotationProcessor> aClass : reflections.getSubTypesOf(ProxyAnnotationProcessor.class)) {
            try {
                proxyAnnotationProcessors.add(aClass.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createSingletons(Reflections reflections) {
        singletons = new HashMap<>();
        for (Class aClass : reflections.getTypesAnnotatedWith(Singleton.class)) {
            Singleton singleton = (Singleton) aClass.getAnnotation(Singleton.class);
            if (!singleton.lazyInit()) {
                try {
                    Object object = aClass.newInstance();
                    singletons.put(aClass, configure(object));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T createObject(Class<T> tClass) {
        try {
            T instance = (T) singletons.get(tClass);

            if (instance != null) {
                return instance;
            }

            if (tClass.isInterface()) {
                instance = (T) JavaConfig.getDefaultImpl(tClass).newInstance();
            } else {
                instance = tClass.newInstance();
            }

            instance = configure(instance);

            if (tClass.isAnnotationPresent(Singleton.class)) {
                singletons.put(tClass, instance);
            }

            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Exception during instantiation", e);
        }
    }

    private <T> T configure(T instance){
        try {
            for (AnnotationProcessor annotationProcessor : annotationProcessors) {
                annotationProcessor.processAnnotation(instance);
            }

            processPostConstructAnnotations(instance);

            Class<T> objectClass = (Class<T>) instance.getClass();

            T proxiedInstance = instance;

            for (ProxyAnnotationProcessor proxyAnnotationProcessor : proxyAnnotationProcessors) {
                proxiedInstance = (T)proxyAnnotationProcessor.processAnnotation(objectClass, proxiedInstance);
            }

            for (Field field : objectClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Self.class)) {
                    field.setAccessible(true);
                    field.set(instance, proxiedInstance);
                }
            }

            return proxiedInstance;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Exception during instantiation", e);
        }
    }

    private <T> void processPostConstructAnnotations(T instance) throws IllegalAccessException, InvocationTargetException {
        for (Method method : instance.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getAnnotation(PostConstruct.class) != null) {
                if (method.getParameterCount() > 0) {
                    throw new RuntimeException("PostConstruct method should have zero args");
                }
                method.invoke(instance);
            }
        }
    }
}
