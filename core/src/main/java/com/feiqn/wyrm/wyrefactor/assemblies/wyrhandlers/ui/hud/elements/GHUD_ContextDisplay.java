package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.hud.elements;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import org.jetbrains.annotations.NotNull;

public class GHUD_ContextDisplay extends Window implements WyrFrame {

    protected final Table table = new Table();

    protected final Array<WyrInteraction> interactions = new Array<>();

    protected final Skin temp; // TODO: later this will pull from asset handler

    public GHUD_ContextDisplay(Skin skin) {
        super("", skin);
        this.temp = skin;
        table.setFillParent(true);
        table.pad(3);
        this.add(table);
    }

    protected void populate() {
        table.clearChildren();

        for(WyrInteraction interaction : interactions) {
            final Image subjectImage = new Image(interaction.getSubject().getDrawable());

            table.add(subjectImage);
            table.add(newLabel(verbString(interaction.getInteractType()), temp.get(Label.LabelStyle.class)));
            if(interaction.hasObject()) table.add(new Image(interaction.getObject().getDrawable()));
            table.row();
        }
    }

    public void inferContext(RPGridTile tile, WyrActor forUnit) {
        interactions.clear();
        interactions.addAll(tile.getAllInteractions());

        final GridPathfinder.Things reachable = GridPathfinder.reachableFromTile(tile, forUnit);

        for(RPGridTile T : reachable.tiles().keySet()) {
            for(WyrInteraction interaction : T.getAllInteractions()) {
                if(interaction.interactableRange() <= forUnit.getReach()) {
                    switch(interaction.getInteractType()) {
                        case MOVE_BY:
                        case MOVE_ATTACK:
                        case MOVE_WAIT:
                        case MOVE_TALK:
                        case WAIT:
                            continue;
                        default:
                            interactions.add(interaction);
                    }
                }
            }
        }

        if(forUnit.stats().canAct()) {
            for(WyrActor enemy :  reachable.enemies().keySet()) {
                final WyrInteraction attack = new WyrInteraction(forUnit).attack(enemy, 1);
                interactions.add(attack);
            }
        }


        final WyrInteraction waitInteraction = new WyrInteraction(forUnit).passPriority();

        interactions.add(waitInteraction);

        this.populate();
    }

    public void setContext(@NotNull RPGridTile tile) {
        interactions.clear();
        interactions.addAll(tile.getAllInteractions());
        populate();
    }

    public void addInteraction(WyrInteraction interaction) {
        interactions.add(interaction);
        populate();
    }

    public void clear() {
        interactions.clear();
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
