package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.feiqn.wyrm.WYRMGame;

public class HUDElement extends Group {

    protected final WYRMGame game;

    protected float xDist,
                    yDist;

    public HUDElement(WYRMGame game) {
        this.game = game;
    }

    public void align() {
        // TODO: SDFAJHGJSRKFJASDFwsrkhmrhgASREfgdi
        this.setPosition(xDist, yDist);
    }

    public void setAlignment(float x, float y) {
        // Pass in percent distances from sides in .5f format; more like pass in FUCK
        this.xDist = x;
        this.yDist = y;
        align();
    }
}
