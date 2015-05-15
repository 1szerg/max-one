package com.gmail.user0abc.max_one.util;/* Created by iSzerg on 5/4/2015. */

import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

import java.util.Comparator;

public class NearestEntityComarator implements Comparator<Entity> {
    private MapTile target;

    public NearestEntityComarator(MapTile target) {
        this.target = target;
    }

    @Override
    public int compare(Entity lhs, Entity rhs) {
        return (int) Math.floor((GameUtils.distance(target.x, target.y, lhs.getCurrentTile().x, lhs.getCurrentTile().y) -
                GameUtils.distance(target.x, target.y, rhs.getCurrentTile().x, rhs.getCurrentTile().y)) * 100);
    }
}
