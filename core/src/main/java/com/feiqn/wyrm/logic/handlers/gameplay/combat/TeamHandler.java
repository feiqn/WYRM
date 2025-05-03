package com.feiqn.wyrm.logic.handlers.gameplay.combat;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
import org.jetbrains.annotations.NotNull;

public class TeamHandler {
    // Handled by BattleScreen
    // Tracks all active teams for an instance of a battle screen

    protected boolean allyTeamUsed,
                      otherTeamUsed;

    private final Array<SimpleUnit> playerTeam,
                              enemyTeam,
                              allyTeam,
                              otherTeam;

    public TeamHandler() {

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

    private void resetTeam(@NotNull Array<SimpleUnit> team) {
        for(SimpleUnit unit : team) {
            unit.setCanMove();
        }
    }

    public void escapeUnit(SimpleUnit unit) {
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

    public void queueRemoval(SimpleUnit unit) {
        unit.remove();
    }

    public void removeUnitFromTeam(SimpleUnit unit) {
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

    public void addUnitToTeam(SimpleUnit unit) {
        addUnitToTeam(unit, unit.getTeamAlignment());
    }

    public void addUnitToTeam(SimpleUnit unit, TeamAlignment team) {
        switch(team) {
            case PLAYER:
                playerTeam.add(unit);
                break;
            case ALLY:
                allyTeam.add(unit);
                if(!allyTeamUsed) allyTeamUsed = true;
                break;
            case ENEMY:
                enemyTeam.add(unit);
                break;
            case OTHER:
                otherTeam.add(unit);
                if(!otherTeamUsed) otherTeamUsed = true;
                break;
        }
    }

    // --SETTERS--
    public void setAllyTeamUsed() { allyTeamUsed = true; }
    public void setOtherTeamUsed() { otherTeamUsed = true; }

    // --GETTERS--
    public boolean allyTeamIsUsed() { return allyTeamUsed; }
    public boolean otherTeamIsUsed() { return  otherTeamUsed; }
    public Array<SimpleUnit> getEnemyTeam() {return enemyTeam;}
    public Array<SimpleUnit> getPlayerTeam() {return playerTeam;}
    public Array<SimpleUnit> getAllyTeam() {return allyTeam;}
    public Array<SimpleUnit> getOtherTeam() {return otherTeam;}
//    public Array<SimpleUnit> currentTeam() {
//        //noinspection EnhancedSwitchMigration
//        switch(game.activeGridScreen.conditionsHandler.getCurrentPhase()) {
//            case OTHER_PHASE:
//                return  otherTeam;
//            case ENEMY_PHASE:
//                return  enemyTeam;
//            case ALLY_PHASE:
//                return  allyTeam;
//            case PLAYER_PHASE:
//            default:
//                return playerTeam;
//        }
//    }

}
