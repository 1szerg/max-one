package com.gmail.user0abc.max_one.model.mapUtils.filters;/* Created by iSzerg on 7/8/2015. */

import com.gmail.user0abc.max_one.model.terrain.TerrainType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileFilterFactory {

    private static final List<TileFilter> terrainFilter = new ArrayList<>();

    public static List<TileFilter> makePassableTerrainFilter() {
        synchronized (terrainFilter) {
            if (terrainFilter.isEmpty()) {
                List<TerrainType> allowedTerrainTypes = new ArrayList<>();
                allowedTerrainTypes.addAll(Arrays.asList(TerrainType.SAND, TerrainType.HILL, TerrainType.GRASS, TerrainType.TREE));
                terrainFilter.add(new TerrainWhiteListFilter(allowedTerrainTypes));
            }
            return terrainFilter;
        }
    }

    ;

}
