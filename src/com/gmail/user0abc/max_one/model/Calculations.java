package com.gmail.user0abc.max_one.model;/*Created by Sergey on 1/29/2015.*/

import com.gmail.user0abc.max_one.model.terrain.MapTile;

public class Calculations {

    public static int calculatePlayerFood(GameContainer game, Player currentPlayer) {
        int foodBalance = 1;
        for (int x = 0; x < game.map.length; x++) {
            for (int y = 0; y < game.map[x].length; y++) {
                foodBalance += getFoodCosts(game.map[x][y], currentPlayer);
            }
        }
        return foodBalance;
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

    public static int calculatePlayerGold(GameContainer game, Player currentPlayer) {
        int goldBalance = 1;
        for (int x = 0; x < game.map.length; x++) {
            for (int y = 0; y < game.map[x].length; y++) {
                goldBalance += getGoldCosts(game.map[x][y], currentPlayer);
            }
        }
        return goldBalance;
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
}
