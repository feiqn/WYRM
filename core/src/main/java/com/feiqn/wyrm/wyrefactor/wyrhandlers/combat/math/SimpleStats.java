package com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.logic.handlers.ai.AIPersonality;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cppersonality.WyrCPPersonality;

public class SimpleStats {

    protected WyrCPPersonality cpPersonality;

    protected Array<WyrStatusCondition> statusConditions = new Array<>();

    // protected WyrLoadout loadout = new WyrLoadout();
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
}
