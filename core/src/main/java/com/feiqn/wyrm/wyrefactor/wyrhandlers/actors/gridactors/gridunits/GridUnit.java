package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.feiqn.wyrm.logic.handlers.ai.AIPersonality;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.MovementType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.StatType;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.equipment.loadout.WyrLoadout;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cppersonality.grid.GridCPPersonality;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.SimpleStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.pathfinder.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

public abstract class GridUnit extends GridActor {

    protected final SimpleStats stats;
    protected final WyrLoadout equipment = new WyrLoadout();
    protected final UnitIDRoster rosterID;
    protected TeamAlignment alignment = TeamAlignment.PLAYER;
    protected GridCPPersonality personality;

    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID) {
        this(metaHandler, rosterID, (Drawable)null);
    }
    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID, NinePatch patch) {
        this(metaHandler, rosterID, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID, TextureRegion region) {
        this(metaHandler, rosterID, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID, Texture texture) {
        this(metaHandler, rosterID, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID, Skin skin, String drawableName) {
        this(metaHandler, rosterID, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID, Drawable drawable) {
        this(metaHandler, rosterID, drawable, Scaling.stretch, Align.center);
    }
    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID, Drawable drawable, Scaling scaling) {
        this(metaHandler, rosterID, drawable, scaling, Align.center);
    }
    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID, Drawable drawable, Scaling scaling, int align) {
        super(metaHandler, ActorType.UNIT, drawable, scaling, align);
        this.rosterID = rosterID;
        stats = new SimpleStats(this);
        this.personality = new GridCPPersonality(WyrType.GRIDWORLD, AIPersonality.PLAYER);
    }

    public void resetForNextTurn() {
        stats.tickDownConditions(true);
        stats.restoreAP();
        // TODO: shader
    }


    public void occupy(GridTile tile) {
        if(occupiedTile == tile) return;
        occupiedTile = tile;
        occupiedTile.occupy(this);
    }

    @Override
    public void hoverOver() {
        super.hoverOver();

        // TODO:
        //  - update hud with unit's info
        //  - highlight units and props too

        h.map().clearAllHighlights();
        h.map().highlightTiles(
            GridPathfinder.currentlyAccessibleTo(this).tiles().keySet()
        );
    }

    @Override
    public void unHover() {
        super.unHover();

        h.map().clearAllHighlights();
        h.conditions().parsePriority();
    }

    @Override
    protected void kill() {

    }

    public void setPersonality(GridCPPersonality personality) { this.personality = personality; }

    public int modifiedStatValue(StatType stat) {
        return stats.modifiedStatValue(stat);
    }
    public boolean canMove() { return stats.getActionPoints() > 0; }
    public UnitIDRoster getRosterID() { return rosterID; }
    public MovementType getMovementType() { return stats.movementType(); }
    public SimpleStats.RPGClass.RPGClassID classID() { return stats.classID(); }
    public int getReach() { return 1; } // todo, stats.weapon.reach
    public GridCPPersonality personality() { return personality; }
    public TeamAlignment teamAlignment() { return alignment; }

//    public static class RPGClass {
//
//        // TODO: might end up pulling this out and making it standalone
//
//        public enum RPGClassID {
//            PEASANT,         // basic commoner
//            DRAFTEE,         // alt basic soldier
//
//            PLANESWALKER,    // unique for LEIF
//            SHIELD_KNIGHT,   // unique for ANTAL
//            WRAITH,          // unique class for LEON
//            KING,            // unique for ERIK and [LEON's FATHER]
//            QUEEN,           // unique for [SOUTHERN QUEEN]
//            CAPTAIN,         // unique for ANVIL
//            HERBALIST,       // unique for LYRA
//            BOSS,            // unique for TOHNI
//
//            SOLDIER,         // generic
//            BLADE_KNIGHT,    // generic
//            CAVALRY,         // generic
//            BOATMAN,         // generic
//
//
//            GREAT_WYRM       // God.
//        }
//
//        private boolean hasMount = false;
//        private boolean mountLocked = false;
//        private boolean isMounted = false;
//        private RPGClassID classID = RPGClassID.PEASANT;
//        private MovementType standardMovementType = MovementType.INFANTRY;
//        private MovementType mountedMovementType = MovementType.INFANTRY;
//
//        private int bonus_Strength   = 0;
//        private int bonus_Defense    = 0;
//        private int bonus_Magic      = 0;
//        private int bonus_Resistance = 0;
//        private int bonus_Speed      = 0;
//        private int bonus_Health     = 0;
//        private int bonus_AP         = 0;
//
//        private RPGClass() {}
//
//
//        public void setTo(RPGClassID type) {
//            switch(classID) {
//                case PEASANT:
//                case DRAFTEE:
//                    break;
//
//                case PLANESWALKER:
//                case SHIELD_KNIGHT:
//                case WRAITH:
//                case KING:
//                case QUEEN:
//                case CAPTAIN:
//                case HERBALIST:
//                case BOSS:
//
//                case SOLDIER:
//                case BLADE_KNIGHT:
//                case CAVALRY:
//                case BOATMAN:
//
//                case GREAT_WYRM:
//            }
//        }
//
//        private String className() {
//            return classID.toString();
//            // TODO: cast all but first letter to lower-case.
//        }
//        public void mount() {
//            if(!hasMount || isMounted || mountLocked) return;
//
//        }
//        private void dismount() {
//            if(!hasMount || !isMounted) return;
//
//        }
//        private MovementType moveType() {
//            if(isMounted) {
//                return mountedMovementType;
//            } else {
//                return standardMovementType;
//            }
//        }
//        private boolean mountAvailable() { return hasMount && !mountLocked; }
//
//
//
//
//    }


}
