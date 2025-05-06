package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.PopupMenu;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.MapObject;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.Ballista;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class BallistaActionsPopup extends PopupMenu {

    final BallistaActionsPopup self = this;

    private Array<LogicalTile> tilesInRange;
    private Ballista ballista;
    private SimpleUnit unit;

    public BallistaActionsPopup(WYRMGame game, SimpleUnit unit, MapObject object) {
        super(game);
        this.unit = unit;
        this.ballista = ((Ballista) object);
        highlightAttackableTiles();
        addSmallTargeted(unit);

    }
    public BallistaActionsPopup(WYRMGame game, SimpleUnit unit, Ballista ballista) {
        super(game);
        this.unit = unit;
        this.ballista = ballista;
        highlightAttackableTiles();
        addSmallTargeted(unit);
    }

    protected void highlightAttackableTiles() {
        tilesInRange = new Array<>();

        final Texture blueSquareTexture = new Texture(Gdx.files.internal("ui/menu.png"));
        final TextureRegion blueSquareRegion = new TextureRegion(blueSquareTexture,0,0,100,100);

        for(LogicalTile tile : game.activeGridScreen.getLogicalMap().getTiles()) {
            if(game.activeGridScreen.getLogicalMap().distanceBetweenTiles(unit.getOccupyingTile(), tile) <= ballista.reach) {
                if(tile != unit.getOccupyingTile()) {
                    tilesInRange.add(tile);
                    tile.highlightCanAttack();
                }
            }
        }
    }

    public void clearHighlights() {
        for(LogicalTile tile : tilesInRange) {
            tile.clearHighlight();
        }
        tilesInRange = new Array<>();
    }

    protected void addSmallTargeted(final SimpleUnit unit) {

        // WAIT
        final Label waitLabel = new Label("Wait", game.assetHandler.menuLabelStyle);

        waitLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                unit.setCannotMove();
                clearHighlights();
//                self.remove();
                game.activeGridScreen.checkLineOrder();
            }

        });

        // EXIT
        final Label exitLabel = new Label("Exit", game.assetHandler.menuLabelStyle);

        exitLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                unit.setCannotMove();
                ballista.exitUnit(unit);
                clearHighlights();
//                self.remove();
                game.activeGridScreen.checkLineOrder();
            }

        });

        // TODO: attack label

        layout.add(waitLabel);
        layout.row();
        layout.add(exitLabel);

    }
}
