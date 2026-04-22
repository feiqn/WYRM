attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans; // this game is going on the woke list for sure

varying vec2 v_texCoords;
varying vec4 v_color;

void main()
{
    v_texCoords = a_texCoord0;
    v_color = a_color;
    gl_Position = u_projTrans * a_position;
}

// i let an llm do this idk wtf is happening
