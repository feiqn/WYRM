package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.prefab;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.WyrActor.Prop;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.WyrActor.Unit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.prefabs.Interactions;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.WyrInventory.PropInventory;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.prefabs.Quartermaster;
import com.feiqn.wyrm.wyrefactor.helpers.Material;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.Name.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.RPGClassID.*;

public final class WYRMActors implements WyrFrame {

    private WYRMActors() {}

    public static class WyrEmblem { // AKA "RPGrid"

        public static class Units {

            /**
             * Allies
             */

            public static Unit danial() {
                final Unit danial = new Unit(Danial, handlers.assets().soldierTexture);

                danial.stats().getRPGClass().setTo(SOLDIER);

//                danial.stats().setBaseSpeed(1);
                danial.setExamine("His retirement party is tomorrow.");

                danial.setTeamAlignment(TeamAlignment.ALLY);

                return danial;
            }

            /**
             * Enemies
             */

            public static Unit collin() {
                final Unit collin = new Unit(Collin, handlers.assets().soldierTexture);

                collin.stats().getRPGClass().setTo(SOLDIER);

                collin.stats().setBaseDefense(2);
                collin.setExamine("Always the life of the party.");

                collin.setTeamAlignment(TeamAlignment.ENEMY);

                return collin;
            }

            public static Unit liam() {
                final Unit liam = new Unit(Liam, handlers.assets().soldierTexture);

                liam.stats().getRPGClass().setTo(SOLDIER);

                liam.stats().setBaseDefense(1);
                liam.stats().setBaseHealth(2, true);
                liam.setExamine("Misses his dog.");

                liam.setTeamAlignment(TeamAlignment.ENEMY);

                return liam;
            }

            public static Unit gordon() {
                final Unit gordon = new Unit(Gordon, handlers.assets().soldierTexture);

                gordon.stats().getRPGClass().setTo(SOLDIER);

                gordon.stats().setBaseDefense(1);
                gordon.stats().setBaseHealth(-1, true);
                gordon.setExamine("Great at sports.");

                gordon.setTeamAlignment(TeamAlignment.ENEMY);

                return gordon;
            }

            public static Unit fran() {
                final Unit fran = new Unit(Fran, handlers.assets().soldierTexture);

                fran.stats().getRPGClass().setTo(SOLDIER);

                fran.stats().setBaseDefense(-1);
                fran.stats().setBaseHealth(-1, true);
                fran.setExamine("Has a great, big, loving family, somewhere.");

                fran.setTeamAlignment(TeamAlignment.ENEMY);

                return fran;
            }

            public static Unit kaylie() {
                final Unit kaylie = new Unit(Kaylie, handlers.assets().soldierTexture);

                kaylie.stats().getRPGClass().setTo(SOLDIER);

                kaylie.setExamine("Always had to work twice as hard as her peers.");

                fran().setTeamAlignment(TeamAlignment.ENEMY);

                return kaylie;
            }

            /**
             * "fodder"
             */

            public static Unit amy() {
                final Unit amy = new Unit(Amy, handlers.assets().soldierTexture);

                amy.stats().getRPGClass().setTo(SOLDIER);

                amy.stats().setBaseHealth(-1, true);
                amy.setExamine("Would rather be gardening.");

                amy.setTeamAlignment(TeamAlignment.ENEMY);

                return amy;
            }


            /**
             * "real" "people"
             */

            public static Unit leif() {
                return new Unit(Leif, handlers.assets().leifUnmountedTexture) {
                    @Override
                    protected void setup() {
                        stats.getRPGClass().setTo(PLANESWALKER);
                        setExamine("A displaced youth with a knack for animal husbandry.");
                    }
                };
            }

            public static Unit antal() {
                final Unit antal = new Unit(Antal, handlers.assets().armorKnightTexture);

                antal.stats().getRPGClass().setTo(SHIELD_KNIGHT);

                antal.stats().setBaseDefense(+1);
                antal.stats().setBaseHealth(+1, true);

                antal.setExamine("His family is out there somewhere, he can feel it.");

                return antal;
            }

        }

        public static class Props {

            // TODO:
            //  copy constructor for props so there can be more than one

            public static Prop ballista() { return ballista(null); }
            public static Prop ballista(@Null String uniqueID) {
                return new Prop(GameKit.RPG.PropType.BALLISTA, handlers.assets().ballistaTexture) {
                    @Override
                    protected void setup() {
                        if(uniqueID != null) setName(uniqueID);
                        ((PropInventory) inventory).setArmament(Quartermaster.PropWeapons.HeavyBallista());
                        isSolid = true;
                        material = new Material(GameKit.RPG.Materials.Type.WOOD, GameKit.RPG.Materials.Type.METAL);
                    }

                    @Override
                    public void deriveInteractions(Unit actingUponMe) {
                        addEphemeralInteraction(Interactions.Aim(actingUponMe, this));

                    }

                    @Override
                    public void deriveInteractions(Unit actingUponMe, GridPath pathToMe) {

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
