package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds.gridworld.elements;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.gridinput.RPGridInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid.RPGridInteraction;

public class GHUD_ActionsMenu extends GHUD_ContextualActions {

    public GHUD_ActionsMenu(Skin skin, RPGridMetaHandler metaHandler) {
        super(skin, metaHandler);
        this.setModal(true);
    }

    @Override
    protected void populate() {
        table.clearChildren();


        for (RPGridInteraction interaction : interactions) {
            final Image subjectImage = new Image(interaction.getSubject().getDrawable());
            final Label label = new Label(verbString(interaction.getInteractType()), temp.get(Label.LabelStyle.class));
            label.addListener(RPGridInputHandler.Listeners.HUD_actionMenuLabel(h, interaction));

            table.add(subjectImage);
            table.add(label);
            if (interaction.hasObject()) table.add(new Image(interaction.getObject().getDrawable()));
            table.row();

        }
    }
}
