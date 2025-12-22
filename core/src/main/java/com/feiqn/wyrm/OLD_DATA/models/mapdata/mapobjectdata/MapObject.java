package com.feiqn.wyrm.OLD_DATA.models.mapdata.mapobjectdata;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.OLD_SimpleUnit;

public class MapObject extends Image {

    private WYRMGame game;

    public String name;

    public OLD_LogicalTile occupyingTile;

    public boolean solid;

    public boolean occupied;

    public OLD_SimpleUnit occupyingUnit;

    protected final MapObject self = this;

    public OLD_ObjectType OLDObjectType;

    public int row,
               column,
               reach;

    public MapObject(WYRMGame game) {
        super();
        this.game = game;
        sharedInit();
    }

    public MapObject(WYRMGame game, Texture texture) {
        super(texture);
        this.game = game;
        sharedInit();
    }

    public MapObject(WYRMGame game, TextureRegion region) {
        super(region);
        this.game = game;
        sharedInit();
    }

    private void sharedInit() {
        name = "Interactable Object";
        setSize(1,1);
        solid = false;
        occupied = false;
        this.OLDObjectType = OLD_ObjectType.DOOR;
        occupyingTile = new OLD_LogicalTile(game, -1,-1);
        reach = 0;

        addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(game.activeOLDGridScreen.getInputMode() != OLD_GridScreen.OLD_InputMode.STANDARD) return;
                game.activeOLDGridScreen.hud().updateHoveredUnitInfoPanel(self);
            }
        });
    }



    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
