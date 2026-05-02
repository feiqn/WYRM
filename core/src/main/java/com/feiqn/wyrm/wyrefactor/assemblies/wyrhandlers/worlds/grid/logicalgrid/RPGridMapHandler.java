package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.helpers.Compass;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.props.RPGridProp;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.WyrMapHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class RPGridMapHandler extends WyrMapHandler<RPGridMetaHandler> {

    // refactor of WyrMap

    private final TiledMap tiledMap; // todo: come back and see about maybe making this local later on

    private final GridTile[][] logicalMap; // it's x y now guys i swear

    private final int tilesWide;
    private final int tilesHigh;

    private final RPGridMetaHandler h; // It's fun to just type "h".

    public RPGridMapHandler(RPGridMetaHandler metaHandler, TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        this.h = metaHandler;

        final MapProperties properties = tiledMap.getProperties();

        this.tilesWide = (int)properties.get("width");
        this.tilesHigh = (int)properties.get("height");

        logicalMap = new GridTile[tilesWide][];

        for(int x = 0; x < tilesWide; x++) {
            logicalMap[x] = new GridTile[tilesHigh];
            for(int y = 0; y < tilesHigh; y++) {
                logicalMap[x][y] = new GridTile(metaHandler, GridTile.TileType.PLAINS, x, y);
            }
        }

        setUpTiles();
    }

    public void highlightTiles(Set<GridTile> set) {
        for(GridTile tile : set) {
            tile.highlight();
        }
    }
    public void highlightTiles(Array<GridTile> tiles) {
        for(GridTile tile : tiles) {
            tile.highlight();
        }
    }

    public void clearAllHighlights() {
        for(GridTile tile : getAllTiles()) {
            tile.unhighlight();
        }


        // TODO: call actor handler to clear highlights there too.
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

        for(GridTile[] array : logicalMap) {
            for(GridTile tile : array) {
                TiledMapTileLayer.Cell cell;

                if(fortressLayer != null) {
                    cell = fortressLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(GridTile.TileType.FORTRESS, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(lowWallLayer != null) {
                    cell = lowWallLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(GridTile.TileType.LOW_WALL, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(roadLayer != null) {
                    cell = roadLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(GridTile.TileType.ROAD, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(impassibleLayer != null) {
                    cell = impassibleLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(GridTile.TileType.IMPASSIBLE_WALL, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(forestLayer != null) {
                    cell = forestLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(GridTile.TileType.FOREST, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(roughHillLayer != null) {
                    cell = roughHillLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(GridTile.TileType.ROUGH_HILLS, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(mountainLayer != null) {
                    cell = mountainLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(GridTile.TileType.MOUNTAIN, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(shallowWaterLayer != null) {
                    cell = shallowWaterLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(GridTile.TileType.SHALLOW_WATER, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(deepWaterLayer != null) {
                    cell = deepWaterLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(GridTile.TileType.DEEP_WATER, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(lavaLayer != null) {
                    cell = lavaLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(GridTile.TileType.LAVA, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }

                if(coralReefLayer != null) {
                    cell = coralReefLayer.getCell(tile.getXColumn(), tile.getYRow());
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(GridTile.TileType.CORAL_REEF, tile.getXColumn(), tile.getYRow());
                        continue;
                    }
                }
            }
        }
    }

    public void placeActor(RPGridActor actor, GridTile tile) {
        this.placeActor(actor, tile.getCoordinates());
    }
    public void placeActor(RPGridActor actor, Vector2 coordinates) {
        this.placeActor(actor, (int)coordinates.x, (int)coordinates.y);
    }
    public void placeActor(RPGridActor actor, int x, int y) {

        switch(actor.getActorType()) {
            case UNIT:
                h.map().tileAt(x, y).occupy((RPGridUnit) actor);
                actor.occupyTile(h.map().tileAt(x, y));

                if(h.map().tileAt(x,y).occupier() != actor) {
                    Gdx.app.log("placeActor", "ERROR: invalid occupier at destination tile.");
                }

                // TODO: check area cutscene trigger
                break;

            case PROP:
                h.map().tileAt(x,y).setProp((RPGridProp) actor);
                break;

            default:
                Gdx.app.log("placeActor", "ERROR: invalid ActorType.");
                break;
        }
        actor.setPosByGrid(x, y);

        if(h.map().tileAt(x, y).occupier() != actor) {
            Gdx.app.log("placeActor", "ERROR: wrong actor at tile!.");
        }
        if(actor.getOccupiedTile() != h.map().tileAt(x, y)) {
            Gdx.app.log("placeActor", "ERROR: wrong tile for actor.");
        }
    }

    public Array<GridTile> allAdjacentTo(RPGridActor actor) {
        return this.allAdjacentTo(actor.getOccupiedTile());
    }
    public Array<GridTile> allAdjacentTo(GridTile tile) {
        return this.allAdjacentTo(tile.getCoordinates());
    }
    public Array<GridTile> allAdjacentTo(Vector2 coordinate) {
        return this.allAdjacentTo((int)coordinate.x, (int)coordinate.y);
    }
    public Array<GridTile> allAdjacentTo(int x, int y) {
        return tilesWithinDistanceOf(1, new Vector2(x, y));
    }
    public Array<GridTile> tilesWithinDistanceOf(int distance, RPGridActor actor) {
        return tilesWithinDistanceOf(distance, actor.getOccupiedTile());
    }
    public Array<GridTile> tilesWithinDistanceOf(int distance, GridTile origin) {
        return tilesWithinDistanceOf(distance, origin.getCoordinates());
    }
    public Array<GridTile> tilesWithinDistanceOf(int distance, Vector2 origin) {
        final Array<GridTile> returnValue = new Array<>();
        for(GridTile tile : getAllTiles()) {
            if(distanceBetweenTiles(origin, tile.getCoordinates()) <= distance) returnValue.add(tile);
        }
        return returnValue;
    }
    public Array<GridTile> getAllTiles() {

        // TODO: boolean check to just calculate this once and fill an array to return a ref to

        if(logicalMap[0].length == 0) setUpTiles();
        final Array<GridTile> returnValue = new Array<>();

        for(GridTile[] gridTiles : logicalMap) {
            returnValue.addAll(gridTiles);
//            for(GridTile tile : gridTiles) { // I am high right now.
//                if(!returnValue.contains(tile, true)) returnValue.add(tile);
//            }
        }
//        Gdx.app.log("getAllTiles", "returnValue length: " + returnValue.size);
        return returnValue;
    }
    private void setTileToType(GridTile.TileType type, int x, int y) {
        if(logicalMap[0].length == 0) setUpTiles();
        logicalMap[x][y] = new GridTile(h, type, x, y);
    }
    public Compass directionFromTileToTile(RPGridActor origin, RPGridActor destination) {
        return this.directionFromTileToTile(origin.getOccupiedTile(), destination.getOccupiedTile());
    }
    public Compass directionFromTileToTile(GridTile origin, GridTile destination) {
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
    public int distanceBetweenTiles(@NotNull RPGridActor origin, @NotNull RPGridActor destination) {
        return this.distanceBetweenTiles(origin.getOccupiedTile(), destination.getOccupiedTile());
    }
    public int distanceBetweenTiles(@NotNull GridTile originTile, @NotNull GridTile destinationTile) {
        return this.distanceBetweenTiles(originTile.getCoordinates(), destinationTile.getCoordinates());
    }
    public int distanceBetweenTiles(@NotNull Vector2 origin, @NotNull Vector2 destination) {
        return (int)Math.abs(origin.y - destination.y) + (int)Math.abs(origin.x - destination.x);
    }
    public GridTile westNeighbor (RPGridActor actor) { return this.westNeighbor(actor.getOccupiedTile()); }
    public GridTile westNeighbor (GridTile tile)   { return this.westNeighbor(tile.getXColumn(), tile.getYRow()); }
    public GridTile westNeighbor (int x, int y)    { return(x < 0 ? null : logicalMap[x-1][y]); }
    public GridTile eastNeighbor (RPGridActor actor) { return this.eastNeighbor(actor.getOccupiedTile()); }
    public GridTile eastNeighbor (GridTile tile)   { return this.eastNeighbor(tile.getXColumn(), tile.getYRow()); }
    public GridTile eastNeighbor (int x, int y)    { return(x >= tilesWide ? null : logicalMap[x+1][y]); }
    public GridTile southNeighbor(RPGridActor actor) { return this.southNeighbor(actor.getOccupiedTile()); }
    public GridTile southNeighbor(GridTile tile)   { return this.southNeighbor(tile.getXColumn(), tile.getYRow()); }
    public GridTile southNeighbor(int x, int y)    { return(y < 0 ? null : logicalMap[x][y-1]); }
    public GridTile northNeighbor(RPGridActor actor) { return this.northNeighbor(actor.getOccupiedTile()); }
    public GridTile northNeighbor(GridTile tile)   { return this.northNeighbor(tile.getXColumn(), tile.getYRow()); }
    public GridTile northNeighbor(int x, int y)    { return(y >= tilesHigh ? null : logicalMap[x][y+1]); }
    public GridTile tileAt(int x, int y) { return logicalMap[x][y]; } // TODO: make this call safer, check if in array bounds
    public int tilesWide() { return tilesWide; }
    public int tilesHigh() { return tilesHigh; }
    public TiledMap getTiledMap() { return tiledMap; }
}
