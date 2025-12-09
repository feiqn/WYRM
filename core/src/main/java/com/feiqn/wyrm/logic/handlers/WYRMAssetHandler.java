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
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;

public class WYRMAssetHandler {

    private final WYRMGame game;

    private final AssetManager manager;

    public Label.LabelStyle menuLabelStyle;
    public Label.LabelStyle nameLabelStyle;

    public String bestFriendName;

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
                         solidBlueTexture,

                         // BULLETS
                        ballistaBulletTexture;

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

        bestFriendName = "Ashe";

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
        manager.load("free/fefge/bard-stand.png", Texture.class);
        manager.load("free/fefge/bard-walk.png", Texture.class);
        manager.load("free/fefge/n426_Pegasus-stand.png", Texture.class);
        manager.load("free/fefge/warpath_Baron_Magic-stand.png", Texture.class);
        manager.load("free/fefge/warpath_Baron_Magic-walk.png", Texture.class);
        manager.load("lazyBullet.png", Texture.class);

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

        final Texture lazyBullet = manager.get("lazyBullet.png", Texture.class);
        ballistaBulletTexture = new TextureRegion(lazyBullet, 0,0,16,16);

        initializeLeif();
        initializeSoldier();
        initializeShieldKn();
    }

    private void initializeLeif() {
        final Texture leifUnmountedIdleSheet = manager.get("free/fefge/bard-stand.png", Texture.class);
        final Texture leifUnmountedWalkSheet = manager.get("free/fefge/bard-walk.png", Texture.class);


        final TextureRegionDrawable leifUnmountedIdle1 = new TextureRegionDrawable(new TextureRegion(leifUnmountedIdleSheet, 0, 0, 16, 16));
        final TextureRegionDrawable leifUnmountedIdle2 = new TextureRegionDrawable(new TextureRegion(leifUnmountedIdleSheet, 0, 16, 16, 16));
        final TextureRegionDrawable leifUnmountedIdle3 = new TextureRegionDrawable(new TextureRegion(leifUnmountedIdleSheet, 0, 32, 16, 16));

        final TextureRegionDrawable leifUnmountedWalkWest1 = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 0, 32, 32));
        final TextureRegionDrawable leifUnmountedWalkWest2 = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32, 32, 32));
        final TextureRegionDrawable leifUnmountedWalkWest3 = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 64, 32, 32));
        final TextureRegionDrawable leifUnmountedWalkWest4 = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 96, 32, 32));

        final TextureRegionDrawable leifUnmountedWalkSouth1 = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 4, 32, 32));
        final TextureRegionDrawable leifUnmountedWalkSouth2 = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 5, 32, 32));
        final TextureRegionDrawable leifUnmountedWalkSouth3 = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 6, 32, 32));
        final TextureRegionDrawable leifUnmountedWalkSouth4 = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 7, 32, 32));

        final TextureRegionDrawable leifUnmountedWalkNorth1 = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 8 , 32, 32));
        final TextureRegionDrawable leifUnmountedWalkNorth2 = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 9 , 32, 32));
        final TextureRegionDrawable leifUnmountedWalkNorth3 = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 10, 32, 32));
        final TextureRegionDrawable leifUnmountedWalkNorth4 = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 11, 32, 32));

        final TextureRegionDrawable leifUnmountedFlourish1  = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 12, 32, 32));
        final TextureRegionDrawable leifUnmountedFlourish2  = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 13, 32, 32));
        final TextureRegionDrawable leifUnmountedFlourish3  = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 14, 32, 32));

        final TextureRegionDrawable leifUnmountedWalkEast1  = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 15, 32, 32));
        final TextureRegionDrawable leifUnmountedWalkEast2  = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 16, 32, 32));
        final TextureRegionDrawable leifUnmountedWalkEast3  = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 17, 32, 32));
        final TextureRegionDrawable leifUnmountedWalkEast4  = new TextureRegionDrawable(new TextureRegion(leifUnmountedWalkSheet, 0, 32 * 18, 32, 32));

        final Array<TextureRegionDrawable> leifUnmountedIdleFrames = new Array<>();
        leifUnmountedIdleFrames.add(leifUnmountedIdle1,leifUnmountedIdle2,leifUnmountedIdle3);

        final Array<TextureRegionDrawable> leifUnmountedWalkWestFrames = new Array<>();
        leifUnmountedWalkWestFrames.add(leifUnmountedWalkWest1,leifUnmountedWalkWest2,leifUnmountedWalkWest3,leifUnmountedWalkWest4);

        final Array<TextureRegionDrawable> leifUnmountedWalkEastFrames = new Array<>();
        leifUnmountedWalkEastFrames.add(leifUnmountedWalkEast1,leifUnmountedWalkEast2,leifUnmountedWalkEast3,leifUnmountedWalkEast4);

        final Array<TextureRegionDrawable> leifUnmountedWalkSouthFrames = new Array<>();
        leifUnmountedWalkSouthFrames.add(leifUnmountedWalkSouth1,leifUnmountedWalkSouth2,leifUnmountedWalkSouth3,leifUnmountedWalkSouth4);

        final Array<TextureRegionDrawable> leifUnmountedWalkNorthFrames = new Array<>();
        leifUnmountedWalkNorthFrames.add(leifUnmountedWalkNorth1,leifUnmountedWalkNorth2,leifUnmountedWalkNorth3,leifUnmountedWalkNorth4);

        final Array<TextureRegionDrawable> leifUnmountedFlourishFrames = new Array<>();
        leifUnmountedFlourishFrames.add(leifUnmountedFlourish1,leifUnmountedFlourish2,leifUnmountedFlourish3);

        leif_Unmounted_Idle = new Animation<>(0.2f, leifUnmountedIdleFrames);
        leif_Unmounted_Idle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        leif_Unmounted_WalkingWest = new Animation<>(0.2f, leifUnmountedWalkWestFrames);
        leif_Unmounted_WalkingWest.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        leif_Unmounted_WalkingEast = new Animation<>(0.2f, leifUnmountedWalkEastFrames);
        leif_Unmounted_WalkingEast.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        leif_Unmounted_WalkingSouth = new Animation<>(0.2f, leifUnmountedWalkSouthFrames);
        leif_Unmounted_WalkingSouth.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        leif_Unmounted_WalkingNorth = new Animation<>(0.2f, leifUnmountedWalkNorthFrames);
        leif_Unmounted_WalkingNorth.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        leif_Unmounted_Flourish = new Animation<>(0.2f, leifUnmountedFlourishFrames);
        leif_Unmounted_Flourish.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);



        final Texture leifMountedIdleSheet = manager.get("free/fefge/ayr_Flier_Harrier-stand.png", Texture.class);
        final Texture leifMountedWalkSheet = manager.get("free/fefge/ayr_Flier_Harrier-walk.png", Texture.class);

        final TextureRegionDrawable leifMountedIdle1 = new TextureRegionDrawable(new TextureRegion(leifMountedIdleSheet, 0, 0,  16, 20));
        final TextureRegionDrawable leifMountedIdle2 = new TextureRegionDrawable(new TextureRegion(leifMountedIdleSheet, 0, 32, 16, 20));
        final TextureRegionDrawable leifMountedIdle3 = new TextureRegionDrawable(new TextureRegion(leifMountedIdleSheet, 0, 64, 16, 20));

        final TextureRegionDrawable leifMountedWalkWest1  = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 0,  32, 32));
        final TextureRegionDrawable leifMountedWalkWest2  = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32, 32, 32));
        final TextureRegionDrawable leifMountedWalkWest3  = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 64, 32, 32));
        final TextureRegionDrawable leifMountedWalkWest4  = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 96, 32, 32));

        final TextureRegionDrawable leifMountedWalkSouth1 = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 4,  32, 32));
        final TextureRegionDrawable leifMountedWalkSouth2 = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 5,  32, 32));
        final TextureRegionDrawable leifMountedWalkSouth3 = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 6,  32, 32));
        final TextureRegionDrawable leifMountedWalkSouth4 = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 7,  32, 32));

        final TextureRegionDrawable leifMountedWalkNorth1 = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 8,  32, 32));
        final TextureRegionDrawable leifMountedWalkNorth2 = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 9,  32, 32));
        final TextureRegionDrawable leifMountedWalkNorth3 = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 10, 32, 32));
        final TextureRegionDrawable leifMountedWalkNorth4 = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 11, 32, 32));

        final TextureRegionDrawable leifMountedFlourish1  = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 12, 32, 32));
        final TextureRegionDrawable leifMountedFlourish2  = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 13, 32, 32));
        final TextureRegionDrawable leifMountedFlourish3  = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 14, 32, 32));

        final TextureRegionDrawable leifMountedWalkEast1  = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 15, 32, 32));
        final TextureRegionDrawable leifMountedWalkEast2  = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 16, 32, 32));
        final TextureRegionDrawable leifMountedWalkEast3  = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 17, 32, 32));
        final TextureRegionDrawable leifMountedWalkEast4  = new TextureRegionDrawable(new TextureRegion(leifMountedWalkSheet, 0, 32 * 18, 32, 32));

        final Array<TextureRegionDrawable> leifMountedIdleFrames = new Array<>();
        leifMountedIdleFrames.add(leifMountedIdle1,leifMountedIdle2,leifMountedIdle3);

        final Array<TextureRegionDrawable> leifMountedWalkWestFrames = new Array<>();
        leifMountedWalkWestFrames.add(leifMountedWalkWest1,leifMountedWalkWest2,leifMountedWalkWest3,leifMountedWalkWest4);

        final Array<TextureRegionDrawable> leifMountedWalkEastFrames = new Array<>();
        leifMountedWalkEastFrames.add(leifMountedWalkEast1,leifMountedWalkEast2,leifMountedWalkEast3,leifMountedWalkEast4);

        final Array<TextureRegionDrawable> leifMountedWalkSouthFrames = new Array<>();
        leifMountedWalkSouthFrames.add(leifMountedWalkSouth1,leifMountedWalkSouth2,leifMountedWalkSouth3,leifMountedWalkSouth4);

        final Array<TextureRegionDrawable> leifMountedWalkNorthFrames = new Array<>();
        leifMountedWalkNorthFrames.add(leifMountedWalkNorth1,leifMountedWalkNorth2,leifMountedWalkNorth3,leifMountedWalkNorth4);

        final Array<TextureRegionDrawable> leifMountedFlourishFrames = new Array<>();
        leifMountedFlourishFrames.add(leifMountedFlourish1,leifMountedFlourish2,leifMountedFlourish3);

        leif_Mounted_Idle = new Animation<>(0.2f, leifMountedIdleFrames);
        leif_Mounted_Idle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        leif_Mounted_WalkingWest = new Animation<>(0.2f, leifMountedWalkWestFrames);
        leif_Mounted_WalkingWest.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        leif_Mounted_WalkingEast = new Animation<>(0.2f, leifMountedWalkEastFrames);
        leif_Mounted_WalkingEast.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        leif_Mounted_WalkingSouth = new Animation<>(0.2f, leifMountedWalkSouthFrames);
        leif_Mounted_WalkingSouth.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        leif_Mounted_WalkingNorth = new Animation<>(0.2f, leifMountedWalkNorthFrames);
        leif_Mounted_WalkingNorth.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        leif_Mounted_Flourish = new Animation<>(0.2f, leifMountedFlourishFrames);
        leif_Mounted_Flourish.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    private void initializeSoldier() {
        final Texture soldierStandSheet = manager.get("free/fefge/alusq_Deserter_Lance-stand.png");
        final Texture soldierWalkSheet  = manager.get("free/fefge/alusq_Deserter_Lance-walk.png");

        final TextureRegionDrawable soldierIdle1 = new TextureRegionDrawable(new TextureRegion(soldierStandSheet,0, 0,  16,16));
        final TextureRegionDrawable soldierIdle2 = new TextureRegionDrawable(new TextureRegion(soldierStandSheet,0, 16, 16,16));
        final TextureRegionDrawable soldierIdle3 = new TextureRegionDrawable(new TextureRegion(soldierStandSheet,0, 32, 16,16));

        final TextureRegionDrawable soldierWalkWest1 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0,0, 21,21));
        final TextureRegionDrawable soldierWalkWest2 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0,32,21,21));
        final TextureRegionDrawable soldierWalkWest3 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0,64,21,21));
        final TextureRegionDrawable soldierWalkWest4 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0,96,21,21));

        final TextureRegionDrawable soldierWalkSouth1 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 4, 21, 21));
        final TextureRegionDrawable soldierWalkSouth2 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 5, 21, 21));
        final TextureRegionDrawable soldierWalkSouth3 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 6, 21, 21));
        final TextureRegionDrawable soldierWalkSouth4 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 7, 21, 21));

        final TextureRegionDrawable soldierWalkNorth1 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 8,  21, 21));
        final TextureRegionDrawable soldierWalkNorth2 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 9,  21, 21));
        final TextureRegionDrawable soldierWalkNorth3 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 10, 21, 21));
        final TextureRegionDrawable soldierWalkNorth4 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet,0, 32 * 11, 21, 21));

        final TextureRegionDrawable soldierFlourish1 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet, 0, 32 * 12, 18,18));
        final TextureRegionDrawable soldierFlourish2 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet, 0, 32 * 13, 18,18));
        final TextureRegionDrawable soldierFlourish3 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet, 0, 32 * 14, 18,18));

        final TextureRegionDrawable soldierWalkEast1 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet, 0, 32 * 15, 21,21));
        final TextureRegionDrawable soldierWalkEast2 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet, 0, 32 * 16, 21,21));
        final TextureRegionDrawable soldierWalkEast3 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet, 0, 32 * 17, 21,21));
        final TextureRegionDrawable soldierWalkEast4 = new TextureRegionDrawable(new TextureRegion(soldierWalkSheet, 0, 32 * 18, 21,21));

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

    public Animation<TextureRegionDrawable> getAnimation(UnitRoster roster, OLD_SimpleUnit.AnimationState state) {
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

                    case FLOURISH:
                    case WALKING_EAST:
                    case WALKING_WEST:
                    case WALKING_NORTH:
                    case WALKING_SOUTH:
                        break;
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
                        if(generic_Soldier_WalkingEast != null) return generic_Soldier_WalkingEast;
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
