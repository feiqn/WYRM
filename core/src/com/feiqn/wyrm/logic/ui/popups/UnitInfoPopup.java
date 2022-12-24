package com.feiqn.wyrm.logic.ui.popups;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.ui.PopupMenu;
import com.feiqn.wyrm.models.unitdata.Unit;

public class UnitInfoPopup extends PopupMenu {

    final Unit unit;

    public UnitInfoPopup(WYRMGame game, Unit unit) {
        super(game);
        this.unit = unit;

        AddLargeRight();
    }

    @Override
    protected void AddLargeRight() {
        super.AddLargeRight();

        final Label nameLabel = new Label(unit.name, game.activeBattleScreen.menuLabelStyle);
        nameLabel.setFontScale(1.5f);
        nameLabel.setPosition(background.getX() + background.getWidth() * 0.35f, background.getHeight());
        addActor(nameLabel);

        final Label levelLabel = new Label("Level: " + unit.getLevel(), game.activeBattleScreen.menuLabelStyle);
        levelLabel.setFontScale(1);
        levelLabel.setPosition(nameLabel.getX(), nameLabel.getY() - levelLabel.getHeight() - 10);
        addActor(levelLabel);

    }
}
