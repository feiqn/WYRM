package com.feiqn.wyrm.OLD_DATA.models.unitdata.units.enemy.generic;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.prefabklasses.generic.SoldierKlass;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitIDRoster;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.OLD_SimpleUnit;

public class SoldierUnitOLD extends OLD_SimpleUnit {

    public SoldierUnitOLD(WYRMGame game) {
        super(game, WYRMGame.assets().soldierTexture);

        characterName = "Soldier";
        rosterID = UnitIDRoster.SOLDIER;

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
