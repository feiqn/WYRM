package com.feiqn.wyrm.models.unitdata.units.player;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses.PlaneswalkerKlass;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class LeifUnit extends SimpleUnit {

    public LeifUnit(WYRMGame game) {
        super(game, game.assetHandler.pegKnightTexture);

        name = "Leif";
        teamAlignment = TeamAlignment.PLAYER;
        rosterID = UnitRoster.LEIF;

        simpleKlass = new PlaneswalkerKlass();

        simple_Speed      = 4;
        simple_Defense    = 2;
        simple_Health     = 10;
        simple_Magic      = 2;
        simple_Resistance = 2;
        simple_Strength   = 2;
        rollingHP = simple_Health;
    }

}
