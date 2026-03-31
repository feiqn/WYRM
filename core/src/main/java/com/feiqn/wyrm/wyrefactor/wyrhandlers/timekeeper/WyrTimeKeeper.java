package com.feiqn.wyrm.wyrefactor.wyrhandlers.timekeeper;

import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;

import java.util.HashMap;

public final class WyrTimeKeeper implements Wyr {

    private float clock = 0;

    private final HashMap<WyrActor, HashMap<String, Float>> ledger = new HashMap<>();

    public WyrTimeKeeper() {}

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
