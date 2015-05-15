package com.gmail.user0abc.max_one.model.ai;/*Created by Sergey on 4/6/2015.*/

import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.entities.buildings.Building;
import com.gmail.user0abc.max_one.model.entities.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.entities.units.Unit;
import com.gmail.user0abc.max_one.model.entities.units.UnitType;

import java.util.List;

public class AiUtils {

    public static int countBuildings(List<Entity> entities, BuildingType type) {
        int c = 0;
        for(Entity entity: entities){
            if(entity instanceof Building){
                if(((Building) entity).getBuildingType().equals(type)) c++;
            }
        }
        return c;
    }

    public static int countUnits(List<Entity> entities, UnitType unitType) {
        int c = 0;
        for(Entity entity : entities){
            if(entity instanceof Unit){
                if(((Unit) entity).getUnitType().equals(UnitType.WORKER))c++;
            }
        }
        return c;
    }
}
