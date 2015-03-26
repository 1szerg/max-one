package com.gmail.user0abc.max_one.model.actions;/*Created by Sergey on 3/7/2015.*/

public class Attack {

    protected AttackType attackType;
    protected double attackStrength;

    public Attack() {
    }

    public Attack(AttackType attackType, double attackStrength) {
        this.attackType = attackType;
        this.attackStrength = attackStrength;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public double getAttackStrength() {
        return attackStrength;
    }

    public void setAttackStrength(double attackStrength) {
        this.attackStrength = attackStrength;
    }
}
