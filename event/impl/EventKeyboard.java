package org.returnclient.event.impl;

import org.returnclient.event.Event;

public class EventKeyboard extends Event {

    public int key;

    public EventKeyboard(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
