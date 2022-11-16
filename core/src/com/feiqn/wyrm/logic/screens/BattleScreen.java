package com.feiqn.wyrm.logic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.prefabLogicalMaps.stage_debug;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.phasedata.Phase;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.feiqn.wyrm.models.mapdata.WyrMap;
import jdk.internal.jline.internal.Log;

public class BattleScreen extends ScreenAdapter {

    // --VARIABLES--
    // --GAME--
    private final WYRMGame game;

    // --MAP--
    public WyrMap logicalMap;

    // --CAMERA--
    public OrthographicCamera gameCamera;

    // --TILED--
    public TiledMap battleMap;
    public OrthogonalTiledMapRenderer orthoMapRenderer;

    public TiledMapTileLayer groundLayer;

    // --STAGE--
    public Stage stage;

    // --GROUPS--

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
    private Array<LogicalTile> reachableTiles;
    private Array<Unit> playerTeam;
    private Array<Unit> enemyTeam;
    private Array<Unit> allyTeam;
    private Array<Unit> otherTeam;

    // --ENUMS--
    private Phase currentPhase;

    public BattleScreen(WYRMGame game) {
        this.game = game;
    }

    private void initializeVariables() {
        keyPressed_A = false;
        keyPressed_D = false;
        keyPressed_S = false;
        keyPressed_W = false;
        allyTeamUsed = false;
        otherTeamUsed = false;

        playerTeam = new Array<>();
        enemyTeam = new Array<>();
        allyTeam = new Array<>();
        otherTeam = new Array<>();

        gameCamera = new OrthographicCamera();

        reachableTiles = new Array<>();

        battleMap        = new TmxMapLoader().load("test/wyrmDebugMap.tmx");
        orthoMapRenderer = new OrthogonalTiledMapRenderer(battleMap, 1/16f);

        final float worldWidth  = Gdx.graphics.getWidth() / 16f;
        final float worldHeight = Gdx.graphics.getHeight() / 16f;
        gameCamera.setToOrtho(false, worldWidth , worldHeight);
        gameCamera.update();

//        final ScalingViewport viewport = new ScalingViewport(Scaling.stretch, worldWidth, worldHeight);
        final FitViewport viewport = new FitViewport(worldWidth, worldHeight);

        stage = new Stage(viewport);

//        gameCamera.position.set(rootGroup.getX(),rootGroup.getY(),rootGroup.getZIndex());

        logicalMap = new stage_debug(game);

        Gdx.input.setInputProcessor(stage);
        stage.setDebugAll(true); // debug

        passPhaseToTeam(TeamAlignment.PLAYER);
    }

