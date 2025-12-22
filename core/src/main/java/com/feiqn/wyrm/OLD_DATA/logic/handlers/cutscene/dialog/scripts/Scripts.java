package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.CutsceneScript;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during.DScript_1A_Antal_HelpMe;

public class Scripts {

    static public <T extends CutsceneScript> T script(Class<T> ID) {
        Pool<T> pool = Pools.get(ID);
        return pool.obtain();
    }

    static public class Antal {

        static public DScript_1A_Antal_HelpMe HelpMe() {
            return script(DScript_1A_Antal_HelpMe.class);
        }

    }

    static public class Leif {

    }

}
