package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_CutsceneFrameChoreography;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.AbilityID;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.OLD_SimpleUnit;
import com.feiqn.wyrm.wyrefactor.helpers.Speed;
import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;

public abstract class WyrCutsceneChoreography<
        Actor extends WyrActor
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
        SLIDE_TO,
        BUMP_INTO,
        HOP,
        SHAKE,
        RUMBLE,
        RESET,
        FLIP,
        CHOREOGRAPHY,
        ARBITRARY_CODE,
    }

    public enum WorldChoreoType {
        NONE,
        SPAWN,
        DESPAWN,
        MOVE,
        FOCUS_UNIT,
        FOCUS_TILE,
        UNIT_DEATH,
        SHORT_PAUSE,
        LINGER,
        ATTACK,
        USE_ABILITY,
        REVEAL_VICTORY_CONDITION,
        SCREEN_TRANSITION,
        FADE_OUT_TO_BLACK,
    }

    protected WorldChoreoType  worldChoreoType  = WorldChoreoType.NONE;
    protected DialogChoreoType dialogChoreoType = DialogChoreoType.NONE;
    private final ChoreoStage choreoStage;
    protected Array<CampaignFlags> associatedCampaignFlags = new Array<>();
    protected Array<Vector2>       associatedCoordinates   = new Array<>();
    protected ScreenAdapter screenForTransition;
    protected boolean playParallel;
    protected boolean loops;
    protected Speed speed;
    protected Runnable payload;

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

    public abstract static class Choreographer{}

    // SETTERS

    public void setSubjectID(String subjectID) { this.subjectID = subjectID; }

    public void setObjectID(String objectID) { this.objectID = objectID; }

    public void setLocation(int column, int row) {
        this.location = new Vector2(column, row);
    }

    public void setLocation(Vector2 coordinates) {
        this.location = coordinates;
    }

    public void setLocation(OLD_LogicalTile tile) {
        this.location = new Vector2(tile.getColumnX(), tile.getRowY());
    }

    public void setVictConFlagID(CampaignFlags flagID) { this.victConFlagID = flagID; }

    public void setObject(OLD_SimpleUnit object) {
        this.object = object;
    }

    public void setSubject(OLD_SimpleUnit subject) {
        this.subject = subject;
    }

    public void setAbility(AbilityID ability) { this.ability = ability; }

    public void setScreenForTransition(ScreenAdapter screen) {
        this.screenForTransition = screen;
    }

    // GETTERS

    public String getSubjectID() { return subjectID; }

    public String getObjectID() { return objectID; }

    public ScreenAdapter getScreenForTransition() {
        return screenForTransition;
    }

    public CampaignFlags getVictConFlagID() { return victConFlagID; }

    public AbilityID getAbility() {
        return ability;
    }

    public OLD_CutsceneFrameChoreography.OLD_ChoreoType getType() {
        return choreoType;
    }

    public OLD_SimpleUnit getObject() {
        return object;
    }

    public OLD_SimpleUnit getSubject() {
        return subject;
    }

    public Vector2 getLocation() {
        return location;
    }

}
