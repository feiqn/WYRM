package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors;

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
import com.badlogic.gdx.utils.*;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.inventory.WyrInventory;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.WyrItem;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.shaders.WyrShaders;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Examinable;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

/** Top-level for any actor in the WyrFrame system.
 */
public class WyrActor extends Image implements Wyr, Examinable {

    protected final ActorType actorType;

    protected MetaHandler h = null;

    protected WyrAnimator animator = null;

    protected WyrStats stats = null;

    protected WyrInventory inventory = null;

    protected final Array<WyrInteraction> staticInteractions    = new Array<>();
    protected final Array<WyrInteraction> ephemeralInteractions = new Array<>();

    protected ShaderState shaderState = ShaderState.STANDARD;
    protected ShaderProgram shader = null;

    private boolean hoveredOver    = false;
    private boolean hoverActivated = false;

    private float hoverTime = 0;

    // TODO:
    //  Need to come back and clean all this up again
    //  to make consistent with new singleton implementation
    //  as of 12/13/25 (I learned to do it.)
    //                  ^^^ I did??? Sorry, I forgot again. Wtf?

    public WyrActor(ActorType actorType) {
        this(actorType, (Drawable)null);
    }
    public WyrActor(ActorType actorType,  @Null NinePatch patch) {
        this(actorType, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public WyrActor(ActorType actorType,  @Null TextureRegion region) {
        this(actorType, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public WyrActor(ActorType actorType, Texture texture) {
        this(actorType, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public WyrActor(ActorType actorType, Skin skin, String drawableName) {
        this(actorType, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public WyrActor(ActorType actorType, @Null Drawable drawable) {
        this(actorType, drawable, Scaling.stretch, Align.center);
    }
    public WyrActor(ActorType actorType,  @Null Drawable drawable, Scaling scaling) {
        this(actorType, drawable, scaling, Align.center);
    }
    public WyrActor(ActorType actorType, @Null Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
        this.actorType = actorType;
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
        super.draw(batch, parentAlpha);
        batch.setShader(null);
    }

    public void kill() { this.remove(); }

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
                this.shader = WyrShaders.Player.dim();
                break;
            case HIGHLIGHT:
                this.shader = WyrShaders.Player.highlight();
                break;
            case STANDARD:
                this.shader = WyrShaders.Player.standard();
                break;
            default:
                break;
        }
    }

    public void addEphemeralInteraction(WyrInteraction interaction) { ephemeralInteractions.add(interaction); }
    public void clearEphemeralInteractions() { ephemeralInteractions.clear(); }
    public Array<WyrInteraction> getInteractions() {
        final Array<WyrInteraction> returnValue = new Array<>();
        returnValue.addAll(ephemeralInteractions);
        returnValue.addAll(staticInteractions);
        return returnValue;
    }
    public WyrAnimator getAnimator()  { return animator; }
    public int getMaxHP()           { return stats.getMaxHP();     }
    public int getRollingHP()       { return stats.getRollingHP(); }
    public int getRollingAP()       { return stats.getRollingAP(); }
    public ActorType getActorType() { return actorType;            }
    public WyrStats stats()         { return stats;                }

}