    private void passPhase() {
        // By default, turn order is as follows:

        // PLAYER -> ENEMY -> ALLY -> OTHER -> PLAYER

        // Calling this function will pass the turn as normal,
        // or you can manually assign turns (I.E., to give the
        // enemy more chances to move on a certain mission, etc.)
        // via passPhaseToTeam(), which this function simply wraps
        // for convenience.

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
            if(!unit.canMove()) {
                unit.toggleCanMove();
                if(unit.getTeamAlignment() == TeamAlignment.PLAYER) {
                    unit.setColor(0, 0, 0, 1);
                }
            }
        }
    }

    private void DEBUGCHAR() {
        final Texture debugCharTexture = new Texture(Gdx.files.internal("test/test_character.png"));
        final TextureRegion debugCharRegion = new TextureRegion(debugCharTexture,0,0,128,160);

        final Unit testChar = new Unit(game, debugCharRegion);
        testChar.setSize(1,1);

        logicalMap.placeUnitAtPosition(testChar, 7, 3);

        testChar.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("I'm at", "row " + testChar.getRow() + " , column " + testChar.getColumn());
                Gdx.app.log("My position is", "x " + testChar.getX() + " , y " + testChar.getY());
                if(testChar.canMove()) {
                    highlightAllTilesUnitCanMoveTo(testChar);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {

            }
        });

        playerTeam.add(testChar);
        stage.addActor(testChar);
    }

    private void DEBUGENEMY() {
        final Texture debugCharTexture = new Texture(Gdx.files.internal("test/test_character.png"));
        final TextureRegion debugCharRegion = new TextureRegion(debugCharTexture,0,0,128,160);

        final Unit testEnemy = new Unit(game, debugCharRegion);
        testEnemy.setSize(1,1);
        testEnemy.setColor(Color.RED);
        testEnemy.setTeamAlignment(TeamAlignment.ENEMY);

        logicalMap.placeUnitAtPosition(testEnemy, 3, 7);

        enemyTeam.add(testEnemy);
        stage.addActor(testEnemy);
    }

    public void highlightAllTilesUnitCanMoveTo(final Unit unit) {
        reachableTiles = new Array<>();

        recursivelySelectReachableTiles(unit);

        final Texture debugCharTexture = new Texture(Gdx.files.internal("test/test_character.png"));
        final TextureRegion debugCharRegion = new TextureRegion(debugCharTexture,0,0,128,160);

        final Array<Image> tileHighlighters = new Array<>();

        for(final LogicalTile tile : reachableTiles) {
            if (!tile.isOccupied) {
                final Image highlightImage = new Image(debugCharRegion);
                highlightImage.setSize(1, 1);
                highlightImage.setPosition(tile.coordinates.x, tile.coordinates.y);
                highlightImage.setColor(.5f, .5f, .5f, .5f);

                tileHighlighters.add(highlightImage);

                highlightImage.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        logicalMap.placeUnitAtPosition(unit, (int) highlightImage.getY(), (int) highlightImage.getX());
                        for (Image image : tileHighlighters) {
                            image.remove();
                        }

                        final Image menuImageWait = new Image(debugCharRegion);
                        menuImageWait.setSize(2, 2);
                        menuImageWait.setColor(0, 0, 1, 1);

                        menuImageWait.addListener(new InputListener() {
                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                unit.toggleCanMove();

                                menuImageWait.remove();
                                checkIfAllUnitsHaveMovedAndPhaseShouldChange(currentTeam());
                                return true;
                            }

                            @Override
                            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                            }
                        });

                        stage.addActor(menuImageWait);

                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int point, int button) {

                    }
                });

                stage.addActor(highlightImage);
            } else if (tile.occupyingUnit.getTeamAlignment() == TeamAlignment.ENEMY) {
                tile.occupyingUnit.setColor(1,0,0,0.5f);
                Gdx.app.log("unit", "i see an enemy");
                tile.occupyingUnit.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        goToCombat(unit, tile.occupyingUnit);
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int point, int button) {}

                });
            }
        }
    }

    private Array<LogicalTile> shortestPath(LogicalTile origin, LogicalTile destination, Array<LogicalTile> reachableTiles) {
        final Array<LogicalTile> shortestPath = new Array<>();


        return shortestPath;
    }

    private void goToCombat(Unit attacker, Unit defender){
        defender.remove();
    }

    private void checkIfAllUnitsHaveMovedAndPhaseShouldChange(Array<Unit> team) {
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

    private Array<Unit> currentTeam() {
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

    private void recursivelySelectReachableTiles(Unit unit) {
        recursivelySelectReachableTiles(unit.getRow(), unit.getColumn(), unit.getSpeed(), unit.getMovementType());
    }
    private void recursivelySelectReachableTiles(int startX, int startY, float speed, MovementType movementType) {
        // Called by highlightAllTilesUnitCanReach()
        // Selects all the tiles within distance speed of selected tile.

        while(speed >= 1) {

            try {
                final int newX = startX -1;
                final Vector2 nextPos = new Vector2(newX, startY);
                final LogicalTile nextTile = logicalMap.getTileAtPosition(nextPos);

                if(nextPos.x >= 0) {
                    if(/* !reachableTiles.contains(nextTile, true) && */ /* !nextTile.isOccupied && */ nextTile.isTraversableByUnitType(movementType)) {
                        if(!reachableTiles.contains(nextTile, true)) {
                            reachableTiles.add(nextTile);
                        }
                        recursivelySelectReachableTiles(newX, startY, speed - nextTile.getMovementCostForMovementType(movementType), movementType);
                    }
                }

            } catch (Exception ignored) {}

            try {
                final int newX = startX + 1;
                final Vector2 nextPos = new Vector2(newX, startY);
                final LogicalTile nextTile = logicalMap.getTileAtPosition(nextPos);

                if(nextPos.x < logicalMap.getTilesWide()) {
                    if(/* !reachableTiles.contains(nextTile, true) && */ /* !nextTile.isOccupied && */ nextTile.isTraversableByUnitType(movementType)) {
                        if(!reachableTiles.contains(nextTile, true)) {
                            reachableTiles.add(nextTile);
                        }
                        recursivelySelectReachableTiles(newX, startY, speed - nextTile.getMovementCostForMovementType(movementType), movementType);
                    }
                }

            } catch (Exception ignored) {}

            try {
                final int newY = startY - 1;
                final Vector2 nextPos = new Vector2(startX, newY);
                final LogicalTile nextTile = logicalMap.getTileAtPosition(nextPos);

                if(nextPos.y >= 0) {
                    if(/* !reachableTiles.contains(nextTile, true) && */ /* !nextTile.isOccupied && */ nextTile.isTraversableByUnitType(movementType)) {
                        if(!reachableTiles.contains(nextTile, true)) {
                            reachableTiles.add(nextTile);
                        }
                        recursivelySelectReachableTiles(startX, newY, speed - nextTile.getMovementCostForMovementType(movementType), movementType);
                    }
                }

            } catch (Exception ignored) {}

            try {
                final int newY = startY + 1;
                final Vector2 nextPos = new Vector2(startX, newY);
                final LogicalTile nextTile = logicalMap.getTileAtPosition(nextPos);

                if(nextPos.y < logicalMap.getTilesHigh()) {
                    if(/* !reachableTiles.contains(nextTile, true)  && */ /* !nextTile.isOccupied && */ nextTile.isTraversableByUnitType(movementType)) {
                        if(!reachableTiles.contains(nextTile, true)) {
                            reachableTiles.add(nextTile);
                        }
                        recursivelySelectReachableTiles(startX, newY, speed - nextTile.getMovementCostForMovementType(movementType), movementType);
                    }
                }
            } catch (Exception ignored) {}

            speed--;
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
        Gdx.input.setInputProcessor(stage);
        gameCamera.update();

        stage.setDebugAll(true);
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

        stage.act();
        stage.draw();
    }

}
