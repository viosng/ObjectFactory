package db.patterns;

import db.patterns.proxy.Dao;
import db.patterns.proxy.Service;
import db.patterns.proxy.impl.MyDao;
import db.patterns.proxy.impl.MyService;
import db.patterns.robots.Cleaner;
import db.patterns.robots.Speaker;
import db.patterns.robots.impl.CleanerImpl;
import db.patterns.robots.impl.ConsoleSpeaker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by StudentDB on 13.04.2015.
 */
public class JavaConfig {
    private final static Map<Class, Class> DEFAULT_IMPLS;
    static {
        DEFAULT_IMPLS = new HashMap<>();
        DEFAULT_IMPLS.put(Speaker.class, ConsoleSpeaker.class);
        DEFAULT_IMPLS.put(Cleaner.class, CleanerImpl.class);
        DEFAULT_IMPLS.put(Dao.class, MyDao.class);
        DEFAULT_IMPLS.put(Service.class, MyService.class);
        DEFAULT_IMPLS.put(TransactionManager.class, TransactionManagerImpl.class);
    }

    public static Class getDefaultImpl(Class tClass) {
        return DEFAULT_IMPLS.get(tClass);
    }
}
