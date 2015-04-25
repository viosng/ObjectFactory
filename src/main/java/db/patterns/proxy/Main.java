package db.patterns.proxy;

import db.patterns.ObjectFactory;

import java.util.Random;

/**
 * Created by StudentDB on 15.04.2015.
 */
public class Main {
    public static void main(String[] args) {
        Service service = ObjectFactory.getInstance().createObject(Service.class);
        Random random = new Random();
        for (int i = 0; i < 1; i++) {
            service.doWork(random.nextInt(3));
        }
    }
}
