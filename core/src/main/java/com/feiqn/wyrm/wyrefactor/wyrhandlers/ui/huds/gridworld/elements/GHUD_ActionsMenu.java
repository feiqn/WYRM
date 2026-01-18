package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.gridworld.elements;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;

public class GHUD_ActionsMenu extends GHUD_ContextualActions {

    public GHUD_ActionsMenu(Skin skin, GridMetaHandler metaHandler) {
        super(skin, metaHandler);
    }

    @Override
    protected void populate() {
        table.clearChildren();

        for(GridInteraction interaction : interactables) {
            final Image subjectImage = new Image(interaction.getParent().getDrawable());
            final Label label = new Label(verbString(interaction.getInteractType()), temp.get(Label.LabelStyle.class));
            label.addListener(GridInputHandler.GridListeners.HUD_actionMenuLabelListener(h, interaction));

            table.add(subjectImage);
            table.add(label);
            if(interaction.hasObject()) table.add(new Image(interaction.getObject().getDrawable()));
            table.row();

        }
    }
}
