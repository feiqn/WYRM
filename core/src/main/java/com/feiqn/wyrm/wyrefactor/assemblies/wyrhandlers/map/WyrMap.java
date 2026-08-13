package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.WyrTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.MobilityType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.TileType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Utilities.CompassDirection;
import org.jetbrains.annotations.NotNull;

public class WyrMap extends WyrHandler {

    private final TiledMap tiledMap; // todo: come back and see about maybe making this local later on

    private final WyrTile[][] logicalMap; // it's x y now guys i swear

    private final Array<WyrTile> aggregatedTiles = new Array<>();

    private final int tilesWide;
    private final int tilesHigh;

    public WyrMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;

        final MapProperties properties = tiledMap.getProperties();

        this.tilesWide = (int)properties.get("width");
        this.tilesHigh = (int)properties.get("height");

        logicalMap = new WyrTile[tilesWide][];

        for(int x = 0; x < tilesWide; x++) {
            logicalMap[x] = new WyrTile[tilesHigh];
            for(int y = 0; y < tilesHigh; y++) {
                logicalMap[x][y] = new WyrTile(TileType.PLAINS, x, y);
            }
        }


        setUpTiles();

        aggregatedTiles.addAll(getAllTiles());

    }

    public void spotlightPath(GridPath path) {
//        hideAllHighlights();
//        handlers.map().clearAllHighlights();
        for(WyrTile t: path.getTiles()) {
            t.highlight();
        }
    }
    public void clearAllHighlights() {
        for(WyrTile tile : getAllTiles()) {
            tile.unHighlight();
        }
    }
    @Override
    public boolean standardize() {
        for(WyrTile tile : getAllTiles()) {
            tile.standardize();
        }
        return true;
    }

    private void setUpTiles() {
        if(tiledMap == null) return;
        aggregatedTiles.clear();

        // TODO: populate objects from tiledMap

        TiledMapTileLayer roadLayer         = null;
        TiledMapTileLayer impassibleLayer   = null;
        TiledMapTileLayer forestLayer       = null;
        TiledMapTileLayer lowWallLayer      = null;
        TiledMapTileLayer fortressLayer     = null;
        TiledMapTileLayer roughHillLayer    = null;
        TiledMapTileLayer mountainLayer     = null;
        TiledMapTileLayer shallowWaterLayer = null;
        TiledMapTileLayer lavaLayer         = null;
        TiledMapTileLayer coralReefLayer    = null;
        TiledMapTileLayer deepWaterLayer    = null;

        try {
            fortressLayer = (TiledMapTileLayer)tiledMap.getLayers().get("fortress tiles");
        } catch (Exception ignored) {}
        try {
            lowWallLayer = (TiledMapTileLayer)tiledMap.getLayers().get("low walls");
        } catch (Exception ignored) {}
        try {
            roadLayer = (TiledMapTileLayer)tiledMap.getLayers().get("road tiles");
        } catch (Exception ignored) {}
        try {
            impassibleLayer = (TiledMapTileLayer)tiledMap.getLayers().get("impassible walls");
        } catch (Exception ignored) {}
        try {
            forestLayer = (TiledMapTileLayer)tiledMap.getLayers().get("forest tiles");
        } catch (Exception ignored) {}
        try {
            roughHillLayer = (TiledMapTileLayer)tiledMap.getLayers().get("rough hill tiles");
        } catch (Exception ignored) {}
        try {
            mountainLayer = (TiledMapTileLayer)tiledMap.getLayers().get("mountain tiles");
        } catch (Exception ignored) {}
        try {
            shallowWaterLayer = (TiledMapTileLayer)tiledMap.getLayers().get("shallow water tiles");
        } catch (Exception ignored) {}
        try {
            lavaLayer = (TiledMapTileLayer)tiledMap.getLayers().get("lava tiles");
        } catch (Exception ignored) {}
        try {
            coralReefLayer = (TiledMapTileLayer)tiledMap.getLayers().get("coral reef tiles");
        } catch (Exception ignored) {}
        try {
            deepWaterLayer = (TiledMapTileLayer)tiledMap.getLayers().get("deep water tiles");
        } catch (Exception ignored) {}
        // Hi, Majulaar.

        for(WyrTile[] array : logicalMap) {
            for(WyrTile tile : array) {
                TiledMapTileLayer.Cell cell;

                if(fortressLayer != null) {
                    cell = fortressLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(TileType.FORTRESS, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(lowWallLayer != null) {
                    cell = lowWallLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(TileType.LOW_WALL, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(roadLayer != null) {
                    cell = roadLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(TileType.ROAD, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(impassibleLayer != null) {
                    cell = impassibleLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(TileType.IMPASSIBLE_WALL, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(forestLayer != null) {
                    cell = forestLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(TileType.FOREST, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(roughHillLayer != null) {
                    cell = roughHillLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(TileType.ROUGH_HILLS, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(mountainLayer != null) {
                    cell = mountainLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(TileType.MOUNTAIN, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(shallowWaterLayer != null) {
                    cell = shallowWaterLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(TileType.SHALLOW_WATER, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(deepWaterLayer != null) {
                    cell = deepWaterLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(TileType.DEEP_WATER, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(lavaLayer != null) {
                    cell = lavaLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(TileType.LAVA, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(coralReefLayer != null) {
                    cell = coralReefLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(TileType.CORAL_REEF, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }
            }
        }
    }

    public void placeActor(WyrActor actor, WyrTile tile) {
        this.placeActor(actor, tile.getCoordinates());
    }
    public void placeActor(WyrActor actor, Vector2 coordinates) {
        this.placeActor(actor, (int)coordinates.x, (int)coordinates.y);
    }
    public void placeActor(WyrActor actor, int x, int y) {

//        switch(actor.getActorType()) {
//            case ENTITY:
//                handlers.map().tileAt(x, y).occupy((WyrActor.Unit)actor);
//                ((WyrActor.Unit)actor).occupyTile(handlers.map().tileAt(x, y));
//
//                if(handlers.map().tileAt(x,y).occupierUnit() != actor) {
//                    Gdx.app.log("placeActor", "ERROR: wrong occupier at destination.");
//                }
//
//                // TODO: check area cutscene trigger
//                break;
//
//            case PROP:
//                handlers.map().tileAt(x,y).setProp((WyrActor.Prop)actor);
//                ((WyrActor.Prop)actor).placeOnGroundAt(handlers.map().tileAt(x,y));
//
//                if(handlers.map().tileAt(x, y).occupierProp() != actor) {
//                    Gdx.app.log("placeActor", "ERROR: wrong prop at tile!");
//                }
//
//                break;
//
//            default:
//                Gdx.app.log("placeActor", "ERROR: invalid ActorType.");
//                break;
//        }

        handlers.map().tileAt(x,y).placeOnGround(actor);
        actor.placeOnGroundAt(handlers.map().tileAt(x,y));
        actor.setPosByGrid(x, y);

//        if(actor.getOccupiedTile() != handlers.map().tileAt(x, y)) {
//            Gdx.app.log("placeActor", "ERROR: wrong tile for actor.");
//        }
    }

    public Array<WyrTile> allAdjacentTo(WyrActor actor) {
        return this.allAdjacentTo(actor.getOccupiedTile());
    }
    public Array<WyrTile> allAdjacentTo(WyrTile tile) {
        return this.allAdjacentTo(tile.getCoordinates());
    }
    public Array<WyrTile> allAdjacentTo(Vector2 coordinate) {
        return this.allAdjacentTo((int)coordinate.x, (int)coordinate.y);
    }
    public Array<WyrTile> allAdjacentTo(int x, int y) {
//        return tilesWithinDistanceOf(1, new Vector2(x, y));
        final Array<WyrTile> neighbors = new Array<>();
        if(westNeighbor(x, y) != null) neighbors.add(westNeighbor(x, y));
        if(eastNeighbor(x, y) != null) neighbors.add(eastNeighbor(x, y));
        if(southNeighbor(x, y) != null) neighbors.add(southNeighbor(x, y));
        if(northNeighbor(x, y) != null) neighbors.add(northNeighbor(x, y));
        return neighbors;
    }

    public @Null WyrTile nearestAccessibleNeighbor(int nearestToX, int nearestToY, WyrActor forActor) {
        WyrTile bestTile = null;
        for(WyrTile tile : allAdjacentTo(nearestToX, nearestToY)) {
            if(tile.groundIsObstructed(forActor) || tile.groundIsOccupied()) continue;
            if(bestTile == null) bestTile = tile;
            if(distanceBetweenTiles(bestTile.getCoordinates(), forActor.getOccupiedTile().getCoordinates()) > distanceBetweenTiles(tile.getCoordinates(), forActor.getOccupiedTile().getCoordinates())) {
                bestTile = tile;
            }
        }
        return bestTile;
    }

    public Array<WyrTile> tilesWithinDistanceOf(int distance, WyrActor actor) {
        return tilesWithinDistanceOf(distance, actor.getOccupiedTile());
    }

    public Array<WyrTile> tilesWithinDistanceOf(int distance, WyrTile origin) {
        return tilesWithinDistanceOf(distance, origin.getCoordinates());
    }
    public Array<WyrTile> tilesWithinDistanceOf(int distance, Vector2 origin) {
        // TODO: optimise, if x+1 <= bounds, return tile at x+1... etc.
        final Array<WyrTile> returnValue = new Array<>();
        for(WyrTile tile : getAllTiles()) {
            if(distanceBetweenTiles(origin, tile.getCoordinates()) <= distance && tile.getCoordinates() != origin) returnValue.add(tile);
        }
        return returnValue;
    }
    public Array<WyrTile> getLocalTiles(Vector2 origin) {
        return getTilesInSquareRadius(25);
    }
    public Array<WyrTile> getTilesInSquareRadius(int sqRadius) {
        return null;
    }
    public Array<WyrTile> getAllTiles() {
        if(!aggregatedTiles.isEmpty()) return aggregatedTiles;

        if(logicalMap[0].length == 0) setUpTiles();
        final Array<WyrTile> returnValue = new Array<>();

        for(WyrTile[] WyrTiles : logicalMap) {
            returnValue.addAll(WyrTiles);
        }
        return returnValue;
    }
    private void setTileToType(TileType type, int x, int y) {
        if(logicalMap[0].length == 0) setUpTiles();
        logicalMap[x][y] = new WyrTile(type, x, y);
    }
    public CompassDirection directionFromTileToTile(WyrActor origin, WyrActor destination) {
        return this.directionFromTileToTile(origin.getOccupiedTile(), destination.getOccupiedTile());
    }
    public CompassDirection directionFromTileToTile(WyrTile origin, WyrTile destination) {
        return this.directionFromTileToTile(origin.getCoordinates(), destination.getCoordinates());
    }
    public CompassDirection directionFromTileToTile(Vector2 origin, Vector2 destination) {
        // Nobody cares about inter-cardinals
        if(origin == null || destination == null || origin.x == -1 || origin.y == -1 || destination.y == -1 || destination.x == -1) {
            Gdx.app.log("directionFrom...", "null error");
            return CompassDirection.S;
        }
        if(origin == destination) {
            Gdx.app.log("directionFrom...", "they're the same tile");
            return CompassDirection.S;
        }
        if(origin.x == destination.x) {
            if(origin.y == destination.y) {
                Gdx.app.log("directionFrom...", "they're the same tile");
                return CompassDirection.S;
            }
            if(origin.y > destination.y) {
                return CompassDirection.S;
            } else {
                return CompassDirection.N;
            }
        } else {
            if(origin.x > destination.x) {
                return CompassDirection.W;
            } else {
                return CompassDirection.E;
            }
        }
    }
    public int distanceBetweenTiles(@NotNull WyrActor origin, @NotNull WyrActor destination) {
        return this.distanceBetweenTiles(origin.getOccupiedTile(), destination.getOccupiedTile());
    }
    public int distanceBetweenTiles(@NotNull WyrTile originTile, @NotNull WyrTile destinationTile) {
        return this.distanceBetweenTiles(originTile.getCoordinates(), destinationTile.getCoordinates());
    }
    public int distanceBetweenTiles(@NotNull Vector2 origin, @NotNull Vector2 destination) {
        return (int)Math.abs(origin.y - destination.y) + (int)Math.abs(origin.x - destination.x);
    }
    public @Null WyrTile westNeighbor (WyrActor actor) { return this.westNeighbor(actor.getOccupiedTile()); }
    public @Null WyrTile westNeighbor (WyrTile tile)   { return this.westNeighbor(tile.getXColumn(), tile.getYRow()); }
    public @Null WyrTile westNeighbor (int x, int y)    { return(x < 0 ? null : logicalMap[x-1][y]); }
    public @Null WyrTile eastNeighbor (WyrActor actor) { return this.eastNeighbor(actor.getOccupiedTile()); }
    public @Null WyrTile eastNeighbor (WyrTile tile)   { return this.eastNeighbor(tile.getXColumn(), tile.getYRow()); }
    public @Null WyrTile eastNeighbor (int x, int y)    { return(x >= tilesWide ? null : logicalMap[x+1][y]); }
    public @Null WyrTile southNeighbor(WyrActor actor) { return this.southNeighbor(actor.getOccupiedTile()); }
    public @Null WyrTile southNeighbor(WyrTile tile)   { return this.southNeighbor(tile.getXColumn(), tile.getYRow()); }
    public @Null WyrTile southNeighbor(int x, int y)    { return(y < 0 ? null : logicalMap[x][y-1]); }
    public @Null WyrTile northNeighbor(WyrActor actor) { return this.northNeighbor(actor.getOccupiedTile()); }
    public @Null WyrTile northNeighbor(WyrTile tile)   { return this.northNeighbor(tile.getXColumn(), tile.getYRow()); }
    public @Null WyrTile northNeighbor(int x, int y)    { return(y >= tilesHigh ? null : logicalMap[x][y+1]); }
    public @Null WyrTile tileAt(int x, int y) { return logicalMap[x][y]; } // TODO: make this call safer, check if in array bounds
    public int tilesWide() { return tilesWide; }
    public int tilesHigh() { return tilesHigh; }
    public TiledMap getTiledMap() { return tiledMap; }

}
