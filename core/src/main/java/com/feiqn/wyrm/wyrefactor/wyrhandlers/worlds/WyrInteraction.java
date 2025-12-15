package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.ToolTipPopup;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;

public abstract class WyrInteraction extends Wyr {

    // Honestly don't know what best practice
    // says about making this one giant enum
    // or a bunch of smaller enums in discrete
    // classes.
    // Most likely the answer is the opposite
    // of whatever I go with.
    public enum InteractionType { // TODO: can probably abstract these to not be "grid_"... etc
        GRID_TALK,
        GRID_ATTACK,
        GRID_MOVE,

        GRID_PROP_ESCAPE, // objectives as props rather than tile types
        GRID_PROP_SEIZE,

        GRID_PROP_PILOT, // like a ballista, etc.

        GRID_PROP_OPEN, // i.e., a door
        GRID_PROP_LOOT, // a chest, a corpse
        GRID_PROP_DESTROY,
    }

    private final InteractionType interactType;

    protected final Label clickableLabel;

    protected final WyrActor parent;

    protected final int interactableRange; // 0 = requires standing on top of parent, same tile.

    protected boolean available = true;

    protected WyrInteraction(WyrType wyrType, WyrActor parent, InteractionType interactType, int interactableRange, CharSequence label, CharSequence toolTipText) {
        super(wyrType);
        this.interactType = interactType;
        this.parent = parent;
        this.interactableRange = interactableRange;
        clickableLabel = new Label("" + label, WYRMGame.assets().menuLabelStyle);
        clickableLabel.setFontScale(2);
        clickableLabel.addListener(new InputListener(){
            boolean clicked = false;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                clicked = true;
                WYRMGame.activeOLDGridScreen.hud().reset();
                payload();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                WYRMGame.activeOLDGridScreen.hud().addToolTip(new ToolTipPopup("" + toolTipText));

                // TODO: make parent glow
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(!clicked) WYRMGame.activeOLDGridScreen.hud().removeToolTip();
            }

        });
    }

    abstract public void payload();


    public void setAvailable() { available = true; }
    public void setUnavailable() { available = false; }

    public Label getClickableLabel() {
        if(clickableLabel != null) return clickableLabel;
        Gdx.app.log("WyrInteraction", "ERROR, label called before set.");
        return new Label("<error>", WYRMGame.assets().menuLabelStyle);
    }
    public InteractionType getInteractType() { return interactType; }
    public boolean isAvailable() { return available; }
    public WyrActor getParent() { return parent; }
    public int getInteractableRange() { return interactableRange; }
}
