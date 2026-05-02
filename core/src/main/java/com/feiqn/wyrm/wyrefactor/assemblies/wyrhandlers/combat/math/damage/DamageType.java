package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.math.damage;

public enum DamageType {

    // Indented types are "discrete" values only returned in certain game modes.

    PHYSICAL,
        SLASHING,
        BLUDGEONING,
        PIERCING,

    MAGIC,
        BURNING,
        FREEZING,
        SHOCKING,
        SPIRITUAL,
        VOID,

    HERBAL,
        TOXIC,
        CORROSIVE,
        SENSORY,

    EXPLOSIVE,
        COMBUSTIVE,
        PROPULSIVE,
        BLINDING,
}
