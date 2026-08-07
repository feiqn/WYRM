package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Pool;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.WyrScreen;
import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;

import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Cutscene.LoopCondition;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Cutscene.Choreography.DialogChoreoType.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Cutscene.TriggerType.*;

public abstract class WyrCutscene implements WyrFrame {

    private   final Array<Shot>    script = new Array<>(); // used to be its own class but felt too bloaty for one assembly
    protected final Array<Trigger> triggers = new Array<>();
    protected final Array<Trigger> defuseTriggers = new Array<>();

    protected boolean hasPlayed   = false;
    protected boolean readyToPlay = false;
    protected boolean defused     = false;

    protected int triggerThreshold = 1;
    protected int defuseThreshold  = 1;
    protected int triggerCount     = 0;
    protected int defuseCount      = 0;
    protected int scriptIndex      = 0;

    protected Image backgroundImage = new Image();

    protected LoopCondition loopCondition = null;

    private final Cutscene.ID CutsceneID;

    protected WyrCutscene(Cutscene.ID id) {
        this.CutsceneID = id;
        buildScript();
        declareTriggers();
    }

    protected abstract void buildScript();

    protected abstract void declareTriggers();

    protected void addTrigger(Trigger trigger) {
        if(!triggers.contains(trigger, true)) triggers.add(trigger);
    }

    protected void addDefuseTrigger(Trigger trigger) {
        if(!defuseTriggers.contains(trigger, true)) defuseTriggers.add(trigger);
    }

    private void resetLoop() {
        if(!shouldLoop()) return;

        switch(loopCondition) {
            case MULTIPLICATIVE_THRESHOLD:
                triggerCount = 0;
            case BROKEN_THRESHOLD:
                readyToPlay = false;
            default:
                scriptIndex = 0;
                triggers.clear();
                declareTriggers();
                hasPlayed = false;
                break;
        }
    }

    public Shot nextShot() {
        if(defused) return null;
        if(script.size == 0) buildScript();
        if(readyToPlay) {
            readyToPlay = false;
            if(!shouldLoop()) {
                hasPlayed = true;
            }
        }

        scriptIndex++;
        return script.get(scriptIndex - 1);
    }

    /**
     * Setters and incrementers
     */
    protected void setLoopCondition(LoopCondition condition) { this.loopCondition = condition;}
    public void incrementTriggerCount() {
        if(readyToPlay) return;
        triggerCount++;
        if(triggerCount >= triggerThreshold) readyToPlay = true;
    }
    public void incrementDefuseCount() {
        if(defused) return;
        defuseCount++;
        if(defuseCount >= defuseThreshold) defused = true;
    }
    protected void incrementTriggerThreshold() { triggerThreshold++; }
    protected void incrementDefuseThreshold() { defuseThreshold++; }
    protected void setFullscreenImage(Drawable drawable) { backgroundImage.setDrawable(drawable); }

    /**
     * Getters
     */
    public boolean isReadyToPlay() {
        if(defused || hasPlayed) return false;
        return readyToPlay;
    }
    public boolean isDefused() { return defused; }
    public boolean hasPlayed() { return hasPlayed; }
    public boolean continues() {
        if(defused) return false;
        boolean continues = false;
        try {
            if(scriptIndex == 0) {
                if(script.size == 0) buildScript();
                return true;
            }
            int upperMax = script.size;
            if(scriptIndex < upperMax) {
                continues = true;
            }
        } catch(Exception ignored) {}

        if(!continues && shouldLoop()) {
            resetLoop();
        }
        return continues;
    }
    public Cutscene.ID getCutsceneID() { return CutsceneID; }
    public Image getBackgroundImage() { return backgroundImage; }
    public Array<Trigger> getTriggers() { return triggers; }
    public Array<Trigger> getDefuseTriggers() { return defuseTriggers; }
    protected LoopCondition getLoopCondition() { return loopCondition; }
    protected boolean shouldLoop() { return loopCondition != null; }
    protected Shot lastSlide() { return script.get(script.size-1); }

    /**
     * Cheeky dev stuff
     */
    public void DEVELOPER_skipToEnd() {
        this.scriptIndex = script.size - 2;
    }

