package com.feiqn.wyrm.models.mapdata.mapobjectdata;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.unitdata.Unit;

public class MapObject extends Image {

    private WYRMGame game;

    public String name;

    public LogicalTile occupyingTile;

    public boolean solid;

    public boolean occupied;

    public Unit occupyingUnit;

    protected final MapObject self = this;

    public ObjectType objectType;

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
        this.objectType = ObjectType.DOOR;
        occupyingTile = new LogicalTile(game, -1,-1);
        reach = 0;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
