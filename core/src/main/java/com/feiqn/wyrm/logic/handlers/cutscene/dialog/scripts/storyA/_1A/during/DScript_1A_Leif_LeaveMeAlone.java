package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.DialogAction;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnit;

public class DScript_1A_Leif_LeaveMeAlone extends ChoreographedCutsceneScript {

    // First time Leif gets attacked in 1A.

    private final String bfn;

    public DScript_1A_Leif_LeaveMeAlone(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_LEIF_LEAVEMEALONE);
        this.bfn = game.assetHandler.bestFriendName;
    }

    @Override
    protected void declareTriggers() {
        armSingleUnitCombatCutsceneTrigger(UnitRoster.GENERIC_SOLDIER, true, true, false);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;
        if(slideshow.size != 0) return;


        choreographShortPause();

        set(CharacterExpression.LEIF_PANICKED, "No no no no no no no no");
        set(CharacterExpression.LEIF_PANICKED, "Get off of me!");

        choreographUseAbility(ags.conditions().teams().getPlayerTeam().get(0), Abilities.DIVE_BOMB, ags.conditions().teams().getEnemyTeam().get(0));

//        choreographShortPause();

        set(CharacterExpression.LEIF_HOPEFUL, bfn + "!");
        set(CharacterExpression.LEIF_WORRIED, "Ooooohhhhh thank you thank you thank you thank you thank you!"); // mounted char portrait
        set(CharacterExpression.LEIF_DETERMINED, "Let's get out of here!");

        lastFrame().addDialogAction(new DialogAction(new Runnable() {
            @Override
            public void run() {
                if(ags.conditions().teams().getPlayerTeam().get(0) instanceof LeifUnit) {
                    ((LeifUnit) ags.conditions().teams().getPlayerTeam().get(0)).mount();
                }
            }
        }));

//        choreographShortPause();
    }

}
