package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Campaign.FlagID;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Campaign.WinConPolarity;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Campaign.WinConType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.Name;

public final class WyrWinCondition {

    private final WinConPolarity necessity;
    private final String associatedFlag; // cast as necessary
    private final Image imageDrawable = new Image();
    private final WinConType conditionType;

    private boolean isTerminal = false;
    private boolean isSatisfied = false;
    private boolean isRevealed = false;

    private String shortDescription;
    private String longDescription = null;

    private Name associatedCharacter = null;
    private String associatedPropUniqueID = null;
    private WyrActor associatedActor = null;
    private Vector2 associatedCoordinate = null;
    private int turnGoal = -1;

    public WyrWinCondition(WinConType type, FlagID flag) { this(type, WinConPolarity.OPTIONAL, flag, flag.toString()); }
    public WyrWinCondition(WinConType type, WinConPolarity polarity, FlagID flag) { this(type, polarity, flag, flag.toString()); }
    public WyrWinCondition(WinConType type, WinConPolarity polarity, FlagID flagID, String shortDescription) {
        this(type, polarity, flagID.toString(), shortDescription);
    }
    public WyrWinCondition(WinConType type, WinConPolarity polarity, String flagID, String shortDescription) {
        this.conditionType = type;
        this.necessity = polarity;
        this.associatedFlag = flagID;
        this.shortDescription = shortDescription;
    }

    public void satisfy() {
        isSatisfied = true;
    }
    public WyrWinCondition reveal() {
        isRevealed = true;
        return this;
    }

    public boolean isRevealed() {
        return isRevealed;
    }
    public boolean isSatisfied() {
        return isSatisfied;
    }
    public boolean isTerminal() {
        return isTerminal;
    }

    public WyrWinCondition setPropUID(String uniqueID) { this.associatedPropUniqueID = uniqueID; return this; }
    public WyrWinCondition setCharacter(Name name) { this.associatedCharacter = name; return this; }
    public WyrWinCondition setTerminal() {
        isTerminal = true;
        return this;
    }
    public WyrWinCondition setActor(WyrActor actor) {
        associatedActor = actor;
        imageDrawable.setDrawable(actor.getDrawable());
        return this;
    }
    public WyrWinCondition setCoordinate(Vector2 coordinate) { associatedCoordinate = coordinate; return this;}
    public WyrWinCondition setTurn(int turnGoal) { this.turnGoal = turnGoal; return this; }

    public WyrWinCondition setLongDescription(String description) { longDescription = description; return this; }
    public WyrWinCondition setShortDescription(String description) { shortDescription = description; return this; }

    public String getAssociatedFlag() { return associatedFlag; }
    public WinConType getConditionType() { return conditionType; }

    public @Null String getShortDescription() { return shortDescription; }
    public @Null String getLongDescription() { return longDescription; }
    public @Null String getPropUniqueID() { return associatedPropUniqueID; }
    public @Null Name getAssociatedCharacter() { return associatedCharacter; }
    public @Null Image getImageDrawable() { return imageDrawable; }
    public @Null int getTurnGoal() { return turnGoal; }
    public @Null WyrActor getAssociatedActor() { return associatedActor; }
    public @Null Vector2 getAssociatedCoordinate() { return associatedCoordinate; }
    public @Null WinConPolarity getPolarity() { return  necessity; }

}
