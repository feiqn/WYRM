package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.grid.elements.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public class GHUD_TurnOrder extends HorizontalGroup {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GHUD_TurnOrder(GridMetaHandler metaHandler) {
        super();
        this.h = metaHandler;
        updateAll();
    }

    public void updateAll() {
        this.clearChildren();

        // TODO: testing,
        //  code should eventually be moved to asset manager
        Skin skin = new Skin();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("test/uiSkinTest/uiskin.atlas"));
        skin.addRegions(atlas);
        //

        for(GridUnit unit : h.conditions().unifiedTurnOrder()) {
            this.addActor(new Panels.UnitPanel(unit, skin));
        }
    }

    private final static class Panels {

//        private static UnitPanel get(GridUnit unit) {
//            return new UnitPanel(unit, )
//        }

        private final static class UnitPanel extends ImageButton {
//            private final GridUnit unit;

            public UnitPanel(GridUnit unit, Skin skin) {
                super(skin);
//                this.unit = unit;
                this.padRight(3);
                this.add(new Image(unit.getDrawable()));

                // TODO:
                //  update background color based on team alignment

                this.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        // TODO: focus on unit
                    }
                });

            }
        }
    }

}
