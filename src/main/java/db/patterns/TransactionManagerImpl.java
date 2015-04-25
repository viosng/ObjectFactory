package db.patterns;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by StudentDB on 15.04.2015.
 */

@Component
public class TransactionManagerImpl implements TransactionManager {

    private ThreadLocal<AtomicLong> threadLocalCounter = new ThreadLocal<>();

    public TransactionManagerImpl() {
        threadLocalCounter.set(new AtomicLong());
    }

    @Override
    public void openTransaction() {
        System.out.println("----> Open transaction");
        threadLocalCounter.get().incrementAndGet();
    }

    @Override
    public void closeTransaction() {
        System.out.println("----> Close transaction");
        threadLocalCounter.get().decrementAndGet();
    }

    @Override
    public boolean hasTransaction() {
        return threadLocalCounter.get().get() > 0;
    }

    @Override
    public void rollBackTransaction() {
        System.out.println("----> Rollback transaction");
        threadLocalCounter.get().decrementAndGet();
    }
}
