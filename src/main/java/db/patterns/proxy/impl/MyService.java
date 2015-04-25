package db.patterns.proxy.impl;

import db.patterns.Benchmark;
import db.patterns.Inject;
import db.patterns.Singleton;
import db.patterns.Transactional;
import db.patterns.proxy.Dao;
import db.patterns.proxy.Service;

import javax.annotation.PostConstruct;

/**
 * Created by StudentDB on 15.04.2015.
 */

@Singleton
public class MyService implements Service {

    @Inject
    private Dao dao;

    @PostConstruct
    private void init(){
        System.out.println("MyService init");
    }

    @Transactional
    @Benchmark
    @Override
    public void print(String s) {
        System.out.println(s);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void doWork(int id) {
        System.out.println(dao.getPerson(id));
        print("Inner");
        dao.getPerson();
    }
}
