package com.gmail.user0abc.max_one.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.view.*;
import com.gmail.user0abc.max_one.GameController;
import com.gmail.user0abc.max_one.R;
import com.gmail.user0abc.max_one.events.GameEvent;
import com.gmail.user0abc.max_one.events.GameEventBus;
import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.actions.AbilityType;
import com.gmail.user0abc.max_one.model.actions.ActionButton;
import com.gmail.user0abc.max_one.model.entities.buildings.BuildingType;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TileFeatureType;
import com.gmail.user0abc.max_one.util.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sergey
 * at 10/31/14 8:06 PM
 */
public class GameField extends SurfaceView {
    private static int MINIMAL_MOVE_THRESHOLD = 2;
    private final SurfaceHolder holder;
    float mapOffsetX = 0, mapOffsetY = 0;
    Integer selectedTileX, selectedTileY;
    private List<MotionEvent> recordedEvents = new ArrayList<>();
    private Bitmap grass, water, worker, selection, tree, coin, apple, tint, camp, warrior, barbarian, ship;
    private Bitmap endTurn, endTurnDisabled, actionPlate, actionMove, actionWait, actionRemove, actionClean, actionAttack,
            actionDelete, actionTown, actionFarm, actionTrade;
    private GameController gameController;
    private List<UiButton> uiButtons = new ArrayList<>();
    private int screenX, screenY;

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
        endTurnDisabled = BitmapFactory.decodeResource(getResources(), R.drawable.end_turn_d);
        actionMove = BitmapFactory.decodeResource(getResources(), R.drawable.walk);
        actionWait = BitmapFactory.decodeResource(getResources(), R.drawable.wait);
        actionRemove = BitmapFactory.decodeResource(getResources(), R.drawable.remove_building);
        actionClean = BitmapFactory.decodeResource(getResources(), R.drawable.clean_terrain);
        actionAttack = BitmapFactory.decodeResource(getResources(), R.drawable.action_attack);
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
        if (recordedEvents.size() > 3) return;
        UiButton button = getPressedButton(event.getX(), event.getY());
        if (button != null) {
            Logger.log("Button clicked " + button.toString());
            gameController.onActionButtonSelect(button.getAbilityType());
            clearCommands();
            redraw();
        } else {
            // then select the currentTile
            int newSelectedTileX = (int) ((event.getX() - mapOffsetX) / grass.getWidth());
            int newSelectedTileY = (int) ((event.getY() - mapOffsetY) / grass.getHeight());
            Logger.log("Selecting tile at "+newSelectedTileX+", "+newSelectedTileY);
            if (newSelectedTileX > -1 && newSelectedTileX < gameController.getMap().length
                    && newSelectedTileY > -1 && newSelectedTileY < gameController.getMap()[0].length) {
                selectedTileY = newSelectedTileY;
                selectedTileX = newSelectedTileX;
                gameController.onTileSelect(gameController.getMap()[selectedTileX][selectedTileY]);
                redraw();
            }
        }
    }

    private UiButton getPressedButton(float x, float y) {
        for (UiButton button : uiButtons) {
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
            if ((Math.abs(endX - startX) + Math.abs(endY - startY)) < MINIMAL_MOVE_THRESHOLD) {
                recognizeSelect(event);
            }
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
        long pTimer = new Date().getTime();
        //canvas.translate(mapOffsetX, mapOffsetY);
        drawMap(canvas);
        drawInfo(canvas);
        drawUnitInfo(canvas);
        drawEndTurn(canvas);
        fixOffset();
        //canvas.save();
        //canvas.restore();
        Logger.log("PERFORMANCE: Redraw time = " + (new Date().getTime() - pTimer));
    }

    private void drawEndTurn(Canvas canvas) {
        float x = canvas.getWidth() - 4 - endTurn.getWidth();
        float y = canvas.getHeight() - 4 - endTurn.getHeight();
        UiButton endTurnButton = new UiButton(endTurn, endTurn, x, y, AbilityType.END_TURN);
        uiButtons.add(endTurnButton);
        canvas.drawBitmap(gameController.isEndTurnEnabled ? endTurn : endTurnDisabled, x, y, null);
    }

    private void drawUnitInfo(Canvas canvas) {
        List<ActionButton> buttons = gameController.getCurrentActionButtons();
        if (buttons != null && buttons.size() > 0) {
            uiButtons = new ArrayList<>(buttons.size());
            for (int i = 0; i < buttons.size(); i++) {
                float x = (float) 4 + i * actionPlate.getWidth();
                float y = (float) canvas.getHeight() - 4 - actionPlate.getHeight();
                UiButton button = new UiButton(
                        getActionImage(buttons.get(i)),
                        getActionPlate(buttons.get(i)),
                        x, y, buttons.get(i).getAbilityType());
                uiButtons.add(button);
                button.display(canvas);
            }
        }
    }


    private Bitmap getActionImage(ActionButton actionButton) {
        switch (actionButton.getAbilityType()) {
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
            case MAKE_WORKER:
                return worker;
            case MAKE_WARRIOR:
                return warrior;
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
        readScreenSize();
        int performanceCounter = 0;
        int tileSize = grass.getWidth();
        for (int posX = 0; posX < gameController.getMap().length; posX++) {
            for (int posY = 0; posY < gameController.getMap()[posX].length; posY++) {
                MapTile tile = gameController.getMap()[posX][posY];
                float x = posX * grass.getWidth() + mapOffsetX;
                float y = posY * grass.getHeight() + mapOffsetY;
                if (x > -tileSize && x < screenX + tileSize && y > -tileSize && y < screenY + tileSize) {
                    // draw tiles
                    performanceCounter++;
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
                    if (tile.building != null) {
                        canvas.drawBitmap(getBuildingImage(tile.building.getBuildingType()), x, y, null);
                        if (tile.building.getOwner() != null) {
                            canvas.drawBitmap(getPlayerFlag(tile.building.getOwner()), x, y, null);
                        }
                    }
                    // draw units

                    drawUnits(canvas, posX, posY, x, y);

                    // draw features
                    if (gameController.getMap()[posX][posY].tileFeature != null) {
                        canvas.drawBitmap(getFeatureImage(gameController.getMap()[posX][posY].tileFeature.featureType), x, y, null);
                    }
                }
            }
        }
        Logger.log("PERFORMANCE: redrawn " + performanceCounter + " tiles");
        if (selectedTileY != null && selectedTileX != null) {
            float x = selectedTileX * grass.getWidth() + mapOffsetX;
            float y = selectedTileY * grass.getHeight() + mapOffsetY;
            canvas.drawBitmap(selection, x, y, null);
        }
    }

    @SuppressLint("NewApi")
    private void readScreenSize() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        try {
            Point size = new Point();
            display.getSize(size);
            screenX = size.x;
            screenY = size.y;
        } catch (Exception e) {
            screenX = display.getWidth();
            screenY = display.getHeight();
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

    private Bitmap getBuildingImage(BuildingType buildingType) {
        switch (buildingType) {
            case TOWN:
                return BitmapFactory.decodeResource(getResources(), R.drawable.town);
            case CAMP:
                return BitmapFactory.decodeResource(getResources(), R.drawable.camp);
            case FARM:
                return BitmapFactory.decodeResource(getResources(), R.drawable.farm);
            case TRADE_POST:
                return BitmapFactory.decodeResource(getResources(), R.drawable.trade);
            default:
                return null;
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

    public Bitmap getActionPlate(ActionButton actionButton) {
        if (actionButton.isActiveAction())
            return BitmapFactory.decodeResource(getResources(), R.drawable.action_plate_active);
        if (actionButton.isAbilityAvailable())
            return BitmapFactory.decodeResource(getResources(), R.drawable.action_plate);
        return BitmapFactory.decodeResource(getResources(), R.drawable.action_plate_disabled);
    }

    public void clearCommands() {
        recordedEvents.clear();
        Logger.log(recordedEvents.toString());
    }
}
