package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogAction;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;
import com.feiqn.wyrm.logic.handlers.gameplay.combat.CombatHandler;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnit;

public class DScript_1A_Leif_LeaveMeAlone extends ChoreographedDialogScript {

    private final String bfn;

    public DScript_1A_Leif_LeaveMeAlone(WYRMGame game) {
        super(game);
        this.bfn = game.assetHandler.bestFriend;
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        set(CharacterExpression.LEIF_PANICKED, "No no no no no no no no");
        set(CharacterExpression.LEIF_PANICKED, "Get off of me!");

        choreographUseAbility(ags.conditions().teams().getPlayerTeam().get(0), Abilities.DIVE_BOMB, ags.conditions().teams().getEnemyTeam().get(0));

        set(CharacterExpression.LEIF_HOPEFUL, bfn + "!");
        set(CharacterExpression.LEIF_WORRIED, "Ooooohhhhh thank you thank you thank you thank you thank you!");
        set(CharacterExpression.LEIF_DETERMINED, "Let's get out of here!");

        lastFrame().addDialogAction(new DialogAction(new Runnable() {
            @Override
            public void run() {
                if(ags.conditions().teams().getPlayerTeam().get(0) instanceof LeifUnit) {
                    ((LeifUnit) ags.conditions().teams().getPlayerTeam().get(0)).mount();
                }
            }
        }));
    }

}
