#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform float u_tolerance;
uniform float u_dimAmount;

void main()
{
    vec4 texColor = texture2D(u_texture, v_texCoords) * v_color;

    if (texColor.b > texColor.r + u_tolerance && texColor.b > texColor.g + u_tolerance)
    {
        texColor = vec4(.55, 0.0, 0.0, texColor.a);
    }

    texColor.rgb *= (1.5 - u_dimAmount);

    gl_FragColor = texColor;
}
