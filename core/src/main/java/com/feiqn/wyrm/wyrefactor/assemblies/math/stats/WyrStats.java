package com.feiqn.wyrm.wyrefactor.assemblies.math.stats;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.WyrActor.Unit;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.WyrActor;

import java.util.HashMap;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.RPGClassID.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatType.*;

/**
 * "stats" may not be all-encompassing enough of a name for this.
 * Inventory, equipment, personality, and abilities also tracked here.
 */
public class WyrStats implements WyrFrame {

    private final RPGClass rpgClass = new RPGClass(this);

    protected Array<WyrStatusCondition> statusConditions = new Array<>();
    protected WyrActor parent;

    protected final Array<AbilityID> abilities = new Array<>();
    protected final HashMap<String, Integer> statMap = new HashMap<>();
    protected float availableSteps = 0;

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
    public WyrStats(WyrActor parent, WyrStats copy) {
        this(parent);
        statMap.clear();
        statMap.putAll(copy.statMap);
    }

    public WyrStats(WyrActor parent) {
        this.parent = parent;
        statMap.put("STEPS", 0);
        statMap.put("HEALTH_ROLLING",  1);
        statMap.put("AP_ROLLING",      0);
        for(StatType t : StatType.values()) {
            setBaseStatValue(t, 0);
        }
        statMap.put("HEALTH", 1);
        switch(parent.getActorType()) {
            case ENTITY:
                statMap.put("AP_RESTORE_RATE", 1);
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
            if(condition.getDuration() <= 0) statusConditions.removeValue(condition, true);
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
        statMap.put("HEALTH_ROLLING", rollingHP);
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

    public  void healToFull() { statMap.put("HEALTH_ROLLING", getMaxHP()); }

    private void shaderAPUpdate() {
        if(statMap.get("AP_ROLLING") <= 0) {
            parent.applyShader(ShaderState.DIM);
        } else {
            parent.applyShader(ShaderState.STANDARD);
        }
    }

    public void setWeaponProficiency(Utilities.Size weaponSize, Equipment.WeaponCategory weaponType, int rank) {
        statMap.put("PROFICIENCY_" + weaponSize + weaponType, Math.max(0, Math.min(rank, 2)));
    }
    public int getWeaponProficiency(Utilities.Size weaponSize, Equipment.WeaponCategory weaponType) {
        return statMap.getOrDefault("PROFICIENCY_" + weaponSize + weaponType, 0);
    }

    // TODO:
    //  - armor proficiency
    //  - mount proficiency
    //  - magic proficiency, etc.

    private int getProficiency(String proficientAttribute) { return statMap.getOrDefault(proficientAttribute, 0); }
    private void setProficiency(String proficientAttribute, int i) { statMap.put(proficientAttribute, Math.max(0, Math.min(i, 10))); }

    public void gainAP() {
        statMap.merge("AP_ROLLING", 1, Integer::sum); // ai showed me this, sorry idk what im doing
        shaderAPUpdate();
    }
    public void spendAP() {
        statMap.merge("AP_ROLLING", -1, Integer::sum);
        shaderAPUpdate();
    }
    public void depleteAP() {
        statMap.put("AP_ROLLING", 0);
        shaderAPUpdate();
    }
    public void restoreAP() {
        statMap.merge("AP_ROLLING", statMap.get("AP_RESTORE_RATE"), Integer::sum);
        shaderAPUpdate();
    }
    public void spendStep() { availableSteps--; }
    public void spendSteps(float amount) {
        availableSteps -= amount;
    }
    public void resetSteps() { availableSteps = getModifiedStatValue(SPEED); }
    public void depleteSteps() { availableSteps = 0; }

    public void setBaseStatValue(StatType type, int i) { statMap.put(type.toString(), Math.min(i, 10)); }
    public void setMaxHealth(int i, boolean healToFull) { statMap.put("HEALTH", Math.min(i, 10)); if(healToFull) healToFull(); }
    public void setAPRestoreRate(int i) { statMap.put("AP_RESTORE_RATE", Math.min(i, 10));                         }

    public void setBaseDefense(int defense)       { setBaseStatValue(DEFENSE,    defense);    }
    public void setBaseStrength(int strength)     { setBaseStatValue(STRENGTH,   strength);   }
    public void setBaseResistance(int resistance) { setBaseStatValue(RESISTANCE, resistance); }
    public void setBaseMagic(int magic)           { setBaseStatValue(MAGIC,      magic);      }
    public void setBaseSpeed(int speed)           { setBaseStatValue(SPEED,      speed);      }
    public void setBaseHealth(int health, boolean healToFull) { setMaxHealth(health, healToFull); }

    public Array<WyrStatusCondition> getStatusConditions() { return statusConditions; }
    public int getStatValue(StatType type) { return statMap.getOrDefault(type.toString(), 0); }
    public int getMaxHP() { return statMap.get("HEALTH"); }
    public int getRollingHP() { return statMap.get("HEALTH_ROLLING"); }
    public int getRollingAP() { return statMap.get("AP_ROLLING"); }
    public float getAvailableSteps() { return availableSteps; }

    public MobilityType getMovementType() { return (rpgClass.getMoveType()); }

    public int getBaseDefense()    { return getStatValue(DEFENSE);    }
    public int getBaseMagic()      { return getStatValue(MAGIC);      }
    public int getBaseStrength()   { return getStatValue(STRENGTH);   }
    public int getBaseSpeed()      { return getStatValue(SPEED);      }
    public int getBaseResistance() { return getStatValue(RESISTANCE); }
    public int getBaseHealth()     { return getMaxHP();               }

    public boolean canAct() { return getRollingAP() > 0; }
    public boolean canStep() { return getAvailableSteps() > 0; }

    public RPGClass getRPGClass() { return this.rpgClass; }
    public RPGClassID getRPGClassID() { return this.rpgClass.RPGClassID; }

    public int getModifiedStatValue(StatType forStat) {

        switch(parent.getActorType()) {
            case PROP:
                break;

            case ENTITY:
                return (
                    statMap.getOrDefault(forStat.toString(), 0) +
                        (parent.getInventory() == null ? 0
                            : ((Unit)parent).getInventory().equipment().combinedGearModifiersValue(forStat)
                        ) + (rpgClass.getStatBonus(forStat))
                );
        }

        return 0;
    }

    public static class RPGClass {

        private boolean hasMount    = false;
        private boolean mountLocked = false;
        private boolean isMounted   = false;

        private MountType mountType = null;

        private final WyrStats parent;

        private String classExamine = "Just as easily somebody from somewhere as nobody from nowhere.";

        private RPGClassID RPGClassID = PEASANT;
        private MobilityType standardRPGridMovementType = MobilityType.INFANTRY;
        private MobilityType mountedRPGridMovementType  = MobilityType.CAVALRY;


        protected final HashMap<String, Integer> statBonusMap = new HashMap<>();

        // TODO:
        //  If desirable, functionality could be built to allow
        //  any class to theoretically mount objects or actors in
        //  the world, with negative bonus if they shouldn't be good
        //  at such. Not really necessary, but could represent fun
        //  emergent gameplay opportunities down the line.

        public RPGClass(WyrStats parent) {
            this.parent = parent;
            // Mounted vs standard stats are either/or, not cumulative.
            for(StatType t : StatType.values()) {
                statBonusMap.put(t.toString(), 0);
                statBonusMap.put("MOUNTED_" + t, 0);
            }
        }

        public void setTo(RPGClassID type) {
            switch(type) {
                case PEASANT:
                case DRAFTEE:
                    break;

                case PLANESWALKER:
                    // Protagonist stats for Leif,
                    // aka: plot armor.
                    hasMount = true;
                    isMounted = false;
                    mountType = MountType.PEGASUS;
                    RPGClassID = PLANESWALKER;
                    mountedRPGridMovementType = MobilityType.FLYING;

                    statBonusMap.put(STRENGTH.toString(), 1);
                    statBonusMap.put(DEFENSE.toString(), 2);
                    statBonusMap.put(MAGIC.toString(), 1);
                    statBonusMap.put(RESISTANCE.toString(), 2);
                    statBonusMap.put(SPEED.toString(), 5);
                    statBonusMap.put(HEALTH.toString(), 5);
                    parent.healToFull();

                    statBonusMap.put("MOUNTED_" + STRENGTH, 1);
                    statBonusMap.put("MOUNTED_" + DEFENSE, 2);
                    statBonusMap.put("MOUNTED_" + MAGIC, 1);
                    statBonusMap.put("MOUNTED_" + RESISTANCE, 3);
                    statBonusMap.put("MOUNTED_" + SPEED, 7);
                    statBonusMap.put("MOUNTED_" + HEALTH, 8);
                    // TODO: in combat, if the difference in mounted hp would cause the unit to drop to 1 or lower, automatically force dismount and set health to 1(?)

                    classExamine = "A pedestrian from the flatland.";
                    break;

                case SHIELD_KNIGHT:
                    // Antal, et. all
                    RPGClassID = SHIELD_KNIGHT;

                    statBonusMap.put(STRENGTH.toString(), 1);
                    statBonusMap.put(DEFENSE.toString(), 4);
                    statBonusMap.put(MAGIC.toString(), 1);
                    statBonusMap.put(RESISTANCE.toString(), 2);
                    statBonusMap.put(SPEED.toString(), 2);
                    statBonusMap.put(HEALTH.toString(), 6);
                    parent.healToFull();

                    classExamine = "It's like talking to a wall.";
                    break;

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
                    RPGClassID = SOLDIER;
                    statBonusMap.put(STRENGTH.toString(), 3);
                    statBonusMap.put(DEFENSE.toString(), 2);
                    statBonusMap.put(MAGIC.toString(), 1);
                    statBonusMap.put(RESISTANCE.toString(), 1);
                    statBonusMap.put(SPEED.toString(), 3);
                    statBonusMap.put(HEALTH.toString(), 4);
                    parent.healToFull();
                    classExamine = "There must be a person behind that helmet, but it sure doesn't seem like it.";
                    break;

                case GREAT_WYRM:
                    break;

                case PROP:
                    this.RPGClassID = PROP;
                    this.standardRPGridMovementType = MobilityType.INANIMATE;
                    this.mountLocked = true;
                    this.classExamine = "It's... something!";
                default:
                    break;
            }
        }

        public String getClassName() {
            return RPGClassID.toString();
            // TODO: cast all but first letter to lower-case.
        }
        public String getClassExamine() {
            return classExamine;
        }
        public void mount() {
            if(!hasMount || isMounted || mountLocked) return;
            isMounted = true;
            parent.parent.getAnimator().generateAnimations();
            parent.spendSteps(-absoluteMountedMovementDifference());
        }
        public void dismount() {
            if(!hasMount || !isMounted) return;
            isMounted = false;
            parent.parent.getAnimator().generateAnimations();
            parent.spendSteps(absoluteMountedMovementDifference());
        }
        public MobilityType getMoveType() {
            if(isMounted) {
                return mountedRPGridMovementType;
            } else {
                return standardRPGridMovementType;
            }
        }
        private int absoluteMountedMovementDifference() {
            return Math.abs(statBonusMap.getOrDefault("MOUNTED_" + SPEED, 0) - statBonusMap.getOrDefault(SPEED.toString(), 0));
        }
        public void lockMount()   {
            if(isMounted) dismount();
            mountLocked = true;
        }
        public void unlockMount() { mountLocked = false; }
        public boolean mountAvailable() { return hasMount && !mountLocked; }
        public boolean isMounted() { return isMounted; }
        public final int getHPBonus() { return getStatBonus(HEALTH); }
        public final int getStatBonus(StatType type) { return (isMounted ? statBonusMap.getOrDefault("MOUNTED_" + type.toString(), 0) : statBonusMap.getOrDefault(type.toString(), 0)); }
        public @Null MountType getMountType() { return mountType; }
        public void setMountType(MountType mountType) { this.mountType = mountType; }
    }


}
