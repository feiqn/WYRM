package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.prefabs;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.math.stats.WyrStatusCondition;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.AbilityID;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatusConditionID.*;

public final class GridAbilitySequences implements WyrFrame {

    private GridAbilitySequences() {}

    public static SequenceAction fromID(AbilityID abilityID, WyrActor subject, WyrActor object, WyrActor prepositional) {
        switch(abilityID) {

            case DIVE_BOMB:
                return diveBomb(subject, object);

            default:
                return null;
        }
    }

    public static SequenceAction diveBomb(WyrActor attacker, WyrActor defender) {
        // returned and applied to attacker

        final SequenceAction rV = Actions.sequence();

        if(attacker.stats().isMounted()) {
            // fly up first
        }

        final Image silhouette = new Image(handlers.assets().pegKnightTexture);
        silhouette.setColor(0,0,0,0);
        silhouette.setPosition(defender.gridX() -1, defender.gridY() +1);

        rV.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                handlers.screen().getGameStage().addActor(silhouette);

                silhouette.addAction(Actions.sequence(
                    Actions.parallel(
                        Actions.fadeIn(.2f),
                        Actions.moveTo(defender.gridX(), defender.gridY(), .3f)
                    ),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            defender.stats().applyCondition(new WyrStatusCondition(STUNNED));
                        }
                    }),
                    Actions.parallel(
                        Actions.fadeOut(.5f),
                        Actions.moveTo(defender.gridX() + 1, defender.gridY() + 1, .5f)
                    )
                ));
            }
        }));

        // fly back if flew up

        return rV;
    }

}
