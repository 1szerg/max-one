package com.gmail.user0abc.max_one.model.mapUtils;/* Created by iSzerg on 5/14/2015. */

import com.gmail.user0abc.max_one.model.terrain.MapTile;

import java.util.LinkedList;
import java.util.List;

public class SpiralTileSearcher implements TileSearcher {
    private MapTile center;
    private int tilesCount = 1;

    public SpiralTileSearcher(MapTile center) {
        this.center = center;
    }

    public SpiralTileSearcher(MapTile center, int maxTiles) {
        this.center = center;
        tilesCount = maxTiles;
    }

    @Override
    public List<MapTile> searchTiles(MapTile[][] map, List<TileFilter> filters) {
        int mapSize = map.length;
        int r = 1;
        int offset = -r;
        List<MapTile> filteredTiles = new LinkedList<>();
        while (tilesCount > filteredTiles.size() && r < mapSize){
            addTiles(filteredTiles, map, center.x - r, center.y + offset, filters);
            addTiles(filteredTiles, map, center.x + r, center.y + offset, filters);
            addTiles(filteredTiles, map, center.x - offset, center.y + r, filters);
            addTiles(filteredTiles, map, center.x + offset, center.y + r, filters);
            if(offset >= r){
                r++;
                offset = -r;
            }
        }
        return filteredTiles;
    }

    private boolean addTiles(List<MapTile> filteredTiles, MapTile[][] map, int x, int y, List<TileFilter> filters) {
        if(x >= 0 && y >= 0 && x < map.length && y < map[0].length && TileFilterHelper.matchTile(map[x][y], filters)){
            filteredTiles.add(map[x][y]);
            return true;
        }
        return false;
    }
}
