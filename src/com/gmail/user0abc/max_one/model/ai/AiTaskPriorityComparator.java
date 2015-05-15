package com.gmail.user0abc.max_one.model.ai;/* Created by iSzerg on 4/22/2015. */

public class AiTaskPriorityComparator implements java.util.Comparator<AiTask> {
    @Override
    public int compare(AiTask t1, AiTask t2) {
    if(t1.getPriority() > t2.getPriority())return 1;
    if(t1.getPriority() < t2.getPriority())return -1;
    return 0;
    }
}
