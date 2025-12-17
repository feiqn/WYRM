package com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.army;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.WyrType;

public class ArmyHandler extends WyrHandler {
    // Handled by CampaignHandler
    // Tracks units', items, etc. over a given save file

    public ArmyHandler(WYRMGame game) {
        super(game, WyrType.GRIDWORLD);
    }
}
