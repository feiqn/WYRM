package com.feiqn.wyrm.logic.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.*;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.OLD_CutscenePlayer;
import com.feiqn.wyrm.logic.handlers.gameplay.OLD_ConditionsHandler;
import com.feiqn.wyrm.logic.handlers.ai.OLD_RecursionHandler;
import com.feiqn.wyrm.logic.handlers.ai.OLD_AIHandler;
import com.feiqn.wyrm.logic.handlers.ai.actions.OLD_AIAction;
import com.feiqn.wyrm.logic.handlers.ui.OLD_HUDElement;
import com.feiqn.wyrm.logic.handlers.ui.OLD_WyrHUD;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.fullscreenmenus.UnitInfoMenu;
import com.feiqn.wyrm.models.mapdata.CameraMan;
import com.feiqn.wyrm.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.OLD_ObjectType;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.OLD_BallistaObject;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.OLD_BreakableWallObject;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.OLD_DoorObject;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.OLD_TreasureChestObject;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;
import com.feiqn.wyrm.models.mapdata.OLD_WyrMap;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.badlogic.gdx.Gdx.input;

public class OLD_GridScreen extends ScreenAdapter {

    public enum OLD_InputMode {
        STANDARD,
        UNIT_SELECTED,
        MENU_FOCUSED,
        LOCKED,
        CUTSCENE,
    }

    public enum MovementControl {
        FREE_MOVE,
        COMBAT
    }

    // --VARIABLES--
    // --GAME--
    protected final WYRMGame game;

    // --MAP--
    protected OLD_WyrMap logicalMap;

    // --CAMERA--
    protected CameraMan cameraMan;

    // --TILED--
    public TiledMap tiledMap;
    public OrthogonalTiledMapRenderer orthoMapRenderer;

    // --STAGE--
    public Stage gameStage,
                 hudStage;

    // --LABELS--
    // --GROUPS--
    public Group rootGroup; // TODO: most likely bypass this in WyRefactor

    public OLD_HUDElement focusedOLDHUDElement;

    // --BOOLEANS--
    protected boolean keyPressed_W;
    protected boolean keyPressed_A;
    protected boolean keyPressed_D;
    protected boolean keyPressed_S;
    protected boolean executingAction;
//    protected boolean conversationQueued;
    protected boolean cutscenePlaying;

    // --INTS--
    // --FLOATS--
    protected float clock = 0;
    private final float halfMax = Float.MAX_VALUE * .5f;

    // --VECTORS--
    // --SPRITES--

    // --ARRAYS--
    public Array<OLD_LogicalTile> tileHighlighters;
    public Array<OLD_LogicalTile> reachableTiles;

    public Array<OLD_SimpleUnit> attackableUnits;

    protected Array<OLD_CutscenePlayer> queuedCutscenes;
    protected Array<OLD_AIAction> queuedActions;

    public Array<OLD_BallistaObject> ballistaObjects;
    public Array<OLD_DoorObject> doorObjects;
    public Array<OLD_BreakableWallObject> breakableWallObjects;
    public Array<OLD_TreasureChestObject> treasureChestObjects;

    // --HASHMAPS--
    public HashMap<OLD_ObjectType, Array> mapObjects;

    // --ENUMS--
    protected OLD_InputMode OLDInputMode;
    protected MovementControl movementControl;

    // --HANDLERS--
    protected OLD_ConditionsHandler conditionsHandler;

    protected OLD_RecursionHandler OLDRecursionHandler;
    protected OLD_AIHandler aiHandler;

    protected OLD_WyrHUD HUD;

    // --IMAGES--
    protected Image curtain; // TODO: put these somewhere else in WyRefactor
    protected Image curtain2;

    // --OTHER--
    public OLD_SimpleUnit activeUnit; // TODO: more scope safety throughout this whole class in WyRefactor
    public OLD_SimpleUnit hoveredUnit;
    protected OLD_SimpleUnit whoseTurn;

    protected Container<OLD_CutscenePlayer> conversationContainer;

    private InputAdapter keyboardListener;

    // -------------------------------
    // --END OF VARIABLE DECLARATION--
    // -------------------------------


