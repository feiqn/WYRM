package com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.actors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.actors.items.inventory.WyrInventory;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.personality.WyrPersonality;

import java.util.HashMap;

public interface WyriStats<
        AbilityID   extends Enum<?>,
        Actor       extends WyrActor<?,?,?,?>,
        Condition   extends WyrStatusCondition<?,?>,
        Inventory   extends WyrInventory,
        Personality extends WyrPersonality<?>,
        StatID      extends Enum<?>
            > extends Wyr {

    WyrActor<?,?,?,?>              parent           = null;
    Array<Enum<?>>                 abilities        = new Array<>();
    Array<WyrStatusCondition<?,?>> statusConditions = new Array<>();
    HashMap<String, Integer>       statMap          = new HashMap<>();
    WyrPersonality<?>              personality      = null;

    /** <p>
     * @StatMap <br>
     *  HEALTH_max <br>
     *  HEALTH_rolling <br>
     *  AP_restore_rate <br>
     *  AP_rolling <br>
     *  </p>
     *  Health is obvious. <br>
     *  AP, or Action Points, broadly defines
     *  how many "turns" or "moves" a game actor
     *  has available to them at a given time. <br>
     *  <br>
     *  Think broadly for this one. <br>
     *  In a fast-paced action game, 1 AP could
     *  represent standard ability to move and 0
     *  represents a stunned state. <br>
     *  AP could be a large, dynamic number serving
     *  for instance as a stamina bar. <br>
     *  In a simple turn-based game, it can represent
     *  whose turn it currently is in the order. <br>
     *  Or you could just ignore it for your project.
     */
    default void WyrStats(Actor parent) {
        statMap.put("HEALTH_max",      1);
        statMap.put("HEALTH_rolling",  1);
        statMap.put("AP_restore_rate", 1);
        statMap.put("AP_rolling",      0);
        // child example:
//        for(StatType t : StatType.values()) {
//            statMap.put(t.toString() + "_base", 0);
//        }
    }

    default  void applyCondition(Condition condition) {
        statusConditions.add(condition);
    }

    default  void tickDownConditions(boolean harmful) {
        for(WyrStatusCondition<?, ?> condition : statusConditions) {
            condition.tickDownEffect();
            if(condition.effectCounter() <= 0) statusConditions.removeValue(condition, true);
//            if(!harmful) continue;
//            switch(condition.getEffectType()) {
//                case BURN:
//                case etc.,
//                default:
//                    break;
//            }
        }
    }

    default  void healBy(int amount) { applyDamage(-amount); }
    default  void applyDamage(int damage) {
        int rollingHP = statMap.get("HEALTH_rolling");
        rollingHP -= damage;
        if(rollingHP > getMaxHP()) healToFull(); // negative damage can heal
        statMap.put("HEALTH_rolling", rollingHP);
        if(rollingHP <= 0) {
            parent.kill();
        }
    }

    default  void healToFull() { statMap.put("HEALTH_rolling", getMaxHP()); }

    default  void gainAP() {
        statMap.merge("AP_rolling", 1, Integer::sum); // ai taught me this, sorry idk
        shaderAPUpdate();
    }
    default  void spendAP() {
        statMap.merge("AP_rolling", -1, Integer::sum);
        shaderAPUpdate();
    }
    default  void restoreAP() {
        statMap.merge("AP_rolling", getAPRestoreRate(), Integer::sum);
        shaderAPUpdate();
    }
    private void shaderAPUpdate() {
        if(statMap.get("AP_rolling") <= 0) {
            parent.applyShader(WyrActor.ShaderState.DIM);
        } else {
            parent.applyShader(WyrActor.ShaderState.STANDARD);
        }
    }

    default  void setStatValue(StatID type, int i) { statMap.put(type.toString() + "_base", i); }
    default  void setMaxHealth(int i)              { statMap.put("HEALTH_max", i); }
    default  void setAPRestoreRate(int i)          { statMap.put("AP_restore_rate", i); }

    default  void setPersonality(Personality personality) { personality = personality; }

    default Personality      getPersonality()      { return (Personality)personality;      }
    default Array<Condition> getStatusConditions() { return (Array<Condition>)statusConditions; }

    default int getStatValue(StatID type) { return (statMap.getOrDefault(type.toString() + "_base", 0)); }
    default int getMaxHP()                { return statMap.get("HEALTH_max");      }
    default int getRollingHP()            { return statMap.get("HEALTH_rolling");  }
    default int getRollingAP()            { return statMap.get("AP_rolling");      }
    default int getAPRestoreRate()        { return statMap.get("AP_restore_rate"); }

    // for child override
    default int getModifiedStatValue(StatID type) { return getStatValue(type); }

    default Inventory inventory() { return null;}

}
