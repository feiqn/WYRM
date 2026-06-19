package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor.Prop;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.StaticInteractions;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.inventory.WyrInventory;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.inventory.WyrInventory.PropInventory;
import com.feiqn.wyrm.wyrefactor.helpers.Material;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr;

public final class WYRMActors implements Wyr{

    private WYRMActors() {}

    public static class WyrEmblem { // AKA "RPGrid"

        public static class Unit {



        }

        public static class Props {

            public static Prop ballista() {
                return new Prop(GameKit.RPG.PropType.BALLISTA, handlers.assets().ballistaTexture) {
                    @Override
                    protected void setup() {
                        addStaticInteraction(StaticInteractions.AimInteraction(this));
//                        ((PropInventory) inventory).setArmament();
                        isSolid = true;
                        material = new Material(GameKit.RPG.Materials.Type.WOOD, GameKit.RPG.Materials.Type.METAL);
                    }
                };
            }

        }

        public static class Bullets {

        }

    }

    // cards...

    // Raflasia games...

}
