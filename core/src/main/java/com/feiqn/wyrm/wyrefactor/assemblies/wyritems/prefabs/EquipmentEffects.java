package com.feiqn.wyrm.wyrefactor.assemblies.wyritems.prefabs;

import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.WyrEquipment.EquipmentEffect;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.Equipment.BonusEffect.*;

public final class EquipmentEffects {

    private EquipmentEffects() {}

    public static EquipmentEffect pierceDefenseHalf() {
        return new EquipmentEffect(PIERCE_DEFENSE_HALF);
    }
    public static EquipmentEffect pierceDefenseFull() {
        return new EquipmentEffect(PIERCE_DEFENSE_FULL);
    }

    public static EquipmentEffect slowAOE() {
        return new EquipmentEffect(SLOW).aoe(2).duration(1);
    }
    public static EquipmentEffect slowAOE(int range, int duration) {
        return new EquipmentEffect(SLOW).aoe(range).duration(duration);
    }

}
