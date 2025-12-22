package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.DialogAction;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.Abilities;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitIDRoster;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.player.LeifUnitOLD;

public class DScript_1A_Leif_LeaveMeAlone extends ChoreographedCutsceneScript {

    // First time Leif gets attacked in 1A.

    private final String bfn;

    public DScript_1A_Leif_LeaveMeAlone(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_LEIF_LEAVEMEALONE);
        this.bfn = WYRMGame.assets().bestFriendName;
    }

    @Override
    protected void declareTriggers() {
        armSingleUnitCombatCutsceneTrigger(UnitIDRoster.SOLDIER, true, true, false);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;
        if(slideshow.size != 0) return;


        choreographShortPause();

        set(CharacterExpression.LEIF_PANICKED, "No no no no no no no no");
        set(CharacterExpression.LEIF_PANICKED, "Get off of me!");

        choreographUseAbility(ags.conditions().teams().getPlayerTeam().get(0), Abilities.DIVE_BOMB, ags.conditions().teams().getEnemyTeam().get(0));

        set(CharacterExpression.LEIF_HOPEFUL, bfn + "!");
        set(CharacterExpression.LEIF_WORRIED, "Ooooohhhhh thank you thank you thank you thank you thank you!"); // mounted char portrait

        choreographFocusOnLocation(45, 20);

        set(CharacterExpression.LEIF_HOPEFUL, "To the east! We can fly right over those flames, and the soldiers wont be able to chase us!");
        set(CharacterExpression.LEIF_DETERMINED, "Let's get out of here!");

        lastFrame().addDialogAction(new DialogAction(new Runnable() {
            @Override
            public void run() {
                if(ags.conditions().teams().getPlayerTeam().get(0) instanceof LeifUnitOLD) {
                    ((LeifUnitOLD) ags.conditions().teams().getPlayerTeam().get(0)).mount();
                }
            }
        }));

    }

}
