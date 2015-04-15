package com.gmail.user0abc.max_one.model.ai.task;/*Created by Sergey on 4/4/2015.*/

import com.gmail.user0abc.max_one.model.actions.Ability;

public interface AiTask {

    public Ability generateAction();
    public int getPriority();

}
