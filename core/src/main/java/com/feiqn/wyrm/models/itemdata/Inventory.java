package com.feiqn.wyrm.models.itemdata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.popups.OverfullInventoryPopup;
import com.feiqn.wyrm.models.unitdata.Unit;

public class Inventory {

    private final WYRMGame game;

    private final Unit owner;

    public boolean isFull;

    public Item item1,
                item2,
                item3,
                item4,
                item5;

    public Inventory(WYRMGame game, Unit owner) {
        this.game = game;
        this.owner = owner;
        isFull = false;
        item1 = new Item(game, ItemType.UtilityItem);
        item2 = new Item(game, ItemType.UtilityItem);
        item3 = new Item(game, ItemType.UtilityItem);
        item4 = new Item(game, ItemType.UtilityItem);
        item5 = new Item(game, ItemType.UtilityItem);
    }

    public void addItem(Item newItem) {
        boolean slotAvailable = false;

        final Array<Item> items = items();

        for(Item item : items()) {
            if(!slotAvailable) { // if we already found an available slot, stop checking to avoid creating duplicates.
                if(item.blankItem) {
                    slotAvailable = true;
                    final int index = items.indexOf(item, true);
                    switch(index) {
                        case 0:
                            item1 = newItem;
                            break;
                        case 1:
                            item2 = newItem;
                            break;
                        case 2:
                            item3 = newItem;
                            break;
                        case 3:
                            item4 = newItem;
                            break;
                        case 4:
                            item5 = newItem;
                            break;
                    }
                }
            }
        }

        if(!slotAvailable) {
            Gdx.app.log("Inventory", "too full, need to drop something.");
            game.activeGridScreen.hudStage.addActor(new OverfullInventoryPopup(game, owner, newItem));
            // TODO: lockout other input during menus
        }

    }

    public void removeItem(Item item) {

    }

    public void removeItem(int itemNumber) {

    }

    public Array<Item> items() {
        final Array<Item> items = new Array<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);

        return items;
    }

}
