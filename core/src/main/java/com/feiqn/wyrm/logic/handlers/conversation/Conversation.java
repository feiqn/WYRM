package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class Conversation {

    public enum Background {
        NONE,

        EXTERIOR_FOREST_DAY,
        EXTERIOR_FOREST_NIGHT,

        EXTERIOR_BEACH_DAY,
        EXTERIOR_BEACH_NIGHT,

        EXTERIOR_STREETS_DIRT_DAY,
        EXTERIOR_STREETS_DIRT_NIGHT,

        EXTERIOR_STREETS_STONE_DAY,
        EXTERIOR_STREETS_STONE_NIGHT,

        EXTERIOR_CAMP_WOODS_DAY,
        EXTERIOR_CAMP_WOODS_NIGHT,

        INTERIOR_STONE_TORCHLIGHT,
        INTERIOR_STONE_DAY,
        INTERIOR_STONE_NIGHT,

        INTERIOR_WOOD_FIRELIGHT,
        INTERIOR_WOOD_DAY,
        INTERIOR_WOOD_NIGHT,

    }

    private Background background;

    private final WYRMGame game;

    private Array<UnitRoster> speakers;

    public Conversation(WYRMGame game) {
        this.game = game;

        speakers = new Array<>();
    }
}
