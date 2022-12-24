package com.feiqn.wyrm.logic.ui.popups;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

        final Label waitLabel = new Label("Wait", game.activeBattleScreen.menuLabelStyle);
        waitLabel.setFontScale(1);
        waitLabel.setPosition((unit.getRow() + 1) * 16, (unit.getY() + 1) * 16);
        addActor(waitLabel);

        waitLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(unit.canMove()) {
                    unit.toggleCanMove();
                }
                waitLabel.remove();
                unit.dimColor();
                game.activeBattleScreen.checkIfAllUnitsHaveMovedAndPhaseShouldChange(game.activeBattleScreen.currentTeam());
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
            }

        });

        background.setHeight(waitLabel.getHeight() * 2);
        background.setWidth(waitLabel.getWidth() * 1.25f);


        background.setPosition(waitLabel.getX() - background.getWidth() * 0.1f, waitLabel.getY() - background.getHeight() * 0.2f);
    }
}
