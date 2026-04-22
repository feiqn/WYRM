#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;

// How “pure” must the blue be to convert it?
// 0.0 = only pure blue; 1.0 = almost any blue
uniform float u_tolerance;

void main()
{
    vec4 texColor = texture2D(u_texture, v_texCoords) * v_color;

    // If this pixel is mostly blue, turn it red; otherwise keep it.
    // Check that blue is much stronger than red and green.
    if (texColor.b > texColor.r + u_tolerance && texColor.b > texColor.g + u_tolerance)
    {
        texColor = vec4(0.5, 0.0, 0.0, texColor.a);
    }

    gl_FragColor = texColor;
}

// robots will replace us