    /**
     * Standard Dialog,
     * Convenience methods for quickly building out scripts.
     */
    protected Shot script(Character.Name characterID, Cutscene.HorizontalPosition position, String line) {
        script.add(new Shot(new DialogDirection(characterID).position(position).line(line)));
        return script.get(script.size-1);
    }
    protected Shot script(Character.Name characterID, String line) {
        // either default location or infer from previous slides
        script.add(new Shot(new DialogDirection(characterID).line(line)));
        return script.get(script.size-1);
    }
    protected Shot script(String line) {
        // Infer based on previous slide
        script.add(new Shot(new DialogDirection(line)));
        return script.get(script.size-1);
    }
    protected Shot script(Shot shot) {
        script.add(shot);
        return script.get(script.size-1);
    }

    /**
     * Dialog Choreography:
     * These are things that happen while the conversation window is visible,
     * typically manipulating character portraits, or are generally agnostic
     * to the external world / screen state.
     */
    protected Shot choreographScreenTransition(WyrScreen screen) {
        script.add(new Shot(new Choreography(SCREEN_TRANSITION).setScreenForTransition(screen)));
        return script.get(script.size-1);
    }
    protected Shot choreographScreenFadeOut() {
        script.add(new Shot(new Choreography(SCREEN_FADE_OUT)));
        return script.get(script.size-1);
    }
    protected Shot choreographScreenFadeIn() {
        script.add(new Shot(new Choreography(SCREEN_FADE_IN)));
        return script.get(script.size-1);
    }
    protected Shot choreographShortPause() {
        script.add(new Shot(new Choreography(PAUSE_SHORT)));
        return script.get(script.size-1);
    }
    protected Shot choreographLongPause() {
        script.add(new Shot(new Choreography(PAUSE_LONG)));
        return script.get(script.size-1);
    }
    protected Shot choreographRevealCondition(Campaign.FlagID conditionFlag) {
        script.add(new Shot(new Choreography(WINCON_REVEAL).setFlag(conditionFlag)));
        return script.get(script.size-1);
    }
    protected Shot choreographSatisfyCondition(Campaign.FlagID conditionFlag) {
        script.add(new Shot(new Choreography(WINCON_SATISFY).setFlag(conditionFlag)));
        return script.get(script.size-1);
    }
    protected Shot choreographEndScene() {
        script.add(new Shot(new Choreography(CUTSCENE_END)));
        return script.get(script.size-1);
    }
    protected Shot choreographLearnName(Character.Name name) {
        return script(new Shot(new Choreography(LEARN_NAME).setCharacterID(name)));
    }

    protected Shot choreograph(WyrInteraction interaction) {
        return script(new Shot(new Choreography(interaction)));
    }
    protected Shot choreographAbility(WyrActor actor, GameKit.RPG.AbilityID abilityID) {
        return script(new Shot(new Choreography(new WyrInteraction(actor).useAbility(abilityID))));
    }
    protected Shot choreographUseProp(WyrActor actor, WyrActor prop) {
        return script(new Shot(new Choreography(new WyrInteraction(actor).useProp(prop))));
    }
    protected Shot choreographUseProp(Character.Name charID, String propUID) {
        return script(new Shot(new Choreography(new WyrInteraction(handlers.register().getActorByName(charID.toString())).useProp(handlers.register().getActorByName(propUID)))));
    }
    protected Shot choreographFireArmament(Character.Name unitFiring, String propUID, String targetName) {
        return script(new Shot(new Choreography(new WyrInteraction(unitFiring.toString()).fireArmament(propUID, targetName))));
    }
    protected Shot choreographSpawn(WyrActor actor, int x, int y) {
        return script(new Shot(new Choreography(new WyrInteraction(actor).spawn(new Vector2(x,y)))));
    }
    protected Shot choreographDespawn(Character.Name actor) {
        return script(new Shot(new Choreography(new WyrInteraction(actor).despawn())));
    }
    protected Shot choreographDespawn(WyrActor actor) {
        return script(new Shot(new Choreography(new WyrInteraction(actor).despawn())));
    }
    protected Shot choreographDeath(Character.Name unit) {
        return script(new Shot(new Choreography(new WyrInteraction(unit).kill())));
    }
    protected Shot choreographDeath(WyrActor unit) {
        return script(new Shot(new Choreography(new WyrInteraction(unit).kill())));
    }
    protected Shot choreographDestroy(WyrActor prop) {
        return script(new Shot(new Choreography(new WyrInteraction(prop).destroy())));
    }
    protected Shot choreographMoveBy(WyrActor actor, float x, float y) {
        return script(new Shot(new Choreography(new WyrInteraction(actor).moveBy(x,y))));
    }
    protected Shot choreographFollowPath(WyrActor actor, GridPath path) {
        return script(new Shot(new Choreography(new WyrInteraction(actor).followPath(path))));
    }
    protected @Null Shot choreographFocusUnit(Character.Name charID) {
        final @Null WyrActor parent = handlers.register().getActorByName(charID.toString());
        if(parent == null) return null;
        return script(new Shot(new Choreography(new WyrInteraction(parent).focus())));
    }
    protected Shot choreographFocusLocation(Vector2 location) {
        return script(new Shot(new Choreography(new WyrInteraction("Leif").focus(location))));
    }
    protected Shot choreographPassPriority(Character.Name charID) {
        return script(new Shot(new Choreography(new WyrInteraction(charID.toString()).passPriority())));
    }


