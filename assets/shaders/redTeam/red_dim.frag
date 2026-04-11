#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform float u_tolerance;     // for blue→red conversion
uniform float u_dimAmount;     // 0.0 = normal, 1.0 = extra dark

void main()
{
    vec4 texColor = texture2D(u_texture, v_texCoords) * v_color;

    // Step 1: turn blue pixels into red
    if (texColor.b > texColor.r + u_tolerance && texColor.b > texColor.g + u_tolerance)
    {
        texColor = vec4(1.0, 0.0, 0.0, texColor.a);
    }

    // Step 2: make the result (original + new red) darker
    texColor.rgb *= (1.0 - u_dimAmount);   // 0.0 = no dim; 1.0 = fully black

    gl_FragColor = texColor;
}
