package com.feiqn.wyrm.wyrefactor.actors.items.items;

import com.badlogic.gdx.Gdx;

public final class ItemBank {

    private ItemBank() {}

    // Items are small and live in menus,
    // as opposed to props which are large
    // and live on the world.

    public final static class Containers {

        public static WyrItem Pocket() {
            return new WyrItem() {
                @Override
                protected void setup() {
                    super.setup();
                    isContainer = true;
                    containerSize = 1;
                }
            };
        }

    }

    public final static class Potions {

        public static WyrItem Heal() {
            return new WyrItem() {
                @Override
                protected void setup() {
                    Gdx.app.log("TODO", "XD");
                }
            };
        }

    }

    public final static class Keys {

        public static WyrItem MasterKey() {
            return new WyrItem() {
                @Override
                protected void setup() {
                    Gdx.app.log("TODO", "XD");
                }
            };
        }

    }

}
