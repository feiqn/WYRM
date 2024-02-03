package com.feiqn.wyrm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.feiqn.wyrm.logic.handlers.CampaignHandler;
import com.feiqn.wyrm.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.logic.screens.BattleScreen;
import com.feiqn.wyrm.logic.screens.MainMenuScreen;
import com.feiqn.wyrm.logic.screens.gamescreens.BattleScreen_1A;

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
		campaignHandler = new CampaignHandler(this);
		batch = new SpriteBatch();
		activeScreen = new MainMenuScreen(this);
		setScreen(activeScreen);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
