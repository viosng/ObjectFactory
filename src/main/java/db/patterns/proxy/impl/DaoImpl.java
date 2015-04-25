package db.patterns.proxy.impl;

import db.patterns.Singleton;
import db.patterns.proxy.Dao;
import db.patterns.proxy.Person;

/**
 * Created by StudentDB on 15.04.2015.
 */

@Singleton
public class DaoImpl implements Dao {

    @Override
    public Person getPerson(int id) {
        System.out.println("Waiting for db answer for 1 sec");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new PersonImpl(id);
    }

    @Override
    public void getPerson() {

    }
}
