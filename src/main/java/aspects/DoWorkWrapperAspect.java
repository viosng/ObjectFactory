package aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by StudentDB on 17.04.2015.
 */

@Aspect
@Component
public class DoWorkWrapperAspect {
    @Pointcut("execution(* db..*.doWork())")
    public void defineEntryPoint() {
    }

    @Before("defineEntryPoint()")
    public void aaa(JoinPoint joinPoint) {
        System.out.println("aspect before");
    }

    @After("defineEntryPoint()")
    public void bbb(JoinPoint joinPoint) {
        System.out.println("aspect after " + joinPoint.getSignature().getName());
    }
}
