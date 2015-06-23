package com.gmail.user0abc.max_one.model.actions.units;

import com.gmail.user0abc.max_one.GameController;
import com.gmail.user0abc.max_one.handlers.TileSelectReceiver;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

/**
 * Created by Sergey
 * at 11/12/14 10:16 PM
 */
public class AttackAction extends Ability implements TileSelectReceiver {

    private Entity attacker;

    @Override
    public boolean execute(Entity attackingEntity, MapTile tileBeingAttacked) {
        if(attackingEntity != null){
            attacker = attackingEntity;
        }
        if (tileBeingAttacked != null && tileBeingAttacked.unit != null) {
            attacker = tileBeingAttacked.unit;
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

    public static int getAPCost(){
        return 2;
    };

    public static boolean isAvailable(Entity entity) {
        return entity != null
                && entity.getAttack().getAttackStrength() > 0
                && entity.getActionPoints() >= getAPCost();
    }
}
