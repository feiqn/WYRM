package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.hud.gridhud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.WyrTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.InteractionType;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.InputMode.MENU_FOCUSED;

public class GHUD_InteractionMenu extends Window implements WyrFrame {

    protected final Table table = new Table();

    protected final Array<InteractionType> hypotheticalActions = new Array<>();
    protected final Array<WyrInteraction> interactions = new Array<>();

    protected boolean clickable = true;
    protected boolean anchored = false;

    protected final Skin temp; // TODO: later this will pull from asset handler

    public GHUD_InteractionMenu(Skin skin) {
        super("", skin);
        this.temp = skin;
//        this.setModal(true);
        table.setFillParent(true);
        table.pad(3);
        this.add(table);
    }

    public void fireFirst() {
        if(!clickable)return;
        if(interactions.isEmpty()) return;
        anchored = true;
        handlers.interactions().parseInteraction(interactions.get(0));
    }

    public void anchor() {
        if(!clickable) return;
        if(interactions.isEmpty()) return;
        anchored = true;
        handlers.input().setInputMode(MENU_FOCUSED);
        handlers.register().setFocusedMenu(this);
//        if(interactions.size == 1) {
//            handlers.interactions().parseInteraction(interactions.get(0));
//            return;
//        }
        populate();
        setVisible(true);
        this.setColor(1,1,1,1);
    }

    public void followMouse() {
        anchored = false;
        clickable = true;
        this.setColor(1,1,1, .6f);
        interactions.clear();
        setVisible(true);
    }

    @Override
    public void setPosition(float x, float y) {
        if(anchored) return;
        super.setPosition(x,y);
    }

    public void readTile(@Null WyrTile tile) {
        if(tile == null) return;
        if(anchored) return;
        interactions.clear();
//        hypotheticalActions.clear();
        switch(handlers.input().getMovementControlMode()) {
            case TURN_BASED:
                if(tile.getStateActions().isEmpty()) {
                    for(WyrActor actor : handlers.priority().unitsHoldingPriority()) {
                        tile.deriveInteractions(actor, null,null,null);
                    }
                }
                interactions.addAll(tile.getStateActions());
                break;
            case FREE_MOVE:
                interactions.addAll(tile.deriveInteractions(handlers.register().avatarUnit()));
                break;
        }
        populate();
        setVisible(true);
    }

    protected void populate() {
        table.clearChildren();

        if(interactions.isEmpty()) {
            setVisible(false);
            setColor(1,1,1,.1f);
            return;
        } else {
            setVisible(true);
            setColor(1,1,1,.85f);
        }

        Image subjectImage = new Image();

        boolean first = true;

        for (WyrInteraction interaction : interactions) {
            if(interaction.isHidden()) continue;
            final Image thisSubjectImage = new Image(interaction.getSubject().getDrawable());
            final Label label = new Label(verbString(interaction.getInteractType()), temp.get(Label.LabelStyle.class));
            if(!interaction.isLocked()) {
                label.addListener(WyrInputHandler.Listeners.HUD_actionMenuLabel(interaction));
                if(first) {
                    label.setColor(Color.YELLOW);
                    first = false;
                }
            } else {
                label.setColor(.3f,.3f,.3f,1);
            }

            table.add(thisSubjectImage);
            table.add(label);
            if (interaction.hasObject()) table.add(new Image(interaction.getObject().getDrawable()));
            table.row();

            subjectImage = new Image(thisSubjectImage.getDrawable());
        }

        if(!anchored) return;

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
        anchored = true;
        setColor(1,1,1,0);
        interactions.clear();
        setVisible(false);
        populate();
    }

    protected String verbString(InteractionType interactionType) {
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
