package com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpg.rpgrid;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.actors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridMovementType;
import com.feiqn.wyrm.wyrefactor.actors.animations.grid.RPGridAnimator;
import com.feiqn.wyrm.wyrefactor.actors.items.inventory.rpgrid.RPGridInventory;
import com.feiqn.wyrm.wyrefactor.helpers.ShaderState;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpg.RPGStatType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.personality.grid.RPGGridPersonality;

import static com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpg.RPGStatType.*;

public final class RPGridStats extends WyrStats<
        RPGridAbilityID,
        RPGridActor,
        RPGridStatusCondition,
        RPGridInventory,
        RPGGridPersonality,
        RPGStatType
            > {



    private final RPGClass rpgClass = new RPGClass();

    /** @StatMap <br>
     *  HEALTH_max <br>
     *  HEALTH_rolling <br>
     *  AP_restore_rate <br>
     *  AP_rolling <br>
     *  <p>
     *      SPEED_base <br>
     *      STRENGTH_base <br>
     *      DEFENSE_base <br>
     *      DEXTERITY_base <br>
     *      MAGIC_base <br>
     *      RESISTANCE_base <br>
     *  </p>
     */
    public RPGridStats(RPGridActor parent) {
        super(parent, RPGStatType.values());
        inventory = new RPGridInventory();
    }
    public RPGClass getRPGClass() { return this.rpgClass; }
    public RPGClass.RPGClassID getRPGClassID() { return RPGClass.RPGClassID; }

    @Override
    public void tickDownConditions(boolean harmful) {
        super.tickDownConditions(harmful);
        for(RPGridStatusCondition condition : statusConditions) {
            if(!harmful) continue;
            switch(condition.getEffectType()) {
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

    @Override
    public Array<RPGStatType> statTypes() {
        return new Array<>(RPGStatType.values());
    }

    @Override
    public int getModifiedStatValue(RPGStatType type) {
        switch(type) {
            case STRENGTH: // TODO:
                return getBaseStrength()   + rpgClass.getStatBonus(RPGStatType.STRENGTH); //  + weapon strength, other bonuses, etc
            case DEFENSE:
                return getBaseDefense()    + rpgClass.getStatBonus(DEFENSE); // ...
            case MAGIC:
                return getBaseMagic()      + rpgClass.getStatBonus(RPGStatType.MAGIC); // ...
            case RESISTANCE:
                return getBaseResistance() + rpgClass.getStatBonus(RPGStatType.RESISTANCE); // ...
            case SPEED:
                return getBaseSpeed()      + rpgClass.getStatBonus(SPEED); // ...
            case DEXTERITY:
            default:
                break;
        }
        return 0;
    }


    public void setBaseDefense(int defense)       { setStatValue(DEFENSE,    defense);    }
    public void setBaseStrength(int strength)     { setStatValue(STRENGTH,   strength);   }
    public void setBaseResistance(int resistance) { setStatValue(RESISTANCE, resistance); }
    public void setBaseMagic(int magic)           { setStatValue(MAGIC,      magic);      }
    public void setBaseSpeed(int speed)           { setStatValue(SPEED,      speed);      }
    public void setBaseHealth(int health, boolean healToFull) { setMaxHealth(health, healToFull); }

    public  RPGridMovementType getMovementType()     { return (rpgClass.isMounted ? getMountedMoveType() : getStandardMoveType()); }
    private RPGridMovementType getStandardMoveType() { return this.rpgClass.standardRPGridMovementType; }
    private RPGridMovementType getMountedMoveType()  { return this.rpgClass.mountedRPGridMovementType;  }

    public int getBaseDefense()    { return getStatValue(DEFENSE);    }
    public int getBaseMagic()      { return getStatValue(MAGIC);      }
    public int getBaseStrength()   { return getStatValue(STRENGTH);   }
    public int getBaseSpeed()      { return getStatValue(SPEED);      }
    public int getBaseResistance() { return getStatValue(RESISTANCE); }
    public int getBaseHealth()     { return getMaxHP();               }


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

            PROP,            // Boxes and doors and cannons, oh my!.
            NONE,
        }

        private boolean hasMount    = false;
        private boolean mountLocked = false;
        private boolean isMounted   = false;

        private static RPGClass.RPGClassID RPGClassID         = RPGClass.RPGClassID.NONE;
        private RPGridMovementType standardRPGridMovementType = RPGridMovementType.INFANTRY;
        private RPGridMovementType mountedRPGridMovementType  = RPGridMovementType.CAVALRY;

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

        public void setTo(RPGClass.RPGClassID type) {
            switch(type) {
                case PEASANT:
                case DRAFTEE:
                    break;

                case PLANESWALKER:
                    // Protagonist stats,
                    // aka: plot armor.
                    this.hasMount = true;
                    this.isMounted = false;
                    this.RPGClassID = RPGClass.RPGClassID.PLANESWALKER;
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
                    this.RPGClassID = RPGClass.RPGClassID.SOLDIER;

                    this.bonus_Strength = 1;
                    this.bonus_Defense  = 1;
                    this.bonus_Health   = 1;
                    break;

                case GREAT_WYRM:
                    break;

                case PROP:
                    this.RPGClassID = RPGClass.RPGClassID.PROP;
                    this.standardRPGridMovementType = RPGridMovementType.INANIMATE;
                    this.mountLocked = true;
                default:
                    break;
            }
        }

        public String className() {
            return RPGClassID.toString();
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
        public final int getHPBonus() { return (isMounted ? bonus_Mounted_Health : bonus_Health); }
        public final int getStatBonus(RPGStatType type) {
            switch(type) {
                case STRENGTH:
                    return (isMounted ? bonus_Mounted_Strength : bonus_Strength);
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
