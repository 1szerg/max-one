package com.gmail.user0abc.max_one.util;

import android.graphics.Color;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.ai.BasicAiProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey
 * at 11/27/14 11:58 PM
 */
public class GameUtils {
    public static Map<String, Object> generateEventDetails() {
        return new HashMap<>();
    }

    public static void sleep(long delayMs) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            Logger.log("Interrupted with " + e.getLocalizedMessage());
        }
    }

    public static List<Player> getSinglePlayer() {
        List<Player> players = new ArrayList<>();
        Player player1 = new Player(false, 1);
        players.add(player1);
        Player player2 = new Player(true, 2);
        player2.aiProcessor = new BasicAiProcessor();
        players.add(player2);
        return players;
    }

    private static int getPlayerColor(int i) {
        int[] colors = {Color.rgb(255, 255, 128), Color.rgb(128, 255, 255), Color.rgb(255, 128, 255),
                Color.rgb(128, 255, 128), Color.rgb(128, 255, 128), Color.rgb(255, 128, 128)};
        return colors[i];
    }

    public static double distance(int x1, int y1, int x2, int y2){
        return (Math.sqrt( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) ));
    }
}
