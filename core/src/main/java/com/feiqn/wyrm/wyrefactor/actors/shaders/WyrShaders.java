package com.feiqn.wyrm.wyrefactor.actors.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/** A standard set of shaders for any WyrFrame project.
 */
public final class WyrShaders {

    private WyrShaders() {}

    static public final class Player {
        static public ShaderProgram standard() {
            return null;
        }
        static public ShaderProgram dim() {
            return null;
        }
        static public ShaderProgram highlight() {
            return null;
        }
    }

    static public final class Ally {
        static public ShaderProgram standard() {
            return null;
        }
        static public ShaderProgram dim() {
            return null;
        }
        static public ShaderProgram highlight() {
            return null;
        }
    }

    static public final class Enemy {
        static public ShaderProgram standard() {
            final ShaderProgram returnValue = new ShaderProgram(
                Gdx.files.internal("shaders/redTeam/basic.vert"),
                Gdx.files.internal("shaders/redTeam/red.frag")
            );
            if(!returnValue.isCompiled()) throw new GdxRuntimeException("Shader compile error: " + returnValue.getLog());

            returnValue.setUniformf("u_tolerance", .2f);

            return returnValue;
        }
        static public ShaderProgram dim() {
            final ShaderProgram returnValue = new ShaderProgram(
                Gdx.files.internal("shaders/redTeam/basic.vert"),
                Gdx.files.internal("shaders/redTeam/red_dim.frag")
            );
            if(!returnValue.isCompiled()) throw new GdxRuntimeException("Shader compile error: " + returnValue.getLog());

            final float dimAmount = 0.5f; // for example, 50% darker

            returnValue.setUniformf("u_tolerance", 0.5f);
            returnValue.setUniformf("u_dimAmount", dimAmount);
            return returnValue;
        }
        static public ShaderProgram highlight() {
            return null;
        }
    }

    static public final class Stranger {
        static public ShaderProgram standard() {
            return null;
        }
        static public ShaderProgram dim() {
            return null;
        }
        static public ShaderProgram highlight() {
            return null;
        }
    }


}
