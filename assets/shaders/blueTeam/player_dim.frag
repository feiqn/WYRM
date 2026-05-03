#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform float u_dimAmount;

void main()
{
    vec4 texColor = texture2D(u_texture, v_texCoords) * v_color;
    texColor.rgb *= (0.5 - u_dimAmount);
    gl_FragColor = texColor;
}

// okay i mostly coppied this from the one the clunker wrote
// but i kind of understand it a little better now at least
// some parts idk what all the sh*t up top is
