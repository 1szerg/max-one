package com.gmail.user0abc.max_one.model.entities.units;

import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.Attack;
import com.gmail.user0abc.max_one.model.actions.AttackType;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.util.Logger;

/*Created by Sergey on 1/12/2015.*/
public class UnitsFactory {

    public static Unit createUnitAtLocation(MapTile tile, Player owner, UnitType type) {
        tile.unit = createUnit(owner, type);
        tile.unit.setCurrentTile(tile);
        return tile.unit;
    }

    public static Unit createUnit(Player owner, UnitType type) {
        switch (type) {
            case WORKER:
                Worker worker = new Worker();
                worker.setOwner(owner);
                worker.setActionPoints(4);
                worker.setMaxActionPoints(4);
                worker.setGoldCost(0);
                worker.setApplesCost(1);
                worker.setAttack(null);
                worker.setProtection(Worker.defaultProtection());
                worker.setVisionRadius(2.0);
                return worker;
            case WARRIOR:
                Warrior warrior = new Warrior();
                warrior.setOwner(owner);
                warrior.setActionPoints(3);
                warrior.setMaxActionPoints(3);
                warrior.setAttack(new Attack(AttackType.SWORD, 5));
                warrior.setProtection(Warrior.defaultProtection());
                warrior.setVisionRadius(3.0);
                return warrior;
            case BARBARIAN:
                break;
            case SHIP:
                break;
        }
        Logger.log("TODO : unit generation is not implemented");
        return null;
    }

}
