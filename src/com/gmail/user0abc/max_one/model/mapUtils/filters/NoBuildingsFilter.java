package com.gmail.user0abc.max_one.model.mapUtils.filters;/* Created by iSzerg on 4/24/2015. */

import com.gmail.user0abc.max_one.model.terrain.MapTile;

public class NoBuildingsFilter implements TileFilter {
    @Override
    public boolean matchTile(MapTile tile) {
        return tile.building == null;
    }
}
