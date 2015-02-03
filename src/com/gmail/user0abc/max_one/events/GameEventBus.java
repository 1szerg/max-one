package com.gmail.user0abc.max_one.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sergey
 * at 11/22/14 1:52 AM
 */
public class GameEventBus {
    private static GameEventBus bus = new GameEventBus();
    private Map<GameEventType, Set<GameEventsSubscriber>> subscribers = new HashMap<>();

    public static GameEventBus getBus() {
        return bus;
    }

    public void fire(GameEvent event) {
        if (subscribers.containsKey(event.eventType)) {
            for (GameEventsSubscriber subscriber : subscribers.get(event.eventType)) {
                subscriber.onEvent(event);
            }
        }
    }

    public void subscribe(GameEventType type, GameEventsSubscriber subscriber) {
        if (!subscribers.containsKey(type)) {
            subscribers.put(type, new HashSet<GameEventsSubscriber>());
        }
        subscribers.get(type).add(subscriber);
    }

    public static enum GameEventType {
        TileClick,
        CommandSelect,
        TurnEnd,
        ScrollMap, TurnStart
    }
}
