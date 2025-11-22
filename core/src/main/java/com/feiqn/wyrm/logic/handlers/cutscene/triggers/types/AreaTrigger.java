//package com.feiqn.wyrm.logic.handlers.cutscene.triggers.types;
//
//import com.badlogic.gdx.math.Vector2;
//import com.feiqn.wyrm.logic.handlers.cutscene.dialog.CutsceneScript;
//import com.feiqn.wyrm.logic.handlers.cutscene.triggers.CutsceneTrigger;
//import com.feiqn.wyrm.models.unitdata.UnitRoster;
//
//import java.util.EnumSet;
//import java.util.Set;
//
//public class AreaTrigger extends CutsceneTrigger {
//
//    private final Set<Vector2> triggerTiles;
//
//    public AreaTrigger(EnumSet<UnitRoster> triggerUnits, Set<Vector2> triggerTiles, CutsceneScript script) {
//        super(triggerUnits, script);
//        this.triggerTiles = triggerTiles;
//    }
//
//    public boolean checkTrigger(UnitRoster unit, Vector2 tilePosition) {
//        if(super.checkTrigger(unit) && triggerTiles.contains(tilePosition)) {
//            hasTriggered = true;
//            return true;
//        }
//        return false;
//    }
//
//}
