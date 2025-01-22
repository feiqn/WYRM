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
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.combat.BattleConditionsHandler;
import com.feiqn.wyrm.logic.handlers.combat.CombatHandler;
import com.feiqn.wyrm.logic.handlers.ai.RecursionHandler;
import com.feiqn.wyrm.logic.handlers.ai.AIHandler;
import com.feiqn.wyrm.logic.handlers.ai.actions.AIAction;
import com.feiqn.wyrm.logic.handlers.combat.TeamHandler;
import com.feiqn.wyrm.logic.handlers.conversation.Conversation;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.logic.handlers.ui.WyrHUD;
import com.feiqn.wyrm.models.mapdata.StageList;
import com.feiqn.wyrm.models.mapdata.prefabLogicalMaps.stage_1a;
import com.feiqn.wyrm.models.mapdata.prefabLogicalMaps.stage_debug;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.ObjectType;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.Ballista;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.BreakableWall;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.Door;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.TreasureChest;
import com.feiqn.wyrm.models.phasedata.Phase;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.feiqn.wyrm.models.mapdata.WyrMap;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

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
    protected boolean keyPressed_W,
                      keyPressed_A,
                      keyPressed_D,
                      keyPressed_S,
                      executingAction,
                      someoneIsTalking;

    // --INTS--
    // --FLOATS--
    // --VECTORS--
    // --SPRITES--

    // --ARRAYS--
    public Array<LogicalTile> tileHighlighters;
    public Array<LogicalTile> reachableTiles;

    public Array<Unit> attackableUnits;

    public Array<Ballista> ballistaObjects;
    public Array<Door> doorObjects;
    public Array<BreakableWall> breakableWallObjects;
    public Array<TreasureChest> treasureChestObjects;
