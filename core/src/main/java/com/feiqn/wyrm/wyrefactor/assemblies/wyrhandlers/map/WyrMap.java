package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.TileType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Utilities.Compass;
import org.jetbrains.annotations.NotNull;

public class WyrMap extends WyrHandler {

    private final TiledMap tiledMap; // todo: come back and see about maybe making this local later on

    private final RPGridTile[][] logicalMap; // it's x y now guys i swear

    private final int tilesWide;
    private final int tilesHigh;

    public WyrMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;

        final MapProperties properties = tiledMap.getProperties();

        this.tilesWide = (int)properties.get("width");
        this.tilesHigh = (int)properties.get("height");

        logicalMap = new RPGridTile[tilesWide][];

        for(int x = 0; x < tilesWide; x++) {
            logicalMap[x] = new RPGridTile[tilesHigh];
            for(int y = 0; y < tilesHigh; y++) {
                logicalMap[x][y] = new RPGridTile(TileType.PLAINS, x, y);
            }
        }

        setUpTiles();
    }

    public void spotlightPath(GridPath path) {
        hideAllHighlights();
        for(RPGridTile t: path.getTiles()) {
            t.highlight();
            t.unhideHighlight();
        }
    }
    public void hideAllHighlights() {
        for(RPGridTile tile : getAllTiles()) {
            tile.hideHighlight();
        }
    }
    public void restoreAllHighlights() {
        for(RPGridTile tile : getAllTiles()) {
            tile.unhideHighlight();
        }
    }
    @Override
    public boolean standardize() {
        for(RPGridTile tile : getAllTiles()) {
            tile.standardize();
        }
        return true;
    }

    private void setUpTiles() {
        if(tiledMap == null) return;

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

        for(RPGridTile[] array : logicalMap) {
            for(RPGridTile tile : array) {
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

    public void placeActor(WyrActor actor, RPGridTile tile) {
        this.placeActor(actor, tile.getCoordinates());
    }
    public void placeActor(WyrActor actor, Vector2 coordinates) {
        this.placeActor(actor, (int)coordinates.x, (int)coordinates.y);
    }
    public void placeActor(WyrActor actor, int x, int y) {

        switch(actor.getActorType()) {
            case ENTITY:
                handlers.map().tileAt(x, y).occupy((WyrActor.Unit)actor);
                ((WyrActor.Unit)actor).occupyTile(handlers.map().tileAt(x, y));

                if(handlers.map().tileAt(x,y).occupier() != actor) {
                    Gdx.app.log("placeActor", "ERROR: invalid occupier at destination tile.");
                }

                // TODO: check area cutscene trigger
                break;

            case PROP:
//                handlers.map().tileAt(x,y).setProp((WyrActor) actor);
                break;

            default:
                Gdx.app.log("placeActor", "ERROR: invalid ActorType.");
                break;
        }
        actor.setPosByGrid(x, y);

        if(handlers.map().tileAt(x, y).occupier() != actor) {
            Gdx.app.log("placeActor", "ERROR: wrong actor at tile!.");
        }
        if(actor.getOccupiedTile() != handlers.map().tileAt(x, y)) {
            Gdx.app.log("placeActor", "ERROR: wrong tile for actor.");
        }
    }

    public Array<RPGridTile> allAdjacentTo(WyrActor actor) {
        return this.allAdjacentTo(actor.getOccupiedTile());
    }
    public Array<RPGridTile> allAdjacentTo(RPGridTile tile) {
        return this.allAdjacentTo(tile.getCoordinates());
    }
    public Array<RPGridTile> allAdjacentTo(Vector2 coordinate) {
        return this.allAdjacentTo((int)coordinate.x, (int)coordinate.y);
    }
    public Array<RPGridTile> allAdjacentTo(int x, int y) {
        return tilesWithinDistanceOf(1, new Vector2(x, y));
    }
    public Array<RPGridTile> tilesWithinDistanceOf(int distance, WyrActor actor) {
        return tilesWithinDistanceOf(distance, actor.getOccupiedTile());
    }
    public Array<RPGridTile> tilesWithinDistanceOf(int distance, RPGridTile origin) {
        return tilesWithinDistanceOf(distance, origin.getCoordinates());
    }
    public Array<RPGridTile> tilesWithinDistanceOf(int distance, Vector2 origin) {
        final Array<RPGridTile> returnValue = new Array<>();
        for(RPGridTile tile : getAllTiles()) {
            if(distanceBetweenTiles(origin, tile.getCoordinates()) <= distance) returnValue.add(tile);
        }
        return returnValue;
    }
    public Array<RPGridTile> getLocalTiles() {
        return getTilesInSquareRadius(25);
    }
    public Array<RPGridTile> getTilesInSquareRadius(int sqRadius) {
        return null;
    }
    public Array<RPGridTile> getAllTiles() {

        // TODO: boolean check to just calculate this once and fill an array to return a ref to

        if(logicalMap[0].length == 0) setUpTiles();
        final Array<RPGridTile> returnValue = new Array<>();

        for(RPGridTile[] RPGridTiles : logicalMap) {
            returnValue.addAll(RPGridTiles);
//            for(GridTile tile : gridTiles) { // I am high right now.
//                if(!returnValue.contains(tile, true)) returnValue.add(tile);
//            }
        }
//        Gdx.app.log("getAllTiles", "returnValue length: " + returnValue.size);
        return returnValue;
    }
    private void setTileToType(TileType type, int x, int y) {
        if(logicalMap[0].length == 0) setUpTiles();
        logicalMap[x][y] = new RPGridTile(type, x, y);
    }
    public Compass directionFromTileToTile(WyrActor origin, WyrActor destination) {
        return this.directionFromTileToTile(origin.getOccupiedTile(), destination.getOccupiedTile());
    }
    public Compass directionFromTileToTile(RPGridTile origin, RPGridTile destination) {
        return this.directionFromTileToTile(origin.getCoordinates(), destination.getCoordinates());
    }
    public Compass directionFromTileToTile(Vector2 origin, Vector2 destination) {
        // Nobody cares about inter-cardinals
        if(origin == null || destination == null || origin.x == -1 || origin.y == -1 || destination.y == -1 || destination.x == -1) {
            Gdx.app.log("directionFrom...", "null error");
            return Compass.S;
        }
        if(origin == destination) {
            Gdx.app.log("directionFrom...", "they're the same tile");
            return Compass.S;
        }
        if(origin.x == destination.x) {
            if(origin.y == destination.y) {
                Gdx.app.log("directionFrom...", "they're the same tile");
                return Compass.S;
            }
            if(origin.y > destination.y) {
                return Compass.S;
            } else {
                return Compass.N;
            }
        } else {
            if(origin.x > destination.x) {
                return Compass.W;
            } else {
                return Compass.E;
            }
        }
    }
    public int distanceBetweenTiles(@NotNull WyrActor origin, @NotNull WyrActor destination) {
        return this.distanceBetweenTiles(origin.getOccupiedTile(), destination.getOccupiedTile());
    }
    public int distanceBetweenTiles(@NotNull RPGridTile originTile, @NotNull RPGridTile destinationTile) {
        return this.distanceBetweenTiles(originTile.getCoordinates(), destinationTile.getCoordinates());
    }
    public int distanceBetweenTiles(@NotNull Vector2 origin, @NotNull Vector2 destination) {
        return (int)Math.abs(origin.y - destination.y) + (int)Math.abs(origin.x - destination.x);
    }
    public RPGridTile westNeighbor (WyrActor actor) { return this.westNeighbor(actor.getOccupiedTile()); }
    public RPGridTile westNeighbor (RPGridTile tile)   { return this.westNeighbor(tile.getXColumn(), tile.getYRow()); }
    public RPGridTile westNeighbor (int x, int y)    { return(x < 0 ? null : logicalMap[x-1][y]); }
    public RPGridTile eastNeighbor (WyrActor actor) { return this.eastNeighbor(actor.getOccupiedTile()); }
    public RPGridTile eastNeighbor (RPGridTile tile)   { return this.eastNeighbor(tile.getXColumn(), tile.getYRow()); }
    public RPGridTile eastNeighbor (int x, int y)    { return(x >= tilesWide ? null : logicalMap[x+1][y]); }
    public RPGridTile southNeighbor(WyrActor actor) { return this.southNeighbor(actor.getOccupiedTile()); }
    public RPGridTile southNeighbor(RPGridTile tile)   { return this.southNeighbor(tile.getXColumn(), tile.getYRow()); }
    public RPGridTile southNeighbor(int x, int y)    { return(y < 0 ? null : logicalMap[x][y-1]); }
    public RPGridTile northNeighbor(WyrActor actor) { return this.northNeighbor(actor.getOccupiedTile()); }
    public RPGridTile northNeighbor(RPGridTile tile)   { return this.northNeighbor(tile.getXColumn(), tile.getYRow()); }
    public RPGridTile northNeighbor(int x, int y)    { return(y >= tilesHigh ? null : logicalMap[x][y+1]); }
    public RPGridTile tileAt(int x, int y) { return logicalMap[x][y]; } // TODO: make this call safer, check if in array bounds
    public int tilesWide() { return tilesWide; }
    public int tilesHigh() { return tilesHigh; }
    public TiledMap getTiledMap() { return tiledMap; }

}
