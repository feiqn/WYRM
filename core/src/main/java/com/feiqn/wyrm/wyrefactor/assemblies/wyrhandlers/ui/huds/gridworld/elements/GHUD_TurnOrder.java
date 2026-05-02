package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds.gridworld.elements;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

public class GHUD_TurnOrder extends HorizontalGroup {

    private final RPGridMetaHandler h; // It's fun to just type "h".


    private final Skin skin;
    public GHUD_TurnOrder(Skin skin, RPGridMetaHandler metaHandler) {
        super();
        this.skin = skin;
        this.clear();
        this.h = metaHandler;
    }

    private void build() {
        this.clearChildren();

        for(RPGridUnit unit : h.register().unifiedTurnOrder()) {
            this.addActor(new Panels.UnitPanel(unit, skin));
        }
    }

    public void update() {
        if(this.getChildren().size != h.register().unifiedTurnOrder().size) build();

//        for(Actor panel : getChildren()) {
//            if(panel instanceof Panels.UnitPanel) {
//                final boolean shouldBeFocused = (h.conditions().unitsHoldingPriority().contains(((Panels.UnitPanel) panel).unit, true));
//                if(((Panels.UnitPanel) panel).isFocused && shouldBeFocused) continue;
//                if(!((Panels.UnitPanel) panel).isFocused && !shouldBeFocused) continue;
//                if(!shouldBeFocused && ((Panels.UnitPanel) panel).isFocused) {
//                    // unfocus
//                } else {
//                    // focus
//                }
//            }
//        }
    }


    private final static class Panels {

//        private static UnitPanel get(GridUnit unit) {
//            return new UnitPanel(unit, )
//        }

        private final static class UnitPanel extends ImageButton {

            // consider making this a container with stack instead

            private final RPGridUnit unit;
            private boolean isFocused = false;

            public UnitPanel(RPGridUnit unit, Skin skin) {
                super(skin);
                this.unit = unit;
                this.sizeBy(2);
                this.clear();
                this.pad(2);
                this.add(new Image(unit.getDrawable())).pad(2);

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
