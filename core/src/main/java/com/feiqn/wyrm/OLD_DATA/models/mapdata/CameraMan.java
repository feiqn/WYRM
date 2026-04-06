package com.feiqn.wyrm.OLD_DATA.models.mapdata;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class CameraMan extends Actor {

    private OrthographicCamera gameCamera;
    private boolean following;
    private Actor star;

    private final float X_TOLERANCE = 1;
    private final float Y_TOLERANCE = 2f;

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
                this.addAction(Actions.moveTo(star.getX(), star.getY(), .175f));
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
        star = actor;
        following = true;
    }

    public void stopFollowing() {
        following = false;
        star = null;
    }

    public OrthographicCamera camera() { return gameCamera; }
}
