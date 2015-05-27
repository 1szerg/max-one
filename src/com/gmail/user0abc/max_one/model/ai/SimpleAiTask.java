package com.gmail.user0abc.max_one.model.ai;/* Created by iSzerg on 4/19/2015. */

import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.units.ActionFactory;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

public class SimpleAiTask implements AiTask {
    protected AbilityType abilityType;
    protected int priority;
    protected Entity assignedTo = null;
    protected MapTile location;

    public SimpleAiTask(AbilityType abilityType, int priority) {
        this.abilityType = abilityType;
        this.priority = priority;
    }

    public void setAbilityType(AbilityType abilityType) {
        this.abilityType = abilityType;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public Ability generateAction() {
        return ActionFactory.createAction(abilityType);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public AbilityType getType() {
        return abilityType;
    }

    @Override
    public boolean isAssigned() {
        return assignedTo != null;
    }

    @Override
    public void setAssigned(Entity e) {
        assignedTo = e;
    }

    @Override
    public Entity getAssigned() {
        return assignedTo;
    }

    @Override
    public MapTile getLocation() {
        return location;
    }

    @Override
    public boolean isBuilding() {
        return ActionFactory.ifBuilding(getType());
    }

    @Override
    public void setLocation(MapTile tile) {
        this.location = tile;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SimpleAiTask{");
        sb.append("abilityType=").append(abilityType);
        sb.append(", priority=").append(priority);
        sb.append('}');
        return sb.toString();
    }

}
