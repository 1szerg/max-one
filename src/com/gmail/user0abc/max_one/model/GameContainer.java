package com.gmail.user0abc.max_one.model;

import com.gmail.user0abc.max_one.model.terrain.MapTile;

import java.io.Serializable;
import java.util.List;

/*  Created by iSzerg on 10/24/2014 */
public class GameContainer implements Serializable {
    public MapTile[][] map;
    public List<Player> players;
    public int seed;
    public int turnsCount = 0;
    public Player currentPlayer;

    public void nextPlayer() {
        if(currentPlayer == null){
            currentPlayer = players.get(0);
            return;
        }
        int nextPlayerIndex = players.indexOf(currentPlayer) + 1;
        if (nextPlayerIndex >= players.size()) {
            nextPlayerIndex = 0;
            turnsCount++;
        }
        currentPlayer = players.get(nextPlayerIndex);
    }

    public int nonAiPlayersLeft(){
        int c = 0;
        for(Player p: players){
            if(!p.isAi && p.isStillInGame()){
                c++;
            }
        }
        return c;
    }

    public int playersLeft() {
        int c = 0;
        for(Player p: players){
            if(p.isStillInGame())c++;
        }
        return c;
    }
}
