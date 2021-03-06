package com.gmail.user0abc.max_one.util;

import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.entities.units.UnitType;
import com.gmail.user0abc.max_one.model.entities.units.UnitsFactory;
import com.gmail.user0abc.max_one.model.mapUtils.searchers.SpiralTileSearcher;
import com.gmail.user0abc.max_one.model.mapUtils.filters.TileFilterFactory;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;

import java.util.*;

import static com.gmail.user0abc.max_one.util.GameUtils.distance;

/*Created by sergii.ivanov on 10/24/2014.*/
public class MapGenerator {
    private static final int NODE_SIZE = 30;
    private static final double MIN_NODE_DISTANCE = 3.0;
    private static final int MAX_HEIGHT = 100, SEA_LEVEL = 33, PLAIN_LEVEL = 66, HILL_LEVEL = 95;
    private static final double SPAWN_FREE_RADIUS = 0.33, MIN_START_DISTANCE = 5.0;
    private static final double DESERT_HUMIDITY = 33.0;
    private Random rand;
    private MapGeneratorProgressDisplay progressDisplay;

    public MapGenerator(Random randGen) {
        rand = randGen;
    }

    private class Node{
        int x, y;
        double height, humidity;

        public Node(int x, int y, int height, int humidity) {
            this.x = x;
            this.y = y;
            this.height = height;
            this.humidity = humidity;
        }
    }
    private class StartPosition {
        int x, y;
        Player player;

        public StartPosition(int x, int y, Player player) {
            this.x = x;
            this.y = y;
            this.player = player;
        }
    }

    public MapTile[][] generateTerrain(int xSize, int ySize, List<Player> players, MapGeneratorProgressDisplay display) {
        Logger.log("Generating map");
        progressDisplay = display;
        Node[] nodes = generateNodes(xSize, ySize);
        progressDisplay.updateDisplay(1);
        List<StartPosition> starts = generateStarts(players, xSize, ySize);
        secureStartPositions(nodes, starts);
        progressDisplay.updateDisplay(2);
        MapTile[][] map = generateTiles(xSize, ySize, nodes, starts);
        dropRivers((int) Math.ceil(xSize*ySize/50), map);
        progressDisplay.updateDisplay(99);
        placeStartPositions(map, starts);
        return map;
    }

    private void dropRivers(int ceil, MapTile[][] map) {
        for(int i = 0; i < ceil; i++){
            int x = rand.nextInt(map.length);
            int y = rand.nextInt(map[0].length);
            drawRiverFromPosition(map, map[x][y]);
        }
    }

    private void drawRiverFromPosition(MapTile[][] map, MapTile tile) {
        while(!tile.terrainType.equals(TerrainType.WATER)){
            tile.terrainType = TerrainType.WATER;
            tile.humidity = 100;
            addRiverHumidity(map, tile);
            tile = getLowestTileNearby(map, tile);
        }
    }

    private MapTile getLowestTileNearby(MapTile[][] map, MapTile tile) {
        List<MapTile> tilesNear = GameUtils.getTilesNear(map, tile);
        MapTile lowestTile = tile;
        for(MapTile mapTile : tilesNear){
            if(lowestTile.height > mapTile.height){
                lowestTile = mapTile;
            }
        }
        return lowestTile;
    }

    private void addRiverHumidity(MapTile[][] map, MapTile tile) {
        //TODO add humidity to tiles near river
    }

