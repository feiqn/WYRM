package com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.OLD_DATA.OLD_UnitIDRoster;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.OLD_SimpleUnit;

public class ObjectiveEscapeTileOLD extends OLD_LogicalTile {

    public VictoryCondition associatedVictCon;

    protected OLD_UnitIDRoster requiredUnit;

    public ObjectiveEscapeTileOLD(WYRMGame game, float column, float row, OLD_UnitIDRoster req) {
        super(game, column, row);
        requiredUnit = req;
        tileType = LogicalTileType.OBJECTIVE_ESCAPE;
        this.highlightCanSupport();
    }


    public void setObjectiveUnit(OLD_UnitIDRoster unit) {
        requiredUnit = unit;
    }

    public OLD_UnitIDRoster getObjectiveUnit() {
        return requiredUnit;
    }

    @Override
    public void highlightCanMove(final OLD_SimpleUnit movingUnit) {
        setColor(0,1,1,.6f);

        moveListener = (new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
//                Gdx.app.log("tile", "touch up fired");
                game.activeOLDGridScreen.getLogicalMap().moveAlongPath(movingUnit, game.activeOLDGridScreen.getRecursionHandler().shortestPath(movingUnit, self, true, false));
//                Gdx.app.log("tile", "after move along path");

                game.activeOLDGridScreen.removeTileHighlighters();
                game.activeOLDGridScreen.clearAttackableEnemies();
            }

        });

        addListener(moveListener);

        game.activeOLDGridScreen.rootGroup.addActor(this);
    }

    @Override
    public void clearHighlight() {
        setColor(0,1,0,.5f);
        try {
            this.removeListener(moveListener);
        } catch (Exception ignored) {}
    }




}
