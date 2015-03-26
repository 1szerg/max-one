package com.gmail.user0abc.max_one.model.entities;/*Created by Sergey on 3/11/2015.*/

import com.gmail.user0abc.max_one.model.actions.AttackType;

import java.util.HashMap;

public class ProtectionFactory {

    public static Protection makeProtection(Double otherProtection, Double bowProtection, Double swordProtection){
        Protection p = new Protection();
        p.setProtectionMatrix(new HashMap<AttackType, Double>());
        p.getProtectionMatrix().put(AttackType.BOW, bowProtection);
        p.getProtectionMatrix().put(AttackType.SWORD, swordProtection);
        p.getProtectionMatrix().put(AttackType.OTHER, otherProtection);
        return p;
    }

}
