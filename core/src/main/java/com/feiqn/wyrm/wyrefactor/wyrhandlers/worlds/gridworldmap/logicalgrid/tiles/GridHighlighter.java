package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public class GridHighlighter extends Image {

    protected final GridTile tile;

    protected final GridMetaHandler h;

    private float alpha = 0;
    private boolean descending = false;

    private final Array<Shader> shaders = new Array<>();

//    private final Array<WyrActor.ShaderState>

    public GridHighlighter(GridMetaHandler metaHandler, GridTile tile, boolean clickable) {
        super(WYRMGame.assets().solidBlueTexture);
        this.h = metaHandler;
        this.tile = tile;

        this.setSize(1,1);

        if (!clickable) return;

        this.addListener(GridInputHandler.GridListeners.tileHighlighterClickListener(h, tile));
        this.addListener(GridInputHandler.GridListeners.tileHighlighterRightClickListener(h,tile));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateAlpha();
        super.draw(batch, parentAlpha);
    }

    public void shade(Shader shader) {
        this.shaders.add(shader);
    }

    public void clearShade() {
        shaders.clear();
    }

    private void updateAlpha() {
        if(descending && alpha > .05f) {
            alpha -= .0025f;
        } else {
            if(descending) descending = false;
            alpha += .0025f;
            if(alpha >= .45f) descending = true;
        }
        this.setColor(1,1,1, alpha);
    }

    // TODO: pulse and shimmer, shade red for enemies
}
