package com.feiqn.wyrm.wyrefactor.assemblies.math.stats;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor.Unit;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.prefab.WYRMActors;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.RPGClass.RPGClassID;

import java.util.Map;
import java.util.TreeMap;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.RPGClass.RPGClassID.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatType.*;

public class WyrStats implements WyrFrame {

    private boolean mountLocked = false;
    private boolean isMounted   = false;

    private String ownedMountID = null;

    private MobilityType standardMobilityType = MobilityType.INFANTRY;

    private RPGClassID rpgClassID;

    protected Array<WyrStatusCondition> statusConditions = new Array<>();
    protected WyrActor parent;

    protected final Array<AbilityID> knownAbilities = new Array<>();
    protected final Map<String, Integer> statMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    protected float availableSteps = 0; // statMap could be changed to hold Float values and this folded in, but why bother.

    /** @StatMap <br>
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
    public WyrStats(WyrActor parent, RPGClassID rpgClass) {
        this.parent = parent;
        rpgClassID = rpgClass;

        for(StatType t : StatType.values()) {
            setBaseValue(t, 0);
        }

        setMaxHealth(1, true);
        depleteAP();

        if(rpgClass != OBJECT) statMap.put("AP_RESTORE_RATE", 1);
    }

    public void applyCondition(WyrStatusCondition condition) { statusConditions.add(condition); }

    public @Null WyrStatusCondition hasCondition(StatusConditionID conditionID) {
        for(WyrStatusCondition c : statusConditions) {
            if(c.getConditionID() == conditionID) {
                return c;
            }
        }
        return null;
    }

    public void tickDownConditions(boolean harmful) {
        for(WyrStatusCondition condition : statusConditions) {
            condition.tickDownEffect();
            if(condition.getDuration() <= 0) statusConditions.removeValue(condition, true);
            if(!harmful) continue;
            switch(condition.getConditionID()) {
                case BURNED:
                case STUNNED:
                case COLD:
                case ENVENOMED:
                case PETRIFIED:
                case SOUL_BRANDED:
                default:
                    break;
            }
        }
    }

    public  void healToFull() { statMap.put("HEALTH_ROLLING", getNetValue(HEALTH)); }
    public  void healBy(int amount) { applyDamage(-amount); }
    public  void applyDamage(int damage) {
        int rollingHP = statMap.get("HEALTH_ROLLING");
        rollingHP -= damage;
        if(rollingHP > getMaxHP()) healToFull(); // negative damage can heal
        statMap.put("HEALTH_ROLLING", rollingHP);
        if(rollingHP <= 0) {

            if(handlers.cutscenes().checkZeroHPTriggers(((Unit)parent).getCharacterID())) {
                handlers.interactions().queueInteraction(new WyrInteraction(parent).kill());
            } else {
                ((Unit) parent).kill();
            }

        }
    }

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
    public void spendSteps(float amount) { availableSteps -= amount; }
    public void resetSteps() { availableSteps = getNetValue(SPEED); }
    public void depleteSteps() { availableSteps = 0; }

    public void setBaseValue(StatType type, int i) { statMap.put(type.toString(), Math.min(i, 10)); }
    public int getBaseValue(StatType type) { return statMap.getOrDefault(type.toString(), 0); }
    public int getNetValue(StatType forStat) {

        int unitStat = 0;
        unitStat += statMap.getOrDefault(forStat.toString(), 0);
        if(parent.getInventory() != null) {
            unitStat += parent.getInventory().combinedGearModifiersValue(forStat);
        }
        unitStat += RPGClass.statBonus(forStat, rpgClassID);
        if(ownsMount() && isMounted()) {
            unitStat += WYRMActors.WyrEmblem.Units.Animals.fromID(ownedMountID).stats().getNetValue(forStat);
        }
        return unitStat;

    }

    public void setMaxHealth(int i, boolean healToFull) { statMap.put("HEALTH", Math.min(i, 10)); if(healToFull) healToFull(); }
    public void setAPRestoreRate(int i) { statMap.put("AP_RESTORE_RATE", Math.min(i, 10));                         }

    public Array<WyrStatusCondition> getStatusConditions() { return statusConditions; }

    public int getMaxHP() { return getNetValue(HEALTH); }
    public int getRollingHP() { return statMap.get("HEALTH_ROLLING"); }
    public int getRollingAP() { return statMap.get("AP_ROLLING"); }
    public float getAvailableSteps() { return availableSteps; }

    public MobilityType getMovementType() { return (isMounted ? WYRMActors.WyrEmblem.Units.Animals.fromID(ownedMountID).getStats().getMovementType() : standardMobilityType); }

    public boolean canAct() { return getRollingAP() > 0; }
    public boolean canStep() { return getAvailableSteps() > 0; }

    public RPGClassID getRPGClassID() { return rpgClassID; }

    public String getClassName() { return rpgClassID.toString(); }
    public String getClassExamine() { return RPGClass.examineText(rpgClassID); }
    public boolean ownsMount() { return ownedMountID != null && !ownedMountID.isEmpty(); }
    public void ownMount(String mountID) { ownedMountID = mountID; }
    public String ownedMountID() { return ownedMountID; }
    public void mountUnownedMount(WyrActor mount) {

    }
    public void mount() {
        if(!ownsMount() || isMounted || mountLocked) return;
        isMounted = true;
        parent.getAnimator().generateAnimations();
        spendSteps(-absoluteMountedMovementDifference());
    }
    public void dismount() {
        if(!ownsMount() || !isMounted) return;
        isMounted = false;
        parent.getAnimator().generateAnimations();
        spendSteps(absoluteMountedMovementDifference());
    }
    private int absoluteMountedMovementDifference() {
        if(!ownsMount()) return 0;
        return Math.abs(getBaseValue(SPEED) - WYRMActors.WyrEmblem.Units.Animals.fromID(ownedMountID).stats().getNetValue(SPEED));
    }
    public void lockMount()   {
        if(isMounted) dismount();
        mountLocked = true;
    }
    public void unlockMount() { mountLocked = false; }
    public boolean mountAvailable() { return ownsMount() && !mountLocked; }
    public boolean isMounted() { return isMounted; }
}
