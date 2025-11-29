package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.MetaHandler;

public abstract class WyrActor extends Image {

    // Things shared between different types of WyrScreens,
    // Grid combat, in menus, on world map, etc.,
    // also a generic higher-level class for bullets and effects, etc

    protected final WYRMGame root;

    protected static MetaHandler h;

    protected String name = "";


    public WyrActor(WYRMGame root) {
        this(root, (Drawable)null);
    }

    public WyrActor (WYRMGame root, @Null NinePatch patch) {
        this(root, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }

    public WyrActor (WYRMGame root, @Null TextureRegion region) {
        this(root, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }

    public WyrActor (WYRMGame root, Texture texture) {
        this(root, new TextureRegionDrawable(new TextureRegion(texture)));
    }

    public WyrActor (WYRMGame root, Skin skin, String drawableName) {
        this(root, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }

    public WyrActor (WYRMGame root, @Null Drawable drawable) {
        this(root, drawable, Scaling.stretch, Align.center);
    }

    public WyrActor (WYRMGame root, @Null Drawable drawable, Scaling scaling) {
        this(root, drawable, scaling, Align.center);
    }

    public WyrActor (WYRMGame root, @Null Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
        this.root = root;
        h = root.handlers();
    }
}
