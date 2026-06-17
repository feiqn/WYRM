package com.feiqn.wyrm.wyrefactor.helpers;

public class State<Verb extends Enum<?>, Adjective extends Enum<?>> {

    private Verb verb;
    private Adjective adjective;

    public State(Verb verb, Adjective adjective) {
        this.verb = verb;
        this.adjective = adjective;
    }

    public void set(Verb verb, Adjective adjective) {
        this.verb = verb;
        this.adjective = adjective;
    }

    public Verb verb() {
        return verb;
    }

    public Adjective adjective() {
        return adjective;
    }
}
