package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.ToolTipPopup;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;

public abstract class WyrInteraction {

    // Honestly don't know what best practice
    // says about making this one giant enum
    // or a bunch of smaller enums in discrete
    // classes.
    // Most likely the answer is the opposite
    // of whatever I go with.
    public enum InteractionType {
        GRID_TALK,
        GRID_ATTACK,

        GRID_PROP_ESCAPE, // objectives as props rather than tile types
        GRID_PROP_SEIZE,

        GRID_PROP_PILOT, // like a ballista, etc.

        GRID_PROP_OPEN,
        GRID_PROP_LOOT,
        GRID_PROP_DESTROY,
    }

    // TODO: some mechanic for triggering active / inactive options?

    private final WyrType wyrType;

    private final InteractionType interactType;

    protected final WYRMGame root;

    protected Label clickableLabel;
    protected RunnableAction runnableInteraction = new RunnableAction();

    protected WyrInteraction(WYRMGame root, WyrType wyrType, InteractionType interactType) {
        this.root = root;
        this.wyrType = wyrType;
        this.interactType = interactType;
    }

    protected void applyListenerToLabel(CharSequence toolTipText) {
        if(clickableLabel == null) return;
        if(runnableInteraction.getRunnable() == null) return;

        clickableLabel.addListener(new InputListener(){
            ToolTipPopup toolTipPopup;
            boolean clicked = false;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                clicked = true;
                root.activeGridScreen.hud().reset();
                runnableInteraction.run();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                toolTipPopup = new ToolTipPopup(root,"" + toolTipText);
                root.activeGridScreen.hud().addToolTip(toolTipPopup);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(!clicked) root.activeGridScreen.hud().removeToolTip();
            }

        });
    }

    public Label getClickableLabel() {
        if(clickableLabel != null) return clickableLabel;
        Gdx.app.log("WyrInteraction", "ERROR, label called before set.");
        return new Label("<error>", root.assets().menuLabelStyle);
    }
    public RunnableAction getRunnableInteraction() {
        if(runnableInteraction != null) return runnableInteraction;
        Gdx.app.log("WyrInteraction", "ERROR, runnable called before set.");
        return new RunnableAction();
    }
    public WyrType getWyrType() { return wyrType; }
    public InteractionType getInteractType() { return interactType; }
}
