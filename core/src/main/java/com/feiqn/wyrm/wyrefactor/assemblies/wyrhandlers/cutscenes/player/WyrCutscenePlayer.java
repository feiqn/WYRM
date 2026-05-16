package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.choreography.WyrCutsceneChoreography;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.slides.ProgressiveLabel;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.slides.WyrCutsceneShot;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.WyrCutscene;

import java.util.HashMap;

public class WyrCutscenePlayer extends WyrHandler {

    protected final Table layout = new Table();
    protected final Table portraitSubTable = new Table();
    protected final Window dialogWindow;
    protected WyrCutscene activeCutscene = null;

    protected final ProgressiveLabel focusedLabel = new ProgressiveLabel("Sample Text", WYRMGame.assets().menuLabelStyle);;
    protected final ProgressiveLabel doubleSpeakLabel = new ProgressiveLabel("Sample Text", WYRMGame.assets().menuLabelStyle);;

    private boolean inFullscreen = false;

    protected WyrCutscenePlayer() {
        this(null, null);
    }

    public WyrCutscenePlayer(MetaHandler metaHandler, Skin skin) {
        super(metaHandler);
//        focusedLabel.getStyle().font.getData().markupEnabled = true;
        focusedLabel.setWrap(true);
        dialogWindow = new Window("", skin);
//        dialogWindow.top().left();
        buildDialogWindow();

        dialogWindow.setFillParent(true);
//        layout.setFillParent(true);
//        dialogWindow.setDebug(true);
        layout.setDebug(true);

        // DEBUG
        layout.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                return metaHandler.input().getInputMode() != InputMode.LOCKED;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                if(metaHandler.input().getInputMode() != InputMode.CUTSCENE) return;

//                if(game.activeOLDGridScreen.getInputMode() == OLD_GridScreen.OLD_InputMode.LOCKED) return;
//                if(choreographing) return;
//
//                if(dialogLabel.isActivelySpeaking()) {
//                    dialogLabel.snapToEnd();
//                } else {
//                    if(cutsceneScript.continues()) {
                        playNext();
//                    } else {
//                        fadeOutAndEnd();
//                    }
//                }
            }
        });
    }

    public void playCutscene(WyrCutscene cutscene) {
        // parse script and act out upon gameStage
        h().input().lock();
        isBusy = true;
        activeCutscene = cutscene;

        portraitSubTable.clear();
        dialogWindow.clear();

        buildLayoutStandard();
        h().hud().buildForCutscene(layout);
        playNext();
    }

    public void playNext() {
        h().input().setInputMode(InputMode.CUTSCENE);
//        if(activeCutscene == null) return;
        if(!activeCutscene.continues() || activeCutscene == null) endScene();
//        if(activeCutscene.nextShot() == null) return;
        final WyrCutsceneShot shot = activeCutscene.nextShot();
//        if(shot == null) throw new GdxRuntimeException("null shot");

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
                h().hud().buildForFullscreenCutscene(activeCutscene.getBackgroundImage(), layout);
            }
        } else {
            if(inFullscreen) {
                inFullscreen = false;
                buildLayoutStandard();
                h().hud().buildForCutscene(layout);
            }
            actOutShot(shot.getFocusedDirection());
        }
        focusedLabel.progressiveDisplay(shot.getFocusedDirection().getLine());
    }

    protected void endScene() {
        Gdx.app.log("CS Player", "end scene");
        layout.addAction(Actions.fadeOut(.3f));
        isBusy = false;
        activeCutscene = null;
        // todo: stagger w/ timer
        h().standardize();
        h().priority().parsePriority();
    }

    protected void actOutShot(WyrCutsceneShot.DialogDirection direction) {

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

    protected void parseChoreo(WyrCutsceneChoreography choreography) {
        if(choreography.getChoreoStage() == WyrCutsceneChoreography.ChoreoStage.WORLD) {
            h().input().lock();
            layout.addAction(Actions.fadeOut(.3f));
            parseWorldChoreo(choreography.getWorldInteraction());
        } else {
            parseDialogChoreo(choreography);
        }
        // todo: do stuff
    }

    protected void parseDialogChoreo(WyrCutsceneChoreography choreo) {
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

    public WyrCutscene getActiveCutscene() { return activeCutscene; }

    private static class PerformanceStage {

         private final HashMap<HorizontalPosition, Float> coordinates = new HashMap<>();

         private void buildCoordinates() {
             final float eighth = Gdx.graphics.getWidth() * .125f;
             coordinates.clear();
             coordinates.put(HorizontalPosition.FAR_LEFT, eighth);
         }

    }

    public static class CharacterPortrait extends WyrActor {
        private CharacterID        characterID   = null;
        private Expression         expression    = Expression.DETERMINED;
        private HorizontalPosition position      = HorizontalPosition.LEFT;
        private boolean            facingLeft    = false;
        private String             preferredName;
        private String             line;

        public CharacterPortrait(CharacterID characterID) {
            super(ActorType.UI);
            this.characterID = characterID;
        }

        public void expression(Expression expression) {
            this.expression = expression;
        }
        public void position(HorizontalPosition position) {
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

        public CharacterID getCharacter() {
            return characterID;
        }
        public boolean isFacingLeft() {
            return facingLeft;
        }
        public Expression getExpression() {
            return expression;
        }
        public HorizontalPosition getPosition() {
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
