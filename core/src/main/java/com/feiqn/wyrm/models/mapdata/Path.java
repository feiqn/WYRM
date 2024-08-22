package com.feiqn.wyrm.models.mapdata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;

import java.util.HashMap;

public class Path {

    // Indexed from 1 instead of 0.

    protected final WYRMGame game;

    private final HashMap<Integer, LogicalTile> steps;

    public Path(WYRMGame game) {
        this.game = game;
        steps = new HashMap<>();
    }

    public Path(WYRMGame game, LogicalTile seed) {
        this.game = game;
        steps = new HashMap<>();
        seed(seed);
    }

    public Path(Path mirror) {
        this.game = mirror.game;
        steps = new HashMap<>();
        mirrorSteps(mirror.steps);
    }

    private void mirrorSteps(HashMap<Integer, LogicalTile> map) {
        for(int i = 1; i <= map.size(); i++) {
            if(map.containsKey(i)) {
                steps.put(i, map.get(i));
            }
        }
    }

    public Array<LogicalTile> retrievePath() {

        final Array<LogicalTile> returnValue = new Array<>();

        for(int p = 0; p <= steps.size(); p++) {
            if(steps.containsKey(p)) {
                returnValue.add(steps.get(p));
            }
        }

        return returnValue;
    }

    public void incorporateNextTile(Direction direction) {

        final LogicalTile lastTileInPath = steps.get(steps.size());

        switch(direction) {
            case UP:
                if(lastTileInPath.getRow() + 1 < game.activeBattleScreen.logicalMap.getTilesHigh()) {
                    steps.put(steps.size() + 1, game.activeBattleScreen.logicalMap.nextTileNorthFrom(lastTileInPath));
                }
                break;
            case DOWN:
                if(lastTileInPath.getRow() - 1 >= 0) {
                    steps.put(steps.size() + 1, game.activeBattleScreen.logicalMap.nextTileSouthFrom(lastTileInPath));
                }
                break;
            case LEFT:
                if(lastTileInPath.getColumn() - 1 >= 0) {
                    steps.put(steps.size() + 1, game.activeBattleScreen.logicalMap.nextTileWestFrom(lastTileInPath));
                }
                break;
            case RIGHT:
                if(lastTileInPath.getColumn() + 1 < game.activeBattleScreen.logicalMap.getTilesWide()) {
                    steps.put(steps.size() + 1, game.activeBattleScreen.logicalMap.nextTileEastFrom(lastTileInPath));
                }
                break;
        }

//        Gdx.app.log("Path", "new length: " + size());

    }

    private void seed(LogicalTile tile) {
        steps.put(1, tile);
    }

    //--GETTERS--

    public int size() {
        return steps.size();
    }
    public LogicalTile lastTile() {
        return  steps.get(steps.size());
    }
    public boolean contains(LogicalTile tile) {
//        boolean returnValue = false;
        for(LogicalTile t : retrievePath()) {
            if (t.getCoordinates() == tile.getCoordinates()) {
//                returnValue = true;
                return true;
//                break;
            }
        }
        return false;
//        return returnValue;
    }

}
