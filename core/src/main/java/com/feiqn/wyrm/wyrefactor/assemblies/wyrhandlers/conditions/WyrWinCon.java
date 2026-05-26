package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr;

public  class WyrWinCon {

    public enum Necessity {
        VICTORY,
        OPTIONAL,
        FAILURE,
    }

    protected Necessity necessity = null;

    protected boolean isTerminal = false;
    protected boolean isSatisfied = false;
    protected boolean isRevealed = false;

    protected String shortDescription = null;
    protected String longDescription = null;

    protected final String uID;

    protected Image imageDrawable = new Image();


    private WyrActor associatedActor = null;
    private Wyr.Campaign.FlagID associatedFlag = null;
    private Vector2 associatedCoordinate = null;
    private int turnGoal = -1;


    public WyrWinCon(String uID) {
        // TODO: sanity check in register for dupes
        this.uID = uID;
    }

    public Necessity getNecessity() { return  necessity; }

    public WyrWinCon terminal() {
        isTerminal = true;
        return this;
    }
    public WyrWinCon setActor(WyrActor actor) {
        associatedActor = actor;
        imageDrawable.setDrawable(actor.getDrawable());
        return this;
    }
    public WyrWinCon setFlag(Wyr.Campaign.FlagID flag) { associatedFlag = flag; return this; }
    public WyrWinCon setLocal(Vector2 coordinate) { associatedCoordinate = coordinate; return this;}
    public WyrWinCon setTurn(int turnGoal) { this.turnGoal = turnGoal; return this; }

    public Wyr.Campaign.FlagID getAssociatedFlag() {
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

    protected WyrWinCon necessity(Necessity n) { necessity = n; return this; }

    public WyrWinCon setLongDescription(String description) { longDescription = description; return this; }
    public WyrWinCon setShortDescription(String description) { shortDescription = description; return this; }

    public String getShortDescription() { return shortDescription; }
    public String getLongDescription() { return longDescription; }

    public Image getImageDrawable() { return imageDrawable; }

    public String getUniqueID() {
        return uID;
    }

}
