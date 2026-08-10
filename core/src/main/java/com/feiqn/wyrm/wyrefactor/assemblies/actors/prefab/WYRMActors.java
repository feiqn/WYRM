package com.feiqn.wyrm.wyrefactor.assemblies.actors.prefab;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor.Prop;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor.Unit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.prefabs.Interactions;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.prefabs.Quartermaster;
import com.feiqn.wyrm.wyrefactor.helpers.Material;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.Name.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.RPGClass.RPGClassID.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatType.*;

public final class WYRMActors implements WyrFrame {

    private WYRMActors() {}

    // Who the hell is Jason? I don't know any Jason!
    // This is Object-Oriented Programming!

    public static class WyrEmblem { // AKA "RPGrid"

        public static class Units {

            /**
             * Allies
             */

            public static Unit danial() {
                final Unit danial = new Unit(Danial, SOLDIER);

                danial.setExamine("His retirement party is tomorrow.");

                danial.setTeamAlignment(TeamAlignment.ALLY);

                return danial;
            }

            /**
             * Enemies
             */

            public static Unit collin() {
                final Unit collin = new Unit(Collin, SOLDIER);

                collin.stats().setBaseValue(DEFENSE, 2);
                collin.setExamine("Always the life of the party.");

                collin.setTeamAlignment(TeamAlignment.ENEMY);

                return collin;
            }

            public static Unit liam() {
                final Unit liam = new Unit(Liam, SOLDIER);

                liam.stats().setBaseValue(DEFENSE, 1);
                liam.stats().setBaseValue(HEALTH, 2);
                liam.setExamine("Misses his dog.");

                liam.setTeamAlignment(TeamAlignment.ENEMY);

                return liam;
            }

            public static Unit gordon() {
                final Unit gordon = new Unit(Gordon, SOLDIER);

                gordon.stats().setBaseValue(DEFENSE, 1);
                gordon.stats().setBaseValue(HEALTH, -1);
                gordon.stats().setBaseValue(SPEED, 3);
                gordon.setExamine("Great at sports.");

                gordon.setTeamAlignment(TeamAlignment.ENEMY);

                return gordon;
            }

            public static Unit fran() {
                final Unit fran = new Unit(Fran, SOLDIER);

                fran.stats().setBaseValue(DEFENSE, -1);
                fran.stats().setBaseValue(HEALTH, -1);
                fran.setExamine("Has a great, big, loving family, somewhere.");

                fran.setTeamAlignment(TeamAlignment.ENEMY);

                return fran;
            }

            public static Unit kaylie() {
                final Unit kaylie = new Unit(Kaylie, SOLDIER);

                kaylie.setExamine("Always had to work twice as hard as her peers.");

                fran().setTeamAlignment(TeamAlignment.ENEMY);

                return kaylie;
            }

            /**
             * "fodder"
             */

            public static Unit amy() {
                final Unit amy = new Unit(Amy, SOLDIER);

                amy.stats().setBaseValue(HEALTH, -1);
                amy.setExamine("Would rather be gardening.");

                amy.setTeamAlignment(TeamAlignment.ENEMY);

                return amy;
            }


            /**
             * "real" "people"
             */

            public static Unit leif() {
                return new Unit(Leif, PLANESWALKER) {
                    @Override
                    protected void setup() {
                        stats.ownMount("ashe");
                        setExamine("A displaced youth with a knack for animal husbandry.");


                    }
                };
            }

            public static Unit antal() {
                final Unit antal = new Unit(Antal, SHIELD_KNIGHT);

                antal.stats().setBaseValue(DEFENSE, 1);
                antal.stats().setBaseValue(HEALTH, 1);

                antal.setExamine("His family is out there somewhere, he can feel it.");

                return antal;
            }

            public static class Animals {

                public static WyrActor fromID(String id) {
                    if(id.equalsIgnoreCase("ashe")) return Ashe();

                    return null;
                }

                public static WyrActor Ashe() {
                    return new WyrActor("Ashe", DOMESTICATED_ANIMAL) {
                        @Override
                        protected void setup() {
                            stats.setBaseValue(STRENGTH, 1);
                            stats.setBaseValue(MAGIC, 1);
                            stats.setBaseValue(DEFENSE, 2);
                            stats.setBaseValue(RESISTANCE, 2);
                            stats.setBaseValue(SPEED, 3);
                            stats.setBaseValue(HEALTH, 3);
                        }
                    };
                }

            }

        }

        public static class Props {

            public static class Objectives {

            }

            public static Prop ballista() { return ballista(null); }
            public static Prop ballista(@Null String uniqueID) {
                return new Prop(GameKit.RPG.PropType.BALLISTA, handlers.assets().ballistaTexture) {
                    @Override
                    protected void setup() {
                        if(uniqueID != null) setName(uniqueID);
                        inventory.equipWeapon(Quartermaster.PropWeapons.HeavyBallista());
                        blocksOwnTeam = true;
                        material = new Material(GameKit.RPG.Materials.Type.WOOD, GameKit.RPG.Materials.Type.METAL);
                    }

                    @Override
                    public void deriveInteractions(WyrActor actingUponMe) {
                        addEphemeralInteraction(Interactions.Aim(actingUponMe, this));

                    }
                };
            }

        }

        public static class Bullets {

            public static Image debugBullet() {
                final Image bullet = new Image(handlers.assets().ballistaBulletTexture);
                bullet.setSize(.75f, .75f);
                bullet.setColor(1,1,1,0);

                bullet.addAction(Actions.forever(Actions.rotateBy(360, .3f)));
                bullet.addAction(Actions.forever(Actions.sequence(
                    Actions.fadeIn(.1f),
                    Actions.fadeOut(.1f)
                )));

                return bullet;
            }

        }

    }

    // cards...

    // Raflasia games...

}
