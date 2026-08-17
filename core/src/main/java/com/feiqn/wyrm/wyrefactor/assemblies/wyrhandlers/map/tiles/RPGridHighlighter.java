package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.prefab.WyrShaders;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.WyrInputHandler;

public final class RPGridHighlighter extends Image {

    private final WyrTile tile;

    private float alpha = 0f;
    private boolean descending = false;
    private boolean pulsing = true;
    private boolean dying = false; // like a star, baby

    private ShaderProgram shader = null;

    public RPGridHighlighter(WyrTile tile) {
        super(WYRMGame.assets().solidBlueTexture);
        this.tile = tile;

        this.setSize(1,1);

        this.addListener(WyrInputHandler.Listeners.TILE_highlighterLeftClick(tile));
        this.addListener(WyrInputHandler.Listeners.TILE_highlighterRightClick(tile));
    }

    public void reset() {
        alpha = 0f;
        descending = false;
        pulsing = true;
        dying = false;
        shader = null;
        remove();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(pulsing) updateAlpha();
        batch.setShader(shader);
        super.draw(batch, parentAlpha);
        batch.setShader(null);
    }

    public RPGridHighlighter red() {
        shader = WyrShaders.Enemy.standard();
        return this;
    }

    public void pulse(boolean pulse) {
        if(pulse) {
            pulsing = true;
        } else {
            pulsing = false;
            this.addAction(Actions.fadeIn(.03f, Interpolation.bounce));
        }
    }

    public RPGridHighlighter setZ(int index) {
        super.setZIndex(index);
        return this;
    }

    private void updateAlpha() {
        if(dying) {
            alpha -= .05f;
            if(alpha <= 0) reset();
        } else {
            if(descending && alpha > .2f) {
                alpha -= .0055f;
            } else {
                if(descending) descending = false;
                if(alpha <= .3f) alpha += .0055f;
                alpha += .0055f;
                if(alpha >= .55f) descending = true;
            }
        }
        // TODO: shade red for enemies
        this.setColor(1,1,1, alpha);
    }

    public void kill() {
        dying = true; // weirdly morbid verbiage
                      // -- ^ totally agree, dude wtf?
                      // --   seeing .kill() made me think
                      // --   this extends WyrActor for
                      // --   some reason.
    }
}
