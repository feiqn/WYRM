package com.feiqn.wyrm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.WyrCampaignHandler;
import com.feiqn.wyrm.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.logic.screens.MainMenuScreen;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

public final class WYRMGame extends Game {
	SpriteBatch batch;

    private static WyrScreen activeScreen = null;

    private static WYRMAssetHandler assetHandler = null; // MFR
    private static WyrCampaignHandler wyrCampaignHandler = null;

	public static ScreenAdapter activeScreenAdapter  = null; // MFR
	public static OLD_GridScreen activeOLDGridScreen = null; // MFR

    // Entrance to the program.

    // I learned about Singleton classes today.
    // Is this what I've been looking for,
    // or am I still confused and wrong?
    private static final WYRMGame ROOT = new WYRMGame();
    private WYRMGame() { super(); }

	@Override
	public void create () {
        batch               = new SpriteBatch();
        assetHandler        = new WYRMAssetHandler(this);
		wyrCampaignHandler  = new WyrCampaignHandler(this);
		activeScreenAdapter = new MainMenuScreen(this);

//        Gdx.graphics.setUndecorated(true);
//        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
//        Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height + 1);

		OLD_TransitionToScreen(activeScreenAdapter);
	}

    public void setScreen(WyrScreen screen) {
        activeScreen = screen;
        super.setScreen(screen);
    }

    public void OLD_TransitionToScreen(ScreenAdapter screen) {
        activeScreenAdapter = screen;
        try {
            activeOLDGridScreen = (OLD_GridScreen) screen;
        } catch (Exception ignored) {}
        setScreen(screen);
    }

	@Override
	public void dispose () {
		batch.dispose();
	}

    public static WYRMGame           root() { return ROOT; }
    public static WyrScreen          activeScreen() { return activeScreen; }
    public static WYRMAssetHandler   assets() { return assetHandler; }
    public static WyrCampaignHandler campaign() { return wyrCampaignHandler; }

}
