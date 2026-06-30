package com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.campaigns.WillYouRememberMe;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.WyrScreen;

public final class WYRMScreens {

    // Who the hell is Jason? I don't know any Jason!

    private WYRMScreens() {}

    public static WyrScreen STAGE_1A() {
        return new WyrScreen(new TmxMapLoader().load("test/maps/1A_v0.tmx")) {
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
