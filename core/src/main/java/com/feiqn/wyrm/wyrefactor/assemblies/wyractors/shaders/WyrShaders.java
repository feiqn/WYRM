package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/** A standard set of basic shaders for any WyrFrame project.
 */
public final class WyrShaders {

    private WyrShaders() {}

    static public final class Player {
        static public ShaderProgram standard() {
            return null;
        }
        static public ShaderProgram dim() {
            final ShaderProgram returnValue = new ShaderProgram(
                Gdx.files.internal("shaders/basic.vert"),
                Gdx.files.internal("shaders/blueTeam/player_dim.frag")
            );
            if(!returnValue.isCompiled()) throw new GdxRuntimeException("Shader compile error: " + returnValue.getLog());
            returnValue.setUniformf("u_dimAmount", 0.5f);
            return returnValue;
        }
        static public ShaderProgram highlight() {
            final ShaderProgram shader = new ShaderProgram(
                Gdx.files.internal("shaders/basic.vert"),
                Gdx.files.internal("shaders/blueTeam/player_highlight.frag")
            );
            if(!shader.isCompiled()) throw new GdxRuntimeException("Shader compile error: " + shader.getLog());
//            shader.setUniformf("u_dimAmount", 0.5f);
            return shader;
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
                Gdx.files.internal("shaders/basic.vert"),
                Gdx.files.internal("shaders/redTeam/red.frag")
            );
            if(!returnValue.isCompiled()) throw new GdxRuntimeException("Shader compile error: " + returnValue.getLog());
            returnValue.setUniformf("u_tolerance", 0.1f);
            return returnValue;
        }
        static public ShaderProgram dim() {
            final ShaderProgram returnValue = new ShaderProgram(
                Gdx.files.internal("shaders/basic.vert"),
                Gdx.files.internal("shaders/redTeam/red_dim.frag")
            );
            if(!returnValue.isCompiled()) throw new GdxRuntimeException("Shader compile error: " + returnValue.getLog());
            returnValue.setUniformf("u_tolerance", 0.1f);
            returnValue.setUniformf("u_dimAmount", 0.5f);
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
