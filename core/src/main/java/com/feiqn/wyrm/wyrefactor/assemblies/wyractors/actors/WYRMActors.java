package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor.Prop;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.Interactions;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.helpers.Material;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;

public final class WYRMActors implements WyrFrame {

    private WYRMActors() {}

    public static class WyrEmblem { // AKA "RPGrid"

        public static class Unit {



        }

        public static class Props {

            public static Prop ballista() {
                return new Prop(GameKit.RPG.PropType.BALLISTA, handlers.assets().ballistaTexture) {
                    @Override
                    protected void setup() {
//                        ((PropInventory) inventory).setArmament(); // TODO NEXT
                        isSolid = true;
                        material = new Material(GameKit.RPG.Materials.Type.WOOD, GameKit.RPG.Materials.Type.METAL);
                    }

                    @Override
                    public void deriveInteractions(WyrActor.Unit actingUponMe) {
                        addEphemeralInteraction(Interactions.Aim(actingUponMe, this));

                    }

                    @Override
                    public void deriveInteractions(WyrActor.Unit actingUponMe, GridPath pathToMe) {

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