    public OLD_GridScreen(WYRMGame game) {
        this.game = game;
    }

    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/wyrmDebugMap.tmx");
        logicalMap = new OLD_WyrMap(game, 10) {
            @Override
            protected void setUpUnits() {}

            @Override
            protected void setUpTiles() {
//                final Array<LogicalTile> roadTiles = new Array<>();
//
//                roadTiles.add(internalLogicalMap[1][4]);
//                roadTiles.add(internalLogicalMap[1][5]);
//                roadTiles.add(internalLogicalMap[1][6]);
//                roadTiles.add(internalLogicalMap[1][7]);
//                roadTiles.add(internalLogicalMap[1][8]);
//
//                roadTiles.add(internalLogicalMap[2][4]);
//                roadTiles.add(internalLogicalMap[2][5]);
//                roadTiles.add(internalLogicalMap[2][6]);
//                roadTiles.add(internalLogicalMap[2][7]);
//                roadTiles.add(internalLogicalMap[2][8]);
//
//                roadTiles.add(internalLogicalMap[3][7]);
//                roadTiles.add(internalLogicalMap[3][8]);
//
//                roadTiles.add(internalLogicalMap[4][7]);
//                roadTiles.add(internalLogicalMap[4][8]);
//
//                roadTiles.add(internalLogicalMap[5][7]);
//                roadTiles.add(internalLogicalMap[5][8]);
//
//                setLogicalTilesToTypeYX(roadTiles, LogicalTileType.ROAD);
//
//                final Array<LogicalTile> roughHillTiles = new Array<>();
//
//                roughHillTiles.add(internalLogicalMap[5][0]);
//                roughHillTiles.add(internalLogicalMap[5][1]);
//                roughHillTiles.add(internalLogicalMap[5][2]);
//                roughHillTiles.add(internalLogicalMap[5][3]);
//                roughHillTiles.add(internalLogicalMap[5][4]);
//                roughHillTiles.add(internalLogicalMap[5][5]);
//
//                roughHillTiles.add(internalLogicalMap[6][0]);
//                roughHillTiles.add(internalLogicalMap[6][1]);
//                roughHillTiles.add(internalLogicalMap[6][2]);
//                roughHillTiles.add(internalLogicalMap[6][3]);
//                roughHillTiles.add(internalLogicalMap[6][4]);
//                roughHillTiles.add(internalLogicalMap[6][5]);
//
//                setLogicalTilesToTypeYX(roughHillTiles, LogicalTileType.ROUGH_HILLS);
//
//                setLogicalTileToTypeXY(1, 8, LogicalTileType.MOUNTAIN);
//
//                setLogicalTileToTypeXY(3, 8, LogicalTileType.FOREST);
//
//                setLogicalTileToTypeXY(5, 8, LogicalTileType.FORTRESS);
//
//                final Array<LogicalTile> impassibleTiles = new Array<>();
//
//                impassibleTiles.add(internalLogicalMap[8][8]);
//                impassibleTiles.add(internalLogicalMap[7][8]);
//
//                setLogicalTilesToTypeYX(impassibleTiles, LogicalTileType.IMPASSIBLE_WALL);

            }
        };
    }

    protected void initializeVariables() {
        keyPressed_A     = false;
        keyPressed_D     = false;
        keyPressed_S     = false;
        keyPressed_W     = false;
        executingAction  = false;
//        someoneIsTalking = false;
        cutscenePlaying  = false;

        rootGroup        = new Group();

        tileHighlighters = new Array<>();

        ballistaObjects  = new Array<>();
        reachableTiles   = new Array<>();

        queuedCutscenes = new Array<>();
        queuedActions   = new Array<>();

        aiHandler         = new OLD_AIHandler(game);
        conditionsHandler = new OLD_ConditionsHandler(game);
        OLDRecursionHandler = new OLD_RecursionHandler(game);

        conversationContainer = new Container<>();

        declareCutscenes();

        mapObjects = new HashMap<>();
        mapObjects.put(OLD_ObjectType.BALLISTA, ballistaObjects);
        mapObjects.put(OLD_ObjectType.DOOR, doorObjects);
        mapObjects.put(OLD_ObjectType.TREASURE_CHEST, treasureChestObjects);
        mapObjects.put(OLD_ObjectType.BREAKABLE_WALL, breakableWallObjects);

        loadMap();

        cameraMan = new CameraMan();
        orthoMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/16f); // TODO: prettier

