package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

import java.util.HashMap;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.WyrCutscene.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.InputState.LOCKED;

public class WyrCutsceneHandler extends WyrHandler {

    protected final Array<WyrCutscene> queuedCutscenes = new Array<>();
    protected Player cutscenePlayer;

    public WyrCutsceneHandler(Skin skin) {
        cutscenePlayer = new Player(skin);
    }


    public void addCutscene(WyrCutscene cutscene) {
        if(!queuedCutscenes.contains(cutscene, true)) queuedCutscenes.add(cutscene);
    }

    protected void startCutscene(WyrCutscene script) {
        if(cutsceneIsPlaying()) {
            queueCutscene(script);
            return;
        }
        isBusy = true;
        handlers.input().lock();
        handlers.clearEphemeral();
        handlers.map().standardize();

        // communicate w/ cs player to begin acting
        cutscenePlayer.playCutscene(script);
    }

    protected void queueCutscene(WyrCutscene script) {

    }

    public void endCutscene() {
        Gdx.app.log("CS Handle", "end scene");
        handlers.hud().standardize();
        isBusy = false;
        handlers.standardize();
        handlers.priority().parsePriority();
    }

    public void playNext() {
        if(!cutsceneIsPlaying()) return;
        cutscenePlayer.playNext();
    }

    public boolean cutsceneIsPlaying() {
        if(cutscenePlayer == null) return false;
        return (cutscenePlayer.getActiveCutscene() != null && cutscenePlayer.getActiveCutscene().continues());
    }

    @Override
    public boolean isBusy() { return super.isBusy() || cutsceneIsPlaying(); }