    public static class Trigger implements Pool.Poolable {

        @Null
        private Pool pool = null;

        protected boolean hasFired = false;
        protected boolean defused = false; // Individual triggers for cutscenes can be diffused, rather than the entire cutscene.
        protected boolean requiresSubjectAggressor = false;
        protected boolean exactTurn = false; // Should turn trigger fire only on exact turns or any turn after?
        protected int defuseThreshold = 1;
        protected int defuseCount = 0;
        protected TeamAlignment requiredSubjectTeam = null;
        protected TeamAlignment requiredObjectTeam = null;
        protected Campaign.FlagID triggerFlag = null;
        protected final Cutscene.TriggerType triggerType;
        protected final Array<Character.Name> subjectUnits = new Array<>();
        protected final Array<Character.Name> objectUnits = new Array<>();
        protected final Array<Vector2> triggerTiles = new Array<>();
        protected final Array<Integer> triggerTurns = new Array<>();
        protected final Array<Cutscene.ID> triggerCSIDs = new Array<>();
        protected final Array<Trigger> defuseTriggers = new Array<>();

        public Trigger(Cutscene.TriggerType triggerType) {
            this.triggerType = triggerType;
        }


        public Trigger setFlag(Campaign.FlagID triggeringFlag) {
            this.triggerFlag = triggeringFlag;
            return this;
        }
        public Trigger setSubjectTeam(TeamAlignment subjectAlignment) {
            this.requiredSubjectTeam = subjectAlignment;
            return this;
        }
        public Trigger setObjectTeam(TeamAlignment objectTeam) {
            this.requiredObjectTeam = objectTeam;
            return this;
        }
        public Trigger setSubjectUnits(Character.Name... id) {
            this.subjectUnits.addAll(id);
            return this;
        }
        public Trigger setObjectUnits(Character.Name... id) {
            this.objectUnits.addAll(id);
            return this;
        }
        public Trigger setTriggeringTiles(Vector2... coordinate) {
            this.triggerTiles.addAll(coordinate);
            return this;
        }
        public Trigger setTriggeringTurns(boolean exactTurn, Integer... turn) {
            this.exactTurn = exactTurn;
            this.triggerTurns.addAll(turn);
            return this;
        }
        public Trigger setTriggeringCutscenes(Cutscene.ID... csid) {
            this.triggerCSIDs.addAll(csid);
            return this;
        }

        public Trigger setDefuseThreshold(int defuseThreshold) {
            this.defuseThreshold = defuseThreshold;
            return this;
        }
        public Trigger setDefuseTriggers(Trigger... trigger) {
            this.defuseTriggers.addAll(trigger);
            return this;
        }
        public Trigger addDefuseTrigger(Trigger trigger) {
            if(!defuseTriggers.contains(trigger,true)) defuseTriggers.add(trigger);
            return this;
        }


