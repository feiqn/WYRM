package com.feiqn.wyrm.logic.handlers.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneHandler;
import com.feiqn.wyrm.logic.handlers.gameplay.combat.CombatHandler;
import com.feiqn.wyrm.logic.handlers.gameplay.combat.TeamHandler;
import com.feiqn.wyrm.logic.screens.MapScreen;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.battleconditions.Phase;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.battleconditions.BattleConditionRegister;

public class ConditionsHandler {
    // Handled by BattleScreen
    // Tracks environmental and logistical parameters for the active instance of a grid screen

    private final WYRMGame game;

    private final TeamHandler teamHandler;
    private final CombatHandler combatHandler;
    private final CutsceneHandler cutsceneHandler;

    private final BattleConditionRegister conditions;


    public ConditionsHandler(WYRMGame game) {
        this.game = game;

        conditions = new BattleConditionRegister();

        teamHandler = new TeamHandler();
        combatHandler = new CombatHandler(game);
        cutsceneHandler = new CutsceneHandler(game);
    }

    public void addVictoryCondition(VictoryCondition victCon) {
        conditions.victoryConditions.add(victCon);
        game.activeGridScreen.hud().reset();
    }

//    public void addFailureCondition(FailureCondition failCon) {}

    public void clearTurnOrder() {
        conditions.battleRoster.clear();
        calculateTurnOrder();
    }

    public void addToTurnOrder(SimpleUnit unit) {
        if(!conditions.battleRoster.contains(unit, true)) {
//            Gdx.app.log("added to turn order:", unit.name);
            conditions.battleRoster.add(unit);
            calculateTurnOrder();
        }
    }

    public void removeFromTurnOrder(SimpleUnit unit) {
        if(conditions.battleRoster.contains(unit, true)) {
            conditions.battleRoster.removeValue(unit, true);
            calculateTurnOrder();
        }
    }

    private void advanceTurn() {
        conditions.currentTurnNumber++;

        cutsceneHandler.checkTurnTriggers(conditions.currentTurnNumber);

        for(SimpleUnit unit : conditions.unifiedTurnOrder) {
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

        conditions.unifiedTurnOrder = new Array<>();

        final Array<SimpleUnit> segregatedPlayer = new Array<>();
        final Array<SimpleUnit> segregatedEnemy = new Array<>();
        final Array<SimpleUnit> segregatedAlly = new Array<>();
        final Array<SimpleUnit> segregatedOther = new Array<>();

        for(SimpleUnit u : conditions.battleRoster) {
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
            for(SimpleUnit p : segregatedPlayer) {
                if(p.modifiedSimpleSpeed() == i) conditions.unifiedTurnOrder.add(p);
            }
            for(SimpleUnit p : segregatedEnemy) {
                if(p.modifiedSimpleSpeed() == i) conditions.unifiedTurnOrder.add(p);
            }
            for(SimpleUnit p : segregatedAlly) {
                if(p.modifiedSimpleSpeed() == i) conditions.unifiedTurnOrder.add(p);
            }
            for(SimpleUnit p : segregatedOther) {
                if(p.modifiedSimpleSpeed() == i) conditions.unifiedTurnOrder.add(p);
            }
        }

        for (SimpleUnit unit : conditions.unifiedTurnOrder) {
            Gdx.app.log("unified order", unit.characterName + " " + unit.modifiedSimpleSpeed());
        }

        try {
            game.activeGridScreen.hud().updateTurnOrderPanel(); // maybe unneeded, didn't test
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
        for(VictoryCondition victCon : conditions.victoryConditions) {
            if(victCon.getAssociatedFlag() == flagID) {
                victCon.satisfy();
                Gdx.app.log("conditions", "satisfied");
            }
        }
    }

    public void revealVictCon(CampaignFlags flagID) {
        for(VictoryCondition victCon : conditions.victoryConditions) {
            if(victCon.getAssociatedFlag() == flagID) {
                victCon.reveal();
                game.activeGridScreen.hud().updateVictConPanel();
            }
        }


    }

    public SimpleUnit whoseNextInLine() {
        for(int i = 0; i < unifiedTurnOrder().size; i++) {
            if(conditions.unifiedTurnOrder.get(i).canMove()) {
                return conditions.unifiedTurnOrder.get(i);
            }
        }
        Gdx.app.log("whoseNext", "Looks like everyone has moved, calling for new turn.");
        advanceTurn();
        return whoseNextInLine();
    }

    // ---GETTERS---
    public CutsceneHandler conversations() { return cutsceneHandler; }
    public TeamHandler teams() { return teamHandler; }
    public CombatHandler combat() { return combatHandler; }

    public int turnCount() { return conditions.currentTurnNumber; }
    public int tickCount() { return whoseNextInLine().modifiedSimpleSpeed(); }
    public Array<SimpleUnit> unifiedTurnOrder() { return conditions.unifiedTurnOrder; }
    public VictoryCondition getVictoryCondition(CampaignFlags flagID) {
        for(VictoryCondition victCon : conditions.victoryConditions) {
            if(victCon.getAssociatedFlag() == flagID) return victCon;
        }
        return null;
    }
    public Array<VictoryCondition> getVictoryConditions() {
        final Array<VictoryCondition> returnValue = new Array<>();

        for(VictoryCondition vc : conditions.victoryConditions) {
            returnValue.add(vc);
        }

        return returnValue;
    }
    public boolean victoryConditionIsSatisfied(CampaignFlags flagID) {
        for(VictoryCondition victCon : conditions.victoryConditions) {
            if(victCon.getAssociatedFlag() == flagID) {
                return victCon.conditionIsSatisfied();
            }
        }
        return false;
    }

    // --CALCULATORS--
    public IronMode iron() {
        if(!conditions.ironModeBTW) {
//            register.ironMode = new IronMode(this);
            conditions.ironModeBTW = true;
        }
        return new IronMode(this);
    }
    public boolean victoryConditionsAreSatisfied() {
        boolean allConsSatisfied = true;
        conditions.terminalVictoryConditionMet = false;

        for(VictoryCondition victcon : conditions.victoryConditions) {
            if(!victcon.conditionIsSatisfied()) {
                allConsSatisfied = false;
            } else if(victcon.conditionIsSatisfied() && victcon.isTerminal()) {
                conditions.terminalVictoryConditionMet = true;
            }
        }

        return allConsSatisfied || conditions.terminalVictoryConditionMet;
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

        private final ConditionsHandler parent;

        private Phase currentPhase;

        public IronMode(ConditionsHandler parent) {
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

                        parent.game.activeGridScreen.checkForStageCleared(); // TODO: this needs to read from children

                        // TODO: do i need to unload this old screen somehow?

                        // The following is debug code that will only run if
                        // child classes are not implemented properly.
                        MapScreen screen = new MapScreen(parent.game);
                        parent.game.activeScreenAdapter = screen;
                        parent.game.activeGridScreen = null;
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
