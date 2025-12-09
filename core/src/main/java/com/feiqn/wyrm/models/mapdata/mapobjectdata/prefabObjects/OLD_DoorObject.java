package com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.MapObject;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.OLD_ObjectType;

public class OLD_DoorObject extends MapObject {

    public int health;
    public boolean breakable,
                   pickable;

//    public Key associatedKey;

    public OLD_DoorObject(WYRMGame game) {
        super(game);
        sharedInit();
    }

    public OLD_DoorObject(WYRMGame game, Texture texture) {
        super(game, texture);
        sharedInit();
    }

    public OLD_DoorObject(WYRMGame game, TextureRegion region) {
        super(game, region);
        sharedInit();
    }

    private void sharedInit() {
        solid = true;
        name = "Door";
        health = 100;
        breakable = false;
        pickable = true;
        OLDObjectType = OLD_ObjectType.DOOR;

    }

    public int getHealth() {
        return health;
    }

}
