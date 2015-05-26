package com.gmail.user0abc.max_one.model.mapUtils.filters;/* Created by iSzerg on 4/23/2015. */

import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;

import java.util.List;

public class TerrainWhiteListFilter implements TileFilter {
    List<TerrainType> whiteList;

    public TerrainWhiteListFilter(List<TerrainType> whiteList) {
        this.whiteList = whiteList;
    }

    @Override
    public boolean matchTile(MapTile tile) {
        return whiteList.contains(tile.terrainType);
    }
}
