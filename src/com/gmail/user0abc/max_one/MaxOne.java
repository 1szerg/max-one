package com.gmail.user0abc.max_one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.util.*;

import java.util.Random;

public class MaxOne extends Activity {
    public static final String GAME_CONTAINER = "GAME_CONTAINER";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Logger.log("MaxOne started");
    }

    public void newGame(View view) {
        int seed = new Random().nextInt(100);
        Logger.log("Creating game with seed " + seed);
        GameContainer game = new GameContainer();
        game.seed = seed;
        game.players = GameUtils.getSinglePlayer();
        game.map = MapGenerator.getGenerator(RandUtil.getRand(game.seed)).generateTerrain(25, 25, game.players);
        GameStorage.getStorage().setGameContainer(game);
        Intent intent = new Intent(this, GameController.class);
        startActivity(intent);
    }
}
