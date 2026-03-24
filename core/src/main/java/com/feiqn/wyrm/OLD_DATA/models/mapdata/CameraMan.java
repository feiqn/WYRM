package com.feiqn.wyrm.OLD_DATA.models.mapdata;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

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
            this.setX(star.getX());
            this.setY(star.getY());
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
        star = actor;
        this.addAction(new SequenceAction(
            Actions.moveTo(star.getX(), star.getY(), .2f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    following = true;
                }
            })
        ));
    }

    public void stopFollowing() {
        following = false;
        star = null;
    }

    public OrthographicCamera camera() { return gameCamera; }
}
