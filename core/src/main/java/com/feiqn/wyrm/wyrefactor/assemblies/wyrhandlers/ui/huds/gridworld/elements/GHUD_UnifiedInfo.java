package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds.gridworld.elements;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.FONT_SCALE;

public class GHUD_UnifiedInfo extends Window {

    private final RPGridMetaHandler h; // It's fun to just type "h".

    private final VerticalGroup verticalGroup = new VerticalGroup();


    private final Table winConTable   = new Table();
    private final Table tileInfoTable = new Table();
    private final Table actorInfoTable = new Table();

    private Image actorPreviewThumbnail;

    private HealthBar actorHealthBar;

    private final Label tileTypeLabel;
    private final Label tileStatsLabel;

    private final Label actorNameLabel;
    private final Label actorHealthLabel;
    private final Label actorMoreInfoLabel;

    private final Skin skin; // TODO: static asset ref

    private final GHUD_UnifiedInfo self = this;


    public GHUD_UnifiedInfo(Skin skin, RPGridMetaHandler metaHandler) {
        super("", skin);
        this.h = metaHandler;
        this.skin = skin;

        super.setModal(false);
        super.setMovable(false);
        super.setResizable(false);


        tileTypeLabel  = new Label("", skin);
        tileTypeLabel.setFontScale(FONT_SCALE);
//        tileTypeLabel.setWrap(true);

        tileStatsLabel = new Label("", skin);
        tileStatsLabel.setFontScale(FONT_SCALE);

        actorNameLabel = new Label("", skin);
        actorNameLabel.setFontScale(FONT_SCALE);

        actorHealthLabel = new Label("", skin);
        actorHealthLabel.setFontScale(FONT_SCALE);

        actorMoreInfoLabel = new Label("", skin);
        actorMoreInfoLabel.setFontScale(FONT_SCALE);

        rebuild();
    }

    private void buildWinConTable() {
        winConTable.clearChildren();
        winConTable.add(new Label("Victory:", skin)).left();
        winConTable.row();
        // for con in cons... add to
        winConTable.add(new Label("Failure:", skin)).left();
        winConTable.row();
        // for cons...
    }

    private void buildTileInfoTable() {
        tileInfoTable.clearChildren();
        tileInfoTable.add(tileTypeLabel).left().expandX();
        tileInfoTable.row();
        tileInfoTable.add(tileStatsLabel).left();
        tileInfoTable.row();
    }

    private void buildActorInfoTable() {
        actorInfoTable.clearChildren();
        actorInfoTable.add(actorNameLabel).left();
        actorInfoTable.row();
        actorInfoTable.add(actorHealthLabel).left();
        actorInfoTable.add(actorHealthBar).expandX();
        actorInfoTable.row();
//        actorInfoTable.add(actorMoreInfoLabel);
    }


    public void rebuild() {
        update(new Runnable() {
            @Override
            public void run() {

                verticalGroup.clearChildren();

//                if(h.register().revealedVictoryConditions().size > 0) {
                    buildWinConTable();
                    self.add(winConTable).fill();
                    self.row();
                    self.add(new Divider(skin)).expandX();
                    self.row();
//                }

//                if(h.register().hoveredTile() != null) {
                    buildTileInfoTable();
                    self.add(tileInfoTable).fill();
                    self.row();
                    self.add(new Divider(skin)).expandX();
                    self.row();
//                }

//                if(h.register().getHoveredActor() != null) {
                    buildActorInfoTable();
                    self.add(actorInfoTable).fill();
                    self.row();
                    self.add(new Divider(skin)).fill();
                    self.row();
//                }

            }
        });

    }

    public void updateTileContent(GridTile tile) {
        tileTypeLabel.setText("" + tile.getTileType());
        //
    }
    public void updateActorContext(RPGridActor actor) {
        actorNameLabel.setText(actor.getName()); // todo: true names / generic
    }

    private void update(Runnable newBuild) {
        final RunnableAction rebuild = new RunnableAction();
        rebuild.setRunnable(newBuild);
        addAction(Actions.sequence(
            Actions.fadeOut(.2f),
            rebuild,
            Actions.fadeIn(.2f)
        ));
    }

    private final static class HealthBar extends ProgressBar {

        private final RPGridActor tracking;

        public HealthBar(Skin skin, RPGridActor actor) {
            super(0, actor.stats().getMaxHP(), 1, false, skin);
            this.setHeight(0.5f);
            this.setDisabled(true);
            this.setValue(actor.stats().getRollingHP());
            this.tracking = actor;
        }


        public void update() {
            this.setValue(tracking.stats().getRollingHP());
        }

    }

    private final static class Divider extends ProgressBar {

        public Divider(Skin skin) {
            super(0, 1, 1, false, skin);
            this.clamp(1);
            this.setHeight(0.002f);
            this.setDisabled(true);
        }

    }

}
