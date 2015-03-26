package com.gmail.user0abc.max_one.model;/*Created by Sergey on 2/6/2015.*/

import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.util.Logger;

import java.util.Date;

public class TurnProcessor {
    private static final long MINIMAL_TURN_DURATION = 1000;
    GameContainer game;
    private long turnStarted;

    public TurnProcessor(GameContainer game) {
        this.game = game;
    }

    public void onStart() {
        turnStarted = new Date().getTime();
        calculatePlayerBalances();
        Logger.log("INFO: Balance Apples " + game.currentPlayer.getApples() + " Gold " + game.currentPlayer.getGold());
        scanMap();
    }

    public boolean onFinish() {
        Logger.log("End turn #" + game.turnsCount + " for player " + game.players.indexOf(game.currentPlayer) + " duration " + (new Date().getTime() - turnStarted) / 1000 + " sec");
        Logger.log("Ending turn #" + game.turnsCount + " for player " + game.players.indexOf(game.currentPlayer));
        int nextPlayerIndex = game.players.indexOf(game.currentPlayer) + 1;
        if (nextPlayerIndex >= game.players.size()) {
            nextPlayerIndex = 0;
            game.turnsCount++;
        }
        game.currentPlayer = game.players.get(nextPlayerIndex);
        return true;
    }

    public void calculatePlayerBalances() {
        int foodBalance = 0;
        int goldBalance = 0;
        for (int x = 0; x < game.map.length; x++) {
            for (int y = 0; y < game.map[x].length; y++) {
                foodBalance += getFoodCosts(game.map[x][y], game.currentPlayer);
                goldBalance += getGoldCosts(game.map[x][y], game.currentPlayer);
            }
        }
        game.currentPlayer.setApples(foodBalance);
        game.currentPlayer.setGold(goldBalance);
    }

    private static int getFoodCosts(MapTile tile, Player player) {
        int res = 0;
        if (tile.unit != null && tile.unit.getOwner().equals(player)) {
            res -= tile.unit.getApplesCost();
        }
        if (tile.building != null && tile.building.getOwner().equals(player)) {
            res += tile.building.getApplesProduction();
        }
        return res;
    }

    private static int getGoldCosts(MapTile tile, Player player) {
        int res = 0;
        if (tile.unit != null && tile.unit.getOwner().equals(player)) {
            res -= tile.unit.getGoldCost();
        }
        if (tile.building != null && tile.building.getOwner().equals(player)) {
            res += tile.building.getGoldProduction();
        }
        return res;
    }

    private void scanMap() {
        for (int x = 0; x < game.map.length; x++) {
            for (int y = 0; y < game.map[x].length; y++) {
                processTileOnStartTurn(game.map[x][y], game.currentPlayer);
            }
        }
    }

    public void processTileOnStartTurn(MapTile tile, Player player) {
        resetUnitActionPoints(tile, player);
        continueActions(tile, player);
    }

    private void resetUnitActionPoints(MapTile mapTile, Player player) {
        if (mapTile.unit != null && mapTile.unit.getOwner().equals(player)) {
            mapTile.unit.setActionPoints(mapTile.unit.getMaxActionPoints());
        }
    }

    private void continueActions(MapTile tile, Player player) {
        if (tile.unit != null && tile.unit.getOwner().equals(player)) {
            tile.unit.onTurnStart();
        }
    }

}
