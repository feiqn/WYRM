package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Campaign.FlagID;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Campaign.WinConPolarity;

public class WyrWinCondition {

    protected final WinConPolarity necessity;

    protected boolean isTerminal = false;
    protected boolean isSatisfied = false;
    protected boolean isRevealed = false;

    protected String shortDescription;
    protected String longDescription = null;

    protected Image imageDrawable = new Image();

    private WyrActor associatedActor = null;
    private final FlagID associatedFlag;
    private Vector2 associatedCoordinate = null;
    private int turnGoal = -1;

    public WyrWinCondition(FlagID flag) {
        this(WinConPolarity.OPTIONAL, flag, flag.toString());
    }

    public WyrWinCondition(WinConPolarity polarity, FlagID flag) {
        this(polarity, flag, flag.toString());
    }

    public WyrWinCondition(WinConPolarity polarity, FlagID flagID, String shortDescription) {
        this.necessity = polarity;
        this.associatedFlag = flagID;
        this.shortDescription = shortDescription;
    }

    public WinConPolarity getPolarity() { return  necessity; }

    public WyrWinCondition terminal() {
        isTerminal = true;
        return this;
    }
    public WyrWinCondition setActor(WyrActor actor) {
        associatedActor = actor;
        imageDrawable.setDrawable(actor.getDrawable());
        return this;
    }
    public WyrWinCondition setLocal(Vector2 coordinate) { associatedCoordinate = coordinate; return this;}
    public WyrWinCondition setTurn(int turnGoal) { this.turnGoal = turnGoal; return this; }

    public FlagID getAssociatedFlag() {
        return associatedFlag;
    }

    public int getTurnGoal() {
        return turnGoal;
    }

    public WyrActor getAssociatedActor() {
        return associatedActor;
    }

    public Vector2 getAssociatedCoordinate() {
        return associatedCoordinate;
    }

    public void satisfy() {
        isSatisfied = true;
    }
    public void reveal() {
        isRevealed = true;
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

    public WyrWinCondition setLongDescription(String description) { longDescription = description; return this; }
    public WyrWinCondition setShortDescription(String description) { shortDescription = description; return this; }

    public String getShortDescription() { return shortDescription; }
    public String getLongDescription() { return longDescription; }

    public Image getImageDrawable() { return imageDrawable; }

}
