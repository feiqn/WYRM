package com.feiqn.wyrm.models.unitdata.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.AIType;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.BallistaActionsPopup;
import com.feiqn.wyrm.models.itemdata.iron.IronInventory;
import com.feiqn.wyrm.models.itemdata.iron.iron_Item;
import com.feiqn.wyrm.models.itemdata.iron.iron_ItemType;
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
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.iron.classdata.IronKlass;

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

    public String name; // TODO: protected

    protected String bio;

    public Boolean isOccupyingMapObject;

    protected int level;
    protected int rollingHP;
    protected int row;
    protected int column;
    protected int simple_Strength,
                  simple_Defense,
                  simple_Magic,
                  simple_Resistance,
                  simple_Speed,
                  simple_Health;
    protected int stunCounter;

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
    private   boolean iron;

    protected HashMap<ArmorType, Boolean> simpleArmorProficiency;

    public UnitRoster rosterID;

    protected final WYRMGame game;

    private final SimpleUnit self = this;

    protected MotionState motionState;

    protected TeamAlignment teamAlignment;

    public InputListener attackListener;

    protected TextureRegion thumbnail;

    private IronMode ironMode;

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

        iron = false;

    }

    private void sharedInit() {
        motionState = MotionState.IDLE;

        name = "Mr. Timn";
        bio = "He turns your jelly into a fish.";

        occupyingTile = new LogicalTile(game, -1,-1);
        isOccupyingMapObject = false;

        setSize(1,1);

        aiType = AIType.STILL;

        canStillMoveThisTurn = true;
        teamAlignment = TeamAlignment.OTHER;

        rosterID = UnitRoster.MR_TIMN;
        isABoss = false;

        level  = 1;
        row    = 0;
        column = 0;

//        simple_Speed      = 3;
        simple_Speed      = 7; // DEBUG, remove this later
        simple_Defense    = 1;
        simple_Health     = 10;
        simple_Magic      = 1;
        simple_Resistance = 1;
        simple_Strength   = 1;

        rollingHP = simple_Health;

        stunCounter = 0;
        stunned     = false;
        burned      = false;
        poisoned    = false;
        soulBranded = false;
        chilled     = false;

        simpleWeapon    = new SimpleWeapon();
        simpleArmor     = new SimpleArmor();
        simpleAmulet    = new SimpleAmulet();
        simpleBracelet  = new SimpleBracelet();
        simpleRing      = new SimpleRing();
        simpleInventory = new SimpleInventory();
        simpleKlass     = new SimpleKlass();

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

                game.activeGridScreen.conditionsHandler.combat().iron().goToCombat(attackingUnit, self);

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

    public void applyDamage(int dmg) {
        this.rollingHP -= dmg;
        if(rollingHP <= 0) this.kill(); // KILL HIM
    }

    public void kill() {
        this.addAction(Actions.sequence(Actions.fadeOut(1), Actions.removeActor()));
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
        if(stunCounter <= 0) {
            canStillMoveThisTurn = true;
            standardColor();
        } else {
            Gdx.app.log("unit", "stun ticked down");
            stunCounter--;
        }

    }
    public void setRollingHP(int newHP) {
        rollingHP = newHP;
    }
    public void setFacedDirection(MotionState motionState) {
        this.motionState = motionState;
    }

    // --GETTERS--
    public boolean isABoss() { return isABoss; }
    public AIType getAiType() { return aiType; }
    public MapObject getOccupyingMapObject() {
        return occupyingMapObject;
    }
    public int getLevel() {return level;}

    public int getRollingHP() {return rollingHP;}
    public boolean canMove() { return canStillMoveThisTurn; }
    public MotionState getFacedDirection() { return motionState; }

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
    public CharSequence bio() {
        return bio;
    }
    public IronMode iron() {
        if(!iron) {
            ironMode = new IronMode(this);
        }
        return ironMode;
    }

    public static class IronMode {

        private iron_Item iron_equippedWeapon;

        private int level;
        private int mobility;
        private int strength;
        private int defense;
        private int dexterity;
        private int speed;
        private int maxHP;
        private int rollingHP;
        private int exp;
        private int constitution;

        private SimpleUnit parent;

        private HashMap<StatTypes, Float> growthRates;
        private HashMap<WeaponType, WeaponRank> weaponProficiencyLevels;
        private HashMap<WeaponType, Integer> weaponProficiencyExp;

        private IronKlass ironKlass;
        private IronInventory ironInventory;

        public IronMode(SimpleUnit parent) {
            iron_equippedWeapon = new iron_Item(iron_ItemType.Weapon);
            mobility      = 5;
            strength      = 3;
            defense       = 1;
            maxHP         = 10;
            dexterity     = 3;
            speed         = 3;
            exp           = 0;
            constitution  = 5;
            rollingHP = maxHP;

            this.parent = parent;

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
        }

        public void levelUp() {
            Gdx.app.log("unit", "Level up!");

            this.level++;

            this.exp -= 100;
            final int remainder = this.exp;

            final Random random = new Random();

            final float growthChanceStr = random.nextFloat();
            Gdx.app.log("unit", "strength " + growthChanceStr);
            if(growthChanceStr < growthRates.get(StatTypes.STRENGTH)) {
                Gdx.app.log("unit", "Ye boi str go up!");
                this.strength++;
                if(growthChanceStr < growthRates.get(StatTypes.STRENGTH) / 2) {
                    Gdx.app.log("unit", "POG!");
                    this.strength++;
                    if(growthChanceStr < growthRates.get(StatTypes.STRENGTH) / 4) {
                        Gdx.app.log("unit", "POGGERS!");
                        this.strength++;
                    }
                }
            }

            final float growthChanceDef = random.nextFloat();
            Gdx.app.log("unit", "defense " + growthChanceDef);
            if(growthChanceDef < growthRates.get(StatTypes.DEFENSE)) {
                Gdx.app.log("unit", "Ye boi defense gone up!");
                this.defense++;
                if(growthChanceDef < growthRates.get(StatTypes.DEFENSE) / 2) {
                    Gdx.app.log("unit", "POG!");
                    this.defense++;
                    if(growthChanceDef < growthRates.get(StatTypes.DEFENSE) / 4) {
                        Gdx.app.log("unit", "POGGERS!");
                        defense++;
                    }
                }
            }

            final float growthChanceSkl = random.nextFloat();
            Gdx.app.log("unit", "skill " + growthChanceSkl);
            if(growthChanceSkl < growthRates.get(StatTypes.DEXTERITY)) {
                Gdx.app.log("unit", "Ye boi skill get big!");
                this.dexterity++;
                if(growthChanceSkl < growthRates.get(StatTypes.DEXTERITY) / 2) {
                    Gdx.app.log("unit", "POG!");
                    this.dexterity++;
                    if(growthChanceSkl < growthRates.get(StatTypes.DEXTERITY) / 4) {
                        Gdx.app.log("unit", "POGGERS!");
                        this.dexterity++;
                    }
                }
            }

            final float growthChanceHP = random.nextFloat();
            Gdx.app.log("unit", "" + growthChanceHP);
            if(growthChanceHP < growthRates.get(StatTypes.HEALTH)) {
                Gdx.app.log("unit", "Ye boi defense gone up!");
                this.maxHP++;
                if(growthChanceHP < growthRates.get(StatTypes.HEALTH) / 2) {
                    Gdx.app.log("unit", "POG!");
                    this.maxHP++;
                    if(growthChanceHP < growthRates.get(StatTypes.HEALTH) / 4) {
                        Gdx.app.log("unit", "POGGERS!");
                        this.maxHP++;
                    }
                }
            }

            final float growthChanceSpd = random.nextFloat();
            Gdx.app.log("unit", "" + growthChanceSpd);
            if(growthChanceSpd < growthRates.get(StatTypes.SPEED)) {
                Gdx.app.log("unit", "Ye boi spd gone up!");
                this.speed++;
                if(growthChanceSpd < growthRates.get(StatTypes.SPEED) / 2) {
                    Gdx.app.log("unit", "POG!");
                    this.speed++;
                    if(growthChanceSpd < growthRates.get(StatTypes.SPEED) / 4) {
                        Gdx.app.log("unit", "POGGERS!");
                        this.speed++;
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

        //---GETTERS && SETTERS---
        public void setDefense(int iron_baseDefense)     { this.defense = iron_baseDefense; }
        public void setMaxHP(int iron_baseMaxHP)         { this.maxHP = iron_baseMaxHP; }
        public void setMobility(int iron_mobility)       { this.mobility = iron_mobility; }
        public void setDexterity(int iron_baseDexterity) { this.dexterity = iron_baseDexterity; }
        public void setSpeed(int iron_baseSpeed)         { this.speed = iron_baseSpeed; }
        public void setStrength(int iron_baseStrength)   { this.strength = iron_baseStrength; }

        public int baseDefense()   { return defense; }
        public int baseMaxHP()     { return maxHP; }
        public int baseMobility()  { return mobility;}
        public int baseDexterity() { return dexterity; }
        public int baseSpeed()     { return speed; }
        public int baseStrength()  { return strength; }

        //--CALCULATORS--
        public int getHitRate() { return (modifiedDexterity() * 2) + getEquippedWeapon().getWeaponAccuracy(); }
        public int getEvade() { return (getAttackSpeed() * 2) + parent.occupyingTile.evadeBonus; }
        public int getAttackSpeed() { return modifiedSpeed() - getBurden(); }
        public int getBurden() {
            int burden = 0;
            for(iron_Item ironItem : ironInventory.items()) {
                burden+= ironItem.getWeight();
            }
            burden -= constitution;
            if(burden < 0) {
                burden = 0;
            }
            return burden;
        }
        public int getReach() {
            int reach;
            if(parent.isOccupyingMapObject) {
                reach = parent.occupyingMapObject.reach;
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
        public int getAttackPower() {
            // todo: account for weapon triangle and effective advantage (eg, bows vs fliers)
            return modifiedStrength();
        }
        public int getDefensePower() { return modifiedDefense() + parent.occupyingTile.defenseBonus; }
        public int modifiedStrength() {
            int modStr = strength;
            modStr += getEquippedWeapon().getStrengthBonus();
            return modStr;
        }
        public int modifiedDefense() {
            int modDef = defense;
            modDef += getEquippedWeapon().getDefenseBonus();
            return modDef;
        }
        public int modifiedDexterity() {
            int modSkl = dexterity;
            modSkl += getEquippedWeapon().getDexterityBonus();
            return modSkl;
        }
        public int modifiedMaxHP() {
            int modHP = maxHP;
            modHP += getEquippedWeapon().getHealthBonus();
            return modHP;
        }
        public int modifiedSpeed() {
            int modSpd = speed;
            modSpd -= (int) getEquippedWeapon().getWeight();
            return modSpd;
        }
        public float modifiedMobility() {
            float modMov = mobility;
            modMov -= getEquippedWeapon().getWeight();
            return modMov;
        }

        //--SORTERS--
        public iron_Item getEquippedWeapon() {
            if(iron_equippedWeapon != null) {
                return iron_equippedWeapon;
            } else {
                for(iron_Item ironItem : ironInventory.items()) {
                    if(ironItem.ironItemType == iron_ItemType.Weapon){
                        return ironItem;
                    }
                }
            }
            return new iron_Item(iron_ItemType.Weapon);
        }

    }
}
