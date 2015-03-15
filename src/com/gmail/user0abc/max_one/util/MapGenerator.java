package com.gmail.user0abc.max_one.util;

import com.gmail.user0abc.max_one.model.Player;
import com.gmail.user0abc.max_one.model.entities.units.UnitType;
import com.gmail.user0abc.max_one.model.entities.units.UnitsFactory;
import com.gmail.user0abc.max_one.model.terrain.MapTile;
import com.gmail.user0abc.max_one.model.terrain.TerrainType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*Created by sergii.ivanov on 10/24/2014.*/
public class MapGenerator {
    private static final int NODE_SIZE = 10;
    private static final double MIN_NODE_DISTANCE = 3.0;
    private static final int MAX_HEIGHT = 100, SEA_LEVEL = 33, PLAIN_LEVEL = 66, HILL_LEVEL = 90;
    private static final double SPAWN_FREE_RADIUS = 0.33, MIN_START_DISTANCE = 5.0;
    private Random rand;

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

    public MapTile[][] generateTerrain(int xSize, int ySize, List<Player> players) {
        Logger.log("Generating map");
        Node[] nodes = generateNodes(xSize, ySize);
        List<StartPosition> starts = generateStarts(players, xSize, ySize);
        secureStartPositions(nodes, starts);
        MapTile[][] map = generateTiles(xSize, ySize, nodes, starts);
        placeStartPositions(map, starts);
        return map;
    }

    private MapTile[][] generateTiles(int xSize, int ySize, Node[] nodes, List<StartPosition> starts) {
        Logger.log("Generating tiles");
        MapTile[][] map = new MapTile[xSize][ySize];
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                map[x][y] = generateTile(x, y, nodes);
            }
        }
        return map;
    }

    private void secureStartPositions(Node[] nodes, List<StartPosition> starts) {
        for(StartPosition s: starts){
            Node n = getClosestNode(nodes, s.x, s.y);
            n.height = SEA_LEVEL + (SEA_LEVEL - PLAIN_LEVEL)/2;
            n.humidity = 0;
        }
    }

    private List<StartPosition> generateStarts(List<Player> players, int xSize, int ySize) {
        Logger.log("Generating start positions");
        List<StartPosition> starts = new ArrayList<>(players.size());
        double safeCircle = Math.min(SPAWN_FREE_RADIUS * xSize / 2, SPAWN_FREE_RADIUS * ySize / 2);
        int centerX = (int) Math.floor(xSize/2);
        int centerY = (int) Math.floor(ySize/2);
        int maxIterations = players.size() * 10000;
        while (starts.size() < players.size()){
            maxIterations--;
            if(maxIterations < 1){
                starts = new ArrayList<>(players.size());
                maxIterations = players.size() * 10000;
                Logger.log("Failed attempt to set places - restarting");
            }
            StartPosition newPos = new StartPosition(rand.nextInt(xSize), rand.nextInt(ySize), players.get(starts.size()));
            boolean isValidPosition = GameUtils.distance(newPos.x, newPos.y, centerX, centerY) > safeCircle;
            if(isValidPosition){
                for(StartPosition position : starts){
                    if(position != null){
                        if(GameUtils.distance(position.x, position.y, newPos.x, newPos.y) < MIN_START_DISTANCE){
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
        Logger.log("Generating terrain nodes");
        int nodeCount = (int)Math.ceil(xSize * ySize / NODE_SIZE);
        Node[] nodes = new Node[nodeCount];
        for(int i = 0; i < nodeCount; i++){
            while (true){
                int x = rand.nextInt(xSize);
                int y = rand.nextInt(ySize);
                boolean validNode = true;
                for (int j = 0; j < i; j++) {
                    validNode = validNode && GameUtils.distance(x, y, nodes[j].x, nodes[j].y) >= MIN_NODE_DISTANCE;
                }
                if(validNode){
                    int height = rand.nextInt(MAX_HEIGHT);
                    nodes[i] = new Node(x, y, height, getHumidityFromHeight(height));
                    break;
                }
            }
        }
        return new Node[0];
    }

    private MapTile generateTile(int x, int y, Node[] nodes) {
        MapTile tile = new MapTile();
        tile.explored = false;
        tile.building = null;
        tile.x = x;
        tile.y = y;
        tile.height = calcTileHeight(tile, nodes);
        tile.humidity = calcTileHumidity(tile, nodes);
        tile.terrainType = (x * y * rand.nextInt(100)) % 3 == 1 ? TerrainType.WATER : TerrainType.GRASS;
        if (tile.terrainType.equals(TerrainType.GRASS)) {
            if (rand.nextInt(100) < 10) {
                tile.terrainType = TerrainType.TREE;
            }
        }
        return tile;
    }

    private double calcTileHumidity(MapTile tile, Node[] nodes) {
        double humidity = 0;
        for(Node n: nodes){
            humidity += n.humidity / GameUtils.distance(tile.x, tile.y, n.x, n.y);
        }
        return humidity;
    }

    private double calcTileHeight(MapTile tile, Node[] nodes) {
        if(nodes.length < 3){
            Node nearestNode = getClosestNode(nodes, tile.x, tile.y);
            return nearestNode.height + rand.nextDouble() * MAX_HEIGHT / 20 - MAX_HEIGHT / 40;
        }else{
            List<Node> threeNodes = getThreeClosestNodes(nodes, tile);

            //TODO get plane equation from 3 points
            //TODO get tile height from plane equation
        }
        return 0;
    }

    private List<Node> getThreeClosestNodes(Node[] nodes, MapTile tile) {
        List<Node> closestThree = new ArrayList<>(3);
        for(Node node: nodes){

        }
        return null;
    }

    private Node getClosestNode(Node[] nodes, int x, int y) {
        Node closest = null;
        for(Node node:nodes){
            if((closest == null) || (GameUtils.distance(closest.x, closest.y, x, y) > GameUtils.distance(node.x, node.y, x, y))){
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
            Logger.log("Player " + startPosition.player + " start location (" + startPosition.x + ", " + startPosition.y + ")");
            UnitsFactory.createUnitAtLocation(map[startPosition.x][startPosition.y], startPosition.player, UnitType.WORKER);
        }
    }

    public static MapGenerator getGenerator(Random randGen) {
        return new MapGenerator(randGen);
    }
}
