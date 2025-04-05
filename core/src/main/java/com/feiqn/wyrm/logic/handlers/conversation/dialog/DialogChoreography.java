package com.feiqn.wyrm.logic.handlers.conversation.dialog;

import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class DialogChoreography {

    // Choreography is stuff that happens on the map / over-world,
    // as opposed to DialogActions which happen inside the Conversation.

    public enum Type {
        SPAWN,
        MOVE,
        CENTER_CAMERA,
        LINGER,
        ATTACK,
        ABILITY,
    }

    private SimpleUnit subject;
    private SimpleUnit object;
    private LogicalTile location;
    private final Type type;


    public DialogChoreography(Type type) {
        this.type = type;
    }

    public void setLocation(LogicalTile location) {
        this.location = location;
    }

    public void setObject(SimpleUnit object) {
        this.object = object;
    }

    public void setSubject(SimpleUnit subject) {
        this.subject = subject;
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

    public LogicalTile getLocation() {
        return location;
    }
}
