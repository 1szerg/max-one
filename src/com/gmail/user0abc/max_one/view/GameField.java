package com.gmail.user0abc.max_one.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.gmail.user0abc.max_one.GameController;
import com.gmail.user0abc.max_one.R;
import com.gmail.user0abc.max_one.events.GameEvent;
import com.gmail.user0abc.max_one.events.GameEventBus;
import com.gmail.user0abc.max_one.exceptions.NotImplementedException;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.units.AbilityType;
import com.gmail.user0abc.max_one.model.terrain.TileFeatureType;
import com.gmail.user0abc.max_one.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey
 * at 10/31/14 8:06 PM
 */
public class GameField extends SurfaceView {
    private static final int REPLACE_COLOR = -1459683108;
    private List<MotionEvent> recordedEvents = new ArrayList<>();
    private final SurfaceHolder holder;
    private Bitmap grass, water, worker, selection, tree, coin, apple, tint, camp, warrior, barbarian, ship;
    private Bitmap endTurn, actionPlate, actionMove, actionWait, actionRemove, actionClean, actionAttack,
            actionDelete, actionTown, actionFarm, actionTrade;
    float mapOffsetX = 0, mapOffsetY = 0;
    Integer selectedTileX, selectedTileY;
    private GameController gameController;
    private List<UiButton> actionButtons = new ArrayList<>();

