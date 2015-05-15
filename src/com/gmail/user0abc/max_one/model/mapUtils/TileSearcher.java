package com.gmail.user0abc.max_one.model.mapUtils;/* Created by iSzerg on 4/22/2015. */

import com.gmail.user0abc.max_one.model.terrain.MapTile;

import java.util.List;

public interface TileSearcher {

    public List<MapTile> searchTiles(MapTile[][] map, List<TileFilter> filters);

}
