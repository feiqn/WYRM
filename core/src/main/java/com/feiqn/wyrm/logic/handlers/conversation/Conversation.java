package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class Conversation {

    private final WYRMGame game;

    private Array<UnitRoster> speakers;

    public Conversation(WYRMGame game) {
        this.game = game;

        speakers = new Array<>();
    }
}