//    public Array<VictConInfoPanel> victConUI;

    // --HASHMAPS--
    public HashMap<ObjectType, Array> mapObjects;

    // --ENUMS--
    protected StageList stageID;
    protected InputMode inputMode;
    protected MovementControl movementControl;

    // --HANDLERS--
    public CombatHandler combatHandler;
    public BattleConditionsHandler conditionsHandler;
    public RecursionHandler recursionHandler;
    public TeamHandler teamHandler;
    protected AIHandler aiHandler;

    protected WyrHUD HUD;

    // --OTHER--
    public Unit activeUnit;
    public Unit hoveredUnit;

    private Conversation activeConversation;

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

    private void loadMap() {
        switch(stageID) { // TODO: just override in child class
            case STAGE_DEBUG:
                tiledMap = new TmxMapLoader().load("test/wyrmDebugMap.tmx");
                logicalMap = new stage_debug(game);
                break;
            case STAGE_1A:
                tiledMap = new TmxMapLoader().load("test/wyrmStage1A.tmx");
                logicalMap = new stage_1a(game);
                break;
            case STAGE_2A:
                // TODO: sprite map (battleMap) and WyrMap (logicalMap) for 2A
                break;
            default:
                break;
        }
    }

    private void initializeVariables() {
        keyPressed_A     = false;
        keyPressed_D     = false;
        keyPressed_S     = false;
        keyPressed_W     = false;
        executingAction  = false;
        someoneIsTalking = false;

        rootGroup         = new Group();

        tileHighlighters = new Array<>();

        ballistaObjects  = new Array<>();
        reachableTiles   = new Array<>();

        aiHandler         = new AIHandler(game);
        combatHandler     = new CombatHandler(game);
        conditionsHandler = new BattleConditionsHandler(game);
        teamHandler       = new TeamHandler(game);
        recursionHandler  = new RecursionHandler(game);

        mapObjects = new HashMap<>();
        mapObjects.put(ObjectType.BALLISTA, ballistaObjects);
        mapObjects.put(ObjectType.DOOR, doorObjects);
        mapObjects.put(ObjectType.TREASURE_CHEST, treasureChestObjects);
        mapObjects.put(ObjectType.BREAKABLE_WALL, breakableWallObjects);

        loadMap();

        gameCamera = new OrthographicCamera();
        orthoMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/16f); // TODO: prettier

        final float worldWidth  = Gdx.graphics.getWidth() / 16f;
        final float worldHeight = Gdx.graphics.getHeight() / 16f;
        gameCamera.setToOrtho(false, worldWidth , worldHeight);
        gameCamera.update();

        final ExtendViewport viewport = new ExtendViewport(worldWidth, worldHeight, gameCamera);

        gameStage = new Stage(viewport);

        final MapProperties mapProperties = tiledMap.getProperties();
        final int mapWidth = mapProperties.get("width", Integer.class);
        final int mapHeight = mapProperties.get("height", Integer.class);

        rootGroup.setSize(mapWidth, mapHeight);

        gameStage.addActor(rootGroup);

        setInputMode(InputMode.STANDARD);
        setMovementControl(MovementControl.FREE_MOVE);

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
        multiplexer.addProcessor(hudStage);
        multiplexer.addProcessor(gameStage);
        multiplexer.addProcessor(keyboardListener);
        input.setInputProcessor(multiplexer);
    }

    // --------
    // -- UI --
    // --------

    private void initialiseHUD() {
        hudStage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        HUD = new WyrHUD(game);
        hudStage.addActor(HUD);
//
//        uiGroup.setPosition(0,0);
//        uiGroup.addActor(victConGroup);
//        uiGroup.addActor(infoPanelGroup);


//        infoPanelGroup.addActor(hoveredUnitInfoPanel);


//        infoPanelGroup.addActor(hoveredTileInfoPanel);

//        cutsceneGroup.setPosition(0,0);
//        hudStage.addActor(cutsceneGroup);


    }

    // ------------
    // -- END UI --
    // ------------

    protected void setUpVictCons() {
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

    public void highlightAllTilesUnitCanAccess(@NotNull final Unit unit) {
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
        for(Unit unit : attackableUnits) {
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

    public void checkIfAllUnitsHaveMovedAndPhaseShouldChange() {
        boolean everyoneHasMoved = true;
        for(Unit unit : teamHandler.currentTeam()) {
            if(unit.canMove()) {
                everyoneHasMoved = false;
            }
        }
        if(everyoneHasMoved) {
            conditionsHandler.passPhase();
        }
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
                if(logicalMap.distanceBetweenTiles(action.getSubjectUnit().occupyingTile, action.getObjectUnit().occupyingTile) > action.getSubjectUnit().iron_getReach()) {
                    // Out of reach, need to move first.

                    RunnableAction combat = new RunnableAction();
                    combat.setRunnable(new Runnable() {
                        @Override
                        public void run() {
                            combatHandler.goToCombat(action.getSubjectUnit(), action.getObjectUnit());
//                            action.getSubjectUnit().setCannotMove();
                        }
                    });
                    logicalMap.moveAlongPath(action.getSubjectUnit(), action.getAssociatedPath(), combat);

                } else {
                    combatHandler.goToCombat(action.getSubjectUnit(), action.getObjectUnit());
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
                            teamHandler.escapeUnit(action.getSubjectUnit());
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

            case WAIT_ACTION:
                action.getSubjectUnit().setCannotMove();
                break;

            case PASS_ACTION:
                conditionsHandler.passPhase();
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
//        else if (aiHandler.isThinking()) {
//            Gdx.app.log("thinking","");
//        } else if (isBusy()) {
//            Gdx.app.log("busy", "");
//        }
        // passPhase();
    }

    public void setInputMode(InputMode mode) {
        inputMode = mode;
    }

    public void setMovementControl(MovementControl move) {
        movementControl = move;
    }

    public void startConversation(Conversation conversation) {
        activeConversation = conversation;
        HUD.addAction(Actions.fadeOut(.5f));
//        this.inputMode = InputMode.CUTSCENE;
        conversation.setColor(1,1,1,0);
        hudStage.addActor(conversation);
//        cutsceneGroup.addActor(conversation);
        conversation.addAction(Actions.fadeIn(.5f));
    }

    /**
     * OVERRIDES
     */

    @Override
    public void show() {
        super.show();

        initializeVariables();

        logicalMap.setUpUnits();

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

        gameStage.setDebugAll(true);

//        logicalMap.debugShowAllTilesOfType(LogicalTileType.IMPASSIBLE_WALL);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        orthoMapRenderer.setView(gameCamera);
        orthoMapRenderer.render();

        if(conditionsHandler.currentPhase() != Phase.PLAYER_PHASE) {
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

        gameStage.getViewport().update(width, height, false);
        gameStage.getCamera().update();

        hudStage.getViewport().update(width, height, true);
        hudStage.getCamera().update();

        for(Actor actor : hudStage.getActors()) {
            if(actor instanceof HUDElement) {
                ((HUDElement) actor).resized(width, height);
            }
        }
    }

    /**
     * GETTERS
     */
    public InputMode getInputMode() {return inputMode;}
    public MovementControl getMovementControl() { return movementControl; }
    public Boolean isBusy() {return executingAction || logicalMap.isBusy();}
    public WyrMap getLogicalMap() { return  logicalMap; }
    public WyrHUD hud() { return  HUD; }

}