// chatgpt advice:-----------------------------
        final MapProperties mapProperties = tiledMap.getProperties();
        final int mapWidth = mapProperties.get("width", Integer.class);
        final int mapHeight = mapProperties.get("height", Integer.class);
        final int tileWidth = mapProperties.get("tilewidth", Integer.class);
        final int tileHeight = mapProperties.get("tileheight", Integer.class);

        // Calculate the world dimensions based on the map's size
        final float worldWidth = mapWidth * tileWidth / 16f;  // Divide by tile scale
        final float worldHeight = mapHeight * tileHeight / 16f;

        cameraMan.camera().setToOrtho(false, worldWidth, worldHeight);

        gameStage = new Stage(new ExtendViewport(worldWidth, worldHeight, cameraMan.camera()));


        cameraMan.camera().zoom = Math.max(0.3f, Math.min(cameraMan.camera().zoom, Math.max(worldWidth / cameraMan.camera().viewportWidth, worldHeight / cameraMan.camera().viewportHeight)));
        cameraMan.camera().update();
//------------------------------------------

        rootGroup.setSize(mapWidth, mapHeight);
        gameStage.addActor(rootGroup); // I think we can probably delete this

        gameStage.addActor(cameraMan);

        cameraMan.setPosition(worldWidth / 2, worldHeight / 2);

        setInputMode(OLD_InputMode.STANDARD);
        setMovementControl(MovementControl.COMBAT);

        keyboardListener = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch(keycode) {
                    case Input.Keys.R:
                        // TODO: Open current hoveredUnit info popUp
                        if(OLDInputMode == OLD_InputMode.STANDARD) {
                            if(hoveredUnit !=null) {
                                final UnitInfoMenu infoPopup = new UnitInfoMenu(game, hoveredUnit);
                                hud().addFullscreen(infoPopup);

                                activeUnit = null;
                            }
                        }
                        break;
                    case Input.Keys.E:
                        // TODO: Add current hoveredUnit to displayed danger area
                        break;
                    case Input.Keys.TAB:
                        // TODO: Cycle through units who can still move
                        break;
                    case Input.Keys.M:
                        // TODO: Open minimap
                        break;
                    case Input.Keys.U:
                        // TODO: Open all units info panel
                        break;
                    case Input.Keys.I:
                        // TODO: Open current hoveredUnit inventory popUp
                        break;
                    case Input.Keys.A:
                        // TODO: var to Scroll camera left while true
                        break;
                    case Input.Keys.W:
                        // TODO: scroll up
                        break;
                    case Input.Keys.S:
                        // TODO: scroll down
                        break;
                    case Input.Keys.D:
                        // TODO: scroll right
                        break;
                    case Input.Keys.X:
                        HUD.toggleUnitInfo();
                        break;
                    case Input.Keys.P: // TODO: dev, remove
                        if(cutscenePlaying) {
                            assert conversationContainer.getChild(0) instanceof OLD_CutscenePlayer;
                            ((OLD_CutscenePlayer) conversationContainer.getChild(0)).DEVELOPER_SkipToEnd();
                        }
                }

                return true;
            }
        };

        initialiseHUD();
        initialiseMultiplexer();

        curtain  = new Image(WYRMGame.assets().menuTexture);
        curtain2 = new Image(WYRMGame.assets().menuTexture);
    }

    public void initialiseMultiplexer() {
        final InputMultiplexer multiplexer = new InputMultiplexer();

        // Scroll listener for zoom
        InputAdapter scrollListener = new InputAdapter() { // Thanks, ChatGPT.
            @Override
            public boolean scrolled(float amountX, float amountY) {
//                Gdx.app.log("input mode", "" + inputMode);
                if(OLDInputMode == OLD_InputMode.STANDARD ||
                   OLDInputMode == OLD_InputMode.UNIT_SELECTED ||
                   OLDInputMode == OLD_InputMode.MENU_FOCUSED) {

                    float zoomChange = 0.1f * amountY; // Adjust zoom increment
                    cameraMan.camera().zoom = Math.max(0.2f, Math.min(cameraMan.camera().zoom + zoomChange, 3.0f));
                    cameraMan.camera().update();
                    return true;

                } else {
                    return false;
                }

            }
        };

        multiplexer.addProcessor(scrollListener);
        multiplexer.addProcessor(hudStage);
        multiplexer.addProcessor(gameStage);
        multiplexer.addProcessor(keyboardListener);
        input.setInputProcessor(multiplexer);
    }

    protected void declareCutscenes() {}

    // --------
    // -- UI --
    // --------

    private void initialiseHUD() {
        hudStage = new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        HUD = new OLD_WyrHUD(game);
        hudStage.addActor(HUD);
    }

    // ------------
    // -- END UI --
    // ------------

    protected void setUpVictFailCons() {
        // for child override
    }

    public void checkForStageCleared() {
        if(conditionsHandler.victoryConditionsAreSatisfied()) {
            Gdx.app.log("gridscreen", "Stage cleared!");
            stageClear();
        }
    }

    protected void stageClear() {
        /* This is called upon victory.
         * Child classes should overwrite with directions
         * to next screen. I.e., map, menu, dialogue, etc.
         */
    }

    public void highlightAllTilesUnitCanAccess(@NotNull final OLD_SimpleUnit unit) {
        reachableTiles = new Array<>();
        attackableUnits = new Array<>();

        OLDRecursionHandler.recursivelySelectReachableTiles(unit);
        reachableTiles.add(unit.getOccupyingTile());

        tileHighlighters = new Array<>();

        for(final OLD_LogicalTile tile : reachableTiles) {
                tileHighlighters.add(tile);
                tile.highlightCanMove(unit);
        }
    }

    public void clearAttackableEnemies() {
        for(OLD_SimpleUnit unit : attackableUnits) {
//            unit.standardColor();
            attackableUnits.removeValue(unit, true);
            unit.removeAttackListener();
        }
        attackableUnits = new Array<>();
    }

    public void removeTileHighlighters() {
        for (OLD_LogicalTile tile : tileHighlighters) {
            tile.clearHighlight();
        }
        tileHighlighters = new Array<>();
    }

    public void centerCameraOnLocation(int column, int row) {
        cameraMan.addAction(Actions.moveTo(column, row, .75f));
    }

    protected void queueAction(OLD_AIAction action) {
        // see queConversation()
        queuedActions.add(action);
    }

    private OLD_AIAction nextQueuedAction() {
        if(queuedActions.size == 0) return  null;

        final OLD_AIAction returnValue = queuedActions.get(0);

        queuedCutscenes.removeIndex(0);

        return returnValue;
    }

    public void executeAction(OLD_AIAction action) {
        if(executingAction) {
            queueAction(action);
            Gdx.app.log("executeAction", "action queued");
            return;
        }

        setInputMode(OLD_InputMode.LOCKED);
        executingAction = true;

        // Landing pad for commands from AIHandler
        // This does not validate or consider commands at all, only executes them. Be careful.

        Gdx.app.log("EXECUTING:", "" + action.getActionType());

        final OLD_SimpleUnit star = action.getSubjectUnit();

        final RunnableAction flagDone = new RunnableAction();
        flagDone.setRunnable(new Runnable() {
            @Override
            public void run() {
                game.activeOLDGridScreen.finishExecutingAction();
            }
        });

        centerCameraOnLocation(star.getColumnX(), star.getRowY());

        switch (action.getActionType()) {

            case MOVE_ACTION:
                com.badlogic.gdx.utils.Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        cameraMan.follow(star);
                        logicalMap.moveAlongPath(action.getSubjectUnit(), action.getAssociatedPath(), flagDone, false);
                    }
                }, .5f);
                break;

            case ATTACK_ACTION:
                if(logicalMap.distanceBetweenTiles(action.getSubjectUnit().getOccupyingTile(), action.getObjectUnit().getOccupyingTile()) > action.getSubjectUnit().getSimpleReach()) {
                    // Out of reach, need to move first.

                    final RunnableAction combat = new RunnableAction();
                    combat.setRunnable(new Runnable() {
                        @Override
                        public void run() {
                            conditionsHandler.combat().visualizeCombat(action.getSubjectUnit(), action.getObjectUnit());
                        }
                    });

                    com.badlogic.gdx.utils.Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            cameraMan.follow(star);
                            logicalMap.moveAlongPath(action.getSubjectUnit(), action.getAssociatedPath(), combat, true);
                        }
                    }, 1);

                } else {
                    conditionsHandler.combat().visualizeCombat(action.getSubjectUnit(), action.getObjectUnit());
                }
                // TODO: Relying on combat sequence to flagDone
                break;

            case ESCAPE_ACTION:
                RunnableAction escape = new RunnableAction();
                escape.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if(action.getFlagID() != null) {
                            game.activeOLDGridScreen.conditionsHandler.satisfyVictCon(action.getFlagID());
                            conditions().conversations().checkCampaignFlagTriggers(action.getFlagID());
                        }

                        game.activeOLDGridScreen.conditions().teams().escapeUnit(action.getSubjectUnit());
                        game.activeOLDGridScreen.conditions().removeFromTurnOrder(action.getSubjectUnit());
                        action.getSubjectUnit().remove();
                        checkLineOrder();

                    }
                });

                final OLD_LogicalTile target = logicalMap.getTileAtPositionXY((int)action.getCoordinate().x, (int)action.getCoordinate().y);

                if(action.getAssociatedPath().lastTile() == target) {
                    // Can escape this turn
//                    Gdx.app.log("escape action", "can escape this turn");
                    logicalMap.moveAlongPath(action.getSubjectUnit(), action.getAssociatedPath(), escape, false);
                } else {
                    // Just follow the path
                    logicalMap.moveAlongPath(action.getSubjectUnit(), action.getAssociatedPath());
                }
                break;

            case PASS_ACTION:
