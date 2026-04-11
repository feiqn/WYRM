package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;
import com.feiqn.wyrm.wyrefactor.helpers.Examinable;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.shaders.Shaders;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions.WyrInteraction;

public abstract class WyrActor<
        Animation extends WyrAnimator<?>,
        Interaction extends WyrInteraction<?,?>
            > extends Image implements Wyr, Examinable {

    public enum ActorType {
        UNIT,
        PROP,
        ITEM,
        BULLET,
    }

    protected final WyrStats<?> stats;

    protected final ActorType actorType;

    protected Animation animator;

    public enum ShaderState {
        DIM,
        HIGHLIGHT,
        STANDARD
    }

    protected final Array<Interaction> staticInteractions    = new Array<>();
    protected final Array<Interaction> ephemeralInteractions = new Array<>();

    protected ShaderState shaderState = ShaderState.STANDARD;
    protected ShaderProgram shader    = null;

    private boolean hoveredOver = false;
    private boolean hoverActivated = false;

    private float hoverTime = 0;

    final WyrType wyrType;

    // TODO:
    //  Need to come back and clean all this up again
    //  to make consistent with new singleton implementation
    //  as of 12/13/25 (I learned to do it.)
    //                  ^^^ I did??? Sorry, I forgot again. Wtf?

    public WyrActor(WyrType type, ActorType actorType) {
        this(type, actorType, (Drawable)null);
    }
    public WyrActor (WyrType type, ActorType actorType,  @Null NinePatch patch) {
        this(type, actorType, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public WyrActor(WyrType type, ActorType actorType,  @Null TextureRegion region) {
        this(type, actorType, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public WyrActor(WyrType type, ActorType actorType, Texture texture) {
        this(type, actorType, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public WyrActor(WyrType type, ActorType actorType, Skin skin, String drawableName) {
        this(type, actorType, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public WyrActor(WyrType type, ActorType actorType, @Null Drawable drawable) {
        this(type, actorType, drawable, Scaling.stretch, Align.center);
    }
    public WyrActor(WyrType type, ActorType actorType,  @Null Drawable drawable, Scaling scaling) {
        this(type, actorType, drawable, scaling, Align.center);
    }
    public WyrActor(WyrType type, ActorType actorType, @Null Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
        this.wyrType = type;
        this.actorType = actorType;
        this.stats = new WyrStats<>(this, actorType);
        this.setAlign(Align.center);
        this.setSize(1, 1); // just a little square
        this.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hoveredOver = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                hoveredOver = false;
            }
        });

    }

    @Override
    public void act(float delta) {
        if(!hoveredOver && hoverTime > 0) { // tick down
            hoverTime -= delta;
            if(hoverTime <= 0) {
                hoverTime = 0;
                unHover();
            }
        } else if(!hoverActivated && hoveredOver && hoverTime < .1f) { // tick up
            hoverTime += delta;
            if(hoverTime >= .1f) {
                hoverOver();
            }
        }

        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setShader(shader);
        super.draw(batch,parentAlpha);
        batch.setShader(null);
    }

    protected void hoverOver() {
        hoverActivated = true;
    }

    protected void unHover() {
        hoverActivated = false;
    }

    public void applyShader(ShaderState state) {
        if(this.shaderState == state) return;
        this.shaderState = state;
        switch(shaderState) {
            case DIM:
//                this.shader = Shaders.enemyDimShader();
                break;
            case HIGHLIGHT:
//                this.shader = Shaders.enemyHighlightShader();
                break;
            case STANDARD:
                this.shader = null;
                break;
        }
    }

    public void addEphemeralInteraction(Interaction interaction) { ephemeralInteractions.add(interaction); }
    public void clearInteractions() { ephemeralInteractions.clear(); }
    public Array<Interaction> getInteractions() {
        final Array<Interaction> returnValue = new Array<>();
        returnValue.addAll(ephemeralInteractions);
        returnValue.addAll(staticInteractions);
        return returnValue;
    }

}
