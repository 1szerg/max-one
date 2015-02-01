package com.gmail.user0abc.max_one.model.actions;/*Created by Sergey on 1/29/2015.*/

import com.gmail.user0abc.max_one.model.Calculations;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

public class EndTurn extends Ability {
    @Override
    public void execute(GameContainer game, MapTile selectedTile) {
        int nextPlayerIndex = game.players.indexOf(game.currentPlayer) + 1;
        nextPlayerIndex = nextPlayerIndex >= game.players.size() ? 0 : nextPlayerIndex;
        game.currentPlayer = game.players.get(nextPlayerIndex);
        game.currentPlayer.setGold(Calculations.calculatePlayerGold(game, game.currentPlayer));
        game.currentPlayer.setApples(Calculations.calculatePlayerFood(game, game.currentPlayer));
    }


}
