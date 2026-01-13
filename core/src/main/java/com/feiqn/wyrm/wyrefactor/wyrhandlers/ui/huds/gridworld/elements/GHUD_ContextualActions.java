package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.gridworld.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;
import org.jetbrains.annotations.NotNull;

public class GHUD_ContextualActions extends Window {

    private final GridMetaHandler h; // It's fun to just type "h".

    private final Table table = new Table();

    private final Array<GridInteraction> interactables = new Array<>();

    private final Skin temp; // TODO: later this will pull from asset handler

    public GHUD_ContextualActions(Skin skin, GridMetaHandler metaHandler) {
        super("", skin);
        this.h = metaHandler;
        this.temp = skin;
        table.setFillParent(true);
        table.pad(3);
        this.add(table);
    }

    private void populate() {
        table.clearChildren();

        for(GridInteraction interaction : interactables) {
            final Image subjectImage = new Image(interaction.getParent().getDrawable());

            table.add(subjectImage);
            table.add(newLabel(verbString(interaction.getInteractType()), temp.get(Label.LabelStyle.class)));
            if(interaction.hasObject()) table.add(new Image(interaction.getObject().getDrawable()));
            table.row();
        }
    }

    public void setContext(@NotNull GridTile tile) {
        interactables.clear();
        interactables.addAll(tile.getEphemeralInteractables());
        populate();
    }

    public void addInteraction(GridInteraction interaction) {
        interactables.add(interaction);
        populate();
    }

    public void clear() {
//        Gdx.app.log("hud:context", "should clear...");
        interactables.clear();
        populate();
    }

    private String verbString(WyrInteraction.InteractionType interactionType) {
        // Can probably streamline this some other way.
        switch(interactionType) {
            case MOVE:
                return "move here";
            case ATTACK:
                return "attack";
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
