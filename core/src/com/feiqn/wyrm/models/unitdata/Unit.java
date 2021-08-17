package com.feiqn.wyrm.models.unitdata;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
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

    private MovementType movementType;

    private boolean canStillMoveThisTurn;

    public String name;

    private int movementSpeed,
                strength,
                defense,
                hp,
                skill,
                speed,
                row,
                column;

    private final WYRMGame game;

    protected FacedDirection facedDirection;

    protected TeamAlignment teamAlignment;

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

        name = "Unit";
        usableWeaponTypes = new Array<>();

        canStillMoveThisTurn = true;

        movementType = MovementType.INFANTRY;

        teamAlignment = TeamAlignment.ALLY;

        row = 0;
        column = 0;
        movementSpeed = 5;
        strength = 3;
        defense = 3;
        hp = 10;
        skill = 3;
        speed = 3;
    }

    // --SETTERS--
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public void setFacedDirection(FacedDirection facedDirection) {
        this.facedDirection = facedDirection;
    }
    public void setHp(int hp) {
        this.hp = hp;
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
    public void toggleCanMove() { canStillMoveThisTurn = !canStillMoveThisTurn; }

    // --GETTERS--
    public boolean canMove() { return canStillMoveThisTurn; }
    public FacedDirection getFacedDirection() {
        return facedDirection;
    }
    public int getDefense() {
        return defense;
    }
    public int getHp() {
        return hp;
    }
    public int getMovementSpeed() {
        return movementSpeed;
    }
    public int getSkill() {
        return skill;
    }
    public int getSpeed() {
        return speed;
    }
    public int getStrength() {
        return strength;
    }
    public MovementType getMovementType() {
        return movementType;
    }
    public int getColumn() {
        return column;
    }
    public int getRow() {
        return row;
    }
    public TeamAlignment getTeamAlignment() {
        return teamAlignment;
    }
}