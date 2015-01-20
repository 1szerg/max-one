package com.gmail.user0abc.max_one.handlers;

import com.gmail.user0abc.max_one.model.terrain.MapTile;

/**
 * Created by Sergey on 1/20/2015.
 */
public class TileSelectHandler {
    private TileSelectReceiver receiver;

    public TileSelectHandler(TileSelectReceiver receiver) {
        this.receiver = receiver;
    }

    public void onTileSelect(MapTile tile){
        receiver.onTileSelect(tile);
    }
}
