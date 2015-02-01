package com.gmail.user0abc.max_one.util;

import android.graphics.Color;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;
import com.gmail.user0abc.max_one.model.units.UnitType;
import com.gmail.user0abc.max_one.model.units.UnitsFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*Created by sergii.ivanov on 10/24/2014.*/
public class MapGenerator {
    public static Random random = new Random();
    public static int seed = 0;

    public static void setSeed(int s) {
        seed = s;
        random = new Random(seed);
    }

    public static MapTile[][] generateTerrain(int xSize, int ySize) {
        MapTile[][] map = new MapTile[xSize][ySize];
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                map[x][y] = generateTile(x, y);
            }
        }
        return map;
    }

    public static void placeStartPositions(List<Player> players, int seed, MapTile[][] map) {
        Random random = new Random(seed);
        int maxX = map.length;
        int maxY = maxX > 0 ? map[0].length : 0;
        for (int i = 0; i < players.size(); i++) {
            int startX;
            int startY;
            switch (i) {
                case 0:
                    startX = maxX / 8;
                    startY = maxY / 8;
                    break;
                case 1:
                    startX = maxX * 7 / 8;
                    startY = maxY * 7 / 8;
                    break;
                case 2:
                    startX = maxX * 7 / 8;
                    startY = maxY / 8;
                    break;
                case 3:
                    startX = maxX / 8;
                    startY = maxY * 7 / 8;
                    break;
                default:
                    startX = random.nextInt(maxX);
                    startY = random.nextInt(maxY);
                    break;
            }
            Logger.log("Player " + i + " start location (" + startX + ", " + startY + ")");
            UnitsFactory.createUnitAtLocation(map[startX][startY], players.get(i), UnitType.WORKER);
            UnitsFactory.createUnitAtLocation(map[startX][startY + 1], players.get(i), UnitType.WARRIOR);
        }
    }

    public static List<Player> getSinglePlayer() {
        List<Player> players = new ArrayList<Player>();
        Player player1 = new Player(false, 1);
        players.add(player1);
        Player player2 = new Player(true, 2);
        players.add(player2);
        return players;
    }

    private static int getPlayerColor(int i) {
        int[] colors = {Color.rgb(255, 255, 128), Color.rgb(128, 255, 255), Color.rgb(255, 128, 255),
                Color.rgb(128, 255, 128), Color.rgb(128, 255, 128), Color.rgb(255, 128, 128)};
        return colors[i];
    }

    private static MapTile generateTile(int x, int y) {
        MapTile tile = new MapTile();
        tile.explored = false;
        tile.building = null;
        tile.x = x;
        tile.y = y;
        tile.terrainType = (x * y * random.nextInt(100)) % 3 == 1 ? TerrainType.WATER : TerrainType.GRASS;
        if (tile.terrainType.equals(TerrainType.GRASS)) {
            if (random.nextInt(100) < 10) {
                tile.terrainType = TerrainType.TREE;
            } else {

            }
        }
        return tile;
    }
}
