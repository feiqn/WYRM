package com.feiqn.wyrm.OLD_DATA.models.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Pool;

public class PauseAction extends Action {

    private boolean complete;
    private float duration;

    public PauseAction(float duration) {
        this.duration = duration;
        complete = false;
        Gdx.app.log("PauseAction", "start");
    }

    public boolean act(float delta) {
        if(complete) return true;
        Pool pool = getPool();
        setPool(null);
        try {
            duration -= delta;
            if(duration <= 0) {
                Gdx.app.log("PauseAction", "done");
                complete = true;
                return true;
            }
            return complete;
        } finally {
            setPool(pool);
        }
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

}
