package com.feiqn.wyrm.wyrefactor.handlers.campaign.army;

import com.feiqn.wyrm.WYRMGame;

public class ArmyHandler {
    // Handled by CampaignHandler
    // Tracks units', items, etc. over a given save file

    private final WYRMGame game;

    public ArmyHandler(WYRMGame game) {
        this.game = game;
    }
}
