package com.feiqn.wyrm.logic.screens.cutscenes.stage2;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.post.DScript_1A_POST_Leif_ShouldFindAntal;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._2A.pre.DScript_2A_PRE_Leif_Antal_GatesAreClosed;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.ConversationTrigger;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.types.TurnTrigger;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.mapdata.AutoFillWyrMap;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.ally.recruitable.AntalUnit;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnit;

public class GridScreen_CUTSCENE_Leif_Antal_GatesAreClosed extends GridScreen {

    public GridScreen_CUTSCENE_Leif_Antal_GatesAreClosed(WYRMGame game) {
        super(game);
    }

    @Override
    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/maps/walled_city_gates.tmx");
        final MapProperties properties = tiledMap.getProperties();
        logicalMap = new AutoFillWyrMap(game, (int)properties.get("width"), (int)properties.get("height"), tiledMap) {
            @Override
            public void setUpUnits() {
                final LeifUnit testChar = new LeifUnit(game);
                placeUnitAtPositionXY(testChar, 10, 9);
                conditionsHandler.addToTurnOrder(testChar);
                conditionsHandler.teams().getPlayerTeam().add(testChar);
                rootGroup.addActor(testChar);
                testChar.setCannotMove();
                testChar.dismount();

                final AntalUnit antalChar = new AntalUnit(game);
                antalChar.setTeamAlignment(TeamAlignment.PLAYER);
                placeUnitAtPositionXY(antalChar, 9, 10);
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
        setInputMode(InputMode.LOCKED);
    }

    @Override
    protected boolean shouldRunAI() {
        return false;
    }

    @Override
    protected void buildConversations() {
        Array<ConversationTrigger> array = new Array<>();

        TurnTrigger trigger = new TurnTrigger(new DScript_2A_PRE_Leif_Antal_GatesAreClosed(game), 1);
        array.add(trigger);

        conditionsHandler.loadConversations(array);
    }

    @Override
    public void show() {
        super.show();
        inputMode = InputMode.LOCKED;



//        Timer.schedule(new Timer.Task() {
//            @Override
//            public void run() {
//                conditions().conversations().startCutscene(new DScript_2A_PRE_Leif_Antal_GatesAreClosed(game));
//            }
//        }, 3);
    }

}
