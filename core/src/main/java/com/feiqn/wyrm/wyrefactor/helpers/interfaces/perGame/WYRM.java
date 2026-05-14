package com.feiqn.wyrm.wyrefactor.helpers.interfaces.perGame;

import java.util.HashMap;

public interface WYRM {

    HashMap<CampaignFlag, Boolean> flags = new HashMap<>();

    enum CampaignFlag {

        /*
         * All campaign event flags tracked here.
         * Verbiage should always reflect "true" being the non-default state.
         */

        /**
         * Recruitable units'
         * Death and Recruitment tracking
         */

        // TODO: programmatically assign constructed strings to
        //  hashMap derived from UnitRoster

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
         * Avatar flags.
         */

        AVATAR_CAN_SEE_STATS,
        AVATAR_CAN_SEE_DEXTERITY,
        AVATAR_CAN_SEE_MAGIC,
        AVATAR_CAN_SEE_LUCK,
        AVATAR_CAN_SEE_WISDOM,
        AVATAR_CAN_SEE_CONSTITUTION,
        AVATAR_CAN_SEE_GUILE,
        AVATAR_CAN_SEE_AGILITY,
        AVATAR_CAN_SEE_NAMES,

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

    enum StageList {
        STAGE_DEBUG,

        STAGE_1A, // The eastern border city is attacked, Leif flees and chooses to rescue Antal.
        STAGE_2A, // Leif and Antal try to sneak into the eastern walled city without being noticed.
        STAGE_3A, // After sneaking past the guards, Leif and Antal are approached by Tohni, a local
        //   mob boss who recruits the pair to perform grunt work in exchange for food and shelter.
        //   The group must help Tohni escape from an illegal gambling operation in which the guards
        //   have set up a sting.
//    STAGE_4A,
//    STAGE_5A,
//    STAGE_6A,

        STAGE_2B, // Leif did not save Antal in 1A, and instead fled alone south along the coast.
        //   TODO: are they waylaid or do they cut straight to the capital? I think they rush
        //   to the capital and then travel back north with the soldiers for some reason but idk why yet
//    STAGE_3B,
//    STAGE_4B,
//    STAGE_5B,
//    STAGE_6B,

        STAGE_3C, // Leif and Antal got caught trying to sneak into the walled city in 2A, and get recruited
        //   by Anvil, the captain of the local guard, to perform in a sting operation against Tohni


        CUTSCENE_1A_POST_LEIF_FOUND_ANTAL,

    }

    enum CutsceneID {

        // STORY A
        // 0A
        // TODO: gather supplies, poke antal, enter, leave


        // 1A
        CSID_1A_ANTAL_ESCAPING_ALIVE,
        CSID_1A_ANTAL_HELP_ME,
        CSID_1A_BALLISTA_1,
        CSID_1A_BALLISTA_2,
        CSID_1A_BALLISTA_LOOP,
        CSID_1A_BALLISTA_DEATH,
        CSID_1A_LEIF_FLEEING_ALONE,
        CSID_1A_LEIF_GETTING_IN_THE_BALLISTA,
        CSID_1A_LEIF_FIRED_BALLISTA,
        CSID_1A_LEIF_INEFFECTIVE_ATTACK,
        CSID_1A_LEIF_LEAVE_ME_ALONE,
        CSID_1A_LEIF_NEED_TO_ESCAPE,
        CSID_1A_LEIF_SAVED_ANTAL,

        CSID_1A_POST_LEIF_ANTAL_CAMPFIRE,
        CSID_1A_POST_LEIF_FOUND_ANTAL,
        CSID_1A_POST_LEIF_SHOULD_FIND_ANTAL,
        CSID_1A_POST_LEIF_ESCAPED_ALONE,


        // 2A
        CSID_2A_PRE_LEIFANTAL_GATESARECLOSED,

        CSID_2A_LEIFANTAL_STEALTHINTOCITY,

        CSID_2A_POST_ANVIL_CAUGHTYOUSNEAKINGIN,
        CSID_2A_POST_TOHNI_SAWYOUSNEAKPASTGUARDS,


        // 3A









        // ===============================
        // STORY BA
        // 1BA


        // STORY BB
        // 1BB







        // ===============================
        // STORY CA
        // 1CA


        // STORY CB
        // 1CB

    }

    enum Character {

        // Player
        Leif,
        Antal,
        Lyra,
        One,
        Tohni,
        D,
        Anvil,

        // Enemy
        Leon,
        Richard,
        Maria,
        Eric,
        Brea,

        // "Generic"
        Liam,
    }

    default void triggerFlag(CampaignFlag flag) {
        flags.put(flag, true);
    }

    default Boolean checkFlag(CampaignFlag flag) {
        return flags.getOrDefault(flag, false);
    }

}
