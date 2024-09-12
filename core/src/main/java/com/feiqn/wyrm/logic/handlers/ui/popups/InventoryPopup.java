package com.feiqn.wyrm.logic.handlers.ui.popups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.PopupMenu;
import com.feiqn.wyrm.models.itemdata.Inventory;
import com.feiqn.wyrm.models.unitdata.Unit;

import java.util.Objects;

public class InventoryPopup extends PopupMenu {

    final Unit unit;
    final Inventory inventory;

    int originRow,
        originColumn;

    public InventoryPopup(WYRMGame game, Unit unit, int originRow, int originColumn) {
        super(game);
        this.unit = unit;
        this.inventory = unit.getInventory();

        this.originRow = originRow;
        this.originColumn = originColumn;

        addLargeRight();
    }

    @Override
    protected void addLargeRight() {
        super.addLargeRight();

        final Label item1Label;

        if(!Objects.equals(inventory.item1.name, "")) {
            item1Label = new Label("" + inventory.item1.name, game.assetHandler.menuLabelStyle);
        } else {
            item1Label = new Label("Empty", game.assetHandler.menuLabelStyle);
        }
        item1Label.setFontScale(1.5f);
        item1Label.setPosition(background.getX() + background.getWidth() * 0.15f, background.getHeight() - background.getHeight() * 0.25f);
        addActor(item1Label);


        final Label item2Label;

        if(!Objects.equals(inventory.item2.name, "")) {
            item2Label = new Label("" + inventory.item2.name, game.assetHandler.menuLabelStyle);
        } else {
            item2Label = new Label("Empty", game.assetHandler.menuLabelStyle);
        }
        item2Label.setFontScale(1.5f);
        item2Label.setPosition(item1Label.getX(), item1Label.getY() - item2Label.getHeight() * 3);
        addActor(item2Label);


        final Label item3Label;

        if(!Objects.equals(inventory.item3.name, "")) {
            item3Label = new Label("" + inventory.item3.name, game.assetHandler.menuLabelStyle);
        } else {
            item3Label = new Label("Empty", game.assetHandler.menuLabelStyle);
        }
        item3Label.setFontScale(1.5f);
        item3Label.setPosition(item1Label.getX(), item2Label.getY() - item3Label.getHeight() * 3);
        addActor(item3Label);


        final Label item4Label;

        if(!Objects.equals(inventory.item4.name, "")) {
            item4Label = new Label("" + inventory.item4.name, game.assetHandler.menuLabelStyle);
        } else {
            item4Label = new Label("Empty", game.assetHandler.menuLabelStyle);
        }
        item4Label.setFontScale(1.5f);
        item4Label.setPosition(item1Label.getX(), item3Label.getY() - item4Label.getHeight() * 3);
        addActor(item4Label);


        final Label item5Label;

        if(!Objects.equals(inventory.item5.name, "")) {
            item5Label = new Label("" + inventory.item5.name, game.assetHandler.menuLabelStyle);
        } else {
            item5Label = new Label("Empty", game.assetHandler.menuLabelStyle);
        }
        item5Label.setFontScale(1.5f);
        item5Label.setPosition(item1Label.getX(), item4Label.getY() - item5Label.getHeight() * 3);
        addActor(item5Label);

        final Label backLabel = new Label("BACK", game.assetHandler.menuLabelStyle);
        backLabel.setPosition(background.getX() - backLabel.getWidth() * .2f, background.getY() + background.getHeight() * .9f);
        backLabel.setColor(Color.ROYAL);
        backLabel.setFontScale(1.5f);

        backLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                game.activeBattleScreen.uiGroup.addActor(new FieldActionsPopup(game, unit, x, y, originRow, originColumn));
                self.remove();
            }
        });

        addActor(backLabel);
    }

}
