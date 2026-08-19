package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.hud.gridhud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.WyrTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.InteractionType;

import java.util.Comparator;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.InputMode.MENU_FOCUSED;

public class GHUD_InteractionMenu extends Window implements WyrFrame {

    protected final Table table = new Table();

    protected final Array<WyrInteraction> interactions = new Array<>();

    protected boolean clickable = true;
    protected boolean anchored = false;

    protected final Skin temp; // TODO: later this will pull from asset handler

    public GHUD_InteractionMenu(Skin skin) {
        super("", skin);
        this.temp = skin;
        table.pad(3);
        this.add(table).expand();
    }

    public void fireFirst() {
        if(!clickable)return;
        if(interactions.isEmpty()) return;
        if(interactions.get(0).isLocked()) return;
        anchored = true;
        handlers.interactions().parseInteraction(interactions.get(0));
    }

    public void anchor() {
        if(!clickable) return;
        if(interactions.isEmpty()) return;
        setModal(true);
        anchored = true;
        handlers.input().setInputMode(MENU_FOCUSED);
        handlers.register().setFocusedMenu(this);
        populate();
        setVisible(true);
        this.setColor(1,1,1,1);
    }

    public void hide() {
        standardize();
        setColor(1,1,1,0);
        setVisible(false);
//        clickable = false;
//        anchored = true;
    }


    @Override
    public void setPosition(float x, float y) {
        if(anchored) return;
        super.setPosition(x,y - this.getHeight());
    }

    public void readTile(@Null WyrTile tile) {
        if(tile == null) return;
        if(anchored) return;
        interactions.clear();
        switch(handlers.input().getMovementControlMode()) {
            case TURN_BASED:
                if(tile.getStateActions().isEmpty()) {
                    for(WyrActor actor : handlers.priority().unitsHoldingPriority()) {
                        tile.deriveInteractions(actor);
                    }
                }
                interactions.addAll(tile.getStateActions());
                sortInteractions();
                break;
            case FREE_MOVE:
                interactions.addAll(tile.deriveInteractions(handlers.register().avatarUnit()));
                sortInteractions();
                break;
        }
        populate();
        setVisible(true);
    }

    protected void populate() {
        table.clear();

        if(interactions.isEmpty()) {
            setVisible(false);
            setColor(1,1,1,.05f);
            return;
        } else {
            setVisible(true);
            setColor(1,1,1,.75f);
        }

        sortInteractions();

        Image subjectImage = new Image();

        boolean first = true;
        boolean allLocked = true;

        for (WyrInteraction interaction : interactions) {
            if(interaction.isHidden()) continue;
            final Image thisSubjectImage = new Image(interaction.getSubject().getDrawable());
            final Label label = new Label(verbString(interaction.getInteractType()), temp.get(Label.LabelStyle.class));
            if(!interaction.isLocked()) {
                allLocked = false;
                label.addListener(WyrInputHandler.Listeners.HUD_actionMenuLabel(interaction));
                if(first) {
                    if(!anchored) label.setColor(Color.YELLOW);
                    first = false;
                }
            } else {
                label.setColor(.5f,.5f,.5f,1);
            }

            table.add(thisSubjectImage).expand();
            table.add(label);
            if (interaction.hasObject()) table.add(new Image(interaction.getObject().getDrawable())).expand();
            table.row();

            subjectImage = new Image(thisSubjectImage.getDrawable());
        }

        if(allLocked) {
            this.setColor(1,1,1, .65f);
        }

        if(!anchored) return;

        if(!WyrFrame.Campaign.checkFlag(Campaign.FlagID.UNDO_CUTSCENE_PLAYED)) {
            final Label undoLabel = new Label("undo", temp.get(Label.LabelStyle.class));
            undoLabel.setColor(Color.PURPLE);
            undoLabel.addListener(new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button)  {
                    super.touchUp(event,x,y,point,button);
                    handlers.cutscenes().checkCSIDTriggers(Cutscene.ID.CSID_0_UNDO);
                    Campaign.hitFlag(Campaign.FlagID.UNDO_CUTSCENE_PLAYED);

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

                standardize();
                handlers.standardizeParse();

            }
        });

        table.add(subjectImage);
        table.add(cancelLabel);
        table.row();
    }

    private void sortInteractions() {
        interactions.sort(new Comparator<WyrInteraction>() {
            @Override
            public int compare(WyrInteraction o1, WyrInteraction o2) {
                return typePriority(typePriority(typePriority(o1.getInteractType()) - typePriority(o2.getInteractType())));
            }

            private InteractionType typePriority(int i) {
                switch(i) {
                    case 0: return InteractionType.TALK;
                    case 1: return InteractionType.ATTACK;

                    case 2: return InteractionType.PROP_AIM;
                    case 3: return InteractionType.PROP_FIRE;

                    case 4: return InteractionType.MOUNT;
                    case 5: return InteractionType.CALL_MOUNT;

                    case 6: return InteractionType.ABILITY_USE;

                    case 7: return InteractionType.MOVE_TO;
                    case 8: return InteractionType.FOLLOW_PATH;

                    case 9: return InteractionType.WAIT;

                    default: return InteractionType.EXAMINE;

                }
            }

            private int typePriority(InteractionType type) {
                switch(type) {
                    case TALK: return 0;
                    case ATTACK: return 1;
                    case PROP_AIM: return 2;
                    case PROP_FIRE: return 3;
                    case MOUNT: return 4;
                    case CALL_MOUNT: return 5;
                    case ABILITY_USE: return 6;
                    case MOVE_TO: return 7;
                    case FOLLOW_PATH: return 8;
                    case WAIT: return 9;
                    case EXAMINE: return 10;
                    default: return 11;
                }
            }

        });
    }

    public void standardize() {
        anchored = false;
        clickable = true;
        setColor(1,1,1,.75f);
        setVisible(true);
        setModal(false);
        populate();
    }

    public String verbString(InteractionType interactionType) {
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

    public boolean isAnchored() {
        return anchored;
    }
}
