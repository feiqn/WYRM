package com.feiqn.wyrm.logic.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.combat.BattleConditionsHandler;
import com.feiqn.wyrm.logic.handlers.combat.CombatHandler;
import com.feiqn.wyrm.logic.handlers.ai.RecursionHandler;
import com.feiqn.wyrm.logic.handlers.ai.AIHandler;
import com.feiqn.wyrm.logic.handlers.ai.actions.AIAction;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.HoveredUnitInfoPanel;
import com.feiqn.wyrm.logic.screens.stagelist.StageList;
import com.feiqn.wyrm.models.mapdata.prefabLogicalMaps.stage_1a;
import com.feiqn.wyrm.models.mapdata.prefabLogicalMaps.stage_debug;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapobjectdata.ObjectType;
import com.feiqn.wyrm.models.mapobjectdata.prefabObjects.Ballista;
import com.feiqn.wyrm.models.mapobjectdata.prefabObjects.BreakableWall;
import com.feiqn.wyrm.models.mapobjectdata.prefabObjects.Door;
import com.feiqn.wyrm.models.mapobjectdata.prefabObjects.TreasureChest;
import com.feiqn.wyrm.models.phasedata.Phase;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.feiqn.wyrm.models.mapdata.WyrMap;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static com.badlogic.gdx.Gdx.input;

public class BattleScreen extends ScreenAdapter {

    // --VARIABLES--
    // --GAME--
    protected final WYRMGame game;

    // --MAP--
    public WyrMap logicalMap;

    // --CAMERA--
    public OrthographicCamera gameCamera,
                              uiCamera;

    // --TILED--
    public TiledMap battleMap;
    public OrthogonalTiledMapRenderer orthoMapRenderer;
    public TiledMapTileLayer groundLayer;

    // --STAGE--
    public Stage gameStage,
                 hudStage;

    // --LABELS--
    public Label tileDataUILabel,
                 unitDataUILabel;

//    public Label.LabelStyle menuLabelStyle;
//    public BitmapFont menuFont;

    // --GROUPS--
    public Group rootGroup,
                 uiGroup,
                 activePopupMenu;

    // --BOOLEANS--
    protected boolean keyPressed_W,
                      keyPressed_A,
                      keyPressed_D,
                      keyPressed_S,
                      allyTeamUsed,
                      otherTeamUsed,
                      executingAction;

    // --INTS--
    // --FLOATS--
    // --VECTORS--
    // --SPRITES--

    // --ARRAYS--
    public Array<LogicalTile> tileHighlighters;
    public Array<Unit> attackableUnits;
    public Array<LogicalTile> reachableTiles;
    public Array<Unit> playerTeam;
    public Array<Unit> enemyTeam;
    public Array<Unit> allyTeam;
    public Array<Unit> otherTeam;
    public Array<Ballista> ballistaObjects;
    public Array<Door> doorObjects;
    public Array<BreakableWall> breakableWallObjects;
    public Array<TreasureChest> treasureChestObjects;

    // --HASHMAPS--
    public HashMap<ObjectType, Array> mapObjects;

    // --ENUMS--
    public StageList stageID;

    // --HANDLERS--
    public CombatHandler combatHandler;
    public BattleConditionsHandler conditionsHandler;
    public RecursionHandler recursionHandler;
    protected AIHandler aiHandler;

    // --OTHER--
    public Unit activeUnit;
    public Unit hoveredUnit;

    protected HoveredUnitInfoPanel hoveredUnitInfoPanel;

    public Phase currentPhase;

    private InputAdapter keyboardListener;

    // -------------------------------
    // --END OF VARIABLE DECLARATION--
    // -------------------------------

    public BattleScreen(WYRMGame game) {
        this.game = game;
        this.stageID = StageList.STAGE_DEBUG;
    }

    public BattleScreen(WYRMGame game, StageList stageID) {
        this.game = game;
        this.stageID = stageID;
    }

