package com.feiqn.wyrm.logic.screens;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.post.DScript_1A_POST_Leif_Antal_Campfire;
import com.feiqn.wyrm.models.mapdata.AutoFillWyrMap;
import com.feiqn.wyrm.models.mapdata.WyrMap;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.ally.recruitable.AntalUnit;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnit;

public class DialogueScreen extends GridScreen {

    // Use as template / example

    private final GridScreen self = this;

    public DialogueScreen(WYRMGame game) {
        super(game);
    }

    @Override
    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/maps/roadside_campfire.tmx");
        final MapProperties properties = tiledMap.getProperties();
        logicalMap = new AutoFillWyrMap(game, (int)properties.get("width"), (int)properties.get("height"), tiledMap) {
            @Override
            public void setUpUnits() {
                final LeifUnit testChar = new LeifUnit(game);
                placeUnitAtPositionXY(testChar, 22, 22);
                conditionsHandler.addToTurnOrder(testChar);
                conditionsHandler.teams().getPlayerTeam().add(testChar);
                rootGroup.addActor(testChar);
                testChar.setCannotMove();
                testChar.dismount();

                final AntalUnit antalChar = new AntalUnit(game);
                antalChar.setTeamAlignment(TeamAlignment.PLAYER);
                placeUnitAtPositionXY(antalChar, 20, 20);
                conditionsHandler.addToTurnOrder(antalChar);
                conditionsHandler.teams().getAllyTeam().add(antalChar);
                rootGroup.addActor(antalChar);
                antalChar.setCannotMove();
            }
        };
    }

    @Override
    protected void initializeVariables() {
        super.initializeVariables();
        setInputMode(InputMode.CUTSCENE);
    }

    @Override
    protected boolean shouldRunAI() {
        return false;
    }

    @Override
    public void show() {
        super.show();
        inputMode = InputMode.CUTSCENE;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                conditions().conversations().startCutscene(new DScript_1A_POST_Leif_Antal_Campfire(game));
            }
        }, 3);
    }

//    @Override
//    protected void buildConversations() {
//        Array<ConversationTrigger> array = new Array<>();
//
//        TurnTrigger trigger = new TurnTrigger(new DScript_1A_POST_Leif_Antal_Campfire(this), 1);
//        array.add(trigger);
//
//        conditionsHandler.loadConversations(array);
//    }


}
