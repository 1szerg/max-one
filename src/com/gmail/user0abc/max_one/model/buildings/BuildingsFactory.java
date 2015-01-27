package com.gmail.user0abc.max_one.model.buildings;

import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.util.Logger;

/**
 * Created by Sergey on 1/12/2015.
 */
public class BuildingsFactory {

    public static Building createBuildingAtLocation(MapTile tile, Player owner, BuildingType type) {
        tile.building = createBuilding(type);
        tile.building.setOwner(owner);
        tile.building.setTile(tile);
        return tile.building;
    }

    private static Building createBuilding(BuildingType type) {
        switch (type) {
            case TOWN:
                return new Town();
            case FARM:
                return new Farm();
            case TRADE_POST:
                return new TradePost();
            case CAMP:
                return new Camp();
        }
        Logger.log("TODO : not implemented building creation");
        return null;
    }

}
