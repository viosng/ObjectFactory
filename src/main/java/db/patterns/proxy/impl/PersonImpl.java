package db.patterns.proxy.impl;

import db.patterns.proxy.Person;

/**
 * Created by StudentDB on 15.04.2015.
 */
public class PersonImpl implements Person {
    private final int id;

    public PersonImpl(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "PersonImpl{" +
                "id=" + id +
                '}';
    }
}
