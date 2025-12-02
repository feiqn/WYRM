package com.feiqn.wyrm.models.unitdata.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.AIPersonality;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.BattlePreviewPopup;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.StatusEffect;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.BallistaActionsPopup;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.itemdata.iron.IronInventory;
import com.feiqn.wyrm.models.itemdata.iron.iron_Item;
import com.feiqn.wyrm.models.itemdata.iron.iron_ItemType;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.amulets.SimpleAmulet;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.bracelets.SimpleBracelet;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.rings.SimpleRing;
import com.feiqn.wyrm.models.itemdata.simple.equipment.armor.ArmorCategory;
import com.feiqn.wyrm.models.itemdata.simple.equipment.armor.SimpleArmor;
import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.SimpleKlass;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponRank;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponCategory;
import com.feiqn.wyrm.models.itemdata.simple.items.SimpleInventory;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.MapObject;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.iron.classdata.IronKlass;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class SimpleUnit extends Image {

    // A classless unit with no weapons.

    public enum AnimationState {
        WALKING_NORTH,
        WALKING_SOUTH,
        WALKING_EAST,
        WALKING_WEST,
        IDLE,
        FLOURISH,
    }

    protected AIPersonality aiPersonality;

    protected Array<Vector2> patrolPoints;
    private int patrolIndex;

    protected Animation<TextureRegionDrawable>
            idleAnimation,
            flourishAnimation,
            walkingNorthAnimation,
            walkingSouthAnimation,
            walkingEastAnimation,
            walkingWestAnimation;

    protected LogicalTile occupyingTile;

    public MapObject occupyingMapObject; // TODO: protected

    private boolean canStillMoveThisTurn;
    protected boolean isABoss;

    public String characterName; // TODO: protected

    protected String bio;
    protected String uniqueID;

    public Boolean isOccupyingMapObject;

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
    protected int burnCounter;
    protected int poisonCounter;
    protected int chillCounter;
    protected int actionsLeft;

    private float previousAnimationChangeClockTime,
                  timeInCurrentAnimationState = 0;
    protected float hoverTime;

    protected SimpleWeapon    simpleWeapon;
    protected SimpleArmor     simpleArmor;
    protected SimpleRing      simpleRing;
    protected SimpleAmulet    simpleAmulet;
    protected SimpleBracelet  simpleBracelet;
    protected SimpleInventory simpleInventory;
    protected SimpleKlass     simpleKlass;

    protected boolean dragged;
    protected boolean clicked;

    protected boolean burned;
    protected boolean poisoned;
    protected boolean soulBranded;
    protected boolean stunned;
    protected boolean chilled;
    private   boolean iron;

    private Array<StatusEffect> statusEffects;

    protected boolean hoveredOver;
    protected boolean hoverActivated;

    protected boolean wide;

    protected HashMap<ArmorCategory, Boolean> armorTraining;
    protected HashMap<WeaponCategory, Boolean> weaponTraining;

    public UnitRoster rosterID;

    protected final WYRMGame game;

    private final SimpleUnit self = this;
    private SimpleUnit brandingUnit; // unit who applied soulbrand effect

    protected Abilities ability;

    protected AnimationState animationState;

    protected TeamAlignment teamAlignment;

    public InputListener attackListener;

    protected TextureRegion thumbnail;

    private final Array<LogicalTile> highlighted = new Array<>();

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
        // TODO: refactor to constructor
        // TODO: programmatically fill animations from roster once all animations have been declared in asset handler (later)

        idle();

        characterName = "Mr. Timn";
        bio = "He wants in on that party, boy.";
        uniqueID = "";

        occupyingTile = new LogicalTile(game, -1,-1);
        isOccupyingMapObject = false;

        setSize(1,1);

        aiPersonality = AIPersonality.STILL;

        patrolPoints = new Array<>();
        patrolIndex = 0;

        canStillMoveThisTurn = true;
        teamAlignment = TeamAlignment.OTHER;

        rosterID = UnitRoster.MR_TIMN;
        isABoss = false;

        row    = 0;
        column = 0;

        simple_Speed      = 2;
        simple_Defense    = 1;
        simple_Health     = 2;
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

        hoverActivated = false;
        hoveredOver = false;
        hoverTime = 0;

        dragged = false;
        clicked = false;

        wide = false;

        statusEffects = new Array<>();

        simpleWeapon    = new SimpleWeapon();
        simpleArmor     = new SimpleArmor();
        simpleAmulet    = new SimpleAmulet();
        simpleBracelet  = new SimpleBracelet();
        simpleRing      = new SimpleRing();
        simpleInventory = new SimpleInventory();
        simpleKlass     = new SimpleKlass();

        armorTraining = new HashMap<>();
        armorTraining.put(ArmorCategory.HEAVY, false);
        armorTraining.put(ArmorCategory.MEDIUM, false);
        armorTraining.put(ArmorCategory.LIGHT, false);
        armorTraining.put(ArmorCategory.CLOTH, true);

        weaponTraining = new HashMap<>();
        for(WeaponCategory category : WeaponCategory.values()) {
            weaponTraining.put(category, false);
        }

        addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hoveredOver = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                hoveredOver = false;
            }

            @Override
            public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                dragged = true;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dragged = false;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button)  {
                if(dragged || !canStillMoveThisTurn) {
                    dragged = false;
                    clicked = false;
                    return;
                }

                clicked = true;
                for(LogicalTile tile : highlighted) {
                    tile.clearHighlight();
                }
                highlighted.clear();

                final GridScreen ags = game.activeGridScreen;

//                Gdx.app.log("unit", "touch up, " + ags.getInputMode());

                switch(ags.getInputMode()) {

                    case STANDARD:
                        switch(ags.getMovementControl()) {

                            case COMBAT:
                                if(ags.conditions().tickCount() == self.modifiedSimpleSpeed()) {
                                    // Only move if it's your turn
                                    if(self.teamAlignment == TeamAlignment.PLAYER) {
                                        // Unit is player's own unit
                                        if(!isOccupyingMapObject) {
                                            ags.setInputMode(GridScreen.InputMode.UNIT_SELECTED);
                                            ags.activeUnit = self;
                                            ags.highlightAllTilesUnitCanAccess(self);
                                            for(SimpleUnit enemy : ags.attackableUnits) {
                                                if(enemy.teamAlignment == TeamAlignment.ENEMY || enemy.teamAlignment == TeamAlignment.OTHER) {
                                                    enemy.addListener(new InputListener() {

                                                        @Override
                                                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                                                        @Override
                                                        public void touchUp(InputEvent event, float x, float y, int point, int button) {
                                                            ags.setInputMode(GridScreen.InputMode.MENU_FOCUSED);
                                                            ags.activeUnit = null;
                                                            ags.removeTileHighlighters();
                                                            final int originCX = getColumnX();
                                                            final int originRY = getRowY();

                                                            final RunnableAction finish = new RunnableAction();
                                                            finish.setRunnable(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    ags.hud().addPopup(new BattlePreviewPopup(game, self, enemy, originRY, originCX));

                                                                }
                                                            });

                                                            ags.getLogicalMap().moveAlongPath(self, ags.getLogicalMap().pathToNearestNeighborInRange(self, enemy.getOccupyingTile()), finish, false);

                                                        }

                                                    });

                                                }
                                            }
                                            flourish();
                                        } else {
                                            switch(occupyingMapObject.objectType) {

                                                case BALLISTA:
                                                    final BallistaActionsPopup bap = new BallistaActionsPopup(game, self, occupyingMapObject);
                                                    ags.hudStage.addActor(bap);
                                                    break;

                                                case DOOR:
                                                default:
                                                    break;

                                            }
                                        }
                                    }
                                }
                                break;

                            case FREE_MOVE:
                                if(self.teamAlignment == TeamAlignment.PLAYER) {

                                }
                                break;
                        }

                        break;
                    case CUTSCENE:
                    case MENU_FOCUSED:
                    case UNIT_SELECTED:
                    case LOCKED:
                        break;
                }

            }
        });
    }

    protected void generateAnimations() {
        idleAnimation = game.assetHandler.getAnimation(rosterID, AnimationState.IDLE);
        flourishAnimation = game.assetHandler.getAnimation(rosterID, AnimationState.FLOURISH);
        walkingEastAnimation = game.assetHandler.getAnimation(rosterID, AnimationState.WALKING_EAST);
        walkingNorthAnimation = game.assetHandler.getAnimation(rosterID, AnimationState.WALKING_NORTH);
        walkingSouthAnimation = game.assetHandler.getAnimation(rosterID, AnimationState.WALKING_SOUTH);
        walkingWestAnimation = game.assetHandler.getAnimation(rosterID, AnimationState.WALKING_WEST);
    }

    @Override
    public void act(float delta) {
        if(!hoveredOver && hoverTime > 0) { // tick down
            hoverTime -= delta;
            if(hoverTime <= 0) {
                hoverTime = 0;
                unHover();
            }
        } else if(!hoverActivated && hoveredOver && hoverTime < .1f) { // tick up
            hoverTime += delta;
            if(hoverTime >= .1f) {
                hoverOver();
            }
        }

        final float timeDifference = game.activeGridScreen.getClock() - previousAnimationChangeClockTime;
        timeInCurrentAnimationState += delta;

        switch(animationState) {
            case IDLE:
                try {
                    if(timeDifference >= idleAnimation.getFrameDuration()) {
                        this.setDrawable(idleAnimation.getKeyFrame(timeInCurrentAnimationState, true));
                        previousAnimationChangeClockTime = game.activeGridScreen.getClock();
                    }
                } catch(Exception ignored){}
                break;
            case FLOURISH:
                try {
                    if(timeDifference >= flourishAnimation.getFrameDuration()) {
                        this.setDrawable(flourishAnimation.getKeyFrame(timeInCurrentAnimationState, true));
                        previousAnimationChangeClockTime = game.activeGridScreen.getClock();
                    }
                } catch(Exception ignored){}
                break;
            case WALKING_EAST:
                try {
                    if(timeDifference >= walkingEastAnimation.getFrameDuration()) {
                        this.setDrawable(walkingEastAnimation.getKeyFrame(timeInCurrentAnimationState, true));
                        previousAnimationChangeClockTime = game.activeGridScreen.getClock();
                    }
                } catch(Exception ignored){}
                break;
            case WALKING_WEST:
                try {
                    if(timeDifference >= walkingWestAnimation.getFrameDuration()) {
                        this.setDrawable(walkingWestAnimation.getKeyFrame(timeInCurrentAnimationState, true));
                        previousAnimationChangeClockTime = game.activeGridScreen.getClock();
                    }
                } catch(Exception ignored){}
                break;
            case WALKING_NORTH:
                try {
                    if(timeDifference >= walkingNorthAnimation.getFrameDuration()) {
                        this.setDrawable(walkingNorthAnimation.getKeyFrame(timeInCurrentAnimationState, true));
                        previousAnimationChangeClockTime = game.activeGridScreen.getClock();
                    }
                } catch(Exception ignored){}
                break;
            case WALKING_SOUTH:
                try {
                    if(timeDifference >= walkingSouthAnimation.getFrameDuration()) {
                        this.setDrawable(walkingSouthAnimation.getKeyFrame(timeInCurrentAnimationState, true));
                        previousAnimationChangeClockTime = game.activeGridScreen.getClock();
                    }
                } catch(Exception ignored){}
                break;
            default:
                break;
        }
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(hoveredOver) {
            // TODO: apply shader
        }

        // TODO: (else)if cases for different shaders, including team alignment

        super.draw(batch, parentAlpha);
        if(hoveredOver) {
            // remove shader
        }
    }


    protected void hoverOver() {
        hoverActivated = true;

        if(self.animationState == AnimationState.IDLE) {
            flourish();
        }

        game.activeGridScreen.hud().updateHoveredUnitInfoPanel(self);
        game.activeGridScreen.hoveredUnit = self;

        if(game.activeGridScreen.activeUnit != null) return;
        if(game.activeGridScreen.getInputMode() != GridScreen.InputMode.STANDARD) return;

        game.activeGridScreen.getRecursionHandler().recursivelySelectReachableTiles(self);

        for(LogicalTile tile : game.activeGridScreen.reachableTiles) {
            tile.highlight();
            highlighted.add(tile);
        }
    }

    protected void unHover() {
        hoverActivated = false;

        if(self.animationState == AnimationState.FLOURISH && game.activeGridScreen.activeUnit != this) {
            idle();
        }

        game.activeGridScreen.hoveredUnit = null;
        for(LogicalTile tile : highlighted) {
            tile.clearHighlight();
        }
        highlighted.clear();

//        if(clicked && teamAlignment != TeamAlignment.PLAYER) {
        // TODO: add unit's reachable tiles to danger heatmap display
//            clicked = false;
//            unHover();
//        }
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
        game.activeGridScreen.getLogicalMap().getTileAtPositionXY(this.getColumnX(),this.getRowY()).setUnoccupied();
        game.activeGridScreen.conditions().teams().removeUnitFromTeam(this);
        game.activeGridScreen.conditions().removeFromTurnOrder(this);
    }

