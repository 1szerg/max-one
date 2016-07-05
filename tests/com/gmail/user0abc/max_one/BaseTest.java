package com.gmail.user0abc.max_one;

import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;
import com.gmail.user0abc.max_one.util.GameStorage;
import org.junit.Before;

/**
 * @author sergii.ivanov
 */
public class BaseTest {

    @Before
    public static void testSetup() {
        GameStorage.getStorage().getGame().map = generateTestMap();
    }

    private static MapTile[][] generateTestMap() {
        MapTile[][] map = new MapTile[5][5];
        TerrainType[][] terrainTypes = {
                {TerrainType.GRASS, TerrainType.GRASS, TerrainType.GRASS, TerrainType.SAND,  TerrainType.SAND},
                {TerrainType.GRASS, TerrainType.GRASS, TerrainType.GRASS, TerrainType.SAND,  TerrainType.SAND},
                {TerrainType.HILL,  TerrainType.TREE,  TerrainType.SAND,  TerrainType.SAND,  TerrainType.WATER},
                {TerrainType.PEAK,  TerrainType.HILL,  TerrainType.TREE,  TerrainType.WATER, TerrainType.WATER},
                {TerrainType.HILL,  TerrainType.TREE,  TerrainType.TREE,  TerrainType.WATER, TerrainType.WATER}
        };
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                map[x][y] = generateTile(20, 20, terrainTypes[x][y], x, y);
            }
        }
        return map;
    }

    private static MapTile generateTile(double height, double humidity, TerrainType terrainType, int x, int y) {
        MapTile tile = new MapTile();
        tile.height = height;
        tile.humidity = humidity;
        tile.terrainType = terrainType;
        tile.x = x;
        tile.y = y;
        return tile;
    }
}
