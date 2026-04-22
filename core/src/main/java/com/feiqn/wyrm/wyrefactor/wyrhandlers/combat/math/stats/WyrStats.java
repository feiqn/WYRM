package com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.AbilityID;
import com.feiqn.wyrm.wyrefactor.actors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridMovementType;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.personality.WyrPersonality;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.WyrEquipment;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.accessories.WyrAmulet;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.accessories.WyrBracelet;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.accessories.WyrRing;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.armor.WyrArmor;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.weapons.WyrWeapon;
import com.feiqn.wyrm.wyrefactor.actors.items.items.WyrItem;

public final class WyrStats<Personality extends WyrPersonality> {

    private Personality Personality;

    private final Array<WyrStatusCondition> statusConditions = new Array<>();

    private final WyrInventory inventory = new WyrInventory();

    private final WyrActor<?,?> parent;
    private final WyrActor.ActorType parentType;

    private final RPGClass rpgClass = new RPGClass();

    private int actionPointRestoreRate = 1;
    private int actionPoints    = 0;
    private int base_Strength   = 0;
    private int base_Defense    = 0;
    private int base_Magic      = 0;
    private int base_Resistance = 0;
    private int base_Speed      = 0;
    private int base_Health     = 1;
    private int rollingHP       = 0;

    private final Array<AbilityID> abilities = new Array<>();

    /**
     * End of declarations.
     */

    public WyrStats(WyrActor<?,?> parent, WyrActor.ActorType type) {
        this.parent = parent;
        this.parentType = type;
    }

    public void applyCondition(WyrStatusCondition condition) {
        statusConditions.add(condition);
    }

    public void tickDownConditions(boolean harmful) {
        for(WyrStatusCondition condition : statusConditions) {
            condition.tickDownEffect();
            if(condition.effectCounter() <= 0) statusConditions.removeValue(condition, true);
            if(!harmful) continue;
            switch(condition.getEffectType()) {
                // TODO
                case BURN:
                case STUN:
                case CHILL:
                case POISON:
                case PETRIFY:
                case SOUL_BRAND:
                default:
                    break;
            }
        }
    }

    public void applyDamage(int damage) {
        rollingHP -= damage;
        if(rollingHP > getMaxHP()) rollingHP = getMaxHP(); // negative damage can heal
        if(rollingHP <= 0) {
            if(parentType == RPGridActor.ActorType.UNIT) {
                assert parent instanceof RPGridUnit;
                final RPGridUnit parentUnit = (RPGridUnit) parent;
                parentUnit.kill();
            }
            // TODO: break prop
        }
    }

    public void healToFull() { this.rollingHP = getMaxHP(); }

    public void gainAP() { actionPoints++; shaderAPUpdate(); }
    public void spendAP() { actionPoints--; shaderAPUpdate(); }
    public void restoreAP() { actionPoints += actionPointRestoreRate; shaderAPUpdate(); }

    private void shaderAPUpdate() {
        if(actionPoints <= 0) {
            parent.applyShader(RPGridActor.ShaderState.DIM);
        } else {
            parent.applyShader(RPGridActor.ShaderState.STANDARD);
        }
    }

    public void setBaseDefense(int defense)       { this.base_Defense = defense; }
    public void setBaseStrength(int strength)     { this.base_Strength = strength; }
    public void setBaseResistance(int resistance) { this.base_Resistance = resistance; }
    public void setBaseHealth(int health)         { this.base_Health = health; }
    public void setBaseMagic(int magic)           { this.base_Magic = magic; }
    public void setBaseSpeed(int speed)           { this.base_Speed = speed; }
    public void setAPRestoreRate(int i)           { actionPointRestoreRate = i; }
    public void setComputerPersonality(Personality personality) { this.Personality = personality; }

    public int getAPRestoreRate()  { return actionPointRestoreRate; }
    public int getActionPoints()   { return actionPoints; }
    public int getBaseDefense()    { return base_Defense; }
    public int getBaseMagic()      { return base_Magic; }
    public int getBaseHealth()     { return base_Health; }
    public int getBaseStrength()   { return base_Strength; }
    public int getBaseSpeed()      { return base_Speed; }
    public int getBaseResistance() { return base_Resistance; }

    public Personality getPersonality() { return Personality; }
    public Array<WyrStatusCondition> getStatusConditions() { return statusConditions; }
    public RPGridMovementType movementType() { return (rpgClass.isMounted ? getMountedMoveType() : getStandardMoveType()); }
    public RPGClass.RPGClassID classID() { return rpgClass.classID; }

