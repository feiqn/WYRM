package com.feiqn.wyrm.models.unitdata;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.Inventory;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.phasedata.Phase;
import com.feiqn.wyrm.models.unitdata.classdata.UnitClass;
import com.feiqn.wyrm.models.weapondata.Weapon;
import com.feiqn.wyrm.models.weapondata.WeaponType;

public class Unit extends Image {
    // A classless unit with no weapons.

    public enum FacedDirection {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    public Array<WeaponType> usableWeaponTypes;

    public LogicalTile occupyingTile;

    private MovementType movementType;

    private boolean canStillMoveThisTurn;

    public String name;

    public Weapon equippedWeapon;

    private int movementSpeed,
                strength,
                defense,
                maxHP,
                currentHP,
                skill,
                speed,
                row,
                column;

    private final WYRMGame game;

    private final Unit self = this;

    protected FacedDirection facedDirection;

    protected TeamAlignment teamAlignment;

    protected UnitClass unitClass;

    protected Inventory inventory;

    public InputListener attackListener;

    public Unit(WYRMGame game) {
        super();
        this.game = game;
        sharedInit();
    }
    public Unit(WYRMGame game, Texture texture) {
        super(texture);
        this.game = game;
        sharedInit();
    }
    public Unit(WYRMGame game, TextureRegion region) {
        super(region);
        this.game = game;
        sharedInit();
    }

    private void sharedInit() {
        facedDirection = FacedDirection.SOUTH;

        name = "Timn";
        usableWeaponTypes = new Array<>();
        unitClass = UnitClass.DRAFTEE;
        inventory = new Inventory(game);
        occupyingTile = new LogicalTile(game, -1,-1);

        setSize(1,1);

        canStillMoveThisTurn = true;

        movementType = MovementType.INFANTRY;

        teamAlignment = TeamAlignment.ALLY;

        equippedWeapon = new Weapon(game);

        row = 0;
        column = 0;
        movementSpeed = 5;
        strength = 3;
        defense = 1;
        maxHP = 10;
        currentHP = maxHP;
        skill = 3;
        speed = 3;


        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(game.activeBattleScreen.currentPhase == Phase.PLAYER_PHASE) {
                    if(self.teamAlignment == TeamAlignment.PLAYER){
                        if(self.canMove()) {
                            game.activeBattleScreen.activeUnit = self;
                            game.activeBattleScreen.highlightAllTilesUnitCanAccess(self);
                        }
                    }
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {

            }
        });
    }

    public void constructAndAddAttackListener(final Unit attackingUnit) {
        attackListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(attackingUnit.canMove()) {
                    attackingUnit.toggleCanMove();
                }

                game.activeBattleScreen.removeTileHighlighters();
                game.activeBattleScreen.clearAttackableEnemies();

                game.activeBattleScreen.goToCombat(attackingUnit, self);

                game.activeBattleScreen.checkIfAllUnitsHaveMovedAndPhaseShouldChange(game.activeBattleScreen.currentTeam());

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
            }
        };

        self.addListener(attackListener);
    }

    public void removeAttackListener() {
        self.removeListener(attackListener);
    }

    public void dimColor() {
        self.setColor(.5f,.5f,.5f,1);
    }

    public void brightColor() {
        self.setColor(1.5f,1.5f,1.5f,1);
    }

    public void standardColor() {
        self.setColor(1,1,1,1);
    }

    public void redColor() {
        self.setColor(1,0,0,1);
    }

    public void kill() {
        this.remove();
        game.activeBattleScreen.logicalMap.getTileAtPosition(this.getRow(),this.getColumn()).occupyingUnit = null;
        game.activeBattleScreen.logicalMap.getTileAtPosition(this.getRow(),this.getColumn()).isOccupied = false;
        switch(teamAlignment) {
            case ENEMY:
                game.activeBattleScreen.removeUnitFromTeam(this,TeamAlignment.ENEMY);
                break;
            case PLAYER:
                game.activeBattleScreen.removeUnitFromTeam(this,TeamAlignment.PLAYER);
                break;
            case ALLY:
                game.activeBattleScreen.removeUnitFromTeam(this,TeamAlignment.ALLY);
                break;
            case OTHER:
                game.activeBattleScreen.removeUnitFromTeam(this,TeamAlignment.OTHER);
                break;
        }
    }

    // --SETTERS--
    public void setTeamAlignment(TeamAlignment newTeamAlignment) {this.teamAlignment = newTeamAlignment;}
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public void setFacedDirection(FacedDirection facedDirection) {
        this.facedDirection = facedDirection;
    }
    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }
    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }
    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }
    public void setSkill(int skill) {
        this.skill = skill;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    public void toggleCanMove() {
        canStillMoveThisTurn = !canStillMoveThisTurn;
        if(canStillMoveThisTurn) {
            standardColor();
        } else {
            dimColor();
        }
    }
    public void setCurrentHP(int newHP) {
        currentHP = newHP;
    }


    // --GETTERS--
    public int getCurrentHP() {return currentHP;}
    public boolean canMove() { return canStillMoveThisTurn; }
    public FacedDirection getFacedDirection() { return facedDirection; }
    public int getDefense() { return defense; }
    public int getMaxHP() { return maxHP; }
    public int getMovementSpeed() { return movementSpeed; }
    public int getSkill() { return skill; }
    public int getSpeed() { return speed; }
    public int getStrength() { return strength; }
    public MovementType getMovementType() { return movementType; }
    public int getColumn() { return column; }
    public int getRow() { return row; }
    public TeamAlignment getTeamAlignment() { return teamAlignment; }

}