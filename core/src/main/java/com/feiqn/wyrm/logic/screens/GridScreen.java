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
import com.badlogic.gdx.utils.viewport.*;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CutscenePlayer;
import com.feiqn.wyrm.logic.handlers.gameplay.ConditionsHandler;
import com.feiqn.wyrm.logic.handlers.ai.RecursionHandler;
import com.feiqn.wyrm.logic.handlers.ai.AIHandler;
import com.feiqn.wyrm.logic.handlers.ai.actions.AIAction;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.logic.handlers.ui.WyrHUD;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.fullscreenmenus.UnitInfoMenu;
import com.feiqn.wyrm.models.mapdata.CameraMan;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.ObjectType;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.BallistaObject;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.BreakableWallObject;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.DoorObject;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.TreasureChestObject;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
import com.feiqn.wyrm.models.mapdata.WyrMap;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.badlogic.gdx.Gdx.input;

public class GridScreen extends ScreenAdapter {

    public enum InputMode {
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
    protected WyrMap logicalMap;

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

    public HUDElement focusedHUDElement;

    // --BOOLEANS--
    protected boolean keyPressed_W;
    protected boolean keyPressed_A;
    protected boolean keyPressed_D;
    protected boolean keyPressed_S;
    protected boolean executingAction;
    protected boolean someoneIsTalking;
    protected boolean conversationQueued;
    protected boolean cutscenePlaying;

    // --INTS--
    // --FLOATS--
    protected float clock = 0;
    private final float halfMax = Float.MAX_VALUE * .5f;

    // --VECTORS--
    // --SPRITES--

    // --ARRAYS--
    public Array<LogicalTile> tileHighlighters;
    public Array<LogicalTile> reachableTiles;

    public Array<SimpleUnit> attackableUnits;

    protected Array<CutscenePlayer> queuedConversations;

    public Array<BallistaObject> ballistaObjects;
    public Array<DoorObject> doorObjects;
    public Array<BreakableWallObject> breakableWallObjects;
    public Array<TreasureChestObject> treasureChestObjects;

    // --HASHMAPS--
    public HashMap<ObjectType, Array> mapObjects;

    // --ENUMS--
    protected InputMode inputMode;
    protected MovementControl movementControl;

    // --HANDLERS--
    protected ConditionsHandler conditionsHandler;

    protected RecursionHandler recursionHandler;
    protected AIHandler aiHandler;

    protected WyrHUD HUD;

    // --IMAGES--
    protected Image curtain; // TODO: put these somewhere else in WyRefactor
    protected Image curtain2;

    // --OTHER--
    public SimpleUnit activeUnit; // TODO: more scope safety throughout this whole class in WyRefactor
    public SimpleUnit hoveredUnit;
    protected SimpleUnit whoseTurn;

    protected Container<CutscenePlayer> conversationContainer;

    private InputAdapter keyboardListener;

    // -------------------------------
    // --END OF VARIABLE DECLARATION--
    // -------------------------------


    public GridScreen(WYRMGame game) {
        this.game = game;
    }

    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/wyrmDebugMap.tmx");
        logicalMap = new WyrMap(game, 10) {
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
        someoneIsTalking = false;
        cutscenePlaying  = false;

        rootGroup        = new Group();

        tileHighlighters = new Array<>();

        ballistaObjects  = new Array<>();
        reachableTiles   = new Array<>();

        queuedConversations = new Array<>();

        aiHandler         = new AIHandler(game);
        conditionsHandler = new ConditionsHandler(game);
        recursionHandler  = new RecursionHandler(game);

        conversationContainer = new Container<>();

        buildConversations();

        mapObjects = new HashMap<>();
        mapObjects.put(ObjectType.BALLISTA, ballistaObjects);
        mapObjects.put(ObjectType.DOOR, doorObjects);
        mapObjects.put(ObjectType.TREASURE_CHEST, treasureChestObjects);
        mapObjects.put(ObjectType.BREAKABLE_WALL, breakableWallObjects);

        loadMap();

        cameraMan = new CameraMan(this);
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

//        cameraMan.camera().update();

        cameraMan.camera().zoom = Math.max(0.3f, Math.min(cameraMan.camera().zoom, Math.max(worldWidth / cameraMan.camera().viewportWidth, worldHeight / cameraMan.camera().viewportHeight)));
//        cameraMan.camera().position.x = Math.round(cameraMan.camera().position.x);
//        cameraMan.camera().position.y = Math.round(cameraMan.camera().position.y);
        cameraMan.camera().update();
//------------------------------------------

        rootGroup.setSize(mapWidth, mapHeight);
        gameStage.addActor(rootGroup); // I think we can probably delete this

        gameStage.addActor(cameraMan);

        cameraMan.setPosition(worldWidth / 2, worldHeight / 2);

        setInputMode(InputMode.STANDARD);
        setMovementControl(MovementControl.COMBAT);

        keyboardListener = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch(keycode) {
                    case Input.Keys.R:
                        // TODO: Open current hoveredUnit info popUp
                        if(inputMode == InputMode.STANDARD) {
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
                }

                return true;
            }
        };

