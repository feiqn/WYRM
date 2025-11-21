//package com.feiqn.wyrm.logic.handlers.cutscene.triggers.types;
//
//import com.feiqn.wyrm.logic.handlers.cutscene.dialog.CutsceneScript;
//import com.feiqn.wyrm.logic.handlers.cutscene.triggers.CutsceneTrigger;
//import com.feiqn.wyrm.models.unitdata.UnitRoster;
//
//import java.util.EnumSet;
//
//public class CombatTrigger extends CutsceneTrigger {
//
//    public enum When {
//        BEFORE,
//        DURING,
//        AFTER
//    }
//
//    private When when;
//
//    public CombatTrigger(EnumSet<UnitRoster> triggerUnits, CutsceneScript script, When when) {
//        super(triggerUnits, script);
//        this.when = when;
//    }
//
//    public boolean checkTrigger(UnitRoster unit, When now) {
//        if(now == when && super.checkTrigger(unit)) {
//            hasTriggered = true;
//            return true;
//        }
//        return false;
//    }
//}
