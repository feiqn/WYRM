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
import com.feiqn.wyrm.models.itemdata.iron.iron_Inventory;
import com.feiqn.wyrm.models.itemdata.iron.iron_Item;
import com.feiqn.wyrm.models.itemdata.iron.iron_ItemType;
import com.feiqn.wyrm.models.itemdata.simple.equipment.SimpleEquipment;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.SimpleAccessory;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.amulets.SimpleAmulet;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.bracelets.SimpleBracelet;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.rings.SimpleRing;
import com.feiqn.wyrm.models.itemdata.simple.equipment.armor.ArmorType;
import com.feiqn.wyrm.models.itemdata.simple.equipment.armor.SimpleArmor;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponRank;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponType;
import com.feiqn.wyrm.models.itemdata.simple.items.SimpleInventory;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.MapObject;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.ObjectType;
import com.feiqn.wyrm.models.phasedata.Phase;
import com.feiqn.wyrm.models.unitdata.classdata.UnitClass;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;

import java.util.HashMap;
import java.util.Random;

public class Unit extends Image {
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

    protected HashMap<StatTypes, Float> growthRates;
    protected HashMap<ArmorType, Boolean> armorProficiency;
    protected HashMap<WeaponType, WeaponRank> weaponProficiencyLevels;
    protected HashMap<WeaponType, Integer> weaponProficiencyExp;
    public UnitRoster rosterID;
    protected final WYRMGame game;
    private final Unit self = this;
    protected MotionState motionState;
    protected TeamAlignment teamAlignment;
    protected UnitClass simpleClass;
    protected iron_Inventory ironInventory;
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
        motionState = MotionState.IDLE;

        name = "Mr. Timn";
        simpleClass = new UnitClass(game);
        ironInventory = new iron_Inventory(game, self);
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

        iron_equippedWeapon = new iron_Item(game, iron_ItemType.Weapon);
        iron_mobility      = 5;
        iron_baseStrength  = 3;
        iron_baseDefense   = 1;
        iron_baseMaxHP     = 10;
        iron_baseDexterity = 3;
        iron_baseSpeed     = 3;
        iron_exp           = 0;
        iron_constitution  = 5;

        simple_Speed      = 3;
        simple_Defense    = 1;
        simple_Health     = 10;
        simple_Magic      = 1;
        simple_Resistance = 1;
        simple_Strength   = 1;

        rollingHP = simple_Health;

        simpleWeapon    = new SimpleWeapon(game);
        simpleArmor     = new SimpleArmor(game);
        simpleAmulet    = new SimpleAmulet(game);
        simpleBracelet  = new SimpleBracelet(game);
        simpleRing      = new SimpleRing(game);
        simpleInventory = new SimpleInventory(game);

        simpleClass     = new UnitClass(game); // default is DRAFTEE

        growthRates = new HashMap<>();
        growthRates.put(StatTypes.SPEED, 0.5f);
        growthRates.put(StatTypes.STRENGTH, 0.5f);
        growthRates.put(StatTypes.DEFENSE, 0.5f);
        growthRates.put(StatTypes.DEXTERITY, 0.5f);
        growthRates.put(StatTypes.HEALTH, 0.5f);

