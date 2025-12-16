package com.feiqn.wyrm.wyrefactor.wyrhandlers.timekeeper;

import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;

import java.util.HashMap;
import java.util.Map;

public class WyrTimeKeeper extends Wyr {

    protected float clock = 0;
    protected final Map<WyrActor, Float> ledger = new HashMap<>();

    public WyrTimeKeeper() {
        super(WyrType.AGNOSTIC);
    }

    public void increment(float delta) {
        clock += delta;
        if(clock > 9999) clock = -9999;
    }

    public float diff(WyrActor actor) {
        // Correct math is only important for small values in
        // this case. Beyond a few seconds, "big number" is the
        // only thing that matters.
        if(ledger.containsKey(actor)) return Math.abs(ledger.get(actor) - clock);
        return 999;
    }

    public void record(WyrActor actor) {
        ledger.put(actor, clock);
    }

}
