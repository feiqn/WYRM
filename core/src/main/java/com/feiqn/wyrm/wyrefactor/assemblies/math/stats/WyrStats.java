package com.feiqn.wyrm.wyrefactor.assemblies.math.stats;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor.Unit;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;

import java.util.HashMap;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.RPGClassID.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatType.*;

/**
 * "stats" may not be all-encompassing enough of a name for this.
 * Inventory, equipment, personality, and abilities also tracked here.
 */
public class WyrStats implements WyrFrame {

    private final RPGClass rpgClass = new RPGClass();

    protected Array<WyrStatusCondition> statusConditions = new Array<>();
    protected WyrActor parent;

    protected final Array<AbilityID> abilities = new Array<>();
    protected final HashMap<String, Float> statMap = new HashMap<>();

    /** @StatMap <br>
     *  STEPS <br>
     *  <br>
     *  HEALTH <br>
     *  HEALTH_ROLLING <br>
     *  <br>
     *  AP_RESTORE_RATE <br>
     *  AP_ROLLING <br>
     *
     *  <p>
     *      SPEED <br>
     *      STRENGTH <br>
     *      DEFENSE <br>
     *      MAGIC <br>
     *      RESISTANCE <br>
     *  </p>
     *  <p>
     *      Extended options available.
     *  </p>
     */
    public WyrStats(WyrActor parent) {
        this.parent = parent;
        statMap.put("STEPS", 0f);
        statMap.put("HEALTH_ROLLING",  1f);
        statMap.put("AP_ROLLING",      0f);
        for(StatType t : StatType.values()) {
            setBaseStatValue(t, 0f);
        }
        switch(parent.getActorType()) {
            case ENTITY:
                statMap.put("AP_RESTORE_RATE", 1f);
                break;

            case PROP:
                rpgClass.setTo(PROP);
                break;
        }
    }

    public  void applyCondition(WyrStatusCondition condition) {
        statusConditions.add(condition);
    }

    public void tickDownConditions(boolean harmful) {
        for(WyrStatusCondition condition : statusConditions) {
            condition.tickDownEffect();
            if(condition.effectCounter() <= 0) statusConditions.removeValue(condition, true);
            if(!harmful) continue;
            switch(condition.getEffectType()) {
                case BURNED:
                case STUNNED:
                case COLD:
                case POISONED:
                case PETRIFIED:
                case SOUL_BRANDED:
                default:
                    break;
            }
        }
    }

    public  void healBy(int amount) { applyDamage(-amount); }
    public  void applyDamage(int damage) {
        int rollingHP = statMap.get("HEALTH_ROLLING").intValue();
        rollingHP -= damage;
        if(rollingHP > getMaxHP()) healToFull(); // negative damage can heal
        statMap.put("HEALTH_ROLLING", (float) rollingHP);
        if(rollingHP <= 0) {
            switch(parent.getActorType()) {
                case ENTITY:
                    ((Unit)parent).kill();
                case PROP:
                default:
                    break;
            }
        }
    }

    public  void healToFull() { statMap.put("HEALTH_ROLLING", (float) getMaxHP()); }

    public  void gainAP() {
        statMap.merge("AP_ROLLING", 1f, Float::sum); // ai showed me this, sorry idk what im doing
        shaderAPUpdate();
    }
    public  void spendAP() {
        statMap.merge("AP_ROLLING", -1f, Float::sum);
        shaderAPUpdate();
    }
    public void depleteAP() {
        statMap.put("AP_ROLLING", 0f);
        shaderAPUpdate();
    }
    public  void restoreAP() {
        statMap.merge("AP_ROLLING", (float) getStatValue(AP_RESTORE_RATE), Float::sum);
        shaderAPUpdate();
    }
    private void shaderAPUpdate() {
        if(statMap.get("AP_ROLLING") <= 0) {
            parent.applyShader(ShaderState.DIM);
        } else {
            parent.applyShader(ShaderState.STANDARD);
        }
    }
    public void spendStep() { statMap.merge("STEPS", -1f, Float::sum); }
    public void spendSteps(float amount) {
        statMap.merge("STEPS", -amount, Float::sum);
    }
    public void resetSteps() { statMap.put("STEPS", (float) getModifiedStatValue(SPEED)); }
    public void depleteSteps() { statMap.put("STEPS", 0f); }

