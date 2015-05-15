package com.gmail.user0abc.max_one.model.mapUtils;/* Created by iSzerg on 4/23/2015. */

import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.util.GameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTileSearcher implements TileSearcher {

    private MapTile center;
    private Double radius;

    public RandomTileSearcher(MapTile center, Double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public List<MapTile> searchTiles(MapTile[][] map, List<TileFilter> filters) {
        Random r = new Random();
        List<MapTile> result = new ArrayList<>();
        int iR = (int)Math.floor(radius);
        int iR2 = (int)Math.floor(radius * 2);
        int c = (int) Math.floor(Math.PI * radius * radius);
        while (c > 0){
            c--;
            int x = center.x + r.nextInt(iR2) - iR;
            int y = center.y + r.nextInt(iR2) - iR;
            if( x > -1 && x < map.length && y > -1 && y < map[0].length){
                if(radius.compareTo(GameUtils.distance(center.x, center.y, x, y)) <= 0){
                    boolean accepted = true;
                    for(TileFilter f : filters){
                        if(!f.matchTile(map[x][y])){
                            accepted = false;
                            break;
                        }
                    }
                    if(accepted){
                        result.add(map[x][y]);
                    }
                }
            }
        }
        return result;
    }
}
