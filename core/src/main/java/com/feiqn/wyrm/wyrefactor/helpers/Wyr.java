package com.feiqn.wyrm.wyrefactor.helpers;

public interface Wyr {

    // Commence operation: Bird On a Wyr!

    default WyrType getWyrType() {
        return WyrType.AGNOSTIC;
    }
}
