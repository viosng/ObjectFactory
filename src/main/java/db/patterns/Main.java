package db.patterns;

import db.patterns.robots.Speaker;
import db.patterns.robots.impl.IRobot;

/**
 * Created by StudentDB on 14.04.2015.
 */
public class Main {
    public static void main(String[] args) {
        Speaker speaker = ObjectFactory.getInstance().createObject(Speaker.class);
        System.out.println(speaker);
        IRobot iRobot = ObjectFactory.getInstance().createObject(IRobot.class);
        iRobot.cleanRoom();
        System.out.println(iRobot);
        System.out.println(ObjectFactory.getInstance().createObject(IRobot.class));
    }
}
