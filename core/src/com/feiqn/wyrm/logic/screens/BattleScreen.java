package com.feiqn.wyrm.logic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.CombatHandler;
import com.feiqn.wyrm.logic.screens.stagelist.StageList;
import com.feiqn.wyrm.logic.ui.PopupMenu;
import com.feiqn.wyrm.logic.ui.popups.BattlePreviewPopup;
import com.feiqn.wyrm.logic.ui.popups.FieldActionsPopup;
import com.feiqn.wyrm.logic.ui.popups.UnitInfoPopup;
import com.feiqn.wyrm.models.mapdata.prefabLogicalMaps.stage_1a;
import com.feiqn.wyrm.models.mapdata.prefabLogicalMaps.stage_debug;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.phasedata.Phase;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.feiqn.wyrm.models.mapdata.WyrMap;

import java.util.HashMap;
import java.util.Random;

public class BattleScreen extends ScreenAdapter {

    // --VARIABLES--
    // --GAME--
    private final WYRMGame game;

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

    public Label.LabelStyle menuLabelStyle;
    public BitmapFont menuFont;

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
                    otherTeamUsed;

    // --INTS--
    // --FLOATS--
    // --VECTORS--
    // --SPRITES--
    // --ARRAYS--

    public Array<Image> tileHighlighters;
    public Array<Unit> attackableUnits;
    private Array<LogicalTile> reachableTiles;
    private Array<Unit> playerTeam;
    private Array<Unit> enemyTeam;
    private Array<Unit> allyTeam;
    private Array<Unit> otherTeam;

    // --ENUMS--

    public StageList stageID;

    public Unit activeUnit;
    public Phase currentPhase;

    private HashMap<LogicalTile, Float> tileCheckedAtSpeed;

