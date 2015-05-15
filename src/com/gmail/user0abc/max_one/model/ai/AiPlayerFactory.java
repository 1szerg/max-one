package com.gmail.user0abc.max_one.model.ai;/* Created by iSzerg on 4/26/2015. */

import com.gmail.user0abc.max_one.model.Player;

public class AiPlayerFactory {

    public static Player makeAiPlayer(AiLevel level, AiPersonality personality){
        Player ai = new Player(true);
        ai.aiProcessor = new SimpleAi();
        return ai;
    }

}
