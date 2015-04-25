package db.patterns.proxy.impl;

import db.patterns.Benchmark;
import db.patterns.Inject;
import db.patterns.Singleton;
import db.patterns.Transactional;
import db.patterns.proxy.Dao;
import db.patterns.proxy.Person;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by StudentDB on 15.04.2015.
 */

@Singleton
public class MyDao implements Dao {

    @Override
    @Transactional
    @Benchmark(timeUnit = TimeUnit.HOURS)
    public Person getPerson(int id) {
        getPerson();
        Person person = personWeakHashMap.get(id);
        if (person == null) {
            person = dao.getPerson(id);
            personWeakHashMap.put(id, person);
        }
        return person;
    }

    @Override
    @Transactional
    public void getPerson() {
        System.out.println("empty");
    }

    @Inject
    private DaoImpl dao;

    private WeakHashMap<Integer, Person> personWeakHashMap = new WeakHashMap<>();
}
