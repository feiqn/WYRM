package com.feiqn.wyrm.models.unitdata.units.player;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses.PlaneswalkerKlass;
import com.feiqn.wyrm.models.unitdata.Abilities;
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
        simple_Defense    = 3;
        simple_Health     = 5;
        simple_Magic      = 2;
        simple_Resistance = 2;
        simple_Strength   = 2;
        rollingHP = modifiedSimpleHealth();

        ability = Abilities.DIVE_BOMB;
    }

    public void dismount() {
        assert simpleKlass instanceof PlaneswalkerKlass;
        ((PlaneswalkerKlass) simpleKlass).dismount();

        setDrawable(new TextureRegionDrawable(game.assetHandler.leifUnmountedTexture));
        setSize(1,1);
    }

    public void mount() {
        assert simpleKlass instanceof PlaneswalkerKlass;
        ((PlaneswalkerKlass) simpleKlass).mount();

        setDrawable(new TextureRegionDrawable(game.assetHandler.pegKnightTexture));
        setSize(1,1.5f);
    }

    @Override
    public Array<Abilities> getAbilities() {
        assert simpleKlass instanceof PlaneswalkerKlass;
        if(((PlaneswalkerKlass) simpleKlass).isMounted()) return super.getAbilities();

        final Array<Abilities> returnValue = new Array<>();
        if(simpleWeapon.getAbility() != null) {
            returnValue.add(simpleWeapon.getAbility());
        }
        return returnValue;
    }

}
