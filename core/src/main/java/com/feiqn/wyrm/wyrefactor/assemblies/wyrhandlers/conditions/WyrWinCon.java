package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class WyrWinCon {

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

    protected Drawable drawable = null;

    public WyrWinCon(String uID) {
        // TODO: sanity check in register for dupes
        this.uID = uID;
    }

    public Necessity getNecessity() { return  necessity; }

    public WyrWinCon terminal() {
        isTerminal = true;
        return this;
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

    public String getUniqueID() {
        return uID;
    }
}
