package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.hud.gridhud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.WyrTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;

public class GHUD_InteractionMenu extends Window implements WyrFrame {

    protected final Table table = new Table();

    protected final Array<WyrInteraction> interactions = new Array<>();

    protected boolean anchored = false;

    protected final Skin temp; // TODO: later this will pull from asset handler

    public GHUD_InteractionMenu(Skin skin) {
        super("", skin);
        this.temp = skin;
        this.setModal(true);
        table.setFillParent(true);
        table.pad(3);
        this.add(table);
    }

    public void anchor() {
        anchored = true;
        setVisible(true);
        this.setColor(1,1,1,1);
    }

    public void followMouse() {
        anchored = false;
        this.setColor(1,1,1, .6f);
        clear();
    }

    @Override
    public void setPosition(float x, float y) {
        if(anchored) return;
        super.setPosition(x,y);
    }

    public void readTile(WyrTile tile) {
        if(anchored) return;
        interactions.clear();
        interactions.addAll(tile.deriveInteractions(handlers.priority().unitsHoldingPriority()));
        populate();
        setVisible(true);
    }

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

        if(!WyrFrame.Campaign.checkFlag(Campaign.FlagID.UNDO_CUTSCENE_PLAYED)) {
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


    public void clear() {
        if(anchored) return;
        interactions.clear();
        setVisible(false);
        populate();
    }

    protected String verbString(GameKit.RPG.InteractionType interactionType) {
        // Can probably streamline this some other way.
        switch(interactionType) {

            case MOVE_BY:
            case MOVE_TO:
            case MOVE_WAIT:
                return "move here";

            case MOVE_ATTACK:
//                return "charge";
            case ATTACK:
                return "attack";
            case WAIT:
                return "wait here";
            case MOVE_TALK:
            case TALK:
                return "talk to";

            case PROP_FIRE:
                return "fire";
            case PROP_LOCK:
                return "lock";
            case PROP_CLOSE:
                return "close";
            case PROP_UNLOCK:
                return "unlock";
            case PROP_AIM:
                return "aim";
            case PROP_USE:
                return "use";
            case PROP_LOOT:
                return "loot";
            case PROP_OPEN:
                return "open";
            case PROP_PILOT:
                return "pilot";
            case PROP_SEIZE:
                return "seize";
            case PROP_ESCAPE:
                return "escape!";
            case PROP_DESTROY:
                return "destroy";

            case EXAMINE:
                return "examine";

            default:
                return interactionType.toString();
        }
    }
}
