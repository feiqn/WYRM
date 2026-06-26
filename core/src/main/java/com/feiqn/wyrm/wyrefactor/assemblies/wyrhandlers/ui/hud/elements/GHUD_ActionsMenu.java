package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.hud.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;

public class GHUD_ActionsMenu extends GHUD_ContextDisplay {

    public GHUD_ActionsMenu(Skin skin) {
        super(skin);
        this.setModal(true);
    }

    @Override
    protected void populate() {
        table.clearChildren();

        Image subjectImage = new Image();

        for (WyrInteraction interaction : interactions) {
            final Image thisSubjectImage = new Image(interaction.getSubject().getDrawable());
            final Label label = new Label(verbString(interaction.getInteractType()), temp.get(Label.LabelStyle.class));
            label.addListener(WyrInputHandler.Listeners.HUD_actionMenuLabel(interaction));

            table.add(thisSubjectImage);
            table.add(label);
            if (interaction.hasObject()) table.add(new Image(interaction.getObject().getDrawable()));
            table.row();

            subjectImage = new Image(thisSubjectImage.getDrawable());
        }

        if(!handlers.campaign().checkCampaignFlag(Campaign.FlagID.UNDO_CUTSCENE_PLAYED)) {
            final Label undoLabel = new Label("undo", temp.get(Label.LabelStyle.class));
            undoLabel.setColor(Color.PURPLE);
            undoLabel.addListener(new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button)  {
                    super.touchUp(event,x,y,point,button);

                    // "we can never go back..."

                }
            });
            table.add(new Image(subjectImage.getDrawable()));
            table.add(undoLabel);
            table.row();
        }

        final Label cancelLabel = new Label("cancel", temp.get(Label.LabelStyle.class));
        cancelLabel.addListener(new ClickListener() {
           @Override
           public void touchUp(InputEvent event, float x, float y, int point, int button)  {
               super.touchUp(event,x,y,point,button);

               handlers.standardizeParse();

           }
        });

        table.add(subjectImage);
        table.add(cancelLabel);
        table.row();
    }
}
