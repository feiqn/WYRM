package com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.games;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.gridworld.RPGridScreen;

public final class WYRMScreens {

    // Who the hell is Jason? I don't know any Jason!

    private WYRMScreens() {}

    public static class Grid {

        public static class A {

            // 0

            public static RPGridScreen _1() {
                return new RPGridScreen(new TmxMapLoader().load("test/maps/1A_v0.tmx")) {
                    @Override
                    protected void setup() {

                    }

                    @Override
                    protected void buildCutscenes() {

                    }

                    @Override
                    protected void win() {

                    }
                };
            }
        }

        public static class B {

        }
    }

    public static class Menu {
        public static class A {

        }

        public static class B {

        }
    }

    public static class Overworld {
        public static class A {

        }

        public static class B {

        }
    }
}
