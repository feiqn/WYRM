package com.feiqn.wyrm.models.itemdata.weapondata;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.EquipEffect;

public class SimpleEquipment {

    private final WYRMGame game;

    /** Weapons and armor may have bonus 0..10, and an optional effect. <br>
     *  Accessories may only have effects, not bonuses.
     */
    public enum Type {
        WEAPON,
        ARMOR,
        ACCESSORY
    }

    protected int bonusStrength;
    protected int bonusDefense;
    protected int bonusSpeed;
    protected int bonusHealth;
    protected int bonusMagic;
    protected int bonusResistance;

    protected String name;

    protected Type type;

    protected EquipEffect effect;

    protected Drawable drawable;

    public SimpleEquipment(WYRMGame game) {
        this(game, Type.WEAPON);
    }

    public SimpleEquipment(WYRMGame game, Type type) {
        this.game = game;

        bonusStrength   = 0;
        bonusDefense    = 0;
        bonusHealth     = 0;
        bonusMagic      = 0;
        bonusResistance = 0;
        bonusSpeed      = 0;

        switch(type) {
            case WEAPON:
                name = "fists";
                break;
            case ARMOR:
                name = "cloth";
                break;
            case ACCESSORY:
                name = "none";
        }
        this.type = type;
        effect = EquipEffect.NONE;
        // drawable = new TextureRegionDrawable( game.assetHandler.fistsTexture )
    }

    public int bonusDefense() {
        return bonusDefense;
    }
    public int bonusHealth() {
        return bonusHealth;
    }
    public int bonusMagic() {
        return bonusMagic;
    }
    public Drawable drawable() {
        return drawable;
    }
    public EquipEffect effect() {
        return effect;
    }
    public int bonusResistance() {
        return bonusResistance;
    }
    public int bonusSpeed() {
        return bonusSpeed;
    }
    public int bonusStrength() {
        return bonusStrength;
    }
    public String name() {
        return name;
    }
    public Type type() {
        return type;
    }

}
