package db.patterns.robots.impl;

import db.patterns.Inject;
import db.patterns.Self;
import db.patterns.Singleton;
import db.patterns.Transactional;
import db.patterns.robots.Cleaner;
import db.patterns.robots.Speaker;

import javax.annotation.PostConstruct;

/**
 * Created by StudentDB on 13.04.2015.
 */

@Singleton(lazyInit = true)
public class IRobot {

    @Self
    private IRobot iRobot;

    @Inject
    private Cleaner cleaner;

    @Inject
    private Speaker speaker;

    @PostConstruct
    private void init() {
        speaker.speak("abc");
    }

    @Transactional(required = false)
    public void subClean() {
        System.out.println("sub clean");
    }

    @Transactional
    public void cleanRoom() {
        speaker.speak("Start work");
        cleaner.clean();
        iRobot.subClean();
        speaker.speak("End work");
    }
}
