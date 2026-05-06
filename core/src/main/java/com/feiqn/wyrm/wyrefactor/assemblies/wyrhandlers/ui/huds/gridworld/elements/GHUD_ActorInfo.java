package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds.gridworld.elements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.shaders.WyrShaders;

import static com.feiqn.wyrm.wyrefactor.helpers.Wyr.FONT_SCALE;

public class GHUD_ActorInfo extends Window {

    // TODO: abstract with tileInfo

    private boolean isVisible = false;

    private final Table actorTable = new Table();
    private final Label nameLabel;
    private final Label infoLabel;
    private Drawable drawable;
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

        build();
    }

    private void build() {

    }

    public void setContext(RPGridActor actor) {
        nameLabel.setText(actor.getName());
        if(isVisible) return;
        isVisible = true;
        addAction(Actions.fadeIn(.3f));
    }

    private final static class HealthBar extends ProgressBar {

        private RPGridActor tracking;
        private final ShaderProgram shader = WyrShaders.Enemy.standard();

        public HealthBar(Skin skin, RPGridActor actor) {
            super(0, actor.stats().getMaxHP(), 1, false, skin);
            this.setHeight(0.5f);
            this.setDisabled(true);
            this.setValue(actor.stats().getRollingHP());
            this.tracking = actor;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.setShader(shader);
            super.draw(batch, parentAlpha);
            batch.setShader(null);
        }

        public void setTracking(RPGridActor actor) {
            tracking = actor;
            update();
        }

        public void update() {
            this.setValue(tracking.stats().getRollingHP());
        }

    }

}
