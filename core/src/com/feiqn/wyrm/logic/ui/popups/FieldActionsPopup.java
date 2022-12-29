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

    public FieldActionsPopup(WYRMGame game, Unit unit) {
        super(game);
        this.unit = unit;
        AddSmallTargeted(this.unit);
    }

    @Override
    protected void AddSmallTargeted(final Unit unit) {

        addActor(background);

        float width;
        float height;

        final Label waitLabel = new Label("Wait", game.activeBattleScreen.menuLabelStyle);
        waitLabel.setFontScale(1);
        waitLabel.setPosition((unit.getRow() + 1) * 16, (unit.getY() + 1) * 16);
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

        final Array<Unit> enemiesInRange = new Array<>();

        for(Unit enemy : game.activeBattleScreen.getEnemyTeam()) {
            final int distance = game.activeBattleScreen.distanceBetweenTiles(enemy.occupyingTile, unit.occupyingTile);
            if(distance <= unit.getReach()) {
                Gdx.app.log("reach", "" + unit.getReach());
                enemiesInRange.add(enemy);
                enemy.constructAndAddAttackListener(unit);
            }
        }

        if(enemiesInRange.size > 0) {
            final Label attackLabel = new Label("Attack", game.activeBattleScreen.menuLabelStyle);
            attackLabel.setFontScale(1);
            attackLabel.setPosition(waitLabel.getX(), waitLabel.getY() + attackLabel.getHeight() * 1.5f);
            addActor(attackLabel);

            if(attackLabel.getWidth() * 1.25f > width) {
                width = attackLabel.getWidth() * 1.25f;
            }
            height += attackLabel.getHeight() * 2;
        }

        background.setHeight(height);
        background.setWidth(width);


        background.setPosition(waitLabel.getX() - background.getWidth() * 0.1f, waitLabel.getY() - background.getHeight() * 0.2f);
    }
}
