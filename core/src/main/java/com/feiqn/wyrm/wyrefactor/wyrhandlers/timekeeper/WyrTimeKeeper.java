package com.feiqn.wyrm.wyrefactor.wyrhandlers.timekeeper;

import com.badlogic.gdx.Gdx;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;

import java.util.HashMap;

public class WyrTimeKeeper extends Wyr {

    protected float clock = 0;

    protected final HashMap<WyrActor, HashMap<String, Float>> ledger = new HashMap<>();

    public WyrTimeKeeper() {
        super(WyrType.AGNOSTIC);
    }

    public void increment(float delta) {
        clock += delta;
        if(clock >= 9999) clock = -9999;
    }

    public float diff(WyrActor actor) {
        // Correct math is only important for small values in
        // this case. Beyond a few seconds, "big number" is the
        // only thing that matters.
        if(ledger.containsKey(actor)) return Math.abs(ledger.get(actor).get("") - clock);
        return 999;
    }
    public float stateTime(WyrActor actor) {
        if(!ledger.containsKey(actor) ||
           !ledger.get(actor).containsKey("stateTime")) {
            recordStateTime(actor);
            return 0;
        }
        return Math.abs(ledger.get(actor).get("stateTime") - clock);
    }

    public void record(WyrActor actor) { record(actor, ""); }
    public void recordStateTime(WyrActor actor) { record(actor, "stateTime"); }
    public void record(WyrActor actor, String tag) {
        if(!ledger.containsKey(actor)) ledger.put(actor, new HashMap<>());
        ledger.get(actor).put(tag, clock);
    }

}
