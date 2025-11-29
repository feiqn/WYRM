package com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects;

import com.badlogic.gdx.Gdx;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.MapObject;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.ObjectType;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class BallistaObject extends MapObject {

    protected int ammo;

    public BallistaObject(WYRMGame game) {
        super(game, game.assetHandler.ballistaTexture);
        name = "Ballista";
        ammo = 5;
        reach = 35;

        setSize(1, 1.5f);

        objectType = ObjectType.BALLISTA;

    }

    public void consumeAmmo() {
        ammo--;
        if(ammo < 0) ammo = 0;
    }

//    public void enterUnit(SimpleUnit unit) {
//        Gdx.app.log("ballista", "I'm in a ballista!");
//        setOccupyingUnit(unit); // idk why I made this wrapper function, but I'm leaving it in just in case I remember later
//    }

//    private void setOccupyingUnit(SimpleUnit unit) {
//        unit.isOccupyingMapObject = true;
//        unit.occupyingMapObject = self;
//        occupyingUnit = unit;
//        occupied = true;
//        solid = true;
//    }

//    public void exitUnit(SimpleUnit unit) {
//        unit.isOccupyingMapObject = false;
//        unit.occupyingMapObject = null;
//        occupyingUnit = null;
//        occupied = false;
//        solid = false;
//    }

}
