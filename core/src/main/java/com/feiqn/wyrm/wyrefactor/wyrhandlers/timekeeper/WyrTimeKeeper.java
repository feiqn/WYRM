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
        if(ledger.containsKey(actor)) {
            final float returnValue = Math.abs(ledger.get(actor) - clock);
            ledger.put(actor, clock);
            return returnValue;
        } else {
            ledger.put(actor, clock);
            return 999;
        }
    }

}
