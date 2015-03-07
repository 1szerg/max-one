package com.gmail.user0abc.max_one.model.actions;/*Created by Sergey on 2/26/2015.*/

public class ActionButton {
    AbilityType abilityType;
    boolean abilityAvailable;
    boolean activeAction;

    public ActionButton(AbilityType abilityType, boolean abilityAvailable, boolean activeAction) {
        this.abilityAvailable = abilityAvailable;
        this.abilityType = abilityType;
        this.activeAction = activeAction;
    }

    public AbilityType getAbilityType() {
        return abilityType;
    }

    public void setAbilityType(AbilityType abilityType) {
        this.abilityType = abilityType;
    }

    public boolean isAbilityAvailable() {
        return abilityAvailable;
    }

    public void setAbilityAvailable(boolean abilityAvailable) {
        this.abilityAvailable = abilityAvailable;
    }

    public boolean isActiveAction() {
        return activeAction;
    }

    public void setActiveAction(boolean activeAction) {
        this.activeAction = activeAction;
    }
}
