package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;

public class ObjectiveEscapeTileOLD extends OLD_LogicalTile {

    public VictoryCondition associatedVictCon;

    protected UnitRoster requiredUnit;

    public ObjectiveEscapeTileOLD(WYRMGame game, float column, float row, UnitRoster req) {
        super(game, column, row);
        requiredUnit = req;
        tileType = LogicalTileType.OBJECTIVE_ESCAPE;
        this.highlightCanSupport();
    }


    public void setObjectiveUnit(UnitRoster unit) {
        requiredUnit = unit;
    }

    public UnitRoster getObjectiveUnit() {
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
