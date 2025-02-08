package com.feiqn.wyrm.models.unitdata.units.enemy.generic;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses.SoldierKlass;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class SoldierUnit extends SimpleUnit {

    public SoldierUnit(WYRMGame game) {
        super(game, game.assetHandler.soldierTexture);

        name = "Soldier";
        teamAlignment = TeamAlignment.ENEMY;
        rosterID = UnitRoster.GENERIC_SOLDIER;

        simpleKlass = new SoldierKlass();

        simple_Speed      = 3;
        simple_Defense    = 2;
        simple_Health     = 4;
        simple_Magic      = 2;
        simple_Resistance = 2;
        simple_Strength   = 2;
        rollingHP = simple_Health;
    }

}
