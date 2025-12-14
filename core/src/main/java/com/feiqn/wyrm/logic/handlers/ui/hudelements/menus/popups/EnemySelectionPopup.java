package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.PopupMenu;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;

public class EnemySelectionPopup extends PopupMenu {

    protected final Array<OLD_SimpleUnit> enemies;

    protected final int originX;
    protected final int originY;

    public EnemySelectionPopup(WYRMGame game, OLD_SimpleUnit selectedUnit, Array<OLD_SimpleUnit> enemies, int originX, int originY) {
        super(game);
        this.enemies = enemies;
        this.originX = originX;
        this.originY = originY;

        layout.padLeft(Gdx.graphics.getWidth() * 0.01f);
        layout.padRight((Gdx.graphics.getWidth() * 0.01f));
        layout.padTop(Gdx.graphics.getHeight() * 0.025f);
        layout.padBottom((Gdx.graphics.getHeight() * 0.025f));

        final Label backLabel = new Label("CANCEL", WYRMGame.assets().menuLabelStyle);
        backLabel.setFontScale(2);
        backLabel.setColor(Color.CYAN);
        backLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                game.activeOLDGridScreen.hud().addPopup(new FieldActionsPopup(game, selectedUnit, originY, originX));
            }
        });
        layout.add(backLabel).padBottom(Gdx.graphics.getHeight() * 0.01f);
        layout.row();

        for(OLD_SimpleUnit unit : enemies) {
            final Label attackLabel = new Label(unit.characterName, WYRMGame.assets().menuLabelStyle);
            attackLabel.setFontScale(2);
            attackLabel.setColor(Color.RED);
            attackLabel.addListener(new InputListener() {

                ToolTipPopup toolTipPopup;

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    // open attack interface
                    ags.hud().addPopup(new BattlePreviewPopup(game, selectedUnit, unit, originX, originY));
                    ags.activeUnit = null;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    toolTipPopup = new ToolTipPopup(game,"Attack the enemy!");
                    game.activeOLDGridScreen.hud().addToolTip(toolTipPopup);
//                    toolTipPopup.setPosition(attackLabel.getX() + layout.getWidth() * 1.5f, attackLabel.getY());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    game.activeOLDGridScreen.hud().removeToolTip();
                }
            });

            layout.add(attackLabel).padBottom(Gdx.graphics.getHeight() * 0.01f);
            layout.row();
        }

    }

}
