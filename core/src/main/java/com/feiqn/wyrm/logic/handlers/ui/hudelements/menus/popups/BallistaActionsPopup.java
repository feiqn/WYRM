package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.PopupMenu;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.MapObject;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.BallistaObject;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class BallistaActionsPopup extends PopupMenu {

    final BallistaActionsPopup self = this;

    private Array<LogicalTile> tilesInRange;
    private BallistaObject ballista;
    private SimpleUnit unit;

    public BallistaActionsPopup(WYRMGame game, SimpleUnit unit, MapObject object) {
        super(game);
        this.unit = unit;
        this.ballista = ((BallistaObject) object);
        highlightAttackableTiles();
        addSmallTargeted(unit);

    }

    public BallistaActionsPopup(WYRMGame game, SimpleUnit unit, BallistaObject ballista) {
        super(game);
        this.unit = unit;
        this.ballista = ballista;
        highlightAttackableTiles();
        addSmallTargeted(unit);
    }

    protected void highlightAttackableTiles() {
        tilesInRange = new Array<>();

        for(LogicalTile tile : game.activeGridScreen.getLogicalMap().getTiles()) {
            if(game.activeGridScreen.getLogicalMap().distanceBetweenTiles(unit.getOccupyingTile(), tile) <= ballista.reach) {
                if(tile != unit.getOccupyingTile()) {
                    tilesInRange.add(tile);
                    tile.highlightCanAttack();
                    tile.addListener(new ClickListener() {
                        boolean dragged;
                        boolean clicked;

                        @Override
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                        }

                        @Override
                        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                        }

                        @Override
                        public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                            dragged = true;
                            clicked = false;
                        }

                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            dragged = false;
                            return true;
                        }

                        @Override
                        public void touchUp(InputEvent event, float x, float y, int point, int button)  {
                            if(dragged) {
                                dragged = false;
                                clicked = false;
                                return;
                            }

                            clicked = true;

                            if(tile.isOccupied()) {
                                game.activeGridScreen.conditions().combat().visualizeCombat(unit, tile.getOccupyingUnit());
                                unit.setCannotMove();

                                for(LogicalTile tiles : tilesInRange) {
                                    tiles.clearHighlight();
                                    tiles.removeListener(this);
                                }
                                game.activeGridScreen.checkLineOrder();
                            }

                        }
                    });
                }
            }
        }
    }

    public void clearHighlights() {
        for(LogicalTile tile : tilesInRange) {
            tile.clearHighlight();
            tile.removeListener(tile.getListeners().get(tile.getListeners().size - 1));
        }
        tilesInRange = new Array<>();
    }

    protected void addSmallTargeted(final SimpleUnit unit) {

        // WAIT
//        final Label waitLabel = new Label("Wait", game.assetHandler.menuLabelStyle);
//
//        waitLabel.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int point, int button) {
//                unit.setCannotMove();
//                clearHighlights();
////                self.remove();
//                game.activeGridScreen.checkLineOrder();
//            }
//
//        });

        // EXIT
        final Label exitLabel = new Label("Exit", game.assetHandler.menuLabelStyle);

        exitLabel.addListener(new InputListener() {
            boolean dragged;
            boolean clicked;

            final ToolTipPopup toolTipPopup = new ToolTipPopup(game,"Return to previous menu.");

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                game.activeGridScreen.hud().addToolTip(toolTipPopup);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                game.activeGridScreen.hud().removeToolTip();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
//                unit.setCannotMove();
//                ballista.exitUnit(unit);
                clearHighlights();
//                self.remove();
                game.activeGridScreen.hud().addPopup(new FieldActionsPopup(game, unit, (int)self.getY(), (int)self.getX()));

//                game.activeGridScreen.checkLineOrder();
            }

        });

        final Label attackLabel = new Label("Attack", game.assetHandler.menuLabelStyle);

        attackLabel.addListener(new ClickListener() {
                                    boolean dragged;
                                    boolean clicked;

                                    final ToolTipPopup toolTipPopup = new ToolTipPopup(game,"Click on an enemy in a highlighted square to select target for attack.");

                                    @Override
                                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                        game.activeGridScreen.hud().addToolTip(toolTipPopup);
                                    }

                                    @Override
                                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                                        game.activeGridScreen.hud().removeToolTip();
                                    }
                                });

        layout.add(attackLabel);
        layout.row();
        layout.add(exitLabel);

    }
}