        public Trigger(Campaign.FlagID triggerFlag) {
            this.triggerType = CAMPAIGN_FLAG;
            this.triggerFlag = triggerFlag;
            // TODO: can broaden the scope on this to include a flag array later if need arrises
        }
        public Trigger(Integer turnToTrigger, boolean exactTurn) {
            this.triggerType = TURN;
            this.exactTurn = exactTurn;
            triggerTurns.add(turnToTrigger);
        }
        public Trigger(Character.Name rosterID, boolean beforeCombat, boolean requiresSubjectAggressor) {
            if(beforeCombat) {
                this.triggerType = COMBAT_START;
            } else {
                this.triggerType = COMBAT_END;
            }
            this.requiresSubjectAggressor = requiresSubjectAggressor;
            subjectUnits.add(rosterID);
        }
        public Trigger(Character.Name attacker, Character.Name defender, boolean beforeCombat) {
//            isCompound = true;
            if(beforeCombat) {
                this.triggerType = COMBAT_START;
            } else {
                this.triggerType = COMBAT_END;
            }
            subjectUnits.add(attacker, defender);
        }
        public Trigger(Character.Name deathOf) {
            this.triggerType = DEATH_OF;
            subjectUnits.add(deathOf);
        }
        public Trigger(TeamAlignment deathOf) {
            this.triggerType = DEATH_OF;

            requiredSubjectTeam = deathOf;
        }
        public Trigger(Cutscene.ID otherID) {
            this.triggerType = OTHER_CUTSCENE;
            triggerCSIDs.add(otherID);
        }
        public Trigger(Character.Name rosterID, Array<Vector2> areas) {
//            isCompound = true;
            this.triggerType = AREA;
            subjectUnits.add(rosterID);
            for(Vector2 vector : areas) {
                triggerTiles.add(vector);
            }
        }
        public Trigger(Character.Name rosterID, Vector2 area) {
//            isCompound = true;
            this.triggerType = AREA;
            subjectUnits.add(rosterID);
            triggerTiles.add(area);
        }
        public Trigger(Vector2 area) {
            this.triggerType = AREA;
            triggerTiles.add(area);
        }
        public Trigger(Vector2 area, TeamAlignment requiredSubjectTeam) {
            this.triggerType = AREA;
//            isCompound = true;
            this.requiredSubjectTeam = requiredSubjectTeam;

            triggerTiles.add(area);
        }

        protected void incrementDefuseCount() {
            if(defused) return;
            defuseCount++;
            if(defuseCount >= defuseThreshold) defused = true;
        }
        public void fire() {
            if(defused) return;
            hasFired = true;
        }
        public boolean hasFired() { return !defused && hasFired; }

