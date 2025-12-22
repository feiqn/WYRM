package com.feiqn.wyrm.OLD_DATA.logic.screens.storyA.stage1;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.post.DScript_1A_POST_Leif_EscapedAlone;
import com.feiqn.wyrm.OLD_DATA.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.AutoFillOLDWyrMap;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.player.LeifUnitOLD;

public class OLDGridScreen_CUTSCENE_Leif_EscapedAlone extends OLD_GridScreen {

    // Leif escaped to the east in 1A without saving Antal first.

    public OLDGridScreen_CUTSCENE_Leif_EscapedAlone(WYRMGame game) {
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
                placeUnitAtPositionXY(testChar, 8, 8);
                conditionsHandler.addToTurnOrder(testChar);
                conditionsHandler.teams().getPlayerTeam().add(testChar);
                rootGroup.addActor(testChar);
                testChar.setCannotMove();
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
                conditions().conversations().startCutscene(new DScript_1A_POST_Leif_EscapedAlone(game));
            }
        }, 3);
    }

}
