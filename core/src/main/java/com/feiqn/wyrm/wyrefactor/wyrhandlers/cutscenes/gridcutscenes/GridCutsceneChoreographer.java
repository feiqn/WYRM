package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes;

import com.badlogic.gdx.ScreenAdapter;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_CutsceneFrame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_CutsceneFrameChoreography;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.AbilityID;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.OLD_SimpleUnit;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.WyrCutsceneChoreographer;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.slides.GridCutsceneSlide;

public final class GridCutsceneChoreographer extends WyrCutsceneChoreographer {

    private GridCutsceneChoreographer() {}

    public static GridCutsceneSlide choreographFadeOut() {
        final GridCutsceneSlide frame = new GridCutsceneSlide();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.ChoreoType.FADE_OUT_TO_BLACK);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographTransitionScreen(ScreenAdapter screen) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.ChoreoType.SCREEN_TRANSITION);

        choreography.setScreenForTransition(screen);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographShortPause() {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();
        frame.choreograph(new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.ChoreoType.SHORT_PAUSE));
        slideshow.add(frame);
    }
    protected void choreographLinger() {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();
        frame.choreograph(new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.ChoreoType.LINGER));
        slideshow.add(frame);
    }
    protected void choreographUseAbility(OLD_SimpleUnit subject, AbilityID ability, OLD_SimpleUnit target) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.ChoreoType.ABILITY);

        choreography.setSubject(subject);
        choreography.setObject(target);
        choreography.setAbility(ability);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographBallistaAttack(OLD_SimpleUnit subject, OLD_SimpleUnit target) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.ChoreoType.BALLISTA_ATTACK);

        choreography.setSubject(subject);
        choreography.setObject(target);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographDespawn(OLD_SimpleUnit subject) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.ChoreoType.DESPAWN);

        choreography.setSubject(subject);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographSpawn(OLD_SimpleUnit subject, int column, int row) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.ChoreoType.SPAWN);

        choreography.setSubject(subject);
        choreography.setLocation(column,row);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographDeath(OLD_SimpleUnit subject) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.ChoreoType.UNIT_DEATH);

        choreography.setSubject(subject);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographMoveTo(OLD_SimpleUnit subject, int column, int row) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.ChoreoType.MOVE);

        choreography.setSubject(subject);
        choreography.setLocation(column, row);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographFocusOnLocation(int column, int row) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.ChoreoType.FOCUS_TILE);
        choreography.setLocation(column, row);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographFocusOnUnit(OLD_SimpleUnit focusCamera) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.ChoreoType.FOCUS_UNIT);
        choreography.setSubject(focusCamera);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographRevealVictCon(CampaignFlags flagID) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.ChoreoType.REVEAL_VICTCON);
        choreography.setVictConFlagID(flagID);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographEndCutscene() {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();
        frame.choreograph(new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.ChoreoType.END_OF_CUTSCENE));
        slideshow.add(frame);
    }
}
