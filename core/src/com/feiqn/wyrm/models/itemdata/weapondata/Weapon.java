package com.feiqn.wyrm.models.itemdata.weapondata;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.Item;
import com.feiqn.wyrm.models.itemdata.ItemType;

public class Weapon extends Item {

    protected WeaponType weaponType;

    protected int strengthBonus,
                defenseBonus,
                skillBonus,
                healthBonus;

    protected float weight; // weight negatively affects speed. Give a negative weight value to apply a speed bonus instead.


    public Weapon(WYRMGame game) {
        super(game);
        this.game = game;
        SharedInit();
    }

    private void SharedInit() {
        itemType = ItemType.Weapon;
        weaponType = WeaponType.HANDS; // weapons are hands by default, be sure to declare type in subclasses.

        name = "Hands";

        strengthBonus = 0;
        defenseBonus = 0;
        skillBonus = 0;
        healthBonus = 0;
        weight = 0;
    }
}