        /**
         * CHECKERS (gotta eat)
         */
        public boolean checkCampaignFlagTrigger(Campaign.FlagID flag) {
            if(defused || hasFired) return false;
//            if(isCompound) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;

                if(def.checkCampaignFlagTrigger(flag)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused || this.triggerType != CAMPAIGN_FLAG) return false;

            if(this.triggerFlag == flag) {
                hasFired = true;
                return true;
            }

            return false;
        }
        public boolean checkZeroHPTrigger(Character.Name roster) {
            if(defused || hasFired) return false;
//            if(isCompound) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;

                if(def.checkZeroHPTrigger(roster)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused || this.triggerType != ZERO_HP) return false;
            if(subjectUnits.isEmpty() && objectUnits.isEmpty()) return false;

            if(this.subjectUnits.contains(roster, true)) {
                fire();
                return true;
            }

            return false;
        }
        public boolean checkDeathTrigger(Character.Name roster) {
            if(defused || hasFired) return false;
//            if(isCompound) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;

                if(def.checkDeathTrigger(roster)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused || this.triggerType != DEATH_OF) return false;
            if(subjectUnits.isEmpty() && objectUnits.isEmpty()) return false;

            if(this.subjectUnits.contains(roster, true)) {
                fire();
                return true;
            }

            return false;
        }
        public boolean checkDeathTrigger(TeamAlignment alignment) {
            if(defused || hasFired) return false;
//            if(isCompound) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;

                if(def.checkDeathTrigger(alignment)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(requiredSubjectTeam == null && requiredObjectTeam == null) return false;
            if(defused || this.triggerType != DEATH_OF) return false;

            if(requiredSubjectTeam == alignment || requiredObjectTeam == alignment) {
                fire();
                return true;
            }

            return false;
        }
        public boolean checkAreaTrigger(Character.Name rosterID, Vector2 tileCoordinate) {
            if(defused || hasFired) return false;
//            if(!isCompound) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;
                if(def.checkAreaTrigger(rosterID, tileCoordinate)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused || this.triggerType != AREA) return false;
            if(!subjectUnits.contains(rosterID, true)) return false;

            for(Vector2 vector : triggerTiles) {
                if(vector.x == tileCoordinate.x && vector.y == tileCoordinate.y) {
                    fire();
                    return true;
                }
            }

            return false;
        }
        public boolean checkAreaTrigger(Vector2 tileCoordinate, TeamAlignment unitsAlignment) {
            if(defused || hasFired) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;
                if(def.checkAreaTrigger(tileCoordinate, unitsAlignment)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused || this.triggerType != AREA) return false;
            if(requiredSubjectTeam == null && requiredObjectTeam == null) return false;
            if(unitsAlignment != requiredSubjectTeam && unitsAlignment != requiredObjectTeam) return false;

            for(Vector2 vector : triggerTiles) {
                if(vector.x == tileCoordinate.x && vector.y == tileCoordinate.y) {
                    fire();
                    return true;
                }
            }

            return false;
        }
        public boolean checkTurnTrigger(int turn) {
            if(defused || hasFired) return false;
//            if(isCompound) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;
                if(def.checkTurnTrigger(turn)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused || this.triggerType != TURN) return false;

            if(exactTurn) {
                if(triggerTurns.contains(turn, true)) {
                    fire();
                    return true;
                }
            } else {
                if(turn >= triggerTurns.get(0)) {
                    fire();
                    return true;
                }
            }

            return false;
        }
        public boolean checkOtherCutsceneTrigger(Cutscene.ID otherID) {
            if(defused || hasFired) return false;
//            if(isCompound) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;
                if(def.checkOtherCutsceneTrigger(otherID)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused || this.triggerType != OTHER_CUTSCENE) return false;

            if(triggerCSIDs.contains(otherID, true)) {
                fire();
                return true;
            }

            return false;
        }
        public boolean checkCombatTrigger(boolean beforeVisualCombat, Character.Name attacker, TeamAlignment attackerTeam, Character.Name defender, TeamAlignment defenderTeam) {
            if(defused || hasFired) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;
                if(def.checkCombatTrigger(beforeVisualCombat, attacker, attackerTeam, defender, defenderTeam)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused || this.triggerType != (beforeVisualCombat ? COMBAT_START : COMBAT_END)) return false;

            final boolean teamBasedTrigger = requiredSubjectTeam != null || requiredObjectTeam != null;
            final boolean doubleTeamTrigger = requiredSubjectTeam != null && requiredObjectTeam != null;
            final boolean singleTeamTrigger = teamBasedTrigger && !doubleTeamTrigger;

            final boolean unitBasedTrigger = !subjectUnits.isEmpty() || !objectUnits.isEmpty();
            final boolean doubleUnitTrigger = !subjectUnits.isEmpty() && !objectUnits.isEmpty();
            final boolean singleUnitTrigger = unitBasedTrigger && !doubleUnitTrigger;

            if(teamBasedTrigger) {
                if(requiredSubjectTeam == attackerTeam) {
                    if(doubleTeamTrigger && requiredObjectTeam != defenderTeam) return false;
                    if(unitBasedTrigger && !subjectUnits.contains(attacker, true)) return false;
                    fire();
                    return true;

                } else if(!requiresSubjectAggressor && requiredSubjectTeam == defenderTeam) {
                    if(doubleTeamTrigger && requiredObjectTeam != attackerTeam) return false;
                    if(unitBasedTrigger && !subjectUnits.contains(defender, true)) return false;
                    fire();
                    return true;
                }

            } else if(unitBasedTrigger) {
                if(subjectUnits.contains(attacker, true)) {
                    if(doubleUnitTrigger && !objectUnits.contains(defender, true)) return false;
                    fire();
                    return true;

                } else if(!requiresSubjectAggressor && subjectUnits.contains(defender, true)) {
                    if(doubleUnitTrigger && !subjectUnits.contains(attacker, true)) return false;
                    fire();
                    return true;
                }
            }

            return false;
        }
        // check zero_hp trigger,

        @Override
        public void reset() { this.pool = null; }

        @Null
        public Pool getPool() { return this.pool; }

        public void setPool(@Null Pool pool) { this.pool = pool; }

    }

    public static class Shot {

        protected Utilities.Speed textSpeed = Utilities.Speed.NORMAL;

        protected Cutscene.Background backgroundID = Cutscene.Background.NONE;
        protected Cutscene.Foreground foregroundID = Cutscene.Foreground.NONE;

        protected DialogDirection focusedDirection = null; // always has lines
        protected DialogDirection doubleSpeakDirection = null; // only has lines when used
        protected Array<DialogDirection> extras = new Array<>(); // silent

