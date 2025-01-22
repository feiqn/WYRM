package com.feiqn.wyrm.logic.screens.gamescreens;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.Conversation;
import com.feiqn.wyrm.logic.handlers.conversation.ConversationHandler;
import com.feiqn.wyrm.logic.handlers.conversation.ConversationTrigger;
import com.feiqn.wyrm.logic.handlers.conversation.dialogscripts.DScript_1A_Leif_NeedToEscape;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.VictConInfoPanel;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.mapdata.StageList;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.EscapeOneVictCon;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GridScreen_1A extends GridScreen {

    // Use this as an example / template going forward.

    public GridScreen_1A(WYRMGame game) {
        super(game, StageList.STAGE_1A);
    }

    @Override
    public void show() {
        super.show();

        teamHandler.setAllyTeamUsed();

        setUpVictCons();
    }

    @Override
    protected void buildConversations() {
        // build cutscene listeners here then add

        List<ConversationTrigger> list = new List<ConversationTrigger>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public @NotNull Iterator<ConversationTrigger> iterator() {
                return null;
            }

            @Override
            public @NotNull Object[] toArray() {
                return new Object[0];
            }

            @Override
            public @NotNull <T> T[] toArray(@NotNull T[] a) {
                return null;
            }

            @Override
            public boolean add(ConversationTrigger conversationTrigger) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NotNull Collection<? extends ConversationTrigger> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NotNull Collection<? extends ConversationTrigger> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public ConversationTrigger get(int index) {
                return null;
            }

            @Override
            public ConversationTrigger set(int index, ConversationTrigger element) {
                return null;
            }

            @Override
            public void add(int index, ConversationTrigger element) {

            }

            @Override
            public ConversationTrigger remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public @NotNull ListIterator<ConversationTrigger> listIterator() {
                return null;
            }

            @Override
            public @NotNull ListIterator<ConversationTrigger> listIterator(int index) {
                return null;
            }

            @Override
            public @NotNull List<ConversationTrigger> subList(int fromIndex, int toIndex) {
                return List.of();
            }
        };

        // first build the conversations by adding prefab script
        // then build trigger metadata; rost list, vector list
        // then build triggers with conversations and metadata
        // then add triggers to handlers

        final Conversation leifNeedToEscape = new Conversation(game, new DScript_1A_Leif_NeedToEscape(game));

//        EnumSet<UnitRoster> rosterSetLeifNeedEscape = EnumSet.of()

//        ConversationTrigger triggerLeifNeedEscape = new ConversationTrigger(game, leifNeedToEscape);


        conversationHandler = new ConversationHandler(game, list);
    }

    @Override
    protected void setUpVictCons() {
        // TODO: Account for if player escapes north with Leif instead.
        final EscapeOneVictCon leifEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.LEIF, true);
        leifEscapeVictCon.setAssociatedCoordinate(18, 0);
        leifEscapeVictCon.setObjectiveText("[GREEN]Victory:[] Leif Escapes");
        leifEscapeVictCon.setMoreInfo("Leif can escape to the West, safely fleeing the assault.");
        conditionsHandler.addVictoryCondition(leifEscapeVictCon);

        // optional, Antal escapes through the north tile.
        final EscapeOneVictCon antalEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.ANTAL,false);
        antalEscapeVictCon.setAssociatedCoordinate(49, 25);
        antalEscapeVictCon.setObjectiveText("[ORANGE]Optional:[] Antal Survives and Escapes");
        antalEscapeVictCon.setMoreInfo("The allied ([GREEN]green[]) knight, [GOLD]Antal[], is trying to escape the assault with his life. To survive, he must reach the forest treeline by following the road north before he is killed by enemy soldiers.");
        conditionsHandler.addVictoryCondition(antalEscapeVictCon);
    }

    @Override
    public void stageClear() {
        game.campaignHandler.setStageAsCompleted(StageList.STAGE_1A);

        if(conditionsHandler.victoryConditionIsSatisfied(1)) { // Antal survived.
            game.campaignHandler.setUnitAsRecruited(UnitRoster.ANTAL);
            game.campaignHandler.setStageAsUnlocked(StageList.STAGE_2A);
        } else {
            game.campaignHandler.setStageAsUnlocked(StageList.STAGE_2B);
        }

    }
}
