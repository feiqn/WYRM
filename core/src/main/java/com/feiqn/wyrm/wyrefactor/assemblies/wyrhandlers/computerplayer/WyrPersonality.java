package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.PersonalityType;

public class WyrPersonality {

    protected PersonalityType personalityType;

    private final Array<WyrActor> unitTargets = new Array<>();
    private final Array<WyrActor> propTargets = new Array<>();
    private final Array<RPGridTile>   tileTargets = new Array<>();

    public WyrPersonality(PersonalityType PersonalityType) {
        this.personalityType = PersonalityType;
    }

    public WyrPersonality setPersonalityType(PersonalityType type) {
        personalityType = type;
        return this;
    }

    public void prioritize(RPGridTile tile) { tileTargets.add(tile); }
    public void prioritize(WyrActor unit) { unitTargets.add(unit); }
//    public void prioritize(WyrActor prop) { propTargets.add(prop); }

    public GridPathfinder.Things priorities() {
        final GridPathfinder.Things returnValue = new GridPathfinder.Things();
        for(RPGridTile tile : tileTargets) {
            returnValue.tiles().put(tile, new GridPath());
        }
        for(WyrActor unit : unitTargets) {
            returnValue.add(unit, new GridPath());
        }
//        for(WyrActor prop : propTargets) {
//            returnValue.add(prop, new GridPath());
//        }
        return returnValue;
    }

    public PersonalityType getPersonalityType() {
        return personalityType;
    }
}
