package com.feiqn.wyrm.wyrefactor.helpers;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.Materials;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.Materials.Type;

public class Material {

    protected Array<Type> materialComposition = new Array<>();
    protected Materials.Metals metalMass = null;
    protected Materials.Woods woodMass = null;
    protected Materials.Textiles textileMass = null;
    protected Materials.Stones stoneMass = null;

    public Material(Materials.Type... type) {
        materialComposition.addAll(type);
    }

    public Array<Type> getComposition() {
        return materialComposition;
    }
    public void setMetalMass(Materials.Metals metalMass) {
        this.metalMass = metalMass;
    }
    public @Null Materials.Metals getMetalMass() {
        return metalMass;
    }
    public void setStoneMass(Materials.Stones stoneMass) {
        this.stoneMass = stoneMass;
    }
    public @Null Materials.Stones getStoneMass() {
        return stoneMass;
    }
    public void setWoodMass(Materials.Woods woodMass) {
        this.woodMass = woodMass;
    }
    public @Null Materials.Woods getWoodMass() {
        return woodMass;
    }
    public void setTextileMass(Materials.Textiles textileMass) {
        this.textileMass = textileMass;
    }
    public @Null Materials.Textiles getTextileMass() {
        return textileMass;
    }
}
