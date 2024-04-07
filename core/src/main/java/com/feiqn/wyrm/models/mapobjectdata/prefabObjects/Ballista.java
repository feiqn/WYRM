package com.feiqn.wyrm.models.mapobjectdata.prefabObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapobjectdata.MapObject;
import com.feiqn.wyrm.models.mapobjectdata.ObjectType;
import com.feiqn.wyrm.models.unitdata.Unit;

public class Ballista extends MapObject {

    public int ammo;

    public Ballista(WYRMGame game) {
        super(game);
        sharedInit();
    }

    public Ballista(WYRMGame game, Texture texture) {
        super(game, texture);
        sharedInit();
    }

    public Ballista(WYRMGame game, TextureRegion region) {
        super(game, region);
        sharedInit();
    }

    private void sharedInit() {
        name = "Ballista";
        ammo = 5;
        reach = 15;

        objectType = ObjectType.BALLISTA;

    }

    public void enterUnit(Unit unit) {
        Gdx.app.log("ballista", "I'm in a ballista!");
        setOccupyingUnit(unit); // idk why I made this wrapper function, but I'm leaving it in just in case I remember later
    }

    private void setOccupyingUnit(Unit unit) {
        unit.isOccupyingMapObject = true;
        unit.occupyingMapObject = self;
        occupyingUnit = unit;
        occupied = true;
        solid = true;
    }

    public void exitUnit(Unit unit) {
        unit.isOccupyingMapObject = false;
        unit.occupyingMapObject = null;
        occupyingUnit = null;
        occupied = false;
        solid = false;
    }

}
