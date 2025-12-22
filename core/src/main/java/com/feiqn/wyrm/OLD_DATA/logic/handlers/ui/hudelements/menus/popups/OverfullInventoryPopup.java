package com.feiqn.wyrm.OLD_DATA.logic.handlers.ui.hudelements.menus.popups;

import com.badlogic.gdx.Gdx;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.ui.hudelements.menus.PopupMenu;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.iron.iron_Item;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.OLD_SimpleUnit;

public class OverfullInventoryPopup extends PopupMenu {

    final OLD_SimpleUnit unit;
//    final iron_Inventory ironInventory;

    public OverfullInventoryPopup(WYRMGame game, OLD_SimpleUnit unit, iron_Item newIronItem) {
        super(game);
        this.unit = unit;
//        this.ironInventory = unit.getInventory();

        backgroundImage.setHeight(Gdx.graphics.getHeight() * .85f);
        backgroundImage.setWidth(Gdx.graphics.getWidth() * .8f);

        backgroundImage.setPosition(Gdx.graphics.getWidth() * .15f, Gdx.graphics.getHeight() * .1f);

        backgroundImage.setColor(1,1,1,.95f);

        addActor(backgroundImage);
//
//        final Label item1Label = new Label("" + ironInventory.ironItem1.name, game.assetHandler.menuLabelStyle);
//        item1Label.setFontScale(1.5f);
//        item1Label.setPosition(backgroundImage.getX() + backgroundImage.getWidth() * 0.15f, backgroundImage.getHeight() - backgroundImage.getHeight() * 0.15f);
//        addActor(item1Label);
//
//        final Label item2Label = new Label("" + ironInventory.ironItem2.name, game.assetHandler.menuLabelStyle);
//        item2Label.setFontScale(1.5f);
//        item2Label.setPosition(item1Label.getX(), item1Label.getY() - item2Label.getHeight() * 3);
//        addActor(item2Label);
//
//        final Label item3Label = new Label("" + ironInventory.ironItem3.name, game.assetHandler.menuLabelStyle);
//        item3Label.setFontScale(1.5f);
//        item3Label.setPosition(item1Label.getX(), item2Label.getY() - item3Label.getHeight() * 3);
//        addActor(item3Label);
//
//        final Label item4Label = new Label("" + ironInventory.ironItem4.name, game.assetHandler.menuLabelStyle);
//        item4Label.setFontScale(1.5f);
//        item4Label.setPosition(item1Label.getX(), item3Label.getY() - item4Label.getHeight() * 3);
//        addActor(item4Label);
//
//        final Label item5Label = new Label("" + ironInventory.ironItem5.name, game.assetHandler.menuLabelStyle);
//        item5Label.setFontScale(1.5f);
//        item5Label.setPosition(item1Label.getX(), item4Label.getY() - item5Label.getHeight() * 3);
//        addActor(item5Label);
//
//        final Label newItemLabel = new Label("" + newIronItem.name, game.assetHandler.menuLabelStyle);
//        newItemLabel.setFontScale(2);
//        newItemLabel.setPosition(item1Label.getX(), item5Label.getY() - newItemLabel.getHeight() * 4);
//        newItemLabel.setColor(Color.YELLOW);
//        addActor(newItemLabel);
//
//        // TODO: each label needs a click listener into confirmation screen to drop item, followed by replacing dropped item's index with newItem
    }

}
