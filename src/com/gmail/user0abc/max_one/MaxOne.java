package com.gmail.user0abc.max_one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.util.*;

import java.io.IOException;
import java.util.Random;

public class MaxOne extends Activity {
    public static final String GAME_CONTAINER = "GAME_CONTAINER";
    private SeekBar mapSizeSeek;
    private TextView mapSizeDisplay;
    private int mapSize;
    private ProgressBar mapGenProgressBar;
    private LinearLayout mapGenProgressFrame;
    private Button startGenBtn;
    private ListView savedGames;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mapSizeDisplay = (TextView) findViewById(R.id.field_size);
        mapGenProgressBar = (ProgressBar) findViewById(R.id.gen_progress_bar);
        startGenBtn = (Button) findViewById(R.id.start_gen_btn);
        mapSizeSeek = (SeekBar) findViewById(R.id.map_size_seek_bar);
        mapGenProgressFrame = (LinearLayout) findViewById(R.id.gen_progress_display_frame);
        savedGames = (ListView) findViewById(R.id.saved_games_list);
        initScreen();
        mapSizeSeek.setProgress(0);
        Logger.log("MaxOne started");
        mapSize = 15;
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

    private void initScreen() {
        startGenBtn.setEnabled(true);
        mapGenProgressFrame.setVisibility(View.INVISIBLE);
        mapGenProgressBar.setMax(100);
        mapGenProgressBar.setProgress(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //File fs = new File("");
            }
        }).start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //saveGame();
        initScreen();
    }

    private void saveGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SaveGameService.saveGame(GameStorage.getStorage().getGame());
                } catch (IOException e) {
                    Logger.log(e.getLocalizedMessage());
                }
            }
        }).start();
    }

    public void newGame(final View view) {
        mapGenProgressFrame.setVisibility(View.VISIBLE);
        startGenBtn.setEnabled(false);
        int seed = new Random().nextInt(100000);
        Logger.log("Creating game with seed " + seed);
        GameContainer game = new GameContainer();
        game.seed = seed;
        game.players = GameUtils.getSinglePlayer();
        GameStorage.getStorage().setGame(game);
        final MapGeneratorProgressDisplay progressDisplay = new MapGeneratorProgressDisplay() {
            @Override
            public void updateDisplay(int progressVolume) {
                mapGenProgressBar.setProgress(progressVolume);
                view.refreshDrawableState();
            }
        };
        final Intent intent = new Intent(this, GameController.class);
        ThreadManager.runThread(new ThreadPayload() {
            @Override
            public void work() {
                GameStorage.getStorage().getGame().map =
                        MapGenerator.getGenerator(
                                RandUtil.getRand(GameStorage.getStorage().getGame().seed))
                                .generateTerrain(mapSize, mapSize, GameStorage.getStorage().getGame().players, progressDisplay);
            }
        }, new ThreadEndListener() {
            @Override
            public void onThreadFinished(ThreadPayload finishedWork) {
                startActivity(intent);
            }
        });
    }

}
