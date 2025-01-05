package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.GridScreen;

public class HUDElement extends Group {

    protected final WYRMGame game;

//    protected float xDist,
//                    yDist;

    protected Image background;

    protected final GridScreen abs;

    public HUDElement(WYRMGame game) {
        this.game = game;
        abs = game.activeGridScreen;
        background = new Image();
    }

//    public void align() {
//        // TODO: this was a failed attempt to automatically rescale ui to fit new viewport after resize()
////        this.setPosition(xDist, yDist);
//    }
//
//    public void setAlignment(float x, float y) {
//        // Pass in percent distances from sides in .5f format; more like pass in FUCK
//        this.xDist = x;
//        this.yDist = y;
//        align();
//    }
}
