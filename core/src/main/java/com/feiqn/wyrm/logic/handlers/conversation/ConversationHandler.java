package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.types.AreaTrigger;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.ConversationTrigger;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.types.CombatTrigger;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.types.DeathTrigger;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.types.TurnTrigger;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.List;

public class ConversationHandler {

    private final WYRMGame game;

    private Conversation activeConversation;

    private final Array<ConversationTrigger> triggers; // same

    public ConversationHandler(WYRMGame game, Array<ConversationTrigger> triggers) {
        this.game = game;
        this.triggers = triggers;
    }

    public void checkTurnTriggers(int turn) {
        for(ConversationTrigger trigger : triggers) {
            if(trigger instanceof TurnTrigger) {
                if(((TurnTrigger) trigger).checkTrigger(turn)) {
                    startCutscene(trigger.getScript());
                    break;
                }
            }
        }
    }

    public void checkCombatTriggers(UnitRoster unit, CombatTrigger.When when) {
        for(ConversationTrigger trigger : triggers) {
            if(trigger instanceof CombatTrigger) {
                if(((CombatTrigger) trigger).checkTrigger(unit, when)) {
                    startCutscene(trigger.getScript());
                    break;
                }
            }
        }
    }

    public void checkDeathTriggers(UnitRoster unit) {
        for(ConversationTrigger trigger : triggers) {
            if(trigger instanceof DeathTrigger) {
                if(((DeathTrigger) trigger).checkTrigger(unit)) {
                    startCutscene(trigger.getScript());
                    break;
                }
            }
        }
    }

    public void checkAreaTriggers(UnitRoster unit, Vector2 tilePosition) {
        for(ConversationTrigger trigger : triggers) {
            if(trigger instanceof AreaTrigger) {
                if(((AreaTrigger) trigger).checkTrigger(unit, tilePosition)) {
                    startCutscene(trigger.getScript());
                    break;
                }
            }
        }
    }

    private void startCutscene(DialogScript DScript) {
//        System.out.println("Cutscene triggered!");
        activeConversation = new Conversation(game, DScript);
        game.activeGridScreen.startConversation(activeConversation);
    }
}
