package com.feiqn.wyrm.wyrefactor.wyrscreen;

import com.badlogic.gdx.ScreenAdapter;
import com.feiqn.wyrm.wyrefactor.Wyr_DEPRECATED;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.WyrCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayer;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.WyrHUD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrMap;

public abstract class WyrScreen<Handler extends MetaHandler<?, ?, ?, ?, ?, ?, ?>> extends ScreenAdapter implements Wyr {

    public WyrScreen() {}

    public abstract Handler handlers();

}
