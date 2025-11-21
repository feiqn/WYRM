package com.feiqn.wyrm.models.mapdata;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.feiqn.wyrm.logic.screens.GridScreen;

public class CameraMan extends Actor {

    private OrthographicCamera gameCamera;
    private GridScreen scene;
    private boolean following;
    private Actor star;

    public CameraMan(GridScreen scene) {
        this.scene = scene;
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

    // TODO: make his camera private and let him do his own job
    public OrthographicCamera camera() { return gameCamera; }
}
