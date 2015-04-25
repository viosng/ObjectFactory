package db.patterns;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by StudentDB on 15.04.2015.
 */
public class TransactionProxyAnnotationProcessor<T> implements ProxyAnnotationProcessor<T> {

    private TransactionManager txManager = new TransactionManagerImpl();

    private Object invoke(Object o, Method method, Object[] objects, Object baseObject, Class aClass) throws Throwable {
        Method originalMethod = aClass.getMethod(method.getName(), method.getParameterTypes());
        Transactional annotation = originalMethod.getAnnotation(Transactional.class);
        if (annotation != null) {
            boolean notRequiredTransaction = !txManager.hasTransaction() && !annotation.required();
            if (annotation.required() || notRequiredTransaction) {
                txManager.openTransaction();
            }
            Object retVal = null;
            try {
                retVal = method.invoke(baseObject, objects);
            } catch (Exception e) {
                txManager.rollBackTransaction();
            }
            if (annotation.required() || notRequiredTransaction) {
                txManager.closeTransaction();
            }
            return retVal;
        } else {
            return method.invoke(baseObject, objects);
        }
    }

    @Override
    public T processAnnotation(Class<T> aClass, T object) {
        boolean hasTransaction = false;
        for (Method method : aClass.getMethods()) {
            if (method.getAnnotation(Transactional.class) != null) {
                hasTransaction = true;
            }
        }
        if (hasTransaction) {
            if (aClass.getInterfaces().length == 0) {
                return (T) Enhancer.create(aClass, new net.sf.cglib.proxy.InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        return TransactionProxyAnnotationProcessor.this.invoke(o, method, objects, object, aClass);
                    }
                });
            }
            return (T) Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return TransactionProxyAnnotationProcessor.this.invoke(proxy, method, args, object, aClass);
                }
            });
        }
        return object;
    }
}
