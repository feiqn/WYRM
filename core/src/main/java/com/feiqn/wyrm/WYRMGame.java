package com.feiqn.wyrm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignHandler;
import com.feiqn.wyrm.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.logic.screens.MainMenuScreen;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

public final class WYRMGame extends Game {
	SpriteBatch batch;

    private static WyrScreen activeScreen;

    private final MetaHandler handlers = new MetaHandler(this);

	public static ScreenAdapter activeScreenAdapter; // MFR
	public static OLD_GridScreen activeOLDGridScreen; // MFR

    // TODO: make private
	public static WYRMAssetHandler assetHandler; // MFR
	public static CampaignHandler campaignHandler;


	// Entrance to the program.

	@Override
	public void create () {
		assetHandler = new WYRMAssetHandler(this);
		campaignHandler = new CampaignHandler(this);
		batch = new SpriteBatch();
		activeScreenAdapter = new MainMenuScreen(this);

//        Gdx.graphics.setUndecorated(true);
//        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
//        Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height + 1);

		OLD_TransitionToScreen(activeScreenAdapter);
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

    public WyrScreen getActiveScreen() { return activeScreen; }

    public MetaHandler handlers() { return handlers; }

    public WYRMAssetHandler assets() { return assetHandler; }

}
