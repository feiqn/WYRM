package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.WyrShaders;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.WyrInputHandler;

public final class RPGridHighlighter extends Image {

    private final RPGridTile tile;

    private float alpha = .4f;
    private boolean descending = false;
    private boolean pulsing = true;

    private ShaderProgram shader = null;

    public RPGridHighlighter(RPGridTile tile) {
        super(WYRMGame.assets().solidBlueTexture);
        this.tile = tile;

        this.setSize(1,1);

        this.addListener(WyrInputHandler.Listeners.TILE_highlighterLeftClick(tile));
        this.addListener(WyrInputHandler.Listeners.TILE_highlighterRightClick(tile));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(pulsing) updateAlpha();
        batch.setShader(shader);
        super.draw(batch, parentAlpha);
        batch.setShader(null);
    }
//
//    @Override
//    protected void hoverOver() {
//        super.hoverOver();
//
//    }


//    public void shade(ShaderState s, TeamAlignment t) {
//        shader = WyrShaders.Enemy.standard();
//        switch(s) {
//            case STANDARD:
//                switch(t) {
//                    case PLAYER:
//                        shader = null;
//                        break;
//                    case ENEMY:
//                        shader = WyrShaders.Enemy.standard();
//                        this.setColor(Color.RED);
//                        break;
//                    case ALLY:
//                        shader = WyrShaders.Ally.standard();
//                        break;
//                }
//            case HIGHLIGHT:
//            case DIM:
//                break;
//        }
//    }
    public void pulse(boolean pulse) {
        if(pulse) {
            pulsing = true;
        } else {
            pulsing = false;
            this.addAction(Actions.fadeIn(.03f, Interpolation.bounce));
        }
    }
    private void updateAlpha() {
        if(descending && alpha > .3f) {
            alpha -= .0055f;
        } else {
            if(descending) descending = false;
            alpha += .0055f;
            if(alpha >= .65f) descending = true;
        }
        this.setColor(1,1,1, alpha);
    }
    // TODO: pulse and shimmer, shade red for enemies
}
