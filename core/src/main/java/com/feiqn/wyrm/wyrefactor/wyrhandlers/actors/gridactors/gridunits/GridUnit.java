package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.equipment.WyrLoadout;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.SimpleStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

public abstract class GridUnit extends GridActor {

    protected final RPGClass rpgClass    = new RPGClass();
    protected final SimpleStats stats    = new SimpleStats();
    protected final WyrLoadout equipment = new WyrLoadout();
    protected final UnitRoster rosterID;
    protected TeamAlignment alignment = TeamAlignment.PLAYER;


    public GridUnit(WYRMGame root, UnitRoster rosterID) {
        this(root, rosterID, (Drawable)null);
    }
    public GridUnit(WYRMGame root, UnitRoster rosterID, NinePatch patch) {
        this(root, rosterID, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public GridUnit(WYRMGame root, UnitRoster rosterID, TextureRegion region) {
        this(root, rosterID, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public GridUnit(WYRMGame root, UnitRoster rosterID, Texture texture) {
        this(root, rosterID, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public GridUnit(WYRMGame root, UnitRoster rosterID, Skin skin, String drawableName) {
        this(root, rosterID, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public GridUnit(WYRMGame root, UnitRoster rosterID, Drawable drawable) {
        this(root, rosterID, drawable, Scaling.stretch, Align.center);
    }
    public GridUnit(WYRMGame root, UnitRoster rosterID, Drawable drawable, Scaling scaling) {
        this(root, rosterID, drawable, scaling, Align.center);
    }
    public GridUnit(WYRMGame root, UnitRoster rosterID, Drawable drawable, Scaling scaling, int align) {
        super(root, ActorType.UNIT, drawable, scaling, align);
        this.rosterID = rosterID;
    }


    public void occupy(GridTile tile) {
        if(occupiedTile == tile) return;
        occupiedTile = tile;
        occupiedTile.occupy(this);
    }

    public int modifiedStatValue(StatTypes stat) {
        switch(stat) {
            case SPEED:
                break;

            case HEALTH:
                break;

            case DEFENSE:
                break;

            case STRENGTH:
                break;

            case DEXTERITY:
                break;

            case RESISTANCE:
                break;

            case MOVEMENT_SPEED:
                break;

            default:
                return 0;
        }
        return 0;
    }
    public boolean canMove() { return stats.getActionPoints() > 0; }
    public UnitRoster getRosterID() { return rosterID; }
    public MovementType getMovementType() { return rpgClass.standardMovementType; }
    public RPGClass.RPGClassID classID() { return rpgClass.classID; }
    public int getReach() { return 1; } // todo, stats.weapon.reach

    public TeamAlignment getAlignment() { return alignment; }

    public static class RPGClass {

        // TODO: might end up pulling this out and making it standalone

        public enum RPGClassID {
            PEASANT,         // basic commoner
            DRAFTEE,         // alt basic soldier

            PLANESWALKER,    // unique for LEIF
            SHIELD_KNIGHT,   // unique for ANTAL
            WRAITH,          // unique class for LEON
            KING,            // unique for ERIK and [LEON's FATHER]
            QUEEN,           // unique for [SOUTHERN QUEEN]
            CAPTAIN,         // unique for ANVIL
            HERBALIST,       // unique for LYRA
            BOSS,            // unique for TOHNI

            SOLDIER,         // generic
            BLADE_KNIGHT,    // generic
            CAVALRY,         // generic
            BOATMAN,         // generic


            GREAT_WYRM       // God.
        }

        private boolean hasMount = false;
        private boolean mountLocked = false;
        private boolean isMounted = false;
        private RPGClassID classID = RPGClassID.PEASANT;
        private MovementType standardMovementType = MovementType.INFANTRY;
        private MovementType mountedMovementType = MovementType.INFANTRY;

        private int bonus_Strength   = 0;
        private int bonus_Defense    = 0;
        private int bonus_Magic      = 0;
        private int bonus_Resistance = 0;
        private int bonus_Speed      = 0;
        private int bonus_Health     = 0;
        private int bonus_AP         = 0;

        private RPGClass() {}


        public void setTo(RPGClassID type) {
            switch(classID) {
                case PEASANT:
                case DRAFTEE:
                    break;

                case PLANESWALKER:
                case SHIELD_KNIGHT:
                case WRAITH:
                case KING:
                case QUEEN:
                case CAPTAIN:
                case HERBALIST:
                case BOSS:

                case SOLDIER:
                case BLADE_KNIGHT:
                case CAVALRY:
                case BOATMAN:

                case GREAT_WYRM:
            }
        }

        private String className() {
            return classID.toString();
            // TODO: cast all but first letter to lower-case.
        }
        public void mount() {
            if(!hasMount || isMounted || mountLocked) return;

        }
        private void dismount() {
            if(!hasMount || !isMounted) return;

        }
        private MovementType moveType() {
            if(isMounted) {
                return mountedMovementType;
            } else {
                return standardMovementType;
            }
        }
        private boolean mountAvailable() { return hasMount && !mountLocked; }




    }


}
