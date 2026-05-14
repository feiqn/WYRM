package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.stats;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.inventory.WyrInventory;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer.personality.WyrPersonality;

import java.util.HashMap;

/**
 * "stats" may not be all-encompassing enough of a name for this.
 * Inventory, equipment, personality, and abilities also tracked here.
 */
public class WyrStats implements Wyr {

    protected WyrPersonality            personality;
    protected Array<WyrStatusCondition> statusConditions = new Array<>();
    protected WyrInventory              inventory;
    protected WyrActor                  parent;

    protected final Array<Enum<?>>           abilities = new Array<>();
    protected final HashMap<String, Integer> statMap   = new HashMap<>();

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
    protected WyrStats(WyrActor parent, Enum<?>[] types) {
        this.parent = parent;
        statMap.put("HEALTH_max",      1);
        statMap.put("HEALTH_rolling",  1);
        statMap.put("AP_restore_rate", 1);
        statMap.put("AP_rolling",      0);
        for(Enum<?> t : types) { setStatValue(t, 0); }
    }



    public  void applyCondition(WyrStatusCondition condition) {
        statusConditions.add(condition);
    }

    public  void tickDownConditions(boolean harmful) {
        for(WyrStatusCondition condition : statusConditions) {
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

    public  void healBy(int amount) { applyDamage(-amount); }
    public  void applyDamage(int damage) {
        int rollingHP = statMap.get("HEALTH_rolling");
        rollingHP -= damage;
        if(rollingHP > getMaxHP()) healToFull(); // negative damage can heal
        statMap.put("HEALTH_rolling", rollingHP);
        if(rollingHP <= 0) {
            parent.kill();
        }
    }

    public  void healToFull() { statMap.put("HEALTH_rolling", getMaxHP()); }

    public  void gainAP() {
        statMap.merge("AP_rolling", 1, Integer::sum); // ai taught me this, sorry idk
        shaderAPUpdate();
    }
    public  void spendAP() {
        statMap.merge("AP_rolling", -1, Integer::sum);
        shaderAPUpdate();
    }
    public  void restoreAP() {
        statMap.merge("AP_rolling", getAPRestoreRate(), Integer::sum);
        shaderAPUpdate();
    }
    private void shaderAPUpdate() {
        if(statMap.get("AP_rolling") <= 0) {
            parent.applyShader(ShaderState.DIM);
        } else {
            parent.applyShader(ShaderState.STANDARD);
        }
    }

    public  void setStatValue(String type, int i)        { statMap.put(type.toUpperCase(), i);                        }
    public  void setStatValue(Enum<?> type, int i)        { statMap.put(type.toString(), i);                 }
    public  void setMaxHealth(int i, boolean healToFull) { statMap.put("HEALTH_max", i); if(healToFull) healToFull(); }
    public  void setAPRestoreRate(int i)                 { statMap.put("AP_restore_rate", i);                         }

    public  void setPersonality(WyrPersonality personality) { this.personality = personality; }

    public WyrPersonality            getPersonality()      { return personality; }
//    public Array<WyrStatusCondition> getStatusConditions() { return statusConditions; }

    public Array<Enum<?>> statTypes() {
        return null;
    }

    public int getStatValue(Enum<?> type) { return (statMap.getOrDefault(type.toString(), 0)); }
    public int getMaxHP()                { return statMap.get("HEALTH_max");      }
    public int getRollingHP()            { return statMap.get("HEALTH_rolling");  }
    public int getRollingAP()            { return statMap.get("AP_rolling");      }
    public int getAPRestoreRate()        { return statMap.get("AP_restore_rate"); }

    // for child override
//    public int getModifiedStatValue(Enum<?> type) { return getStatValue(type); }

    public WyrInventory inventory() { return this.inventory;}

}
