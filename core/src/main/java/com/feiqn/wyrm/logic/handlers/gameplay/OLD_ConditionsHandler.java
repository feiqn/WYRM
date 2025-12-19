package com.feiqn.wyrm.logic.handlers.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;
import com.feiqn.wyrm.logic.handlers.cutscene.OLD_CutsceneHandler;
import com.feiqn.wyrm.logic.handlers.gameplay.combat.OLD_CombatHandler;
import com.feiqn.wyrm.logic.handlers.gameplay.combat.TeamHandler;
import com.feiqn.wyrm.logic.screens.OLD_MapScreen;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.Phase;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;

public class OLD_ConditionsHandler {
    // Dec.6 2025
    // Starting to rename pre-refactor classes
    // with OLD_ naming convention, for clarity
    // of what works with what.

    // Handled by BattleScreen
    // Tracks environmental and logistical parameters for the active instance of a grid screen

    private final WYRMGame game;

    protected boolean fogOfWar                    = false;
    protected boolean terminalVictoryConditionMet = false;
    protected boolean terminalFailureConditionMet = false;
    protected boolean ironModeBTW                 = false;

    protected int currentTurnNumber = 0;

    protected Array<OLD_SimpleUnit> unifiedTurnOrder          = new Array<>();
    protected Array<OLD_SimpleUnit> battleRoster              = new Array<>();
    protected Array<VictoryCondition> victoryConditions = new Array<>();
//    public static Array<FailureCondition> failureConditions;

    protected static OLD_CombatHandler.IronMode ironMode;


    private final TeamHandler teamHandler;
    private final OLD_CombatHandler OLDCombatHandler;
    private final OLD_CutsceneHandler OLDCutsceneHandler;


    public OLD_ConditionsHandler(WYRMGame game) {
        this.game = game;

        teamHandler = new TeamHandler();
        OLDCombatHandler = new OLD_CombatHandler(game);
        OLDCutsceneHandler = new OLD_CutsceneHandler(game);
    }

    public void addVictoryCondition(VictoryCondition victCon) {
        victoryConditions.add(victCon);
        game.activeOLDGridScreen.hud().reset();
    }

//    public void addFailureCondition(FailureCondition failCon) {}

    public void clearTurnOrder() {
        battleRoster.clear();
        calculateTurnOrder();
    }

    public void addToTurnOrder(OLD_SimpleUnit unit) {
        if(!battleRoster.contains(unit, true)) {
            battleRoster.add(unit);
            calculateTurnOrder();
        }
    }

    public void removeFromTurnOrder(OLD_SimpleUnit unit) {
        if(battleRoster.contains(unit, true)) {
            battleRoster.removeValue(unit, true);
            calculateTurnOrder();
        }
    }

    private void advanceTurn() {
        currentTurnNumber++;

        OLDCutsceneHandler.checkTurnTriggers(currentTurnNumber);

        for(OLD_SimpleUnit unit : unifiedTurnOrder) {
            unit.setCanMove();
        }
    }


