package com.feiqn.wyrm.logic.screens.storyA.stage2;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._2A.pre.DScript_2A_PRE_LeifAntal_GatesAreClosed;
import com.feiqn.wyrm.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.models.mapdata.AutoFillOLDWyrMap;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.ally.recruitable.AntalUnitOLD;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnitOLD;

public class OLDGridScreen_CUTSCENE_Leif_Antal_GatesAreClosed extends OLD_GridScreen {

    public OLDGridScreen_CUTSCENE_Leif_Antal_GatesAreClosed(WYRMGame game) {
        super(game);
    }

    @Override
    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/maps/walled_city_gates.tmx");
        final MapProperties properties = tiledMap.getProperties();
        logicalMap = new AutoFillOLDWyrMap(game, (int)properties.get("width"), (int)properties.get("height"), tiledMap) {
            @Override
            public void setUpUnits() {
                final LeifUnitOLD testChar = new LeifUnitOLD(game);
                placeUnitAtPositionXY(testChar, 15, 14);
                conditionsHandler.addToTurnOrder(testChar);
                conditionsHandler.teams().getPlayerTeam().add(testChar);
                rootGroup.addActor(testChar);
                testChar.setCannotMove();
                testChar.dismount();

                final AntalUnitOLD antalChar = new AntalUnitOLD(game);
                antalChar.setTeamAlignment(TeamAlignment.PLAYER);
                placeUnitAtPositionXY(antalChar, 14, 15);
                conditionsHandler.addToTurnOrder(antalChar);
                conditionsHandler.teams().getPlayerTeam().add(antalChar);
                rootGroup.addActor(antalChar);
                antalChar.setCannotMove();

                // TODO: soldiers blocking the gate, etc
            }
        };
    }
    @Override
    protected void initializeVariables() {
        super.initializeVariables();
        setInputMode(OLD_InputMode.LOCKED);
    }

    @Override
    protected boolean shouldRunAI() {
        return false;
    }

    @Override
    public void show() {
        super.show();
        OLDInputMode = OLD_InputMode.LOCKED;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                conditions().conversations().startCutscene(new DScript_2A_PRE_LeifAntal_GatesAreClosed(game));
            }
        }, 3);
    }

}
