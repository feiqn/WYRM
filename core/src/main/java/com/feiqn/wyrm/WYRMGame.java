package com.feiqn.wyrm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.feiqn.wyrm.logic.handlers.CampaignHandler;
import com.feiqn.wyrm.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.logic.screens.BattleScreen;
import com.feiqn.wyrm.logic.screens.MainMenuScreen;

public class WYRMGame extends Game {
	SpriteBatch batch;

	public ScreenAdapter activeScreen;
	public BattleScreen activeBattleScreen;

	public WYRMAssetHandler assetHandler;
	public CampaignHandler campaignHandler;


	// Entrance to the program.

	@Override
	public void create () {
		assetHandler = new WYRMAssetHandler(this);
        assetHandler.load();
        assetHandler.getManager().finishLoading();
		campaignHandler = new CampaignHandler(this);
		batch = new SpriteBatch();
		activeScreen = new MainMenuScreen(this);
		setScreen(activeScreen);
	}

    public void setScreen(ScreenAdapter screen) {
        super.setScreen(screen);
        activeScreen = screen;
        if(screen instanceof BattleScreen) {
            activeBattleScreen = (BattleScreen) screen;
        }
    }

	@Override
	public void dispose () {
		batch.dispose();
	}
}
