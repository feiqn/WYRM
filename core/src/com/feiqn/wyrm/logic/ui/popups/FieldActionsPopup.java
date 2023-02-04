package com.feiqn.wyrm.logic.ui.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.ui.PopupMenu;
import com.feiqn.wyrm.models.unitdata.Unit;

public class FieldActionsPopup extends PopupMenu {

    final Unit unit;

    final FieldActionsPopup self = this;

    public FieldActionsPopup(WYRMGame game, Unit unit, float x, float y) {
        super(game);
        this.unit = unit;
        AddSmallTargeted(this.unit);
        Gdx.app.log("x", "" + x);
        Gdx.app.log("y", "" + y);
    }

    @Override
    protected void AddSmallTargeted(final Unit unit) {

        addActor(background);

        float width;
        float height;

        // TODO: CANCEL button to fully reset unit to original position

        // WAIT
        final Label waitLabel = new Label("Wait", game.activeBattleScreen.menuLabelStyle);
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
        final Label inventoryLabel = new Label("Inventory", game.activeBattleScreen.menuLabelStyle);
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
                final InventoryPopup inventoryPopup = new InventoryPopup(game, unit);
                game.activeBattleScreen.uiGroup.addActor(inventoryPopup);

                self.remove(); // needs to be put back by inventory when closed unless action used
            }

        });

        // TODO: unit info label

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
            final Label attackLabel = new Label("Attack", game.activeBattleScreen.menuLabelStyle);
            attackLabel.setFontScale(1);
            attackLabel.setPosition(inventoryLabel.getX(), inventoryLabel.getY() + attackLabel.getHeight() * 1.5f);
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
                    // select enemy from list
                    if(enemiesInRange.size == 1) {
                        game.activeBattleScreen.uiGroup.addActor(new BattlePreviewPopup(game, game.activeBattleScreen.activeUnit, enemiesInRange.get(0)));
                        self.remove();
                    } else {
                        // list/highlight enemies in range and select which one to attack
                    }
                }

            });
        }

        background.setHeight(height);
        background.setWidth(width);


        background.setPosition(waitLabel.getX() - background.getWidth() * 0.1f, waitLabel.getY() - background.getHeight() * 0.2f);
    }
}
