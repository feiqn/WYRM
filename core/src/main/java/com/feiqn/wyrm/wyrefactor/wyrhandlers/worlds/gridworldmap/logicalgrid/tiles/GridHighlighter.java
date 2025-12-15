package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.BallistaActionsPopup;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.BattlePreviewPopup;
import com.feiqn.wyrm.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public class GridHighlighter extends Image {

    protected final GridTile tile;

    protected final GridMetaHandler h;

    public GridHighlighter(GridMetaHandler metaHandler, GridTile tile, boolean clickable) {
        super(WYRMGame.assets().solidBlueTexture);
        this.h = metaHandler;
        this.tile = tile;

        this.setSize(1,1);

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
                tile.fireFirstInteractable();
                for(GridTile t : h.map().getAllTiles()) {
                    t.removeLastInteractable();
                }
            }

        });
    }

    // TODO: pulse and shimmer, shade red for enemies
}
