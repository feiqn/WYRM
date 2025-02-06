package com.feiqn.wyrm.logic.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.*;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.combat.ConditionsHandler;
import com.feiqn.wyrm.logic.handlers.ai.RecursionHandler;
import com.feiqn.wyrm.logic.handlers.ai.AIHandler;
import com.feiqn.wyrm.logic.handlers.ai.actions.AIAction;
import com.feiqn.wyrm.logic.handlers.conversation.Conversation;
import com.feiqn.wyrm.logic.handlers.conversation.ConversationHandler;
import com.feiqn.wyrm.logic.handlers.conversation.ConversationTrigger;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.logic.handlers.ui.WyrHUD;
import com.feiqn.wyrm.models.mapdata.StageList;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.ObjectType;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.Ballista;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.BreakableWall;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.Door;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.TreasureChest;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
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
    public OrthographicCamera gameCamera;

    // --TILED--
    public TiledMap tiledMap;
    public OrthogonalTiledMapRenderer orthoMapRenderer;

    // --STAGE--
    public Stage gameStage,
                 hudStage;

    // --LABELS--
    // --GROUPS--
    public Group rootGroup;

    public HUDElement focusedHUDElement;

    // --BOOLEANS--
    protected boolean keyPressed_W;
    protected boolean keyPressed_A;
    protected boolean keyPressed_D;
    protected boolean keyPressed_S;
    protected boolean executingAction;
    protected boolean someoneIsTalking;

    // --INTS--
    // --FLOATS--
    // --VECTORS--
    // --SPRITES--

    // --ARRAYS--
    public Array<LogicalTile> tileHighlighters;
    public Array<LogicalTile> reachableTiles;

    public Array<SimpleUnit> attackableUnits;

    public Array<Ballista> ballistaObjects;
    public Array<Door> doorObjects;
    public Array<BreakableWall> breakableWallObjects;
    public Array<TreasureChest> treasureChestObjects;

    // --HASHMAPS--
    public HashMap<ObjectType, Array> mapObjects;

    // --ENUMS--
    protected StageList stageID;
    protected InputMode inputMode;
    protected MovementControl movementControl;

    // --HANDLERS--

    public ConditionsHandler conditionsHandler;
    public RecursionHandler recursionHandler;
    protected AIHandler aiHandler;
    protected ConversationHandler conversationHandler;

    protected WyrHUD HUD;

    // --OTHER--
    public SimpleUnit activeUnit; // TODO: more scope safety throughout this whole class
    public SimpleUnit hoveredUnit;
    protected SimpleUnit whoseTurn;

    protected Container<Conversation> conversationContainer;

    private InputAdapter keyboardListener;

    // -------------------------------
    // --END OF VARIABLE DECLARATION--
    // -------------------------------

    public GridScreen(WYRMGame game) {
        this(game, StageList.STAGE_DEBUG);
    }

    public GridScreen(WYRMGame game, StageList stageID) {
        this.game = game;
        this.stageID = stageID;
    }

    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/wyrmDebugMap.tmx");
        logicalMap = new WyrMap(game, 10) {
            @Override
            protected void setUpTiles() {
                final Array<LogicalTile> roadTiles = new Array<>();

                roadTiles.add(internalLogicalMap[1][4]);
                roadTiles.add(internalLogicalMap[1][5]);
                roadTiles.add(internalLogicalMap[1][6]);
                roadTiles.add(internalLogicalMap[1][7]);
                roadTiles.add(internalLogicalMap[1][8]);

                roadTiles.add(internalLogicalMap[2][4]);
                roadTiles.add(internalLogicalMap[2][5]);
                roadTiles.add(internalLogicalMap[2][6]);
                roadTiles.add(internalLogicalMap[2][7]);
                roadTiles.add(internalLogicalMap[2][8]);

                roadTiles.add(internalLogicalMap[3][7]);
                roadTiles.add(internalLogicalMap[3][8]);

                roadTiles.add(internalLogicalMap[4][7]);
                roadTiles.add(internalLogicalMap[4][8]);

                roadTiles.add(internalLogicalMap[5][7]);
                roadTiles.add(internalLogicalMap[5][8]);

                setLogicalTilesToType(roadTiles, LogicalTileType.ROAD);

                final Array<LogicalTile> roughHillTiles = new Array<>();

                roughHillTiles.add(internalLogicalMap[5][0]);
                roughHillTiles.add(internalLogicalMap[5][1]);
                roughHillTiles.add(internalLogicalMap[5][2]);
                roughHillTiles.add(internalLogicalMap[5][3]);
                roughHillTiles.add(internalLogicalMap[5][4]);
                roughHillTiles.add(internalLogicalMap[5][5]);

                roughHillTiles.add(internalLogicalMap[6][0]);
                roughHillTiles.add(internalLogicalMap[6][1]);
                roughHillTiles.add(internalLogicalMap[6][2]);
                roughHillTiles.add(internalLogicalMap[6][3]);
                roughHillTiles.add(internalLogicalMap[6][4]);
                roughHillTiles.add(internalLogicalMap[6][5]);

                setLogicalTilesToType(roughHillTiles, LogicalTileType.ROUGH_HILLS);

                setLogicalTileToType(8, 1, LogicalTileType.MOUNTAIN);

                setLogicalTileToType(8, 3, LogicalTileType.FOREST);

                setLogicalTileToType(8, 5, LogicalTileType.FORTRESS);

                final Array<LogicalTile> impassibleTiles = new Array<>();

                impassibleTiles.add(internalLogicalMap[8][8]);
                impassibleTiles.add(internalLogicalMap[7][8]);

                setLogicalTilesToType(impassibleTiles, LogicalTileType.IMPASSIBLE_WALL);

            }
        };

    }

    private void initializeVariables() {
        keyPressed_A     = false;
        keyPressed_D     = false;
        keyPressed_S     = false;
        keyPressed_W     = false;
        executingAction  = false;
        someoneIsTalking = false;

        rootGroup        = new Group();

        tileHighlighters = new Array<>();

        ballistaObjects  = new Array<>();
        reachableTiles   = new Array<>();

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

        gameCamera = new OrthographicCamera();
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

        gameCamera.setToOrtho(false, worldWidth, worldHeight);
        gameStage = new Stage(new ExtendViewport(worldWidth, worldHeight, gameCamera));
        gameCamera.update();

        gameCamera.zoom = Math.max(0.1f, Math.min(gameCamera.zoom, Math.max(worldWidth / gameCamera.viewportWidth, worldHeight / gameCamera.viewportHeight)));
        gameCamera.position.x = Math.round(gameCamera.position.x);
        gameCamera.position.y = Math.round(gameCamera.position.y);
        gameCamera.update();
//------------------------------------------

        rootGroup.setSize(mapWidth, mapHeight);

        gameStage.addActor(rootGroup);

        setInputMode(InputMode.STANDARD);
        setMovementControl(MovementControl.COMBAT);

        keyboardListener = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch(keycode) {
                    case Input.Keys.R:
                        // TODO: Open current hoveredUnit info popUp
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
                }

                return true;
            }
        };

        initialiseHUD();
        initialiseMultiplexer();
    }

    public void initialiseMultiplexer() {
        final InputMultiplexer multiplexer = new InputMultiplexer();

        // Scroll listener for zoom
        InputAdapter scrollListener = new InputAdapter() { // Thanks, ChatGPT.
            @Override
            public boolean scrolled(float amountX, float amountY) {
                float zoomChange = 0.1f * amountY; // Adjust zoom increment
                gameCamera.zoom = Math.max(0.5f, Math.min(gameCamera.zoom + zoomChange, 2.0f));
                // Clamp zoom between 0.5 (zoomed in) and 2.0 (zoomed out)
                gameCamera.update();
                return true;
            }
        };

        multiplexer.addProcessor(scrollListener);
        multiplexer.addProcessor(hudStage);
        multiplexer.addProcessor(gameStage);
        multiplexer.addProcessor(keyboardListener);
        input.setInputProcessor(multiplexer);
    }

    protected void buildConversations() {
        conversationHandler = new ConversationHandler(game, new List<>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public @NotNull Iterator<ConversationTrigger> iterator() {
                return null;
            }

            @Override
            public @NotNull Object[] toArray() {
                return new Object[0];
            }

            @Override
            public @NotNull <T> T[] toArray(@NotNull T[] a) {
                return null;
            }

            @Override
            public boolean add(ConversationTrigger conversationTrigger) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NotNull Collection<? extends ConversationTrigger> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NotNull Collection<? extends ConversationTrigger> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public ConversationTrigger get(int index) {
                return null;
            }

            @Override
            public ConversationTrigger set(int index, ConversationTrigger element) {
                return null;
            }

            @Override
            public void add(int index, ConversationTrigger element) {

            }

            @Override
            public ConversationTrigger remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public @NotNull ListIterator<ConversationTrigger> listIterator() {
                return null;
            }

            @Override
            public @NotNull ListIterator<ConversationTrigger> listIterator(int index) {
                return null;
            }

            @Override
            public @NotNull List<ConversationTrigger> subList(int fromIndex, int toIndex) {
                return List.of();
            }
        });
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
            Gdx.app.log("conditions", "Stage cleared!");
            stageClear();
        }
    }

    public void stageClear() {
        /* This is called upon victory.
         * Child classes should overwrite with directions
         * to next screen. I.e., map, menu, dialogue, etc.
         */
    }

    public void highlightAllTilesUnitCanAccess(@NotNull final SimpleUnit unit) {
        reachableTiles = new Array<>();
        attackableUnits = new Array<>();

        final int originRow = unit.getRow();
        final int originColumn = unit.getColumn();

        recursionHandler.recursivelySelectReachableTiles(unit);
        reachableTiles.add(unit.occupyingTile);

        final Texture blueSquareTexture = new Texture(Gdx.files.internal("ui/menu.png"));
        final TextureRegion blueSquareRegion = new TextureRegion(blueSquareTexture,0,0,100,100);

        tileHighlighters = new Array<>();

        /* TODO: this got noticeably slower once migrated to LogicalTile
        *   due to creation of new Texture/Region objects per tile
        *   rather than once before for() loop. Issue should resolve
        *   itself once AssetHandler is working properly.
        *   For now, passing in region as a constant seems to serve as a
        *   bandaid solution.
        */

        for(final LogicalTile tile : reachableTiles) {
                tileHighlighters.add(tile);

                tile.highlightCanMove(unit, originColumn, originRow, blueSquareRegion);
        }
    }

    public void clearAttackableEnemies() {
        for(SimpleUnit unit : attackableUnits) {
            unit.setCanMove();
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

    public void executeAction(AIAction action) {
        // Landing pad for commands from AIHandler
        // This does not validate or consider commands at all, only executes them. Be careful.

        Gdx.app.log("EXECUTING:", "" + action.getActionType());

        executingAction = true;

        switch (action.getActionType()) {

            case MOVE_ACTION:
                logicalMap.moveAlongPath(action.getSubjectUnit(), action.getAssociatedPath());
                break;

            case ATTACK_ACTION:
                if(logicalMap.distanceBetweenTiles(action.getSubjectUnit().occupyingTile, action.getObjectUnit().occupyingTile) > action.getSubjectUnit().getSimpleReach()) {
                    // Out of reach, need to move first.

                    RunnableAction combat = new RunnableAction();
                    combat.setRunnable(new Runnable() {
                        @Override
                        public void run() {
                            // TODO: visualize
                            conditionsHandler.combat().physicalAttack(action.getSubjectUnit(), action.getObjectUnit());
//                            action.getSubjectUnit().setCannotMove();
                        }
                    });
                    logicalMap.moveAlongPath(action.getSubjectUnit(), action.getAssociatedPath(), combat);

                } else {
                    // TODO: visualize
                    conditionsHandler.combat().physicalAttack(action.getSubjectUnit(), action.getObjectUnit());
                    action.getSubjectUnit().setCannotMove();
                }

                break;

            case ESCAPE_ACTION:
                if(action.getAssociatedPath().contains(logicalMap.getTileAtPosition(action.getCoordinate()))) {
                    // Can escape this turn
                    RunnableAction escape = new RunnableAction();
                    escape.setRunnable(new Runnable() {
                        @Override
                        public void run() {
                            conditionsHandler.teams().escapeUnit(action.getSubjectUnit());
                            if(action.getIndex() != 42069) { // this is true if the index has been manually set
                                game.activeGridScreen.conditionsHandler.satisfyVictCon(action.getIndex());
                            }
                        }
                    });
                    logicalMap.moveAlongPath(action.getSubjectUnit(), action.getAssociatedPath(), escape);
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
    }

    private void runAI() {
        if(!aiHandler.isThinking() && !isBusy()) {
            aiHandler.run();
        }
    }

    public void setInputMode(InputMode mode) {
        inputMode = mode;
    }

    public void setMovementControl(MovementControl move) {
        movementControl = move;
    }

    public void startConversation(Conversation conversation) {
        HUD.addAction(Actions.fadeOut(.5f));
        this.inputMode = InputMode.CUTSCENE;
        conversation.setColor(1,1,1,0);

        conversationContainer = new Container<>(conversation)
            .bottom();
        conversationContainer.setFillParent(true);

        hudStage.addActor(conversationContainer);
        conversation.addAction(Actions.fadeIn(.5f));
    }

    public void endConversation() {
        conversationContainer.remove();
        this.inputMode = InputMode.STANDARD;
    }


    /**
     * OVERRIDES
     */
    @Override
    public void show() {
        super.show();

        initializeVariables();

        whoseTurn = conditionsHandler.whoseNextInLine();

        Gdx.app.log("show", "whoseTurn: " + whoseTurn.name);

        gameStage.addListener(new DragListener() {
            final Vector3 tp = new Vector3();

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                game.activeGridScreen.gameStage.getCamera().unproject(tp.set(input.getX(), input.getY(),0));

                // TODO: arbitrary click anywhere works now! implement desired game features, i.e., tile info, free move, etc -- do I still need selective image regions for tile hover?

                return true;
            }

            @Override
            public boolean mouseMoved (InputEvent event, float x, float y) {
                try {
                    game.activeGridScreen.gameStage.getCamera().unproject(tp.set((float) (double) input.getX(), (float) (double) input.getY(), 0));

//                    Gdx.app.log("hovered tile", "" + logicalMap.getTileAtPosition((int) tp.y, (int) tp.x).tileType);
                    hud().updateTilePanel(logicalMap.getTileAtPosition((int) tp.y, (int) tp.x).tileType);
                    return false;
                } catch (Exception ignored) {}
                return false;
            }

            @Override
            public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                final float x = input.getDeltaX() * .05f; // TODO: variable scroll speed setting can be injected here
                final float y = input.getDeltaY() * .05f;

                gameCamera.translate(-x,y);
                gameCamera.update();

                gameStage.act();
                gameStage.draw();
            }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                    Gdx.app.log("enter", "entered!");
                }
        });

        gameCamera.update();

        setUpVictFailCons();

        hud().updateTurnOrderPanel();
//        gameStage.setDebugAll(true);
//        logicalMap.debugShowAllTilesOfType(LogicalTileType.IMPASSIBLE_WALL);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        gameCamera.update();

        orthoMapRenderer.setView(gameCamera);
        orthoMapRenderer.render();

        if(whoseTurn.getTeamAlignment() != TeamAlignment.PLAYER) {
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

    @Override
    public void resize(int width, int height) {
//        float worldWidth = width / 16f; // Adjust world size dynamically
//        float worldHeight = height / 16f;
//        gameStage.getViewport().setWorldSize(worldWidth, worldHeight);

        gameStage.getViewport().update(width, height, false);
        gameStage.getCamera().update();

        hudStage.getViewport().setWorldSize(width, height);
        hudStage.getViewport().update(width, height, true);
        hudStage.getCamera().update();

        HUD.resized(width,height);
    }

    public void checkLineOrder() { whoseTurn = conditionsHandler.whoseNextInLine(); }

    /**
     * GETTERS
     */
    public InputMode getInputMode() {return inputMode;}
    public MovementControl getMovementControl() { return movementControl; }
    public Boolean isBusy() {return executingAction || logicalMap.isBusy();}
    public SimpleUnit whoseNext() { return whoseTurn; }
    public WyrMap getLogicalMap() { return  logicalMap; }
    public WyrHUD hud() { return  HUD; }
    public ConversationHandler getConversationHandler() { return conversationHandler; }

}