    /**
     * Checks
     */
    public void checkDeathTriggers(Character.Name roster) {
        for(WyrCutscene cutscene : queuedCutscenes) {
            cutscene.checkDeathTriggers(roster);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkAreaTriggers(Character.Name rosterID, TeamAlignment teamAlignment, Vector2 tileCoordinate) {
        for(WyrCutscene cutscene : queuedCutscenes) {
            cutscene.checkAreaTriggers(rosterID, tileCoordinate);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
        final boolean isPlayerUnit = teamAlignment == TeamAlignment.PLAYER;
        checkAreaTriggers(tileCoordinate, teamAlignment);
    }
    private void checkAreaTriggers(Vector2 tileCoordinate, TeamAlignment teamAlignment) {
        for(WyrCutscene cutscene : queuedCutscenes) {
            cutscene.checkAreaTriggers(tileCoordinate, teamAlignment);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkTurnTriggers(int turn) {
//        Gdx.app.log("CS handle", "checking turns");
//        Gdx.app.log("CS", "cutscenes.size = " + cutscenes.size);

        for(WyrCutscene cutscene : queuedCutscenes) {
            cutscene.checkTurnTriggers(turn);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkOtherCutsceneTriggers(Cutscene.ID otherID) {
        for(WyrCutscene cutscene : queuedCutscenes) {
            cutscene.checkOtherCutsceneTriggers(otherID);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    private void checkCombatStartTriggers(Character.Name rosterID, boolean unitIsAggressor) {
        for(WyrCutscene cutscene : queuedCutscenes) {
            cutscene.checkCombatStartTriggers(rosterID, unitIsAggressor);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkCombatStartTriggers(Character.Name attacker, Character.Name defender) {
        for(WyrCutscene cutscene : queuedCutscenes) {
            cutscene.checkCombatStartTriggers(attacker, true);
            cutscene.checkCombatStartTriggers(defender, false);
            cutscene.checkCombatStartTriggers(attacker, defender);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    private void checkCombatEndTriggers(Character.Name roster, boolean unitIsAggressor) {
        for(WyrCutscene cutscene : queuedCutscenes) {
            cutscene.checkCombatEndTriggers(roster, unitIsAggressor);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkCombatEndTriggers(Character.Name attacker, Character.Name defender) {
        for(WyrCutscene cutscene : queuedCutscenes) {
            cutscene.checkCombatEndTriggers(attacker, true);
            cutscene.checkCombatEndTriggers(defender, false);
            cutscene.checkCombatEndTriggers(attacker, defender);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }

    public static class Player {

        protected final Window layout;
        protected final Table portraitSubTable = new Table();
        protected final Window dialogWindow;
        protected WyrCutscene activeCutscene = null;

        protected final ProgressiveLabel focusedLabel = new ProgressiveLabel("Sample Text", WYRMGame.assets().menuLabelStyle);;
        protected final ProgressiveLabel doubleSpeakLabel = new ProgressiveLabel("Sample Text", WYRMGame.assets().menuLabelStyle);;

        private boolean inFullscreen = false;

        protected Player() {
            this(null);
        }

        public Player(Skin skin) {
            layout = new Window("", skin);
            focusedLabel.setWrap(true);
            dialogWindow = new Window("", skin);
            buildDialogWindow();

            dialogWindow.setFillParent(true);
//        layout.setFillParent(true);
//        dialogWindow.setDebug(true);
            layout.setDebug(true);

            // DEBUG
            layout.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                   return handlers.input().getInputMode() != LOCKED;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    if(handlers.input().getInputMode() != InputState.CUTSCENE) return;

                    if(focusedLabel.isActivelySpeaking()) {
                        focusedLabel.snapToEnd();
                    } else {
                        if(activeCutscene.continues()) {
                            playNext();
                        } else {
                            endScene();
                        }
                    }
                }
            });
        }

        public void playCutscene(WyrCutscene cutscene) {
            // parse script and act out upon gameStage
            activeCutscene = cutscene;

            portraitSubTable.clear();
            dialogWindow.clear();

            buildLayoutStandard();
            handlers.hud().buildForCutscene(layout);
            playNext();
        }

        public void playNext() {
            if(handlers.input().getInputMode() != InputState.CUTSCENE) handlers.input().setInputMode(InputState.CUTSCENE);
            if(!activeCutscene.continues() || activeCutscene == null) endScene();
            final Shot shot = activeCutscene.nextShot();

            if(shot.isChoreographed()) {
                // Choreographed shots should contain only their choreography,
                // and not stack choreo / lines / expressions in one shot.
                // Use multiple subsequent shots, and Player will auto-progress
                // after performing visual actions.
                parseChoreo(shot.getChoreo());
                return;
            }
            if(shot.isFullscreen()) {
                if(!inFullscreen) {
                    inFullscreen = true;
                    buildLayoutFullscreen();
                    handlers.hud().buildForFullscreenCutscene(activeCutscene.getBackgroundImage(), layout);
                }
            } else {
                if(inFullscreen) {
                    inFullscreen = false;
                    buildLayoutStandard();
                    handlers.hud().buildForCutscene(layout);
                }
                actOutShot(shot.getFocusedDirection());
            }
            focusedLabel.progressiveDisplay(shot.getFocusedDirection().getLine());
        }

        protected void endScene() {
//        layout.addAction(Actions.fadeOut(.3f));
            activeCutscene = null;
            // todo: stagger w/ timer
            handlers.cutscenes().endCutscene();
//        h().standardize();
//        h().priority().parsePriority();
        }

        protected void actOutShot(DialogDirection direction) {

        }

        protected void buildLayoutStandard() {
            layout.clearChildren();
//        layout.center();
//        layout.pad(Gdx.graphics.getHeight() * .025f);
//        layout.add(portraitSubTable).fill().uniform();
//        layout.row();
            layout.add(dialogWindow).expand().fill().height(Gdx.graphics.getWidth() * .2f);
            buildDialogWindow();
        }
        protected void buildLayoutFullscreen() {

        }

        protected void buildDialogWindow() {
            dialogWindow.clearChildren();
            dialogWindow.add(focusedLabel).expand().fillX().top().pad(20);
        }
        protected void buildDialogWindowForDoubleSpeak() {

        }

        protected void buildPerformanceStage() {

        }

        protected void parseChoreo(Choreography choreography) {
            if(choreography.getChoreoStage() == Cutscene.Choreography.ChoreoStage.WORLD) {
                handlers.input().lock();
                layout.addAction(Actions.fadeOut(.3f));
                parseWorldChoreo(choreography.getWorldInteraction());
            } else {
                parseDialogChoreo(choreography);
            }
            // todo: do stuff
        }

        protected void parseDialogChoreo(Choreography choreo) {
            // CharacterPortraits on PerformanceStage act, then call playNext()
        }

        protected void parseWorldChoreo(WyrInteraction interaction) {
            // InteractionHandler acts out choreo then priority continues
            // CS after being called for parse.
        }

        protected void endChoreo() {
            layout.addAction(Actions.fadeIn(.3f));
            playNext();
        }

        public @Null WyrCutscene getActiveCutscene() { return activeCutscene; }

        private static class PerformanceStage {

            private final HashMap<Cutscene.HorizontalPosition, Float> coordinates = new HashMap<>();

            private void buildCoordinates() {
                final float eighth = Gdx.graphics.getWidth() * .125f;
                coordinates.clear();
                coordinates.put(Cutscene.HorizontalPosition.FAR_LEFT, eighth);
            }

        }

        public static class CharacterPortrait extends WyrActor {
            private Character.Name characterID = null;
            private Character.Expression expression = null;
            private Cutscene.HorizontalPosition position = Cutscene.HorizontalPosition.LEFT;
            private boolean            facingLeft    = false;
            private String             preferredName;
            private String             line;

            public CharacterPortrait(Character.Name characterID) {
                this.characterID = characterID;
            }

            public void expression(Character.Expression expression) {
                this.expression = expression;
            }
            public void position(Cutscene.HorizontalPosition position) {
                this.position = position;
            }
            public void faceLeft(boolean faceLeft) {
                if(facingLeft == faceLeft) return;
                this.facingLeft = faceLeft;
//            imageDrawable.setScaleX(faceLeft ? -1 : 1);
            }
            public void preferredName(String name) {
                this.preferredName = name;
            }
            public void line(String line) {
                this.line = line;
            }

            public Character.Name getCharacter() {
                return characterID;
            }
            public boolean isFacingLeft() {
                return facingLeft;
            }
            public Character.Expression getExpression() {
                return expression;
            }
            public Cutscene.HorizontalPosition getPosition() {
                return position;
            }
            public String getPreferredName() {
                return preferredName;
            }
            public String getLine() {
                return line;
            }
        }


    }

}
