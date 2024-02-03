package com.feiqn.wyrm.logic.ui.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.ui.PopupMenu;
import com.feiqn.wyrm.models.itemdata.Inventory;
import com.feiqn.wyrm.models.itemdata.Item;
import com.feiqn.wyrm.models.unitdata.Unit;

public class OverfullInventoryPopup extends PopupMenu {

    final Unit unit;
    final Inventory inventory;

    public OverfullInventoryPopup(WYRMGame game, Unit unit, Item newItem) {
        super(game);
        this.unit = unit;
        this.inventory = unit.getInventory();

        background.setHeight(Gdx.graphics.getHeight() * .85f);
        background.setWidth(Gdx.graphics.getWidth() * .8f);

        background.setPosition(Gdx.graphics.getWidth() * .15f, Gdx.graphics.getHeight() * .1f);

        background.setColor(1,1,1,.95f);

        addActor(background);

        final Label item1Label = new Label("" + inventory.item1.name, game.assetHandler.menuLabelStyle);
        item1Label.setFontScale(1.5f);
        item1Label.setPosition(background.getX() + background.getWidth() * 0.15f, background.getHeight() - background.getHeight() * 0.15f);
        addActor(item1Label);

        final Label item2Label = new Label("" + inventory.item2.name, game.assetHandler.menuLabelStyle);
        item2Label.setFontScale(1.5f);
        item2Label.setPosition(item1Label.getX(), item1Label.getY() - item2Label.getHeight() * 3);
        addActor(item2Label);

        final Label item3Label = new Label("" + inventory.item3.name, game.assetHandler.menuLabelStyle);
        item3Label.setFontScale(1.5f);
        item3Label.setPosition(item1Label.getX(), item2Label.getY() - item3Label.getHeight() * 3);
        addActor(item3Label);

        final Label item4Label = new Label("" + inventory.item4.name, game.assetHandler.menuLabelStyle);
        item4Label.setFontScale(1.5f);
        item4Label.setPosition(item1Label.getX(), item3Label.getY() - item4Label.getHeight() * 3);
        addActor(item4Label);

        final Label item5Label = new Label("" + inventory.item5.name, game.assetHandler.menuLabelStyle);
        item5Label.setFontScale(1.5f);
        item5Label.setPosition(item1Label.getX(), item4Label.getY() - item5Label.getHeight() * 3);
        addActor(item5Label);

        final Label newItemLabel = new Label("" + newItem.name, game.assetHandler.menuLabelStyle);
        newItemLabel.setFontScale(2);
        newItemLabel.setPosition(item1Label.getX(), item5Label.getY() - newItemLabel.getHeight() * 4);
        newItemLabel.setColor(Color.YELLOW);
        addActor(newItemLabel);

        // TODO: each label needs a click listener into confirmation screen to drop item, followed by replacing dropped item's index with newItem
    }

}
