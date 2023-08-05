package com.feiqn.wyrm.logic.handlers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.feiqn.wyrm.WYRMGame;

public class WYRMAssetHandler {

    private final WYRMGame game;

    private final AssetManager manager;

    public TextureRegion // UNITS
                         soldierTexture,
                         pegKnightTexture,
                         ballistaTexture,
                         armorKnightTexture,

                         // UI
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
        manager.load("ui/menu.png", Texture.class);
        manager.load("test/ripped/fe/sprites.png", Texture.class);
    }

    public void Initialize() {
//        final Texture unitSpriteSheet = manager.get("sprites.png", Texture.class);
//        soldierTexture      = new TextureRegion(unitSpriteSheet, 16, 16*11, 16,16);
//        pegKnightTexture    = new TextureRegion(unitSpriteSheet, 16*4+10,16*13, 16,22);
//        armorKnightTexture  = new TextureRegion(unitSpriteSheet, 16, 16*8, 16,16);
//        ballistaTexture     = new TextureRegion(unitSpriteSheet, 16*5+11, 16*15, 16, 21);

//        final Texture menuSpriteSheet = manager.get("menu.png", Texture.class);
//        menuTexture         = new TextureRegion(menuSpriteSheet, 0,  192,96, 96);
//        backButtonTexture   = new TextureRegion(menuSpriteSheet, 192,0 , 32, 32);
//        yellowButtonTexture = new TextureRegion(menuSpriteSheet, 96, 192, 192,64);
//        purpleButtonTexture = new TextureRegion(menuSpriteSheet, 96, 256, 192,64);
//        blueButtonTexture   = new TextureRegion(menuSpriteSheet,96, 320, 192, 64);

    }

}
