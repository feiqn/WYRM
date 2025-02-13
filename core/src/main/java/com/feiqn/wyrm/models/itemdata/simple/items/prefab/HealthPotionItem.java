package com.feiqn.wyrm.models.itemdata.simple.items.prefab;

import com.feiqn.wyrm.models.itemdata.simple.items.SimpleItem;

public class HealthPotionItem extends SimpleItem {

    private final int healingValue;

    public HealthPotionItem() {
        name = "Health Potion";
        healingValue = 5;
    }

    public int getHealingValue() {
        return healingValue;
    }

}
