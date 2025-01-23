package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.fullscreenmenus;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.FullScreenMenu;
import com.feiqn.wyrm.models.unitdata.Unit;

public class UnitInfoMenu extends FullScreenMenu {

    final Unit unit;

    public UnitInfoMenu(WYRMGame game, Unit unit) {
        super(game);
        this.unit = unit;

        add();
    }

    protected void add() {
        addActor(backgroundImage);

        // TODO: Realign whole thing, add lots of cool features and breakdowns of stats

        final Label nameLabel = new Label(unit.name, game.assetHandler.menuLabelStyle);
        nameLabel.setFontScale(1.5f);
        nameLabel.setPosition(backgroundImage.getX() + backgroundImage.getWidth() * 0.35f, backgroundImage.getHeight());
        addActor(nameLabel);

        final Label levelLabel = new Label("Level: " + unit.getLevel(), game.assetHandler.menuLabelStyle);
        levelLabel.setFontScale(1.25f);
        levelLabel.setPosition(nameLabel.getX(), nameLabel.getY() - levelLabel.getHeight() - backgroundImage.getHeight() * 0.04f);
        addActor(levelLabel);

        final Label healthLabel = new Label("Health: " + unit.getRollingHP() + "/" + unit.getIron_baseMaxHP(), game.assetHandler.menuLabelStyle);
        healthLabel.setFontScale(1.25f);
        healthLabel.setPosition(backgroundImage.getX() + backgroundImage.getWidth() * 0.05f, levelLabel.getY() - healthLabel.getHeight() - backgroundImage.getHeight() * 0.04f);
        addActor(healthLabel);

        final Label strengthLabel = new Label("Strength: " + unit.getIron_baseStrength(), game.assetHandler.menuLabelStyle);
        strengthLabel.setFontScale(1.25f);
        strengthLabel.setPosition(backgroundImage.getX() + backgroundImage.getWidth() * 0.05f, healthLabel.getY() - strengthLabel.getHeight() - backgroundImage.getHeight() * 0.04f);
        addActor(strengthLabel);

        final Label skillLabel = new Label("Skill: " + unit.getIron_baseDexterity(), game.assetHandler.menuLabelStyle);
        skillLabel.setFontScale(1.25f);
        skillLabel.setPosition(strengthLabel.getX(), strengthLabel.getY() - skillLabel.getHeight() - backgroundImage.getHeight() * 0.04f);
        addActor(skillLabel);

        final Label defenseLabel = new Label("Defense: " + unit.getIron_baseDefense(), game.assetHandler.menuLabelStyle);
        defenseLabel.setFontScale(1.25f);
        defenseLabel.setPosition(skillLabel.getX(), skillLabel.getY() - defenseLabel.getHeight() - backgroundImage.getHeight() * 0.04f);
        addActor(defenseLabel);

        final Label speedLabel = new Label("Speed: " + unit.getIron_baseSpeed(), game.assetHandler.menuLabelStyle);
        speedLabel.setFontScale(1.25f);
        speedLabel.setPosition(defenseLabel.getX(), defenseLabel.getY() - speedLabel.getHeight() - backgroundImage.getHeight() * 0.04f);
        addActor(speedLabel);

        final Label movementLabel = new Label("Movement: " + unit.getIron_baseMobility(), game.assetHandler.menuLabelStyle);
        movementLabel.setFontScale(1.25f);
        movementLabel.setPosition(speedLabel.getX(), speedLabel.getY() - movementLabel.getHeight() - backgroundImage.getHeight() * 0.04f);
        addActor(movementLabel);

        // TODO: classname
    }
}
