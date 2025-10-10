package com.feiqn.wyrm.models.unitdata.units.enemy.generic;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class CavalryUnit extends SimpleUnit {

    public CavalryUnit(WYRMGame game) {
        super(game, game.assetHandler.pegKnightTexture); // TODO: HORSE

        name = "Cavalier";
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
