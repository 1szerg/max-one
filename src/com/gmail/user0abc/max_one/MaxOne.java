package com.gmail.user0abc.max_one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.util.*;

import java.util.Random;

public class MaxOne extends Activity {
    public static final String GAME_CONTAINER = "GAME_CONTAINER";
    private SeekBar mapSizeSeek;
    private TextView mapSizeDisplay;
    private int mapSize;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Logger.log("MaxOne started");
        mapSize = 15;
        mapSizeDisplay = (TextView) findViewById(R.id.field_size);
        mapSizeSeek = (SeekBar) findViewById(R.id.map_size_seek_bar);
        mapSizeSeek.setProgress(0);
        mapSizeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mapSize = 15 + progress;
                mapSizeDisplay.setText(Integer.toString(mapSize));
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void newGame(View view) {
        int seed = new Random().nextInt(100000);
        Logger.log("Creating game with seed " + seed);
        GameContainer game = new GameContainer();
        game.seed = seed;
        game.players = GameUtils.getSinglePlayer();
        game.map = MapGenerator.getGenerator(RandUtil.getRand(game.seed)).generateTerrain(mapSize, mapSize, game.players);
        GameStorage.getStorage().setGameContainer(game);
        Intent intent = new Intent(this, GameController.class);
        startActivity(intent);
    }
}