        initialiseHUD();
        initialiseMultiplexer();

        curtain  = new Image(game.assetHandler.menuTexture);
        curtain2 = new Image(game.assetHandler.menuTexture);
    }

    public void initialiseMultiplexer() {
        final InputMultiplexer multiplexer = new InputMultiplexer();

        // Scroll listener for zoom
        InputAdapter scrollListener = new InputAdapter() { // Thanks, ChatGPT.
            @Override
            public boolean scrolled(float amountX, float amountY) {
//                Gdx.app.log("input mode", "" + inputMode);
                if(inputMode == InputMode.STANDARD ||
                   inputMode == InputMode.UNIT_SELECTED ||
                   inputMode == InputMode.MENU_FOCUSED) {

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

    protected void buildConversations() {
//        conditionsHandler.loadConversations(new Array<>());
    }

    // --------
    // -- UI --
    // --------

    private void initialiseHUD() {
        hudStage = new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        HUD = new WyrHUD(game);
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

    public void highlightAllTilesUnitCanAccess(@NotNull final SimpleUnit unit) {
        reachableTiles = new Array<>();
        attackableUnits = new Array<>();

        recursionHandler.recursivelySelectReachableTiles(unit);
        reachableTiles.add(unit.getOccupyingTile());

        tileHighlighters = new Array<>();

        for(final LogicalTile tile : reachableTiles) {
                tileHighlighters.add(tile);
                tile.highlightCanMove(unit);
        }
    }

    public void clearAttackableEnemies() {
        for(SimpleUnit unit : attackableUnits) {
//            unit.standardColor();
            attackableUnits.removeValue(unit, true);
            unit.removeAttackListener();
        }
        attackableUnits = new Array<>();
    }

    public void removeTileHighlighters() {
        for (LogicalTile tile : tileHighlighters) {
            tile.clearHighlight();
        }
        tileHighlighters = new Array<>();
    }

    public void centerCameraOnLocation(int column, int row) {
        cameraMan.addAction(Actions.moveTo(column, row, 1));
    }

    public void executeAction(AIAction action) {
        if(!executingAction) {
            // Landing pad for commands from AIHandler
            // This does not validate or consider commands at all, only executes them. Be careful.

            Gdx.app.log("EXECUTING:", "" + action.getActionType());

            executingAction = true;

            switch (action.getActionType()) {

                case MOVE_ACTION:
                    logicalMap.moveAlongPath(action.getSubjectUnit(), action.getAssociatedPath());
                    break;

                case ATTACK_ACTION:
                    if(logicalMap.distanceBetweenTiles(action.getSubjectUnit().getOccupyingTile(), action.getObjectUnit().getOccupyingTile()) > action.getSubjectUnit().getSimpleReach()) {
                        // Out of reach, need to move first.

                        RunnableAction combat = new RunnableAction();
                        combat.setRunnable(new Runnable() {
                            @Override
                            public void run() {
                                conditionsHandler.combat().simpleVisualCombat(action.getSubjectUnit(), action.getObjectUnit());
//                            action.getSubjectUnit().setCannotMove();
                            }
                        });
                        logicalMap.moveAlongPath(action.getSubjectUnit(), action.getAssociatedPath(), combat, true);

                    } else {
                        conditionsHandler.combat().simpleVisualCombat(action.getSubjectUnit(), action.getObjectUnit());
//                        action.getSubjectUnit().setCannotMove();
                    }

                    break;

                case ESCAPE_ACTION:
                    if (action.getAssociatedPath().contains(logicalMap.getTileAtPositionXY((int)action.getCoordinate().x, (int)action.getCoordinate().y))) {
                        // Can escape this turn
                        RunnableAction escape = new RunnableAction();
                        escape.setRunnable(new Runnable() {
                            @Override
                            public void run() {
                                conditionsHandler.teams().escapeUnit(action.getSubjectUnit());
                                if (action.getFlagID() != null) {
                                    game.activeGridScreen.conditionsHandler.satisfyVictCon(action.getFlagID());
                                }
                            }
                        });
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
                    action.getSubjectUnit().setCannotMove();
                    whoseTurn = conditionsHandler.whoseNextInLine();
                default:
                    break;
            }

            executingAction = false;
            aiHandler.stopWaiting();
        } else {
            Gdx.app.log("executeAction", "waiting / tripped");
        }
    }

    protected void runAI() {
        if(!aiHandler.isThinking() && !isBusy() ) {
            aiHandler.run();
        }
    }

    public void setInputMode(InputMode mode) {
        Gdx.app.log("setInputMode", "" + mode);
        inputMode = mode;
    }

    public void setMovementControl(MovementControl move) {
        movementControl = move;
    }

    public void queueConversation(CutscenePlayer cutscenePlayer) {
        // Leaving this scope public for the possibility of
        // queueing a cutscene to only occur specifically after
        // some other cutscene plays. Niche, but it's a feature.

        queuedConversations.add(cutscenePlayer);
        conversationQueued = true;
    }

    private CutscenePlayer nextQueuedConversation() {
        if(!conversationQueued) return null;
        if(queuedConversations.size == 0) return null;

        final CutscenePlayer returnValue = queuedConversations.get(0);

        queuedConversations.removeIndex(0);

        if(queuedConversations.size == 0) conversationQueued = false;

        return returnValue;
    }

    public void startCutscene(CutscenePlayer cutscenePlayer) {
        if(cutscenePlaying) {
             queueConversation(cutscenePlayer);
             return;
        }

        cutscenePlaying = true;

        this.inputMode = InputMode.LOCKED;

        cutscenePlayer.setColor(1,1,1,0);
        conversationContainer = new Container<>(cutscenePlayer)
            .fill();
        conversationContainer.setFillParent(true);

        curtain  = new Image(game.assetHandler.menuTexture);
        curtain2 = new Image(game.assetHandler.menuTexture);

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

        cutscenePlayer.addAction(Actions.sequence(
                Actions.fadeIn(.15f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        setInputMode(InputMode.CUTSCENE);
                    }
                }))
        );
    }

    public void endConversation() {
        setInputMode(InputMode.LOCKED);

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
            Actions.fadeIn(1),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    cutscenePlaying = false;

                    if(conversationQueued) {
                        startCutscene(nextQueuedConversation());
                    } else {
                        setInputMode(InputMode.STANDARD);
                        checkLineOrder();
                    }
                }
            })
        ));
    }

    /**
     * OVERRIDES
     */
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

                switch(inputMode) {
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

                switch(inputMode) {
                    case UNIT_SELECTED:
                        gameStage.getCamera().unproject(tp.set((float) (double) input.getX(), (float) (double) input.getY(), 0));

                        if(!reachableTiles.contains(logicalMap.getTileAtPositionXY((int) tp.x, (int) tp.y), true)) {
                            removeTileHighlighters();
                            activeUnit.idle();
                            activeUnit = null;
                            clearAttackableEnemies();
                            setInputMode(GridScreen.InputMode.STANDARD);
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
                    if(inputMode == InputMode.STANDARD ||
                       inputMode == InputMode.UNIT_SELECTED ||
                       inputMode == InputMode.MENU_FOCUSED) {

                        game.activeGridScreen.gameStage.getCamera().unproject(tp.set((float) (double) input.getX(), (float) (double) input.getY(), 0));

                        hud().updateTilePanel(logicalMap.getTileAtPositionXY((int) tp.x, (int) tp.y).tileType);

                    }
                } catch (Exception ignored) {}
                return false;
            }

            @Override
            public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                if(inputMode == InputMode.STANDARD ||
                   inputMode == InputMode.UNIT_SELECTED ||
                   inputMode == InputMode.MENU_FOCUSED) {

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
        inputMode = InputMode.LOCKED;

        Image fadeOutImage = new Image(game.assetHandler.solidBlueTexture);
        fadeOutImage.setColor(0,0,0,0);

        Container<Image> fadeOutContainer = new Container<>(fadeOutImage).fill();
        fadeOutContainer.setFillParent(true);
        gameStage.addActor(fadeOutContainer);
        fadeOutContainer.addAction(Actions.fadeIn(3));
    }

    protected void fadeInFromBlack() {
        HUD.setColor(0,0,0,0);
        conversationContainer.setColor(0,0,0,0);

        Image fadeInImage = new Image(game.assetHandler.solidBlueTexture);
        fadeInImage.setColor(0,0,0,1);

        inputMode = InputMode.CUTSCENE;
        Container<Image> fadeInContainer = new Container<>(fadeInImage).fill();
        fadeInContainer.setFillParent(true);
        gameStage.addActor(fadeInContainer);
        fadeInContainer.addAction(Actions.sequence(
            Actions.fadeOut(3),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    inputMode = InputMode.STANDARD;
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
            && inputMode != InputMode.CUTSCENE
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
    public InputMode getInputMode() { return inputMode; }
    public MovementControl getMovementControl() { return movementControl; }
    public Boolean isBusy() {
        return executingAction ||
               logicalMap.isBusy() ||
               cutscenePlaying ||
               conditionsHandler.combat().isVisualizing();
    }
    public SimpleUnit whoseNext() { return whoseTurn; }
    public WyrMap getLogicalMap() { return  logicalMap; }
    public WyrHUD hud() { return  HUD; }
    public RecursionHandler getRecursionHandler() { return  recursionHandler; }
    public ConditionsHandler conditions() { return conditionsHandler; }
    public Boolean isPlayingCutscene() { return cutscenePlaying; }

}
