package com.gmail.user0abc.max_one.util;

import com.gmail.user0abc.max_one.model.terrain.MapTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey
 * at 11/26/14 12:49 AM
 */
public class GameMessages {
    private static List<GameMessage> messagesToDisplay = new ArrayList<>();

    public static void add(MapTile tile, String message) {
        messagesToDisplay.add(new GameMessage(GameMessage.MessageType.Misc, tile, message));
    }

    public static void add(GameMessage.MessageType messageType, MapTile tile, String message) {
        messagesToDisplay.add(new GameMessage(messageType, tile, message));
    }

    public static void error(Exception e) {
        Logger.log("ERROR");
        for(StackTraceElement el:e.getStackTrace()){
            Logger.log(el.toString());
        }
    }
}
