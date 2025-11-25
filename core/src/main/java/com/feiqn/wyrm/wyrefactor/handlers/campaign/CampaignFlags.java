package com.feiqn.wyrm.wyrefactor.handlers.campaign;

public enum CampaignFlags {

    /*
     * All campaign event flags tracked here.
     * Verbiage should always reflect "true" being the non-default state.
     */

    /**
     * Recruitable units'
     * Death and Recruitment tracking
     */

    LEIF_DIED,

    ANTAL_DIED,
    ANTAL_RECRUITED,

    LYRA_DIED,
    LYRA_RECRUITED,

    ONE_DIED,
    ONE_RECRUITED,

    TOHNI_DIED,
    TOHNI_RECRUITED,

    D_DIED,
    D_RECRUITED,

    ANVIL_DIED,
    ANVIL_RECRUITED,


    /**
     * Flags for Stage 1A
     * Escaping the Border City
     */
    STAGE_1A_CLEARED,
    STAGE_1A_ANTAL_ESCAPED,
    STAGE_1A_LEIF_ESCAPED_WEST,
    STAGE_1A_LEIF_ESCAPED_EAST,


    /**
     * Stage 2A
     * Sneaking into the Walled City
     */
    STAGE_2A_UNLOCKED,
    STAGE_2A_CLEARED,
    STAGE_2A_SNEAK_IN_SUCCESSFULLY,


    /**
     * Stage 2B
     * Leif fled alone to the Capital City
     */
    STAGE_2B_UNLOCKED,
    STAGE_2B_CLEARED,


    /**
     * Stage 3A
     * Snuck into the Walled City successfully,
     * and got recruited by Tohni.
     */
    STAGE_3A_UNLOCKED,
    STAGE_3A_CLEARED,


    /**
     * Stage 3B
     * Got caught sneaking into the Walled City,
     * and got recruited by Anvil.
     */
    STAGE_3B_UNLOCKED,
    STAGE_3B_CLEARED,
}
