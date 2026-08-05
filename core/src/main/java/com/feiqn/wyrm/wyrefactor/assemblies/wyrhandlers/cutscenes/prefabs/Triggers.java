package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.prefabs;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.WyrCutscene.Trigger;

public final class Triggers {

    private Triggers() {}

    public static Trigger trigger() {
        // I basically copied this from Gdx.Actions,
        // idk how pools work.
        Pool<Trigger> pool = Pools.get(Trigger.class);
        Trigger t = pool.obtain();
        t.setPool(pool);
        return t;
    }



}