    public int getMaxHP() { return modifiedStatValue(StatType.HEALTH); }
    public int getRollingHP() { return rollingHP; }

    public int modifiedStatValue(StatType stat) {
        switch(stat) {
            case STRENGTH:            // TODO:
                return base_Strength + rpgClass.getStatBonus(StatType.STRENGTH); //  + weapon strength, other bonuses, etc
            case DEFENSE:
                return base_Defense + rpgClass.getStatBonus(StatType.DEFENSE); // ...
            case MAGIC:
                return base_Magic + rpgClass.getStatBonus(StatType.MAGIC); // ...
            case RESISTANCE:
                return base_Resistance + rpgClass.getStatBonus(StatType.RESISTANCE); // ...
            case SPEED:
                return base_Strength + rpgClass.getStatBonus(StatType.SPEED); // ...
            case HEALTH:
                return base_Health + rpgClass.getStatBonus(StatType.HEALTH); // ...

            case DEXTERITY:
            default:
                break;
        }
        return 0;
    }

    public RPGClass getRPGClass() { return this.rpgClass; }
    private RPGridMovementType getStandardMoveType() { return this.rpgClass.standardRPGridMovementType; }
    private RPGridMovementType getMountedMoveType() { return this.rpgClass.mountedRPGridMovementType; }

    public WyrInventory inventory() { return this.inventory;}


    /**
     * Inventory
     */
    public static final class WyrInventory {

        // defines and holds info for an actor's,
        // usually a GridUnit's, equipment slots and
        // loadout. Gear, inventory, etc.

        // probably replaces SimpleInventory

        private static final Array<WyrItem> containers = new Array<>();

        private static WyrAmulet   amuletSlot   = new WyrAmulet();
        private static WyrArmor    armorSlot    = new WyrArmor();
        private static WyrRing     ringSlot     = new WyrRing();
        private static WyrWeapon   weaponSlot   = new WyrWeapon();
        private static WyrBracelet braceletSlot = new WyrBracelet();

        public WyrInventory() {}

        public int combinedGearModifiersValue(StatType stat) {
            // Add values from all relevant gear then return total.
            switch(stat) {
                case STRENGTH:
                case DEXTERITY:
                case DEFENSE:

                case MAGIC:
                case RESISTANCE:

                case SPEED:
                case HEALTH:
                default:
                    break;
            }
            return 0;
        }

        public Array<WyrEquipment> getEquippedGear() {
            final Array<WyrEquipment> returnValue = new Array<>();
            returnValue.addAll(amuletSlot, armorSlot, braceletSlot, weaponSlot, ringSlot);
            return returnValue;
        }

        public void equipBracelet(WyrBracelet bracelet) {
            Gdx.app.log("TODO", "XD");
        }
        public void equipWeapon(WyrWeapon weapon) {
            Gdx.app.log("TODO", "XD");
        }
        public void equipAmulet(WyrAmulet amulet) {
            Gdx.app.log("TODO", "XD");
        }
        public void equipArmor(WyrArmor armor) {
            Gdx.app.log("TODO", "XD");
        }
        public void equipRing(WyrRing ring) {
            Gdx.app.log("TODO", "XD");
        }

        public WyrBracelet getEquippedBracelet() { return braceletSlot; }
        public WyrWeapon   getEquippedWeapon()   { return weaponSlot; }
        public WyrAmulet   getEquippedAmulet()   { return amuletSlot; }
        public WyrArmor    getEquippedArmor()    { return armorSlot; }
        public WyrRing     getEquippedRing()     { return ringSlot; }

        public Array<WyrItem> getContainers() { return containers; }
    }


    /**
     * RPG Class
     */
    public static class RPGClass {

        public enum RPGClassID {
            PEASANT,         // default / basic commoner
            DRAFTEE,         // alt basic soldier

            PLANESWALKER,    // unique for LEIF
            SHIELD_KNIGHT,   // unique for ANTAL
            WRAITH,          // unique class for LEON
            KING,            // unique for ERIK and [LEON's FATHER]
            QUEEN,           // unique for [SOUTHERN QUEEN]
            CAPTAIN,         // unique for ANVIL
            HERBALIST,       // unique for LYRA
            BOSS,            // unique for TOHNI

            SOLDIER,         // generic
            BLADE_KNIGHT,    // generic
            CAVALRY,         // generic
            BOATMAN,         // generic

            GREAT_WYRM,      // God.