    private MapTile[][] generateTiles(int xSize, final int ySize, final Node[] nodes, List<StartPosition> starts) {
        Logger.log("Generating tiles");
        final MapTile[][] map = new MapTile[xSize][ySize];
        final int[] progress = new int[xSize];
        Arrays.fill(progress, 0);
        for (int x = 0; x < xSize; x++) {
            final int cX = x;
            ThreadManager.runThread(new ThreadPayload() {
                @Override
                public void work() {
                    for (int y = 0; y < ySize; y++) {
                        map[cX][y] = generateTile(cX, y, nodes);
                        progress[cX] = y + 1;
                    }
                }
            });
            progressDisplay.updateDisplay( 10 + (int)(Math.floor(90 * x / xSize)) );
        }
        int totalSize = xSize * ySize;
        while (true){
            int progr = 0;
            for(int p : progress) progr += p;
            progressDisplay.updateDisplay( 10 + (int)(Math.floor(90 * progr / totalSize)) );
            if(progr == totalSize) break;
        }
        return map;
    }

    private void secureStartPositions(Node[] nodes, List<StartPosition> starts) {
        Logger.log("Securing start positions");
        for(StartPosition s: starts){
            Node n = getClosestNode(nodes, s.x, s.y);
            n.height = SEA_LEVEL + (PLAIN_LEVEL - SEA_LEVEL)/2;
            n.humidity = 0;
        }
    }

    private List<StartPosition> generateStarts(List<Player> players, int xSize, int ySize) {
        Logger.log("Generating start positions");
        List<StartPosition> starts = new ArrayList<>(players.size());
        double safeCircle = Math.min(SPAWN_FREE_RADIUS * xSize / 2, SPAWN_FREE_RADIUS * ySize / 2);
        int centerX = (int) Math.floor(xSize/2);
        int centerY = (int) Math.floor(ySize / 2);
        int maxIterations = players.size() * 10000;
        while (starts.size() < players.size()){
            Logger.log("Generating start positions. Players left : " + players.size());
            maxIterations--;
            if(maxIterations < 1){
                starts = new ArrayList<>(players.size());
                maxIterations = players.size() * 10000;
                Logger.log("Failed attempt to set places - restarting");
            }
            StartPosition newPos = new StartPosition(rand.nextInt(xSize), rand.nextInt(ySize), players.get(starts.size()));
            boolean isValidPosition = distance(newPos.x, newPos.y, centerX, centerY) > safeCircle;
            if(isValidPosition){
                for(StartPosition position : starts){
                    if(position != null){
                        if(distance(position.x, position.y, newPos.x, newPos.y) < MIN_START_DISTANCE){
                            isValidPosition = false;
                            break;
                        }
                    }
                }
            }
            if(isValidPosition){
                starts.add(newPos);
            }
        }
        return starts;
    }

    private Node[] generateNodes(int xSize, int ySize) {
        int nodeCount = (int)Math.ceil(xSize * ySize / NODE_SIZE);
        Logger.log("Generating " + nodeCount + " terrain nodes");
        Node[] nodes = new Node[nodeCount];
        for(int i = 0; i < nodeCount; i++){
            while (true){
                int x = rand.nextInt(xSize);
                int y = rand.nextInt(ySize);
                boolean validNode = true;
                for (int j = 0; j < i; j++) {
                    validNode = validNode && distance(x, y, nodes[j].x, nodes[j].y) >= MIN_NODE_DISTANCE;
                }
                if(validNode){
                    int height = rand.nextInt(MAX_HEIGHT);
                    nodes[i] = new Node(x, y, height, getHumidityFromHeight(height));
                    break;
                }
            }
        }
        return nodes;
    }

    private MapTile generateTile(int x, int y, Node[] nodes) {
        MapTile tile = new MapTile();
        tile.exploredBy = new HashSet<>();
        tile.visibleBy = new HashSet<>();
        tile.building = null;
        tile.x = x;
        tile.y = y;
        tile.height = calcTileHeight(tile, nodes);
        tile.humidity = calcTileHumidity(tile, nodes);
        tile.terrainType = calcTerrainType(tile, nodes);
        return tile;
    }

