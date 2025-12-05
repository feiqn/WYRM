package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.Direction;
import com.feiqn.wyrm.models.mapdata.Path;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;
import org.jetbrains.annotations.NotNull;

public abstract class WyrGrid {

    // refactor of WyrMap

    private final WYRMGame root;

    private final TiledMap tiledMap; // todo: come back and see about maybe making this local later on

    private final GridActorHandler actorHandler;

    private final GridTile[][] logicalMap; // it's x y now guys i swear

    private final int tilesWide;
    private final int tilesHigh;

    /*
    - grid combat handler
    - computer player handler
     */

    public WyrGrid(WYRMGame game, TiledMap tiledMap) {
        this.root = game;
        this.tiledMap = tiledMap;

        this.actorHandler = new GridActorHandler(root);

        final TiledMapTileLayer ground = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        tilesWide = ground.getWidth();
        tilesHigh = ground.getHeight();

        logicalMap = new GridTile[tilesWide][];

        for(int y = 0; y < tilesHigh; y++) {
            logicalMap[y] = new GridTile[tilesWide];
            for(int x = 0; x < tilesWide; x++) {
                logicalMap[x][y] = new GridTile(root, GridTile.TileType.PLAINS, x, y);
            }
        }

        setUpTiles();
        setUpUnits();
    }

    protected abstract void setUpUnits(); // this talks to ActorHandler to add and position actors

    private void setUpTiles() {
        if(tiledMap == null) return;

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


    // TODO:
    //  nearest available neighbor to tile ()
    //  all adjacent tiles, of those available, of those nearest, if any ()
    public Array<GridTile> filterUnavailable(Array<GridTile> tiles, GridActor filterFor) {
        for(GridTile tile : tiles) {
            switch(filterFor.getActorType()) {
                case UNIT:
                    assert filterFor instanceof GridUnit;
                    if(!tile.isTraversableBy((GridUnit) filterFor))
                    break;

                case PROP:
                    break;
            }
        }
        return tiles;
    }
    public Array<GridTile> allAdjacentTo(GridActor actor) {
        return this.allAdjacentTo(actor.occupyingTile());
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
    public Array<GridTile> tilesWithinDistanceOf(int distance, GridActor actor) {
        return tilesWithinDistanceOf(distance, actor.occupyingTile());
    }
    public Array<GridTile> tilesWithinDistanceOf(int distance, GridTile origin) {
        return tilesWithinDistanceOf(distance, origin.getCoordinates());
    }
    public Array<GridTile> tilesWithinDistanceOf(int distance, Vector2 origin) {
        final Array<GridTile> returnValue = new Array<>();
        for(GridTile[] tileArray : logicalMap) {
            for(GridTile tile : tileArray) {
                if(distanceBetweenTiles(origin, tile.getCoordinates()) <= distance) returnValue.add(tile);
            }
        }
        return returnValue;
    }
    public Array<GridTile> getAllTiles() {
        if(logicalMap[0].length == 0) setUpTiles();
        final Array<GridTile> returnValue = new Array<>();
        for(GridTile[] gridTiles : logicalMap) {
            for(GridTile tile : gridTiles) { // I am high right now.
                if(!returnValue.contains(tile, true)) returnValue.add(tile);
            }
        }
        return returnValue;
    }
    private void setTileToType(GridTile.TileType type, int x, int y) {
        if(logicalMap[0].length == 0) setUpTiles();
        logicalMap[x][y] = new GridTile(root, type, x, y);
    }
    public Direction directionFromTileToTile(GridActor origin, GridActor destination) {
        return this.directionFromTileToTile(origin.occupyingTile(), destination.occupyingTile());
    }
    public Direction directionFromTileToTile(GridTile origin, GridTile destination) {
        return this.directionFromTileToTile(origin.getCoordinates(), destination.getCoordinates());
    }
    public Direction directionFromTileToTile(Vector2 origin, Vector2 destination) {
        // Nobody cares about inter-cardinals.
        if(origin.x == destination.x) {
            if(origin.y > destination.y) {
                return Direction.SOUTH;
            } else {
                return Direction.NORTH;
            }
        } else {
            if(origin.x > destination.y) {
                return Direction.WEST;
            } else {
                return Direction.EAST;
            }
        }
    }
    public int distanceBetweenTiles(@NotNull GridActor origin, @NotNull GridActor destination) {
        return this.distanceBetweenTiles(origin.occupyingTile(), destination.occupyingTile());
    }
    public int distanceBetweenTiles(@NotNull GridTile originTile, @NotNull GridTile destinationTile) {
        return this.distanceBetweenTiles(originTile.getCoordinates(), destinationTile.getCoordinates());
    }
    public int distanceBetweenTiles(@NotNull Vector2 origin, @NotNull Vector2 destination) {
        return (int)Math.abs(origin.y - destination.y) + (int)Math.abs(origin.x - destination.x);
    }
    public GridTile westNeighbor (GridActor actor) { return this.westNeighbor(actor.occupyingTile()); }
    public GridTile westNeighbor (GridTile tile)   { return this.westNeighbor(tile.getXColumn(), tile.getYRow()); }
    public GridTile westNeighbor (int x, int y)    { return(x < 0 ? null : logicalMap[x-1][y]); }
    public GridTile eastNeighbor (GridActor actor) { return this.eastNeighbor(actor.occupyingTile()); }
    public GridTile eastNeighbor (GridTile tile)   { return this.eastNeighbor(tile.getXColumn(), tile.getYRow()); }
    public GridTile eastNeighbor (int x, int y)    { return(x >= tilesWide ? null : logicalMap[x+1][y]); }
    public GridTile southNeighbor(GridActor actor) { return this.southNeighbor(actor.occupyingTile()); }
    public GridTile southNeighbor(GridTile tile)   { return this.southNeighbor(tile.getXColumn(), tile.getYRow()); }
    public GridTile southNeighbor(int x, int y)    { return(y < 0 ? null : logicalMap[x][y-1]); }
    public GridTile northNeighbor(GridActor actor) { return this.northNeighbor(actor.occupyingTile()); }
    public GridTile northNeighbor(GridTile tile)   { return this.northNeighbor(tile.getXColumn(), tile.getYRow()); }
    public GridTile northNeighbor(int x, int y)    { return(y >= tilesHigh ? null : logicalMap[x][y+1]); }
    public GridActorHandler getActorHandler() { return actorHandler; }
    public GridTile tileAt(int x, int y) { return logicalMap[x][y]; }
    public int tilesWide() { return tilesWide; }
    public int tilesHigh() { return tilesHigh; }
    public TiledMap getTiledMap() { return tiledMap; }

}
