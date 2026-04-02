package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions.grid;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid.GridCutscene;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.GridPath;

import static com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions.WyrInteraction.InteractionType.MOVE_WAIT;

public final class GridInteraction extends WyrInteraction<GridActor> {

    private GridPath path;
    private GridCutscene cutscene;

    public GridInteraction(GridActor parent, GridActor object, InteractionType actType, int interactableRange) {
        super(parent, object, actType, interactableRange);
    }

    public GridInteraction(GridActor parent, GridPath path) {
        super(parent, null, MOVE_WAIT, 0);
        this.path = path;
    }

    public GridInteraction(GridUnit parent, GridActor object, GridCutscene scriptToTrigger) {
        super(parent, object, InteractionType.TALK, 1);
        this.cutscene = scriptToTrigger;
    }
    public GridInteraction(GridActor parent) {
        super(parent, null, InteractionType.WAIT, 0);
    }

    public GridPath getPath() { return path;}
    public GridCutscene getCutscene() { return cutscene; }

}
