package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.choreography;

import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.perGame.WYRM;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public final class GridCutsceneChoreography extends WyrCutsceneChoreography {

    private WyRPG.AbilityID ability;

    public GridCutsceneChoreography(RPGridInteraction worldInteraction) {
        super(worldInteraction);
    }
    public GridCutsceneChoreography(DialogChoreoType dialogChoreoType) {
        super(dialogChoreoType);
    }

    public void setAbility(WyRPG.AbilityID ability) { this.ability = ability; }

    public WyRPG.AbilityID getAbility() {
        return ability;
    }
    @Override
    public WYRM.Character getCharacterID() {
        return (characterID instanceof WYRM.Character ? (WYRM.Character) characterID : null);
    }
    @Override
    public RPGridInteraction getWorldInteraction() {
        return (RPGridInteraction) worldInteraction;
    }
    @Override
    public RPGridActor getSubject() {
        assert subject instanceof RPGridActor;
        return (RPGridActor) subject;
    }
    @Override
    public RPGridActor getObject() {
        assert object instanceof RPGridActor;
        return (RPGridActor) object;
    }
    @Override
    public WyrType getWyrType() { return WyrType.RPGRID; }



}
