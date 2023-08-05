package com.feiqn.wyrm.logic.ui.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.ui.PopupMenu;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapobjectdata.MapObject;
import com.feiqn.wyrm.models.mapobjectdata.prefabObjects.Ballista;
import com.feiqn.wyrm.models.mapobjectdata.prefabObjects.BreakableWall;
import com.feiqn.wyrm.models.unitdata.Unit;

public class BallistaActionsPopup extends PopupMenu {

    final BallistaActionsPopup self = this;

    private Array<LogicalTile> tilesInRange;
    private Ballista ballista;
    private Unit unit;

    public BallistaActionsPopup(WYRMGame game, Unit unit, MapObject object) {
        super(game);
        this.unit = unit;
        this.ballista = ((Ballista) object);
        highlightAttackableTiles();
        addSmallTargeted(unit);

    }
    public BallistaActionsPopup(WYRMGame game, Unit unit, Ballista ballista) {
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

        for(LogicalTile tile : game.activeBattleScreen.logicalMap.getTiles()) {
            if(game.activeBattleScreen.distanceBetweenTiles(unit.occupyingTile, tile) <= ballista.reach) {
                if(tile != unit.occupyingTile) {
                    tilesInRange.add(tile);
                    tile.highlightCanAttack(blueSquareRegion);
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

    @Override
    protected void addSmallTargeted(final Unit unit) {

        addActor(background);

        float width;
        float height;

        // WAIT
        final Label waitLabel = new Label("Wait", game.activeBattleScreen.menuLabelStyle);
        waitLabel.setFontScale(1);
        waitLabel.setPosition((unit.getRow() + 1), (unit.getY() + 1));
        addActor(waitLabel);

        width = waitLabel.getWidth() * 1.25f;
        height = waitLabel.getHeight() * 2;

        waitLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                if(unit.canMove()) {
                    unit.toggleCanMove();
                }
                clearHighlights();
                self.remove();
                game.activeBattleScreen.checkIfAllUnitsHaveMovedAndPhaseShouldChange(game.activeBattleScreen.currentTeam());
            }

        });

        // EXIT
        final Label exitLabel = new Label("Exit", game.activeBattleScreen.menuLabelStyle);
        exitLabel.setFontScale(1);
        exitLabel.setPosition(waitLabel.getX(), waitLabel.getY() + exitLabel.getHeight() * 1.5f);
        addActor(exitLabel);

        exitLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                if(unit.canMove()) {
                    unit.toggleCanMove();
                }
                ballista.exitUnit(unit);
                clearHighlights();
                self.remove();
                game.activeBattleScreen.checkIfAllUnitsHaveMovedAndPhaseShouldChange(game.activeBattleScreen.currentTeam());
            }

        });

        // TODO: attack label

    }
}
