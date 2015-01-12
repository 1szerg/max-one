package com.gmail.user0abc.max_one.model.units;

import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.util.Logger;

/**
 * Created by Sergey on 1/12/2015.
 */
public class UnitsFactory {

    public static Unit createUnitAtLocation(MapTile tile, Player owner, UnitType type){
        tile.unit = createUnit(owner, type);
        tile.unit.setCurrentTile(tile);
        return tile.unit;
    }

    public static Unit createUnit(Player owner, UnitType type){
        switch (type){
            case WORKER:
                Worker worker = new Worker();
                worker.setOwner(owner);
                worker.setActionPoints(worker.getMaxActionPoints());
                return worker;
            case WARRIOR:
                break;
            case BARBARIAN:
                break;
            case SHIP:
                break;
        }
        Logger.log("TODO : unit generation is not implemented");
        return null;
    }

}
