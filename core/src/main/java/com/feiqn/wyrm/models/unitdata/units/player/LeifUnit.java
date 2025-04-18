package com.feiqn.wyrm.models.unitdata.units.player;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses.PlaneswalkerKlass;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class LeifUnit extends SimpleUnit {

    public LeifUnit(WYRMGame game) {
        super(game, game.assetHandler.pegKnightTexture);

        setSize(1, 1.5f);

        name = "Leif";
        teamAlignment = TeamAlignment.PLAYER;
        rosterID = UnitRoster.LEIF;

        bio = "A displaced youth with a knack for animal husbandry.";

        simpleKlass = new PlaneswalkerKlass();

        simple_Speed      = 4;
        simple_Defense    = 2;
        simple_Health     = 5;
        simple_Magic      = 2;
        simple_Resistance = 2;
        simple_Strength   = 2;
        rollingHP = simple_Health;
    }

    public void dismount() {
        assert simpleKlass instanceof PlaneswalkerKlass;
        ((PlaneswalkerKlass) simpleKlass).dismount();
    }

    public void mount() {
        assert simpleKlass instanceof PlaneswalkerKlass;
        ((PlaneswalkerKlass) simpleKlass).mount();
    }

}
