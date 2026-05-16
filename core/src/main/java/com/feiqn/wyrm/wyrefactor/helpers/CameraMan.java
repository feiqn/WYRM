package com.feiqn.wyrm.wyrefactor.helpers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.*;

public class CameraMan extends Actor {

    private final OrthographicCamera gameCamera;
    private boolean following;
    private Actor star;

    public CameraMan() {
        gameCamera = new OrthographicCamera();
    }

    @Override
    public void act(float delta) {
        // He is holding.
        if(following) {
            final float xDif = this.getX() - star.getX();
            final float yDif = this.getY() - star.getY();
            if(Math.abs(xDif) > X_TOLERANCE || Math.abs(yDif) > Y_TOLERANCE) {
                this.clearActions();
                this.addAction(Actions.moveTo(star.getX(), star.getY(), FOLLOW_SPEED));
                // TODO:
                //  This does not function as intended.
                //  As is, when the tolerance is broke, the camera
                //  moves to spot-on the target's position.
                //  We want it to start moving when it is outside tolerance,
                //  but once within tolerance, stop moving prematurely.
            }
        }

        super.act(delta);

        if(gameCamera.position.x != this.getX() ||
           gameCamera.position.y != this.getY()) {

            gameCamera.position.x = this.getX();
            gameCamera.position.y = this.getY();
        }
        gameCamera.update();
    }

    @Override
    public void setPosition(float x, float y) {
        gameCamera.position.x = x;
        gameCamera.position.y = y;
        gameCamera.update();
        super.setPosition(x,y);
    }

    public void translate(float x, float y) {
        gameCamera.translate(x, y);
        gameCamera.update();

        this.setX(gameCamera.position.x);
        this.setY(gameCamera.position.y);
    }

    public void follow(Actor actor) {
        this.clearActions();
        star = actor;
        following = true;
    }

    public void standardize() {
        following = false;
        star = null;
    }

    public OrthographicCamera actual() { return gameCamera; }
}
