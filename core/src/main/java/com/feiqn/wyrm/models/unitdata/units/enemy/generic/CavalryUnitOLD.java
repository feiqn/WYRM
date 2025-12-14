package com.feiqn.wyrm.models.unitdata.units.enemy.generic;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;

public class CavalryUnitOLD extends OLD_SimpleUnit {

    public CavalryUnitOLD(WYRMGame game) {
        super(game, WYRMGame.assets().pegKnightTexture); // TODO: HORSE

        characterName = "Cavalier";
        rosterID = UnitRoster.GENERIC_CAVALRY;

        simple_Speed      = 4;
        simple_Defense    = 2;
        simple_Health     = 4;
        simple_Magic      = 2;
        simple_Resistance = 2;
        simple_Strength   = 1;
        rollingHP = simple_Health;

        generateAnimations();
    }
}
