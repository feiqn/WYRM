package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid;

import com.feiqn.wyrm.OLD_DATA.models.unitdata.AbilityID;
import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.WyrCutsceneChoreography;

public final class GridCutsceneChoreography extends WyrCutsceneChoreography<RPGridActor> {

    private AbilityID ability;

    public GridCutsceneChoreography(WorldChoreoType worldChoreoType) { super(worldChoreoType); }
    public GridCutsceneChoreography(DialogChoreoType dialogChoreoType) { super(dialogChoreoType); }

    @Override
    public WyrType getWyrType() { return WyrType.RPGRIDWORLD; }

    public void setAbility(AbilityID ability) { this.ability = ability; }

    public AbilityID getAbility() { return ability; }

}
