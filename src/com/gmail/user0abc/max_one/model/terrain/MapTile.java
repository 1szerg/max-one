package com.gmail.user0abc.max_one.model.terrain;

import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.entities.buildings.Building;
import com.gmail.user0abc.max_one.model.entities.units.Unit;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/*Created by sergii.ivanov on 10/24/2014.*/
public class MapTile implements Serializable {
    public TerrainType terrainType;
    public Building building;
    public TileFeature tileFeature;
    public Unit unit;
    public int x;
    public int y;
    public double height, humidity;
    public Set<Entity> visibleBy = new HashSet<>();
    public Set<Player> exploredBy = new HashSet<>();
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MapTile{");
        sb.append("terrainType=").append(terrainType);
        if (building != null) sb.append(", building=").append(building.getBuildingType()).append(" of ").append(building.getOwner());
        if (tileFeature != null) sb.append(", tileFeature=").append(tileFeature);
        if (unit != null) sb.append(", unit=").append(unit.getUnitType()).append(" of ").append(unit.getOwner());
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }

}
