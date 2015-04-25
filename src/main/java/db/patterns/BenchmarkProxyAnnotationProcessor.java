package db.patterns;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by StudentDB on 15.04.2015.
 */
public class BenchmarkProxyAnnotationProcessor<T> implements ProxyAnnotationProcessor<T> {

    private static Object invoke(Object o, Method method, Object[] objects, Object baseObject, Class aClass) throws Throwable {
        Method originalMethod = aClass.getMethod(method.getName(), method.getParameterTypes());
        Benchmark annotation = originalMethod.getAnnotation(Benchmark.class);
        if (annotation != null) {
            System.out.println("/ --- Benchmark ------ /");
            long start = System.currentTimeMillis();
            Object retVal = method.invoke(baseObject, objects);
            long end = System.currentTimeMillis();
            System.out.println("Working time: " +
                    annotation.timeUnit().convert(end - start, TimeUnit.MILLISECONDS) +  " " + annotation.timeUnit());
            System.out.println("/ --- Benchmark ------ /");
            return retVal;
        } else {
            return method.invoke(baseObject, objects);
        }
    }

    @Override
    public T processAnnotation(Class<T> aClass, T object) {
        boolean hasBenchmarks = false;
        for (Method method : aClass.getMethods()) {
            Benchmark methodAnnotation = method.getAnnotation(Benchmark.class);
            if (methodAnnotation != null) {
                hasBenchmarks = true;
            }
        }
        if (hasBenchmarks) {
            if (aClass.getInterfaces().length == 0) {
                return (T) Enhancer.create(aClass, new net.sf.cglib.proxy.InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        return BenchmarkProxyAnnotationProcessor.invoke(o, method, objects, object, aClass);
                    }
                });
            }
            return (T) Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return BenchmarkProxyAnnotationProcessor.invoke(proxy, method, args, object, aClass);
                }
            });
        }
        return object;
    }
}
