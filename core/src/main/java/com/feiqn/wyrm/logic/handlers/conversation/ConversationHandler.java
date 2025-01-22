package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.WyrHUD;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.List;

public class ConversationHandler {

    private final WYRMGame game;

    private Conversation activeConversation;

    private final List<ConversationTrigger> triggers;

    public ConversationHandler(WYRMGame game, List<ConversationTrigger> triggers) {
        this.game = game;
        this.triggers = triggers;
    }

    public void checkTriggers(UnitRoster unit, Vector2 tilePosition) {
        for(ConversationTrigger trigger : triggers) {
            if(trigger.checkTrigger(unit, tilePosition)) {
                // Play the cutscene
                startCutscene(trigger.getConversation());
                break;
            }
        }
    }

    private void startCutscene(Conversation conversation) {
//        System.out.println("Cutscene triggered!");
        activeConversation = conversation;
        game.activeGridScreen.startConversation(conversation);
    }
}
