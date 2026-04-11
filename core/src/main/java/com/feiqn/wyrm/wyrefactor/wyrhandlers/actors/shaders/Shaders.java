package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

public final class Shaders {

    private Shaders() {}

    // I don't understand this shit. Robot do it.

    static public ShaderProgram enemyStandardShader() {
        final ShaderProgram returnValue = new ShaderProgram(
            Gdx.files.internal("shaders/basic.vert"),
            Gdx.files.internal("shaders/red.frag")
        );
        if(!returnValue.isCompiled()) throw new GdxRuntimeException("Shader compile error: " + returnValue.getLog());

        returnValue.setUniformf("u_tolerance", 0.2f);

        return returnValue;
    }

    static public ShaderProgram enemyDimShader() {
        final ShaderProgram returnValue = new ShaderProgram(
            Gdx.files.internal("shaders/basic.vert"),
            Gdx.files.internal("shaders/red_dim.frag")
        );
        if(!returnValue.isCompiled()) throw new GdxRuntimeException("Shader compile error: " + returnValue.getLog());

        final float dimAmount = 0.5f; // for example, 50% darker

        returnValue.setUniformf("u_tolerance", 0.2f);
        returnValue.setUniformf("u_dimAmount", dimAmount);
        return returnValue;
    }

    static public ShaderProgram enemyHighlightShader() {
        return null;
    }


}
