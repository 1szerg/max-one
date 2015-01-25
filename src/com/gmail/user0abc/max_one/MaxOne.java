package com.gmail.user0abc.max_one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.util.GameStorage;
import com.gmail.user0abc.max_one.util.MapGenerator;

import java.util.Random;

public class MaxOne extends Activity {
    public static final String GAME_CONTAINER = "GAME_CONTAINER";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d("d", "MaxOne started");
    }

    public void newGame(View view) {
        int seed = new Random().nextInt(100);
        Log.d("d", "Creating game with seed " + seed);
        GameContainer game = new GameContainer();
        game.seed = seed;
        MapGenerator.setSeed(seed);
        game.map = MapGenerator.generateTerrain(11, 11);
        game.players = MapGenerator.getSinglePlayer();
        MapGenerator.placeStartPositions(game.players, game.seed, game.map);
        GameStorage.getStorage().setGameContainer(game);
        Intent intent = new Intent(this, GameController.class);
        startActivity(intent);
    }
}
