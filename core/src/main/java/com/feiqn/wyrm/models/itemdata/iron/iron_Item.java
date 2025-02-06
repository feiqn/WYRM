package com.feiqn.wyrm.models.itemdata.iron;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponCatalogue;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponRank;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponCategory;

public class iron_Item {

    protected boolean blankItem;

    public String name;
    public iron_ItemType ironItemType;

    public Image image;

    // --WEAPON VARIABLES--
    protected WeaponCategory weaponCategory;
    protected WeaponRank weaponRank;

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

    public iron_Item(iron_ItemType type) {
        blankItem = true;
        if(type == iron_ItemType.UtilityItem) {
            utilityItemInit();
        } else if(type == iron_ItemType.Weapon) {
            weaponInit();
        }
    }

    private void utilityItemInit() {
        this.name = "";
        this.ironItemType = iron_ItemType.UtilityItem;
        weight = 0;

        image = new Image();
    }

    private void weaponInit() {
        ironItemType = iron_ItemType.Weapon;
        weaponCategory = WeaponCategory.HANDS; // weapons are hands by default, be sure to declare type in subclasses.
        catalogueID = WeaponCatalogue.HANDS;

        name = "Hands";

        range = 1;
        strengthBonus = 0;
        defenseBonus = 0;
        dexterityBonus = 0;
        healthBonus = 0;
        weight = 0;
        weaponAccuracy = 100;
        weaponRank = WeaponRank.F;
    }

    // --GETTERS--
    public WeaponRank getWeaponLevel() {return weaponRank;}
    public int getWeaponAccuracy() {return weaponAccuracy;}
    public int getRange() {return range;}
    public int getStrengthBonus() {return strengthBonus;}
    public int getDefenseBonus() {return defenseBonus;}
    public int getDexterityBonus() {return dexterityBonus;}
    public int getHealthBonus() {return healthBonus;}
    public float getWeight() {return weight;}
}
