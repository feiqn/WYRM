package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class ObjectiveEscapeTile extends LogicalTile {

    public VictoryCondition associatedVictCon;

    protected UnitRoster requiredUnit;

    public ObjectiveEscapeTile(WYRMGame game, float column, float row, UnitRoster req) {
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
    public void highlightCanMove(final SimpleUnit movingUnit) {
        setColor(0,1,1,.6f);

        moveListener = (new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
//                Gdx.app.log("tile", "touch up fired");
                game.activeGridScreen.getLogicalMap().moveAlongPath(movingUnit, game.activeGridScreen.getRecursionHandler().shortestPath(movingUnit, self, true, false));
//                Gdx.app.log("tile", "after move along path");

                game.activeGridScreen.removeTileHighlighters();
                game.activeGridScreen.clearAttackableEnemies();
            }

        });

        addListener(moveListener);

        game.activeGridScreen.rootGroup.addActor(this);
    }

    @Override
    public void clearHighlight() {
        setColor(0,1,0,.5f);
        try {
            this.removeListener(moveListener);
        } catch (Exception ignored) {}
    }




}
