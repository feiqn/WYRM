package com.feiqn.wyrm.models.mapdata.tiledata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.FieldActionsPopup;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.HashMap;

public class LogicalTile extends Image {

    // Something something tiledmap.properties something something toby fox

    private final WYRMGame game;

    // --UNITS--
    public SimpleUnit occupyingUnit;

    // --FLOATS--
    public float defenseValue,
                 visionReduction;

    // --ENUMS--
    public LogicalTileType tileType;

    // --HASHMAPS--
    protected HashMap<MovementType, Float> movementCost;

    // --INTS--
    private final int row,
                      column;

    public int evadeBonus,
               defenseBonus;

    // --VECTORS--
    private final Vector2 coordinates;

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

    private InputListener moveListener;

    private final LogicalTile self = this;


    public LogicalTile(WYRMGame game, Vector2 coordinates) {
        this(game, coordinates.x, coordinates.y);
    }

    public LogicalTile(WYRMGame game, float column, float row) {
        super(game.assetHandler.solidBlueTexture);
        this.game = game;
        this.row = (int) row;
        this.column = (int) column;
        this.coordinates = new Vector2(column,row);
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

        setSize(1,1);
        setPosition(coordinates.x, coordinates.y);

        this.addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                Gdx.app.log("enter", "entered!");
                game.activeGridScreen.hud().updateTilePanel(tileType);
            }

            @Override
            public boolean isOver (Actor actor, float x, float y) {
//                Gdx.app.log("over", "over!");
                return true;
            }

        });
    }

    public void setObjectiveUnit(UnitRoster unit) {

    }

    public boolean isTraversableByUnitType(MovementType type) {
        switch(type) {
            case FLYING:   return isTraversableByFlyers;
            case WHEELS:   return isTraversableByWheels;
            case CAVALRY:  return isTraversableByCavalry;
            case SAILING:  return isTraversableByBoats;
            case INFANTRY: return isTraversableByInfantry;
            default: throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public float getMovementCostForMovementType(MovementType type){
        switch (type) {
            case INFANTRY: return movementCost.get(MovementType.INFANTRY);
            case SAILING:  return movementCost.get(MovementType.SAILING);
            case CAVALRY:  return movementCost.get(MovementType.CAVALRY);
            case WHEELS:   return movementCost.get(MovementType.WHEELS);
            case FLYING:   return movementCost.get(MovementType.FLYING);
            default: throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public void highlightCanMove(final SimpleUnit movingUnit) {
        setColor(1,1,1,.4f);

        moveListener = (new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                game.activeGridScreen.getLogicalMap().moveAlongPath(movingUnit, game.activeGridScreen.getRecursionHandler().shortestPath(movingUnit, self, true));

                game.activeGridScreen.removeTileHighlighters();
                game.activeGridScreen.clearAttackableEnemies();
            }

        });

        addListener(moveListener);

        game.activeGridScreen.rootGroup.addActor(this);

    }

    public void highlightCanAttack() {
        //TODO: need to pass in some things for this
        // similar to above
//        highlightImage = new Image(region);
//        highlightImage.setColor(1, 0, 0, .4f);
//        highlightImage.setSize(1,1);
//        highlightImage.setPosition(coordinates.x, coordinates.y);
//
//        game.activeBattleScreen.rootGroup.addActor(highlightImage);
    }

    public void highlightCanSupport() {
//        final Texture t = new Texture(Gdx.files.internal("ui/menu.png")); // todo: asset handler
//        final TextureRegion region = new TextureRegion(t,0,0,100,100);
//        highlightImage = new Image(region);
//        highlightImage.setColor(0, 1, 0, .4f);
//        highlightImage.setSize(1,1);
//        highlightImage.setPosition(coordinates.x, coordinates.y);
//
//        game.activeBattleScreen.rootGroup.addActor(highlightImage);
    }

    public void clearHighlight() {
//        setColor(1,1,1,0);
        this.remove();
        this.removeListener(moveListener);
    }

    public void setUnoccupied() {
        occupyingUnit = null;
        isOccupied = false;
    }

    public void highlight() {
        setColor(Color.YELLOW);
        game.activeGridScreen.rootGroup.addActor(this);
    }

    // --GETTERS--
    public Vector2 getCoordinates() {return coordinates;}
    public int getRow() {return row;}
    public int getColumn() {return column;}
}
