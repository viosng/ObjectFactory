package db.patterns.robots.impl;

import db.patterns.InjectRandomInt;
import db.patterns.Transactional;
import db.patterns.robots.Speaker;

/**
 * Created by StudentDB on 13.04.2015.
 */
public class ConsoleSpeaker implements Speaker {

    @InjectRandomInt
    private int rand;

    @Override
    @Transactional(required = false)
    public void speak(String message) {
        System.out.println(message);
    }
}
