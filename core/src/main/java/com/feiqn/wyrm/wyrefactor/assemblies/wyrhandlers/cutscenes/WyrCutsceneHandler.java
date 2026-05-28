package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.WyrCutscene.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.InputMode.LOCKED;

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

        cutscenePlayer.layout.addAction(Actions.sequence(
            Actions.fadeOut(.3f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    isBusy = false;
                    cutscenePlayer.activeCutscene = null;
                    handlers.standardizeParse();
                }
            })

            )
        );
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
     * Trigger checks
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

    /**
     * Cutscene player
     */
    public static class Player {

        private final Performance performance = new Performance();

        protected static final Table layout = new Table();
        protected final Window dialogWindow;
        protected final Window nameWindow;

        protected static final Label nameLabel = new Label("?", WYRMGame.assets().menuLabelStyle);

        protected WyrCutscene activeCutscene = null;

        protected final ProgressiveLabel focusedLabel = new ProgressiveLabel("Sample Text", WYRMGame.assets().menuLabelStyle);
        protected final ProgressiveLabel doubleSpeakLabel = new ProgressiveLabel("Sample Text", WYRMGame.assets().menuLabelStyle);

        private boolean inFullscreen = false;
        private boolean layoutVisible = false;

        public Player(Skin skin) {
            focusedLabel.setWrap(true);
            dialogWindow = new Window("", skin);
            nameWindow = new Window("", skin);
//            dialogWindow.setFillParent(true);
//            nameWindow.setFillParent(true);

            buildDialogWindow();
            buildNameWindow();

            layout.setDebug(true);

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
                Performance.direct(shot.getFocusedDirection());
                updateNameLabel(shot.getFocusedDirection().getPreferredName(), positionToScreen(shot.getFocusedDirection().getPosition()));
            }

            focusedLabel.progressiveDisplay(shot.getFocusedDirection().getLine());
        }

        protected void buildLayoutStandard() {
            layout.clearChildren();
            buildDialogWindow();
            buildNameWindow();

            layout.add(performance)
                .colspan(6)
                .expand()
                .fill()
            ;
            layout.row();

            layout.add(nameWindow);
            layout.row();

            layout.add(dialogWindow)
                .colspan(6)
                .expand()
                .fill()
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
                .padLeft(15)
                .padRight(15)
                .padTop(5)
                .padBottom(5)
            ;

        }

        public void updateNameLabel(String name, Vector2 pos) {
            nameLabel.setText(name);
//            buildNameWindow();
        }

        /**
         * Choreo
         */
        protected void parseChoreo(Choreography choreography) {
            handlers.input().lock();
            if(choreography.getChoreoStage() == Cutscene.Choreography.ChoreoStage.WORLD) {
                if(layoutVisible) {
                    layout.addAction(Actions.sequence(
                        Actions.fadeOut(.2f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                parseWorldChoreo(choreography.getWorldInteraction());
                            }
                        })
                    ));
                } else {
                    parseWorldChoreo(choreography.getWorldInteraction());
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
                default:
                    break;
            }

        }

        protected void parseWorldChoreo(WyrInteraction interaction) {
            // InteractionHandler acts out choreo,
            // then continue CS.


        }

        /**
         * etc.
         */
        public @Null WyrCutscene getActiveCutscene() { return activeCutscene; }

        private static Vector2 positionToScreen(Cutscene.HorizontalPosition horizontalPosition) {
            final float y = layout.getY() + layout.getHeight();
            switch(horizontalPosition) {
                case FAR_LEFT:
                case LEFT:
                case LEFT_OF_CENTER:
                    final float lX = layout.getX() + (layout.getWidth() * .2f);
                    return new Vector2(lX, y);

                case CENTER:
                    final float cX = layout.getX() + (layout.getWidth() * .5f);
                    return new Vector2(cX, y);

                case RIGHT_OF_CENTER:
                case RIGHT:
                case FAR_RIGHT:
                    final float rX = layout.getX() + (layout.getWidth() * .8f);
                    return new Vector2(rX, y);
            }
            return new Vector2();
        }

        /**
         * Helper classes
         */
        private static class Performance extends Table {

            private static final Array<CharacterPortrait> characters = new Array<>();

            public Performance() {
                super();

            }

            private static void direct(DialogDirection direction) {
                if (direction == null) return;
                if (characterIsOnStage(direction.getCharacterID())) {
                    directExistingCharacter(direction);
                } else {
                    directNewCharacter(direction);
                }
            }

            private static void directNewCharacter(DialogDirection direction) {
                final CharacterPortrait nC = new CharacterPortrait(direction.getCharacterID());
                characters.add(nC);


            }

            private static void directExistingCharacter(DialogDirection direction) {
                // "change" the character by moving or removing the existing portrait before acting
                final int cI = characterIndex(direction.getCharacterID());
                final CharacterPortrait cP = characters.get(cI);

                final boolean shouldFlip = (direction.isFacingLeft() != cP.facingLeft);

                if(direction.getPosition() != cP.position) {
                    cP.addAction(Actions.sequence(
                        Actions.moveTo(positionToScreen(direction.getPosition()).x, positionToScreen(direction.getPosition()).y, .3f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                if(shouldFlip) cP.flip();
                            }
                        })
                    ));
                } else {
                    if(shouldFlip) cP.flip();
                }

                if (direction.getExpression() != cP.expression) {
                    cP.setExpression(direction.getExpression());
                }

            }

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
            private Cutscene.HorizontalPosition position = Cutscene.HorizontalPosition.LEFT;
            private boolean facingLeft = false;
            private String preferredName;
            private String line;

            public CharacterPortrait(Character.Name characterID) {
                this.characterID = characterID;

            }

            public void deriveUpdatedDrawable() {
                switch(characterID) {

                    case Leif:
                        switch(expression) {
                            case NEUTRAL:
                                // TODO: set drawable to robinFireEmblem.jpg
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
                if(facingLeft) {
                    setScaleX(1);
                } else {
                    setScaleX(-1);
                }
                facingLeft = !facingLeft;
            }

            public void setExpression(Character.Expression expression) {
                this.expression = expression;

                // TODO: switch based on expression & char ID to update drawable

            }
            public void setPlayerPosition(Cutscene.HorizontalPosition position) {
                this.position = position;
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
