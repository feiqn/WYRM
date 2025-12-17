package com.feiqn.wyrm.models.unitdata.units.player;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses.unique.PlaneswalkerKlass;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitRoster;

public class LeifUnitOLD extends OLD_SimpleUnit {

    private Boolean mountUnavailable;

    public LeifUnitOLD(WYRMGame game) {
        super(game, WYRMGame.assets().pegKnightTexture);

//        setSize(1, 1.25f); // starts out mounted

        characterName = "Leif";
        bio = "A displaced youth with a knack for animal husbandry.";

        teamAlignment = TeamAlignment.PLAYER;

        rosterID = UnitRoster.LEIF_MOUNTED;

        generateAnimations();

        simpleKlass = new PlaneswalkerKlass();

        simple_Speed      = 4;
        simple_Defense    = 3;
        simple_Health     = 5;
        simple_Magic      = 2;
        simple_Resistance = 2;
        simple_Strength   = 2;
        rollingHP = modifiedSimpleHealth();

        mountUnavailable = false;
        ability = Abilities.DIVE_BOMB;
    }



    public void dismount() {
        assert simpleKlass instanceof PlaneswalkerKlass;
        ((PlaneswalkerKlass) simpleKlass).dismount();
        rosterID = UnitRoster.LEIF;
        generateAnimations();

//        setDrawable(new TextureRegionDrawable(game.assetHandler.leifUnmountedTexture));
        setSize(1,1);
    }

    public void mount() {
        if(!mountUnavailable) {
            assert simpleKlass instanceof PlaneswalkerKlass;
            ((PlaneswalkerKlass) simpleKlass).mount();
            rosterID = UnitRoster.LEIF_MOUNTED;
            generateAnimations();

//            setDrawable(new TextureRegionDrawable(game.assetHandler.pegKnightTexture));
            setSize(1,1.25f);
        }
    }

    public void lockMount() {
        mountUnavailable = true;
    }

    public void unlockMount() {
        mountUnavailable = false;
    }

    @Override
    public Array<Abilities> getAbilities() {

        if(!mountUnavailable) {
            assert simpleKlass instanceof PlaneswalkerKlass;
            if(((PlaneswalkerKlass) simpleKlass).isMounted()) return super.getAbilities();
        }

        final Array<Abilities> returnValue = new Array<>();
        if(simpleWeapon.getAbility() != null) {
            returnValue.add(simpleWeapon.getAbility());
        }
        return returnValue;
    }

    public boolean mountLocked() {
        return mountUnavailable;
    }

    @Override
    public void idle() {
        super.idle();
        if(rosterID == UnitRoster.LEIF_MOUNTED) {

            // TODO: LogicalTile.getCenterCoordinate() - this.width * .5f

            this.setSize(1,1.25f);
            this.setPosition(column, row);
            wide = false;
        } else {
            this.setSize(1, 1);
            this.setPosition(column, row);
            wide = false;
        }
    }

    @Override
    public void flourish() {
        super.flourish();
//        if(rosterID == UnitRoster.LEIF_MOUNTED) {
            this.setPosition(column - .5f, row);
            this.setSize(1.7f,1.7f);
            wide = true;
//        }
    }

    @Override
    public void faceWest() {
        super.faceWest();
        if(rosterID == UnitRoster.LEIF_MOUNTED) {
            this.setSize(2,2);
            wide = true;
        } else {
            this.setSize(1.7f,1.7f);
            wide = true;
        }
    }

    @Override
    public void faceEast() {
        super.faceEast();
        if(rosterID == UnitRoster.LEIF_MOUNTED) {
            this.setSize(2,2);
            wide = true;
        } else {
            this.setSize(1.7f,1.7f);
            wide = true;
        }
    }

    @Override
    public void faceNorth() {
        super.faceNorth();
        if(rosterID == UnitRoster.LEIF_MOUNTED) {
            this.setSize(2,2);
            wide = true;
        } else {
            this.setSize(1.7f,1.7f);
            wide = true;
        }
    }

    @Override
    public void faceSouth() {
        super.faceSouth();
        if(rosterID == UnitRoster.LEIF_MOUNTED) {
            this.setSize(2,2);
            wide = true;
        } else {
            this.setSize(1.7f,1.7f);
            wide = true;
        }
    }

}
