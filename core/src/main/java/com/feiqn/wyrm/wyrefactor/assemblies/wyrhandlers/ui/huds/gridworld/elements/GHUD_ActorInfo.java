package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds.gridworld.elements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.shaders.WyrShaders;
import com.feiqn.wyrm.wyrefactor.helpers.ActorType;

import static com.feiqn.wyrm.wyrefactor.helpers.Wyr.FONT_SCALE;

public class GHUD_ActorInfo extends Window {

    // TODO: abstract with tileInfo

    private boolean isVisible = false;

    private final Table actorTable = new Table();
    private final Label nameLabel;
    private final Label infoLabel;
    private final Image thumbnail = new Image();
    private HealthBar healthBar;

    private final Skin skin;

    public GHUD_ActorInfo(Skin skin) {
        super("", skin);
        this.skin = skin;

        super.setModal(false);
        super.setMovable(false);
        super.setResizable(false);

        nameLabel = new Label("", skin);
        infoLabel = new Label("", skin);
        nameLabel.setFontScale(FONT_SCALE);
        infoLabel.setFontScale(FONT_SCALE);

        this.add(actorTable).fill();

        this.setVisible(false);
    }

    private void build() {
        actorTable.clearChildren();
        actorTable.add(thumbnail).left();
        actorTable.add(nameLabel).right().expandX();
        actorTable.row();
        actorTable.add(infoLabel).right().expandX();
        actorTable.row();
        actorTable.add(healthBar).expandX();
        actorTable.row();
    }

    public void setContext(RPGridActor actor) {
        if(actor == null) return;
        if(actor.getActorType() == ActorType.UI) return;
        nameLabel.setText(actor.getName());
        thumbnail.setDrawable(actor.getDrawable());
        healthBar = new HealthBar(skin, actor); // TODO: pooling
        if(isVisible) return;
        isVisible = true;
        setVisible(true);
        build();
        addAction(Actions.fadeIn(.3f));
    }

    private final static class HealthBar extends ProgressBar {

        private final RPGridActor tracking;
        private final ShaderProgram shader = WyrShaders.Enemy.standard();

        public HealthBar(Skin skin, RPGridActor actor) {
            super(0, actor.getMaxHP(), 1, false, skin);
            this.setHeight(0.5f);
            this.setDisabled(true);
            tracking = actor;
            update();
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.setShader(shader);
            super.draw(batch, parentAlpha);
            batch.setShader(null);
        }

        public void update() {
            this.setValue(tracking.stats().getRollingHP());
        }

    }

}
