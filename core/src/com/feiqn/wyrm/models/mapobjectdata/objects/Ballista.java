package com.feiqn.wyrm.models.mapobjectdata.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapobjectdata.MapObject;
import com.feiqn.wyrm.models.unitdata.Unit;

public class Ballista extends MapObject {

    public int ammo;
    public int range;
    public Unit occupyingUnit;

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
        range = 30;
    }

    public void setOccupyingUnit(Unit unit) {
        occupyingUnit = unit;
    }

    public void exitUnit() {
        occupyingUnit = null;
    }

}
