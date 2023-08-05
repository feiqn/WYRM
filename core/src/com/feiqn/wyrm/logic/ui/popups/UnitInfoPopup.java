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

        addLargeRight();
    }

    @Override
    protected void addLargeRight() {
        super.addLargeRight();

        final Label nameLabel = new Label(unit.name, game.activeBattleScreen.menuLabelStyle);
        nameLabel.setFontScale(1.5f);
        nameLabel.setPosition(background.getX() + background.getWidth() * 0.35f, background.getHeight());
        addActor(nameLabel);

        final Label levelLabel = new Label("Level: " + unit.getLevel(), game.activeBattleScreen.menuLabelStyle);
        levelLabel.setFontScale(1.25f);
        levelLabel.setPosition(nameLabel.getX(), nameLabel.getY() - levelLabel.getHeight() - background.getHeight() * 0.04f);
        addActor(levelLabel);

        final Label healthLabel = new Label("Health: " + unit.getCurrentHP() + "/" + unit.getBaseMaxHP(), game.activeBattleScreen.menuLabelStyle);
        healthLabel.setFontScale(1.25f);
        healthLabel.setPosition(background.getX() + background.getWidth() * 0.05f, levelLabel.getY() - healthLabel.getHeight() - background.getHeight() * 0.04f);
        addActor(healthLabel);

        final Label strengthLabel = new Label("Strength: " + unit.getBaseStrength(), game.activeBattleScreen.menuLabelStyle);
        strengthLabel.setFontScale(1.25f);
        strengthLabel.setPosition(background.getX() + background.getWidth() * 0.05f, healthLabel.getY() - strengthLabel.getHeight() - background.getHeight() * 0.04f);
        addActor(strengthLabel);

        final Label skillLabel = new Label("Skill: " + unit.getBaseDexterity(), game.activeBattleScreen.menuLabelStyle);
        skillLabel.setFontScale(1.25f);
        skillLabel.setPosition(strengthLabel.getX(), strengthLabel.getY() - skillLabel.getHeight() - background.getHeight() * 0.04f);
        addActor(skillLabel);

        final Label defenseLabel = new Label("Defense: " + unit.getBaseDefense(), game.activeBattleScreen.menuLabelStyle);
        defenseLabel.setFontScale(1.25f);
        defenseLabel.setPosition(skillLabel.getX(), skillLabel.getY() - defenseLabel.getHeight() - background.getHeight() * 0.04f);
        addActor(defenseLabel);

        final Label speedLabel = new Label("Speed: " + unit.getBaseSpeed(), game.activeBattleScreen.menuLabelStyle);
        speedLabel.setFontScale(1.25f);
        speedLabel.setPosition(defenseLabel.getX(), defenseLabel.getY() - speedLabel.getHeight() - background.getHeight() * 0.04f);
        addActor(speedLabel);

        final Label movementLabel = new Label("Movement: " + unit.getBaseMovementSpeed(), game.activeBattleScreen.menuLabelStyle);
        movementLabel.setFontScale(1.25f);
        movementLabel.setPosition(speedLabel.getX(), speedLabel.getY() - movementLabel.getHeight() - background.getHeight() * 0.04f);
        addActor(movementLabel);

        // TODO: classname
    }
}
