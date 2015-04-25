package db.patterns.robots;

import db.patterns.ObjectFactory;
import db.patterns.robots.impl.IRobot;

/**
 * Created by StudentDB on 15.04.2015.
 */
public class Main {
    public static void main(String[] args) {
        IRobot iRobot = ObjectFactory.getInstance().createObject(IRobot.class);
        System.out.println(iRobot);
        iRobot.subClean();
        iRobot.cleanRoom();
        IRobot iRobot2 = ObjectFactory.getInstance().createObject(IRobot.class);
        System.out.println(iRobot2);
        iRobot2.cleanRoom();
        iRobot2.subClean();
    }
}
