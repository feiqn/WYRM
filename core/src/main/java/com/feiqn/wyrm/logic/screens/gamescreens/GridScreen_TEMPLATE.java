package com.feiqn.wyrm.logic.screens.gamescreens;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.GridScreen;

public class GridScreen_TEMPLATE extends GridScreen {

    // Checkout GridScreen_1A for a live, fully implemented example.

    public GridScreen_TEMPLATE(WYRMGame game) {
        super(game);
        // don't do anything else here, use show() so everything
        // is initialized first.
    }

    /* Here's all the things you should Overwrite when implementing
     * a new instance of GridScreen -- the fundamental "level" / "stage"
     * class of WYRM.
     */

    @Override
    public void show() {
        super.show();
        // conditionsHandler.teams().setAllyTeamUsed();
        // conditionsHandler.teams().setOtherTeamUsed();
    }

    @Override
    protected void loadMap() {
        // Do NOT call Super().
        // tiledMap = new TmxMapLoader().load("assets/.tmx");
        // logicalMap = new wyrMap(game) { ... };
    }

    @Override
    protected void buildConversations() {
        // TODO: come back and update this template after triggers implemented
        // build cutscene triggers
        // add cutscene triggers to new List<>() { ... };
        // ...
        // conversationHandler = new ConversationHandler(game, list);
    }

    @Override
    protected void setUpVictFailCons() {
        // build victory conditions
        // add conditions to handler
    }

    @Override
    public void stageClear() {
        // game.campaignHandler.setStageAsCompleted(StageList.STAGE_DEBUG);
        // handle flags for branching paths
    }

}
