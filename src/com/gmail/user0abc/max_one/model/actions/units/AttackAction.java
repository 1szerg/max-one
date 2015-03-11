package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.GameController;
import com.gmail.user0abc.max_one.handlers.TileSelectReceiver;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.entities.units.Unit;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

/**
 * Created by Sergey
 * at 11/12/14 10:16 PM
 */
public class AttackAction extends Ability implements TileSelectReceiver {

    private Unit attacker;

    @Override
    public boolean execute(GameContainer game, MapTile selectedTile) {
        if (selectedTile != null && selectedTile.unit != null) {
            attacker = selectedTile.unit;
            GameController.getCurrentInstance().selectAnotherTile(this);
        }
        return true;
    }

    @Override
    public AbilityType getType() {
        return AbilityType.ATTACK_TILE;
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
        if (!tile.building.onIncomingAttack(attacker.getAttack())) {
            tile.building = null;
        }
    }

    private void attackUnit(MapTile tile) {
        if (tile.unit.equals(attacker)) return;
        if (tile.unit.getOwner().equals(attacker.getOwner())) return;
        if (!tile.unit.onIncomingAttack(attacker.getAttack())) {
            tile.unit = null;
        }
    }
}