    private void calculateTurnOrder() {
        /* TODO: YO this implementation is FUCKING DISGUSTING!
        *   The point was just to get it working for now!
        *   We'll come back though later and clean this up,
        *   possibly with ai help!
        *   What was that fucking sorter function it used earlier?
        */

        unifiedTurnOrder = new Array<>();

        final Array<OLD_SimpleUnit> segregatedPlayer = new Array<>();
        final Array<OLD_SimpleUnit> segregatedEnemy = new Array<>();
        final Array<OLD_SimpleUnit> segregatedAlly = new Array<>();
        final Array<OLD_SimpleUnit> segregatedOther = new Array<>();

        for(OLD_SimpleUnit u : battleRoster) {
            switch(u.getTeamAlignment()) {
                case PLAYER:
                    segregatedPlayer.add(u);
                    break;
                case ENEMY:
                    segregatedEnemy.add(u);
                    break;
                case ALLY:
                    segregatedAlly.add(u);
                    break;
                case OTHER:
                    segregatedOther.add(u);
                    break;
            }
        }

        for(int i = 40; i >= 1; i--) {
            for(OLD_SimpleUnit p : segregatedPlayer) {
                if(p.modifiedSimpleSpeed() == i) unifiedTurnOrder.add(p);
            }
            for(OLD_SimpleUnit p : segregatedEnemy) {
                if(p.modifiedSimpleSpeed() == i) unifiedTurnOrder.add(p);
            }
            for(OLD_SimpleUnit p : segregatedAlly) {
                if(p.modifiedSimpleSpeed() == i) unifiedTurnOrder.add(p);
            }
            for(OLD_SimpleUnit p : segregatedOther) {
                if(p.modifiedSimpleSpeed() == i) unifiedTurnOrder.add(p);
            }
        }

        try {
            game.activeOLDGridScreen.hud().updateTurnOrderPanel(); // maybe unneeded, didn't test
        } catch (Exception ignored) {}

        /* ROTATION LOGIC:
         * ---------------
         * speed values will be: base(0..10) + class(0..10) + weapon(0..10) + armor(0..10)
         * each turn consists of 40 ticks and counts down, with tick 1 being last
         * a unit's combined speed value equals the turn tick they can act on
         * units with same speed may move within battle tick in whatever order they want,
         * with default priority to player -> enemy -> ally -> other
         * a speed of zero will have their turn skipped
         */

    }

    public void satisfyVictCon(CampaignFlags flagID) {
        for(VictoryCondition victCon : victoryConditions) {
            if(victCon.getAssociatedFlag() == flagID) {
                victCon.satisfy();
                Gdx.app.log("conditions", "satisfied");
            }
        }
    }

    public void revealVictCon(CampaignFlags flagID) {
        for(VictoryCondition victCon : victoryConditions) {
            if(victCon.getAssociatedFlag() == flagID) {
                victCon.reveal();
                game.activeOLDGridScreen.hud().updateVictConPanel();
            }
        }


    }

    public OLD_SimpleUnit whoseNextInLine() {
        for(int i = 0; i < unifiedTurnOrder().size; i++) {
            if(unifiedTurnOrder.get(i).canMove()) {
                return unifiedTurnOrder.get(i);
            }
        }
        Gdx.app.log("whoseNext", "Looks like everyone has moved, calling for new turn.");
        advanceTurn();
        return whoseNextInLine();
    }

    // ---GETTERS---
    public OLD_CutsceneHandler conversations() { return OLDCutsceneHandler; }
    public TeamHandler teams() { return teamHandler; }
    public OLD_CombatHandler combat() { return OLDCombatHandler; }

    public int turnCount() { return currentTurnNumber; }
    public int tickCount() { return whoseNextInLine().modifiedSimpleSpeed(); }
    public Array<OLD_SimpleUnit> unifiedTurnOrder() { return unifiedTurnOrder; }
    public VictoryCondition getVictoryCondition(CampaignFlags flagID) {
        for(VictoryCondition victCon : victoryConditions) {
            if(victCon.getAssociatedFlag() == flagID) return victCon;
        }
        return null;
    }
    public Array<VictoryCondition> getVictoryConditions() {
        final Array<VictoryCondition> returnValue = new Array<>();

        for(VictoryCondition vc : victoryConditions) {
            returnValue.add(vc);
        }

        return returnValue;
    }
    public boolean victoryConditionIsSatisfied(CampaignFlags flagID) {
        for(VictoryCondition victCon : victoryConditions) {
            if(victCon.getAssociatedFlag() == flagID) {
                return victCon.conditionIsSatisfied();
            }
        }
        return false;
    }

    // --CALCULATORS--
    public IronMode iron() {
        if(!ironModeBTW) {
//            register.ironMode = new IronMode(this);
            ironModeBTW = true;
        }
        return new IronMode(this);
    }
    public boolean victoryConditionsAreSatisfied() {
        boolean allConsSatisfied = true;
        terminalVictoryConditionMet = false;

        for(VictoryCondition victcon : victoryConditions) {
            if(!victcon.conditionIsSatisfied()) {
                allConsSatisfied = false;
            } else if(victcon.conditionIsSatisfied() && victcon.isTerminal()) {
                terminalVictoryConditionMet = true;
            }
        }

        return allConsSatisfied || terminalVictoryConditionMet;
    }
    public boolean failureConditionsAreSatisfied() {
        return false;
    }
    public Phase getCurrentPhase() {
        // TODO: patchwork wrapper because (holy) fuck there's a lot of calls to this in recursionHandler
        switch(whoseNextInLine().getTeamAlignment()) {
            case ENEMY: return Phase.ENEMY_PHASE;
            case OTHER: return Phase.OTHER_PHASE;
            case ALLY: return Phase.ALLY_PHASE;
            case PLAYER:
            default: return Phase.PLAYER_PHASE;
        }
    }

