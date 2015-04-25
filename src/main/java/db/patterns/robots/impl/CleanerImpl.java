package db.patterns.robots.impl;

import db.patterns.Singleton;
import db.patterns.Transactional;
import db.patterns.robots.Cleaner;

/**
 * Created by StudentDB on 13.04.2015.
 */

@Singleton
public class CleanerImpl implements Cleaner {
    @Override
    public void clean() {
        System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
    }
}
