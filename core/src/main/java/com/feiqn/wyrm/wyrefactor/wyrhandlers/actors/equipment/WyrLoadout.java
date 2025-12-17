package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.equipment;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.math.stats.StatType;

public class WyrLoadout {

    // defines and holds info for a WyrActor's,
    // usually a GridUnit's, equipment slots and
    // loadout. Gear, inventory, etc.

    // probably replaces SimpleInventory

    public WyrLoadout() {}

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
