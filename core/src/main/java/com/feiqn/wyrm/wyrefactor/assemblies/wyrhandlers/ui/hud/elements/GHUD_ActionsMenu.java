package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.hud.elements;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;

public class GHUD_ActionsMenu extends GHUD_ContextualActions {

    public GHUD_ActionsMenu(Skin skin) {
        super(skin);
        this.setModal(true);
    }

    @Override
    protected void populate() {
        table.clearChildren();


        for (WyrInteraction interaction : interactions) {
            final Image subjectImage = new Image(interaction.getSubject().getDrawable());
            final Label label = new Label(verbString(interaction.getInteractType()), temp.get(Label.LabelStyle.class));
            label.addListener(WyrInputHandler.Listeners.HUD_actionMenuLabel(interaction));

            table.add(subjectImage);
            table.add(label);
            if (interaction.hasObject()) table.add(new Image(interaction.getObject().getDrawable()));
            table.row();

        }
    }
}
