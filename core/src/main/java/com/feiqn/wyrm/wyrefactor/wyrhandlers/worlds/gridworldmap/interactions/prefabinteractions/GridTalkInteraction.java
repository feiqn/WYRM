package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.prefabinteractions;

import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.CutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.GridCutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;

public final class GridTalkInteraction extends GridInteraction {

    private final GridCutsceneScript GCSScript;

    public GridTalkInteraction(GridUnit parent, GridActor object, GridCutsceneScript scriptToTrigger) {
        super(parent, object, InteractionType.TALK, 1);
        this.GCSScript = scriptToTrigger;
    }

    @Override
    public GridUnit getParent() { return (GridUnit)super.getParent(); }

    public GridCutsceneScript associatedScript() { return GCSScript; }

}
