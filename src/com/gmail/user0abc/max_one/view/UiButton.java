package com.gmail.user0abc.max_one.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.Button;
import com.gmail.user0abc.max_one.model.actions.AbilityType;

/**
 * Created by Sergey
 * at 11/12/14 8:59 PM
 */
public class UiButton {
    private Bitmap iconDisplay, actionPlate;
    private float posX, posY;
    private AbilityType abilityType;


    public UiButton(Bitmap iconDisplay, Bitmap actionPlate, float posX, float posY, AbilityType type) {
        this.iconDisplay = iconDisplay;
        this.actionPlate = actionPlate;
        this.posX = posX;
        this.posY = posY;
        abilityType = type;
    }

    public void display(Canvas c, float x, float y) {
        setPosX(x);
        setPosY(y);
        display(c);
    }

    public void display(Canvas c) {
        c.drawBitmap(actionPlate, posX, posY, null);
        c.drawBitmap(iconDisplay, posX, posY, null);
    }

    public AbilityType getAbilityType() {
        return abilityType;
    }

    public boolean isHit(float x, float y) {
        return (x >= posX
                && x <= (posX + actionPlate.getWidth())
                && y >= posY
                && y <= (posY + actionPlate.getHeight())
        );
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UiButton{");
        sb.append("posX=").append(posX);
        sb.append(", posY=").append(posY);
        sb.append(", abilityType=").append(abilityType);
        sb.append('}');
        return sb.toString();
    }
}
