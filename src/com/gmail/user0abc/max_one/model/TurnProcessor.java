package com.gmail.user0abc.max_one.model;/*Created by Sergey on 2/6/2015.*/

import android.os.AsyncTask;
import com.gmail.user0abc.max_one.GameController;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.entities.buildings.Building;
import com.gmail.user0abc.max_one.model.entities.units.Unit;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.util.GameStorage;
import com.gmail.user0abc.max_one.util.GameUtils;
import com.gmail.user0abc.max_one.util.Logger;

import java.util.Date;

import static com.gmail.user0abc.max_one.util.GameStorage.getStorage;

public class TurnProcessor extends AsyncTask<Player, MapTile, Boolean> {
    private long turnStarted;
    private GameContainer game;

    @Override
    protected void onPreExecute() {
        game = GameStorage.getStorage().getGame();
        turnStarted = new Date().getTime();
        Logger.log("turn " + game.turnsCount + " started at " + turnStarted);
    }

    @Override
    protected Boolean doInBackground(Player... currentPlayer) {
        Logger.log("Starting turn for player " + game.players.indexOf(game.currentPlayer));
        GameStorage.getStorage().setEntitiesMap(GameUtils.scanMap(game.map));
        if(GameStorage.getStorage().getEntitiesMap().containsKey(game.currentPlayer)){
            calculatePlayerBalances();
            Logger.log("INFO: Balance Apples " + game.currentPlayer.getApples() + " Gold " + game.currentPlayer.getGold());
            continueEntitiesActivities();
            if(game.currentPlayer.isAi){
                manageAi();
            }
        }else{
            game.currentPlayer.setDead();
        }
        return currentPlayer[0].isAi;
    }

    private void manageAi() {
        game.currentPlayer.aiProcessor.manageTasks(getStorage().getGame());
    }

    @Override
    protected void onProgressUpdate(MapTile... tiles) {
        GameController.getCurrentInstance().refreshTiles(tiles);
    }

    @Override
    protected void onPostExecute(Boolean isAi) {
        Logger.log("TurnProcessor turn #" + game.turnsCount + " for player " + game.players.indexOf(game.currentPlayer) +
                " duration " + (new Date().getTime() - turnStarted) / 1000 + " sec");
        if(isAi) GameController.getCurrentInstance().onTurnEnd();
    }

    private void continueEntitiesActivities() {
        for(Entity entity: GameStorage.getStorage().getEntitiesMap().get(game.currentPlayer)){
            entity.setActionPoints(entity.getMaxActionPoints());
            if(entity.getCurrentAction() != null){
                entity.getCurrentAction().execute();
            }
        }
    }

    public void calculatePlayerBalances() {
        int foodBalance = 0;
        int goldBalance = 0;
        for(Entity entity: GameStorage.getStorage().getEntitiesMap().get(game.currentPlayer)){
            foodBalance += getFoodBalance(entity);
            goldBalance += getGoldBalance(entity);
        }
        game.currentPlayer.setApples(foodBalance);
        game.currentPlayer.setGold(goldBalance);
    }

    private static int getFoodBalance(Entity entity) {
        if(entity instanceof Building){
            return ((Building) entity).getFoodProduction();
        }
        if(entity instanceof Unit){
            return entity.getApplesCost();
        }
        return 0;
    }

    private static int getGoldBalance(Entity entity) {
        if(entity instanceof Building){
            return ((Building) entity).getGoldProduction();
        }
        if(entity instanceof Unit){
            return entity.getGoldCost();
        }
        return 0;
    }

}
