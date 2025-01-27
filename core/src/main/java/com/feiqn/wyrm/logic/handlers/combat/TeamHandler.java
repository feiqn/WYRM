package com.feiqn.wyrm.logic.handlers.combat;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.Unit;
import org.jetbrains.annotations.NotNull;

public class TeamHandler {
    // Handled by BattleScreen
    // Tracks all active teams for an instance of a battle screen

    private final WYRMGame game;

    protected boolean allyTeamUsed,
                      otherTeamUsed;

    private final Array<Unit> playerTeam,
                              enemyTeam,
                              allyTeam,
                              otherTeam;

    public TeamHandler(WYRMGame game) {
        this.game = game;

        allyTeamUsed    = false;
        otherTeamUsed   = false;

        playerTeam       = new Array<>();
        enemyTeam        = new Array<>();
        allyTeam         = new Array<>();
        otherTeam        = new Array<>();
    }

    public void resetTeams() {
        resetTeam(playerTeam);
        resetTeam(enemyTeam);
        if(otherTeamUsed) resetTeam(otherTeam);
        if(allyTeamUsed) resetTeam(allyTeam);
    }

    private void resetTeam(@NotNull Array<Unit> team) {
        for(Unit unit : team) {
            unit.setCanMove();
        }
    }

    public void escapeUnit(Unit unit) {
        switch(unit.getTeamAlignment()) {
            case PLAYER:
                if(playerTeam.contains(unit, true)) {
                    playerTeam.removeValue(unit,true);
                    queueRemoval(unit);
                }
                break;
            case ENEMY:
                if(enemyTeam.contains(unit,true)) {
                    enemyTeam.removeValue(unit,true);
                    queueRemoval(unit);
                }
                break;
            case ALLY:
                if(allyTeamUsed) {
                    if(allyTeam.contains(unit, true)) {
                        allyTeam.removeValue(unit,true);
                        queueRemoval(unit);
                    }
                }
                break;
            case OTHER:
                if(otherTeamUsed) {
                    if(otherTeam.contains(unit,true)) {
                        otherTeam.removeValue(unit, true);
                        queueRemoval(unit);
                    }
                }
                break;
        }
        unit.occupyingTile.setUnoccupied();
    }

    public void queueRemoval(Unit unit) {
        unit.setPosition(-50, -50); // TODO: Yo..........
    }

    public void removeUnitFromTeam(Unit unit) {
        switch(unit.getTeamAlignment()) {
            case OTHER:
                if(otherTeam.contains(unit, true)) {
                    otherTeam.removeValue(unit,true);
                }
                break;
            case ALLY:
                if(allyTeam.contains(unit,true)) {
                    allyTeam.removeValue(unit,true);
                }
                break;
            case PLAYER:
                if(playerTeam.contains(unit,true)) {
                    playerTeam.removeValue(unit, true);
                }
                break;
            case ENEMY:
                if(enemyTeam.contains(unit,true)) {
                    enemyTeam.removeValue(unit,true);
                }
                break;
        }
    }

    // --SETTERS--
    public void setAllyTeamUsed() { allyTeamUsed = true; }
    public void setOtherTeamUsed() { otherTeamUsed = true; }

    // --GETTERS--
    public boolean allyTeamIsUsed() { return allyTeamUsed; }
    public boolean otherTeamIsUsed() { return  otherTeamUsed; }
    public Array<Unit> getEnemyTeam() {return enemyTeam;}
    public Array<Unit> getPlayerTeam() {return playerTeam;}
    public Array<Unit> getAllyTeam() {return allyTeam;}
    public Array<Unit> getOtherTeam() {return otherTeam;}
    public Array<Unit> currentTeam() {
        //noinspection EnhancedSwitchMigration
        switch(game.activeGridScreen.conditionsHandler.getUpdatedPhase()) {
            case OTHER_PHASE:
                return  otherTeam;
            case ENEMY_PHASE:
                return  enemyTeam;
            case ALLY_PHASE:
                return  allyTeam;
            case PLAYER_PHASE:
            default:
                return playerTeam;
        }
    }

}
