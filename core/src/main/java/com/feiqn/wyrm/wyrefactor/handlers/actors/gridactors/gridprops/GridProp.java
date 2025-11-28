package com.feiqn.wyrm.wyrefactor.handlers.actors.gridactors.gridprops;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.handlers.actors.gridactors.GridActor;

public class GridProp extends GridActor {



    public GridProp(WYRMGame root) {
        super(root);
    }

    public GridProp(WYRMGame root, NinePatch patch) {
        super(root, patch);
    }

    public GridProp(WYRMGame root, TextureRegion region) {
        super(root, region);
    }

    public GridProp(WYRMGame root, Texture texture) {
        super(root, texture);
    }

    public GridProp(WYRMGame root, Skin skin, String drawableName) {
        super(root, skin, drawableName);
    }

    public GridProp(WYRMGame root, Drawable drawable) {
        super(root, drawable);
    }

    public GridProp(WYRMGame root, Drawable drawable, Scaling scaling) {
        super(root, drawable, scaling);
    }

    public GridProp(WYRMGame root, Drawable drawable, Scaling scaling, int align) {
        super(root, drawable, scaling, align);
    }
}
