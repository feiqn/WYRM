package com.feiqn.wyrm.OLD_DATA.logic.screens.storyA.stage1;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.post.DScript_1A_POST_Leif_ShouldFindAntal;
import com.feiqn.wyrm.OLD_DATA.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.AutoFillOLDWyrMap;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.player.LeifUnitOLD;

public class OLDGridScreen_CUTSCENE_Leif_ShouldFindAntal extends OLD_GridScreen {

    // Leif specifically fled east after helping Antal escape.

    public OLDGridScreen_CUTSCENE_Leif_ShouldFindAntal(WYRMGame game) {
        super(game);
    }

    @Override
    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/maps/forest_road.tmx");
        final MapProperties properties = tiledMap.getProperties();
        logicalMap = new AutoFillOLDWyrMap(game, (int)properties.get("width"), (int)properties.get("height"), tiledMap) {
            @Override
            public void setUpUnits() {
                final LeifUnitOLD testChar = new LeifUnitOLD(game);
                placeUnitAtPositionXY(testChar, 6, 6);
                conditionsHandler.addToTurnOrder(testChar);
                conditionsHandler.teams().getPlayerTeam().add(testChar);
                rootGroup.addActor(testChar);
                testChar.setCannotMove();
//                testChar.dismount();

//                final AntalUnit antalChar = new AntalUnit(game);
//                antalChar.setTeamAlignment(TeamAlignment.PLAYER);
//                placeUnitAtPositionXY(antalChar, 7, 7);
//                conditionsHandler.addToTurnOrder(antalChar);
//                conditionsHandler.teams().getAllyTeam().add(antalChar);
//                rootGroup.addActor(antalChar);
//                antalChar.setCannotMove();
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
                conditions().conversations().startCutscene(new DScript_1A_POST_Leif_ShouldFindAntal(game));
            }
        }, 3);
    }

}
