package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.fullscreenmenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.FullScreenMenu;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class UnitInfoMenu extends FullScreenMenu {

    final SimpleUnit unit;

    public UnitInfoMenu(WYRMGame game, SimpleUnit unit) {
        super(game);
        this.unit = unit;

        final Label nameLabel       = new Label(unit.characterName, game.assetHandler.menuLabelStyle);

        final Label healthLabel     = new Label("Health: " + unit.getRollingHP() + "/" + unit.modifiedSimpleHealth(), game.assetHandler.menuLabelStyle);
        final Label strengthLabel   = new Label("Strength: " + unit.modifiedSimpleStrength(), game.assetHandler.menuLabelStyle);
        final Label speedLabel      = new Label("Speed: " + unit.modifiedSimpleSpeed(), game.assetHandler.menuLabelStyle);
        final Label defenseLabel    = new Label("Defense: " + unit.modifiedSimpleDefense(), game.assetHandler.menuLabelStyle);
        final Label magicLabel      = new Label("Magic: " + unit.modifiedSimpleMagic(), game.assetHandler.menuLabelStyle);
        final Label resistanceLabel = new Label("Resistance: " + unit.modifiedSimpleResistance(), game.assetHandler.menuLabelStyle);

        final Label klassLabel      = new Label("Class: " + unit.simpleKlass().name(), game.assetHandler.menuLabelStyle);
        final Label movementLabel   = new Label("Movement Type: " + unit.simpleKlass().movementType().toString(), game.assetHandler.menuLabelStyle);

        final Label weaponLabel     = new Label("Weapon: " + unit.simpleWeapon().name(), game.assetHandler.menuLabelStyle);
        final Label armorLabel      = new Label("Armor: " + unit.simpleArmor().name(), game.assetHandler.menuLabelStyle);
        final Label amuletLabel     = new Label("Amulet: " + unit.simpleAmulet().name(), game.assetHandler.menuLabelStyle);
        final Label ringLabel       = new Label("Ring:" + unit.simpleRing().name(), game.assetHandler.menuLabelStyle);
        final Label braceletLabel   = new Label("Bracelet: " + unit.simpleBracelet().name(), game.assetHandler.menuLabelStyle);

        final Label historyLabel    = new Label("History: ", game.assetHandler.menuLabelStyle);
        final Label bioLabel        = new Label(unit.bio(), game.assetHandler.menuLabelStyle);

        // connection with other units
        // grief

        // fold inventory & equipment into this

        layout.pad(Gdx.graphics.getHeight() * 0.1f);

        final Image backButton = new Image(game.assetHandler.backButtonTexture);
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
               game.activeGridScreen.hud().removeFullscreen();
            }
        });

        layout.add(backButton).padBottom(Gdx.graphics.getHeight() * 0.01f).left();
        layout.row();
        layout.add(nameLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).left() ; layout.add(klassLabel).right();
        layout.row();
        layout.add(healthLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).colspan(2);
        layout.row();
        layout.add(strengthLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).left() ; layout.add(defenseLabel).right();
        layout.row();
        layout.add(magicLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).left() ; layout.add(resistanceLabel).right();
        layout.row();
        layout.add(speedLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).left() ; layout.add(movementLabel).right();
        layout.row();
        layout.add(weaponLabel).colspan(2).padBottom(Gdx.graphics.getHeight() * 0.01f).left();
        layout.row();
        layout.add(armorLabel).colspan(2).padBottom(Gdx.graphics.getHeight() * 0.01f).left();
        layout.row();
        layout.add(amuletLabel).colspan(2).padBottom(Gdx.graphics.getHeight() * 0.01f).left();
        layout.row();
        layout.add(ringLabel).colspan(2).padBottom(Gdx.graphics.getHeight() * 0.01f).left();
        layout.row();
        layout.add(braceletLabel).colspan(2).padBottom(Gdx.graphics.getHeight() * 0.01f).left();
        layout.row();
        layout.add(historyLabel).colspan(2).padBottom(Gdx.graphics.getHeight() * 0.01f).left();
        layout.row();
        layout.add(bioLabel).colspan(2).padBottom(Gdx.graphics.getHeight() * 0.01f).left();

    }

}
