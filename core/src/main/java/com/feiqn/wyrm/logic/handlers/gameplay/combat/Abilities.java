package com.feiqn.wyrm.logic.handlers.gameplay.combat;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnitOLD;

public class Abilities {

    private final WYRMGame game;

    public Abilities(WYRMGame game) {
        this.game = game;
    }

    public void FireLighter(OLD_SimpleUnit attacker, OLD_SimpleUnit defender) {
        defender.burn();
        defender.burn();
        // deal 1 damage
        // play a blowing-fire effect
    }

    public void Shove(OLD_SimpleUnit attacker, Array<OLD_SimpleUnit> defenders) {
        // for each defender, first determine its cardinal
        // direction from attacker,
        // then determine if the next tile in that direction
        // is occupied and or traversable by that unit's type
        // if the spot is valid for shove, move each defender
        // into the next tile.
        // apply a "bump" / "push-back" stutter in the animation
        // play an impact sound
        // later: apply some dust animation under the defenders


    }

    public void DiveBomb(OLD_SimpleUnit defender) {
        // new image from attacker's flyer mount drawable
        // fade in new image up and to the left of defender
        // image "swoop" down on defender,
        // visually apply stun to defender
        // image fly off up right
        // image fade out

        game.activeOLDGridScreen.setInputMode(OLD_GridScreen.OLD_InputMode.CUTSCENE);

        LeifUnitOLD lu = new LeifUnitOLD(game);
        lu.setSize(1, 1.5f);
        lu.setColor(0,0,0,0);
        lu.setPosition(defender.getX() - 1, defender.getY() + 1);

        game.activeOLDGridScreen.gameStage.addActor(lu);

        lu.addAction(Actions.sequence(
            Actions.parallel(
                Actions.fadeIn(.2f),
                Actions.moveTo(defender.getX(), defender.getY(), .5f)
                // todo: animation / drawable changes
            ),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    final Label damageLabel = new Label("Stunned!", WYRMGame.assets().menuLabelStyle);
                    damageLabel.setFontScale(3);

                    game.activeOLDGridScreen.hudStage.addActor(damageLabel);
                    damageLabel.setPosition(Gdx.graphics.getWidth() * .2f, Gdx.graphics.getHeight() * .6f);
                    defender.stun();

                    // apply affects here?

                    damageLabel.addAction(Actions.sequence(
                        Actions.parallel(
                            Actions.moveTo(damageLabel.getX(), Gdx.graphics.getHeight() * .8f, 3),
                            Actions.fadeOut(3.5f)
                        ),
                        Actions.removeActor()
                    ));
                }
            }),
            Actions.parallel(
                Actions.fadeOut(.2f),
                Actions.moveTo(defender.getX() + 1, defender.getY() + 1)
            ),
            Actions.removeActor()
        ));
    }

}