        protected boolean fullscreen         = false;
        protected boolean omitFromLog        = false;
        protected boolean autoProgressToNext = false;
        protected boolean worldChoreographed = false;
        protected boolean stageChoreographed = false;

        protected int snapToIndex = 0;

        protected Choreography choreography = null;

        public Shot() {}

        public Shot(Choreography choreography) {
            this.choreography = choreography;
            switch(choreography.getChoreoStage()) {
                case DIALOG:
                    stageChoreographed = true;
                    break;
                case WORLD:
                    worldChoreographed = true;
                    break;
            }
        }

        public Shot(DialogDirection direction) {
            focusedDirection = direction;
        }

        public Shot(Character.Name focusedCharacterID, Character.Expression expression, String dialog) { focusedDirection = new DialogDirection(focusedCharacterID).expression(expression).line(dialog); }
        public Shot flipFacing() { this.focusedDirection.flipFacing(); return  this; }
        public Shot position(Cutscene.HorizontalPosition position) { this.focusedDirection.position = position; return this; }
        public Shot focus(DialogDirection character)         { this.focusedDirection = character;        return this; }
        public Shot doubleSpeak(DialogDirection character)   { this.doubleSpeakDirection = character;    return this; }
        public Shot autoplay()                               { autoProgressToNext = true;                return this; }
        public Shot fullscreen()                             { fullscreen = true;                        return this; }
        public Shot omit()                                   { omitFromLog = true;                       return this; }
        public Shot addSupporting(DialogDirection character) { this.extras.add(character); return this; }
        public Shot background(Cutscene.Background backgroundID) { this.backgroundID = backgroundID;     return this; }
        public Shot foreground(Cutscene.Foreground foregroundID) { this.foregroundID = foregroundID;     return this; }
        public Shot textSpeed(Utilities.Speed textSpeed) { this.textSpeed = textSpeed;               return this; }
        public Shot snapToIndex(int index) { this.snapToIndex = index;                 return this; }
        public Shot preferredName(String name) { this.focusedDirection.preferredName(name); return this; }
        public Shot also(Character.Name extraChar) {
            return also(extraChar, Character.Expression.NEUTRAL, Cutscene.HorizontalPosition.RIGHT);
        }
        public Shot also(Character.Name extraChar, Cutscene.HorizontalPosition atPos) {
            return also(extraChar, null, atPos);
        }
        public Shot also(Character.Name extraChar, Character.Expression expression) {
            return also(extraChar, expression, null, null);
        }
        public Shot also(Character.Name extraChar, Cutscene.HorizontalPosition atPos, Boolean flipFacing) {
            return also(extraChar, null, atPos, flipFacing);
        }
        public Shot also(Character.Name extraChar, Character.Expression expression, Boolean flipFacing) {
            return also(extraChar, expression, null, flipFacing);
        }
        public Shot also(Character.Name extraChar, Character.Expression expression, Cutscene.HorizontalPosition atPos) {
            return also(extraChar, expression, atPos, false);
        }
        public Shot also(Character.Name extraChar, @Null Character.Expression expression, @Null Cutscene.HorizontalPosition atPos, Boolean flipFacing) {
            final DialogDirection extraDirection = new DialogDirection(extraChar);
            if(expression != null) extraDirection.expression = expression;
            if(atPos != null) extraDirection.position = atPos;
            if(flipFacing) extraDirection.flipFacing();
            return this;
        }

        public DialogDirection getFocusedDirection()            { return focusedDirection; }
        public DialogDirection getDoubleSpeakDirection()        { return doubleSpeakDirection; }
        public Array<DialogDirection> getExtras() { return extras; }

        public void choreograph(Choreography choreography) { this.choreography = choreography; stageChoreographed = true; }

        public Choreography getChoreo() { return choreography; }

        public Cutscene.Background getBackgroundID() { return backgroundID; }
        public Cutscene.Foreground getForegroundID() { return foregroundID; }
        public boolean       usesDoubleSpeak()    { return doubleSpeakDirection != null;}
        public boolean       autoProgresses()     { return autoProgressToNext; }
        public boolean       isFullscreen()       { return fullscreen; }
        public boolean       omittedFromLog()     { return omitFromLog; }
        public boolean       isChoreographed()    { return choreography != null; }
        public boolean       stageChoreographed() { return stageChoreographed; }
        public boolean       worldChoreographed() { return worldChoreographed; }
        public Utilities.Speed getDisplaySpeed()    { return textSpeed; }
        public int           getSnapToIndex()     { return snapToIndex; }

    }

