package com.feiqn.wyrm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.campaign.WyrCampaignHandler;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.OLD_DATA.logic.screens.OLD_MainMenuScreen;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;

public final class WYRMGame extends Game implements WyrFrame {

    private static MetaHandler metaHandler = null;
    private static WYRMAssetHandler assetHandler = null; // MFR
//    private static WyrCampaignHandler wyrCampaignHandler = null;

	public static ScreenAdapter activeScreenAdapter  = null; // MFR

    // Entrance to the program.

    // I learned about Singleton classes today.
    // Is this what I've been looking for,
    // or am I still confused and wrong?
    private static final WYRMGame ROOT = new WYRMGame();
    private WYRMGame() { super(); }

	@Override
	public void create () {
        assetHandler        = new WYRMAssetHandler();
//		wyrCampaignHandler  = new WyrCampaignHandler();
		activeScreenAdapter = new OLD_MainMenuScreen(this);

//        Gdx.graphics.setUndecorated(true);
//        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
//        Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height + 1);

		OLD_TransitionToScreen(activeScreenAdapter);
	}

    public void setHandler(MetaHandler handler) {
        metaHandler = handler;
    }

    public void OLD_TransitionToScreen(ScreenAdapter screen) {
        activeScreenAdapter = screen;
        setScreen(screen);
    }

//	@Override
//	public void dispose () {
//		batch.dispose();
//	}

    public static WYRMGame           root() { return ROOT; }
    public static WYRMAssetHandler   assets() { return assetHandler; }
//    public static WyrCampaignHandler campaign() { return wyrCampaignHandler; }
    public static @Null MetaHandler metaHandler() { return metaHandler; }
}
