package com.gmail.user0abc.max_one.util;

import android.graphics.Color;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.ai.AiLevel;
import com.gmail.user0abc.max_one.model.ai.AiPersonality;
import com.gmail.user0abc.max_one.model.ai.AiPlayerFactory;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

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
        Player player2 = AiPlayerFactory.makeAiPlayer(AiLevel.EASY, AiPersonality.CHAOTIC);
        player2.banner = 2;
        players.add(player2);
        return players;
    }

    private static int getPlayerColor(int i) {
        int[] colors = {Color.rgb(255, 255, 128), Color.rgb(128, 255, 255), Color.rgb(255, 128, 255),
                Color.rgb(128, 255, 128), Color.rgb(128, 255, 128), Color.rgb(255, 128, 128)};
        return colors[i];
    }

    public static double distance(int x1, int y1, int x2, int y2) {
        return (Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
    }

    public static List<MapTile> getTilesNear(MapTile[][] map, MapTile tile) {
        List<MapTile> result = new ArrayList<>();
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                int x = tile.x + dx;
                int y = tile.y + dy;
                if (x > -1 && y > -1 && x < map.length && y < map[0].length) {
                    result.add(map[x][y]);
                }
            }
        }
        return result;
    }

    public static Map<Player, List<Entity>> scanMap(MapTile[][] map) {
        Map<Player, List<Entity>> entities = new HashMap<>();
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y].building != null) {
                    addToScan(entities, map[x][y].building);
                    map[x][y].building.updateHistory();
                }
                if (map[x][y].unit != null) {
                    addToScan(entities, map[x][y].unit);
                    map[x][y].unit.updateHistory();
                }
            }
        }
        return entities;
    }

    private static void addToScan(Map<Player, List<Entity>> mapScan, Entity entity) {
        if (!mapScan.containsKey(entity.getOwner())) mapScan.put(entity.getOwner(), new ArrayList<Entity>());
        mapScan.get(entity.getOwner()).add(entity);
    }

    public static List<MapTile> getTilesRadius(MapTile[][] map, MapTile centerTile, double radius) {
        List<MapTile> selectedTiles = new ArrayList<>();
        int centerX = centerTile.x;
        int centerY = centerTile.y;
        int radiusInt = (int) Math.ceil(radius);
        for (int x = (centerX - radiusInt); x <= (centerX + radiusInt); x++) {
            for (int y = (centerY - radiusInt); y <= (centerY + radiusInt); y++) {
                if ((x >= 0 && y >= 0 && x < map.length && y < map[0].length) && distance(x, y, centerX, centerY) <= radius) {
                    selectedTiles.add(map[x][y]);
                }
            }
        }
        return selectedTiles;
    }

    public static boolean tileExists(MapTile[][] map, int x, int y) {
        return (x >=0 && y >= 0 && x < map.length && y < map[x].length);
    }

    public static boolean entityBelongsCurrentPlayer(Entity entity){
        return entity != null && entity.getOwner() != null && entity.getOwner().equals(GameStorage.getStorage().getGame().currentPlayer);
    }
}
