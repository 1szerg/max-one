package com.gmail.user0abc.max_one.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.gmail.user0abc.max_one.R;
import com.gmail.user0abc.max_one.model.actions.AbilityType;

/**
 * Created by Sergey
 * at 11/12/14 8:59 PM
 */
public class UiButton {
    private Bitmap iconDisplay, iconPressed, iconDisabled, actionPlate;
    private boolean enabled = true, pressed = false;
    private float posX, posY;
    private AbilityType abilityType;


    public UiButton(Bitmap iconDisplay, Bitmap iconPressed, Bitmap iconDisabled, Bitmap actionPlate, float posX, float posY, AbilityType type) {
        this.actionPlate = actionPlate;
        this.iconDisplay = iconDisplay;
        this.iconPressed = iconPressed;
        this.iconDisabled = iconDisabled;
        this.posX = posX;
        this.posY = posY;
        abilityType = type;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void display(Canvas c, float x, float y){
        setPosX(x);
        setPosY(y);
        c.drawBitmap(actionPlate, posX, posY, null );
        c.drawBitmap(getBitmap(), posX, posY, null );
    }

    public void display(Canvas c){
        c.drawBitmap(actionPlate, posX, posY, null );
        c.drawBitmap(getBitmap(), posX, posY, null );
    }

    public AbilityType getAbilityType() {
        return abilityType;
    }

    public void setAbilityType(AbilityType abilityType) {
        this.abilityType = abilityType;
    }

    public void setEnabled(boolean newState){
        enabled = newState;
    }

    public void setIcons(Bitmap display, Bitmap pressed, Bitmap disabled){
        iconDisplay = display;
        iconPressed = pressed;
        iconDisabled = disabled;
    }

    private Bitmap getBitmap() {
        if(enabled){
            if(pressed){
                return iconPressed;
            }else{
                return iconDisplay;
            }
        }else{
            return iconDisabled;
        }
    }

    public boolean isHit(float x, float y){
        boolean isHit = ( x >= posX
                        && x <= (posX + actionPlate.getWidth())
                        && y >= posY
                        && y <= (posY+actionPlate.getHeight())
        );
        return isHit;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}
