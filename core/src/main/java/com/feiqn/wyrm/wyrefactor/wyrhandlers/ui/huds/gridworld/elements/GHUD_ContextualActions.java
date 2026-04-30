package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.gridworld.elements;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.pathfinder.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;
import org.jetbrains.annotations.NotNull;

public class GHUD_ContextualActions extends Window {

    protected final RPGridMetaHandler h; // It's fun to just type "h".

    protected final Table table = new Table();

    protected final Array<RPGridInteraction> interactions = new Array<>();

    protected final Skin temp; // TODO: later this will pull from asset handler

    public GHUD_ContextualActions(Skin skin, RPGridMetaHandler metaHandler) {
        super("", skin);
        this.h = metaHandler;
        this.temp = skin;
        table.setFillParent(true);
        table.pad(3);
        this.add(table);
    }

    protected void populate() {
        table.clearChildren();

        for(RPGridInteraction interaction : interactions) {
            final Image subjectImage = new Image(interaction.getSubject().getDrawable());

            table.add(subjectImage);
            table.add(newLabel(verbString(interaction.getInteractType()), temp.get(Label.LabelStyle.class)));
            if(interaction.hasObject()) table.add(new Image(interaction.getObject().getDrawable()));
            table.row();
        }
    }

    public void inferContext(GridTile tile, RPGridUnit forUnit) {
        interactions.clear();
        interactions.addAll(tile.getAllInteractions());

        final GridPathfinder.Things reachable = GridPathfinder.reachableFromTile(h.map(), tile, forUnit);

        for(GridTile T : reachable.tiles().keySet()) {
            for(RPGridInteraction interaction : T.getAllInteractions()) {
                if(interaction.interactableRange() <= forUnit.getReach()) {
                    interactions.add(interaction);
                }
            }
        }

        for(RPGridUnit enemy :  reachable.enemies().keySet()) {
            final RPGridInteraction attack = new RPGridInteraction(forUnit).attack(enemy, 1);
            interactions.add(attack);
        }

        final RPGridInteraction waitInteraction = new RPGridInteraction(forUnit).passPriority();

        interactions.add(waitInteraction);

        this.populate();
    }

    public void setContext(@NotNull GridTile tile) {
        interactions.clear();
        interactions.addAll(tile.getAllInteractions());
        populate();
    }

    public void addInteraction(RPGridInteraction interaction) {
        interactions.add(interaction);
        populate();
    }

    public void clear() {
        interactions.clear();
        populate();
    }

    protected String verbString(RPGridInteraction.GridInteractID interactionType) {
        // Can probably streamline this some other way.
        switch(interactionType) {
            case MOVE_WAIT:
                return "move here";
            case MOVE_ATTACK:
            case ATTACK:
                return "attack";
            case WAIT:
                return "wait here";
            case TALK:
                return "talk to";

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
            default:
                return "???";
        }
    }
}
