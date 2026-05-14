package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.choreography;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.WyrWinCon;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.WyrScreen;
import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

public class WyrCutsceneChoreography extends Subjectivity<WyrActor> implements Wyr {

    // refactored combination of CutsceneFrameChoreography and DialogAction

    // "Choreography is stuff that happens on the map / over-world,
    // as opposed to DialogActions which happen inside the Conversation window."

    public enum ChoreoStage {
        WORLD,
        DIALOG,
    }

    // You can put basically anything here
    // and build out behavior for it later.
    public enum DialogChoreoType {
        SCREEN_TRANSITION,
        SCREEN_FADE_IN,
        SCREEN_FADE_OUT,

        PORTRAIT_SLIDE_TO,
        PORTRAIT_BUMP_INTO,
        PORTRAIT_HOP,
        PORTRAIT_SHAKE,
        PORTRAIT_RUMBLE,
        PORTRAIT_STANDARDIZE,
        PORTRAIT_FLIP,

        WINCON_REVEAL,
        WINCON_SATISFY,

        PAUSE_SHORT,
        PAUSE_LONG,

        CUTSCENE_END,
    }

    private final ChoreoStage  choreoStage;
    protected Enum<?>          characterID            = null;
    protected WyrInteraction   worldInteraction       = null;
    protected DialogChoreoType dialogChoreoType       = null;
    protected Enum<?>          associatedCampaignFlag = null;
    protected Vector2          associatedCoordinate   = null;
    protected WyrScreen        screenForTransition    = null;
    protected Runnable         payload                = null;
    protected WyrWinCon        associatedWinCon       = null;
    protected boolean          loops                  = false;
    protected boolean          playParallel           = false;
    protected Speed            actSpeed               = Speed.NORMAL;
    protected WyrCutscene.LoopCondition loopCondition = null;


    public WyrCutsceneChoreography(WyrInteraction worldInteraction) {
        this.choreoStage = ChoreoStage.WORLD;
        this.worldInteraction = worldInteraction;
    }
    public WyrCutsceneChoreography(DialogChoreoType dialogChoreoType) {
        this.choreoStage = ChoreoStage.DIALOG;
        this.dialogChoreoType = dialogChoreoType;
    }

    // SETTERS
    public WyrCutsceneChoreography loop()                                   { this.loops = true; return this;}
    public WyrCutsceneChoreography setCoordinate(float column, float row)   { this.associatedCoordinate = new Vector2(column, row); return this; }
    public WyrCutsceneChoreography setCoordinate(Vector2 coordinates)       { this.associatedCoordinate = coordinates; return this; }
    public WyrCutsceneChoreography setLocation(GridTile tile)               { this.associatedCoordinate = new Vector2(tile.getXColumn(), tile.getYRow()); return this; }
    public WyrCutsceneChoreography setFlag(Enum<?> flagID)                  { this.associatedCampaignFlag = flagID; return this; }
    public WyrCutsceneChoreography setScreenForTransition(WyrScreen screen) { this.screenForTransition = screen; return this; }
    public WyrCutsceneChoreography setWinCon(WyrWinCon condition)           { this.associatedWinCon = condition; return this; }

    // GETTERS
    public ChoreoStage      getChoreoStage()      { return choreoStage; }
    public WyrInteraction   getWorldInteraction() { return worldInteraction; }
    public DialogChoreoType getDialogChoreoType() { return dialogChoreoType; }

    public boolean loops() { return loops;}
    public ScreenAdapter getScreenForTransition() { return screenForTransition; }
    public Enum<?> getFlag() { return associatedCampaignFlag; }
    public Vector2 getLocation() { return associatedCoordinate; }
    public Enum<?> getCharacterID() { return characterID; }
}
