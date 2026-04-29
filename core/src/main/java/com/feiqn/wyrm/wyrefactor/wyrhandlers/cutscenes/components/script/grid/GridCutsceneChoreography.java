package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpg.rpgrid.RPGridAbilityID;
import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.WyrCutsceneChoreography;

public final class GridCutsceneChoreography extends WyrCutsceneChoreography<RPGridActor> {

    private RPGridAbilityID ability;

    public GridCutsceneChoreography(WorldChoreoType worldChoreoType) { super(worldChoreoType); }
    public GridCutsceneChoreography(DialogChoreoType dialogChoreoType) { super(dialogChoreoType); }

    @Override
    public WyrType getWyrType() { return WyrType.RPGRID; }

    public void setAbility(RPGridAbilityID ability) { this.ability = ability; }

    public RPGridAbilityID getAbility() { return ability; }

}
