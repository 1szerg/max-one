package com.gmail.user0abc.max_one.model.mapUtils;/* Created by iSzerg on 5/4/2015. */

import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.util.GameUtils;

import java.util.Comparator;

public class NearestTileComparator implements Comparator<MapTile> {
    private MapTile target;

    public NearestTileComparator(MapTile target) {
        this.target = target;
    }

    @Override
    public int compare(MapTile lhs, MapTile rhs) {
        return (int) Math.floor((GameUtils.distance(target.x, target.y, lhs.x, lhs.y) -
                GameUtils.distance(target.x, target.y, rhs.x, rhs.y)) * 100);
    }
}
