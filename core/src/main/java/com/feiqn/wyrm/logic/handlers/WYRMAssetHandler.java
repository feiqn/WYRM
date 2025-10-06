package com.feiqn.wyrm.logic.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.badlogic.gdx.graphics.*;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class WYRMAssetHandler {

    private final WYRMGame game;

    private final AssetManager manager;

    public Label.LabelStyle menuLabelStyle;
    public Label.LabelStyle nameLabelStyle;

    public String bestFriend;

    public TextureRegion // UNITS
                         soldierTexture,
                         mercenaryTexture,
                         pegKnightTexture,
                         leifUnmountedTexture,
                         ballistaTexture,
                         armorKnightTexture,

                         // UI
                         menuTexture,
                         backButtonTexture,
                         yellowButtonTexture,
                         purpleButtonTexture,
                         blueButtonTexture,
                         solidBlueTexture;

    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>
                      leif_Mounted_WalkingNorth,
                      leif_Mounted_WalkingSouth,
                      leif_Mounted_WalkingEast,
                      leif_Mounted_WalkingWest,
                      leif_Mounted_Idle,
                      leif_Mounted_Flourish,

                      leif_Unmounted_WalkingNorth,
                      leif_Unmounted_WalkingSouth,
                      leif_Unmounted_WalkingEast,
                      leif_Unmounted_WalkingWest,
                      leif_Unmounted_Idle,
                      leif_Unmounted_Flourish,

                      antal_WalkingNorth,
                      antal_WalkingSouth,
                      antal_WalkingEast,
                      antal_WalkingWest,
                      antal_Idle,
                      antal_Flourish;


    public WYRMAssetHandler(WYRMGame game) {
        this.game = game;
        manager = new AssetManager();

        bestFriend = "Ashe";

        load();
        manager.finishLoading();
        initialize();
        initialiseFont();
    }

    public void load() {
        manager.load("ui/menu.png", Texture.class);
        manager.load("test/ripped/fe/sprites.png", Texture.class);
        manager.load("test/ripped/fe/rippedPegKnSprites.png", Texture.class);
    }

    public void initialize() {
        final Texture unitSpriteSheet = manager.get("test/ripped/fe/sprites.png", Texture.class);
        soldierTexture       = new TextureRegion(unitSpriteSheet, 16*11, 16, 16,16);
        pegKnightTexture     = new TextureRegion(unitSpriteSheet, 16*13,16*4+10, 16,22);
        armorKnightTexture   = new TextureRegion(unitSpriteSheet, 16*8, 16, 16,16);
        ballistaTexture      = new TextureRegion(unitSpriteSheet, 0, 16*8+10, 16, 22);
        mercenaryTexture     = new TextureRegion(unitSpriteSheet, 0, 0, 16, 16);
        leifUnmountedTexture = new TextureRegion(unitSpriteSheet, 16, 16*3,16,16);

        final Texture menuSpriteSheet = manager.get("ui/menu.png", Texture.class);
        menuTexture         = new TextureRegion(menuSpriteSheet, 0,  192, 96, 96);
        backButtonTexture   = new TextureRegion(menuSpriteSheet, 192,0  , 32, 32);
        yellowButtonTexture = new TextureRegion(menuSpriteSheet, 96, 192, 192,64);
        purpleButtonTexture = new TextureRegion(menuSpriteSheet, 96, 256, 192,64);
        blueButtonTexture   = new TextureRegion(menuSpriteSheet, 96, 320, 192,64);
        solidBlueTexture    = new TextureRegion(menuSpriteSheet, 0,  192, 32, 32);

        final Texture pegKnSpriteSheet = manager.get("test/ripped/fe/rippedPegKnSprites.png", Texture.class);
        final TextureRegion pegKnIdleFrame1 = new TextureRegion(pegKnSpriteSheet, 0, 0 , 16, 21);
        final TextureRegion pegKnIdleFrame2 = new TextureRegion(pegKnSpriteSheet, 0, 32, 16, 21);
        final TextureRegion pegKnIdleFrame3 = new TextureRegion(pegKnSpriteSheet, 0, 64, 16, 21);
        final Array<TextureRegion> pegKnIdleFrames = new Array<>();
        pegKnIdleFrames.add(pegKnIdleFrame1, pegKnIdleFrame2, pegKnIdleFrame3);
        leif_Mounted_Idle = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.025f, pegKnIdleFrames);
    }

    private void initialiseFont() {
        // TODO: this is where we can define multicolor fonts to highlight keywords

        final Texture fontTexture = new Texture(Gdx.files.internal("ui/font/tinyFont.png"), true);
        fontTexture.setFilter(Texture.TextureFilter.MipMapNearestNearest, Texture.TextureFilter.Linear);

        BitmapFont tinyFont = new BitmapFont(Gdx.files.internal("ui/font/tinyFont.fnt"), new TextureRegion(fontTexture), false);
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font/COPPERPLATE.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter menuFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        menuFontParameter.color = Color.WHITE;
        menuFontParameter.borderWidth = 1.5f;
        menuFontParameter.borderColor = Color.BLACK;
        menuFontParameter.size = 18;
        menuFontParameter.incremental = true;
        menuFontParameter.spaceX = 1;
        menuFontParameter.spaceY = 15;

        FreeTypeFontGenerator.FreeTypeFontParameter nameFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        nameFontParameter.color = Color.WHITE;
        nameFontParameter.borderWidth = 1.5f;
        nameFontParameter.borderColor = Color.NAVY;
        nameFontParameter.shadowColor = Color.BLACK;
        nameFontParameter.shadowOffsetX = 1;
        nameFontParameter.shadowOffsetY = 1;
        nameFontParameter.size = 16;
        nameFontParameter.incremental = true;
        nameFontParameter.spaceX = 1;
        nameFontParameter.spaceY = 15;

        tinyFont.getData().markupEnabled = true;

        tinyFont = fontGenerator.generateFont(menuFontParameter);

        menuLabelStyle = new Label.LabelStyle();
        menuLabelStyle.font = tinyFont;

        tinyFont = fontGenerator.generateFont(nameFontParameter);

        nameLabelStyle = new Label.LabelStyle();
        nameLabelStyle.font = tinyFont;

        fontGenerator.dispose();

    }

    public AssetManager getManager() { return manager; }

    public Animation<TextureRegion> getAnimation(UnitRoster roster, SimpleUnit.AnimationState state) {
        switch (roster) {
            case LEIF:
                switch (state) {
                    case IDLE:
                        if(leif_Unmounted_Idle != null) return leif_Unmounted_Idle;
                        load();
                        manager.finishLoading();
                        initialize();
                        return leif_Unmounted_Idle;

                    case FLOURISH:
                        if(leif_Unmounted_Flourish != null) return leif_Unmounted_Flourish;
                        load();
                        manager.finishLoading();
                        initialize();
                        return leif_Unmounted_Flourish;

                    case WALKING_EAST:
                        if(leif_Unmounted_WalkingEast != null) return leif_Unmounted_WalkingEast;
                        load();
                        manager.finishLoading();
                        initialize();
                        return leif_Unmounted_WalkingEast;

                    case WALKING_WEST:
                        if(leif_Unmounted_WalkingWest != null) return leif_Unmounted_WalkingWest;
                        load();
                        manager.finishLoading();
                        initialize();
                        return leif_Unmounted_WalkingWest;

                    case WALKING_NORTH:
                        if(leif_Unmounted_WalkingNorth != null) return leif_Unmounted_WalkingNorth;
                        load();
                        manager.finishLoading();
                        initialize();
                        return leif_Unmounted_WalkingNorth;

                    case WALKING_SOUTH:
                        if(leif_Unmounted_WalkingSouth != null) return leif_Unmounted_WalkingSouth;
                        load();
                        manager.finishLoading();
                        initialize();
                        return leif_Unmounted_WalkingSouth;
                }

            case LEIF_MOUNTED:
                switch (state) {
                    case IDLE:
                        if(leif_Mounted_Idle != null) return leif_Mounted_Idle;
                        load();
                        manager.finishLoading();
                        initialize();
                        return leif_Mounted_Idle;

                    case FLOURISH:
                        if(leif_Mounted_Flourish != null) return leif_Mounted_Flourish;
                        load();
                        manager.finishLoading();
                        initialize();
                        return leif_Mounted_Flourish;

                    case WALKING_EAST:
                        if(leif_Mounted_WalkingEast != null) return leif_Mounted_WalkingEast;
                        load();
                        manager.finishLoading();
                        initialize();
                        return leif_Mounted_WalkingWest;

                    case WALKING_WEST:
                        if(leif_Mounted_WalkingWest != null) return leif_Mounted_WalkingWest;
                        load();
                        manager.finishLoading();
                        initialize();
                        return leif_Mounted_WalkingWest;

                    case WALKING_NORTH:
                        if(leif_Mounted_WalkingNorth != null) return  leif_Mounted_WalkingNorth;
                        load();
                        manager.finishLoading();
                        initialize();
                        return leif_Mounted_WalkingNorth;

                    case WALKING_SOUTH:
                        if(leif_Mounted_WalkingSouth != null) return leif_Mounted_WalkingSouth;
                        load();
                        manager.finishLoading();
                        initialize();
                        return leif_Mounted_WalkingSouth;
                }

            case ANTAL:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }

            case ANVIL:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }

            case D:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case TOHNI:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case ONE:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case LYRA:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case ERIC:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case RICHARD:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case LEON:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case MARIA:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case MR_TIMN:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case JAY:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case KAI:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case MOE:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case ALEX:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case BREA:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case RILEY:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case GENERIC_CAVALRY:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
            case GENERIC_SOLDIER:
                switch (state) {
                    case IDLE:
                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                }
        }
    }
}
