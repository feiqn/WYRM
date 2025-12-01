package com.feiqn.wyrm.models.unitdata.units.enemy.generic;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses.generic.SoldierKlass;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class SoldierUnit extends SimpleUnit {

    public SoldierUnit(WYRMGame game) {
        super(game, game.assetHandler.soldierTexture);

        characterName = "Soldier";
        rosterID = UnitRoster.GENERIC_SOLDIER;

        simpleKlass = new SoldierKlass();

        simple_Speed      = 3;
        simple_Defense    = 2;
        simple_Health     = 4;
        simple_Magic      = 1;
        simple_Resistance = 1;
        simple_Strength   = 2;
        rollingHP = modifiedSimpleHealth();

        generateAnimations();
    }
}
