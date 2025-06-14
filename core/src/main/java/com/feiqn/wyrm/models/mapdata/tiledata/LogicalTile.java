package com.feiqn.wyrm.models.mapdata.tiledata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
import com.feiqn.wyrm.models.unitdata.MovementType;

import java.util.HashMap;

public class LogicalTile extends Image {

    // Something something tiledmap.properties something something toby fox

    protected final WYRMGame game;

    // --UNITS--
    protected SimpleUnit occupyingUnit;

    // --FLOATS--
    public float defenseValue,
                 visionReduction;

    // --ENUMS--
    public LogicalTileType tileType;

    // --HASHMAPS--
    protected HashMap<MovementType, Float> movementCost;

    // --INTS--
    private final int rowY,
                      columnX;

    public int evadeBonus,
               defenseBonus;

    // --VECTORS--

    // --BOOLEANS--
    protected boolean isOccupied,
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

    protected InputListener moveListener;

    protected final LogicalTile self = this;
//
//    public LogicalTile(LogicalTile mirror) {
//
//    }

//    public LogicalTile(WYRMGame game, Vector2 coordinates) {
//        this(game, coordinates.x, coordinates.y);
//    }

    public LogicalTile(WYRMGame game, float columnXRight, float rowYUp) {
        super(game.assetHandler.solidBlueTexture);
        this.game = game;
        this.rowY = (int) rowYUp;
        this.columnX = (int) columnXRight;
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
        setPosition(columnX, rowY);

        this.addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                Gdx.app.log("enter", "entered!");
                game.activeGridScreen.hud().updateTilePanel(tileType);
            }
//
//            @Override
//            public boolean isOver (Actor actor, float x, float y) {
////                Gdx.app.log("over", "over!");
//                return true;
//            }

        });
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
        setColor(0,1,0,.6f);

        game.activeGridScreen.rootGroup.addActor(this);
    }

    public void clearHighlight() {
        this.remove();
        try {
            this.removeListener(moveListener);
        } catch (Exception ignored) {}
    }


    public void highlight() {
        setColor(.85f,.75f,.65f,.4f);
        game.activeGridScreen.rootGroup.addActor(this);
    }

    public void occupy(SimpleUnit unit) {
        occupyingUnit = unit;
        isOccupied = true;
    }

    public void setUnoccupied() {
        occupyingUnit = null;
        isOccupied = false;
    }

    // --GETTERS--
    public Vector2 getCoordinatesXY() {return new Vector2(columnX, rowY);}
    public int getRowY() {return rowY;}
    public int getColumnX() {return columnX;}
    public SimpleUnit getOccupyingUnit() {return  occupyingUnit;}
    public boolean isOccupied() {return isOccupied;}
}

