package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract class WyrCutsceneScript {

    // refactor of CutsceneScript

    public enum LoopCondition {
        MULTIPLICATIVE_THRESHOLD,
        BROKEN_THRESHOLD
    }

    protected final Array<WyrCutsceneSlide>   script         = new Array<>();
    protected final Array<WyrCutsceneTrigger> triggers       = new Array<>();
    protected final Array<WyrCutsceneTrigger> defuseTriggers = new Array<>();

    protected boolean hasPlayed   = false;
    protected boolean readyToPlay = false;
    protected boolean defused     = false;

    protected int triggerThreshold = 1;
    protected int defuseThreshold  = 1;
    protected int triggerCount     = 0;
    protected int defuseCount      = 0;
    protected int slideIndex       = 0;

    protected LoopCondition loopCondition;

    private final WyrType type;
    private final CutsceneID cutsceneID;

    protected WyrCutsceneScript(WyrType type, CutsceneID id) {
        this.type = type;
        this.cutsceneID = id;
    }

    abstract void buildScript();

    abstract void declareTriggers();

    protected void addTrigger(WyrCutsceneTrigger trigger) {
        if(!triggers.contains(trigger, true)) triggers.add(trigger);
    }

    protected void addDefuseTrigger(WyrCutsceneTrigger trigger) {
        if(!defuseTriggers.contains(trigger, true)) defuseTriggers.add(trigger);
    }

    protected void setLoopCondition(LoopCondition condition) { this.loopCondition = condition;}
    protected LoopCondition getLoopCondition() { return loopCondition; }
    protected boolean isLooping() { return loopCondition != null;}
    public WyrType getType() { return type; }
    public CutsceneID getCutsceneID() { return cutsceneID; }
}
