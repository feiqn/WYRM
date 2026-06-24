package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.hud.elements;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.WyrWinCon;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.handlers;

public class GHUD_WinCons extends VerticalGroup {

    private final Skin skin;

    private final Table conTable = new Table();
    private final Array<Panel> panels = new Array<>();

    public GHUD_WinCons(Skin skin) {
        super();
        this.skin = skin;

    }

    public void refresh() {
        if(panels.size == handlers.register().revealedVictoryConditions().size) return;
        for(WyrWinCon c : handlers.register().revealedVictoryConditions()) {
            boolean represented = false;
            for(Panel p : panels) {
                if(p.con == c) {
                    represented = true;
                    break;
                }
            }
            if(!represented) {
                final Panel panel = new Panel(skin, c);
                // TODO: lower turn order in hud() to compensate
                conTable.row();
                conTable.add(panel).left();
            }
        }
    }

    private static class Panel extends Window {

        private final WyrWinCon con;

        private Panel(Skin skin, WyrWinCon con) {
            super("", skin);
            this.con = con;
            super.setModal(false);
            super.setMovable(false);
            super.setResizable(false);

            if(con.getAssociatedActor() != null) {
                add(con.getImageDrawable());
            }
            String tag = "";
            switch(con.getNecessity()) {
                case VICTORY:
                    tag = "[GREEN]VICTORY: ";
                    break;
                case OPTIONAL:
                    tag = "[GOLDENROD]OPTIONAL: ";
                    break;
                case FAILURE:
                    tag = "[RED]FAILURE: ";
                    break;
                default:
                    break;
            }
            final Label tagLabel = new Label(tag, skin);
            add(tagLabel);
            add(con.getShortDescription());
            // todo: add click listener for long description
        }
    }
}