    private void loadMap() {
        switch(stageID) {
            case STAGE_DEBUG:
                battleMap  = new TmxMapLoader().load("test/wyrmDebugMap.tmx");
                logicalMap = new stage_debug(game);
                break;
            case STAGE_1A:
                battleMap  = new TmxMapLoader().load("test/wyrmStage1A.tmx");
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
        currentPhase = Phase.PLAYER_PHASE;
        keyPressed_A = false;
        keyPressed_D = false;
        keyPressed_S = false;
        keyPressed_W = false;
        allyTeamUsed = false;
        otherTeamUsed = false;
        executingAction = false;

        tileHighlighters = new Array<>();

        rootGroup = new Group();
        uiGroup = new Group();

        playerTeam = new Array<>();
        enemyTeam = new Array<>();
        allyTeam = new Array<>();
        otherTeam = new Array<>();

        ballistaObjects = new Array<>();

        aiHandler = new AIHandler(game);
        combatHandler = new CombatHandler(game);
        conditionsHandler = new BattleConditionsHandler(game);
        recursionHandler = new RecursionHandler(game);

        hoveredUnitInfoPanel = new HoveredUnitInfoPanel(game);

        gameCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera();

        reachableTiles = new Array<>();

        mapObjects = new HashMap<>();
        mapObjects.put(ObjectType.BALLISTA, ballistaObjects);
        mapObjects.put(ObjectType.DOOR, doorObjects);
        mapObjects.put(ObjectType.TREASURE_CHEST, treasureChestObjects);
        mapObjects.put(ObjectType.BREAKABLE_WALL, breakableWallObjects);

        loadMap();
        orthoMapRenderer = new OrthogonalTiledMapRenderer(battleMap, 1/16f);

        final float worldWidth  = Gdx.graphics.getWidth() / 16f;
        final float worldHeight = Gdx.graphics.getHeight() / 16f;
        gameCamera.setToOrtho(false, worldWidth , worldHeight);
        gameCamera.update();

//        final ScalingViewport viewport = new ScalingViewport(Scaling.stretch, worldWidth, worldHeight);
        final FitViewport viewport = new FitViewport(worldWidth, worldHeight);

        gameStage = new Stage(viewport);

        final MapProperties mapProperties = battleMap.getProperties();
        final int mapWidth = mapProperties.get("width", Integer.class);
        final int mapHeight = mapProperties.get("height", Integer.class);

        rootGroup.setSize(mapWidth, mapHeight);

        gameStage.addActor(rootGroup);

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

        initialiseUI();
        initialiseMultiplexer();

        passPhaseToTeam(TeamAlignment.PLAYER);
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

    private void initialiseUI(){
        uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        final FitViewport fitViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        hudStage = new Stage(fitViewport);

        uiCamera.position.set(0,0,0);
        uiGroup.setPosition(0,0);

        hudStage.addActor(uiGroup);
    }

    private void layoutUI() {
        tileDataUILabel = new Label("Tile: ", game.assetHandler.menuLabelStyle);
        uiGroup.addActor(tileDataUILabel);
        tileDataUILabel.setPosition(1, 1);

        unitDataUILabel = new Label("Unit: ", game.assetHandler.menuLabelStyle);
        uiGroup.addActor(unitDataUILabel);
        unitDataUILabel.setPosition(1, tileDataUILabel.getHeight() + 5);

    }

    private void updateHUD() {

    }

    // ------------
    // -- END UI --
    // ------------

    public void checkForStageCleared() {
        if(conditionsHandler.victoryConditionsAreSatisfied()) {
            Gdx.app.log("conditions", "Stage cleared!");
            stageClear();
        }
    }

    protected void stageClear() {
        /* This is called upon victory.
         * Child classes should overwrite with directions
         * to next screen. I.e., map, menu, dialogue, etc.
         */
    }

    public void escapeUnit(Unit unit) { // TODO: migrate to TeamHandler
        switch(unit.getTeamAlignment()) {
            case PLAYER:
                if(playerTeam.contains(unit, true)) {
                    playerTeam.removeValue(unit,true);
                    unit.remove();
                }
                break;
            case ENEMY:
                if(enemyTeam.contains(unit,true)) {
                    enemyTeam.contains(unit,true);
                    unit.remove();
                }
                break;
            case ALLY:
                if(allyTeamUsed) {
                    if(allyTeam.contains(unit, true)) {
                        allyTeam.removeValue(unit,true);
                        unit.remove();
                    }
                }
                break;
            case OTHER:
                if(otherTeamUsed) {
                    if(otherTeam.contains(unit,true)) {
                        otherTeam.removeValue(unit, true);
                        unit.remove();
                    }
                }
                break;
        }
    }

    public void addHoveredUnitInfoPanel(Unit unit) {
        hoveredUnitInfoPanel.setUnit(unit);
        hudStage.addActor(hoveredUnitInfoPanel);
    }

    public void removeHoveredUnitInfoPanel() {
        hoveredUnitInfoPanel.remove();
    }

    private void passPhase() {
        // By default, turn order is as follows:

        // PLAYER -> ENEMY -> ALLY -> OTHER -> PLAYER

        // Calling this function will pass the turn as normal,
        // or you can manually assign turns (I.E., to give the
        // enemy more chances to move on a certain mission, etc.)
        // via passPhaseToTeam(), which this function simply wraps
        // for convenience.
        activeUnit = null;

        switch (currentPhase) {
            case PLAYER_PHASE:
                passPhaseToTeam(TeamAlignment.ENEMY);
                break;
            case ENEMY_PHASE:
                if(allyTeamUsed) {
                    passPhaseToTeam(TeamAlignment.ALLY);
                } else if(otherTeamUsed) {
                    passPhaseToTeam(TeamAlignment.OTHER);
                } else {
                    passPhaseToTeam(TeamAlignment.PLAYER);
                }
                break;
            case ALLY_PHASE:
                if(otherTeamUsed) {
                    passPhaseToTeam(TeamAlignment.OTHER);
                } else {
                    passPhaseToTeam(TeamAlignment.PLAYER);
                }
                break;
            case OTHER_PHASE:
                passPhaseToTeam(TeamAlignment.PLAYER);
                break;
        }
    }

    private void passPhaseToTeam(@NotNull TeamAlignment team) {
        resetTeams();
        switch (team) {
            case PLAYER:
                if(conditionsHandler.victoryConditionsAreSatisfied() && conditionsHandler.turnCount() != 0) {
                    Gdx.app.log("conditions", "You win!");
                    stageClear();

                    // TODO: do i need to unload this old screen somehow?

                    // The following is debug code that will only run if
                    // child classes are not implemented properly.
                    MapScreen screen = new MapScreen(game);
                    game.activeScreen = screen;
                    game.activeBattleScreen = null;
                    game.setScreen(screen);
                    // --END--
                } else {
                    Gdx.app.log("phase: ", "Player Phase");
                    conditionsHandler.nextTurn();
                    currentPhase = Phase.PLAYER_PHASE;
                }
                break;
            case ALLY:
                Gdx.app.log("phase: ", "Ally Phase");
                currentPhase = Phase.ALLY_PHASE;
                break;
            case ENEMY:
                Gdx.app.log("phase: ", "Enemy Phase");
                currentPhase = Phase.ENEMY_PHASE;
                break;
            case OTHER:
                Gdx.app.log("phase: ", "Other Phase");
                currentPhase = Phase.OTHER_PHASE;
                break;
        }
    }

    private void resetTeams() {
        resetTeam(playerTeam);
        resetTeam(enemyTeam);
        if(otherTeamUsed) resetTeam(otherTeam);
        if(allyTeamUsed) resetTeam(allyTeam);
    }

    private void resetTeam(@NotNull Array<Unit> team) {
        for(Unit unit : team) {
            unit.standardColor();
            if(!unit.canMove()) {
                unit.toggleCanMove();
            }
        }
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
            unit.removeAttackListener();
            if(unit.canMove()) {
                unit.standardColor();
            } else {
                unit.dimColor();
            }
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
        for(Unit unit : currentTeam()) {
            if(unit.canMove()) {
                everyoneHasMoved = false;
            }
        }
        if(everyoneHasMoved) {
            passPhase();
        }
    }

    public Array<Unit> currentTeam() {
        switch(currentPhase) {
            case OTHER_PHASE:
                return  otherTeam;
            case ENEMY_PHASE:
                return  enemyTeam;
            case ALLY_PHASE:
                return  allyTeam;
            case PLAYER_PHASE:
            default:
                return playerTeam;
        }
    }

    public int distanceBetweenTiles(@NotNull LogicalTile originTile, @NotNull LogicalTile destinationTile) {

        int yDistance;
        if(originTile.getRow() > destinationTile.getRow()) {
            yDistance = originTile.getRow() - destinationTile.getRow();
        } else {
            yDistance = destinationTile.getRow() - originTile.getRow();
        }

        int xDistance;
        if(originTile.getColumn() > destinationTile.getColumn()) {
            xDistance = originTile.getColumn() - destinationTile.getColumn();
        } else {
            xDistance = destinationTile.getColumn() - originTile.getColumn();
        }

        return yDistance + xDistance;
    }

    @NotNull
    private Array<LogicalTile> tilesWithinDistanceOfOrigin(LogicalTile origin, int distance) {
        Array<LogicalTile> tilesInRange = new Array<>();

        for(LogicalTile[] tileArray : logicalMap.internalLogicalMap) {
            for(LogicalTile tile : tileArray) {
                if(distanceBetweenTiles(origin, tile) <= distance) {
                    tilesInRange.add(tile);
                }
            }
        }

        return tilesInRange;
    }

    public void executeAction(AIAction action) {
        // Landing pad for commands from AIHandler
        // This does not validate or consider commands at all, only executes them. Be careful.

//        Gdx.app.log("EXECuTING:", "" + action.getActionType());

        executingAction = true;

        switch (action.getActionType()) {
            case MOVE_ACTION:
                logicalMap.moveAlongPath(action.getSubjectUnit(), action.getAssociatedPath());
                if(action.getSubjectUnit().canMove()) {
                    action.getSubjectUnit().toggleCanMove();
                }
                break;
            case ATTACK_ACTION:
                if(distanceBetweenTiles(action.getSubjectUnit().occupyingTile, action.getObjectUnit().occupyingTile) > action.getSubjectUnit().getReach()) {
                    logicalMap.moveAlongPath(action.getSubjectUnit(), action.getAssociatedPath());
                }
                if(action.getSubjectUnit().canMove()) {
                    action.getSubjectUnit().toggleCanMove();
                }
                combatHandler.goToCombat(action.getSubjectUnit(), action.getObjectUnit()); // TODO: put this in a runnable and pass to moveAlongPath
                break;
            case WAIT_ACTION:
                action.getSubjectUnit().toggleCanMove();
                break;
            case PASS_ACTION:
                passPhase();
                break;
            case ESCAPE_ACTION:
                passPhase();
                // TODO



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
        // passPhase();
    }

    @Override
    public void show() {
        super.show();

        initializeVariables();

        layoutUI();

        logicalMap.setUpUnits();

        gameStage.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                final float x = input.getDeltaX() * .05f;
                final float y = input.getDeltaY() * .05f;

                gameCamera.translate(-x,y);
                gameCamera.update();

                final float destinationX = rootGroup.getX() + x;
                final float destinationY = rootGroup.getY() - y;

                rootGroup.setPosition(destinationX, destinationY);
                gameStage.act();
                gameStage.draw();
            }

        });

        gameCamera.update();

        gameStage.setDebugAll(false);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        orthoMapRenderer.setView(gameCamera);
        orthoMapRenderer.render();

        if(currentPhase != Phase.PLAYER_PHASE) {
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

    // --SETTERS--

    // TODO: migrate to TeamHandler class
    public void removeUnitFromTeam(Unit unit, TeamAlignment team) {
        switch(team) {
            case OTHER:
                if(otherTeam.contains(unit, true)) {
                    otherTeam.removeValue(unit,true);
                }
                break;
            case ALLY:
                if(allyTeam.contains(unit,true)) {
                    allyTeam.removeValue(unit,true);
                }
                break;
            case PLAYER:
                if(playerTeam.contains(unit,true)) {
                    playerTeam.removeValue(unit, true);
                }
                break;
            case ENEMY:
                if(enemyTeam.contains(unit,true)) {
                    enemyTeam.removeValue(unit,true);
                }
                break;
        }
    }

    @Override
    public void resize(int width, int height) {
        gameStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        gameStage.getCamera().update();

        initialiseUI();
        initialiseMultiplexer();
    }

    // --GETTERS--
    public Boolean isBusy() {return executingAction || logicalMap.isBusy();}
    public Array<Unit> getEnemyTeam() {return enemyTeam;}
    public Array<Unit> getPlayerTeam() {return playerTeam;}
    public Array<Unit> getAllyTeam() {return allyTeam;}
    public Array<Unit> getOtherTeam() {return otherTeam;}

}
