package com.gmail.user0abc.max_one.model.mapUtils;/* Created by iSzerg on 5/4/2015. */

import com.gmail.user0abc.max_one.model.terrain.MapTile;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ScanMapSearcher implements TileSearcher {
    private MapTile center;

    public ScanMapSearcher(MapTile center) {
        this.center = center;
    }

    @Override
    public List<MapTile> searchTiles(MapTile[][] map, List<TileFilter> filters) {
        List<MapTile> filteredTiles = new LinkedList<>();
        for(MapTile[] row: map){
            for(MapTile tile: row){
                if(TileFilterHelper.matchTile(tile, filters)) filteredTiles.add(tile);
            }
        }
        Collections.sort(filteredTiles, new NearestTileComparator(center));
        return filteredTiles;
    }
}
