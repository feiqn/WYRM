package com.feiqn.wyrm.models.itemdata.iron;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.OverfullInventoryPopup;
import com.feiqn.wyrm.models.unitdata.Unit;

public class iron_Inventory {

    private final WYRMGame game;

    private final Unit owner;

    public boolean isFull;

    public iron_Item ironItem1,
                     ironItem2,
                     ironItem3,
                     ironItem4,
                     ironItem5;

    public iron_Inventory(WYRMGame game, Unit owner) {
        this.game = game;
        this.owner = owner;
        isFull = false;
        ironItem1 = new iron_Item(game, iron_ItemType.UtilityItem);
        ironItem2 = new iron_Item(game, iron_ItemType.UtilityItem);
        ironItem3 = new iron_Item(game, iron_ItemType.UtilityItem);
        ironItem4 = new iron_Item(game, iron_ItemType.UtilityItem);
        ironItem5 = new iron_Item(game, iron_ItemType.UtilityItem);
    }

    public void addItem(iron_Item newIronItem) {
        boolean slotAvailable = false;

        final Array<iron_Item> items = items();

        for(iron_Item ironItem : items()) {
            if(!slotAvailable) { // if we already found an available slot, stop checking to avoid creating duplicates.
                if(ironItem.blankItem) {
                    slotAvailable = true;
                    final int index = items.indexOf(ironItem, true);
                    switch(index) {
                        case 0:
                            ironItem1 = newIronItem;
                            break;
                        case 1:
                            ironItem2 = newIronItem;
                            break;
                        case 2:
                            ironItem3 = newIronItem;
                            break;
                        case 3:
                            ironItem4 = newIronItem;
                            break;
                        case 4:
                            ironItem5 = newIronItem;
                            break;
                    }
                }
            }
        }

        if(!slotAvailable) {
            Gdx.app.log("Inventory", "too full, need to drop something.");
            game.activeGridScreen.hudStage.addActor(new OverfullInventoryPopup(game, owner, newIronItem));
            // TODO: lockout other input during menus
        }

    }

    public void removeItem(iron_Item ironItem) {

    }

    public void removeItem(int itemNumber) {

    }

    public Array<iron_Item> items() {
        final Array<iron_Item> items = new Array<>();
        items.add(ironItem1);
        items.add(ironItem2);
        items.add(ironItem3);
        items.add(ironItem4);
        items.add(ironItem5);

        return items;
    }

}
