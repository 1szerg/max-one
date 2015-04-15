package com.gmail.user0abc.max_one.model.ai;/*Created by Sergey on 4/4/2015.*/

import com.gmail.user0abc.max_one.model.AiProcessor;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.ai.task.AiTask;
import com.gmail.user0abc.max_one.model.ai.task.AiUtils;
import com.gmail.user0abc.max_one.model.entities.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleAi implements AiProcessor {

    Map<Player, List<Entity>> mapScan = new HashMap<>();
    Map<Class, Integer> entitiesCount = new HashMap<>();
    Map<Entity, List<AiTask>> plan = new HashMap<>();

    @Override
    public void manageTasks(GameContainer game) {
        mapScan = AiUtils.scanMap(game.map);
        analyzeScan(game);
        reviewPlan(game);
        for(Entity e: plan.keySet()){
            if(e.getCurrentAction() == null){
                if(plan.get(e).size() > 0){
                    e.setCurrentAction(plan.get(e).get(0).generateAction());
                    plan.get(e).remove(0);
                }
            }
        }
    }

    private void analyzeScan(GameContainer game) {
        if(mapScan.containsKey(game.currentPlayer)){

        }else{
            game.currentPlayer.setDead();
        }
    }

    private void reviewPlan(GameContainer game) {

    }

}
