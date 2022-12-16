package com.feiqn.wyrm.models.unitdata.units.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.Unit;

public class Leif extends Unit {
    public Leif(WYRMGame game) {
        super(game);
        sharedInit();
    }

    public Leif(WYRMGame game, Texture texture) {
        super(game, texture);
        sharedInit();
    }

    public Leif(WYRMGame game, TextureRegion region) {
        super(game, region);
        sharedInit();
    }

    private void sharedInit() {

    }
}
