package db.patterns;

/**
 * Created by StudentDB on 15.04.2015.
 */
public interface TransactionManager {
    void openTransaction();
    void closeTransaction();
    boolean hasTransaction();
    void rollBackTransaction();
}
