package com.feiqn.wyrm.models.unitdata.units.enemy.recruitable;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses.unique.BossKlass;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class TohniUnit extends SimpleUnit {

    public TohniUnit(WYRMGame game) {
        super(game, game.assetHandler.mercenaryTexture); // TODO: replace texture

        setSize(1,1);

        name = "Tohni";
        rosterID = UnitRoster.TOHNI;

        bio = "The leader of the Wall Mob.";

        simpleKlass = new BossKlass();

        simple_Resistance = 3;
        simple_Speed      = 4;
        simple_Defense    = 4;
        simple_Health     = 6;
        simple_Magic      = 1;
        simple_Strength   = 1;
        rollingHP = modifiedSimpleHealth();

        ability = Abilities.LEADERSHIP;
    }

}
