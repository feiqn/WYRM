package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.math.stats;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.MovementType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.equipment.WyrLoadout;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cppersonality.WyrCPPersonality;

public final class SimpleStats {

    private WyrCPPersonality cpPersonality;

    private Array<WyrStatusCondition> statusConditions = new Array<>();

    private WyrLoadout loadout = new WyrLoadout();
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

    private final GridUnit parent;

    private RPGClass rpgClass = new RPGClass();

    private int actionPointRestoreRate = 1;
    private int actionPoints;
    private int simple_Strength;
    private int simple_Defense;
    private int simple_Magic;
    private int simple_Resistance;
    private int simple_Speed;
    private int simple_Health;

    private final Array<Abilities> abilities = new Array<>();


    public SimpleStats(GridUnit parent) { this.parent = parent; }

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

    public void gainAP() { actionPoints++; shaderAPUpdate(); }
    public void consumeAP() { actionPoints--; shaderAPUpdate(); }
    public void restoreAP() { actionPoints += actionPointRestoreRate; shaderAPUpdate(); }
    private void shaderAPUpdate() {
        if(actionPoints > 0) {
            parent.applyShader(GridActor.ShaderState.DIM);
        } else {
            parent.removeShader(GridActor.ShaderState.DIM);
        }
    }

    public void setBaseDefense(int defense) { this.simple_Defense = defense; }
    public void setBaseStrength(int strength) { this.simple_Strength = strength; }
    public void setBaseResistance(int resistance) { this.simple_Resistance = resistance; }
    public void setBaseHealth(int health) { this.simple_Health = health; }
    public void setBaseMagic(int magic) { this.simple_Magic = magic; }
    public void setBaseSpeed(int speed) { this.simple_Speed = speed; }
    public void setCpPersonality(WyrCPPersonality cpPersonality) { this.cpPersonality = cpPersonality; }

    public int getActionPoints() { return actionPoints; }
    public int getBaseDefense() { return simple_Defense; }
    public int getBaseMagic() { return simple_Magic; }
    public int getBaseHealth() { return simple_Health; }
    public int getBaseStrength() { return simple_Strength; }
    public int getBaseSpeed() { return simple_Speed; }
    public int getBaseResistance() { return simple_Resistance; }
    public WyrCPPersonality getCpPersonality() { return cpPersonality; }
    public Array<WyrStatusCondition> getStatusConditions() { return statusConditions; }
    public MovementType movementType() { return (rpgClass.isMounted ? mountedMovement() : standardMovement()); }
    public RPGClass.RPGClassID classID() { return rpgClass.classID; }
    public int modifiedStatValue(StatType stat) {
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

    private RPGClass getRPGClass() { return rpgClass; }
    private MovementType standardMovement() { return rpgClass.standardMovementType; }
    private MovementType mountedMovement() { return rpgClass.mountedMovementType; }



    public static class RPGClass {

        public enum RPGClassID {
            PEASANT,         // basic commoner
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


            GREAT_WYRM       // God.
        }

        private boolean hasMount = false;
        private boolean mountLocked = false;
        private boolean isMounted = false;
        private RPGClassID classID = RPGClassID.PEASANT;
        private MovementType standardMovementType = MovementType.INFANTRY;
        private MovementType mountedMovementType = MovementType.INFANTRY;

        private int bonus_Strength   = 0;
        private int bonus_Defense    = 0;
        private int bonus_Magic      = 0;
        private int bonus_Resistance = 0;
        private int bonus_Speed      = 0;
        private int bonus_Health     = 0;
        private int bonus_AP         = 0;

        public RPGClass() {}


        private void setTo(RPGClassID type) {
            switch(classID) {
                case PEASANT:
                case DRAFTEE:
                    break;

                case PLANESWALKER:
                case SHIELD_KNIGHT:
                case WRAITH:
                case KING:
                case QUEEN:
                case CAPTAIN:
                case HERBALIST:
                case BOSS:

                case SOLDIER:
                case BLADE_KNIGHT:
                case CAVALRY:
                case BOATMAN:

                case GREAT_WYRM:
            }
        }

        private String className() {
            return classID.toString();
            // TODO: cast all but first letter to lower-case.
        }
        public void mount() {
            if(!hasMount || isMounted || mountLocked) return;

        }
        private void dismount() {
            if(!hasMount || !isMounted) return;

        }
        private MovementType moveType() {
            if(isMounted) {
                return mountedMovementType;
            } else {
                return standardMovementType;
            }
        }
        private boolean mountAvailable() { return hasMount && !mountLocked; }




    }

}