    private TerrainType calcTerrainType(MapTile tile, Node[] nodes) {
        Node node = getClosestNode(nodes, tile.x, tile.y);
        if(node.height <= SEA_LEVEL) return TerrainType.WATER;
        if(node.height <= SEA_LEVEL + 2) return TerrainType.SAND;
        if (node.height <= PLAIN_LEVEL) {
            if (tile.humidity > DESERT_HUMIDITY) return TerrainType.GRASS;
            else return TerrainType.SAND;
        }
        if(tile.height > HILL_LEVEL)return TerrainType.PEAK;
        if(tile.humidity > DESERT_HUMIDITY)return TerrainType.TREE;
        return TerrainType.HILL;
    }

    private double calcTileHumidity(MapTile tile, Node[] nodes) {
        double humidity = 0;
        for(Node n: nodes){
            double d = distance(tile.x, tile.y, n.x, n.y);
            if(Double.compare(d, 0) == 0){
                humidity += n.humidity;
            }else if(Double.compare(d, 10) < 0){
                humidity += n.humidity / d;
            }
        }
        return humidity;
    }

    private double calcTileHeight(MapTile tile, Node[] nodes) {
        if(nodes.length < 2){
            Node nearestNode = getClosestNode(nodes, tile.x, tile.y);
            return nearestNode.height + rand.nextDouble() * MAX_HEIGHT / 20 - MAX_HEIGHT / 40;
        }else{
            Node closest1 = nodes[0];
            Node closest2 = nodes[1];
            if(distance(closest1.x, closest1.y, tile.x, tile.y) > distance(closest2.x, closest2.y, tile.x, tile.y)){
                closest1 = nodes[1];
                closest2 = nodes[0];
            }
            for(int i = 2; i < nodes.length; i++){
                if(distance(nodes[i].x, nodes[i].y, tile.x, tile.y) < distance(closest1.x, closest1.y, tile.x, tile.y)){
                    closest2 = closest1;
                    closest1 = nodes[i];
                }else if(distance(nodes[i].x, nodes[i].y, tile.x, tile.y) < distance(closest2.x, closest2.y, tile.x, tile.y)){
                    closest2 = nodes[i];
                }
            }
            if(closest1.height > closest2.height){
                return closest2.height + rand.nextDouble() * (closest2.height) / 20
                        + distance(closest2.x, closest2.y, tile.x, tile.y) * (closest1.height - closest2.height)
                        / distance(closest1.x, closest1.y, closest2.x, closest2.y);
            }else{
                return closest1.height + rand.nextDouble() * (closest1.height) / 20
                        + distance(closest1.x, closest1.y, tile.x, tile.y) * (closest2.height - closest1.height)
                        / distance(closest1.x, closest1.y, closest2.x, closest2.y);
            }
        }
    }

    private Node getClosestNode(Node[] nodes, int x, int y) {
        Node closest = null;
        for(Node node:nodes){
            if((closest == null) || (distance(closest.x, closest.y, x, y) > distance(node.x, node.y, x, y))){
                closest = node;
            }
        }
        return closest;
    }

    private int getHumidityFromHeight(int height) {
        if(height <= SEA_LEVEL) return 100;
        return 0;
    }

    public void placeStartPositions(MapTile[][] map, List<StartPosition> starts) {
        Logger.log("Placing start units");
        for (StartPosition startPosition : starts) {
            List<MapTile> plausibleTiles = new SpiralTileSearcher(
                    map[startPosition.x][startPosition.y],1)
                    .searchTiles(map, TileFilterFactory.makePassableTerrainFilter()
                    );
            if(!plausibleTiles.isEmpty()){
                startPosition.x = plausibleTiles.get(0).x;
                startPosition.y = plausibleTiles.get(0).y;
            }
            Logger.log("Player " + startPosition.player + " start location (" + startPosition.x + ", " + startPosition.y + ")");
            UnitsFactory.createUnitAtLocation(map[startPosition.x][startPosition.y], startPosition.player, UnitType.WORKER);

        }
    }

    public static MapGenerator getGenerator(Random randGen) {
        return new MapGenerator(randGen);
    }
}
