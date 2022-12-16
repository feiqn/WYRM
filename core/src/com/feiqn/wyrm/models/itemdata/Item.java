package com.feiqn.wyrm.models.itemdata;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponCatalogue;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponType;

public class Item extends Actor {

    public WYRMGame game;
    public String name;
    public ItemType itemType;

    public Image image;

    // --WEAPON VARIABLES--
    protected WeaponType weaponType;
    protected WeaponCatalogue catalogueID;
    protected int strengthBonus,
                  defenseBonus,
                  skillBonus,
                  healthBonus;
    protected float weight; // weight negatively affects speed. Give a negative weight value to apply a speed bonus instead.
    // --END WEAPON VARIABLES--

    public Item(WYRMGame game, ItemType type) {
        this.game = game;
        if(type == ItemType.UtilityItem) {
            utilityItemInit();
        } else if(type == ItemType.Weapon) {
            weaponInit();
        }
    }

    private void utilityItemInit() {
        this.name = "";
        this.itemType = ItemType.UtilityItem;

        image = new Image();
    }

    private void weaponInit() {
        itemType = ItemType.Weapon;
        weaponType = WeaponType.HANDS; // weapons are hands by default, be sure to declare type in subclasses.
        catalogueID = WeaponCatalogue.HANDS;

        name = "Hands";

        strengthBonus = 0;
        defenseBonus = 0;
        skillBonus = 0;
        healthBonus = 0;
        weight = 0;
    }

    // --GETTERS--
    public int getStrengthBonus() {return strengthBonus;}
    public int getDefenseBonus() {return defenseBonus;}
    public int getSkillBonus() {return skillBonus;}
    public int getHealthBonus() {return healthBonus;}
    public float getWeight() {return weight;}
}
