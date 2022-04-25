package com.feiqn.wyrm.models.weapondata;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;

public class Weapon extends Image {

    public WYRMGame game;

    public String name;

    private WeaponType weaponType;

    private int strengthBonus,
                defenseBonus,
                skillBonus,
                healthBonus,
                weight; // weight negatively affects speed. Give a negative weight value to apply a speed bonus instead.


    public Weapon(WYRMGame game) {
        super();
        this.game = game;
        SharedInit();
    }

    public Weapon(WYRMGame game, Texture texture) {
        super(texture);
        this.game = game;
        SharedInit();
    }

    public Weapon(WYRMGame game, TextureRegion region) {
        super(region);
        this.game = game;
        SharedInit();
    }

    private void SharedInit() {
        weaponType = WeaponType.HANDS; // weapons are hands by default, be sure to declare type in subclasses.

        name = "Hands";

        strengthBonus = 0;
        defenseBonus = 0;
        skillBonus = 0;
        healthBonus = 0;
        weight = 0;
    }
}
