#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;

uniform float u_tolerance ;

void main() {

    // don't get excited im still just copy pasting

    vec4 texColor = texture2D(u_texture, v_texCoords) * v_color;

    if(texColor.b > texColor.g + u_tolerance) {
        texColor = vec4(0.2, .45, 0.2, texColor.a);
    }

    gl_FragColor = texColor;

}