    public CombatHandler combatHandler;

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
        }
    }

    private void initializeVariables() {
        keyPressed_A = false;
        keyPressed_D = false;
        keyPressed_S = false;
        keyPressed_W = false;
        allyTeamUsed = false;
        otherTeamUsed = false;

        tileHighlighters = new Array<>();

        rootGroup = new Group();
        uiGroup = new Group();

        playerTeam = new Array<>();
        enemyTeam = new Array<>();
        allyTeam = new Array<>();
        otherTeam = new Array<>();

        combatHandler = new CombatHandler(game);

        gameCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera();

        reachableTiles = new Array<>();

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

    private void initialiseUI(){
        uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        final FitViewport fitViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        hudStage = new Stage(fitViewport);

        uiCamera.position.set(0,0,0);
        uiGroup.setPosition(0,0);

        hudStage.addActor(uiGroup);
    }

    private void layoutUI() {

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

    private void passPhaseToTeam(TeamAlignment team) {
        switch (team) {
            case PLAYER:
                Gdx.app.log("Phase: ", "Player Phase");
                resetTeams();
                currentPhase = Phase.PLAYER_PHASE;
                break;
            case ALLY:
                Gdx.app.log("Phase: ", "Ally Phase");
                resetTeams();
                currentPhase = Phase.ALLY_PHASE;
                break;
            case ENEMY:
                Gdx.app.log("Phase: ", "Enemy Phase");
                resetTeams();
                currentPhase = Phase.ENEMY_PHASE;
                break;
            case OTHER:
                Gdx.app.log("Phase: ", "Other Phase");
                resetTeams();
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

    private void resetTeam(Array<Unit> team) {
        for(Unit unit : team) {
            unit.standardColor();
            if(!unit.canMove()) {
                unit.toggleCanMove();
            }
        }
    }

    private void DEBUGCHAR() {
        final Texture debugCharTexture = new Texture(Gdx.files.internal("test/test_character.png"));
        final TextureRegion debugCharRegion = new TextureRegion(debugCharTexture,0,0,128,160);

        final Unit testChar = new Unit(game, debugCharRegion);

        testChar.setTeamAlignment(TeamAlignment.PLAYER);
        testChar.setMovementSpeed(16);

        logicalMap.placeUnitAtPosition(testChar, 23, 33);

        playerTeam.add(testChar);
        rootGroup.addActor(testChar);

        testChar.levelUp();
        testChar.levelUp();
        testChar.levelUp();
        testChar.levelUp();
        testChar.levelUp();
    }

    private void DEBUGENEMY() {
        final Texture debugCharTexture = new Texture(Gdx.files.internal("test/test_character.png"));
        final TextureRegion debugCharRegion = new TextureRegion(debugCharTexture,0,0,128,160);

        final Unit testEnemy = new Unit(game, debugCharRegion);
        testEnemy.setSize(1,1);
        testEnemy.setColor(Color.RED);
        testEnemy.setTeamAlignment(TeamAlignment.ENEMY);
        testEnemy.name = "Evil Timn";

        logicalMap.placeUnitAtPosition(testEnemy, 3, 7);

        enemyTeam.add(testEnemy);
        rootGroup.addActor(testEnemy);
    }

    public void highlightAllTilesUnitCanAccess(final Unit unit) {
        reachableTiles = new Array<>();
        attackableUnits = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();

        recursivelySelectReachableTiles(unit);

        final Texture blueSquareTexture = new Texture(Gdx.files.internal("ui/menu.png"));
        final TextureRegion blueSquareRegion = new TextureRegion(blueSquareTexture,0,0,128,128);

        tileHighlighters = new Array<>();

        for(final LogicalTile tile : reachableTiles) {
                final Image highlightImage = new Image(blueSquareRegion);
                highlightImage.setSize(1, 1);
                highlightImage.setPosition(tile.coordinates.x, tile.coordinates.y);
                highlightImage.setColor(.5f, .5f, .5f, .3f);

                tileHighlighters.add(highlightImage);

                highlightImage.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        logicalMap.placeUnitAtPosition(unit, (int) highlightImage.getY(), (int) highlightImage.getX());

                        if(unit.canMove()) {
                            unit.toggleCanMove();
                        }

                        removeTileHighlighters();
                        clearAttackableEnemies();

                        final FieldActionsPopup fap = new FieldActionsPopup(game, unit);

                        uiGroup.addActor(fap);

                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int point, int button) {

                    }
                });

                rootGroup.addActor(highlightImage);

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
        for (Image image : tileHighlighters) {
            image.remove();
        }
        tileHighlighters = new Array<>();
    }

    private Array<LogicalTile> shortestPath(LogicalTile origin, LogicalTile destination, Array<LogicalTile> reachableTiles) {
        final Array<LogicalTile> shortestPath = new Array<>();


        return shortestPath;
    }

    public void checkIfAllUnitsHaveMovedAndPhaseShouldChange(Array<Unit> team) {
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
    public int distanceBetweenTiles(LogicalTile originTile, LogicalTile destinationTile) {

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

    private void recursivelySelectReachableTiles(Unit unit) {
        recursivelySelectReachableTiles(unit.getRow(), unit.getColumn(), unit.getBaseMovementSpeed(), unit.getMovementType());
    }
    private void recursivelySelectReachableTiles(int startX, int startY, float moveSpeed, MovementType movementType) {
        // Called by highlightAllTilesUnitCanReach()
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
                        attackableUnits.add(nextTileLeft.occupyingUnit);
                        nextTileLeft.occupyingUnit.redColor();
                        nextTileLeft.occupyingUnit.constructAndAddAttackListener(activeUnit);
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
                        attackableUnits.add(nextTileRight.occupyingUnit);
                        nextTileRight.occupyingUnit.redColor();
                        nextTileRight.occupyingUnit.constructAndAddAttackListener(activeUnit);
                        Gdx.app.log("unit", "i see an enemy");

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
                        attackableUnits.add(nextTileDown.occupyingUnit);
                        nextTileDown.occupyingUnit.redColor();
                        nextTileDown.occupyingUnit.constructAndAddAttackListener(activeUnit);
                        Gdx.app.log("unit", "i see an enemy");

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
                        attackableUnits.add(nextTileUp.occupyingUnit);
                        nextTileUp.occupyingUnit.redColor();
                        nextTileUp.occupyingUnit.constructAndAddAttackListener(activeUnit);
                        Gdx.app.log("unit", "i see an enemy");

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

    private void runAI() {
        passPhase();
    }

    @Override
    public void show() {
        super.show();

        initializeVariables();
        DEBUGCHAR();
        DEBUGENEMY();

        layoutUI();

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

        hudStage.act();
        hudStage.draw();

        gameStage.act();
        gameStage.draw();
    }

    // --SETTERS--
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
    public Array<Unit> getEnemyTeam() {return enemyTeam;}
    public Array<Unit> getPlayerTeam() {return playerTeam;}
    public Array<Unit> getAllyTeam() {return  allyTeam;}
    public Array<Unit> getOtherTeam() {return  otherTeam;}



}