        weaponProficiencyLevels = new HashMap<>();
        weaponProficiencyLevels.put(WeaponType.AXE, WeaponRank.F);
        weaponProficiencyLevels.put(WeaponType.LANCE, WeaponRank.F);
        weaponProficiencyLevels.put(WeaponType.SWORD, WeaponRank.F);
        weaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.F);
        weaponProficiencyLevels.put(WeaponType.HANDS, WeaponRank.F);
        weaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponRank.F);
        weaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponRank.F);
        weaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponRank.F);
        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.F);
        weaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponRank.F);
        weaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponRank.F);

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

        armorProficiency.put(ArmorType.HEAVY, false);
        armorProficiency.put(ArmorType.MEDIUM, false);
        armorProficiency.put(ArmorType.LIGHT, false);
        armorProficiency.put(ArmorType.CLOTH, false);

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(game.activeGridScreen.conditionsHandler.currentPhase() == Phase.PLAYER_PHASE) {
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
                game.activeGridScreen.hud().removeHoveredUnitInfoPanel();
                game.activeGridScreen.hoveredUnit = null;
            }

        });
    }

    public void constructAndAddAttackListener(final Unit attackingUnit) {

        this.redColor();
        attackListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setCannotMove();

                game.activeGridScreen.removeTileHighlighters();
                game.activeGridScreen.clearAttackableEnemies();

                game.activeGridScreen.combatHandler.goToCombat(attackingUnit, self);

                game.activeGridScreen.checkIfAllUnitsHaveMovedAndPhaseShouldChange();

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
        if(growthChanceStr < self.growthRates.get(StatTypes.STRENGTH)) {
            Gdx.app.log("unit", "Ye boi str go up!");
            this.iron_baseStrength++;
            if(growthChanceStr < self.growthRates.get(StatTypes.STRENGTH) / 2) {
                Gdx.app.log("unit", "POG!");
                this.iron_baseStrength++;
                if(growthChanceStr < self.growthRates.get(StatTypes.STRENGTH) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.iron_baseStrength++;
                }
            }
        }

        final float growthChanceDef = random.nextFloat();
        Gdx.app.log("unit", "defense " + growthChanceDef);
        if(growthChanceDef < self.growthRates.get(StatTypes.DEFENSE)) {
            Gdx.app.log("unit", "Ye boi defense gone up!");
            this.iron_baseDefense++;
            if(growthChanceDef < self.growthRates.get(StatTypes.DEFENSE) / 2) {
                Gdx.app.log("unit", "POG!");
                this.iron_baseDefense++;
                if(growthChanceDef < self.growthRates.get(StatTypes.DEFENSE) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.iron_baseDefense++;
                }
            }
        }

        final float growthChanceSkl = random.nextFloat();
        Gdx.app.log("unit", "skill " + growthChanceSkl);
        if(growthChanceSkl < self.growthRates.get(StatTypes.DEXTERITY)) {
            Gdx.app.log("unit", "Ye boi skill get big!");
            this.iron_baseDexterity++;
            if(growthChanceSkl < self.growthRates.get(StatTypes.DEXTERITY) / 2) {
                Gdx.app.log("unit", "POG!");
                this.iron_baseDexterity++;
                if(growthChanceSkl < self.growthRates.get(StatTypes.DEXTERITY) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.iron_baseDexterity++;
                }
            }
        }

        final float growthChanceHP = random.nextFloat();
        Gdx.app.log("unit", "" + growthChanceHP);
        if(growthChanceHP < self.growthRates.get(StatTypes.HEALTH)) {
            Gdx.app.log("unit", "Ye boi defense gone up!");
            this.iron_baseMaxHP++;
            if(growthChanceHP < self.growthRates.get(StatTypes.HEALTH) / 2) {
                Gdx.app.log("unit", "POG!");
                this.iron_baseMaxHP++;
                if(growthChanceHP < self.growthRates.get(StatTypes.HEALTH) / 4) {
                    Gdx.app.log("unit", "POGGERS!");
                    this.iron_baseMaxHP++;
                }
            }
        }

        final float growthChanceSpd = random.nextFloat();
        Gdx.app.log("unit", "" + growthChanceSpd);
        if(growthChanceSpd < self.growthRates.get(StatTypes.SPEED)) {
            Gdx.app.log("unit", "Ye boi spd gone up!");
            this.iron_baseSpeed++;
            if(growthChanceSpd < self.growthRates.get(StatTypes.SPEED) / 2) {
                Gdx.app.log("unit", "POG!");
                this.iron_baseSpeed++;
                if(growthChanceSpd < self.growthRates.get(StatTypes.SPEED) / 4) {
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
        game.activeGridScreen.teamHandler.removeUnitFromTeam(this);
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
    public void setSimpleClass(UnitClass simpleClass) {
        this.simpleClass = simpleClass;
    }
    private void increaseWeaponProficiency(WeaponType type) {
        switch(type) {
            case AXE:

                final int remainder_axe = weaponProficiencyExp.get(WeaponType.AXE) - 100;

                switch(weaponProficiencyLevels.get(WeaponType.AXE)) {
                    case S:
                        break;
                    case A:
                        weaponProficiencyLevels.put(WeaponType.AXE, WeaponRank.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.AXE, WeaponRank.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.AXE, WeaponRank.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.AXE, WeaponRank.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.AXE, WeaponRank.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.AXE, WeaponRank.E);
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
                        weaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.E);
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
                        weaponProficiencyLevels.put(WeaponType.LANCE, WeaponRank.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.LANCE, WeaponRank.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.LANCE, WeaponRank.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.LANCE, WeaponRank.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.LANCE, WeaponRank.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.LANCE, WeaponRank.E);
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
                        weaponProficiencyLevels.put(WeaponType.SWORD, WeaponRank.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.SWORD, WeaponRank.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.SWORD, WeaponRank.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.SWORD, WeaponRank.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.SWORD, WeaponRank.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.SWORD, WeaponRank.E);
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
                        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.E);
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
                        weaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponRank.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponRank.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponRank.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponRank.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponRank.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.MAGE_DARK, WeaponRank.E);
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
                        weaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponRank.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponRank.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponRank.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponRank.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponRank.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.MAGE_ANIMA, WeaponRank.E);
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
                        weaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponRank.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponRank.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponRank.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponRank.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponRank.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.MAGE_LIGHT, WeaponRank.E);
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
                        weaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponRank.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponRank.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponRank.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponRank.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponRank.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_FLORAL, WeaponRank.E);
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
                        weaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponRank.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponRank.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponRank.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponRank.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponRank.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.HERBAL_POTION, WeaponRank.E);
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
                        weaponProficiencyLevels.put(WeaponType.HANDS, WeaponRank.S);
                        break;
                    case B:
                        weaponProficiencyLevels.put(WeaponType.HANDS, WeaponRank.A);
                        break;
                    case C:
                        weaponProficiencyLevels.put(WeaponType.HANDS, WeaponRank.B);
                        break;
                    case D:
                        weaponProficiencyLevels.put(WeaponType.HANDS, WeaponRank.C);
                        break;
                    case E:
                        weaponProficiencyLevels.put(WeaponType.HANDS, WeaponRank.D);
                        break;
                    case F:
                        weaponProficiencyLevels.put(WeaponType.HANDS, WeaponRank.E);
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
    public int iron_getHitRate() {
        // do i want luck to exist in this game?

        return (getModifiedDexterity() * 2) + getIron_equippedWeapon().getWeaponAccuracy();
    }
    public int iron_getEvade(){
        return (iron_getAttackSpeed() * 2) + occupyingTile.evadeBonus;
    }
    public int iron_getAttackSpeed() {
        return getModifiedSpeed() - iron_getBurden();
    }
    public int iron_getBurden() {
        int burden = 0;
        for(iron_Item ironItem : ironInventory.items()) {
            burden+= ironItem.getWeight();
        }
        burden -= iron_constitution;
        if(burden < 0) {
            burden = 0;
        }
        return burden;
    }
    public int iron_getReach() {

        int reach;

        if(isOccupyingMapObject) {
            reach = occupyingMapObject.reach;
        } else {
            reach = 1;
            for(iron_Item ironItem : ironInventory.items()) {
                if(ironItem.ironItemType == iron_ItemType.Weapon) {
                    if(ironItem.getRange() > reach) {
                        reach = ironItem.getRange();
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
    public iron_Item getIron_equippedWeapon() {
        if(iron_equippedWeapon != null) {
            return iron_equippedWeapon;
        } else {
            for(iron_Item ironItem : ironInventory.items()) {
                if(ironItem.ironItemType == iron_ItemType.Weapon){
                    return ironItem;
                }
            }
        }
        return new iron_Item(game, iron_ItemType.Weapon);
    }
    public int getRollingHP() {return rollingHP;}
    public boolean canMove() { return canStillMoveThisTurn; }
    public MotionState getFacedDirection() { return motionState; }
    public int getModifiedStrength() {
        int modStr = iron_baseStrength;
        modStr += getIron_equippedWeapon().getStrengthBonus();
        return modStr;
    }
    public int getModifiedDefense() {
        int modDef = iron_baseDefense;
        modDef += getIron_equippedWeapon().getDefenseBonus();
        return modDef;
    }
    public int getModifiedDexterity() {
        int modSkl = iron_baseDexterity;
        modSkl += getIron_equippedWeapon().getDexterityBonus();
        return modSkl;
    }
    public int getModifiedMaxHP() {
        int modHP = iron_baseMaxHP;
        modHP += getIron_equippedWeapon().getHealthBonus();
        return modHP;
    }
    public int getModifiedSpeed() {
        int modSpd = iron_baseSpeed;
        modSpd -= (int) getIron_equippedWeapon().getWeight();
        return modSpd;
    }
    public float getModifiedMobility() {
        float modMov = iron_mobility;
        modMov -= getIron_equippedWeapon().getWeight();
        return modMov;
    }
    public int getIron_baseDefense() { return iron_baseDefense; }
    public int getIron_baseMaxHP() { return iron_baseMaxHP; }
    public int getBaseMobility() {
        // "movement" stat referred to as "mobility"
        return iron_mobility;
    }
    public int getIron_baseDexterity() { return iron_baseDexterity; }
    public int getIron_baseSpeed() { return iron_baseSpeed; }
    public int getIron_baseStrength() { return iron_baseStrength; }
    public MovementType getMovementType() { return simpleClass.movementType(); }
    public int getColumn() { return column; }
    public int getRow() { return row; }
    public TeamAlignment getTeamAlignment() { return teamAlignment; }
    public iron_Inventory getInventory() { return ironInventory; }
    public TextureRegion getThumbnail() { return thumbnail; }

    /** This is the part where I started believing in myself, for better or worse.
     */
    public Boolean proficienct(ArmorType arm) { return armorProficiency.get(arm); }

    public SimpleWeapon simpleWeapon() { return simpleWeapon; }
    public SimpleArmor simpleArmor() { return simpleArmor; }
    public SimpleRing simpleRing() { return simpleRing; }
    public SimpleAmulet simpleAmulet() { return simpleAmulet; }
    public SimpleBracelet simpleBracelet() { return simpleBracelet; }
    public SimpleInventory simpleInventory() { return simpleInventory; }

    public int baseSimpleSpeed() { return simple_Speed; } // TODO: modifiers from weight, etc? probably not tbh
    public int baseSimpleStrength() { return simple_Strength; }
    public int baseSimpleDefense() { return simple_Defense; }
    public int baseSimpleMagic() { return simple_Magic; }
    public int baseSimpleHealth() { return simple_Health; }
    public int baseSimpleResistance() { return simple_Resistance; }
    public int modifiedSimpleSpeed() {
        return simple_Speed + simpleWeapon.bonusSpeed() + simpleArmor.bonusSpeed() + simpleRing.bonusSpeed();
    }
    public int modifiedSimpleStrength() {
        return simple_Strength + simpleWeapon.bonusStrength() + simpleArmor.bonusStrength() + simpleRing.bonusStrength();
    }
    public int modifiedSimpleDefense() {
        return simple_Defense + simpleWeapon.bonusDefense() + simpleArmor.bonusDefense() + simpleRing.bonusDefense();
    }
    public int modifiedSimpleMagic() {
        return simple_Magic + simpleWeapon.bonusMagic() + simpleArmor.bonusDefense() + simpleRing.bonusDefense();
    }
    public int modifiedSimpleHealth() {
        return simple_Health + simpleWeapon.bonusHealth() + simpleArmor.bonusHealth() + simpleRing.bonusHealth();
    }
    public int modifiedSimpleResistance() {
        return simple_Resistance + simpleWeapon.bonusResistance() + simpleArmor.bonusResistance() + simpleRing.bonusResistance();
    }

}
