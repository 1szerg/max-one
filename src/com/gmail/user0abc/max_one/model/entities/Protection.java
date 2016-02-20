package com.gmail.user0abc.max_one.model.entities;/*Created by Sergey on 3/7/2015.*/

import com.gmail.user0abc.max_one.model.actions.AttackType;

import java.util.Iterator;
import java.util.Map;

public class Protection {
    protected Map<AttackType, Double> protectionMatrix;

    public Protection() {
    }

    public Protection(Map<AttackType, Double> protectionMatrix) {
        this.protectionMatrix = protectionMatrix;
    }

    public Map<AttackType, Double> getProtectionMatrix() {
        return protectionMatrix;
    }

    public void setProtectionMatrix(Map<AttackType, Double> protectionMatrix) {
        this.protectionMatrix = protectionMatrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Protection{");
        Iterator<AttackType> ati = protectionMatrix.keySet().iterator();
        while(ati.hasNext()){
            AttackType at = ati.next();
            sb.append(at.toString())
                    .append("-")
                    .append(String.valueOf(protectionMatrix.get(at)));
            if(ati.hasNext()) sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }
}
