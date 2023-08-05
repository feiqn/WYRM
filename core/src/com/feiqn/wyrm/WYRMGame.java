package com.feiqn.wyrm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.feiqn.wyrm.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.logic.screens.BattleScreen;
import com.feiqn.wyrm.logic.screens.gamescreens.BattleScreen_1A;

public class WYRMGame extends Game {
	SpriteBatch batch;

	public ScreenAdapter activeScreen;
	public BattleScreen activeBattleScreen;
	public WYRMAssetHandler AssetHandler;

	@Override
	public void create () {
		AssetHandler = new WYRMAssetHandler(this);

		batch = new SpriteBatch();

		activeBattleScreen = new BattleScreen_1A(this);

		activeScreen = activeBattleScreen;
		setScreen(activeBattleScreen);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
