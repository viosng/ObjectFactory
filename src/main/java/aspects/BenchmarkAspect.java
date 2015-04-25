package aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by StudentDB on 17.04.2015.
 */

@Component
@Aspect
public class BenchmarkAspect {

    @Autowired
    private BenchmarkController controller;

    @Pointcut("execution(* db..*.doWork())")
    public void defineEntryPoint() {
    }

    @Around("defineEntryPoint()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        if (controller.isEnabled()) {
            System.out.println("Benchmark");
        }
        return joinPoint.proceed();
    }

}
