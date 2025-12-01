package com.feiqn.wyrm.logic.handlers.cutscene.dialog;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class CutsceneFrameChoreography {

    // Choreography is stuff that happens on the map / over-world,
    // as opposed to DialogActions which happen inside the Conversation window.

    public enum ChoreoType {
        SPAWN,
        DESPAWN,
        MOVE,
        FOCUS_UNIT,
        FOCUS_TILE,
        UNIT_DEATH,
        SHORT_PAUSE,
        LINGER,
        ATTACK,
        BALLISTA_ATTACK,
        ABILITY,
        REVEAL_VICTCON,
        SCREEN_TRANSITION,
        FADE_OUT_TO_BLACK,
        END_OF_CUTSCENE,
    }

    // consider making all these methods protected,
    // possibly abstracting this class,
    // and making an inheritor class Choreographer
    // with all the wrapper methods currently in
    // CutsceneScript

    private final ChoreoType choreoType;
    private SimpleUnit subject;
    private SimpleUnit object;
    private String subjectID = "";
    private String objectID = "";
    private Vector2 location = new Vector2();
    private Abilities ability;
    private CampaignFlags victConFlagID;
    private ScreenAdapter screenForTransition;


    public CutsceneFrameChoreography(ChoreoType choreoType) {
        this.choreoType = choreoType;
    }

    // SETTERS

    public void setSubjectID(String subjectID) { this.subjectID = subjectID; }

    public void setObjectID(String objectID) { this.objectID = objectID; }

    public void setLocation(int column, int row) {
        this.location = new Vector2(column, row);
    }

    public void setLocation(Vector2 coordinates) {
        this.location = coordinates;
    }

    public void setLocation(LogicalTile tile) {
        this.location = new Vector2(tile.getColumnX(), tile.getRowY());
    }

    public void setVictConFlagID(CampaignFlags flagID) { this.victConFlagID = flagID; }

    public void setObject(SimpleUnit object) {
        this.object = object;
    }

    public void setSubject(SimpleUnit subject) {
        this.subject = subject;
    }

    public void setAbility(Abilities ability) { this.ability = ability; }

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

    public Abilities getAbility() {
        return ability;
    }

    public ChoreoType getType() {
        return choreoType;
    }

    public SimpleUnit getObject() {
        return object;
    }

    public SimpleUnit getSubject() {
        return subject;
    }

    public Vector2 getLocation() {
        return location;
    }
}
