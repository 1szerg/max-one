package com.gmail.user0abc.max_one.model.mapUtils.filters;/* Created by iSzerg on 5/14/2015. */

import com.gmail.user0abc.max_one.model.terrain.MapTile;

import java.util.Arrays;
import java.util.List;

public class TileFilterHelper {

    public static boolean matchTile(MapTile tile, List<TileFilter> filters){
        if(filters == null || filters.size() < 1)return true;
        boolean match = true;
        for(TileFilter filter : filters){
            match = match && filter.matchTile(tile);
        }
        return match;
    }

    public static boolean matchTile(MapTile tile, TileFilter[] filters){
        if(filters == null || filters.length < 1)return true;
        return matchTile(tile, Arrays.asList(filters));
    }

}
