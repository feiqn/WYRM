package com.feiqn.wyrm.OLD_DATA.models.unitdata.units.enemy.recruitable;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.prefabklasses.unique.BossKlass;
import com.feiqn.wyrm.OLD_DATA.OLD_UnitIDRoster;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.OLD_SimpleUnit;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class TohniUnitOLD extends OLD_SimpleUnit {

    public TohniUnitOLD(WYRMGame game) {
        super(game, WYRMGame.assets().mercenaryTexture); // TODO: replace texture

        setSize(1,1);

        characterName = "Tohni";
        rosterID = OLD_UnitIDRoster.TOHNI;

        bio = "The leader of the Wall Mob.";

        simpleKlass = new BossKlass();

        simple_Resistance = 3;
        simple_Speed      = 4;
        simple_Defense    = 4;
        simple_Health     = 6;
        simple_Magic      = 1;
        simple_Strength   = 1;
        rollingHP = modifiedSimpleHealth();

        ability = WyRPG.AbilityID.FIRELIGHTER;
        generateAnimations();
    }

}
