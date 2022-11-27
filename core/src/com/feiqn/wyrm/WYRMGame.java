package com.feiqn.wyrm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.feiqn.wyrm.logic.screens.BattleScreen;
import com.feiqn.wyrm.models.WYRMAssetHandler;

public class WYRMGame extends Game {
	SpriteBatch batch;

	public ScreenAdapter activeScreen;
	public BattleScreen activeBattleScreen;
//	public WYRMAssetHandler AssetHandler;

	@Override
	public void create () {
//		AssetHandler = new WYRMAssetHandler(this);

		batch = new SpriteBatch();

		activeBattleScreen = new BattleScreen(this);
		activeScreen = activeBattleScreen;
		setScreen(activeBattleScreen);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
