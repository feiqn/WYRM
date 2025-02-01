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
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.BallistaActionsPopup;
import com.feiqn.wyrm.models.itemdata.iron.iron_Item;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.amulets.SimpleAmulet;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.bracelets.SimpleBracelet;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.rings.SimpleRing;
import com.feiqn.wyrm.models.itemdata.simple.equipment.armor.ArmorType;
import com.feiqn.wyrm.models.itemdata.simple.equipment.armor.SimpleArmor;
import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.SimpleKlass;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponRank;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponType;
import com.feiqn.wyrm.models.itemdata.simple.items.SimpleInventory;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.MapObject;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.ObjectType;
import com.feiqn.wyrm.models.phasedata.Phase;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;

import java.util.HashMap;
import java.util.Random;

public class SimpleUnit extends Image {
    // A classless unit with no weapons.

    public enum MotionState {
        WALKING_NORTH,
        WALKING_SOUTH,
        WALKING_EAST,
        WALKING_WEST,
        IDLE,
        FLOURISH,
    }

    protected AIType aiType;

    public LogicalTile occupyingTile;

    public MapObject occupyingMapObject;

    private boolean canStillMoveThisTurn;
    protected boolean isABoss;

    public String name;

    public iron_Item iron_equippedWeapon;

    public Boolean isOccupyingMapObject;

    // TODO: weapon proficiency

    protected int level,
                  iron_mobility,
                  iron_baseStrength,
                  iron_baseDefense,
                  iron_baseMaxHP,
                  rollingHP,
                  iron_baseDexterity,
                  iron_baseSpeed,
                  row,
                  column,
                  iron_exp,
                  iron_constitution; // con represents a units static overall size 1-10 and does not change with growths outside of class change

    protected int simple_Strength,
                  simple_Defense,
                  simple_Magic,
                  simple_Resistance,
                  simple_Speed,
                  simple_Health;

    protected SimpleWeapon simpleWeapon;
    protected SimpleArmor simpleArmor;
    protected SimpleRing simpleRing;
    protected SimpleAmulet simpleAmulet;
    protected SimpleBracelet simpleBracelet;
    protected SimpleInventory simpleInventory;
    protected SimpleKlass simpleKlass;

    protected boolean burned;
    protected boolean poisoned;
    protected boolean soulBranded;
    protected boolean stunned;
    protected boolean chilled;

    protected HashMap<ArmorType, Boolean> simpleArmorProficiency;

    protected HashMap<StatTypes, Float> ironGrowthRates;
    protected HashMap<WeaponType, WeaponRank> ironWeaponProficiencyLevels;
    protected HashMap<WeaponType, Integer> ironWeaponProficiencyExp;

    public UnitRoster rosterID;

    protected final WYRMGame game;

    private final SimpleUnit self = this;

    protected MotionState motionState;

    protected TeamAlignment teamAlignment;

//    protected IronKlass ironKlass;

//    protected iron_Inventory ironInventory;

    public InputListener attackListener;

    protected TextureRegion thumbnail;

    public SimpleUnit(WYRMGame game) {
        super();
        this.game = game;
        sharedInit();
    }
    public SimpleUnit(WYRMGame game, Texture texture) {
        super(texture);
        this.game = game;

        // TODO: wrap texture to region. below implementation does nothing
        thumbnail = new TextureRegion(texture); // TODO: eventually this will be different from map sprite image

        sharedInit();
    }
    public SimpleUnit(WYRMGame game, TextureRegion region) {
        super(region);
        this.game = game;

        thumbnail = new TextureRegion(region);

        sharedInit();
    }

