package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.fullscreenmenus;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.FullScreenMenu;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class CombatVisualizer extends FullScreenMenu {

    // The fully animated scenes.
    // These probably will come way last, maybe even post release tbh :/
    // idk just a possibility to keep in mind. it's a visual flare, but that's it.

    // simple map animations in the meantime.

    public enum Background {
        NONE,
        PLAINS,
        FOREST,
        INTERIOR_WOOD,
        INTERIOR_STONE,
    }

    private final Container<Image> backgroundContainer;
    private final Container<Table> tableContainer;

    private final Image background;
    private final Table visualizerTable;

    private final SimpleUnit attacker;
    private final SimpleUnit defender;

    private final int damage;

    private final boolean twice;

    private final Background backgroundID;


    public CombatVisualizer(WYRMGame game, SimpleUnit attacker, SimpleUnit defender, int damage, boolean twice, Background backgroundID) {
        super(game);
        clear();

        this.attacker = attacker;
        this.defender = defender;
        this.damage = damage;
        this.twice = twice;
        this.backgroundID = backgroundID;

        background = new Image();
        visualizerTable = new Table();

        backgroundContainer = new Container<>(background).fill();
        tableContainer = new Container<>(visualizerTable).fill();

//        add();
    }


    public void visualize() {

    }


}
