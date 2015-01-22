package com.gmail.user0abc.max_one.util;

import com.gmail.user0abc.max_one.model.terrain.MapTile;

/**
 * Created by Sergey
 * at 11/26/14 12:52 AM
 */
public class GameMessage {
    public enum MessageType {
        Misc
    }

    private String message;
    private MapTile tile;
    private MessageType type;

    public GameMessage(MessageType messageType, MapTile tile, String message) {
        this.message = message;
        this.tile = tile;
        type = messageType;
    }
}
