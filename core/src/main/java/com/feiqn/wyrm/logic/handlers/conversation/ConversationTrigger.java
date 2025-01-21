package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class ConversationTrigger {

    private final Array<UnitRoster> triggerUnits;
    private final Array<Vector2> triggerTiles;

    public ConversationTrigger() {
        triggerTiles = new Array<>();
        triggerUnits = new Array<>();
    }
}
