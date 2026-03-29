package com.feiqn.wyrm.wyrefactor.helpers;

import com.feiqn.wyrm.wyrefactor.WyrType;

public interface Wyr {

    // Commence operation: Bird On a Wyr!

    default WyrType getWyrType() {
        return WyrType.AGNOSTIC;
    }
}
