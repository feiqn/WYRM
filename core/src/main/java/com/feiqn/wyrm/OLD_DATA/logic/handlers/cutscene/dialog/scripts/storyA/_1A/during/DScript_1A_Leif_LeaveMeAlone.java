package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.OLD_CharacterExpression;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.CutsceneID;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_DialogAction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpg.rpgrid.RPGridAbilityID;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.prefab.UnitIDRoster;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.player.LeifUnitOLD;

public class DScript_1A_Leif_LeaveMeAlone extends OLD_ChoreographedCutsceneScript {

    // First time Leif gets attacked in 1A.

    private final String bfn;

    public DScript_1A_Leif_LeaveMeAlone(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_LEIF_LEAVE_ME_ALONE);
        this.bfn = WYRMGame.assets().bestFriendName;
    }

    @Override
    protected void declareTriggers() {
        armSingleUnitCombatCutsceneTrigger(UnitIDRoster.GENERIC_SOLDIER, true, true, false);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;
        if(slideshow.size != 0) return;


        choreographShortPause();

        set(OLD_CharacterExpression.LEIF_PANICKED, "No no no no no no no no");
        set(OLD_CharacterExpression.LEIF_PANICKED, "Get off of me!");

        choreographUseAbility(ags.conditions().teams().getPlayerTeam().get(0), RPGridAbilityID.DIVE_BOMB, ags.conditions().teams().getEnemyTeam().get(0));

        set(OLD_CharacterExpression.LEIF_HOPEFUL, bfn + "!");
        set(OLD_CharacterExpression.LEIF_WORRIED, "Ooooohhhhh thank you thank you thank you thank you thank you!"); // mounted char portrait

        choreographFocusOnLocation(45, 20);

        set(OLD_CharacterExpression.LEIF_HOPEFUL, "To the east! We can fly right over those flames, and the soldiers wont be able to chase us!");
        set(OLD_CharacterExpression.LEIF_DETERMINED, "Let's get out of here!");

        lastFrame().addDialogAction(new OLD_DialogAction(new Runnable() {
            @Override
            public void run() {
                if(ags.conditions().teams().getPlayerTeam().get(0) instanceof LeifUnitOLD) {
                    ((LeifUnitOLD) ags.conditions().teams().getPlayerTeam().get(0)).mount();
                }
            }
        }));

    }

}
