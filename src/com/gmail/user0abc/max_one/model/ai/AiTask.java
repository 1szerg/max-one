package com.gmail.user0abc.max_one.model.ai;/*Created by Sergey on 4/4/2015.*/

import com.gmail.user0abc.max_one.model.actions.Ability;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

public interface AiTask {

    public Ability generateAction();

    public int getPriority();

    public AbilityType getType();

    public boolean isAssigned();

    void setAssigned(Entity e);

    Entity getAssigned();

    MapTile getLocation();

    boolean isBuilding();

    void setLocation(MapTile buildingSite);
}
