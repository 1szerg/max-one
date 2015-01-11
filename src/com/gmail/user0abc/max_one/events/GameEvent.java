package com.gmail.user0abc.max_one.events;

import java.util.Map;

/**
 * Created by Sergey
 * at 11/22/14 1:55 AM
 */
public class GameEvent {
    public GameEventBus.GameEventType eventType;
    public GameEventDetails eventDetails;
    public boolean consumed = false;

    public GameEvent(GameEventBus.GameEventType eventType, GameEventDetails eventDetails) {
        this.eventType = eventType;
        this.eventDetails = eventDetails;
    }
}
