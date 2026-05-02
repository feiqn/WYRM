package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.grid;

import com.badlogic.gdx.ScreenAdapter;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_CutsceneFrame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_CutsceneFrameChoreography;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.math.stats.rpg.rpgrid.RPGridAbilityID;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.OLD_SimpleUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.campaign.wyrm.CampaignFlags;

public final class GridCutsceneChoreographer extends com.feiqn.wyrm.wyrefactor.helpers.Subjectivity<RPGridUnit> implements com.feiqn.wyrm.wyrefactor.helpers.Wyr {


    public static void choreographTransitionScreen(ScreenAdapter screen) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.SCREEN_TRANSITION);

        choreography.setScreenForTransition(screen);

        frame.choreograph(choreography);

//        slideshow.add(frame);
    }
    public static void choreographShortPause() {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();
        frame.choreograph(new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.SHORT_PAUSE));
//        slideshow.add(frame);
    }
    public static void choreographLinger() {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();
        frame.choreograph(new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.LINGER));
//        slideshow.add(frame);
    }
    public static void choreographUseAbility(OLD_SimpleUnit subject, RPGridAbilityID ability, OLD_SimpleUnit target) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.ABILITY);

        choreography.setSubject(subject);
        choreography.setObject(target);
        choreography.setAbility(ability);

        frame.choreograph(choreography);

//        slideshow.add(frame);
    }
    public static void choreographBallistaAttack(OLD_SimpleUnit subject, OLD_SimpleUnit target) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.BALLISTA_ATTACK);

        choreography.setSubject(subject);
        choreography.setObject(target);

        frame.choreograph(choreography);

//        slideshow.add(frame);
    }
    public static void choreographDespawn(OLD_SimpleUnit subject) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.DESPAWN);

        choreography.setSubject(subject);

        frame.choreograph(choreography);

//        slideshow.add(frame);
    }
    public static void choreographSpawn(OLD_SimpleUnit subject, int column, int row) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.SPAWN);

        choreography.setSubject(subject);
        choreography.setLocation(column,row);

        frame.choreograph(choreography);

//        slideshow.add(frame);
    }
    public static void choreographDeath(OLD_SimpleUnit subject) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.UNIT_DEATH);

        choreography.setSubject(subject);

        frame.choreograph(choreography);

//        slideshow.add(frame);
    }
    public static void choreographMoveTo(OLD_SimpleUnit subject, int column, int row) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.MOVE);

        choreography.setSubject(subject);
        choreography.setLocation(column, row);

        frame.choreograph(choreography);

//        slideshow.add(frame);
    }
    public static void choreographFocusOnLocation(int column, int row) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.FOCUS_TILE);
        choreography.setLocation(column, row);

        frame.choreograph(choreography);

//        slideshow.add(frame);
    }
    public static void choreographFocusOnUnit(OLD_SimpleUnit focusCamera) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.FOCUS_UNIT);
        choreography.setSubject(focusCamera);

        frame.choreograph(choreography);

//        slideshow.add(frame);
    }
    public static void choreographRevealVictCon(CampaignFlags flagID) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.REVEAL_VICTCON);
        choreography.setVictConFlagID(flagID);

        frame.choreograph(choreography);

//        slideshow.add(frame);
    }
    public static void choreographEndCutscene() {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();
        frame.choreograph(new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.END_OF_CUTSCENE));
//        slideshow.add(frame);
    }
}
