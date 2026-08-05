package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Cutscene.Choreography.ChoreoStage;
import org.jetbrains.annotations.NotNull;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.WyrCutscene.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Cutscene.HorizontalPosition.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.InputMode.*;

public class WyrCutsceneHandler extends WyrHandler {

    protected final Array<WyrCutscene> loadedCutscenes = new Array<>();
    protected final Array<Cutscene.ID> queuedCutscenes = new Array<>();
    protected final Player cutscenePlayer;

    public WyrCutsceneHandler(Skin skin) {
        cutscenePlayer = new Player(skin);
    }

    public void addCutscene(WyrCutscene cutscene) {
        if(!loadedCutscenes.contains(cutscene, true)) loadedCutscenes.add(cutscene);
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
        if(!queuedCutscenes.contains(script.getCutsceneID(), true)) queuedCutscenes.add(script.getCutsceneID());
    }

    public void endCutscene() {
        cutscenePlayer.layout.addAction(Actions.sequence(
            Actions.fadeOut(.3f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    isBusy = false;
                    cutscenePlayer.activeCutscene = null;
                    handlers.standardizeParse();
                }
            }))
        );
    }

    public void continueScene() {
        if(!cutsceneIsPlaying()) {
            endCutscene();
            return;
        }
        cutscenePlayer.playNext();
    }

    public void playFromQueue() {
        if(queuedCutscenes.isEmpty()) {
            handlers.standardizeParse();
            return;
        }

        final Cutscene.ID queuedID = queuedCutscenes.get(0);

        for(WyrCutscene cs : loadedCutscenes) {
            if(cs.getCutsceneID() == queuedID) {
                queuedCutscenes.removeIndex(0);
                startCutscene(cs);
                return;
            }
        }
    }

    public boolean cutsceneInQueue() {
        return !queuedCutscenes.isEmpty();
    }

    public boolean cutsceneIsPlaying() {
        if(cutscenePlayer == null) return false;
        return (cutscenePlayer.getActiveCutscene() != null && cutscenePlayer.getActiveCutscene().continues());
    }

    public boolean isChoreographing() { return cutscenePlayer.choreographing; }

    @Override
    public boolean isBusy() { return super.isBusy() || cutsceneIsPlaying(); }

    /**
     * Trigger checks
     */
    public void checkDeathTriggers(Character.Name roster) {
        for(WyrCutscene cutscene : loadedCutscenes) {

            if(cutscene.isDefused() || cutscene.hasPlayed()) continue;

            for(Trigger def : cutscene.getDefuseTriggers()) {
                if(def.checkDeathTrigger(roster)) {
                    def.fire();
                    cutscene.incrementDefuseCount();
                }
            }

            if(cutscene.isDefused()) continue;

            for(Trigger trigger : cutscene.getTriggers()) {
                if(trigger.checkDeathTrigger(roster)) {
                    trigger.fire();
                    cutscene.incrementTriggerCount();
                }
            }

            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkDeathTriggers(TeamAlignment teamAlignment) {
        for(WyrCutscene cutscene : loadedCutscenes) {

            if(cutscene.isDefused() || cutscene.hasPlayed()) continue;

            for(Trigger def : cutscene.getDefuseTriggers()) {
                if(def.checkDeathTrigger(teamAlignment)) {
                    def.fire();
                    cutscene.incrementDefuseCount();
                }
            }

            if(cutscene.isDefused()) continue;

            for(Trigger trigger : cutscene.getTriggers()) {
                if(trigger.checkDeathTrigger(teamAlignment)) {
                    trigger.fire();
                    cutscene.incrementTriggerCount();
                }
            }

            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkAreaTriggers(Character.Name rosterID, TeamAlignment teamAlignment, Vector2 tileCoordinate) {
        for(WyrCutscene cutscene : loadedCutscenes) {

            if(cutscene.isDefused() || cutscene.hasPlayed()) continue;

            for(Trigger def : cutscene.getDefuseTriggers()) {
                if(def.checkAreaTrigger(rosterID, tileCoordinate)) {
                    def.fire();
                    cutscene.incrementDefuseCount();
                }
            }

            if(cutscene.isDefused()) continue;

            for(Trigger trigger : cutscene.getTriggers()) {
                if(trigger.checkAreaTrigger(rosterID, tileCoordinate)) {
                    trigger.fire();
                    cutscene.incrementTriggerCount();
                }
            }

            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
        checkAreaTriggers(tileCoordinate, teamAlignment);
    }
    private void checkAreaTriggers(Vector2 tileCoordinate, TeamAlignment teamAlignment) {
        for(WyrCutscene cutscene : loadedCutscenes) {

            if(cutscene.isDefused() || cutscene.hasPlayed()) continue;

            for(Trigger def : cutscene.getDefuseTriggers()) {
                if(def.checkAreaTrigger(tileCoordinate, teamAlignment)) {
                    def.fire();
                    cutscene.incrementDefuseCount();
                }
            }

            if(cutscene.isDefused()) continue;

            for(Trigger trigger : cutscene.getTriggers()) {
                if(trigger.checkAreaTrigger(tileCoordinate, teamAlignment)) {
                    trigger.fire();
                    cutscene.incrementTriggerCount();
                }
            }

            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkFlagTriggers(Campaign.FlagID flagID) {
        for(WyrCutscene cutscene : loadedCutscenes) {

            if(cutscene.isDefused() || cutscene.hasPlayed()) continue;

            for(Trigger def : cutscene.getDefuseTriggers()) {
                if(def.checkCampaignFlagTrigger(flagID)) {
                    def.fire();
                    cutscene.incrementDefuseCount();
                }
            }

            if(cutscene.isDefused()) continue;

            for(Trigger trigger : cutscene.getTriggers()) {
                if(trigger.checkCampaignFlagTrigger(flagID)) {
                    trigger.fire();
                    cutscene.incrementTriggerCount();
                }
            }

            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkCSIDTriggers(Cutscene.ID otherID) {
        for(WyrCutscene cutscene : loadedCutscenes) {

            if(cutscene.isDefused() || cutscene.hasPlayed()) continue;

            for(Trigger def : cutscene.getDefuseTriggers()) {
                if(def.checkOtherCutsceneTrigger(otherID)) {
                    def.fire();
                    cutscene.incrementDefuseCount();
                }
            }

            if(cutscene.isDefused()) continue;

            for(Trigger trigger : cutscene.getTriggers()) {
                if(trigger.checkOtherCutsceneTrigger(otherID)) {
                    trigger.fire();
                    cutscene.incrementTriggerCount();
                }
            }

            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkTurnTriggers(int turn) {
        for(WyrCutscene cutscene : loadedCutscenes) {

            if(cutscene.isDefused() || cutscene.hasPlayed()) continue;

            for(Trigger defuseTrigger : cutscene.getDefuseTriggers()) {
                if(defuseTrigger.checkTurnTrigger(turn)) {
                    defuseTrigger.fire();
                    cutscene.incrementDefuseCount();
                }
            }

            if(cutscene.isDefused()) continue;

            for(Trigger trigger : cutscene.getTriggers()) {
                if(trigger.checkTurnTrigger(turn)) {
                    trigger.fire();
                    cutscene.incrementTriggerCount();
                }
            }

            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkCombatStartTriggers(Character.Name attacker, TeamAlignment attackerTeam, Character.Name defender, TeamAlignment defenderTeam) {
        checkCombatTriggers(true, attacker, attackerTeam, defender, defenderTeam);
    }
    public void checkCombatEndTriggers(Character.Name attacker, TeamAlignment attackerTeam, Character.Name defender, TeamAlignment defenderTeam) {
        checkCombatTriggers(false, attacker, attackerTeam, defender, defenderTeam);
    }
    private void checkCombatTriggers(boolean beforeVisual, Character.Name attacker, TeamAlignment attackerTeam, Character.Name defender, TeamAlignment defenderTeam) {
        for(WyrCutscene cutscene : loadedCutscenes) {

            if(cutscene.isDefused() || cutscene.hasPlayed()) continue;

            for(Trigger def : cutscene.getDefuseTriggers()) {
                if(def.checkCombatTrigger(beforeVisual, attacker, attackerTeam, defender, defenderTeam)) {
                    def.fire();
                    cutscene.incrementDefuseCount();
                }
            }

            if(cutscene.isDefused()) continue;

            for(Trigger trigger : cutscene.getTriggers()) {
                if(trigger.checkCombatTrigger(beforeVisual, attacker, attackerTeam, defender, defenderTeam)) {
                    trigger.fire();
                    cutscene.incrementTriggerCount();
                }
            }

            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }

    /**
     * Cutscene player
     */
    public static class Player {

        private final Performance performance = new Performance();

        protected static final Table layout = new Table();
        protected static final Table nameLayer = new Table().bottom();
        protected final Window dialogWindow;
        protected final Window nameWindow;

        protected static final Label nameLabel = new Label("?", WYRMGame.assets().menuLabelStyle);

        protected WyrCutscene activeCutscene = null;

        protected final ProgressiveLabel focusedLabel = new ProgressiveLabel("Sample Text", WYRMGame.assets().menuLabelStyle);
        protected final ProgressiveLabel doubleSpeakLabel = new ProgressiveLabel("Sample Text", WYRMGame.assets().menuLabelStyle);

        private boolean inFullscreen = false;
        private boolean layoutVisible = false;
        private boolean choreographing = false;

        public Player(Skin skin) {
            focusedLabel.setWrap(true);
            dialogWindow = new Window("", skin);
            nameWindow = new Window("", skin);

            nameLabel.setColor(Color.GOLDENROD);

            buildDialogWindow();
            buildNameWindow();

//            layout.setDebug(true);

            layout.setColor(1,1,1,0);

            layout.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                   return handlers.input().getInputMode() != LOCKED;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    if(handlers.input().getInputMode() != InputMode.CUTSCENE) return;

                    if(focusedLabel.isActivelySpeaking()) {
                        focusedLabel.snapToEnd();
                    } else {
                        if(activeCutscene.continues()) {
                            playNext();
                        } else {
                            handlers.cutscenes().endCutscene();
                        }
                    }
                }
            });
        }

        public void playCutscene(WyrCutscene cutscene) {
            // parse script and act out upon gameStage
            activeCutscene = cutscene;

            dialogWindow.clear();

            buildLayoutStandard();
            handlers.hud().buildForCutscene(layout);
            playNext();
        }

        public void playNext() {
            if(handlers.input().getInputMode() != InputMode.CUTSCENE) handlers.input().setInputMode(InputMode.CUTSCENE);
            if(activeCutscene == null) {
                handlers.cutscenes().endCutscene();
                return;
            }
            if(!activeCutscene.continues()) {
                handlers.cutscenes().endCutscene();
                return;
            }

            final Shot shot = activeCutscene.nextShot();

            // Choreo handling
            if(shot.isChoreographed()) {
                // Choreographed shots should contain only their choreography,
                // and not stack choreo / lines / expressions in one shot.
                // Use multiple subsequent shots, and Player will auto-progress
                // after performing visual actions.
                parseChoreo(shot.getChoreo());
                return;
            } else if(!layoutVisible) {
                layout.addAction(Actions.fadeIn(.1f));
            }
            choreographing = false;

            // Fullscreen catch handle
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
                final CharacterPortrait dC = Performance.direct(shot.getFocusedDirection());
                updateNameLabel(dC);
            }

            focusedLabel.progressiveDisplay(shot.getFocusedDirection().getLine());
        }

        protected void buildLayoutStandard() {
            layout.clearChildren();
            buildDialogWindow();
            buildNameWindow();

            final Stack upperStack = new Stack();

            upperStack.add(performance.getView());
            upperStack.add(nameLayer);

            layout.add(upperStack)
                .bottom()
                .uniform()
            ;
            layout.row();

            layout.add(dialogWindow)
                .expand()
                .fill()
                .uniform()
            ;
        }
        protected void buildLayoutFullscreen() {

        }

        protected void buildDialogWindow() {
            dialogWindow.clearChildren();
            dialogWindow.add(focusedLabel)
                .expand()
                .fillX()
                .top()
                .pad(20)
            ;
        }
        protected void buildDialogWindowForDoubleSpeak() {

        }

        protected void buildNameWindow() {
            nameWindow.clearChildren();
            nameWindow.add(nameLabel)
                .padLeft(20)
                .padRight(20)
                .padTop(5)
                .padBottom(5)
            ;

            nameLayer.clearChildren();
            nameLayer.add(nameWindow);
        }

        public void updateNameLabel(@NotNull CharacterPortrait cP) {
            nameLabel.setText(cP.getPreferredName());

            switch(cP.position) {
                case FAR_LEFT:
                case LEFT:
                case LEFT_OF_CENTER:
                    nameLayer.left();
                    break;

                case CENTER:
                    nameLayer.center();
                    break;

                case RIGHT_OF_CENTER:
                case RIGHT:
                case FAR_RIGHT:
                    nameLayer.right();
            }
        }

        /**
         * Choreo
         */
        protected void parseChoreo(Choreography choreography) {
            handlers.input().lock();
            choreographing = true;
            if(choreography.getChoreoStage() == ChoreoStage.WORLD) {
                if(layoutVisible) {
                    layout.addAction(Actions.sequence(
                        Actions.fadeOut(.2f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                handlers.interactions().parseInteraction(choreography.getWorldInteraction());
                            }
                        })
                    ));
                } else {
                    handlers.interactions().parseInteraction(choreography.getWorldInteraction());
                }
            } else {
                parseDialogChoreo(choreography);
            }
        }

        protected void parseDialogChoreo(Choreography choreo) {
            // CharacterPortraits on PerformanceStage act, then call playNext()

            switch(choreo.dialogChoreoType) {

                case PAUSE_SHORT:
                case PAUSE_LONG:
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            playNext();
                        }
                    }, 1);
                    break;

                case WINCON_REVEAL:
                    handlers.register().revealWinCon(choreo.associatedCampaignFlag);
                    playNext();
                    break;

                default:
                    break;
            }

        }


        /**
         * etc.
         */
        public @Null WyrCutscene getActiveCutscene() { return activeCutscene; }

        /**
         * Helper classes
         */
        private static class Performance {

            private static final Stack viewStack = new Stack();

            private static final Table rearStage = new Table().bottom();
            private static final Table midStage = new Table().bottom();
            private static final Table frontStage = new Table().bottom();
            private static final Container<Image> centerStage = new Container<>();

            private static final Array<CharacterPortrait> characters = new Array<>();

            public Performance() {
                viewStack.add(rearStage);
                viewStack.add(midStage);
                viewStack.add(frontStage);
                viewStack.add(centerStage);
            }

            public Stack getView() {
                return viewStack;
            }

            public static @Null CharacterPortrait direct(DialogDirection direction) {
                if(direction == null) return null;
                if(!characterIsOnStage(direction.getCharacterID())) addNewCharacter(direction);
                final CharacterPortrait rV = directCharacter(direction);
                buildStages();
                return rV;
            }

            private static void addNewCharacter(DialogDirection direction) {
                final CharacterPortrait nC = new CharacterPortrait(direction.getCharacterID());
                characters.add(nC);

                // TODO:
                //  - check how many characters are already on stage, and where
                //  - check if nC position is occupied,
                //  - - fade out or move to back table if so
                //  - check if table needs to be rebuilt or reoriented,
                //  - - based on number of characters in scene

            }

            private static CharacterPortrait directCharacter(DialogDirection direction) {
                final int charIndex = characterIndex(direction.getCharacterID());
                final CharacterPortrait cP = characters.get(charIndex);

                // TODO:
                //  if null && null, search for next open space in set priority,
                //  only cycle back to Left if the whole stage is full.
                shufflePositions(cP, (direction.getPosition() == null && cP.position == null ? LEFT : direction.getPosition()));

                if(direction.getExpression() != null && direction.getExpression() != cP.getExpression()) {
                    cP.setExpression(direction.getExpression());
                }

                if(direction.shouldFlipFacing()) cP.flip();

                if(direction.getPreferredName() != null) {
                    cP.setPreferredName(direction.getPreferredName());
                }

                return cP;
            }

            private static void shufflePositions(CharacterPortrait focusedChar, @Null Cutscene.HorizontalPosition targetPosition) {
                if((focusedChar.position != null && focusedChar.position == targetPosition) || targetPosition == null ) return;

                CharacterPortrait oldChar = null;
                for(CharacterPortrait cX : characters) {
                    if(cX.position == targetPosition) {
                        oldChar = cX;
                        break;
                    }
                }
                if(oldChar != null) {
                    oldChar.remove();
                }

//                focusedChar.addAction(Actions.fadeOut(.11f));
                focusedChar.position = targetPosition;
            }

            private static void buildStages() {
                buildRearStage();
                buildMidStage();
                buildFrontStage();
            }

            private static void buildRearStage() {
                CharacterPortrait leftPortrait = null;
                CharacterPortrait rightPortrait = null;

                for(CharacterPortrait cP : characters) {
                    if(cP.position == FAR_LEFT) {
                        leftPortrait = cP;
                        break;
                    }
                }

                for(CharacterPortrait cP : characters) {
                    if(cP.position == FAR_RIGHT) {
                        rightPortrait = cP;
                        break;
                    }
                }

                rearStage.clearChildren();
                if(leftPortrait == null) {
                    rearStage.add().uniform();
                } else {
                    rearStage.add(leftPortrait).uniform();
                }
                rearStage.add().uniform();
                rearStage.add().uniform();
                if(rightPortrait == null) {
                    rearStage.add().uniform();
                } else {
                    rearStage.add(rightPortrait).uniform();
                }
            }
            private static void buildMidStage() {
                CharacterPortrait leftPortrait = null;
                CharacterPortrait rightPortrait = null;

                for(CharacterPortrait cP : characters) {
                    if(cP.position == LEFT) {
                        leftPortrait = cP;
                        break;
                    }
                }

                for(CharacterPortrait cP : characters) {
                    if(cP.position == RIGHT) {
                        rightPortrait = cP;
                        break;
                    }
                }

                midStage.clearChildren();
                if(leftPortrait == null) {
                    midStage.add().uniform();
                } else {
                    midStage.add(leftPortrait).uniform();
                }
                if(rightPortrait == null) {
                    midStage.add().uniform();
                } else {
                    midStage.add(rightPortrait).uniform();
                }
            }
            private static void buildFrontStage() {
                CharacterPortrait leftPortrait = null;
                CharacterPortrait rightPortrait = null;

                for(CharacterPortrait cP : characters) {
                    if(cP.position == LEFT_OF_CENTER) {
                        leftPortrait = cP;
                        break;
                    }
                }
                for(CharacterPortrait cP : characters) {
                    if(cP.position == RIGHT_OF_CENTER) {
                        rightPortrait = cP;
                        break;
                    }
                }
                frontStage.clearChildren();
                frontStage.add().uniform();
                if(leftPortrait == null) {
                    frontStage.add().uniform();
                } else {
                    frontStage.add(leftPortrait).uniform();
                }
                if(rightPortrait == null) {
                    frontStage.add().uniform();
                } else {
                    frontStage.add(rightPortrait).uniform();
                }
                frontStage.add().uniform();
            }

            // todo: buildCenterStage() {...}

            private static boolean characterIsOnStage(Character.Name charName) {
                return (characterIndex(charName) >= 0);
            }

            private static int characterIndex(Character.Name charName) {
                for (CharacterPortrait c : characters) {
                    if (c.characterID == charName) {
                        return characters.indexOf(c, true);
                    }
                }
                return -1;
            }

        }

        public static class CharacterPortrait extends Image {
            private final Character.Name characterID;
            private Character.Expression expression = Character.Expression.NEUTRAL;
            private Cutscene.HorizontalPosition position = null;
            private boolean facingLeft = false;
            private String preferredName = null;
            private String line = null;
            private TextureRegionDrawable drawable = null;

            public CharacterPortrait(Character.Name characterID) {
                super();
                this.characterID = characterID;
                setScaling(Scaling.fit);
                deriveUpdatedDrawable();
            }

            public void deriveUpdatedDrawable() {
                switch(characterID) {

                    case Leif:
                        switch(expression) {
                            case NEUTRAL:
                                drawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("test/robin.png")));
//                                drawable.getRegion().flip(true,false);
                                setDrawable(drawable);
                                break;
                            default:
                                break;
                        }
                        break;

                    case Antal:

                    default:
                        break;
                }
            }

            public void flip() {
//                if(facingLeft) {
//                    setScaleX(1);
//                    setX(getX() - getWidth());
//                } else {
//                    setScaleX(-1);
//                    setX(getX() + getWidth());
//                }
                drawable.getRegion().flip(true,false);
                setDrawable(drawable);

                facingLeft = !facingLeft;
            }

            public void setExpression(Character.Expression expression) {
                if(expression == null) {
                    this.expression = Character.Expression.NEUTRAL;
                    deriveUpdatedDrawable();
                    return;
                }
                this.expression = expression;
                deriveUpdatedDrawable();
            }
            public void setPreferredName(String name) {
                this.preferredName = name;
            }
            public void setLine(String line) {
                this.line = line;
            }

            public Character.Name getCharacter() {
                return characterID;
            }
            public boolean isFacingLeft() {
                return facingLeft;
            }
            @Null
            public Character.Expression getExpression() {
                return expression;
            }
            @Null
            public Cutscene.HorizontalPosition getPosition() {
                return position;
            }
            public String getPreferredName() {
                // TODO:
                //  AVATAR_CAN_SEE_NAMES ? (preferred == null ?...) : RPGClass.ID.toString() ;
                return (preferredName == null ? characterID.toString() : preferredName);
            }
            @Null
            public String getLine() {
                return line;
            }
        }

    }

}
