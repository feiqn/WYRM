package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.wyrefactor.helpers.Speed;
import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.campaign.wyrm.CampaignFlags;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.slides.Position;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

public abstract class WyrCutsceneChoreography<
        Actor extends WyrActor<?,?,?,?>
            > extends Subjectivity<Actor> implements Wyr {

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
        NONE,

        ADD_PORTRAIT,
        REMOVE_PORTRAIT,

        SLIDE_TO,
        BUMP_INTO,
        HOP,
        SHAKE,
        RUMBLE,
        RESET,
        FLIP,
    }

    public enum WorldChoreoType {
        NONE,

        SPAWN,
        DESPAWN,

        MOVE,
        ATTACK,

        USE_PROP,
        USE_ABILITY,

        FOCUS_UNIT,
        FOCUS_TILE,

        UNIT_DEATH,
        SHORT_PAUSE,
        LINGER,

        REVEAL_CONDITION,

        SCREEN_TRANSITION,
        FADE_TO_BLACK,
    }

    private final ChoreoStage choreoStage;
    protected WorldChoreoType  worldChoreoType  = WorldChoreoType.NONE;
    protected DialogChoreoType dialogChoreoType = DialogChoreoType.NONE;
    protected Position dialogSubjectPosition;
    protected Position dialogObjectPosition;
    protected CampaignFlags associatedCampaignFlag;
    protected Vector2       associatedCoordinate;
    protected ScreenAdapter screenForTransition;
    protected boolean playParallel;
    protected Runnable payload;
    protected boolean loops;
    protected Speed speed;

    public WyrCutsceneChoreography(WorldChoreoType worldChoreoType) {
        this.choreoStage = ChoreoStage.WORLD;
        this.worldChoreoType = worldChoreoType;
    }
    public WyrCutsceneChoreography(DialogChoreoType dialogChoreoType) {
        this.choreoStage = ChoreoStage.DIALOG;
        this.dialogChoreoType = dialogChoreoType;
    }

    public ChoreoStage getChoreoStage()           { return choreoStage; }
    public WorldChoreoType getChoreoType()        { return worldChoreoType; }
    public DialogChoreoType getDialogChoreoType() { return dialogChoreoType; }

    // SETTERS
    public void loop() { this.loops = true;}
    public void setCoordinate(float column, float row) { this.associatedCoordinate = new Vector2(column, row); }
    public void setCoordinate(Vector2 coordinates) { this.associatedCoordinate = coordinates; }
    public void setLocation(GridTile tile) { this.associatedCoordinate = new Vector2(tile.getXColumn(), tile.getYRow()); }
    public void setFlag(CampaignFlags flagID) { this.associatedCampaignFlag = flagID; }
    public void setScreenForTransition(ScreenAdapter screen) { this.screenForTransition = screen; }

    // GETTERS
    public boolean loops() { return loops;}
    public ScreenAdapter getScreenForTransition() { return screenForTransition; }
    public CampaignFlags getFlag() { return associatedCampaignFlag; }
    public Vector2 getLocation() { return associatedCoordinate; }
}
