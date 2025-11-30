package com.feiqn.wyrm.logic.screens.storyA.stage1;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.post.DScript_1A_POST_Leif_FoundAntal;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.mapdata.AutoFillWyrMap;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.ally.recruitable.AntalUnit;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnit;

public class GridScreen_CUTSCENE_Leif_FoundAntal extends GridScreen {

    // Leif catches up with Antal after helping him escape in 1A.

    public GridScreen_CUTSCENE_Leif_FoundAntal(WYRMGame game) {
        super(game);
    }


    @Override
    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/maps/forest_road2.tmx");
        final MapProperties properties = tiledMap.getProperties();
        logicalMap = new AutoFillWyrMap(game, (int)properties.get("width"), (int)properties.get("height"), tiledMap) {
            @Override
            public void setUpUnits() {
                final LeifUnit testChar = new LeifUnit(game);
                placeUnitAtPositionXY(testChar, 4, 4);
                conditionsHandler.addToTurnOrder(testChar);
                conditionsHandler.teams().getPlayerTeam().add(testChar);
                rootGroup.addActor(testChar);
                testChar.setCannotMove();
//                testChar.dismount();

                final AntalUnit antalChar = new AntalUnit(game);
                antalChar.setTeamAlignment(TeamAlignment.PLAYER);
                placeUnitAtPositionXY(antalChar, 5, 5);
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
                conditions().conversations().startCutscene(new DScript_1A_POST_Leif_FoundAntal(game));
            }
        }, 3);
    }

}
