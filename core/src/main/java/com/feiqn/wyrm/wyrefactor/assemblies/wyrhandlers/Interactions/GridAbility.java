package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions;

import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.AbilityID;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.Equipment.WeaponCategory;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.AbilityID.*;

public final class GridAbility {

    private final AbilityID id;

    private boolean requiresMountAvailable = false;
    private boolean requiresMounted = false;
    private boolean requiresWeapon = false;
    private WeaponCategory requiredWeaponType = null;
    private String description = "Do something really cool.";

    private GridAbility(AbilityID abilityID) {
        this.id = abilityID;
    }

    public boolean requiresMountAvailable() {
        return requiresMountAvailable;
    }

    public GridAbility setRequiresMountAvailable() {
        this.requiresMountAvailable = true;
        return this;
    }

    public boolean requiresMounted() {
        return requiresMounted;
    }

    public GridAbility setRequiresMounted(boolean requiresMounted) {
        this.requiresMounted = requiresMounted;
        return this;
    }

    public boolean requiresWeapon() {
        return requiresWeapon;
    }

    public GridAbility setRequiresWeapon(boolean requiresWeapon) {
        this.requiresWeapon = requiresWeapon;
        return this;
    }

    public WeaponCategory getRequiredWeaponType() {
        return requiredWeaponType;
    }

    public GridAbility setRequiredWeaponType(WeaponCategory requiredWeaponType) {
        this.requiredWeaponType = requiredWeaponType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GridAbility setDescription(String description) {
        this.description = description;
        return this;
    }

    public AbilityID getId() {
        return id;
    }

    public static GridAbility diveBomb() {
        return new GridAbility(DIVE_BOMB).setRequiresMountAvailable().setDescription("Crash into the enemy from the sky, stunning them.");
    }
}
