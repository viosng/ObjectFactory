package aspects;

import db.exceptions.DatabaseRuntimeException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by StudentDB on 17.04.2015.
 */

@Aspect
@Component
public class DBRuntimeExceptionAspect {

    @Value("${dba.mail}")
    private String[] dbaMails;

    private Map<DatabaseRuntimeException, DatabaseRuntimeException> databaseRuntimeExceptions = new WeakHashMap<>();

    @AfterThrowing(pointcut="execution(* db..*.*(..))", throwing="ex")
    public void doRecoveryActions(DatabaseRuntimeException ex) {
        if (databaseRuntimeExceptions.containsKey(ex)) return;
        databaseRuntimeExceptions.put(ex, ex);
        for (String dbaMail : dbaMails) {
            System.out.println("Sending email to " + dbaMail + " exception " + ex);
        }
    }
}
