package com.gmail.user0abc.max_one.model.mapUtils.filters;/* Created by iSzerg on 5/4/2015. */

import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.entities.Entity;
import com.gmail.user0abc.max_one.model.terrain.MapTile;

public class VisibleTilesFilter implements TileFilter {

    private Player player;

    public VisibleTilesFilter(Player player) {
        this.player = player;
    }

    @Override
    public boolean matchTile(MapTile tile) {
        for(Entity entity: tile.visibleBy){
            if(entity.getOwner().equals(player)) return true;
        }
        return false;
    }
}
