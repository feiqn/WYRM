package com.feiqn.wyrm.logic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.BattleConditionsHandler;
import com.feiqn.wyrm.logic.handlers.CombatHandler;
import com.feiqn.wyrm.logic.handlers.ai.AIHandler;
import com.feiqn.wyrm.logic.handlers.ai.ActionType;
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
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.feiqn.wyrm.models.mapdata.WyrMap;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class BattleScreen extends ScreenAdapter {

    // --VARIABLES--
    // --GAME--
    protected final WYRMGame game;

    // --MAP--
    public WyrMap logicalMap;

    protected AIHandler aiHandler;

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

    public Label tileDataUILabel,
                 unitDataUILabel;

//    public Label.LabelStyle menuLabelStyle;
//    public BitmapFont menuFont;

    // --GROUPS--
    public Group rootGroup,
                 uiGroup,
                 activePopupMenu;

    // --BOOLEANS--
    private boolean keyPressed_W,
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

    public HashMap<ObjectType, Array> mapObjects;

    // --ENUMS--

    public StageList stageID;

    public Unit activeUnit;
    public Phase currentPhase;

    public   HashMap<LogicalTile, Float> tileCheckedAtSpeed;

    public CombatHandler combatHandler;
    public BattleConditionsHandler conditionsHandler;

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
//        game.assetHandler.Initialize();
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

//        rootGroup.setSize(mapWidth, mapHeight);

//        rootGroup.setPosition(0,0,0);

//        gameCamera.position.scl(0,0,0);

        gameStage.addActor(rootGroup);

        initialiseFont();
        initialiseUI();

//        gameCamera.position.set(rootGroup.getX(),rootGroup.getY(),rootGroup.getZIndex());

        initialiseMultiplexer();

//        game.AssetHandler.Initialize();

        passPhaseToTeam(TeamAlignment.PLAYER);
    }

    public void initialiseMultiplexer() {
        final InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(hudStage);
        multiplexer.addProcessor(gameStage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void initialiseFont() {
        // TODO: load via asset handler
//        final Texture fontTexture = new Texture(Gdx.files.internal("ui/font/tinyFont.png"), true);
//        fontTexture.setFilter(Texture.TextureFilter.MipMapNearestNearest, Texture.TextureFilter.Linear);
//
//        menuFont = new BitmapFont(Gdx.files.internal("ui/font/tinyFont.fnt"), new TextureRegion(fontTexture), false);
//        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font/COPPERPLATE.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter menuFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        menuFontParameter.color = Color.WHITE;
//        menuFontParameter.borderWidth = 2f;
//        menuFontParameter.borderColor = Color.BLACK;
//        menuFontParameter.size = 16;
//        menuFontParameter.incremental = true;
//        menuFont = fontGenerator.generateFont(menuFontParameter);
//        fontGenerator.dispose();
//
//        menuLabelStyle = new Label.LabelStyle();
//        menuLabelStyle.font = menuFont;
    }

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

    public void escapeUnit(Unit unit) {
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
                    if(otherTeam.contains(unit,true)); {
                        otherTeam.removeValue(unit, true);
                        unit.remove();
                    }
                }
                break;
        }
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

    private void updateHUD() {

    }

//    private void DEBUGCHAR() {
//        final Texture debugCharTexture = new Texture(Gdx.files.internal("test/ripped/fe/sprites.png"));
//        final TextureRegion pegKnightRegion = new TextureRegion(debugCharTexture,16*13,16*4+10, 16,22);
//
//        final Unit testChar = new Leif(game, pegKnightRegion);
//        testChar.setSize(1, 1.5f);
//
//        logicalMap.placeUnitAtPosition(testChar, 15, 23);
//
//        playerTeam.add(testChar);
//        rootGroup.addActor(testChar);
//
////        testChar.addListener(new ClickListener() {
////
////            @Override
////            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
////                unitDataUILabel.setText("Unit: " + testChar.name);
////            }
////
////        });
//
//        testChar.addExp(550);
//        testChar.getInventory().addItem(new IronSword(game));
//    }

//    private void DEBUGENEMY() {
//        final Texture debugCharTexture = new Texture(Gdx.files.internal("test/ripped/fe/sprites.png"));
//        final TextureRegion debugCharRegion = new TextureRegion(debugCharTexture,0,0,16,16);
//
//        final Unit testEnemy = new Unit(game, debugCharRegion);
//        testEnemy.setSize(1,1);
//        testEnemy.setColor(Color.RED);
//        testEnemy.setTeamAlignment(TeamAlignment.ENEMY);
//        testEnemy.name = "Evil Timn";
//
//        logicalMap.placeUnitAtPosition(testEnemy, 27, 23);
//
//        enemyTeam.add(testEnemy);
//        rootGroup.addActor(testEnemy);
//    }

    public void highlightAllTilesUnitCanAccess(@NotNull final Unit unit) {
        reachableTiles = new Array<>();
        attackableUnits = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();

        final int originRow = unit.getRow();
        final int originColumn = unit.getColumn();

        recursivelySelectReachableTiles(unit);
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
//                final Image highlightImage = new Image(blueSquareRegion);
//                highlightImage.setSize(1, 1);
//                highlightImage.setPosition(tile.coordinates.x, tile.coordinates.y);
//                highlightImage.setColor(.5f, .5f, .5f, .4f);

                tileHighlighters.add(tile);

                tile.highlightCanMove(unit, originColumn, originRow, blueSquareRegion);

//                highlightImage.addListener(new InputListener() {
//                    @Override
//                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                        logicalMap.placeUnitAtPosition(unit, (int) highlightImage.getY(), (int) highlightImage.getX());
//
//                        if(unit.canMove()) {
//                            unit.toggleCanMove();
//                        }
//
//                        removeTileHighlighters();
//                        clearAttackableEnemies();
//
//                        final FieldActionsPopup fap = new FieldActionsPopup(game, unit, x, y, originRow, originColumn);
//
//                        uiGroup.addActor(fap);
//
//                        return true;
//                    }
//
//                    @Override
//                    public void touchUp(InputEvent event, float x, float y, int point, int button) {
//
//                    }
//                });
//
//                rootGroup.addActor(highlightImage);

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

    @NotNull
    @Contract(pure = true)
    private Array<LogicalTile> shortestPath(LogicalTile origin, LogicalTile destination, Array<LogicalTile> reachableTiles) {
        final Array<LogicalTile> shortestPath = new Array<>();

        // TODO
        return shortestPath;
    }

    public void checkIfAllUnitsHaveMovedAndPhaseShouldChange(@NotNull Array<Unit> team) {
        boolean everyoneHasMoved = true;
        for(Unit unit : team) {
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
        if(originTile.row > destinationTile.row) {
            yDistance = originTile.row - destinationTile.row;
        } else {
            yDistance = destinationTile.row - originTile.row;
        }

        int xDistance;
        if(originTile.column > destinationTile.column) {
            xDistance = originTile.column - destinationTile.column;
        } else {
            xDistance = destinationTile.column - originTile.column;
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

    public void recursivelySelectReachableTiles(@NotNull Unit unit) {
        recursivelySelectReachableTiles(unit.getRow(), unit.getColumn(), unit.getBaseMovementSpeed(), unit.getMovementType());
    }
    private void recursivelySelectReachableTiles(int startX, int startY, float moveSpeed, MovementType movementType) {
        // Called by highlightAllTilesUnitCanReach()
        // Called by AIHandler
        // Selects all the tiles within distance moveSpeed of selected tile.

        if (moveSpeed >= 1) {

            boolean continueUp = false;
            boolean continueDown = false;
            boolean continueLeft = false;
            boolean continueRight = false;

            LogicalTile nextTileLeft = new LogicalTile(game, -1, -1);
            LogicalTile nextTileRight = new LogicalTile(game, -1, -1);
            LogicalTile nextTileDown = new LogicalTile(game, -1, -1);
            LogicalTile nextTileUp = new LogicalTile(game, -1, -1);

            final int newX = startX - 1;
            final Vector2 nextPos = new Vector2(newX, startY);

            if (nextPos.x >= 0) {
                nextTileLeft = logicalMap.getTileAtPosition(nextPos);

                if(!tileCheckedAtSpeed.containsKey(nextTileLeft) || tileCheckedAtSpeed.get(nextTileLeft) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileLeft,moveSpeed);

                    if(!nextTileLeft.isOccupied) {

                        if(nextTileLeft.isTraversableByUnitType(movementType)) {
                            if(!reachableTiles.contains(nextTileLeft, true)) {
                                reachableTiles.add(nextTileLeft);
                            }
                            continueLeft = true;
                        }

                    } else if (nextTileLeft.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY && nextTileLeft.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) {

                        continueLeft = true;

                    } else if(!attackableUnits.contains(nextTileLeft.occupyingUnit, true)){
                        // TODO: later qol improvement to directly click enemy rather than moving then selecting attack
//                        attackableUnits.add(nextTileLeft.occupyingUnit);
//                        nextTileLeft.occupyingUnit.redColor();
//                        nextTileLeft.occupyingUnit.constructAndAddAttackListener(activeUnit);
//                        Gdx.app.log("unit", "i see an enemy");

                    }
                }
            }


            final int newX1 = startX + 1;
            final Vector2 nextPos1 = new Vector2(newX1, startY);

            if (nextPos1.x < logicalMap.getTilesWide()) {
                nextTileRight = logicalMap.getTileAtPosition(nextPos1);

                if(!tileCheckedAtSpeed.containsKey(nextTileRight) || tileCheckedAtSpeed.get(nextTileRight) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileRight, moveSpeed);

                    if(!nextTileRight.isOccupied) {

                        if (nextTileRight.isTraversableByUnitType(movementType)) {
                            if(!reachableTiles.contains(nextTileRight, true)) {
                                reachableTiles.add(nextTileRight);
                            }
                            continueRight = true;

                        }
                    } else if(nextTileRight.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY && nextTileRight.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) {

                        continueRight = true;

                    } else if(!attackableUnits.contains(nextTileRight.occupyingUnit, true)){
//                        attackableUnits.add(nextTileRight.occupyingUnit);
//                        nextTileRight.occupyingUnit.redColor();
//                        nextTileRight.occupyingUnit.constructAndAddAttackListener(activeUnit);
//                        Gdx.app.log("unit", "i see an enemy");

                    }
                }
            }


            final int newY = startY - 1;
            final Vector2 nextPos2 = new Vector2(startX, newY);

            if (nextPos2.y >= 0) {
                nextTileDown = logicalMap.getTileAtPosition(nextPos2);

                if(!tileCheckedAtSpeed.containsKey(nextTileDown) || tileCheckedAtSpeed.get(nextTileDown) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileDown, moveSpeed);

                    if(!nextTileDown.isOccupied) {

                        if (nextTileDown.isTraversableByUnitType(movementType)) {
                            if (!reachableTiles.contains(nextTileDown, true)) {
                                reachableTiles.add(nextTileDown);
                            }
                            continueDown = true;

                        }
                    } else if(nextTileDown.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY && nextTileDown.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) {

                        continueDown = true;

                    } else if(!attackableUnits.contains(nextTileDown.occupyingUnit, true)){
//                        attackableUnits.add(nextTileDown.occupyingUnit);
//                        nextTileDown.occupyingUnit.redColor();
//                        nextTileDown.occupyingUnit.constructAndAddAttackListener(activeUnit);
//                        Gdx.app.log("unit", "i see an enemy");

                    }
                }
            }


            final int newY1 = startY + 1;
            final Vector2 nextPos3 = new Vector2(startX, newY1);

            if (nextPos3.y < logicalMap.getTilesHigh()) {
                nextTileUp = logicalMap.getTileAtPosition(nextPos3);

                if(!tileCheckedAtSpeed.containsKey(nextTileUp) || tileCheckedAtSpeed.get(nextTileUp) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileUp, moveSpeed);

                    if(!nextTileUp.isOccupied) {

                        if (nextTileUp.isTraversableByUnitType(movementType)) {
                            if (!reachableTiles.contains(nextTileUp, true)) {
                                reachableTiles.add(nextTileUp);
                            }
                            continueUp = true;
                        }

                    } else if(nextTileUp.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY && nextTileUp.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) {

                        continueUp = true;

                    } else if(!attackableUnits.contains(nextTileUp.occupyingUnit, true)){
//                        attackableUnits.add(nextTileUp.occupyingUnit);
//                        nextTileUp.occupyingUnit.redColor();
//                        nextTileUp.occupyingUnit.constructAndAddAttackListener(activeUnit);
//                        Gdx.app.log("unit", "i see an enemy");
                    }
                }
            }


            if(continueUp) {
                recursivelySelectReachableTiles(startX, newY1, moveSpeed - nextTileUp.getMovementCostForMovementType(movementType), movementType);
            }
            if(continueLeft) {
                recursivelySelectReachableTiles(newX, startY, moveSpeed - nextTileLeft.getMovementCostForMovementType(movementType), movementType);
            }
            if(continueDown) {
                recursivelySelectReachableTiles(startX, newY, moveSpeed - nextTileDown.getMovementCostForMovementType(movementType), movementType);
            }
            if(continueRight) {
                recursivelySelectReachableTiles(newX1, startY, moveSpeed - nextTileRight.getMovementCostForMovementType(movementType), movementType);
            }
        }
    }

    public void executeAction(ActionType actionType, @Null Unit subject, @Null Unit object, @Null Vector2 destination) {
        // Landing pad for commands from AIHandler
        // This does not validate or consider commands at all, only executes them. Be careful.
        executingAction = true;

        switch (actionType) {
            case MOVE_ACTION:
                logicalMap.placeUnitAtPosition(subject, (int)destination.x, (int)destination.y);
                if(subject.canMove()) {
                    subject.toggleCanMove();
                }
                break;
            case ATTACK_ACTION:
            case PASS_ACTION:
                passPhase();
            default:
                break;
        }

        executingAction = false;
    }

    private void runAI() {
        passPhase();
//        aiHandler.run();
    }

    @Override
    public void show() {
        super.show();

        initializeVariables();

        game.assetHandler.initialize();

        layoutUI();

        logicalMap.setUpUnits();

        gameStage.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                final float x = Gdx.input.getDeltaX() * .05f;
                final float y = Gdx.input.getDeltaY() * .05f;

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
//        game.assetHandler.Initialize();
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
        gameStage.draw(); // TODO: write a wrapper function to draw things in order for proper sprite layering

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
    public Boolean isBusy() {return executingAction;}
    public Array<Unit> getEnemyTeam() {return enemyTeam;}
    public Array<Unit> getPlayerTeam() {return playerTeam;}
    public Array<Unit> getAllyTeam() {return allyTeam;}
    public Array<Unit> getOtherTeam() {return otherTeam;}

}
