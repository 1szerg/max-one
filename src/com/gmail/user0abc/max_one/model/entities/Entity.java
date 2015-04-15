package com.gmail.user0abc.max_one.model.entities;/*Created by Sergey on 3/7/2015.*/

import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.Attack;
import com.gmail.user0abc.max_one.model.actions.units.ActionFactory;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.util.Logger;

import java.util.List;

public abstract class Entity {
    protected MapTile currentTile;
    protected Ability currentAction;
    protected Protection protection;
    protected double health;
    protected Attack attack;
    protected int applesCost;
    protected int goldCost;
    protected Player owner;
    protected double actionPoints;
    protected int maxActionPoints;

    public boolean isAbilityAvailable(AbilityType abilityType) {
        return ActionFactory.isActionAvailable(abilityType, this);
    }

    public boolean isActiveAction(AbilityType abilityType) {
        return currentAction != null && currentAction.getType().equals(abilityType);
    }

    public boolean onIncomingAttack(Attack attack) {
        Double protectionRate = protection.getProtectionMatrix().get(attack.getAttackType());
        if (protectionRate == null || protectionRate < 1) {
            protectionRate = 1.0;
        }
        health -= attack.getAttackStrength() / protectionRate;
        Logger.log("ATTACK: " + this + " Health left " + Double.toString(health));
        return health > 0;
    }

    public MapTile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentAction(Ability currentAction) {
        this.currentAction = currentAction;
    }

    public Ability getCurrentAction() {
        return currentAction;
    }

    public int getApplesCost() {
        return applesCost;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public void setApplesCost(int applesCost) {
        this.applesCost = applesCost;
    }

    public void setGoldCost(int goldCost) {
        this.goldCost = goldCost;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public double getActionPoints() {
        return actionPoints;
    }

    public void setActionPoints(double actionPoints) {
        this.actionPoints = actionPoints;
    }

    public abstract void executeAction(AbilityType abilityType, GameContainer gameContainer, MapTile tile);

    public void setCurrentTile(MapTile currentTile) {
        this.currentTile = currentTile;
    }

    public void setAttack(Attack attack) {
        this.attack = attack;
    }

    public abstract List<AbilityType> getAvailableActions();

    public Attack getAttack(){
        return attack;
    };

    public Protection getProtection() {
        return protection;
    }

    public void setProtection(Protection protection) {
        this.protection = protection;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setMaxActionPoints(int maxActionPoints) {
        this.maxActionPoints = maxActionPoints;
    }

    public int getMaxActionPoints() {
        return maxActionPoints;
    }
}
