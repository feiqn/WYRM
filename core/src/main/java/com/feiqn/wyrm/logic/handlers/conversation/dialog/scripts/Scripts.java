package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.DScript_1A_Antal_HelpMe;

public class Scripts {

    static public <T extends DialogScript> T script(Class<T> ID) {
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
