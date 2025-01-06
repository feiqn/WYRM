package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.PopupMenu;
import com.feiqn.wyrm.models.itemdata.iron.iron_Inventory;
import com.feiqn.wyrm.models.unitdata.Unit;

import java.util.Objects;

public class InventoryPopup extends PopupMenu {

    final Unit unit;
    final iron_Inventory ironInventory;

    int originRow,
        originColumn;

    public InventoryPopup(WYRMGame game, Unit unit, int originRow, int originColumn) {
        super(game);
        this.unit = unit;
        this.ironInventory = unit.getInventory();

        this.originRow = originRow;
        this.originColumn = originColumn;

        addLargeRight();
    }

    protected void addLargeRight() {

        addActor(background);

        final Array<Label> labels = new Array<>();

        // 1
        final Label item1Label;

        if(!Objects.equals(ironInventory.ironItem1.name, "")) {
            item1Label = new Label("" + ironInventory.ironItem1.name, game.assetHandler.menuLabelStyle);
        } else {
            item1Label = new Label("Empty", game.assetHandler.menuLabelStyle);
        }
        item1Label.setFontScale(1.5f);
//        item1Label.setPosition(background.getX() + background.getWidth() * 0.15f, background.getHeight() - background.getHeight() * 0.25f);
//        addActor(item1Label);
        labels.add(item1Label);

        // 2
        final Label item2Label;

        if(!Objects.equals(ironInventory.ironItem2.name, "")) {
            item2Label = new Label("" + ironInventory.ironItem2.name, game.assetHandler.menuLabelStyle);
        } else {
            item2Label = new Label("Empty", game.assetHandler.menuLabelStyle);
        }
        item2Label.setFontScale(1.5f);
//        item2Label.setPosition(item1Label.getX(), item1Label.getY() - item2Label.getHeight() * 3);
//        addActor(item2Label);
        labels.add(item2Label);

        // 3
        final Label item3Label;

        if(!Objects.equals(ironInventory.ironItem3.name, "")) {
            item3Label = new Label("" + ironInventory.ironItem3.name, game.assetHandler.menuLabelStyle);
        } else {
            item3Label = new Label("Empty", game.assetHandler.menuLabelStyle);
        }
        item3Label.setFontScale(1.5f);
//        item3Label.setPosition(item1Label.getX(), item2Label.getY() - item3Label.getHeight() * 3);
//        addActor(item3Label);
        labels.add(item3Label);

        // 4
        final Label item4Label;

        if(!Objects.equals(ironInventory.ironItem4.name, "")) {
            item4Label = new Label("" + ironInventory.ironItem4.name, game.assetHandler.menuLabelStyle);
        } else {
            item4Label = new Label("Empty", game.assetHandler.menuLabelStyle);
        }
        item4Label.setFontScale(1.5f);
//        item4Label.setPosition(item1Label.getX(), item3Label.getY() - item4Label.getHeight() * 3);
//        addActor(item4Label);
        labels.add(item4Label);

        // 5
        final Label item5Label;

        if(!Objects.equals(ironInventory.ironItem5.name, "")) {
            item5Label = new Label("" + ironInventory.ironItem5.name, game.assetHandler.menuLabelStyle);
        } else {
            item5Label = new Label("Empty", game.assetHandler.menuLabelStyle);
        }
        item5Label.setFontScale(1.5f);
//        item5Label.setPosition(item1Label.getX(), item4Label.getY() - item5Label.getHeight() * 3);
//        addActor(item5Label);
        labels.add(item5Label);

        // LAYOUT TODO: move up to super
        Vector2 nextPosition = new Vector2(background.getX() + background.getWidth() * 0.15f, background.getHeight() - background.getHeight() * 0.25f);
        float width = 0;
        for(Label label : labels) {
            addActor(label);
            if(label.getWidth() > width) {
                width = label.getWidth() + label.getWidth() * .25f;
            }
            label.setPosition(nextPosition.x, nextPosition.y);
            nextPosition = new Vector2(label.getX(), label.getY() + label.getHeight() * 1.5f);
        }

        background.setWidth(width * 2.25f);
        background.setHeight(item1Label.getHeight() * (labels.size * 3));

        // BACK
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
                game.activeGridScreen.hudStage.addActor(new FieldActionsPopup(game, unit, x, y, originRow, originColumn));
                self.remove();
            }
        });

        addActor(backLabel);
    }

}
