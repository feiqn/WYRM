package com.feiqn.wyrm.logic.handlers.ai;

public enum AIPersonality {
    // For the last time, no, not like an LLM.
    // Sol isn't here, stop asking.

    // TODO: extrapolate this into a data class
    //  like choreo / triggers, apply things like
    //  goal tiles, priority targets, and patrol
    //  points there instead.

    DEFENSIVE,
    AGGRESSIVE,
    RECKLESS,
    STILL,
    FLANKING,
    PATROLLING,
    LOS_AGGRO,
    LOS_FLEE,
    PLAYER,
    TARGET_TILE,
    TARGET_UNIT,
    TARGET_OBJECT,
    ESCAPE
}
