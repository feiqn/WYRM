package com.feiqn.wyrm.models.unitdata;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.Inventory;
import com.feiqn.wyrm.models.itemdata.Item;
import com.feiqn.wyrm.models.itemdata.ItemType;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.phasedata.Phase;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponType;
import com.feiqn.wyrm.models.unitdata.classdata.UnitClass;

public class Unit extends Image {
    // A classless unit with no weapons.

    public enum FacedDirection {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    public LogicalTile occupyingTile;
    private boolean canStillMoveThisTurn;
    public String name;
    public Item equippedWeapon;

    private int level,
                movementSpeed,
                baseStrength,
                baseDefense,
                baseMaxHP,
                currentHP,
                baseSkill,
                baseSpeed,
                row,
                column;

    public UnitRoster rosterID;
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

        name = "Mr. Timn";
        unitClass = new UnitClass(game);
        inventory = new Inventory(game);
        occupyingTile = new LogicalTile(game, -1,-1);

        setSize(1,1);

        canStillMoveThisTurn = true;
        teamAlignment = TeamAlignment.ALLY;
        equippedWeapon = new Item(game, ItemType.Weapon);
        rosterID = UnitRoster.MR_TIMN;

        level = 1;
        row = 0;
        column = 0;
        movementSpeed = 5;
        baseStrength = 3;
        baseDefense = 1;
        baseMaxHP = 10;
        currentHP = baseMaxHP;
        baseSkill = 3;
        baseSpeed = 3;


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

        this.redColor();
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
    public void setBaseDefense(int baseDefense) {
        this.baseDefense = baseDefense;
    }
    public void setFacedDirection(FacedDirection facedDirection) {
        this.facedDirection = facedDirection;
    }
    public void setBaseMaxHP(int baseMaxHP) {
        this.baseMaxHP = baseMaxHP;
    }
    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }
    public void setMovementType(MovementType movementType) {
        this.unitClass.movementType = movementType;
    }
    public void setBaseSkill(int baseSkill) {
        this.baseSkill = baseSkill;
    }
    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }
    public void setBaseStrength(int baseStrength) {
        this.baseStrength = baseStrength;
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

    public int getReach() {
        int reach = 1;
        for(Item item : inventory.items()) {
            if(item.itemType == ItemType.Weapon) {
                if(item.getRange() > reach) {
                    reach = item.getRange();
                }
            }
        }
        return reach;
    }
    public int getLevel() {return level;}
    public Item getEquippedWeapon() {
        if(equippedWeapon != null) {
            return equippedWeapon;
        } else {
            for(Item item : inventory.items()) {
                if(item.itemType == ItemType.Weapon){
                    return item;
                }
            }
        }
        return new Item(game, ItemType.Weapon);
    }
    public int getCurrentHP() {return currentHP;}
    public boolean canMove() { return canStillMoveThisTurn; }
    public FacedDirection getFacedDirection() { return facedDirection; }
    public int getModifiedStrength() {
        int modStr = baseStrength;
        modStr += getEquippedWeapon().getStrengthBonus();
        return modStr;
    }
    public int getModifiedDefense() {
        int modDef = baseDefense;
        modDef += getEquippedWeapon().getDefenseBonus();
        return modDef;
    }
    public int getModifiedSkill() {
        int modSkl = baseSkill;
        modSkl += getEquippedWeapon().getSkillBonus();
        return modSkl;
    }
    public int getModifiedMaxHP() {
        int modHP = baseMaxHP;
        modHP += getEquippedWeapon().getHealthBonus();
        return modHP;
    }
    public float getModifiedSpeed() {
        float modSpd = baseSpeed;
        modSpd -= getEquippedWeapon().getWeight();
        return modSpd;
    }
    public float getModifiedMovementSpeed() {
        float modMov = movementSpeed;
        modMov -= getEquippedWeapon().getWeight();
        return modMov;
    }
    public int getBaseDefense() { return baseDefense; }
    public int getBaseMaxHP() { return baseMaxHP; }
    public int getBaseMovementSpeed() { return movementSpeed; }
    public int getBaseSkill() { return baseSkill; }
    public int getBaseSpeed() { return baseSpeed; }
    public int getBaseStrength() { return baseStrength; }
    public MovementType getMovementType() { return unitClass.movementType; }
    public int getColumn() { return column; }
    public int getRow() { return row; }
    public TeamAlignment getTeamAlignment() { return teamAlignment; }

}