    // --HELPER CLASSES--
    private static class IronMode {
        // It was just a Phase.

        private final OLD_ConditionsHandler parent;

        private Phase currentPhase;

        public IronMode(OLD_ConditionsHandler parent) {
            this.parent = parent;
            currentPhase = Phase.PLAYER_PHASE;
        }

        private void nextTurn() {

        }

        private Phase phaseFromAlignment(TeamAlignment team) {
            switch(team) {
                case PLAYER:
                    return Phase.PLAYER_PHASE;
                case ENEMY:
                    return Phase.ENEMY_PHASE;
                case ALLY:
                    return Phase.ALLY_PHASE;
                case OTHER:
                    return Phase.OTHER_PHASE;
            }
            return Phase.PLAYER_PHASE;
        }

        public void passPhase() {
            // By default, turn order is as follows:

            // PLAYER -> ENEMY -> ALLY -> OTHER -> PLAYER

            // Calling this function will pass the turn as normal,
            // or you can manually assign turns (I.E., to give the
            // enemy more chances to move on a certain mission, etc.)
            // via passPhaseToTeam(), which this function simply wraps
            // for convenience.

            switch(currentPhase) {
                case PLAYER_PHASE:
                    passPhaseToTeam(TeamAlignment.ENEMY);
                    break;
                case ENEMY_PHASE:
                    if(parent.teams().allyTeamIsUsed()) {
                        passPhaseToTeam(TeamAlignment.ALLY);
                    } else if(parent.teams().allyTeamIsUsed()) {
                        passPhaseToTeam(TeamAlignment.OTHER);
                    } else {
                        passPhaseToTeam(TeamAlignment.PLAYER);
                    }
                    break;
                case ALLY_PHASE:
                    if(parent.teams().otherTeamIsUsed()) {
                        passPhaseToTeam(TeamAlignment.OTHER);
                    } else {
                        passPhaseToTeam(TeamAlignment.PLAYER);
                    }
                    break;
                case OTHER_PHASE:
                    passPhaseToTeam(TeamAlignment.PLAYER);
                    break;
            }
        }

        private void passPhaseToTeam(TeamAlignment team) {
            parent.teams().resetTeams();
            switch (team) {
                case PLAYER:
                    if(parent.victoryConditionsAreSatisfied() && parent.turnCount() != 0) {
                        Gdx.app.log("conditions", "You win!");

                        parent.game.activeOLDGridScreen.checkForStageCleared(); // TODO: this needs to read from children

                        // TODO: do i need to unload this old screen somehow?

                        // The following is debug code that will only run if
                        // child classes are not implemented properly.
                        OLD_MapScreen screen = new OLD_MapScreen(parent.game);
                        parent.game.activeScreenAdapter = screen;
                        parent.game.activeOLDGridScreen = null;
                        parent.game.setScreen(screen);
                        // --END--
                    } else {
                        Gdx.app.log("phase: ", "Player Phase");
                        nextTurn();
                        currentPhase = Phase.PLAYER_PHASE;
                    }
                    break;
                case ALLY:
                    Gdx.app.log("phase: ", "Ally Phase");
                    currentPhase = Phase.ALLY_PHASE;
                    break;
                case ENEMY:
                    Gdx.app.log("phase: ", "Enemy Phase");
                    currentPhase = Phase.ENEMY_PHASE;
                    break;
                case OTHER:
                    Gdx.app.log("phase: ", "Other Phase");
                    currentPhase = Phase.OTHER_PHASE;
                    break;
            }
        }

        public Phase getCurrentPhase() {
            return currentPhase;
        }
    }
}
