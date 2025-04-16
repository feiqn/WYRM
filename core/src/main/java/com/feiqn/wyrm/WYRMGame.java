package com.feiqn.wyrm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.logic.handlers.campaign.CampaignHandler;
import com.feiqn.wyrm.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.logic.screens.MainMenuScreen;

import java.util.Timer;
import java.util.TimerTask;

public class WYRMGame extends Game {
	SpriteBatch batch;

	public ScreenAdapter activeScreen;
	public GridScreen activeGridScreen;

	public WYRMAssetHandler assetHandler;
	public CampaignHandler campaignHandler;


	// Entrance to the program.

	@Override
	public void create () {
		assetHandler = new WYRMAssetHandler(this);
		campaignHandler = new CampaignHandler(this);
		batch = new SpriteBatch();
		activeScreen = new MainMenuScreen(this);

//        Gdx.graphics.setUndecorated(true);
//        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
//        Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height + 1);

		transitionScreen(activeScreen);
	}

    public void transitionScreen(ScreenAdapter screen) {

//        if(screen instanceof GridScreen) {
//            ((GridScreen) screen).fadeOutToBlack();
//            Timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//
//                }
//            }, 3);
//        } else {
            setScreen(screen);
//        }

    }

//    private void setScreen(ScreenAdapter screen) {
//        super.setScreen(screen);
//        activeScreen = screen;
//        if(screen instanceof GridScreen) {
//            activeGridScreen = (GridScreen) screen;
//        } else {
//            activeGridScreen = null;
//        }
//    }

	@Override
	public void dispose () {
		batch.dispose();
	}
}
