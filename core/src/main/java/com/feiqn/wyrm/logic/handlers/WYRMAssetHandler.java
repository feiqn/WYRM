package com.feiqn.wyrm.logic.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;

public class WYRMAssetHandler {

    private final WYRMGame game;

    private final AssetManager manager;

    public Label.LabelStyle menuLabelStyle;
    public BitmapFont menuFont;

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

        initialiseFont();
//        Load();
    }

    public void Load() {
//        manager.load("ui/menu.png", Texture.class);
//        manager.load("test/ripped/fe/sprites.png", Texture.class);
    }

    public void Initialize() {
//        final Texture unitSpriteSheet = manager.get("sprites.png", Texture.class);
//        soldierTexture      = new TextureRegion(unitSpriteSheet, 16, 16*11, 16,16);
//        pegKnightTexture    = new TextureRegion(unitSpriteSheet, 16*4+10,16*13, 16,22);
//        armorKnightTexture  = new TextureRegion(unitSpriteSheet, 16, 16*8, 16,16);
//        ballistaTexture     = new TextureRegion(unitSpriteSheet, 16*5+11, 16*15, 16, 21);
//
//        final Texture menuSpriteSheet = manager.get("menu.png", Texture.class);
//        menuTexture         = new TextureRegion(menuSpriteSheet, 0,  192,96, 96);
//        backButtonTexture   = new TextureRegion(menuSpriteSheet, 192,0 , 32, 32);
//        yellowButtonTexture = new TextureRegion(menuSpriteSheet, 96, 192, 192,64);
//        purpleButtonTexture = new TextureRegion(menuSpriteSheet, 96, 256, 192,64);
//        blueButtonTexture   = new TextureRegion(menuSpriteSheet,96, 320, 192, 64);

    }

    private void initialiseFont() {
        final Texture fontTexture = new Texture(Gdx.files.internal("ui/font/tinyFont.png"), true);
        fontTexture.setFilter(Texture.TextureFilter.MipMapNearestNearest, Texture.TextureFilter.Linear);

        menuFont = new BitmapFont(Gdx.files.internal("ui/font/tinyFont.fnt"), new TextureRegion(fontTexture), false);
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font/COPPERPLATE.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter menuFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        menuFontParameter.color = Color.WHITE;
        menuFontParameter.borderWidth = 2f;
        menuFontParameter.borderColor = Color.BLACK;
        menuFontParameter.size = 16;
        menuFontParameter.incremental = true;
        menuFont = fontGenerator.generateFont(menuFontParameter);
        fontGenerator.dispose();

        menuLabelStyle = new Label.LabelStyle();
        menuLabelStyle.font = menuFont;
    }

    public AssetManager getManager() { return manager; }
}
