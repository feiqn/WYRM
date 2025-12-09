package com.feiqn.wyrm.models.mapdata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;

import java.util.HashMap;

public class Path {

    // Indexed from 1 instead of 0.
    // ...Because OP hates you.

    protected final WYRMGame game;

    private final HashMap<Integer, OLD_LogicalTile> steps;

    private boolean seeded;

    public Path(WYRMGame game) {
        this.game = game;
        steps = new HashMap<>();
    }

    public Path(WYRMGame game, OLD_LogicalTile seed) {
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

    private void mirrorSteps(HashMap<Integer, OLD_LogicalTile> map) {
        for(int i = 1; i <= map.size(); i++) {
            if(map.containsKey(i)) {
                steps.put(i, map.get(i));
            }
        }
    }

    public Array<OLD_LogicalTile> retrievePath() {

        final Array<OLD_LogicalTile> returnValue = new Array<>();

        for(int p = 1; p <= steps.size(); p++) {
            if(steps.containsKey(p)) {
                returnValue.add(steps.get(p));
            }
        }

        return returnValue;
    }

    public void shortenPathBy(int lengthToTrim) {
        truncate(steps.size() - lengthToTrim);
    }

    public void truncate(int newLength) {
        final int rot = steps.size() + 1;
        for(int i = newLength + 1; i <= rot; i++) {
            steps.remove(i);
        }
    }

    public void clearSeedTile() {
        if(seeded) {
            steps.remove(1);
            for(int i = 2; i <= steps.size() + 1; i++) {
                if(steps.containsKey(i)) {
                    steps.put(i-1, steps.get(i));
                }
            }
            steps.remove(steps.size());
            seeded = false;
        }
    }

    public void iDoThinkThatIKnowWhatIAmDoingAndSoIFeelQuiteComfortableArbitrarilyAddingThisTileToTheEndOfThisPath(OLD_LogicalTile tile) {
        steps.put(steps.size() + 1, tile);
    }

    public void incorporateNextTile(Direction direction) {

        final OLD_LogicalTile lastTileInPath = steps.get(steps.size());

        switch(direction) {
            case NORTH:
                if(lastTileInPath.getRowY() + 1 < game.activeOLDGridScreen.getLogicalMap().getTilesHigh()) {
                    steps.put(steps.size() + 1, game.activeOLDGridScreen.getLogicalMap().nextTileUpFrom(lastTileInPath));
                }
                break;
            case SOUTH:
                if(lastTileInPath.getRowY() - 1 >= 0) {
                    steps.put(steps.size() + 1, game.activeOLDGridScreen.getLogicalMap().nextTileDownFrom(lastTileInPath));
                }
                break;
            case WEST:
                if(lastTileInPath.getColumnX() - 1 >= 0) {
                    steps.put(steps.size() + 1, game.activeOLDGridScreen.getLogicalMap().nextTileLeftFrom(lastTileInPath));
                }
                break;
            case EAST:
                if(lastTileInPath.getColumnX() + 1 < game.activeOLDGridScreen.getLogicalMap().getTilesWide()) {
                    steps.put(steps.size() + 1, game.activeOLDGridScreen.getLogicalMap().nextTileRightFrom(lastTileInPath));
                }
                break;
        }

    }

    private void seed(OLD_LogicalTile tile) {
        steps.put(1, tile);
        seeded = true;
    }

    //--GETTERS--

    public float cost(OLD_SimpleUnit unitToFindCostFor) {
        float returnValue = 0;

        for(OLD_LogicalTile tile : retrievePath()) {
            returnValue += tile.getMovementCostForMovementType(unitToFindCostFor.getMovementType());
        }

        return returnValue;
    }
    public int size() {
        return retrievePath().size;
    }
    public OLD_LogicalTile lastTile() {
        if(steps.containsKey(steps.size())) {
            return steps.get(steps.size());
        } else if(steps.get(1) != null){
            return steps.get(1);
        } else {
            Gdx.app.log("path", "error");
            return new OLD_LogicalTile(game, -1,-1);
        }
    }
    public boolean contains(OLD_LogicalTile tile) {
        for(OLD_LogicalTile t : retrievePath()) {
            if (t.getCoordinatesXY() == tile.getCoordinatesXY()) {
                return true;
            }
        }
        return false;
    }

}
