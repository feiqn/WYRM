package com.feiqn.wyrm.models.mapdata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

import java.util.HashMap;

public class Path {

    // Indexed from 1 instead of 0.

    protected final WYRMGame game;

    private final HashMap<Integer, LogicalTile> steps;

    private boolean seeded;

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
        seeded = mirror.seeded;
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

        for(int p = 1; p <= steps.size(); p++) {
            if(steps.containsKey(p)) {
                returnValue.add(steps.get(p));
            }
        }

        return returnValue;
    }

    public void shortenPathBy(int lengthToTrim) {
        truncate(steps.size() - 1 - lengthToTrim);
    }

    public void truncate(int newLength) {
        for(int i = newLength + 1; i < steps.size(); i++) {
            steps.remove(i);
        }
    }

    public void clearSeedTile() {
        if(seeded) {
            steps.remove(1);
            for(int i = 2; i <= steps.size(); i++) {
                if(steps.containsKey(i)) {
                    steps.put(i-1, steps.get(i));
                }
            }
            seeded = false;
        }
    }

    public void iDoThinkThatIKnowWhatIAmDoingAndSoIFeelQuiteComfortableArbitrarilyAddingThisTileToTheEndOfThisPath(LogicalTile tile) {
        steps.put(steps.size() + 1, tile);
    }

    public void incorporateNextTile(Direction direction) {

        final LogicalTile lastTileInPath = steps.get(steps.size());

        switch(direction) {
            case UP:
                if(lastTileInPath.getRow() + 1 < game.activeGridScreen.getLogicalMap().getTilesHigh()) {
                    steps.put(steps.size() + 1, game.activeGridScreen.getLogicalMap().nextTileNorthFrom(lastTileInPath));
                }
                break;
            case DOWN:
                if(lastTileInPath.getRow() - 1 >= 0) {
                    steps.put(steps.size() + 1, game.activeGridScreen.getLogicalMap().nextTileSouthFrom(lastTileInPath));
                }
                break;
            case LEFT:
                if(lastTileInPath.getColumn() - 1 >= 0) {
                    steps.put(steps.size() + 1, game.activeGridScreen.getLogicalMap().nextTileWestFrom(lastTileInPath));
                }
                break;
            case RIGHT:
                if(lastTileInPath.getColumn() + 1 < game.activeGridScreen.getLogicalMap().getTilesWide()) {
                    steps.put(steps.size() + 1, game.activeGridScreen.getLogicalMap().nextTileEastFrom(lastTileInPath));
                }
                break;
        }

    }

    private void seed(LogicalTile tile) {
        steps.put(1, tile);
        seeded = true;
    }

    //--GETTERS--

    public float cost(SimpleUnit unitToFindCostFor) {
        float returnValue = 0;

        for(LogicalTile tile : retrievePath()) {
            returnValue += tile.getMovementCostForMovementType(unitToFindCostFor.getMovementType());
        }

//        Gdx.app.log("Cost: ", "" + returnValue);
        return returnValue;
    }
    public int size() {
        return retrievePath().size;
    }
    public LogicalTile lastTile() {
//        Gdx.app.log("last tile", "" + steps.size());
        if(steps.containsKey(steps.size())) {
            return steps.get(steps.size());
        } else if(steps.get(1) != null){
            return steps.get(1);
        } else {
            Gdx.app.log("path", "error");
            return new LogicalTile(game, new Vector2());
        }
    }
    public boolean contains(LogicalTile tile) {
        for(LogicalTile t : retrievePath()) {
            if (t.getCoordinates() == tile.getCoordinates()) {
                return true;
            }
        }
        return false;
    }

}
