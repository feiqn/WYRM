package com.feiqn.wyrm.models.itemdata;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponCatalogue;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponLevel;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponType;

public class Item extends Actor {

    protected boolean blankItem;

    public WYRMGame game;
    public String name;
    public ItemType itemType;

    public Image image;

    // --WEAPON VARIABLES--
    protected WeaponType weaponType;
    protected WeaponLevel weaponLevel;

    protected int weaponAccuracy,
                  weaponStrength;

    protected int range;
    protected WeaponCatalogue catalogueID;
    protected int strengthBonus,
                  defenseBonus,
                  dexterityBonus,
                  healthBonus;
    protected int weight; // weight negatively affects speed

    // protected WeaponEffect effect = WeaponEffect.NONE;
    // --END WEAPON VARIABLES--

    public Item(WYRMGame game, ItemType type) {
        this.game = game;
        blankItem = true;
        if(type == ItemType.UtilityItem) {
            utilityItemInit();
        } else if(type == ItemType.Weapon) {
            weaponInit();
        }
    }

    private void utilityItemInit() {
        this.name = "";
        this.itemType = ItemType.UtilityItem;
        weight = 0;

        image = new Image();
    }

    private void weaponInit() {
        itemType = ItemType.Weapon;
        weaponType = WeaponType.HANDS; // weapons are hands by default, be sure to declare type in subclasses.
        catalogueID = WeaponCatalogue.HANDS;

        name = "Hands";

        range = 1;
        strengthBonus = 0;
        defenseBonus = 0;
        dexterityBonus = 0;
        healthBonus = 0;
        weight = 0;
        weaponAccuracy = 100;
        weaponLevel = WeaponLevel.F;
    }

    // --GETTERS--
    public WeaponLevel getWeaponLevel() {return weaponLevel;}
    public int getWeaponAccuracy() {return weaponAccuracy;}
    public int getRange() {return range;}
    public int getStrengthBonus() {return strengthBonus;}
    public int getDefenseBonus() {return defenseBonus;}
    public int getDexterityBonus() {return dexterityBonus;}
    public int getHealthBonus() {return healthBonus;}
    public float getWeight() {return weight;}
}