//                conditionsHandler.updatePhase();
                Gdx.app.log("pass action?", "we don't do that shit anymore -- need to update someone on the new memo: everyone gets clocked at conception.");
            case WAIT_ACTION:
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        action.getSubjectUnit().setCannotMove();
                        finishExecutingAction();
                    }
                }, .75f);
            default:
                break;
        }

    }

    public void finishExecutingAction() {
        setInputMode(OLD_InputMode.LOCKED); // May not want this locked, will see.

        cameraMan.stopFollowing();
        executingAction = false;
//        Gdx.app.log("finishExecuting", "done executing");

        if(queuedActions.size > 0) {
//            Gdx.app.log("finishExecuting", "action queued");
            executeAction(nextQueuedAction());
        } else {
            setInputMode(OLD_InputMode.STANDARD);
//            Gdx.app.log("finishExecuting", "moving on");
            checkLineOrder();
        }
    }

    protected void runAI() {
        if(!isBusy() ) {
            aiHandler.run();
        }
    }

    public void setInputMode(OLD_InputMode mode) {
        Gdx.app.log("setInputMode", "" + mode);
        OLDInputMode = mode;
    }

    public void setMovementControl(MovementControl move) {
        movementControl = move;
    }

    protected void queueConversation(OLD_CutscenePlayer OLDCutscenePlayer) {
        // Leaving this scope public for the possibility of
        // queueing a cutscene to only occur specifically after
        // some other cutscene plays. Niche, but it's a feature.

        // ^ Cool feature! I added it as a native function of
        // CutsceneTrigger, so that functionality can now be
        // achieved in a much neater and more consistent way.
        // Therefore, making this protected!

        queuedCutscenes.add(OLDCutscenePlayer);
//        conversationQueued = true;
    }

    private OLD_CutscenePlayer nextQueuedConversation() {
//        if(!conversationQueued) return null;
        if(queuedCutscenes.size == 0) return null;

        final OLD_CutscenePlayer returnValue = queuedCutscenes.get(0);

        queuedCutscenes.removeIndex(0);

//        if(queuedConversations.size == 0) conversationQueued = false;

        return returnValue;
    }

    public void startCutscene(OLD_CutscenePlayer OLDCutscenePlayer) {
        if(cutscenePlaying) {
             queueConversation(OLDCutscenePlayer);
             return;
        }
        cutscenePlaying = true;

        this.OLDInputMode = OLD_InputMode.LOCKED;

        OLDCutscenePlayer.setColor(1,1,1,0);
        conversationContainer = new Container<>(OLDCutscenePlayer)
            .fill();
        conversationContainer.setFillParent(true);

        curtain  = new Image(WYRMGame.assets().menuTexture);
        curtain2 = new Image(WYRMGame.assets().menuTexture);

        curtain.setColor(Color.BLACK);
        curtain2.setColor(Color.BLACK);

        curtain.setSize(hudStage.getCamera().viewportWidth, hudStage.getCamera().viewportHeight * .12f);
        curtain.setPosition(0, hudStage.getCamera().viewportHeight);

        curtain2.setSize(hudStage.getCamera().viewportWidth, hudStage.getCamera().viewportHeight * .12f);
        curtain2.setPosition(0, 0 - curtain2.getHeight());

        hudStage.addActor(curtain);
        hudStage.addActor(curtain2);
        hudStage.addActor(conversationContainer);

        HUD.addAction(Actions.fadeOut(.5f));

        curtain.addAction(Actions.moveBy(0, 0-curtain.getHeight(), 1));
        curtain2.addAction(Actions.moveBy(0, curtain2.getHeight(), 1));

        OLDCutscenePlayer.addAction(Actions.sequence(
                Actions.fadeIn(.15f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        setInputMode(OLD_InputMode.CUTSCENE);
                    }
                }))
        );
    }

    public void endCutscene() {
        setInputMode(OLD_InputMode.LOCKED);

        conversationContainer.addAction(Actions.sequence(
            Actions.fadeOut(.25f),
            Actions.removeActor()
        ));

        curtain.addAction(Actions.sequence(
            Actions.fadeOut(.5f),
            Actions.removeActor()
        ));
        curtain2.addAction(Actions.sequence(
            Actions.fadeOut(.5f),
            Actions.removeActor()
        ));

        HUD.addAction(Actions.sequence(
            Actions.fadeIn(.75f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    cutscenePlaying = false;

                    try {
                        conditions().conversations().checkOtherCutsceneTriggers(conversationContainer.getActor().script().getCutsceneID());
                    }catch (Exception e) {
                        Gdx.app.log("gridScreen", "failed to check cutscene triggers");
                    }

                    if(queuedCutscenes.size > 0) {
                        startCutscene(nextQueuedConversation());
                    } else {
                        setInputMode(OLD_InputMode.STANDARD);
                        checkLineOrder();
                    }
                }
            })
        ));
    }


    @Override
    public void show() {
        super.show();

        initializeVariables();

        checkLineOrder();

        gameStage.addListener(new DragListener() {
            final Vector3 tp = new Vector3();
            boolean dragged = false;
            boolean clicked = false;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dragged = false;

                switch(OLDInputMode) {
                    case STANDARD:
                    case MENU_FOCUSED:
                    case UNIT_SELECTED:
                        clicked = true;
                        return true;
                    default:
                        clicked = false;
                        return false;
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button)  {
                if(dragged) {
                    dragged = false;
                    clicked = false;
                    return;
                }

                switch(OLDInputMode) {
                    case UNIT_SELECTED:
                        gameStage.getCamera().unproject(tp.set((float) (double) input.getX(), (float) (double) input.getY(), 0));

                        if(!reachableTiles.contains(logicalMap.getTileAtPositionXY((int) tp.x, (int) tp.y), true)) {
                            removeTileHighlighters();
                            activeUnit.idle();
                            activeUnit = null;
                            clearAttackableEnemies();
                            setInputMode(OLD_InputMode.STANDARD);
                            hud().reset();
                        }
                        break;

                    case STANDARD:
                    case MENU_FOCUSED:
                    default:
                        break;
                }


            }

            @Override
            public boolean mouseMoved (InputEvent event, float x, float y) {
                try {
                    if(OLDInputMode == OLD_InputMode.STANDARD ||
                       OLDInputMode == OLD_InputMode.UNIT_SELECTED ||
                       OLDInputMode == OLD_InputMode.MENU_FOCUSED) {

                        game.activeOLDGridScreen.gameStage.getCamera().unproject(tp.set((float) (double) input.getX(), (float) (double) input.getY(), 0));

                        hud().updateTilePanel(logicalMap.getTileAtPositionXY((int) tp.x, (int) tp.y).tileType);

                    }
                } catch (Exception ignored) {}
                return false;
            }

            @Override
            public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                if(OLDInputMode == OLD_InputMode.STANDARD ||
                   OLDInputMode == OLD_InputMode.UNIT_SELECTED ||
                   OLDInputMode == OLD_InputMode.MENU_FOCUSED) {

                    dragged = true;

                    final float x = input.getDeltaX() * .05f; // TODO: variable scroll speed setting can be injected here
                    final float y = input.getDeltaY() * .05f; // TODO: when you zoom in, make the scroll slower


                    cameraMan.translate(-x, y);
//                    cameraMan.camera().translate(-x,y);
//                    cameraMan.camera().update();
                }
            }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                    Gdx.app.log("enter", "entered!");
                }
        });

        cameraMan.camera().update();

        setUpVictFailCons();

        hud().updateTurnOrderPanel();

        fadeInFromBlack();

    }


    public void fadeOutToBlack() {
        OLDInputMode = OLD_InputMode.LOCKED;

        Image fadeOutImage = new Image(WYRMGame.assets().solidBlueTexture);
        fadeOutImage.setColor(0,0,0,0);

        Container<Image> fadeOutContainer = new Container<>(fadeOutImage).fill();
        fadeOutContainer.setFillParent(true);
        gameStage.addActor(fadeOutContainer);
        fadeOutContainer.addAction(Actions.fadeIn(3));
    }

    protected void fadeInFromBlack() {
        HUD.setColor(0,0,0,0);
        conversationContainer.setColor(0,0,0,0);

        Image fadeInImage = new Image(WYRMGame.assets().solidBlueTexture);
        fadeInImage.setColor(0,0,0,1);

        OLDInputMode = OLD_InputMode.CUTSCENE;
        Container<Image> fadeInContainer = new Container<>(fadeInImage).fill();
        fadeInContainer.setFillParent(true);
        gameStage.addActor(fadeInContainer);
        fadeInContainer.addAction(Actions.sequence(
            Actions.fadeOut(3),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    OLDInputMode = OLD_InputMode.STANDARD;
                    conversationContainer.addAction(Actions.fadeIn(1));
//                    HUD.addAction(Actions.fadeIn(1)); // TODO: switch here for these ^ if no cutscene to start
                }
            }),
            Actions.removeActor()
        ));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.15f, 0.5f, 0.25f, 1);

        clock += delta;

        cameraMan.camera().update();

        orthoMapRenderer.setView(cameraMan.camera());
        orthoMapRenderer.render();

        if(shouldRunAI()) {
            runAI();
        }

        gameStage.act();
        gameStage.draw(); /* TODO: write a wrapper function to draw things in order for proper sprite layering
                           *  UPDATE: ^ I don't think this is necessary anymore due to
                           *            how placing things is implemented in WyrMap
                           */
        hudStage.act();
        hudStage.draw();
    }


    protected boolean shouldRunAI() {
        return whoseTurn.getTeamAlignment() != TeamAlignment.PLAYER
            && OLDInputMode != OLD_InputMode.CUTSCENE
            && !conditionsHandler.combat().isVisualizing();
    }


    @Override
    public void resize(int width, int height) {
        gameStage.getViewport().update(width, height, false);
        gameStage.getCamera().update();

        hudStage.getViewport().setWorldSize(width, height);
        hudStage.getViewport().update(width, height, true);
        hudStage.getCamera().update();
    }


    public void checkLineOrder() {
        whoseTurn = conditionsHandler.whoseNextInLine();
        hud().updateTurnOrderPanel();
    }


    /**
     * GETTERS
     */
    public float getClock() {
        if(clock == Float.POSITIVE_INFINITY) {
            clock = 0;
            return Float.MAX_VALUE;
        }
        return clock;
    }
    public CameraMan getCameraMan() { return cameraMan; }
    public OLD_InputMode getInputMode() { return OLDInputMode; }
    public MovementControl getMovementControl() { return movementControl; }
    public Boolean isBusy() {
        return executingAction ||
               logicalMap.isBusy() ||
               cutscenePlaying ||
               conditionsHandler.combat().isVisualizing();
    }
    public OLD_SimpleUnit whoseNext() {
        checkLineOrder();
        return whoseTurn;
    }
    public OLD_WyrMap getLogicalMap() { return  logicalMap; }
    public OLD_WyrHUD hud() { return  HUD; }
    public OLD_RecursionHandler getRecursionHandler() { return OLDRecursionHandler; }
    public OLD_ConditionsHandler conditions() { return conditionsHandler; }
    public Boolean isPlayingCutscene() { return cutscenePlaying; }

}
