package db.patterns.robots.impl;

import db.patterns.Transactional;
import db.patterns.robots.Alarm;
import db.patterns.robots.Radio;

/**
 * Created by StudentDB on 13.04.2015.
 */
public class RadioAlarm {
    private Radio radio;
    private Alarm alarm;

    public RadioAlarm(Radio radio, Alarm alarm) {
        this.radio = radio;
        this.alarm = alarm;
    }

    public Radio getRadio() {
        return radio;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarmTime() {
        alarm.setAlarmTime();
    }

    public void stop() {
        alarm.stop();
    }

    public void setChannel() {
        radio.setChannel();
    }

    public void setVolume() {
        radio.setVolume();
    }
}
