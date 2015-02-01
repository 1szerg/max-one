package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.GameController;
import com.gmail.user0abc.max_one.handlers.TileSelectReceiver;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.units.Unit;

/**
 * Created by Sergey
 * at 11/12/14 10:16 PM
 */
public class Attack extends Ability implements TileSelectReceiver {

    private Unit attacker;

    @Override
    public void execute(GameContainer game, MapTile selectedTile) {
        if (selectedTile != null && selectedTile.unit != null) {
            attacker = selectedTile.unit;
            GameController.getCurrentInstance().selectAnotherTile(this);
        }
    }

    @Override
    public void onTileSelect(MapTile tile) {
        if (tile.unit != null) {
            attackUnit(tile);
        } else if (tile.building != null) {
            attackBuilding(tile);
        }
    }

    private void attackBuilding(MapTile tile) {
        if (tile.building.getOwner().equals(attacker.getOwner())) return;
        if (!tile.building.acceptAttack(attacker.getAttackStrength())) {
            tile.building = null;
        }
    }

    private void attackUnit(MapTile tile) {
        if (tile.unit.equals(attacker)) return;
        if (tile.unit.getOwner().equals(attacker.getOwner())) return;
        if (!tile.unit.acceptAttack(attacker.getAttackStrength())) {
            tile.unit = null;
        }
    }
}
