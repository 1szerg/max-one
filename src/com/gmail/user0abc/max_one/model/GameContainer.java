package com.gmail.user0abc.max_one.model;

import com.gmail.user0abc.max_one.model.terrain.MapTile;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sergii.ivanov on 10/24/2014.
 */
public class GameContainer implements Serializable {
    public MapTile[][] map;
    public List<Player> players;
    public int seed;
    public int turnsCount = 0;
    public Player currentPlayer;
}