    public  void setBaseStatValue(StatType type, float i) { statMap.put(type.toString(), i);                 }
    public  void setMaxHealth(float i, boolean healToFull) { statMap.put("HEALTH", i); if(healToFull) healToFull(); }
    public  void setAPRestoreRate(float i)                 { statMap.put("AP_RESTORE_RATE", i);                         }
    public Array<WyrStatusCondition> getStatusConditions() { return statusConditions; }

    public int getStatValue(StatType type) { return (statMap.getOrDefault(type.toString(), 0f)).intValue(); }
    public int getMaxHP() { return statMap.get("HEALTH").intValue(); }
    public int getRollingHP() { return statMap.get("HEALTH_ROLLING").intValue(); }
    public int getRollingAP() { return statMap.get("AP_ROLLING").intValue(); }
    public float getAvailableSteps() { return statMap.get("STEPS"); }

    public void setBaseDefense(int defense)       { setBaseStatValue(DEFENSE,    defense);    }
    public void setBaseStrength(int strength)     { setBaseStatValue(STRENGTH,   strength);   }
    public void setBaseResistance(int resistance) { setBaseStatValue(RESISTANCE, resistance); }
    public void setBaseMagic(int magic)           { setBaseStatValue(MAGIC,      magic);      }
    public void setBaseSpeed(int speed)           { setBaseStatValue(SPEED,      speed);      }
    public void setBaseHealth(int health, boolean healToFull) { setMaxHealth(health, healToFull); }

    public MobilityType getMovementType()     { return (rpgClass.isMounted ? getMountedMoveType() : getStandardMoveType()); }
    private MobilityType getStandardMoveType() { return this.rpgClass.standardRPGridMovementType; }
    private MobilityType getMountedMoveType()  { return this.rpgClass.mountedRPGridMovementType;  }

    public int getBaseDefense()    { return getStatValue(DEFENSE);    }
    public int getBaseMagic()      { return getStatValue(MAGIC);      }
    public int getBaseStrength()   { return getStatValue(STRENGTH);   }
    public int getBaseSpeed()      { return getStatValue(SPEED);      }
    public int getBaseResistance() { return getStatValue(RESISTANCE); }
    public int getBaseHealth()     { return getMaxHP();               }

    public boolean canAct() { return getRollingAP() >= 1; }
    public boolean canStep() { return getAvailableSteps() > 0; }

    public RPGClass getRPGClass() { return this.rpgClass; }
    public RPGClassID getRPGClassID() { return RPGClass.RPGClassID; }

    public int getModifiedStatValue(StatType forStat) {

        switch(parent.getActorType()) {
            case PROP:
                break;

            case ENTITY:
                return (
                    statMap.getOrDefault(forStat.toString(), 0f).intValue() +
                        (parent.getInventory() == null ? 0 : ((Unit)parent).getInventory().equipment().combinedGearModifiersValue(forStat) )
                );
        }

        return 0;
    }

    public static class RPGClass {

        private boolean hasMount    = false;
        private boolean mountLocked = false;
        private boolean isMounted   = false;

        private static RPGClassID RPGClassID = PEASANT;
        private MobilityType standardRPGridMovementType = MobilityType.INFANTRY;
        private MobilityType mountedRPGridMovementType  = MobilityType.CAVALRY;

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

        // TODO:
        //  collapse these to hashmap and assign iteratively from enum

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
                    isMounted = false;
                    RPGClassID = PLANESWALKER;
                    mountedRPGridMovementType = MobilityType.FLYING;

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
                case BLADE_KNIGHT:
                case CAVALRY:
                case BOATMAN:

                case SOLDIER:
                    RPGClassID = RPGClass.RPGClassID.SOLDIER;

                    this.bonus_Strength = 1;
                    this.bonus_Defense  = 1;
                    this.bonus_Health   = 1;
                    break;

                case GREAT_WYRM:
                    break;

                case PROP:
                    this.RPGClassID = RPGClass.RPGClassID.PROP;
                    this.standardRPGridMovementType = MobilityType.INANIMATE;
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
        public MobilityType moveType() {
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
        public final int getStatBonus(StatType type) {
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
