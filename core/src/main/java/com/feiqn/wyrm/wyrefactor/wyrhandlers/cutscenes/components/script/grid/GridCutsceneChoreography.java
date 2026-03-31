package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid;

import com.feiqn.wyrm.OLD_DATA.models.unitdata.AbilityID;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.WyrCutsceneChoreography;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.GridCutsceneSlide;

public final class GridCutsceneChoreography extends WyrCutsceneChoreography<GridActor> {

    private AbilityID ability;

    public GridCutsceneChoreography(WorldChoreoType worldChoreoType) { super(worldChoreoType); }
    public GridCutsceneChoreography(DialogChoreoType dialogChoreoType) { super(dialogChoreoType); }

    @Override
    public WyrType getWyrType() { return WyrType.GRIDWORLD; }

    // TODO:
    //  my buddy jason is gonna teach me how to read

//    public final static class Choreographer {
//
//        // TODO:
//        //  switch to using pooled values, probably,
//        //  just as soon as I learn how to do that.
//
//        /**
//         * Dialog Choreography:
//         * These are things that happen while the conversation window is visible,
//         * typically manipulating character portraits.
//         */
//        public static class Dialog {
//
//
//
//        }
//
//        /**
//         * World Choreography:
//         * These happen after removing the conversation window,
//         * typically manipulating units, props, and world states.
//         */
//        public static class World {
//
//            public static GridCutsceneChoreography fadeOut() {
//                // fades entire screen to black.
//                // useful for a transition, or to censor
//                // an implied scene.
//                return new GridCutsceneChoreography(FADE_OUT_TO_BLACK);
//            }
//
//        }
//
//    }
}
