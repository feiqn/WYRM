package com.feiqn.wyrm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.feiqn.wyrm.wyrefactor.handlers.campaign.CampaignHandler;
import com.feiqn.wyrm.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.logic.screens.MainMenuScreen;
import com.feiqn.wyrm.wyrefactor.handlers.MetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

public class WYRMGame extends Game {
	SpriteBatch batch;

    private static WyrScreen activeScreen;

//    private final MetaHandler handlers = new MetaHandler(this);


	public ScreenAdapter activeScreenAdapter; // MFR
	public GridScreen activeGridScreen; // MFR

	public WYRMAssetHandler assetHandler; // MFR
	public CampaignHandler campaignHandler; // MFR


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

		transitionToScreen(activeScreenAdapter);
	}

    public void transitionToScreen(ScreenAdapter screen) {

//        if(screen instanceof GridScreen) {
//            ((GridScreen) screen).fadeOutToBlack();
//            Timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//
//                }
//            }, 3);
//        } else {

            activeScreenAdapter = screen;
            try {
                activeGridScreen = (GridScreen) screen;
            } catch (Exception ignored) {}
            setScreen(screen);
//        }

    }

	@Override
	public void dispose () {
		batch.dispose();
	}

//    public MetaHandler handlers() {
//        return handlers;
//    }
    public WYRMAssetHandler assets() { return assetHandler; }

}
