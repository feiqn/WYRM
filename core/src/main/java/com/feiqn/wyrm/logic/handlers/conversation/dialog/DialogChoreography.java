package com.feiqn.wyrm.logic.handlers.conversation.dialog;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.logic.handlers.campaign.CampaignFlags;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class DialogChoreography {

    // Choreography is stuff that happens on the map / over-world,
    // as opposed to DialogActions which happen inside the Conversation window.

    public enum Type {
        SPAWN,
        DESPAWN,
        MOVE,
        FOCUS_UNIT,
        FOCUS_TILE,
        SHORT_PAUSE,
        LINGER,
        ATTACK,
        ABILITY,
        REVEAL_VICTCON,
    }

    private SimpleUnit subject;
    private SimpleUnit object;
    private Vector2 location;
    private final Type type;
    private Abilities ability;
    private CampaignFlags victConFlagID;


    public DialogChoreography(Type type) {
        this.type = type;
    }

    // SETTERS

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

    // GETTERS

    public CampaignFlags getVictConFlagID() { return victConFlagID; }

    public Abilities getAbility() {
        return ability;
    }

    public Type getType() {
        return type;
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