    private void sharedInit() {
        motionState = MotionState.IDLE;

        name = "Mr. Timn";

//        ironKlass = new IronKlass(game);
//        ironInventory = new iron_Inventory(game, self);

        occupyingTile = new LogicalTile(game, -1,-1);
        isOccupyingMapObject = false;

        setSize(1,1);

        aiType = AIType.STILL;

        canStillMoveThisTurn = true;
        teamAlignment = TeamAlignment.ALLY;

        rosterID = UnitRoster.MR_TIMN;
        isABoss = false;

        level  = 1;
        row    = 0;
        column = 0;

//        iron_equippedWeapon = new iron_Item(game, iron_ItemType.Weapon);
//        iron_mobility      = 5;
//        iron_baseStrength  = 3;
//        iron_baseDefense   = 1;
//        iron_baseMaxHP     = 10;
//        iron_baseDexterity = 3;
//        iron_baseSpeed     = 3;
//        iron_exp           = 0;
//        iron_constitution  = 5;

//        simple_Speed      = 3;
        simple_Speed      = 7; // DEBUG, remove this later
        simple_Defense    = 1;
        simple_Health     = 10;
        simple_Magic      = 1;
        simple_Resistance = 1;
        simple_Strength   = 1;

        rollingHP = simple_Health;

        simpleWeapon    = new SimpleWeapon();
        simpleArmor     = new SimpleArmor();
        simpleAmulet    = new SimpleAmulet();
        simpleBracelet  = new SimpleBracelet();
        simpleRing      = new SimpleRing();
        simpleInventory = new SimpleInventory();
        simpleKlass     = new SimpleKlass();
        simpleKlass     = new SimpleKlass();

//        ironGrowthRates = new HashMap<>();
//        ironGrowthRates.put(StatTypes.SPEED, 0.5f);
//        ironGrowthRates.put(StatTypes.STRENGTH, 0.5f);
//        ironGrowthRates.put(StatTypes.DEFENSE, 0.5f);
//        ironGrowthRates.put(StatTypes.DEXTERITY, 0.5f);
//        ironGrowthRates.put(StatTypes.HEALTH, 0.5f);
//
//        ironWeaponProficiencyLevels = new HashMap<>();
//        ironWeaponProficiencyLevels.put(WeaponType.AXE, WeaponRank.F);
//        ironWeaponProficiencyLevels.put(WeaponType.LANCE, WeaponRank.F);
//        ironWeaponProficiencyLevels.put(WeaponType.SWORD, WeaponRank.F);
//        ironWeaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.F);
//        ironWeaponProficiencyLevels.put(WeaponType.HANDS, WeaponRank.F);
//        ironWeaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponRank.F);
//        ironWeaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponRank.F);
//        ironWeaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponRank.F);
//        ironWeaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.F);
//        ironWeaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponRank.F);
//        ironWeaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponRank.F);
//
//        ironWeaponProficiencyExp = new HashMap<>();
//        ironWeaponProficiencyExp.put(WeaponType.AXE, 0);
//        ironWeaponProficiencyExp.put(WeaponType.LANCE, 0);
//        ironWeaponProficiencyExp.put(WeaponType.SWORD, 0);
//        ironWeaponProficiencyExp.put(WeaponType.BOW, 0);
//        ironWeaponProficiencyExp.put(WeaponType.HANDS, 0);
//        ironWeaponProficiencyExp.put(WeaponType.MAGE_LIGHT, 0);
//        ironWeaponProficiencyExp.put(WeaponType.MAGE_DARK, 0);
//        ironWeaponProficiencyExp.put(WeaponType.MAGE_ANIMA, 0);
//        ironWeaponProficiencyExp.put(WeaponType.SHIELD, 0);
//        ironWeaponProficiencyExp.put(WeaponType.HERBAL_POTION, 0);
//        ironWeaponProficiencyExp.put(WeaponType.HERBAL_FLORAL, 0);

        simpleArmorProficiency = new HashMap<>();
        simpleArmorProficiency.put(ArmorType.HEAVY, false);
        simpleArmorProficiency.put(ArmorType.MEDIUM, false);
        simpleArmorProficiency.put(ArmorType.LIGHT, false);
        simpleArmorProficiency.put(ArmorType.CLOTH, false);

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(game.activeGridScreen.conditionsHandler.tickCount() == self.modifiedSimpleSpeed()) {
                    // Only allow input during player phase
                    if(self.teamAlignment == TeamAlignment.PLAYER){
                        // Unit is player's own unit
                        if(game.activeGridScreen.activeUnit == null) {
                            // Haven't already selected another unit
                            if(self.canMove()) {
                                if(!isOccupyingMapObject) {
                                    game.activeGridScreen.activeUnit = self;
                                    game.activeGridScreen.highlightAllTilesUnitCanAccess(self);
                                } else if(occupyingMapObject.objectType == ObjectType.BALLISTA){
                                    // TODO: contextual responses when occupying objects such as ballista
                                    final BallistaActionsPopup bap = new BallistaActionsPopup(game, self, occupyingMapObject);
                                    game.activeGridScreen.hudStage.addActor(bap);
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

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                game.activeGridScreen.hud().updateHoveredUnitInfoPanel(self);
                game.activeGridScreen.hoveredUnit = self;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                game.activeGridScreen.hud().removeHoveredUnitInfoPanel();
                game.activeGridScreen.hoveredUnit = null;
            }

        });
    }

    public void constructAndAddAttackListener(final SimpleUnit attackingUnit) {

        this.redColor();
        attackListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setCannotMove();

                game.activeGridScreen.removeTileHighlighters();
                game.activeGridScreen.clearAttackableEnemies();

                game.activeGridScreen.combatHandler.iron_goToCombat(attackingUnit, self);

//                game.activeGridScreen.checkIfAllUnitsHaveMovedAndPhaseShouldChange();

                game.activeGridScreen.checkLineOrder();

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

    public void iron_levelUp() {
        Gdx.app.log("unit", "Level up!");

        this.level++;

        this.iron_exp -= 100;
        final int remainder = this.iron_exp;

        final Random random = new Random();

        final float growthChanceStr = random.nextFloat();
        Gdx.app.log("unit", "strength " + growthChanceStr);
        if(growthChanceStr < self.ironGrowthRates.get(StatTypes.STRENGTH)) {
            Gdx.app.log("unit", "Ye boi str go up!");
            this.iron_baseStrength++;
            if(growthChanceStr < self.ironGrowthRates.get(StatTypes.STRENGTH) / 2) {
                Gdx.app.log("unit", "POG!");
                this.iron_baseStrength++;
                if(growthChanceStr < self.ironGrowthRates.get(StatTypes.STRENGTH) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.iron_baseStrength++;
                }
            }
        }

        final float growthChanceDef = random.nextFloat();
        Gdx.app.log("unit", "defense " + growthChanceDef);
        if(growthChanceDef < self.ironGrowthRates.get(StatTypes.DEFENSE)) {
            Gdx.app.log("unit", "Ye boi defense gone up!");
            this.iron_baseDefense++;
            if(growthChanceDef < self.ironGrowthRates.get(StatTypes.DEFENSE) / 2) {
                Gdx.app.log("unit", "POG!");
                this.iron_baseDefense++;
                if(growthChanceDef < self.ironGrowthRates.get(StatTypes.DEFENSE) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.iron_baseDefense++;
                }
            }
        }

        final float growthChanceSkl = random.nextFloat();
        Gdx.app.log("unit", "skill " + growthChanceSkl);
        if(growthChanceSkl < self.ironGrowthRates.get(StatTypes.DEXTERITY)) {
            Gdx.app.log("unit", "Ye boi skill get big!");
            this.iron_baseDexterity++;
            if(growthChanceSkl < self.ironGrowthRates.get(StatTypes.DEXTERITY) / 2) {
                Gdx.app.log("unit", "POG!");
                this.iron_baseDexterity++;
                if(growthChanceSkl < self.ironGrowthRates.get(StatTypes.DEXTERITY) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.iron_baseDexterity++;
                }
            }
        }

        final float growthChanceHP = random.nextFloat();
        Gdx.app.log("unit", "" + growthChanceHP);
        if(growthChanceHP < self.ironGrowthRates.get(StatTypes.HEALTH)) {
            Gdx.app.log("unit", "Ye boi defense gone up!");
            this.iron_baseMaxHP++;
            if(growthChanceHP < self.ironGrowthRates.get(StatTypes.HEALTH) / 2) {
                Gdx.app.log("unit", "POG!");
                this.iron_baseMaxHP++;
                if(growthChanceHP < self.ironGrowthRates.get(StatTypes.HEALTH) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.iron_baseMaxHP++;
                }
            }
        }

        final float growthChanceSpd = random.nextFloat();
        Gdx.app.log("unit", "" + growthChanceSpd);
        if(growthChanceSpd < self.ironGrowthRates.get(StatTypes.SPEED)) {
            Gdx.app.log("unit", "Ye boi spd gone up!");
            this.iron_baseSpeed++;
            if(growthChanceSpd < self.ironGrowthRates.get(StatTypes.SPEED) / 2) {
                Gdx.app.log("unit", "POG!");
                this.iron_baseSpeed++;
                if(growthChanceSpd < self.ironGrowthRates.get(StatTypes.SPEED) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.iron_baseSpeed++;
                }
            }
        }

        this.iron_exp = 0;
        Gdx.app.log("leveler", "recycling  " + remainder + " exp");
        this.iron_addExp(remainder);
    }

    public void iron_addExp(int expGain) {
        this.iron_exp += expGain;

        Gdx.app.log("Unit", "gained " + expGain + " exp");

        if(this.iron_exp >= 100) {
            iron_levelUp();
        }
    }

    public void kill() {
        this.remove();
        game.activeGridScreen.getLogicalMap().getTileAtPosition(this.getRow(),this.getColumn()).occupyingUnit = null;
        game.activeGridScreen.getLogicalMap().getTileAtPosition(this.getRow(),this.getColumn()).isOccupied = false;
        game.activeGridScreen.conditionsHandler.teams().removeUnitFromTeam(this);
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
//    public void setIronKlass(IronKlass ironKlass) {
//        this.ironKlass = ironKlass;
//    }
    private void increaseWeaponProficiency(WeaponType type) {
        switch(type) {
            case AXE:

                final int remainder_axe = ironWeaponProficiencyExp.get(WeaponType.AXE) - 100;

                switch(ironWeaponProficiencyLevels.get(WeaponType.AXE)) {
                    case S:
                        break;
                    case A:
                        ironWeaponProficiencyLevels.put(WeaponType.AXE, WeaponRank.S);
                        break;
                    case B:
                        ironWeaponProficiencyLevels.put(WeaponType.AXE, WeaponRank.A);
                        break;
                    case C:
                        ironWeaponProficiencyLevels.put(WeaponType.AXE, WeaponRank.B);
                        break;
                    case D:
                        ironWeaponProficiencyLevels.put(WeaponType.AXE, WeaponRank.C);
                        break;
                    case E:
                        ironWeaponProficiencyLevels.put(WeaponType.AXE, WeaponRank.D);
                        break;
                    case F:
                        ironWeaponProficiencyLevels.put(WeaponType.AXE, WeaponRank.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.AXE, remainder_axe);
                break;

            case BOW:

                final int remainder_bow = ironWeaponProficiencyExp.get(WeaponType.BOW) - 100;

                switch(ironWeaponProficiencyLevels.get(WeaponType.BOW)) {
                    case S:
                        break;
                    case A:
                        ironWeaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.S);
                        break;
                    case B:
                        ironWeaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.A);
                        break;
                    case C:
                        ironWeaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.B);
                        break;
                    case D:
                        ironWeaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.C);
                        break;
                    case E:
                        ironWeaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.D);
                        break;
                    case F:
                        ironWeaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.BOW, remainder_bow);
                break;

            case LANCE:

                final int remainder_lance = ironWeaponProficiencyExp.get(WeaponType.LANCE) - 100;

                switch(ironWeaponProficiencyLevels.get(WeaponType.LANCE)) {
                    case S:
                        break;
                    case A:
                        ironWeaponProficiencyLevels.put(WeaponType.LANCE, WeaponRank.S);
                        break;
                    case B:
                        ironWeaponProficiencyLevels.put(WeaponType.LANCE, WeaponRank.A);
                        break;
                    case C:
                        ironWeaponProficiencyLevels.put(WeaponType.LANCE, WeaponRank.B);
                        break;
                    case D:
                        ironWeaponProficiencyLevels.put(WeaponType.LANCE, WeaponRank.C);
                        break;
                    case E:
                        ironWeaponProficiencyLevels.put(WeaponType.LANCE, WeaponRank.D);
                        break;
                    case F:
                        ironWeaponProficiencyLevels.put(WeaponType.LANCE, WeaponRank.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.LANCE, remainder_lance);
                break;

            case SWORD:

                final int remainder_sword = ironWeaponProficiencyExp.get(WeaponType.SWORD) - 100;

                switch(ironWeaponProficiencyLevels.get(WeaponType.SWORD)) {
                    case S:
                        break;
                    case A:
                        ironWeaponProficiencyLevels.put(WeaponType.SWORD, WeaponRank.S);
                        break;
                    case B:
                        ironWeaponProficiencyLevels.put(WeaponType.SWORD, WeaponRank.A);
                        break;
                    case C:
                        ironWeaponProficiencyLevels.put(WeaponType.SWORD, WeaponRank.B);
                        break;
                    case D:
                        ironWeaponProficiencyLevels.put(WeaponType.SWORD, WeaponRank.C);
                        break;
                    case E:
                        ironWeaponProficiencyLevels.put(WeaponType.SWORD, WeaponRank.D);
                        break;
                    case F:
                        ironWeaponProficiencyLevels.put(WeaponType.SWORD, WeaponRank.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.SWORD, remainder_sword);
                break;

            case SHIELD:

                final int remainder_shield = ironWeaponProficiencyExp.get(WeaponType.SHIELD) - 100;

                switch(ironWeaponProficiencyLevels.get(WeaponType.SHIELD)) {
                    case S:
                        break;
                    case A:
                        ironWeaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.S);
                        break;
                    case B:
                        ironWeaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.A);
                        break;
                    case C:
                        ironWeaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.B);
                        break;
                    case D:
                        ironWeaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.C);
                        break;
                    case E:
                        ironWeaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.D);
                        break;
                    case F:
                        ironWeaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.SHIELD, remainder_shield);
                break;

            case MAGE_DARK:

                final int remainder_dark = ironWeaponProficiencyExp.get(WeaponType.MAGE_DARK) - 100;

                switch(ironWeaponProficiencyLevels.get(WeaponType.MAGE_DARK)) {
                    case S:
                        break;
                    case A:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponRank.S);
                        break;
                    case B:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponRank.A);
                        break;
                    case C:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponRank.B);
                        break;
                    case D:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponRank.C);
                        break;
                    case E:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponRank.D);
                        break;
                    case F:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponRank.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.MAGE_DARK, remainder_dark);
                break;

            case MAGE_ANIMA:

                final int remainder_anima = ironWeaponProficiencyExp.get(WeaponType.MAGE_ANIMA) - 100;

                switch(ironWeaponProficiencyLevels.get(WeaponType.MAGE_ANIMA)) {
                    case S:
                        break;
                    case A:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponRank.S);
                        break;
                    case B:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponRank.A);
                        break;
                    case C:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponRank.B);
                        break;
                    case D:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponRank.C);
                        break;
                    case E:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponRank.D);
                        break;
                    case F:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponRank.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.MAGE_ANIMA, remainder_anima);
                break;

            case MAGE_LIGHT:

                final int remainder_light = ironWeaponProficiencyExp.get(WeaponType.MAGE_LIGHT) - 100;

                switch(ironWeaponProficiencyLevels.get(WeaponType.MAGE_LIGHT)) {
                    case S:
                        break;
                    case A:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponRank.S);
                        break;
                    case B:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponRank.A);
                        break;
                    case C:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponRank.B);
                        break;
                    case D:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponRank.C);
                        break;
                    case E:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponRank.D);
                        break;
                    case F:
                        ironWeaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponRank.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.MAGE_LIGHT, remainder_light);
                break;

            case HERBAL_FLORAL:

                final int remainder_floral = ironWeaponProficiencyExp.get(WeaponType.HERBAL_FLORAL) - 100;

                switch(ironWeaponProficiencyLevels.get(WeaponType.HERBAL_FLORAL)) {
                    case S:
                        break;
                    case A:
                        ironWeaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponRank.S);
                        break;
                    case B:
                        ironWeaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponRank.A);
                        break;
                    case C:
                        ironWeaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponRank.B);
                        break;
                    case D:
                        ironWeaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponRank.C);
                        break;
                    case E:
                        ironWeaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponRank.D);
                        break;
                    case F:
                        ironWeaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponRank.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.HERBAL_FLORAL, remainder_floral);
                break;

            case HERBAL_POTION:

                final int remainder_potions = ironWeaponProficiencyExp.get(WeaponType.HERBAL_POTION) - 100;

                switch(ironWeaponProficiencyLevels.get(WeaponType.HERBAL_POTION)) {
                    case S:
                        break;
                    case A:
                        ironWeaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponRank.S);
                        break;
                    case B:
                        ironWeaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponRank.A);
                        break;
                    case C:
                        ironWeaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponRank.B);
                        break;
                    case D:
                        ironWeaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponRank.C);
                        break;
                    case E:
                        ironWeaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponRank.D);
                        break;
                    case F:
                        ironWeaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponRank.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.HERBAL_POTION, remainder_potions);
                break;

            case HANDS:

                final int remainder_hands = ironWeaponProficiencyExp.get(WeaponType.HANDS) - 100;

                switch(ironWeaponProficiencyLevels.get(WeaponType.HANDS)) {
                    case S:
                        break;
                    case A:
                        ironWeaponProficiencyLevels.put(WeaponType.HANDS, WeaponRank.S);
                        break;
                    case B:
                        ironWeaponProficiencyLevels.put(WeaponType.HANDS, WeaponRank.A);
                        break;
                    case C:
                        ironWeaponProficiencyLevels.put(WeaponType.HANDS, WeaponRank.B);
                        break;
                    case D:
                        ironWeaponProficiencyLevels.put(WeaponType.HANDS, WeaponRank.C);
                        break;
                    case E:
                        ironWeaponProficiencyLevels.put(WeaponType.HANDS, WeaponRank.D);
                        break;
                    case F:
                        ironWeaponProficiencyLevels.put(WeaponType.HANDS, WeaponRank.E);
                        break;
                }

                addWeaponProficiencyExp(WeaponType.HANDS, remainder_hands);
                break;
        }
    }
    public void addWeaponProficiencyExp(WeaponType type, int exp) {
        switch(type) {
            case LANCE:
                ironWeaponProficiencyExp.put(WeaponType.LANCE, ironWeaponProficiencyExp.get(WeaponType.LANCE) + exp);
                if(ironWeaponProficiencyExp.get(WeaponType.LANCE) >= 100) {
                    increaseWeaponProficiency(WeaponType.LANCE);
                }
                break;
            case BOW:
                ironWeaponProficiencyExp.put(WeaponType.BOW, ironWeaponProficiencyExp.get(WeaponType.BOW) + exp);
                if(ironWeaponProficiencyExp.get(WeaponType.BOW) >= 100) {
                    increaseWeaponProficiency(WeaponType.BOW);
                }
                break;
            case AXE:
                ironWeaponProficiencyExp.put(WeaponType.AXE, ironWeaponProficiencyExp.get(WeaponType.AXE) + exp);
                if(ironWeaponProficiencyExp.get(WeaponType.AXE) >= 100) {
                    increaseWeaponProficiency(WeaponType.AXE);
                }
                break;
            case SWORD:
                ironWeaponProficiencyExp.put(WeaponType.SWORD, ironWeaponProficiencyExp.get(WeaponType.SWORD) + exp);
                if(ironWeaponProficiencyExp.get(WeaponType.SWORD) >= 100) {
                    increaseWeaponProficiency(WeaponType.SWORD);
                }
                break;
            case SHIELD:
                ironWeaponProficiencyExp.put(WeaponType.SHIELD, ironWeaponProficiencyExp.get(WeaponType.SHIELD) + exp);
                if(ironWeaponProficiencyExp.get(WeaponType.SHIELD) >= 100) {
                    increaseWeaponProficiency(WeaponType.SHIELD);
                }
                break;
            case MAGE_DARK:
                ironWeaponProficiencyExp.put(WeaponType.MAGE_DARK, ironWeaponProficiencyExp.get(WeaponType.MAGE_DARK) + exp);
                if(ironWeaponProficiencyExp.get(WeaponType.MAGE_DARK) >= 100) {
                    increaseWeaponProficiency(WeaponType.MAGE_DARK);
                }
                break;
            case MAGE_ANIMA:
                ironWeaponProficiencyExp.put(WeaponType.MAGE_ANIMA, ironWeaponProficiencyExp.get(WeaponType.MAGE_ANIMA) + exp);
                if(ironWeaponProficiencyExp.get(WeaponType.MAGE_ANIMA) >= 100) {
                    increaseWeaponProficiency(WeaponType.MAGE_ANIMA);
                }
                break;
            case MAGE_LIGHT:
                ironWeaponProficiencyExp.put(WeaponType.MAGE_LIGHT, ironWeaponProficiencyExp.get(WeaponType.MAGE_LIGHT) + exp);
                if(ironWeaponProficiencyExp.get(WeaponType.MAGE_LIGHT) >= 100) {
                    increaseWeaponProficiency(WeaponType.MAGE_LIGHT);
                }
                break;
            case HERBAL_FLORAL:
                ironWeaponProficiencyExp.put(WeaponType.HERBAL_FLORAL, ironWeaponProficiencyExp.get(WeaponType.HERBAL_FLORAL) + exp);
                if(ironWeaponProficiencyExp.get(WeaponType.HERBAL_FLORAL) >= 100) {
                    increaseWeaponProficiency(WeaponType.HERBAL_FLORAL);
                }
                break;
            case HERBAL_POTION:
                ironWeaponProficiencyExp.put(WeaponType.HERBAL_POTION, ironWeaponProficiencyExp.get(WeaponType.HERBAL_POTION) + exp);
                if(ironWeaponProficiencyExp.get(WeaponType.HERBAL_POTION) >= 100) {
                    increaseWeaponProficiency(WeaponType.HERBAL_POTION);
                }
                break;
            case HANDS:
                ironWeaponProficiencyExp.put(WeaponType.HANDS, ironWeaponProficiencyExp.get(WeaponType.HANDS) + exp);
                if(ironWeaponProficiencyExp.get(WeaponType.HANDS) >= 100) {
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
    public void setIron_baseDefense(int iron_baseDefense) {
        this.iron_baseDefense = iron_baseDefense;
    }
    public void setFacedDirection(MotionState motionState) {
        this.motionState = motionState;
    }
    public void setIron_baseMaxHP(int iron_baseMaxHP) {
        this.iron_baseMaxHP = iron_baseMaxHP;
    }
    public void setIron_mobility(int iron_mobility) {
        this.iron_mobility = iron_mobility;
    }
    public void setIron_baseDexterity(int iron_baseDexterity) {
        this.iron_baseDexterity = iron_baseDexterity;
    }
    public void setIron_baseSpeed(int iron_baseSpeed) {
        this.iron_baseSpeed = iron_baseSpeed;
    }
    public void setIron_baseStrength(int iron_baseStrength) {
        this.iron_baseStrength = iron_baseStrength;
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
    public void setRollingHP(int newHP) {
        rollingHP = newHP;
    }

    // --GETTERS--
    public boolean isABoss() { return isABoss; }
    public AIType getAiType() { return aiType; }
    public MapObject getOccupyingMapObject() {
        return occupyingMapObject;
    }

//    public int iron_getHitRate() {
//        // do i want luck to exist in this game?
//
//        return (getIron_modifiedDexterity() * 2) + getIron_equippedWeapon().getWeaponAccuracy();
//    }
//    public int iron_getEvade(){
//        return (iron_getAttackSpeed() * 2) + occupyingTile.evadeBonus;
//    }
//    public int iron_getAttackSpeed() {
//        return getIron_modifiedSpeed() - iron_getBurden();
//    }
//    public int iron_getBurden() {
//        int burden = 0;
//        for(iron_Item ironItem : ironInventory.items()) {
//            burden+= ironItem.getWeight();
//        }
//        burden -= iron_constitution;
//        if(burden < 0) {
//            burden = 0;
//        }
//        return burden;
//    }
//    public int iron_getReach() {
//
//        int reach;
//
//        if(isOccupyingMapObject) {
//            reach = occupyingMapObject.reach;
//        } else {
//            reach = 1;
//            for(iron_Item ironItem : ironInventory.items()) {
//                if(ironItem.ironItemType == iron_ItemType.Weapon) {
//                    if(ironItem.getRange() > reach) {
//                        reach = ironItem.getRange();
//                    }
//                }
//            }
//        }
//        return reach;
//
//    }

    public int getLevel() {return level;}
//    public int getAttackPower() {
//        // todo: account for weapon triangle and effective advantage (eg, bows vs fliers)
//
//        return getIron_modifiedStrength();
//    }
//    public int getDefensePower() {return getIron_modifiedDefense() + occupyingTile.defenseBonus;}
//    public iron_Item getIron_equippedWeapon() {
//        if(iron_equippedWeapon != null) {
//            return iron_equippedWeapon;
//        } else {
//            for(iron_Item ironItem : ironInventory.items()) {
//                if(ironItem.ironItemType == iron_ItemType.Weapon){
//                    return ironItem;
//                }
//            }
//        }
//        return new iron_Item(game, iron_ItemType.Weapon);
//    }
    public int getRollingHP() {return rollingHP;}
    public boolean canMove() { return canStillMoveThisTurn; }
    public MotionState getFacedDirection() { return motionState; }
//    public int getIron_modifiedStrength() {
//        int modStr = iron_baseStrength;
//        modStr += getIron_equippedWeapon().getStrengthBonus();
//        return modStr;
//    }
//    public int getIron_modifiedDefense() {
//        int modDef = iron_baseDefense;
//        modDef += getIron_equippedWeapon().getDefenseBonus();
//        return modDef;
//    }
//    public int getIron_modifiedDexterity() {
//        int modSkl = iron_baseDexterity;
//        modSkl += getIron_equippedWeapon().getDexterityBonus();
//        return modSkl;
//    }
//    public int getIron_modifiedMaxHP() {
//        int modHP = iron_baseMaxHP;
//        modHP += getIron_equippedWeapon().getHealthBonus();
//        return modHP;
//    }
//    public int getIron_modifiedSpeed() {
//        int modSpd = iron_baseSpeed;
//        modSpd -= (int) getIron_equippedWeapon().getWeight();
//        return modSpd;
//    }
//    public float getIron_modifiedMobility() {
//        float modMov = iron_mobility;
//        modMov -= getIron_equippedWeapon().getWeight();
//        return modMov;
//    }
//    public int getIron_baseDefense() { return iron_baseDefense; }
//    public int getIron_baseMaxHP() { return iron_baseMaxHP; }
//    public int getIron_baseMobility() {
//        // "movement" stat referred to as "mobility"
//        return iron_mobility;
//    }
//    public int getIron_baseDexterity() { return iron_baseDexterity; }
//    public int getIron_baseSpeed() { return iron_baseSpeed; }
//    public int getIron_baseStrength() { return iron_baseStrength; }

    public MovementType getMovementType() { return simpleKlass.movementType(); }
    public int getColumn() { return column; }
    public int getRow() { return row; }
    public TeamAlignment getTeamAlignment() { return teamAlignment; }
    public SimpleInventory getInventory() { return simpleInventory; }
    public TextureRegion getThumbnail() { return thumbnail; }

    /** This is the part where I started believing in myself, for better or worse.
     */
    public Boolean proficienct(ArmorType arm) { return simpleArmorProficiency.get(arm); }

    public SimpleWeapon simpleWeapon() { return simpleWeapon; }
    public SimpleArmor simpleArmor() { return simpleArmor; }
    public SimpleRing simpleRing() { return simpleRing; }
    public SimpleAmulet simpleAmulet() { return simpleAmulet; }
    public SimpleBracelet simpleBracelet() { return simpleBracelet; }
    public SimpleInventory simpleInventory() { return simpleInventory; }
    public SimpleKlass simpleKlass() { return simpleKlass; }

    public int baseSimpleSpeed() { return simple_Speed; } // TODO: modifiers from weight, etc? probably not tbh
    public int baseSimpleStrength() { return simple_Strength; }
    public int baseSimpleDefense() { return simple_Defense; }
    public int baseSimpleMagic() { return simple_Magic; }
    public int baseSimpleHealth() { return simple_Health; }
    public int baseSimpleResistance() { return simple_Resistance; }

    public int getSimpleReach() {
        // TODO
        return 1;
    }

    public int modifiedSimpleSpeed() {
        return simple_Speed + simpleWeapon.bonusSpeed() + simpleArmor.bonusSpeed() + simpleKlass.bonusSpeed();
    }
    public int modifiedSimpleStrength() {
        return simple_Strength + simpleWeapon.bonusStrength() + simpleArmor.bonusStrength() + simpleKlass.bonusStrength();
    }
    public int modifiedSimpleDefense() {
        return simple_Defense + simpleWeapon.bonusDefense() + simpleArmor.bonusDefense() + simpleKlass.bonusDefense();
    }
    public int modifiedSimpleMagic() {
        return simple_Magic + simpleWeapon.bonusMagic() + simpleArmor.bonusDefense() + simpleKlass.bonusDefense();
    }
    public int modifiedSimpleHealth() {
        return simple_Health + simpleWeapon.bonusHealth() + simpleArmor.bonusHealth() + simpleKlass.bonusHealth();
    }
    public int modifiedSimpleResistance() {
        return simple_Resistance + simpleWeapon.bonusResistance() + simpleArmor.bonusResistance() + simpleKlass.bonusResistance();
    }

    private static class IronMode {



    }

}
