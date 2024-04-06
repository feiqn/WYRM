package com.feiqn.wyrm.models.mapdata.tiledata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.ui.popups.FieldActionsPopup;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.HashMap;

public class LogicalTile extends Actor {

    // Something something tiledmap.properties something something toby fox

    private final WYRMGame game;

    private Image highlightImage;

    // --UNITS--
    public Unit occupyingUnit;

    // --FLOATS--
    public float defenseValue,
                 visionReduction;

    // --ENUMS--
    public LogicalTileType tileType;

    // --HASHMAPS--
    protected HashMap<MovementType, Float> movementCost;

    // --INTS--
    public final int row,
                     column;

    public int evadeBonus,
               defenseBonus;

    // --VECTORS--
    public Vector2 coordinates;

    // --BOOLEANS--
    public boolean isOccupied,
                   isTraversableByBoats,
                   damagesBoats,
                   isTraversableByInfantry,
                   damagesInfantry,
                   isTraversableByFlyers,
                   damagesFlyers,
                   isTraversableByCavalry,
                   damagesCavalry,
                   isTraversableByWheels,
                   damagesWheels,
                   blocksLineOfSight;

    public LogicalTile(WYRMGame game, float column, float row) {
        super();
        this.game = game;
        this.row = (int) row;
        this.column = (int) column;
        this.coordinates = new Vector2(column,row);
        sharedInit();
    }

    public LogicalTile(WYRMGame game, Vector2 coordinates) {
        super();
        this.game = game;
        this.coordinates = coordinates;

        this.row = (int)coordinates.y;
        this.column = (int)coordinates.x;
        sharedInit();
    }

    private void sharedInit() {
        tileType = LogicalTileType.PLAINS;

        isTraversableByBoats = false;
        damagesBoats = false;
        isTraversableByCavalry = true;
        damagesCavalry = false;
        isTraversableByWheels = true;
        damagesWheels = false;
        isOccupied = false;
        isTraversableByInfantry = true;
        isTraversableByFlyers = true;
        damagesInfantry = false;
        damagesFlyers = false;
        blocksLineOfSight = false;
        defenseValue = 0;
        visionReduction = 0;

        evadeBonus = 0;
        defenseBonus = 0;

        movementCost = new HashMap<>();
        movementCost.put(MovementType.INFANTRY, 1f);
        movementCost.put(MovementType.FLYING, 1f);
        movementCost.put(MovementType.CAVALRY, 1f);
        movementCost.put(MovementType.WHEELS, 1.5f);
        movementCost.put(MovementType.SAILING, 999f);

        highlightImage = new Image();

        // clicklistener listen for enter? { update tileDataUILabel

        this.addListener(new ClickListener() {

            // todo: needs a defined tap square or something

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.app.log("enter", "entered!");
            }

            @Override
            public boolean isOver (Actor actor, float x, float y) {
                Gdx.app.log("over", "over!");
                return true;
            }

        });
    }

    public void setObjectiveUnit(UnitRoster unit) {

    }

    public boolean isTraversableByUnitType(MovementType type) {
        switch(type) {
            case FLYING:
                return isTraversableByFlyers;
            case WHEELS:
                return isTraversableByWheels;
            case CAVALRY:
                return isTraversableByCavalry;
            case SAILING:
                return isTraversableByBoats;
            case INFANTRY:
                return isTraversableByInfantry;
        }
        return false;
    }

    public float getMovementCostForMovementType(MovementType type){
        switch(type) {
            case INFANTRY:
                return movementCost.get(MovementType.INFANTRY);
            case SAILING:
                return movementCost.get(MovementType.SAILING);
            case CAVALRY:
                return movementCost.get(MovementType.CAVALRY);
            case WHEELS:
                return movementCost.get(MovementType.WHEELS);
            case FLYING:
                return movementCost.get(MovementType.FLYING);
            default:
                return 1f;
        }
    }

    public void highlightCanMove(final Unit movingUnit, final int originColumn, final int originRow, final TextureRegion region) {
        // add a blue highlight image with data and touch listener

//        final Texture blueSquareTexture = new Texture(Gdx.files.internal("ui/menu.png"));
//        final TextureRegion blueSquareRegion = new TextureRegion(blueSquareTexture,0,0,100,100);

        highlightImage = new Image(region);
        highlightImage.setSize(1,1);
        highlightImage.setPosition(coordinates.x, coordinates.y);
        highlightImage.setColor(1,1,1,.4f);

        highlightImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.activeBattleScreen.logicalMap.placeUnitAtPosition(movingUnit, (int) highlightImage.getY(), (int) highlightImage.getX());

                if(movingUnit.canMove()) {
                    movingUnit.toggleCanMove();
                }

                game.activeBattleScreen.removeTileHighlighters();
                game.activeBattleScreen.clearAttackableEnemies();

                final FieldActionsPopup fap = new FieldActionsPopup(game, movingUnit, x, y, originRow, originColumn);

                game.activeBattleScreen.uiGroup.addActor(fap);

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {

            }
        });

        game.activeBattleScreen.rootGroup.addActor(highlightImage);

    }

    public void highlightCanAttack(TextureRegion region) { //TODO: need to pass in some things for this
        // similar to above
        highlightImage = new Image(region);
        highlightImage.setColor(1, 0, 0, .4f);
        highlightImage.setSize(1,1);
        highlightImage.setPosition(coordinates.x, coordinates.y);

        game.activeBattleScreen.rootGroup.addActor(highlightImage);
    }

    public void highlightCanSupport() {
        final Texture t = new Texture(Gdx.files.internal("ui/menu.png")); // todo: asset handler
        final TextureRegion region = new TextureRegion(t,0,0,100,100);
        highlightImage = new Image(region);
        highlightImage.setColor(0, 1, 0, .4f);
        highlightImage.setSize(1,1);
        highlightImage.setPosition(coordinates.x, coordinates.y);

        game.activeBattleScreen.rootGroup.addActor(highlightImage);
    }

    public void clearHighlight() {
        highlightImage.remove();
        highlightImage = new Image();

    }
}
