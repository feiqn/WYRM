package com.feiqn.wyrm.models.unitdata.classdata;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponType;
import com.feiqn.wyrm.models.unitdata.MovementType;

import java.util.HashMap;

public class UnitClass {

    private WYRMGame game;

    public UnitClassList classType;

    public MovementType movementType;

//    public HashMap<WeaponType, >

    public String name;

    public UnitClass(WYRMGame game) {
        this.game = game;

        classType = UnitClassList.DRAFTEE;

        movementType = MovementType.INFANTRY;

        name = "Draftee";

    }
}