    public GameField(Context context) {
        super(context);
        gameController = (GameController) getContext();
        holder = getHolder();
        setClickable(true);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                //TODO implement method
                redraw();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                //TODO implement method
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                //TODO implement method
            }
        });
        loadImages();
    }

    private void loadImages() {
        grass = BitmapFactory.decodeResource(getResources(), R.drawable.grass);
        water = BitmapFactory.decodeResource(getResources(), R.drawable.water);
        worker = BitmapFactory.decodeResource(getResources(), R.drawable.worker);
        warrior = BitmapFactory.decodeResource(getResources(), R.drawable.warrior);
        barbarian = BitmapFactory.decodeResource(getResources(), R.drawable.barbarian);
        ship = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
        selection = BitmapFactory.decodeResource(getResources(), R.drawable.selected);
        tree = BitmapFactory.decodeResource(getResources(), R.drawable.tree);
        coin = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        apple = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
        tint = BitmapFactory.decodeResource(getResources(), R.drawable.tint);
        camp = BitmapFactory.decodeResource(getResources(), R.drawable.camp);
        actionPlate = BitmapFactory.decodeResource(getResources(), R.drawable.action_plate);
        endTurn = BitmapFactory.decodeResource(getResources(), R.drawable.end_turn);
        actionMove = BitmapFactory.decodeResource(getResources(), R.drawable.walk);
        actionWait = BitmapFactory.decodeResource(getResources(), R.drawable.wait);
        actionRemove = BitmapFactory.decodeResource(getResources(), R.drawable.remove_building);
        actionClean = BitmapFactory.decodeResource(getResources(), R.drawable.clean_terrain);
        actionAttack = BitmapFactory.decodeResource(getResources(), R.drawable.attack);
        actionDelete = BitmapFactory.decodeResource(getResources(), R.drawable.delete_unit);
        actionTown = BitmapFactory.decodeResource(getResources(), R.drawable.town);
        actionFarm = BitmapFactory.decodeResource(getResources(), R.drawable.farm);
        actionTrade = BitmapFactory.decodeResource(getResources(), R.drawable.trade);
    }

    public void redraw() {
        Canvas canvas = holder.lockCanvas(null);
        draw(canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event == null) {
            return false;
        }
        Logger.log("Event: " + event.toString());


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                recordedEvents = new ArrayList<>();
                recordedEvents.add(event);
                break;
            case MotionEvent.ACTION_UP:
                recordedEvents.add(event);
                recognizeSelect(event);
                break;
            case MotionEvent.ACTION_MOVE:
                recordedEvents.add(event);
                scrollMap(event);
                break;
            default:
                recordedEvents.add(event);
        }
        return true;
    }

    private void recognizeSelect(MotionEvent event) {
        for (int i = 0; i < recordedEvents.size(); i++) {
            Logger.log("recorded events[" + i + "]=" + recordedEvents.get(i).getAction() + " " + recordedEvents.get(i).getX() + ", " + recordedEvents.get(i).getY());
        }
        if (recordedEvents.size() == 2) {
            UiButton button = getPressedButton(event.getX(), event.getY());
            if (button != null) {
                button.setPressed(true);
                gameController.onActionButtonSelect(button.getAbilityType());
                redraw();
            } else {
                // then select the tile
                int newSelectedTileX = (int) ((event.getX() - mapOffsetX) / grass.getWidth());
                int newSelectedTileY = (int) ((event.getY() - mapOffsetY) / grass.getHeight());
                if (newSelectedTileX > -1 && newSelectedTileX < gameController.getMap().length
                        && newSelectedTileY > -1 && newSelectedTileY < gameController.getMap()[0].length) {
                    selectedTileY = newSelectedTileY;
                    selectedTileX = newSelectedTileX;
                    gameController.onTileSelect(gameController.getMap()[selectedTileX][selectedTileY]);
                    redraw();
                }
            }
        }
    }

    private UiButton getPressedButton(float x, float y) {
        for (UiButton button : actionButtons) {
            if (button.isHit(x, y)) return button;
        }
        return null;
    }

    private void scrollMap(MotionEvent event) {
        if (event.getHistorySize() > 0) {
            float endX = event.getX();
            float endY = event.getY();
            float startX = event.getHistoricalX(0);
            float startY = event.getHistoricalY(0);
            GameEventBus.getBus().fire(
                    new GameEvent(
                            GameEventBus.GameEventType.ScrollMap,
                            null)
            );
            Canvas canvas = getHolder().lockCanvas(null);
            mapOffsetX += endX - startX;
            mapOffsetY += endY - startY;
            draw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.translate(mapOffsetX, mapOffsetY);
        drawMap(canvas);
        canvas.restore();
        drawInfo(canvas);
        drawUnitInfo(canvas);
        drawEndTurn(canvas);
        fixOffset();
    }

    private void drawEndTurn(Canvas canvas) {
        float x = canvas.getWidth() - 4 - endTurn.getWidth();
        float y = canvas.getHeight() - 4 - endTurn.getHeight();
        canvas.drawBitmap(endTurn, x, y, null);
    }

    private void drawUnitInfo(Canvas canvas) {
        List<AbilityType> availableActions = null;
        try {
            availableActions = gameController.getAvailableActions();
        } catch (NotImplementedException e) {
            return;
        }
        if (availableActions == null) return;
        for (int i = 0; i < availableActions.size(); i++) {
            float x = (float) 4 + i * actionPlate.getWidth();
            float y = (float) canvas.getHeight() - 4 - coin.getHeight();
            UiButton button = new UiButton(
                    getActionImage(availableActions.get(i)),
                    getActionImage(availableActions.get(i)),
                    getActionImage(availableActions.get(i)),
                    actionPlate,
                    x, y,
                    availableActions.get(i)
            );
            try {
                button.setEnabled(
                        gameController.isActionAvailable(availableActions.get(i),
                                gameController.getMap()[selectedTileX][selectedTileY])
                );
            } catch (NotImplementedException e) {
                return;
            }
            actionButtons.add(button);
            button.display(canvas);
        }
    }

    private Bitmap getActionImage(AbilityType availableAction) {
        switch (availableAction) {
            case MOVE_ACTION:
                return actionMove;
            case WAIT_ACTION:
                return actionWait;
            case ATTACK_TILE:
                return actionAttack;
            case BUILD_FARM:
                return actionFarm;
            case BUILD_POST:
                return actionTrade;
            case BUILD_TOWN:
                return actionTown;
            case CLEAN_TERRAIN:
                return actionClean;
            case DELETE_UNIT:
                return actionDelete;
            case REMOVE_BUILDING:
                return actionRemove;
            default:
                return null;
        }
    }

    public void drawInfo(Canvas canvas) {
        //apples
        for (int i = 0; i < gameController.getApples(); i++) {
            float x = (float) 4 + i * apple.getWidth();
            float y = 4;
            canvas.drawBitmap(tint, x, y, null);
            canvas.drawBitmap(apple, x, y, null);
        }
        //gold
        for (int i = 0; i < gameController.getGold(); i++) {
            float x = (float) canvas.getWidth() - 4 - (i + 1) * coin.getWidth();
            float y = 4;
            canvas.drawBitmap(tint, x, y, null);
            canvas.drawBitmap(coin, x, y, null);
        }
    }

    private void fixOffset() {
        //TODO implement method
//        float maxOffsetY = canvas.getWidth() - getWidth();
//        float maxOffsetX = canvas.getHeight() - getHeight();
//        if(mapOffsetY < 0) maxOffsetY = 0;
//        if(mapOffsetY > maxOffsetY) mapOffsetY = maxOffsetY;
//        if(mapOffsetX < 0) mapOffsetX = 0;
//        if(mapOffsetX > maxOffsetX) mapOffsetX = maxOffsetX;
    }

    private void drawMap(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        for (int posX = 0; posX < gameController.getMap().length; posX++) {
            for (int posY = 0; posY < gameController.getMap()[posX].length; posY++) {
                // draw tiles
                float x = posX * grass.getWidth();
                float y = posY * grass.getHeight();
                switch (gameController.getMap()[posX][posY].terrainType) {
                    case GRASS:
                        canvas.drawBitmap(grass, x, y, null);
                        break;
                    case WATER:
                        canvas.drawBitmap(water, x, y, null);
                        break;
                    case TREE:
                        canvas.drawBitmap(grass, x, y, null);
                        canvas.drawBitmap(tree, x, y, null);
                        break;
                }
                // draw buildings
                drawBuildings(canvas, posX, posY, x, y);
                // draw units
                drawUnits(canvas, posX, posY, x, y);

                // draw features
                if (gameController.getMap()[posX][posY].tileFeature != null) {
                    canvas.drawBitmap(getFeatureImage(gameController.getMap()[posX][posY].tileFeature.featureType), x, y, null);
                }
            }
        }
        if (selectedTileY != null && selectedTileX != null) {
            float x = selectedTileX * grass.getWidth();
            float y = selectedTileY * grass.getHeight();
            canvas.drawBitmap(selection, x, y, null);
        }
    }

    private void drawUnits(Canvas canvas, int posX, int posY, float x, float y) {
        if (gameController.getMap()[posX][posY].unit == null) return;
        switch (gameController.getMap()[posX][posY].unit.getUnitType()) {
            case WORKER:
                canvas.drawBitmap(worker, x, y, null);
                break;
            case WARRIOR:
                canvas.drawBitmap(warrior, x, y, null);
                break;
            case BARBARIAN:
                canvas.drawBitmap(barbarian, x, y, null);
                break;
            case SHIP:
                canvas.drawBitmap(ship, x, y, null);
                break;
            default:
                break;
        }
        if (gameController.getMap()[posX][posY].unit.getOwner() != null) {
            canvas.drawBitmap(getPlayerBanner(gameController.getMap()[posX][posY].unit.getOwner()), x, y, null);
        }
    }

    private Bitmap getPlayerBanner(Player owner) {
        switch (owner.banner) {
            case 1:
                return BitmapFactory.decodeResource(getResources(), R.drawable.banner_1);
            case 2:
                return BitmapFactory.decodeResource(getResources(), R.drawable.banner_2);
            case 3:
                return BitmapFactory.decodeResource(getResources(), R.drawable.banner_3);
            case 4:
                return BitmapFactory.decodeResource(getResources(), R.drawable.banner_4);
            case 5:
                return BitmapFactory.decodeResource(getResources(), R.drawable.banner_5);
            case 6:
                return BitmapFactory.decodeResource(getResources(), R.drawable.banner_6);
            default:
                return BitmapFactory.decodeResource(getResources(), R.drawable.banner_clean);
        }
    }

    private Bitmap getPlayerFlag(Player owner) {
        switch (owner.banner) {
            case 1:
                return BitmapFactory.decodeResource(getResources(), R.drawable.flag_1);
            case 2:
                return BitmapFactory.decodeResource(getResources(), R.drawable.flag_2);
            case 3:
                return BitmapFactory.decodeResource(getResources(), R.drawable.flag_3);
            case 4:
                return BitmapFactory.decodeResource(getResources(), R.drawable.flag_4);
            case 5:
                return BitmapFactory.decodeResource(getResources(), R.drawable.flag_5);
            case 6:
                return BitmapFactory.decodeResource(getResources(), R.drawable.flag_6);
            default:
                return BitmapFactory.decodeResource(getResources(), R.drawable.flag_clean);
        }
    }

    private void drawBuildings(Canvas canvas, int posX, int posY, float x, float y) {
        if (gameController.getMap()[posX][posY].building == null) return;
        switch (gameController.getMap()[posX][posY].building.getBuildingType()) {
            case TOWN:
                canvas.drawBitmap(actionTown, x, y, null);
                break;
            case FARM:
                canvas.drawBitmap(actionFarm, x, y, null);
                break;
            case CAMP:
                canvas.drawBitmap(camp, x, y, null);
                break;
            case TRADE_POST:
                canvas.drawBitmap(actionTrade, x, y, null);
                break;
        }
        if (gameController.getMap()[posX][posY].building.getOwner() != null) {
            canvas.drawBitmap(getPlayerFlag(gameController.getMap()[posX][posY].building.getOwner()), x, y, null);
        }
    }

    private Bitmap getFeatureImage(TileFeatureType featureType) {
        switch (featureType) {
            case FEATURE_POSSIBLE_MOVE:
                return actionMove;
            case FEATURE_POSSIBLE_ATTACK:
                return actionAttack;
            default:
                return null;
        }
    }

}
