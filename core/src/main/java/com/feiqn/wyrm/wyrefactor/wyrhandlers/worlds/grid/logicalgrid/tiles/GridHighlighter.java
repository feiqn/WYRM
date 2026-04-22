package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

public class GridHighlighter extends Image {

    protected final GridTile tile;

    protected final RPGridMetaHandler h;

    private float alpha = 0;
    private boolean descending = false;
    private boolean pulsing = true;

    private final Array<Shader> shaders = new Array<>();

    public GridHighlighter(RPGridMetaHandler metaHandler, GridTile tile) {
        super(WYRMGame.assets().solidBlueTexture);
        this.h = metaHandler;
        this.tile = tile;

        this.setSize(1,1);

        this.addListener(GridInputHandler.Listeners.tileHighlighterLeftClickListener(h, tile));
        this.addListener(GridInputHandler.Listeners.tileHighlighterRightClickListener(h,tile));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(pulsing) updateAlpha();
        super.draw(batch, parentAlpha);
    }

    public void shade(Shader shader) { this.shaders.add(shader); }
    public void clearShade() { shaders.clear(); }
    public void pulse(boolean pulse) {
        if(pulse) {
            pulsing = true;
        } else {
            pulsing = false;
            this.addAction(Actions.fadeIn(.5f, Interpolation.bounce));
        }
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
