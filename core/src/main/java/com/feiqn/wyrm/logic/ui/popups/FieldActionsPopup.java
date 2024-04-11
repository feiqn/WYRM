package com.feiqn.wyrm.logic.ui.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.ui.PopupMenu;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.EscapeOneVictCon;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles.ObjectiveEscapeTile;
import com.feiqn.wyrm.models.mapobjectdata.prefabObjects.Ballista;
import com.feiqn.wyrm.models.unitdata.Unit;

public class FieldActionsPopup extends PopupMenu {

    final Unit unit;

    int storedOriginRow,
        storedOriginColumn;

    final FieldActionsPopup self = this;

    public FieldActionsPopup(WYRMGame game, Unit unit, float x, float y, int originRow, int originColumn) {
        super(game);
        this.unit = unit;
        Gdx.app.log("x", "" + x);
        Gdx.app.log("y", "" + y);
        storedOriginColumn = originColumn;
        storedOriginRow = originRow;
        addSmallTargeted(this.unit);
    }

    @Override
    protected void addSmallTargeted(final Unit unit) {

        addActor(background);

        float width;
        float height;

        /*
         * I should have just added all the labels to an array
         * and then laid them out afterwards; but here we are.
         * Hindsight is 20/20, maybe one day I'll come back and
         * refactor all this to be a little nicer, but not today.
         */

        // TODO: VICTORY / OBJECTIVE

        // TODO: CANCEL button to fully reset unit to original position

        // WAIT
        final Label waitLabel = new Label("Wait", game.assetHandler.menuLabelStyle);
        waitLabel.setFontScale(1);
        waitLabel.setPosition((unit.getRow() + 1), (unit.getY() + 1)); // TODO: this sux
        addActor(waitLabel);

        width = waitLabel.getWidth() * 1.25f;
        height = waitLabel.getHeight() * 2;

        waitLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                if(unit.canMove()) {
                    unit.toggleCanMove();
                }
                self.remove();
                game.activeBattleScreen.checkIfAllUnitsHaveMovedAndPhaseShouldChange(game.activeBattleScreen.currentTeam());
            }

        });

        // INVENTORY
        final Label inventoryLabel = new Label("Inventory", game.assetHandler.menuLabelStyle);
        inventoryLabel.setFontScale(1);
        inventoryLabel.setPosition(waitLabel.getX(), waitLabel.getY() + inventoryLabel.getHeight() * 1.5f);
        addActor(inventoryLabel);

        if(inventoryLabel.getWidth() * 1.25f > width) {
            width = inventoryLabel.getWidth() * 1.25f;
        }
        height += inventoryLabel.getHeight() * 2;

        inventoryLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                final InventoryPopup inventoryPopup = new InventoryPopup(game, unit, storedOriginRow, storedOriginColumn);
                game.activeBattleScreen.uiGroup.addActor(inventoryPopup);

                self.remove(); // needs to be put back by inventory when closed unless action used
            }

        });

        // TODO: unit info label

        // TODO: multiple contextual options for nearby MapObjects (ballista, door, chest, breakable)

        // BALLISTA
        boolean onABallista = false;
        Ballista presentBallista = null;

        for(Ballista ballista : game.activeBattleScreen.ballistaObjects) {
            if(ballista.row == unit.getRow() && ballista.column == unit.getColumn()) {
                onABallista = true;
                presentBallista = ballista;
            }
        }

        if(onABallista) {
            final Label ballistaLabel = new Label("Ballista", game.assetHandler.menuLabelStyle);
            ballistaLabel.setFontScale(1);
            ballistaLabel.setPosition(inventoryLabel.getX(), inventoryLabel.getY() + ballistaLabel.getHeight() * 1.5f);
            addActor(ballistaLabel);

            if(ballistaLabel.getWidth() * 1.25f > width) {
                width = ballistaLabel.getWidth() * 1.25f;
            }
            height += ballistaLabel.getHeight() * 2;

            final Ballista finalPresentBallista = presentBallista;
            ballistaLabel.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    finalPresentBallista.enterUnit(unit);
                    final BallistaActionsPopup bap = new BallistaActionsPopup(game, unit, finalPresentBallista);
                    game.activeBattleScreen.uiGroup.addActor(bap);
                    self.remove();
                }
            });
        }

        // ATTACK
        final Array<Unit> enemiesInRange = new Array<>();

        for(Unit enemy : game.activeBattleScreen.getEnemyTeam()) {
            final int distance = game.activeBattleScreen.distanceBetweenTiles(enemy.occupyingTile, unit.occupyingTile);
            if(distance <= unit.getReach()) {
//                Gdx.app.log("reach", "" + unit.getReach());
                enemiesInRange.add(enemy);
            }
        }

        if(enemiesInRange.size > 0) {
            final Label attackLabel = new Label("Attack", game.assetHandler.menuLabelStyle);
            attackLabel.setFontScale(1);
            if(!onABallista) { // check for contextual menu options to determine where to place this one
                attackLabel.setPosition(inventoryLabel.getX(), inventoryLabel.getY() + attackLabel.getHeight() * 1.5f);
            } // TODO: check for other labels: door, chest, breakable; and set position accordingly
            addActor(attackLabel);

            if(attackLabel.getWidth() * 1.25f > width) {
                width = attackLabel.getWidth() * 1.25f;
            }
            height += attackLabel.getHeight() * 2;

            attackLabel.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    // open attack interface
                    // TODO: select enemy from list
                    if(enemiesInRange.size == 1) {
                        game.activeBattleScreen.uiGroup.addActor(new BattlePreviewPopup(game, game.activeBattleScreen.activeUnit, enemiesInRange.get(0), storedOriginRow, storedOriginColumn));
                        self.remove();
                    } else {
                        // list/highlight enemies in range and select which one to attack
                    }
                }

            });
        }

        // Victory / escape / seize / etc
        if(unit.occupyingTile.tileType == LogicalTileType.OBJECTIVE_ESCAPE) {
            final Label escapeLabel = new Label("Escape", game.assetHandler.menuLabelStyle);
            escapeLabel.setFontScale(1);
            escapeLabel.setColor(Color.GREEN);

            if(escapeLabel.getWidth() * 1.25f > width) {
                width = escapeLabel.getWidth() * 1.25f;
            }

            escapeLabel.setPosition(waitLabel.getX(), waitLabel.getY() + height - escapeLabel.getHeight() * .5f);
            addActor(escapeLabel);

            height += escapeLabel.getHeight() * 2;

            escapeLabel.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    // here
                    if(unit.occupyingTile instanceof ObjectiveEscapeTile) {
                        // First, reassure compiler of type safety.
                        if(((ObjectiveEscapeTile) unit.occupyingTile).requiredUnit == unit.rosterID) {
                            // Check if escaping unit is associated with tile's victory condition. If not, falls to else{}.
                            for(VictoryCondition victcon : game.activeBattleScreen.conditionsHandler.victoryConditions()) {
                                // Iterate through victory conditions to find the relevant one.
                                if(victcon instanceof EscapeOneVictCon) {
                                    // Once again, reassure compiler of type safety.
                                    if(victcon.associatedUnit() == unit.rosterID) {
                                        // Double check we have the correct victory condition selected.
                                        // TODO: escape unit
                                        victcon.satisfy();
                                    }
                                }
                            }
                        } else {
                            // escape unit, no victcon flags
                        }
                    }
                }

            });

        }

        background.setHeight(height);
        background.setWidth(width);

        background.setPosition(waitLabel.getX() - background.getWidth() * 0.1f, waitLabel.getY() - background.getHeight() * 0.2f);
    }
}
