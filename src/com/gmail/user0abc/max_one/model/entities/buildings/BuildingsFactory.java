package com.gmail.user0abc.max_one.model.entities.buildings;

import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.entities.Protection;
import com.gmail.user0abc.max_one.model.entities.ProtectionFactory;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.util.Logger;

/**
 * Created by Sergey on 1/12/2015.
 */
public class BuildingsFactory {

    public static Building createBuildingAtLocation(MapTile tile, Player owner, BuildingType type) {
        tile.building = createBuilding(type);
        tile.building.setOwner(owner);
        tile.building.setCurrentTile(tile);
        return tile.building;
    }

    private static Building createBuilding(BuildingType type) {
        switch (type) {
            case TOWN:
                Town town = new Town();
                town.setProtection(Town.defaultProtection());
                return town;
            case FARM:
                Farm farm = new Farm();
                farm.setProtection(Farm.defaultProtection());
                return farm;
            case TRADE_POST:
                TradePost post = new TradePost();
                post.setProtection(TradePost.defaultProtection());
                return post;
            case CAMP:
                Camp camp = new Camp();
                camp.setProtection(Camp.defaultProtection());
                return camp;
        }
        Logger.log("TODO : not implemented building creation of type "+type);
        return null;
    }

}
