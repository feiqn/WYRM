package com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.Abilities;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.MovementType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cppersonality.WyrCPPersonality;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.WyrAmulet;

public final class WyrStats {

    private WyrCPPersonality cpPersonality;

    private final Array<WyrStatusCondition> statusConditions = new Array<>();

    private WyrInventory loadout = new WyrInventory();
    // TODO: WyrLoadout data type to hold equipment
    //  information, including available slots for
    //  gear types, and whats in them, etc.

    // TODO: methods to expose loadout and gear, etc.

    // TODO: methods to calculate and expose
    //  modified stat values

    // TODO: consider if weapon training status
    //  is the way to go or if it's not what we
    //  want for this system. If we do keep it,
    //  it should go here in this class.

    private final GridActor parent;
    private final GridActor.ActorType parentType;

    private final RPGClass rpgClass = new RPGClass();

    private int actionPointRestoreRate = 1;
    private int actionPoints    = 0;
    private int base_Strength   = 0;
    private int base_Defense    = 0;
    private int base_Magic      = 0;
    private int base_Resistance = 0;
    private int base_Speed      = 0;
    private int base_Health     = 0;
    private int rollingHP       = 0;

    private final Array<Abilities> abilities = new Array<>();

    /**
     * End of declarations.
     */

    public WyrStats(GridActor parent, GridActor.ActorType type) {
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
            if(parentType == GridActor.ActorType.UNIT) {
                assert parent instanceof GridUnit;
                final GridUnit parentUnit = (GridUnit) parent;
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
        if(actionPoints > 0) {
            parent.applyShader(GridActor.ShaderState.DIM);
        } else {
            parent.removeShader(GridActor.ShaderState.DIM);
        }
    }

    public void setBaseDefense(int defense)       { this.base_Defense = defense; }
    public void setBaseStrength(int strength)     { this.base_Strength = strength; }
    public void setBaseResistance(int resistance) { this.base_Resistance = resistance; }
    public void setBaseHealth(int health)         { this.base_Health = health; }
    public void setBaseMagic(int magic)           { this.base_Magic = magic; }
    public void setBaseSpeed(int speed)           { this.base_Speed = speed; }
    public void setAPRestoreRate(int i)           { actionPointRestoreRate = i; }
    public void setComputerPersonality(WyrCPPersonality cpPersonality) { this.cpPersonality = cpPersonality; }

    public int getAPRestoreRate()  { return actionPointRestoreRate; }
    public int getActionPoints()   { return actionPoints; }
    public int getBaseDefense()    { return base_Defense; }
    public int getBaseMagic()      { return base_Magic; }
    public int getBaseHealth()     { return base_Health; }
    public int getBaseStrength()   { return base_Strength; }
    public int getBaseSpeed()      { return base_Speed; }
    public int getBaseResistance() { return base_Resistance; }
    public WyrCPPersonality getCpPersonality() { return cpPersonality; }
    public Array<WyrStatusCondition> getStatusConditions() { return statusConditions; }
    public MovementType movementType() { return (rpgClass.isMounted ? mountedMovement() : standardMovement()); }
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

    public RPGClass getRPGClass() { return rpgClass; }
    private MovementType standardMovement() { return rpgClass.standardMovementType; }
    private MovementType mountedMovement() { return rpgClass.mountedMovementType; }

    /**
     * Inventory
     */

    public static class WyrInventory {

        // defines and holds info for an actor's,
        // usually a GridUnit's, equipment slots and
        // loadout. Gear, inventory, etc.

        // probably replaces SimpleInventory

        private WyrAmulet amuletSlot = new WyrAmulet();

        public WyrInventory() {}

        public int combinedModifiersValue(StatType stat) {
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

            PROP             // Boxes and doors and cannons, oh my!.
        }

        private boolean hasMount    = false;
        private boolean mountLocked = false;
        private boolean isMounted   = false;

        private RPGClassID classID = RPGClassID.PEASANT;
        private MovementType standardMovementType = MovementType.INFANTRY;
        private MovementType mountedMovementType  = MovementType.CAVALRY;


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
                    hasMount = true;
                    classID = RPGClassID.PLANESWALKER;
                    mountedMovementType = MovementType.FLYING;

                    bonus_Speed  = 2;
                    bonus_Health = 3;

                    bonus_Mounted_Resistance = 1;
                    bonus_Mounted_Defense    = 1;
                    bonus_Mounted_Speed      = 4;
                    bonus_Mounted_Health     = 5; // TODO: in combat, if the difference in mounted hp would cause the unit to drop to 1 or lower, automatically force dismount and set health to 1(?)
                    break;

                case SHIELD_KNIGHT:
                case WRAITH:
                case KING:
                case QUEEN:
                case CAPTAIN:
                case HERBALIST:
                case BOSS:

                case SOLDIER:
                    classID = RPGClassID.SOLDIER;

                    bonus_Strength = 1;
                    bonus_Defense  = 1;
                    bonus_Health   = 1;
                    break;

                case BLADE_KNIGHT:
                case CAVALRY:
                case BOATMAN:

                case GREAT_WYRM:
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
        public MovementType moveType() {
            if(isMounted) {
                return mountedMovementType;
            } else {
                return standardMovementType;
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
                    return bonus_Strength;
                case HEALTH:
                    return bonus_Health;
                case SPEED:
                    return (isMounted ? bonus_Mounted_Speed : bonus_Speed);
                case MAGIC:
                    return bonus_Magic;
                case DEFENSE:
                    return bonus_Defense;
                case RESISTANCE:
                    return bonus_Resistance;
                default:
                    return 0;
            }
        }


    }

}
