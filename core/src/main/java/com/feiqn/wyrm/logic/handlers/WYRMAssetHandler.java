package com.feiqn.wyrm.logic.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
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

    private Animation<TextureRegionDrawable>
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
                      antal_Flourish,

                      lyra_WalkingNorth,
                      lyra_WalkingSouth,
                      lyra_WalkingEast,
                      lyra_WalkingWest,
                      lyra_Idle,
                      lyra_Flourish,

                      one_WalkingNorth,
                      one_WalkingSouth,
                      one_WalkingEast,
                      one_WalkingWest,
                      one_Idle,
                      one_Flourish,

                      tohni_WalkingNorth,
                      tohni_WalkingSouth,
                      tohni_WalkingEast,
                      tohni_WalkingWest,
                      tohni_Idle,
                      tohni_Flourish,

                      anvil_WalkingNorth,
                      anvil_WalkingSouth,
                      anvil_WalkingEast,
                      anvil_WalkingWest,
                      anvil_Idle,
                      anvil_Flourish,

                      generic_Soldier_WalkingNorth,
                      generic_Soldier_WalkingSouth,
                      generic_Soldier_WalkingEast,
                      generic_Soldier_WalkingWest,
                      generic_Soldier_Idle,
                      generic_Soldier_Flourish,

                      generic_Cavalry_WalkingNorth,
                      generic_Cavalry_WalkingSouth,
                      generic_Cavalry_WalkingEast,
                      generic_Cavalry_WalkingWest,
                      generic_Cavalry_Idle,
                      generic_Cavalry_Flourish;


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
        manager.load("free/fefge/alusq_Deserter_Lance-stand.png", Texture.class);
        manager.load("free/fefge/alusq_Deserter_Lance-walk.png", Texture.class);
        manager.load("free/fefge/ayr_Flier_Harrier-stand.png", Texture.class);
        manager.load("free/fefge/ayr_Flier_Harrier-walk.png", Texture.class);
        manager.load("free/fefge/flasuban_Knight-stand.png", Texture.class);
        manager.load("free/fefge/flasuban_Knight-walk.png", Texture.class);
        manager.load("free/fefge/is_Prince-stand.png", Texture.class);
        manager.load("free/fefge/n426_Pegasus-stand.png", Texture.class);
        manager.load("free/fefge/warpath_Baron_Magic-stand.png", Texture.class);
        manager.load("free/fefge/warpath_Baron_Magic-walk.png", Texture.class);

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

        initializeLeif();
        initializeSoldier();
        initializeShieldKn();

    }

    private void initializeLeif() {
        // TODO: update
        final Texture pegKnSpriteSheet = manager.get("test/ripped/fe/rippedPegKnSprites.png", Texture.class);

        final TextureRegionDrawable pegKnIdle1 = new TextureRegionDrawable(new TextureRegion(pegKnSpriteSheet, 0, 0 , 16, 21));
        final TextureRegionDrawable pegKnIdle2 = new TextureRegionDrawable(new TextureRegion(pegKnSpriteSheet, 0, 32, 16, 21));
        final TextureRegionDrawable pegKnIdle3 = new TextureRegionDrawable(new TextureRegion(pegKnSpriteSheet, 0, 65, 16, 21));

        final Array<TextureRegionDrawable> pegKnIdleFrames = new Array<>();
        pegKnIdleFrames.add(pegKnIdle1, pegKnIdle2, pegKnIdle3);

        leif_Mounted_Idle = new Animation<>(0.25f, pegKnIdleFrames);
        leif_Mounted_Idle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    private void initializeSoldier() {
        final Texture soldierStandSheet = manager.get("free/fefge/alusq_Deserter_Lance-stand.png");
        final Texture soldierWalkSheet  = manager.get("free/fefge/alusq_Deserter_Lance-walk.png");

        final TextureRegionDrawable soldierIdle1 = new TextureRegionDrawable(new TextureRegion(soldierStandSheet,0, 0,  16,16));
        final TextureRegionDrawable soldierIdle2 = new TextureRegionDrawable(new TextureRegion(soldierStandSheet,0, 16, 16,16));
        final TextureRegionDrawable soldierIdle3 = new TextureRegionDrawable(new TextureRegion(soldierStandSheet,0, 32, 16,16));

        final TextureRegion west1 = new TextureRegion(soldierWalkSheet,0,0, 32,32);
        final TextureRegion west2 = new TextureRegion(soldierWalkSheet,0,32,32,32);
        final TextureRegion west3 = new TextureRegion(soldierWalkSheet,0,64,32,32);
        final TextureRegion west4 = new TextureRegion(soldierWalkSheet,0,96,32,32);

        final TextureRegionDrawable soldierWalkWest1 = new TextureRegionDrawable(west1);
        final TextureRegionDrawable soldierWalkWest2 = new TextureRegionDrawable(west2);
        final TextureRegionDrawable soldierWalkWest3 = new TextureRegionDrawable(west3);
        final TextureRegionDrawable soldierWalkWest4 = new TextureRegionDrawable(west4);

        final TextureRegionDrawable soldierWalkSouth1 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 4, 32, 32));
        final TextureRegionDrawable soldierWalkSouth2 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 5, 32, 32));
        final TextureRegionDrawable soldierWalkSouth3 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 6, 32, 32));
        final TextureRegionDrawable soldierWalkSouth4 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 7, 32, 32));

        final TextureRegionDrawable soldierWalkNorth1 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 8,  32, 32));
        final TextureRegionDrawable soldierWalkNorth2 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 9,  32, 32));
        final TextureRegionDrawable soldierWalkNorth3 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 10, 32, 32));
        final TextureRegionDrawable soldierWalkNorth4 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 11, 32, 32));

        west1.flip(false, true);
        west2.flip(false, true);
        west3.flip(false, true);
        west4.flip(false, true);

        final TextureRegionDrawable soldierWalkEast1 = new TextureRegionDrawable(west1);
        final TextureRegionDrawable soldierWalkEast2 = new TextureRegionDrawable(west2);
        final TextureRegionDrawable soldierWalkEast3 = new TextureRegionDrawable(west3);
        final TextureRegionDrawable soldierWalkEast4 = new TextureRegionDrawable(west4);

        final TextureRegionDrawable soldierFlourish1 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet, 0, 32 * 12, 32,32));
        final TextureRegionDrawable soldierFlourish2 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet, 0, 32 * 13, 32,32));
        final TextureRegionDrawable soldierFlourish3 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet, 0, 32 * 14, 32,32));

        final Array<TextureRegionDrawable> soldierIdleFrames = new Array<>();
        soldierIdleFrames.add(soldierIdle1, soldierIdle2, soldierIdle3);

        final Array<TextureRegionDrawable> soldierWalkWestFrames = new Array<>();
        soldierWalkWestFrames.add(soldierWalkWest1, soldierWalkWest2, soldierWalkWest3, soldierWalkWest4);

        final Array<TextureRegionDrawable> soldierWalkSouthFrames = new Array<>();
        soldierWalkSouthFrames.add(soldierWalkSouth1, soldierWalkSouth2, soldierWalkSouth3, soldierWalkSouth4);

        final Array<TextureRegionDrawable> soldierWalkNorthFrames = new Array<>();
        soldierWalkNorthFrames.add(soldierWalkNorth1, soldierWalkNorth2, soldierWalkNorth3, soldierWalkNorth4);

        final Array<TextureRegionDrawable> soldierWalkEastFrames = new Array<>();
        soldierWalkEastFrames.add(soldierWalkEast1, soldierWalkEast2, soldierWalkEast3, soldierWalkEast4);

        final Array<TextureRegionDrawable> soldierFlourishFrames = new Array<>();
        soldierFlourishFrames.add(soldierFlourish1, soldierFlourish2, soldierFlourish3);

        generic_Soldier_Idle = new Animation<>(0.25f, soldierIdleFrames);
        generic_Soldier_Idle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        generic_Soldier_WalkingWest = new Animation<>(0.25f, soldierWalkWestFrames);
        generic_Soldier_WalkingWest.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        generic_Soldier_WalkingEast = new Animation<>(0.25f, soldierWalkEastFrames);
        generic_Soldier_WalkingEast.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        generic_Soldier_WalkingNorth = new Animation<>(0.25f, soldierWalkNorthFrames);
        generic_Soldier_WalkingNorth.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        generic_Soldier_WalkingSouth = new Animation<>(0.25f, soldierWalkSouthFrames);
        generic_Soldier_WalkingSouth.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        generic_Soldier_Flourish = new Animation<>(0.25f,soldierFlourishFrames);
        generic_Soldier_Flourish.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    private void initializeShieldKn() {

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

    public Animation<TextureRegionDrawable> getAnimation(UnitRoster roster, SimpleUnit.AnimationState state) {
        switch (roster) {
            case LEIF: // unmounted
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
                        if(antal_Idle != null) return  antal_Idle;
                        load();
                        manager.finishLoading();
                        initialize();
                        return antal_Idle;

//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
                }

//            case ANVIL:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//
//            case D:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case TOHNI:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case ONE:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case LYRA:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case ERIC:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case RICHARD:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case LEON:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case MARIA:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case MR_TIMN:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case JAY:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case KAI:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case MOE:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case ALEX:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case BREA:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case RILEY:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
//            case GENERIC_CAVALRY:
//                switch (state) {
//                    case IDLE:
//                    case FLOURISH:
//                    case WALKING_EAST:
//                    case WALKING_WEST:
//                    case WALKING_NORTH:
//                    case WALKING_SOUTH:
//                }
            case GENERIC_SOLDIER:
                switch (state) {
                    case IDLE:
                        if(generic_Soldier_Idle != null) return generic_Soldier_Idle;
                        load();
                        manager.finishLoading();
                        initialize();
                        return generic_Soldier_Idle;

                    case FLOURISH:
                        if(generic_Soldier_Flourish != null) return generic_Soldier_Flourish;
                        load();
                        manager.finishLoading();
                        initialize();
                        return generic_Soldier_Flourish;

                    case WALKING_EAST:
                        if(generic_Soldier_WalkingEast != null) return generic_Soldier_WalkingWest;
                        load();
                        manager.finishLoading();
                        initialize();
                        return generic_Soldier_WalkingEast;

                    case WALKING_WEST:
                        if(generic_Soldier_WalkingWest != null) return generic_Soldier_WalkingWest;
                        load();
                        manager.finishLoading();
                        initialize();
                        return generic_Soldier_WalkingWest;

                    case WALKING_NORTH:
                        if(generic_Soldier_WalkingNorth != null) return generic_Soldier_WalkingNorth;
                        load();
                        manager.finishLoading();
                        initialize();
                        return generic_Soldier_WalkingNorth;

                    case WALKING_SOUTH:
                        if(generic_Soldier_WalkingSouth != null) return generic_Soldier_WalkingSouth;
                        load();
                        manager.finishLoading();
                        initialize();
                        return generic_Soldier_WalkingSouth;
                }

            default: return null;
        }

    }
}
