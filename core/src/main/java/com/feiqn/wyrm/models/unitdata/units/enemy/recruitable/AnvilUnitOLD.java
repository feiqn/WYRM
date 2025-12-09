package com.feiqn.wyrm.models.unitdata.units.enemy.recruitable;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses.unique.CaptainKlass;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;

public class AnvilUnitOLD extends OLD_SimpleUnit {

    private Boolean mountUnavailable;

    public AnvilUnitOLD(WYRMGame game) {
        super(game, game.assetHandler.mercenaryTexture); // TODO: different drawable

        setSize(1,1);

        characterName = "Anvil";
        rosterID = UnitRoster.ANVIL;

        bio = "The Captain of the Walled City Guard";

        simpleKlass = new CaptainKlass();

        simple_Speed      = 5;
        simple_Defense    = 4;
        simple_Health     = 7;
        simple_Magic      = 2;
        simple_Resistance = 3;
        simple_Strength   = 3;
        rollingHP = modifiedSimpleHealth();

        mountUnavailable = false;
        ability = Abilities.WARRANT;

        generateAnimations();
    }

//    public void dismount() {
//        assert simpleKlass instanceof PlaneswalkerKlass;
//        ((PlaneswalkerKlass) simpleKlass).dismount();
//
//
//        setDrawable(new TextureRegionDrawable(game.assetHandler.leifUnmountedTexture));
//        setSize(1,1);
//    }
//
//    public void mount() {
//        if(!mountUnavailable) {
//            assert simpleKlass instanceof PlaneswalkerKlass;
//            ((PlaneswalkerKlass) simpleKlass).mount();
//
//            setDrawable(new TextureRegionDrawable(game.assetHandler.pegKnightTexture));
//            setSize(1,1.5f);
//        }
//    }
}
