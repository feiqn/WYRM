package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.math;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.equipment.WyrLoadout;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cppersonality.WyrCPPersonality;

public class SimpleStats {

    protected WyrCPPersonality cpPersonality;

    protected Array<WyrStatusCondition> statusConditions = new Array<>();

    public SimpleStats() {}

     protected WyrLoadout loadout = new WyrLoadout();
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

    protected RPGClass rpgClass = new RPGClass();

    protected int actionPointRestoreRate = 1;
    protected int actionPoints;
    protected int simple_Strength;
    protected int simple_Defense;
    protected int simple_Magic;
    protected int simple_Resistance;
    protected int simple_Speed;
    protected int simple_Health;

    public void gainAP() { actionPoints++; }
    public void consumeAP() { actionPoints--; }
    public void restoreAP() { actionPoints += actionPointRestoreRate; }

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
    public int modifiedStatValue(StatTypes stat) {
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

    protected RPGClass getRPGClass() { return rpgClass; }
    protected MovementType standardMovement() { return rpgClass.standardMovementType; }
    protected MovementType mountedMovement() { return rpgClass.mountedMovementType; }



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
