package com.gmail.user0abc.max_one.test;

import com.gmail.user0abc.max_one.GameController;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.util.GameStorage;
import com.gmail.user0abc.max_one.util.MapGenerator;
import com.gmail.user0abc.max_one.util.MapGeneratorProgressDisplay;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/* Created by iSzerg on 6/25/2015. */
public class GameControllerTest extends TestCase {
    GameController gameController;

    public void setUp() throws Exception {
        super.setUp();
        GameStorage.getStorage().setGame(makeTestContainer());
        gameController = new GameController();
    }

    private GameContainer makeTestContainer() {
        GameContainer gameContainer = new GameContainer();
        gameContainer.players = Arrays.asList(new Player(false, 0), new Player(true, 1));
        gameContainer.seed = 1000;
        gameContainer.map = makeTestMap(gameContainer.players, gameContainer.seed);
        return gameContainer;
    }

    private MapTile[][] makeTestMap(List<Player> players, int seed) {
        Random r = new Random(seed);
        return MapGenerator.getGenerator(r).generateTerrain(15, 15, players, new MapGeneratorProgressDisplay() {
            @Override
            public void updateDisplay(int progressVolume) {}
        });
    }

    public void testOnTileSelect() throws Exception {

    }

    public void testGetCurrentActionButtons() throws Exception {

    }

    public void testOnActionButtonSelect() throws Exception {

    }

    public void testOnTurnEnd() throws Exception {

    }
}