            PROP             // Boxes and doors and cannons, oh my!. Also used for items.
        }

        private boolean hasMount    = false;
        private boolean mountLocked = false;
        private boolean isMounted   = false;

        private RPGClassID classID = RPGClassID.PEASANT;
        private RPGridMovementType standardRPGridMovementType = RPGridMovementType.INFANTRY;
        private RPGridMovementType mountedRPGridMovementType = RPGridMovementType.CAVALRY;

        /**
         * Mounted vs standard stats are either/or, not cumulative.
         */
        private int bonus_Mounted_Strength   = 0;
        private int bonus_Mounted_Defense    = 0;
        private int bonus_Mounted_Magic      = 0;
        private int bonus_Mounted_Resistance = 0;
        private int bonus_Mounted_Speed      = 0;
        private int bonus_Mounted_Health     = 0;
        private int bonus_Mounted_AP_Gain    = 0;

        private int bonus_Strength      = 0;
        private int bonus_Defense       = 0;
        private int bonus_Magic         = 0;
        private int bonus_Resistance    = 0;
        private int bonus_Speed         = 0;
        private int bonus_Health        = 0;
        private int bonus_AP_Gain       = 0;

        // TODO:
        //  If desirable, functionality could be built to allow
        //  any class to theoretically mount objects or actors in
        //  the world, with negative bonus if they shouldn't be good
        //  at such. Not really necessary, but could represent fun
        //  emergent gameplay opportunities down the line.

        public RPGClass() {}

        public void setTo(RPGClassID type) {
            switch(type) {
                case PEASANT:
                case DRAFTEE:
                    break;

                case PLANESWALKER:
                    // Protagonist stats,
                    // aka: plot armor.
                    this.hasMount = true;
                    this.classID             = RPGClassID.PLANESWALKER;
                    this.mountedRPGridMovementType = RPGridMovementType.FLYING;

                    this.bonus_Speed  = 2;
                    this.bonus_Health = 3;

                    this.bonus_Mounted_Resistance = 1;
                    this.bonus_Mounted_Defense    = 1;
                    this.bonus_Mounted_Speed      = 4;
                    this.bonus_Mounted_Health     = 5; // TODO: in combat, if the difference in mounted hp would cause the unit to drop to 1 or lower, automatically force dismount and set health to 1(?)
                    break;

                case SHIELD_KNIGHT:
                case WRAITH:
                case KING:
                case QUEEN:
                case CAPTAIN:
                case HERBALIST:
                case BOSS:
                case BLADE_KNIGHT:
                case CAVALRY:
                case BOATMAN:

                case SOLDIER:
                    this.classID = RPGClassID.SOLDIER;

                    this.bonus_Strength = 1;
                    this.bonus_Defense  = 1;
                    this.bonus_Health   = 1;
                    break;

                case GREAT_WYRM:
                    break;

                case PROP:
                    this.classID = RPGClassID.PROP;
                    this.standardRPGridMovementType = RPGridMovementType.INANIMATE;
                    this.mountLocked = true;
                default:
                    break;
            }
        }

        public String className() {
            return classID.toString();
            // TODO: cast all but first letter to lower-case.
        }
        public void mount() {
            if(!hasMount || isMounted || mountLocked) return;
            isMounted = true;
        }
        public void dismount() {
            if(!hasMount || !isMounted) return;
            isMounted = false;
        }
        public RPGridMovementType moveType() {
            if(isMounted) {
                return mountedRPGridMovementType;
            } else {
                return standardRPGridMovementType;
            }
        }

        public void lockMount()   {
            if(isMounted) dismount();
            mountLocked = true;
        }
        public void unlockMount() { mountLocked = false; }
        public boolean mountAvailable() { return hasMount && !mountLocked; }
        public boolean isMounted() { return isMounted; }
        public final int getStatBonus(StatType type) {
            switch(type) {
                case STRENGTH:
                    return (isMounted ? bonus_Mounted_Strength : bonus_Strength);
                case HEALTH:
                    return (isMounted ? bonus_Mounted_Health : bonus_Health);
                case SPEED:
                    return (isMounted ? bonus_Mounted_Speed : bonus_Speed);
                case MAGIC:
                    return (isMounted ? bonus_Mounted_Magic : bonus_Magic);
                case DEFENSE:
                    return (isMounted ? bonus_Mounted_Defense : bonus_Defense);
                case RESISTANCE:
                    return (isMounted ? bonus_Mounted_Resistance : bonus_Resistance);
                default:
                    return 0;
            }
        }


    }

}
