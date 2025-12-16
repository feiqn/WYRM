package com.feiqn.wyrm.logic.handlers.ai.actions;

public enum ActionType {
    MOVE_ACTION,
    ATTACK_ACTION,
    WAIT_ACTION,
    USE_ITEM_ACTION,
    USE_ABILITY_ACTION, // Mount/dismount should be made into either an ability, or an RPGClassSpecialAction()
    WORLD_INTERACT_ACTION,

    // These two should be deprecated.
    // Passing turns is only for IronMode,
    // Escaping is now handled by World Interact
    PASS_ACTION,
    ESCAPE_ACTION
}
