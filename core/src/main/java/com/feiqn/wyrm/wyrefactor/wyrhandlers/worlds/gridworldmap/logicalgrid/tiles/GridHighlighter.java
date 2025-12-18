package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public class GridHighlighter extends Image {

    protected final GridTile tile;

    protected final GridMetaHandler h;

    private float alpha = 1;
    private boolean descending = true;

    public GridHighlighter(GridMetaHandler metaHandler, GridTile tile, boolean clickable) {
        super(WYRMGame.assets().solidBlueTexture);
        this.h = metaHandler;
        this.tile = tile;

        this.setSize(1,1);

        final SequenceAction pulseSequence = new SequenceAction();
        pulseSequence.addAction(Actions.fadeOut(3));
        pulseSequence.addAction(Actions.fadeIn(3));
        this.addAction(Actions.forever(pulseSequence));

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
//                tile.fireInteractable();
                for(GridTile t : h.map().getAllTiles()) {
                    // TODO: this might be wrong.
//                    t.removeLastInteractable();
                }
            }

        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        updateAlpha();
        super.draw(batch, parentAlpha);
    }

    private void updateAlpha() {
        if(descending && alpha > 0) {
            alpha -= .1f;
        } else {
            if(descending) descending = false;
            alpha += .1f;
            if(alpha >= 1) descending = true;
        }
    }

    // TODO: pulse and shimmer, shade red for enemies
}
