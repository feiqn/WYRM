package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.perGame.WYRM;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.WyrCutscene;

import java.util.HashMap;

public class WyrCutscenePlayer extends WyrHandler {

    protected Stage gameStage;

    public WyrCutscenePlayer() {}

    public WyrCutscenePlayer(MetaHandler metaHandler) {
        super(metaHandler);
    }

    public void playCutscene(WyrCutscene cutscene) {
        // parse script and act out upon gameStage
    }

    private static class PerformanceStage {

         private final HashMap<HorizontalPosition, Float> coordinates = new HashMap<>();

         private void buildCoordinates() {
             final float eighth = Gdx.graphics.getWidth() * .125f;
             coordinates.clear();
             coordinates.put(HorizontalPosition.FAR_LEFT, eighth);
         }

    }

    public static class CharacterPortrait extends WyrActor {
        private final WYRM.Character  character;
        private Wyr.Expression        expression    = Expression.DETERMINED;
        private HorizontalPosition    position      = HorizontalPosition.LEFT;
        private boolean               facingLeft    = false;
        private String                preferredName;
        private String                line;

        public CharacterPortrait(WYRM.Character character) {
            super(ActorType.UI);
            this.character = character;
        }

        public void expression(Expression expression) {
            this.expression = expression;
        }
        public void position(HorizontalPosition position) {
            this.position = position;
        }
        public void faceLeft(boolean faceLeft) {
            if(facingLeft == faceLeft) return;
            this.facingLeft = faceLeft;
//            imageDrawable.setScaleX(faceLeft ? -1 : 1);
        }
        public void preferredName(String name) {
            this.preferredName = name;
        }
        public void line(String line) {
            this.line = line;
        }

        public WYRM.Character getCharacter() {
            return character;
        }
        public boolean isFacingLeft() {
            return facingLeft;
        }
        public Expression getExpression() {
            return expression;
        }
        public HorizontalPosition getPosition() {
            return position;
        }
        public String getPreferredName() {
            return preferredName;
        }
        public String getLine() {
            return line;
        }
    }

}
