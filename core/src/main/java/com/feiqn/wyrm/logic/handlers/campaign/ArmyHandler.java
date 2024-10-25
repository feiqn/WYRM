package com.feiqn.wyrm.logic.handlers.campaign;

import com.feiqn.wyrm.WYRMGame;

public class ArmyHandler {
    // Handled by CampaignHandler
    // Tracks all living and dead recruited units, items, etc. over a given save file

    private final WYRMGame game;

    public ArmyHandler(WYRMGame game) {
        this.game = game;
    }
}
