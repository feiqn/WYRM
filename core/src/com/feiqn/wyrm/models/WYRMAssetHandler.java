package com.feiqn.wyrm.models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.feiqn.wyrm.WYRMGame;

public class WYRMAssetHandler {

    private final WYRMGame game;

    private final AssetManager manager;

    public TextureRegion // UI
                         menuTexture,
                         backButtonTexture,
                         yellowButtonTexture,
                         purpleButtonTexture,
                         blueButtonTexture;

    public WYRMAssetHandler(WYRMGame game) {
        this.game = game;
        manager = new AssetManager();

        Load();

    }

    public void Load() {
        manager.load("test/menu.png", Texture.class);
    }

    public void Initialize() {
        final Texture menuSpriteSheet = manager.get("menu.png", Texture.class);
        menuTexture = new TextureRegion(menuSpriteSheet, 0,  192,96, 96);
        backButtonTexture = new TextureRegion(menuSpriteSheet, 192,0 , 32, 32);
        yellowButtonTexture = new TextureRegion(menuSpriteSheet, 96, 192, 192,64);
        purpleButtonTexture = new TextureRegion(menuSpriteSheet, 96, 256, 192,64);
        blueButtonTexture = new TextureRegion(menuSpriteSheet,96, 320, 192, 64);

    }

}
