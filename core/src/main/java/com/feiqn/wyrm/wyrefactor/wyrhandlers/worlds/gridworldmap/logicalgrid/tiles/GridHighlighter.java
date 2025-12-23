package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public class GridHighlighter extends Image {

    protected final GridTile tile;

    protected final GridMetaHandler h;

    private float alpha = 1;
    private boolean descending = true;

    private final Array<Shader> shaders = new Array<>();

//    private final Array<WyrActor.ShaderState>

    public GridHighlighter(GridMetaHandler metaHandler, GridTile tile, boolean clickable) {
        super(WYRMGame.assets().solidBlueTexture);
        this.h = metaHandler;
        this.tile = tile;

        this.setSize(1,1);

//        final SequenceAction pulseSequence = new SequenceAction();
//        pulseSequence.addAction(Actions.fadeOut(3));
//        pulseSequence.addAction(Actions.fadeIn(3));
//        this.addAction(Actions.forever(pulseSequence));

        if (!clickable) return;

        this.addListener(new ClickListener() {
            private boolean dragged = false;
            private boolean clicked = true;

            @Override
            public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                dragged = true;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dragged = false;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button)  {
                if(dragged) {
                    dragged = false;
                    clicked = false;
                    return;
                }

                clicked = true;

                for(GridTile t : h.map().getAllTiles()) {
                    t.unhighlight();
                }

                if(tile.interactables.size == 1) {
                    h.actors().parseInteractable(tile.interactables.get(0));
                }

                // TODO:
                //  If tile has multiple interactables,
                //  open a menu to select which one to fire.

                // TODO:
                //  make a debug menu to show all tile's interactables
                //  on hover.

            }

        });
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
        if(descending && alpha > .3f) {
            alpha -= .0035f;
        } else {
            if(descending) descending = false;
            alpha += .0035f;
            if(alpha >= .7f) descending = true;
        }
        this.setColor(1,1,1, alpha);
    }

    // TODO: pulse and shimmer, shade red for enemies
}
