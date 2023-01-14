package com.feiqn.wyrm.models.unitdata;

import com.badlogic.gdx.Gdx;
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
import com.feiqn.wyrm.models.unitdata.units.StatTypes;

import java.util.HashMap;
import java.util.Random;

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

    // TODO: weapon proficiency

    protected int level,
                movementSpeed,
                baseStrength,
                baseDefense,
                baseMaxHP,
                currentHP,
                baseSkill,
                baseSpeed,
                row,
                column,
                exp;

    protected HashMap<StatTypes, Float> growthRates;
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
        movementSpeed = 5; // TODO: derive from speed
        baseStrength = 3;
        baseDefense = 1;
        baseMaxHP = 10;
        currentHP = baseMaxHP;
        baseSkill = 3;
        baseSpeed = 3;
        exp = 0;

        growthRates = new HashMap<>();

        growthRates.put(StatTypes.SPEED, 0.5f);
        growthRates.put(StatTypes.STRENGTH, 0.5f);
        growthRates.put(StatTypes.DEFENSE, 0.5f);
        growthRates.put(StatTypes.SKILL, 0.5f);
        growthRates.put(StatTypes.HEALTH, 0.5f);

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
        try {
            self.removeListener(attackListener);
        } catch(Exception ignored) {}
    }

    public void levelUp() {
        Gdx.app.log("unit", "Level up!");

        this.level++;

        this.exp -= 100;
        final int remainder = this.exp;

        final Random random = new Random();

        final float growthChanceStr = random.nextFloat();
        Gdx.app.log("unit", "strength " + growthChanceStr);
        if(growthChanceStr < self.growthRates.get(StatTypes.STRENGTH)) {
            Gdx.app.log("unit", "Ye boi str go up!");
            this.baseStrength++;
            if(growthChanceStr < self.growthRates.get(StatTypes.STRENGTH) / 2) {
                Gdx.app.log("unit", "POG!");
                this.baseStrength++;
                if(growthChanceStr < self.growthRates.get(StatTypes.STRENGTH) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.baseStrength++;
                }
            }
        }

        final float growthChanceDef = random.nextFloat();
        Gdx.app.log("unit", "defense " + growthChanceDef);
        if(growthChanceDef < self.growthRates.get(StatTypes.DEFENSE)) {
            Gdx.app.log("unit", "Ye boi defense gone up!");
            this.baseDefense++;
            if(growthChanceDef < self.growthRates.get(StatTypes.DEFENSE) / 2) {
                Gdx.app.log("unit", "POG!");
                this.baseDefense++;
                if(growthChanceDef < self.growthRates.get(StatTypes.DEFENSE) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.baseDefense++;
                }
            }
        }

        final float growthChanceSkl = random.nextFloat();
        Gdx.app.log("unit", "skill " + growthChanceSkl);
        if(growthChanceSkl < self.growthRates.get(StatTypes.SKILL)) {
            Gdx.app.log("unit", "Ye boi skill get big!");
            this.baseSkill++;
            if(growthChanceSkl < self.growthRates.get(StatTypes.SKILL) / 2) {
                Gdx.app.log("unit", "POG!");
                this.baseSkill++;
                if(growthChanceSkl < self.growthRates.get(StatTypes.SKILL) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.baseSkill++;
                }
            }
        }

        final float growthChanceHP = random.nextFloat();
        Gdx.app.log("unit", "" + growthChanceHP);
        if(growthChanceHP < self.growthRates.get(StatTypes.HEALTH)) {
            Gdx.app.log("unit", "Ye boi defense gone up!");
            this.baseMaxHP++;
            if(growthChanceHP < self.growthRates.get(StatTypes.HEALTH) / 2) {
                Gdx.app.log("unit", "POG!");
                this.baseMaxHP++;
                if(growthChanceHP < self.growthRates.get(StatTypes.HEALTH) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.baseMaxHP++;
                }
            }
        }

        final float growthChanceSpd = random.nextFloat();
        Gdx.app.log("unit", "" + growthChanceSpd);
        if(growthChanceSpd < self.growthRates.get(StatTypes.SPEED)) {
            Gdx.app.log("unit", "Ye boi spd gone up!");
            this.baseSpeed++;
            if(growthChanceSpd < self.growthRates.get(StatTypes.SPEED) / 2) {
                Gdx.app.log("unit", "POG!");
                this.baseSpeed++;
                if(growthChanceSpd < self.growthRates.get(StatTypes.SPEED) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.baseSpeed++;
                }
            }
        }

        this.exp = 0;
        this.addExp(remainder);
    }

    public void addExp(int expGain) {
        this.exp += expGain;

        if(this.exp >= 100) {
            levelUp();
        }
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

    public int getHitRate() {
        return 100;
    }
    public int getEvade
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
    public int getBaseMovementSpeed() {
        // TODO: derive from speed
        return movementSpeed;
    }
    public int getBaseSkill() { return baseSkill; }
    public int getBaseSpeed() { return baseSpeed; }
    public int getBaseStrength() { return baseStrength; }
    public MovementType getMovementType() { return unitClass.movementType; }
    public int getColumn() { return column; }
    public int getRow() { return row; }
    public TeamAlignment getTeamAlignment() { return teamAlignment; }
    public Inventory getInventory() {return inventory;}

}