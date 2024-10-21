package com.feiqn.wyrm.models.unitdata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.AIType;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.popups.BallistaActionsPopup;
import com.feiqn.wyrm.models.itemdata.Inventory;
import com.feiqn.wyrm.models.itemdata.Item;
import com.feiqn.wyrm.models.itemdata.ItemType;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponLevel;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponType;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapobjectdata.MapObject;
import com.feiqn.wyrm.models.mapobjectdata.ObjectType;
import com.feiqn.wyrm.models.phasedata.Phase;
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

    protected AIType aiType;
    public LogicalTile occupyingTile;
    public MapObject occupyingMapObject;
    private boolean canStillMoveThisTurn;
    protected boolean isABoss;
    public String name;
    public Item equippedWeapon;
    public Boolean isOccupyingMapObject;

    // TODO: weapon proficiency

    protected int level,
                mobility,
                baseStrength,
                baseDefense,
                baseMaxHP,
                currentHP,
                baseDexterity,
                baseSpeed,
                row,
                column,
                exp,
                constitution; // con represents a units static overall size 1-10 and does not change with growths outside of class change

    protected HashMap<StatTypes, Float> growthRates;
    protected HashMap<WeaponType, WeaponLevel> weaponProficiencyLevels;
    protected HashMap<WeaponType, Integer> weaponProficiencyExp;
    public UnitRoster rosterID;
    protected final WYRMGame game;
    private final Unit self = this;
    protected FacedDirection facedDirection;
    protected TeamAlignment teamAlignment;
    protected UnitClass unitClass;
    protected Inventory inventory;
    public InputListener attackListener;
    protected TextureRegion thumbnail;

    public Unit(WYRMGame game) {
        super();
        this.game = game;
        sharedInit();
    }
    public Unit(WYRMGame game, Texture texture) {
        super(texture);
        this.game = game;

        // TODO: wrap texture to region. below implementation does nothing
        thumbnail = new TextureRegion(texture); // TODO: eventually this will be different from map sprite image

        sharedInit();
    }
    public Unit(WYRMGame game, TextureRegion region) {
        super(region);
        this.game = game;

        thumbnail = new TextureRegion(region);

        sharedInit();
    }

    private void sharedInit() {
        facedDirection = FacedDirection.SOUTH;

        name = "Mr. Timn";
        unitClass = new UnitClass(game);
        inventory = new Inventory(game, self);
        occupyingTile = new LogicalTile(game, -1,-1);
        isOccupyingMapObject = false;

        setSize(1,1);

        aiType = AIType.STILL;

        canStillMoveThisTurn = true;
        teamAlignment = TeamAlignment.ALLY;
        equippedWeapon = new Item(game, ItemType.Weapon);
        rosterID = UnitRoster.MR_TIMN;
        isABoss = false;

        level = 1;
        row = 0;
        column = 0;
        mobility = 5; // TODO: derive from speed?
        baseStrength = 3;
        baseDefense = 1;
        baseMaxHP = 10;
        currentHP = baseMaxHP;
        baseDexterity = 3;
        baseSpeed = 3;
        exp = 0;
        constitution = 5;

        this.unitClass = new UnitClass(game); // default is DRAFTEE

        growthRates = new HashMap<>();
        growthRates.put(StatTypes.SPEED, 0.5f);
        growthRates.put(StatTypes.STRENGTH, 0.5f);
        growthRates.put(StatTypes.DEFENSE, 0.5f);
        growthRates.put(StatTypes.DEXTERITY, 0.5f);
        growthRates.put(StatTypes.HEALTH, 0.5f);

        weaponProficiencyLevels = new HashMap<>();
        weaponProficiencyLevels.put(WeaponType.AXE, WeaponLevel.F);
        weaponProficiencyLevels.put(WeaponType.LANCE, WeaponLevel.F);
        weaponProficiencyLevels.put(WeaponType.SWORD, WeaponLevel.F);
        weaponProficiencyLevels.put(WeaponType.BOW, WeaponLevel.F);
        weaponProficiencyLevels.put(WeaponType.HANDS, WeaponLevel.F);
        weaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponLevel.F);
        weaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponLevel.F);
        weaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponLevel.F);
        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponLevel.F);
        weaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponLevel.F);
        weaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponLevel.F);

        weaponProficiencyExp = new HashMap<>();
        weaponProficiencyExp.put(WeaponType.AXE, 0);
        weaponProficiencyExp.put(WeaponType.LANCE, 0);
        weaponProficiencyExp.put(WeaponType.SWORD, 0);
        weaponProficiencyExp.put(WeaponType.BOW, 0);
        weaponProficiencyExp.put(WeaponType.HANDS, 0);
        weaponProficiencyExp.put(WeaponType.MAGE_LIGHT, 0);
        weaponProficiencyExp.put(WeaponType.MAGE_DARK, 0);
        weaponProficiencyExp.put(WeaponType.MAGE_ANIMA, 0);
        weaponProficiencyExp.put(WeaponType.SHIELD, 0);
        weaponProficiencyExp.put(WeaponType.HERBAL_POTION, 0);
        weaponProficiencyExp.put(WeaponType.HERBAL_FLORAL, 0);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(game.activeBattleScreen.currentPhase == Phase.PLAYER_PHASE) {
                    // Only allow input during player phase
                    if(self.teamAlignment == TeamAlignment.PLAYER){
                        // Unit is player's own unit
                        if(game.activeBattleScreen.activeUnit == null) {
                            // Haven't already selected another unit
                            if(self.canMove()) {
                                if(!isOccupyingMapObject) {
                                    game.activeBattleScreen.activeUnit = self;

                                    game.activeBattleScreen.highlightAllTilesUnitCanAccess(self);
                                } else if(occupyingMapObject.objectType == ObjectType.BALLISTA){
                                    // TODO: contextual responses when occupying objects such as ballista

                                    final BallistaActionsPopup bap = new BallistaActionsPopup(game, self, occupyingMapObject);
                                    game.activeBattleScreen.uiGroup.addActor(bap);
                                }
                            }
                        }
                    } // TODO: contextual options for if unit is enemy, ally, other
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {

            }
        });
        addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                game.activeBattleScreen.addHoveredUnitInfoPanel(self);
                game.activeBattleScreen.hoveredUnit = self;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                game.activeBattleScreen.removeHoveredUnitInfoPanel();
                game.activeBattleScreen.hoveredUnit = null;
            }

        });
    }

    public void constructAndAddAttackListener(final Unit attackingUnit) {

        this.redColor();
        attackListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setCannotMove();

                game.activeBattleScreen.removeTileHighlighters();
                game.activeBattleScreen.clearAttackableEnemies();

                game.activeBattleScreen.combatHandler.goToCombat(attackingUnit, self);

                game.activeBattleScreen.checkIfAllUnitsHaveMovedAndPhaseShouldChange();

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
        if(growthChanceSkl < self.growthRates.get(StatTypes.DEXTERITY)) {
            Gdx.app.log("unit", "Ye boi skill get big!");
            this.baseDexterity++;
            if(growthChanceSkl < self.growthRates.get(StatTypes.DEXTERITY) / 2) {
                Gdx.app.log("unit", "POG!");
                this.baseDexterity++;
                if(growthChanceSkl < self.growthRates.get(StatTypes.DEXTERITY) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.baseDexterity++;
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
        Gdx.app.log("leveler", "recycling  " + remainder + " exp");
        this.addExp(remainder);
    }

    public void addExp(int expGain) {
        this.exp += expGain;

        Gdx.app.log("Unit", "gained " + expGain + " exp");

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

    // --SETTERS & INCREMENTS--
    public void enterMapObject(MapObject object) {
        isOccupyingMapObject = true;
        occupyingMapObject = object;
    }
    public void leaveMapObject() {
        isOccupyingMapObject = false;
        occupyingMapObject = null;

        // todo: respawn left object (i.e., ballista) on map under unit
    }
    public void setBossStatus(boolean status) { isABoss = status; }
    public void setAIType(AIType newType) {
        this.aiType = newType;
    }
    public void setUnitClass(UnitClass unitClass) {
        this.unitClass = unitClass;
    }
    private void increaseWeaponProficiency(WeaponType type) {
        switch(type) {
            case AXE:

                final int remainder_axe = weaponProficiencyExp.get(WeaponType.AXE) - 100;

                switch(weaponProficiencyLevels.get(WeaponType.AXE)) {
                    case S:
                        break;
                    case A:
                        weaponProficiencyLevels.put(WeaponType.AXE, WeaponLevel.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.AXE, WeaponLevel.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.AXE, WeaponLevel.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.AXE, WeaponLevel.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.AXE, WeaponLevel.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.AXE, WeaponLevel.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.AXE, remainder_axe);
                break;

            case BOW:

                final int remainder_bow = weaponProficiencyExp.get(WeaponType.BOW) - 100;

                switch(weaponProficiencyLevels.get(WeaponType.BOW)) {
                    case S:
                        break;
                    case A:
                        weaponProficiencyLevels.put(WeaponType.BOW, WeaponLevel.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.BOW, WeaponLevel.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.BOW, WeaponLevel.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.BOW, WeaponLevel.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.BOW, WeaponLevel.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.BOW, WeaponLevel.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.BOW, remainder_bow);
                break;

            case LANCE:

                final int remainder_lance = weaponProficiencyExp.get(WeaponType.LANCE) - 100;

                switch(weaponProficiencyLevels.get(WeaponType.LANCE)) {
                    case S:
                        break;
                    case A:
                        weaponProficiencyLevels.put(WeaponType.LANCE, WeaponLevel.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.LANCE, WeaponLevel.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.LANCE, WeaponLevel.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.LANCE, WeaponLevel.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.LANCE, WeaponLevel.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.LANCE, WeaponLevel.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.LANCE, remainder_lance);
                break;

            case SWORD:

                final int remainder_sword = weaponProficiencyExp.get(WeaponType.SWORD) - 100;

                switch(weaponProficiencyLevels.get(WeaponType.SWORD)) {
                    case S:
                        break;
                    case A:
                        weaponProficiencyLevels.put(WeaponType.SWORD, WeaponLevel.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.SWORD, WeaponLevel.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.SWORD, WeaponLevel.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.SWORD, WeaponLevel.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.SWORD, WeaponLevel.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.SWORD, WeaponLevel.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.SWORD, remainder_sword);
                break;

            case SHIELD:

                final int remainder_shield = weaponProficiencyExp.get(WeaponType.SHIELD) - 100;

                switch(weaponProficiencyLevels.get(WeaponType.SHIELD)) {
                    case S:
                        break;
                    case A:
                        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponLevel.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponLevel.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponLevel.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponLevel.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponLevel.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponLevel.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.SHIELD, remainder_shield);
                break;

            case MAGE_DARK:

                final int remainder_dark = weaponProficiencyExp.get(WeaponType.MAGE_DARK) - 100;

                switch(weaponProficiencyLevels.get(WeaponType.MAGE_DARK)) {
                    case S:
                        break;
                    case A:
                        weaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponLevel.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponLevel.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponLevel.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponLevel.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponLevel.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponLevel.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.MAGE_DARK, remainder_dark);
                break;

            case MAGE_ANIMA:

                final int remainder_anima = weaponProficiencyExp.get(WeaponType.MAGE_ANIMA) - 100;

                switch(weaponProficiencyLevels.get(WeaponType.MAGE_ANIMA)) {
                    case S:
                        break;
                    case A:
                        weaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponLevel.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponLevel.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponLevel.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponLevel.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponLevel.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponLevel.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.MAGE_ANIMA, remainder_anima);
                break;

            case MAGE_LIGHT:

                final int remainder_light = weaponProficiencyExp.get(WeaponType.MAGE_LIGHT) - 100;

                switch(weaponProficiencyLevels.get(WeaponType.MAGE_LIGHT)) {
                    case S:
                        break;
                    case A:
                        weaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponLevel.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponLevel.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponLevel.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponLevel.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponLevel.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponLevel.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.MAGE_LIGHT, remainder_light);
                break;

            case HERBAL_FLORAL:

                final int remainder_floral = weaponProficiencyExp.get(WeaponType.HERBAL_FLORAL) - 100;

                switch(weaponProficiencyLevels.get(WeaponType.HERBAL_FLORAL)) {
                    case S:
                        break;
                    case A:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponLevel.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponLevel.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponLevel.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponLevel.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponLevel.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponLevel.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.HERBAL_FLORAL, remainder_floral);
                break;

            case HERBAL_POTION:

                final int remainder_potions = weaponProficiencyExp.get(WeaponType.HERBAL_POTION) - 100;

                switch(weaponProficiencyLevels.get(WeaponType.HERBAL_POTION)) {
                    case S:
                        break;
                    case A:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponLevel.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponLevel.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponLevel.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponLevel.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponLevel.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponLevel.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.HERBAL_POTION, remainder_potions);
                break;

            case HANDS:

                final int remainder_hands = weaponProficiencyExp.get(WeaponType.HANDS) - 100;

                switch(weaponProficiencyLevels.get(WeaponType.HANDS)) {
                    case S:
                        break;
                    case A:
                        weaponProficiencyLevels.put(WeaponType.HANDS, WeaponLevel.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.HANDS, WeaponLevel.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.HANDS, WeaponLevel.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.HANDS, WeaponLevel.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.HANDS, WeaponLevel.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.HANDS, WeaponLevel.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.HANDS, remainder_hands);
                break;
        }
    }
    public void addWeaponProficiencyExp(WeaponType type, int exp) {
        switch(type) {
            case LANCE:
                weaponProficiencyExp.put(WeaponType.LANCE, weaponProficiencyExp.get(WeaponType.LANCE) + exp);
                if(weaponProficiencyExp.get(WeaponType.LANCE) >= 100) {
                    increaseWeaponProficiency(WeaponType.LANCE);
                }
                break;
            case BOW:
                weaponProficiencyExp.put(WeaponType.BOW, weaponProficiencyExp.get(WeaponType.BOW) + exp);
                if(weaponProficiencyExp.get(WeaponType.BOW) >= 100) {
                    increaseWeaponProficiency(WeaponType.BOW);
                }
                break;
            case AXE:
                weaponProficiencyExp.put(WeaponType.AXE, weaponProficiencyExp.get(WeaponType.AXE) + exp);
                if(weaponProficiencyExp.get(WeaponType.AXE) >= 100) {
                    increaseWeaponProficiency(WeaponType.AXE);
                }
                break;
            case SWORD:
                weaponProficiencyExp.put(WeaponType.SWORD, weaponProficiencyExp.get(WeaponType.SWORD) + exp);
                if(weaponProficiencyExp.get(WeaponType.SWORD) >= 100) {
                    increaseWeaponProficiency(WeaponType.SWORD);
                }
                break;
            case SHIELD:
                weaponProficiencyExp.put(WeaponType.SHIELD, weaponProficiencyExp.get(WeaponType.SHIELD) + exp);
                if(weaponProficiencyExp.get(WeaponType.SHIELD) >= 100) {
                    increaseWeaponProficiency(WeaponType.SHIELD);
                }
                break;
            case MAGE_DARK:
                weaponProficiencyExp.put(WeaponType.MAGE_DARK, weaponProficiencyExp.get(WeaponType.MAGE_DARK) + exp);
                if(weaponProficiencyExp.get(WeaponType.MAGE_DARK) >= 100) {
                    increaseWeaponProficiency(WeaponType.MAGE_DARK);
                }
                break;
            case MAGE_ANIMA:
                weaponProficiencyExp.put(WeaponType.MAGE_ANIMA, weaponProficiencyExp.get(WeaponType.MAGE_ANIMA) + exp);
                if(weaponProficiencyExp.get(WeaponType.MAGE_ANIMA) >= 100) {
                    increaseWeaponProficiency(WeaponType.MAGE_ANIMA);
                }
                break;
            case MAGE_LIGHT:
                weaponProficiencyExp.put(WeaponType.MAGE_LIGHT, weaponProficiencyExp.get(WeaponType.MAGE_LIGHT) + exp);
                if(weaponProficiencyExp.get(WeaponType.MAGE_LIGHT) >= 100) {
                    increaseWeaponProficiency(WeaponType.MAGE_LIGHT);
                }
                break;
            case HERBAL_FLORAL:
                weaponProficiencyExp.put(WeaponType.HERBAL_FLORAL, weaponProficiencyExp.get(WeaponType.HERBAL_FLORAL) + exp);
                if(weaponProficiencyExp.get(WeaponType.HERBAL_FLORAL) >= 100) {
                    increaseWeaponProficiency(WeaponType.HERBAL_FLORAL);
                }
                break;
            case HERBAL_POTION:
                weaponProficiencyExp.put(WeaponType.HERBAL_POTION, weaponProficiencyExp.get(WeaponType.HERBAL_POTION) + exp);
                if(weaponProficiencyExp.get(WeaponType.HERBAL_POTION) >= 100) {
                    increaseWeaponProficiency(WeaponType.HERBAL_POTION);
                }
                break;
            case HANDS:
                weaponProficiencyExp.put(WeaponType.HANDS, weaponProficiencyExp.get(WeaponType.HANDS) + exp);
                if(weaponProficiencyExp.get(WeaponType.HANDS) >= 100) {
                    increaseWeaponProficiency(WeaponType.HANDS);
                }
                break;

        }
    }
    public void dimColor() {
        self.setColor(.5f,.5f,.5f,1);
    }
    public void brightColor() {
        self.setColor(1.5f,1.5f,1.5f,1);
    }
    public void standardColor() {

        self.setColor(1,1,1,1);

        switch(teamAlignment) {
            case ENEMY:
                self.setColor(Color.RED);
                break;
            case ALLY:
                self.setColor(Color.GREEN);
                break;
            case OTHER:
                self.setColor(Color.GRAY);
                break;
            case PLAYER:
            default:
                break;
        }


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
    public void setMobility(int mobility) {
        this.mobility = mobility;
    }
    public void setBaseDexterity(int baseDexterity) {
        this.baseDexterity = baseDexterity;
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
    public void setCannotMove() {
        canStillMoveThisTurn = false;
        dimColor();
    }
    public void setCanMove() {
        canStillMoveThisTurn = true;
        standardColor();
    }
    public void setCurrentHP(int newHP) {
        currentHP = newHP;
    }

    // --GETTERS--
    public boolean isABoss() { return isABoss; }
    public AIType getAiType() { return aiType; }
    public MapObject getOccupyingMapObject() {
        return occupyingMapObject;
    }
    public int getHitRate() {
        // do i want luck to exist in this game?

        return (getModifiedDexterity() * 2) + getEquippedWeapon().getWeaponAccuracy();
    }
    public int getEvade(){
        return (getAttackSpeed() * 2) + occupyingTile.evadeBonus;
    }
    public int getAttackSpeed() {
        return getModifiedSpeed() - getBurden();
    }
    public int getBurden() {
        int burden = 0;
        for(Item item : inventory.items()) {
            burden+=item.getWeight();
        }
        burden -= constitution;
        if(burden < 0) {
            burden = 0;
        }
        return burden;
    }
    public int getReach() {

        int reach;

        if(isOccupyingMapObject) {
            reach = occupyingMapObject.reach;
        } else {
            reach = 1;
            for(Item item : inventory.items()) {
                if(item.itemType == ItemType.Weapon) {
                    if(item.getRange() > reach) {
                        reach = item.getRange();
                    }
                }
            }
        }
        return reach;

    }
    public int getLevel() {return level;}
    public int getAttackPower() {
        // todo: account for weapon triangle and effective advantage (eg, bows vs fliers)

        return getModifiedStrength();
    }
    public int getDefensePower() {return getModifiedDefense() + occupyingTile.defenseBonus;}
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
    public int getModifiedDexterity() {
        int modSkl = baseDexterity;
        modSkl += getEquippedWeapon().getDexterityBonus();
        return modSkl;
    }
    public int getModifiedMaxHP() {
        int modHP = baseMaxHP;
        modHP += getEquippedWeapon().getHealthBonus();
        return modHP;
    }
    public int getModifiedSpeed() {
        int modSpd = baseSpeed;
        modSpd -= (int) getEquippedWeapon().getWeight();
        return modSpd;
    }
    public float getModifiedMobility() {
        float modMov = mobility;
        modMov -= getEquippedWeapon().getWeight();
        return modMov;
    }
    public int getBaseDefense() { return baseDefense; }
    public int getBaseMaxHP() { return baseMaxHP; }
    public int getBaseMobility() {
        // "movement" stat referred to as "mobility"
        return mobility;
    }
    public int getBaseDexterity() { return baseDexterity; }
    public int getBaseSpeed() { return baseSpeed; }
    public int getBaseStrength() { return baseStrength; }
    public MovementType getMovementType() { return unitClass.movementType; }
    public int getColumn() { return column; }
    public int getRow() { return row; }
    public TeamAlignment getTeamAlignment() { return teamAlignment; }
    public Inventory getInventory() {return inventory;}
    public TextureRegion getThumbnail() {return thumbnail;}

}