    public static class Choreography extends Subjectivity {

        // refactored combination of CutsceneFrameChoreography and DialogAction

        // "Choreography is stuff that happens on the map / over-world,
        // as opposed to DialogActions which happen inside the Conversation window."

        private final Cutscene.Choreography.ChoreoStage choreoStage;
        protected Character.Name characterID = null;
        protected WyrInteraction worldInteraction = null;
        protected Cutscene.Choreography.DialogChoreoType dialogChoreoType = null;
        protected Campaign.FlagID associatedCampaignFlag = null;
        protected Vector2 associatedCoordinate = null;
        protected WyrScreen screenForTransition = null;
        protected Runnable payload = null;
        protected boolean loops = false;
        protected boolean  playParallel  = false;
        protected Utilities.Speed actSpeed = Utilities.Speed.NORMAL;
        protected LoopCondition loopCondition = null;

        public Choreography(WyrInteraction worldInteraction) {
            this.choreoStage = Cutscene.Choreography.ChoreoStage.WORLD;
            this.worldInteraction = worldInteraction;
        }
        public Choreography(Cutscene.Choreography.DialogChoreoType dialogChoreoType) {
            this.choreoStage = Cutscene.Choreography.ChoreoStage.DIALOG;
            this.dialogChoreoType = dialogChoreoType;
        }

        // SETTERS
        public Choreography loop() { this.loops = true; return this;}
        public Choreography setCoordinate(float column, float row) { this.associatedCoordinate = new Vector2(column, row); return this; }
        public Choreography setCoordinate(Vector2 coordinates) { this.associatedCoordinate = coordinates; return this; }
        public Choreography setLocation(RPGridTile tile) { this.associatedCoordinate = new Vector2(tile.getXColumn(), tile.getYRow()); return this; }
        public Choreography setFlag(Campaign.FlagID flagID) { this.associatedCampaignFlag = flagID; return this; }
        public Choreography setScreenForTransition(WyrScreen screen) { this.screenForTransition = screen; return this; }
        public Choreography setCharacterID(Character.Name charID) { this.characterID = charID; return this; }

        // GETTERS
        public Cutscene.Choreography.ChoreoStage getChoreoStage() { return choreoStage; }
        public WyrInteraction   getWorldInteraction() { return worldInteraction; }
        public Cutscene.Choreography.DialogChoreoType getDialogChoreoType() { return dialogChoreoType; }

        public boolean loops() { return loops;}
        public ScreenAdapter getScreenForTransition() { return screenForTransition; }
        public Campaign.FlagID getFlag() { return associatedCampaignFlag; }
        public Vector2 getLocation() { return associatedCoordinate; }
        public Character.Name getCharacterID() { return characterID; }
    }

    public static class DialogDirection implements WyrFrame {
        private Character.Name              characterID = null;
        private Character.Expression        expression  = null;
        private Cutscene.HorizontalPosition position    = null;
        private boolean flipFacing = false; // default is facing right
        private String                      preferredName = null;
        private String                      line = null;

        public DialogDirection(String line) {
            // when character is null, player assigns line
            // to the last referenced character in script
            this.line = line;
        }
        public DialogDirection(Character.Name characterID) { this.characterID = characterID; }

        public DialogDirection expression(Character.Expression expression) {
            this.expression = expression;
            return this;
        }
        public DialogDirection position(Cutscene.HorizontalPosition position) {
            this.position = position;
            return this;
        }
        private DialogDirection flipFacing() {
            flipFacing = true;
            return this;
        }
        public DialogDirection preferredName(String name) {
            this.preferredName = name;
            return this;
        }
        public DialogDirection line(String line) {
            this.line = line;
            return this;
        }

        public Character.Name getCharacterID() {
            return characterID;
        }
        public boolean shouldFlipFacing() {
            return flipFacing;
        }
        @Null
        public Character.Expression getExpression() {
            return expression;
        }
        @Null
        public Cutscene.HorizontalPosition getPosition() {
            return position;
        }
        @Null
        public String getPreferredName() {
            return preferredName;
        }
        @Null
        public String getLine() {
            return line;
        }
    }

}
