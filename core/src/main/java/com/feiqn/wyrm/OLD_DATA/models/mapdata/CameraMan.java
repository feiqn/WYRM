package com.feiqn.wyrm.OLD_DATA.models.mapdata;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CameraMan extends Actor {

    private OrthographicCamera gameCamera;
    private boolean following;
    private Actor star;

    public CameraMan() {
        gameCamera = new OrthographicCamera();
    }

    @Override
    public void act(float delta) {
        // He is holding.
        if(following) {
            if(this.getX() != star.getX() || this.getY() != star.getY()) {
                setPosition(star.getX(), star.getY());
            }
        }
        super.act(delta);
        if(gameCamera.position.x != this.getX() ||
           gameCamera.position.y != this.getY()) {

            gameCamera.position.x = this.getX();
            gameCamera.position.y = this.getY();
        }
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
        following = true;
        star = actor;
    }

    public void stopFollowing() {
        following = false;
        star = null;
    }

    public OrthographicCamera camera() { return gameCamera; }
}
