package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.scenes.scene2d.Group;

public class HUDElement extends Group {

    protected float xPos,
                    yPos;

    public void align() {
        this.setPosition(xPos, yPos);
    }

    public void setAlignment(float x, float y) {
        this.xPos = x;
        this.yPos = y;
        align();
    }
}