//    public void constructAndAddAttackListener(final SimpleUnit attackingUnit) {
//
//        this.redColor();
//        attackListener = new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                setCannotMove();
//
//                game.activeGridScreen.removeTileHighlighters();
//                game.activeGridScreen.clearAttackableEnemies();
//
//                game.activeGridScreen.conditions().combat().iron().goToCombat(attackingUnit, self);
//
//                game.activeGridScreen.checkIfAllUnitsHaveMovedAndPhaseShouldChange();
//
//                game.activeGridScreen.checkLineOrder();
//
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int point, int button) {
//            }
//        };
//
//        self.addListener(attackListener);
//    }

    // --SETTERS & INCREMENTS--
    public void giveUniqueID(String id) {
        this.uniqueID = id;
    }
    public void setAnimationState(AnimationState state) {
        previousAnimationChangeClockTime = 0;
        this.animationState = state;
    }
    public void idle() {
        if(this.animationState == AnimationState.IDLE) return;
        try {
            this.setDrawable(idleAnimation.getKeyFrame(0));
            previousAnimationChangeClockTime = game.activeGridScreen.getClock();
        } catch (Exception ignored) {}
        timeInCurrentAnimationState = 0;
        this.animationState = AnimationState.IDLE;
    }
    public void flourish() {
        if(this.animationState == AnimationState.FLOURISH) return;
        try {
            this.setDrawable(flourishAnimation.getKeyFrame(0));
            previousAnimationChangeClockTime = game.activeGridScreen.getClock();
        } catch (Exception ignored) {}
        timeInCurrentAnimationState = 0;
        this.animationState = AnimationState.FLOURISH;
    }
    public void faceWest() {
        if(this.animationState == AnimationState.WALKING_WEST) return;
        try {
            this.setDrawable(walkingWestAnimation.getKeyFrame(0));
            previousAnimationChangeClockTime = game.activeGridScreen.getClock();
        } catch (Exception ignored) {}
        timeInCurrentAnimationState = 0;
        this.animationState = AnimationState.WALKING_WEST;
    }
    public void faceEast() {
        if(this.animationState == AnimationState.WALKING_EAST) return;

        try {
            this.setDrawable(walkingEastAnimation.getKeyFrame(0));
            previousAnimationChangeClockTime = game.activeGridScreen.getClock();
        } catch (Exception ignored) {}
        timeInCurrentAnimationState = 0;
        this.animationState = AnimationState.WALKING_EAST;
    }
    public void faceNorth() {
        if(this.animationState == AnimationState.WALKING_NORTH) return;

        try {
            this.setDrawable(walkingNorthAnimation.getKeyFrame(0));
            previousAnimationChangeClockTime = game.activeGridScreen.getClock();
        } catch (Exception ignored) {}
        timeInCurrentAnimationState = 0;
        this.animationState = AnimationState.WALKING_NORTH;
    }
    public void faceSouth() {
        if(this.animationState == AnimationState.WALKING_SOUTH) return;
        try {
            this .setDrawable(walkingSouthAnimation.getKeyFrame(0));
            previousAnimationChangeClockTime = game.activeGridScreen.getClock();
        } catch (Exception ignored) {}
        timeInCurrentAnimationState = 0;
        this.animationState = AnimationState.WALKING_SOUTH;
    }
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
    public void setAIType(AIPersonality newType) {
        this.aiPersonality = newType;
    }
    public void addPatronPoint(Vector2 point) {
        patrolPoints.add(point);
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
    public void occupyTile(LogicalTile tile) {
        occupyingTile = tile;
        setColumn(tile.getColumnX());
        setRow(tile.getRowY());
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
        if(!stunned) {
            canStillMoveThisTurn = true;
            standardColor();
        }
        tickDownEffects();
    }
    public void setRollingHP(int newHP) {
        rollingHP = newHP;
    }
    public void burn() {
        if(!burned) burned = true;
        burnCounter += 4;
    }
    public void poison() {
        if(!poisoned) poisoned = true;
        poisonCounter += 3;
    }
    public void stun() {
        if(!stunned) stunned = true;
        if(canStillMoveThisTurn) {
            setCannotMove();
        } else {
            stunCounter += 1;
        }

    }
    public void chill() {
        if(!chilled) chilled = true;
        chillCounter += 2;
    }
    public void brand(SimpleUnit brandingUnit) {
        if(!soulBranded) {
            soulBranded = true;
            this.brandingUnit = brandingUnit;
        }
    }
    private void tickDownEffects() {
        if(burned) {
            burnCounter--;
            if(burnCounter <= 0) {
                burned = false;
                burnCounter = 0;
            }
        }
        if(poisoned) {
            poisonCounter--;
            applyDamage(2);

            final Label damageLabel = new Label("Poisoned! 2", game.assetHandler.menuLabelStyle);
            damageLabel.setColor(Color.GREEN);
            damageLabel.setFontScale(3);
            damageLabel.setPosition(Gdx.graphics.getWidth() * .2f, Gdx.graphics.getHeight() * .6f);
            damageLabel.addAction(Actions.sequence(
                Actions.parallel(
                    Actions.moveTo(damageLabel.getX(), Gdx.graphics.getHeight() * .8f, 3),
                    Actions.fadeOut(3)
                ),
                Actions.removeActor()
            ));

            if(poisonCounter <= 0) {
                poisoned = false;
                poisonCounter = 0;
            }
        }
        if(chilled) {
            chillCounter--;
            if(chillCounter <= 0) {
                chilled = false;
                chillCounter = 0;
            }
        }
        if(stunned) {
            stunCounter--;
            if(stunCounter <= 0) {
                stunned = false;
                stunCounter = 0;
            }
        }
    }

    // --GETTERS--
    public boolean hasUniqueID(String id) { return (Objects.equals(uniqueID, id)); }
    public boolean hasUniqueID() { return (!uniqueID.isEmpty()); }
    public boolean isABoss() { return isABoss; }
    public AIPersonality getAiType() { return aiPersonality; }
    public LogicalTile getOccupyingTile() {return occupyingTile;}
    public MapObject getOccupyingMapObject() {
        return occupyingMapObject;
    }

    public int getRollingHP() {return rollingHP;}
    public boolean canMove() { return canStillMoveThisTurn; }
    public AnimationState getFacedDirection() { return animationState; }

    public MovementType getMovementType() { return simpleKlass.movementType(); }
    public int getColumnX() { return column; }
    public int getRowY() { return row; }
    public TeamAlignment getTeamAlignment() { return teamAlignment; }
    public SimpleInventory getInventory() { return simpleInventory; }
    public TextureRegion getThumbnail() { return thumbnail; }

    public Array<Vector2> getPatrolPoints() {
        return patrolPoints;
    }
    public Vector2 getNextPatrolPoint() {
        if(patrolPoints.size == 0) return new Vector2(getColumnX(),getRowY());

        patrolIndex += 1;
        if(patrolIndex-1 > patrolPoints.size) {
            patrolIndex = 0;
        }
        return patrolPoints.get(patrolIndex-1);
    }

    /** This is the part where I started believing in myself, for better or worse.
     */
    public Boolean proficient(ArmorCategory arm) { return armorTraining.get(arm); }

    public Array<Abilities> getAbilities() {
        Array<Abilities> abilities = new Array<>();
        if(ability != null) {
            abilities.add(ability);
        }
        if(simpleWeapon.getAbility() != null) {
            abilities.add(simpleWeapon.getAbility());
        }
        return abilities;
    }

    public SimpleWeapon simpleWeapon()       { return simpleWeapon;    }
    public SimpleArmor simpleArmor()         { return simpleArmor;     }
    public SimpleRing simpleRing()           { return simpleRing;      }
    public SimpleAmulet simpleAmulet()       { return simpleAmulet;    }
    public SimpleBracelet simpleBracelet()   { return simpleBracelet;  }
    public SimpleInventory simpleInventory() { return simpleInventory; }
    public SimpleKlass simpleKlass()         { return simpleKlass;     }

    public int baseSimpleSpeed()      { return simple_Speed;      } // TODO: modifiers from weight, etc? probably not tbh
    public int baseSimpleStrength()   { return simple_Strength;   }
    public int baseSimpleDefense()    { return simple_Defense;    }
    public int baseSimpleMagic()      { return simple_Magic;      }
    public int baseSimpleHealth()     { return simple_Health;     }
    public int baseSimpleResistance() { return simple_Resistance; }

    public boolean isBurned()      { return burned;      }
    public boolean isPoisoned()    { return poisoned;    }
    public boolean isChilled()     { return chilled;     }
    public boolean isStunned()     { return stunned;     }
    public boolean isSoulBranded() { return soulBranded; }

    public boolean brandedBy(SimpleUnit unit) { return unit == brandingUnit; }

    public int getStunCount()     { return stunCounter;   }
    public int getBurnCounter()   { return burnCounter;   }
    public int getChillCounter()  { return chillCounter;  }
    public int getPoisonCounter() { return poisonCounter; }

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

    public boolean isWide() {
        return wide;
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
        private HashMap<WeaponCategory, WeaponRank> weaponProficiencyLevels;
        private HashMap<WeaponCategory, Integer> weaponProficiencyExp;

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
            level         = 1;
            rollingHP = maxHP;

            this.parent = parent;

            growthRates = new HashMap<>();
            growthRates.put(StatTypes.SPEED, 0.5f);
            growthRates.put(StatTypes.STRENGTH, 0.5f);
            growthRates.put(StatTypes.DEFENSE, 0.5f);
            growthRates.put(StatTypes.DEXTERITY, 0.5f);
            growthRates.put(StatTypes.HEALTH, 0.5f);

            weaponProficiencyLevels = new HashMap<>();
            weaponProficiencyLevels.put(WeaponCategory.AXE, WeaponRank.F);
            weaponProficiencyLevels.put(WeaponCategory.LANCE, WeaponRank.F);
            weaponProficiencyLevels.put(WeaponCategory.SWORD, WeaponRank.F);
            weaponProficiencyLevels.put(WeaponCategory.BOW, WeaponRank.F);
            weaponProficiencyLevels.put(WeaponCategory.HANDS, WeaponRank.F);
            weaponProficiencyLevels.put(WeaponCategory.MAGE_ANIMA, WeaponRank.F);
            weaponProficiencyLevels.put(WeaponCategory.MAGE_DARK, WeaponRank.F);
            weaponProficiencyLevels.put(WeaponCategory.MAGE_LIGHT, WeaponRank.F);
            weaponProficiencyLevels.put(WeaponCategory.SHIELD, WeaponRank.F);
            weaponProficiencyLevels.put(WeaponCategory.HERBAL_FLORAL, WeaponRank.F);
            weaponProficiencyLevels.put(WeaponCategory.HERBAL_POTION, WeaponRank.F);

            weaponProficiencyExp = new HashMap<>();
            weaponProficiencyExp.put(WeaponCategory.AXE, 0);
            weaponProficiencyExp.put(WeaponCategory.LANCE, 0);
            weaponProficiencyExp.put(WeaponCategory.SWORD, 0);
            weaponProficiencyExp.put(WeaponCategory.BOW, 0);
            weaponProficiencyExp.put(WeaponCategory.HANDS, 0);
            weaponProficiencyExp.put(WeaponCategory.MAGE_LIGHT, 0);
            weaponProficiencyExp.put(WeaponCategory.MAGE_DARK, 0);
            weaponProficiencyExp.put(WeaponCategory.MAGE_ANIMA, 0);
            weaponProficiencyExp.put(WeaponCategory.SHIELD, 0);
            weaponProficiencyExp.put(WeaponCategory.HERBAL_POTION, 0);
            weaponProficiencyExp.put(WeaponCategory.HERBAL_FLORAL, 0);
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

        public void addWeaponProficiencyExp(WeaponCategory type, int exp) {
            switch(type) {
                case LANCE:
                    weaponProficiencyExp.put(WeaponCategory.LANCE, weaponProficiencyExp.get(WeaponCategory.LANCE) + exp);
                    if(weaponProficiencyExp.get(WeaponCategory.LANCE) >= 100) {
                        increaseWeaponProficiency(WeaponCategory.LANCE);
                    }
                    break;
                case BOW:
                    weaponProficiencyExp.put(WeaponCategory.BOW, weaponProficiencyExp.get(WeaponCategory.BOW) + exp);
                    if(weaponProficiencyExp.get(WeaponCategory.BOW) >= 100) {
                        increaseWeaponProficiency(WeaponCategory.BOW);
                    }
                    break;
                case AXE:
                    weaponProficiencyExp.put(WeaponCategory.AXE, weaponProficiencyExp.get(WeaponCategory.AXE) + exp);
                    if(weaponProficiencyExp.get(WeaponCategory.AXE) >= 100) {
                        increaseWeaponProficiency(WeaponCategory.AXE);
                    }
                    break;
                case SWORD:
                    weaponProficiencyExp.put(WeaponCategory.SWORD, weaponProficiencyExp.get(WeaponCategory.SWORD) + exp);
                    if(weaponProficiencyExp.get(WeaponCategory.SWORD) >= 100) {
                        increaseWeaponProficiency(WeaponCategory.SWORD);
                    }
                    break;
                case SHIELD:
                    weaponProficiencyExp.put(WeaponCategory.SHIELD, weaponProficiencyExp.get(WeaponCategory.SHIELD) + exp);
                    if(weaponProficiencyExp.get(WeaponCategory.SHIELD) >= 100) {
                        increaseWeaponProficiency(WeaponCategory.SHIELD);
                    }
                    break;
                case MAGE_DARK:
                    weaponProficiencyExp.put(WeaponCategory.MAGE_DARK, weaponProficiencyExp.get(WeaponCategory.MAGE_DARK) + exp);
                    if(weaponProficiencyExp.get(WeaponCategory.MAGE_DARK) >= 100) {
                        increaseWeaponProficiency(WeaponCategory.MAGE_DARK);
                    }
                    break;
                case MAGE_ANIMA:
                    weaponProficiencyExp.put(WeaponCategory.MAGE_ANIMA, weaponProficiencyExp.get(WeaponCategory.MAGE_ANIMA) + exp);
                    if(weaponProficiencyExp.get(WeaponCategory.MAGE_ANIMA) >= 100) {
                        increaseWeaponProficiency(WeaponCategory.MAGE_ANIMA);
                    }
                    break;
                case MAGE_LIGHT:
                    weaponProficiencyExp.put(WeaponCategory.MAGE_LIGHT, weaponProficiencyExp.get(WeaponCategory.MAGE_LIGHT) + exp);
                    if(weaponProficiencyExp.get(WeaponCategory.MAGE_LIGHT) >= 100) {
                        increaseWeaponProficiency(WeaponCategory.MAGE_LIGHT);
                    }
                    break;
                case HERBAL_FLORAL:
                    weaponProficiencyExp.put(WeaponCategory.HERBAL_FLORAL, weaponProficiencyExp.get(WeaponCategory.HERBAL_FLORAL) + exp);
                    if(weaponProficiencyExp.get(WeaponCategory.HERBAL_FLORAL) >= 100) {
                        increaseWeaponProficiency(WeaponCategory.HERBAL_FLORAL);
                    }
                    break;
                case HERBAL_POTION:
                    weaponProficiencyExp.put(WeaponCategory.HERBAL_POTION, weaponProficiencyExp.get(WeaponCategory.HERBAL_POTION) + exp);
                    if(weaponProficiencyExp.get(WeaponCategory.HERBAL_POTION) >= 100) {
                        increaseWeaponProficiency(WeaponCategory.HERBAL_POTION);
                    }
                    break;
                case HANDS:
                    weaponProficiencyExp.put(WeaponCategory.HANDS, weaponProficiencyExp.get(WeaponCategory.HANDS) + exp);
                    if(weaponProficiencyExp.get(WeaponCategory.HANDS) >= 100) {
                        increaseWeaponProficiency(WeaponCategory.HANDS);
                    }
                    break;

            }
        }

        private void increaseWeaponProficiency(WeaponCategory type) {
            switch(type) {
                case AXE:

                    final int remainder_axe = weaponProficiencyExp.get(WeaponCategory.AXE) - 100;

                    switch(weaponProficiencyLevels.get(WeaponCategory.AXE)) {
                        case S:
                            break;
                        case A:
                            weaponProficiencyLevels.put(WeaponCategory.AXE, WeaponRank.S);
                            break;
                        case B:
                            weaponProficiencyLevels.put(WeaponCategory.AXE, WeaponRank.A);
                            break;
                        case C:
                            weaponProficiencyLevels.put(WeaponCategory.AXE, WeaponRank.B);
                            break;
                        case D:
                            weaponProficiencyLevels.put(WeaponCategory.AXE, WeaponRank.C);
                            break;
                        case E:
                            weaponProficiencyLevels.put(WeaponCategory.AXE, WeaponRank.D);
                            break;
                        case F:
                            weaponProficiencyLevels.put(WeaponCategory.AXE, WeaponRank.E);
                            break;
                    }

                    addWeaponProficiencyExp(WeaponCategory.AXE, remainder_axe);
                    break;

                case BOW:

                    final int remainder_bow = weaponProficiencyExp.get(WeaponCategory.BOW) - 100;

                    switch(weaponProficiencyLevels.get(WeaponCategory.BOW)) {
                        case S:
                            break;
                        case A:
                            weaponProficiencyLevels.put(WeaponCategory.BOW, WeaponRank.S);
                            break;
                        case B:
                            weaponProficiencyLevels.put(WeaponCategory.BOW, WeaponRank.A);
                            break;
                        case C:
                            weaponProficiencyLevels.put(WeaponCategory.BOW, WeaponRank.B);
                            break;
                        case D:
                            weaponProficiencyLevels.put(WeaponCategory.BOW, WeaponRank.C);
                            break;
                        case E:
                            weaponProficiencyLevels.put(WeaponCategory.BOW, WeaponRank.D);
                            break;
                        case F:
                            weaponProficiencyLevels.put(WeaponCategory.BOW, WeaponRank.E);
                            break;
                    }

                    addWeaponProficiencyExp(WeaponCategory.BOW, remainder_bow);
                    break;

                case LANCE:

                    final int remainder_lance = weaponProficiencyExp.get(WeaponCategory.LANCE) - 100;

                    switch(weaponProficiencyLevels.get(WeaponCategory.LANCE)) {
                        case S:
                            break;
                        case A:
                            weaponProficiencyLevels.put(WeaponCategory.LANCE, WeaponRank.S);
                            break;
                        case B:
                            weaponProficiencyLevels.put(WeaponCategory.LANCE, WeaponRank.A);
                            break;
                        case C:
                            weaponProficiencyLevels.put(WeaponCategory.LANCE, WeaponRank.B);
                            break;
                        case D:
                            weaponProficiencyLevels.put(WeaponCategory.LANCE, WeaponRank.C);
                            break;
                        case E:
                            weaponProficiencyLevels.put(WeaponCategory.LANCE, WeaponRank.D);
                            break;
                        case F:
                            weaponProficiencyLevels.put(WeaponCategory.LANCE, WeaponRank.E);
                            break;
                    }

                    addWeaponProficiencyExp(WeaponCategory.LANCE, remainder_lance);
                    break;

                case SWORD:

                    final int remainder_sword = weaponProficiencyExp.get(WeaponCategory.SWORD) - 100;

                    switch(weaponProficiencyLevels.get(WeaponCategory.SWORD)) {
                        case S:
                            break;
                        case A:
                            weaponProficiencyLevels.put(WeaponCategory.SWORD, WeaponRank.S);
                            break;
                        case B:
                            weaponProficiencyLevels.put(WeaponCategory.SWORD, WeaponRank.A);
                            break;
                        case C:
                            weaponProficiencyLevels.put(WeaponCategory.SWORD, WeaponRank.B);
                            break;
                        case D:
                            weaponProficiencyLevels.put(WeaponCategory.SWORD, WeaponRank.C);
                            break;
                        case E:
                            weaponProficiencyLevels.put(WeaponCategory.SWORD, WeaponRank.D);
                            break;
                        case F:
                            weaponProficiencyLevels.put(WeaponCategory.SWORD, WeaponRank.E);
                            break;
                    }

                    addWeaponProficiencyExp(WeaponCategory.SWORD, remainder_sword);
                    break;

                case SHIELD:

                    final int remainder_shield = weaponProficiencyExp.get(WeaponCategory.SHIELD) - 100;

                    switch(weaponProficiencyLevels.get(WeaponCategory.SHIELD)) {
                        case S:
                            break;
                        case A:
                            weaponProficiencyLevels.put(WeaponCategory.SHIELD, WeaponRank.S);
                            break;
                        case B:
                            weaponProficiencyLevels.put(WeaponCategory.SHIELD, WeaponRank.A);
                            break;
                        case C:
                            weaponProficiencyLevels.put(WeaponCategory.SHIELD, WeaponRank.B);
                            break;
                        case D:
                            weaponProficiencyLevels.put(WeaponCategory.SHIELD, WeaponRank.C);
                            break;
                        case E:
                            weaponProficiencyLevels.put(WeaponCategory.SHIELD, WeaponRank.D);
                            break;
                        case F:
                            weaponProficiencyLevels.put(WeaponCategory.SHIELD, WeaponRank.E);
                            break;
                    }

                    addWeaponProficiencyExp(WeaponCategory.SHIELD, remainder_shield);
                    break;

                case MAGE_DARK:

                    final int remainder_dark = weaponProficiencyExp.get(WeaponCategory.MAGE_DARK) - 100;

                    switch(weaponProficiencyLevels.get(WeaponCategory.MAGE_DARK)) {
                        case S:
                            break;
                        case A:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_DARK, WeaponRank.S);
                            break;
                        case B:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_DARK, WeaponRank.A);
                            break;
                        case C:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_DARK, WeaponRank.B);
                            break;
                        case D:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_DARK, WeaponRank.C);
                            break;
                        case E:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_DARK, WeaponRank.D);
                            break;
                        case F:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_DARK, WeaponRank.E);
                            break;
                    }

                    addWeaponProficiencyExp(WeaponCategory.MAGE_DARK, remainder_dark);
                    break;

                case MAGE_ANIMA:

                    final int remainder_anima = weaponProficiencyExp.get(WeaponCategory.MAGE_ANIMA) - 100;

                    switch(weaponProficiencyLevels.get(WeaponCategory.MAGE_ANIMA)) {
                        case S:
                            break;
                        case A:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_ANIMA, WeaponRank.S);
                            break;
                        case B:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_ANIMA, WeaponRank.A);
                            break;
                        case C:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_ANIMA, WeaponRank.B);
                            break;
                        case D:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_ANIMA, WeaponRank.C);
                            break;
                        case E:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_ANIMA, WeaponRank.D);
                            break;
                        case F:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_ANIMA, WeaponRank.E);
                            break;
                    }

                    addWeaponProficiencyExp(WeaponCategory.MAGE_ANIMA, remainder_anima);
                    break;

                case MAGE_LIGHT:

                    final int remainder_light = weaponProficiencyExp.get(WeaponCategory.MAGE_LIGHT) - 100;

                    switch(weaponProficiencyLevels.get(WeaponCategory.MAGE_LIGHT)) {
                        case S:
                            break;
                        case A:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_LIGHT, WeaponRank.S);
                            break;
                        case B:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_LIGHT, WeaponRank.A);
                            break;
                        case C:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_LIGHT, WeaponRank.B);
                            break;
                        case D:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_LIGHT, WeaponRank.C);
                            break;
                        case E:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_LIGHT, WeaponRank.D);
                            break;
                        case F:
                            weaponProficiencyLevels.put(WeaponCategory.MAGE_LIGHT, WeaponRank.E);
                            break;
                    }

                    addWeaponProficiencyExp(WeaponCategory.MAGE_LIGHT, remainder_light);
                    break;

                case HERBAL_FLORAL:

                    final int remainder_floral = weaponProficiencyExp.get(WeaponCategory.HERBAL_FLORAL) - 100;

                    switch(weaponProficiencyLevels.get(WeaponCategory.HERBAL_FLORAL)) {
                        case S:
                            break;
                        case A:
                            weaponProficiencyLevels.put(WeaponCategory.HERBAL_FLORAL, WeaponRank.S);
                            break;
                        case B:
                            weaponProficiencyLevels.put(WeaponCategory.HERBAL_FLORAL, WeaponRank.A);
                            break;
                        case C:
                            weaponProficiencyLevels.put(WeaponCategory.HERBAL_FLORAL, WeaponRank.B);
                            break;
                        case D:
                            weaponProficiencyLevels.put(WeaponCategory.HERBAL_FLORAL, WeaponRank.C);
                            break;
                        case E:
                            weaponProficiencyLevels.put(WeaponCategory.HERBAL_FLORAL, WeaponRank.D);
                            break;
                        case F:
                            weaponProficiencyLevels.put(WeaponCategory.HERBAL_FLORAL, WeaponRank.E);
                            break;
                    }

                    addWeaponProficiencyExp(WeaponCategory.HERBAL_FLORAL, remainder_floral);
                    break;

                case HERBAL_POTION:

                    final int remainder_potions = weaponProficiencyExp.get(WeaponCategory.HERBAL_POTION) - 100;

                    switch(weaponProficiencyLevels.get(WeaponCategory.HERBAL_POTION)) {
                        case S:
                            break;
                        case A:
                            weaponProficiencyLevels.put(WeaponCategory.HERBAL_POTION, WeaponRank.S);
                            break;
                        case B:
                            weaponProficiencyLevels.put(WeaponCategory.HERBAL_POTION, WeaponRank.A);
                            break;
                        case C:
                            weaponProficiencyLevels.put(WeaponCategory.HERBAL_POTION, WeaponRank.B);
                            break;
                        case D:
                            weaponProficiencyLevels.put(WeaponCategory.HERBAL_POTION, WeaponRank.C);
                            break;
                        case E:
                            weaponProficiencyLevels.put(WeaponCategory.HERBAL_POTION, WeaponRank.D);
                            break;
                        case F:
                            weaponProficiencyLevels.put(WeaponCategory.HERBAL_POTION, WeaponRank.E);
                            break;
                    }

                    addWeaponProficiencyExp(WeaponCategory.HERBAL_POTION, remainder_potions);
                    break;

                case HANDS:

                    final int remainder_hands = weaponProficiencyExp.get(WeaponCategory.HANDS) - 100;

                    switch(weaponProficiencyLevels.get(WeaponCategory.HANDS)) {
                        case S:
                            break;
                        case A:
                            weaponProficiencyLevels.put(WeaponCategory.HANDS, WeaponRank.S);
                            break;
                        case B:
                            weaponProficiencyLevels.put(WeaponCategory.HANDS, WeaponRank.A);
                            break;
                        case C:
                            weaponProficiencyLevels.put(WeaponCategory.HANDS, WeaponRank.B);
                            break;
                        case D:
                            weaponProficiencyLevels.put(WeaponCategory.HANDS, WeaponRank.C);
                            break;
                        case E:
                            weaponProficiencyLevels.put(WeaponCategory.HANDS, WeaponRank.D);
                            break;
                        case F:
                            weaponProficiencyLevels.put(WeaponCategory.HANDS, WeaponRank.E);
                            break;
                    }

                    addWeaponProficiencyExp(WeaponCategory.HANDS, remainder_hands);
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

        public int getLevel() { return level; }
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
