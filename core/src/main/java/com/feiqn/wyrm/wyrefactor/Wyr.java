package com.feiqn.wyrm.wyrefactor;

public interface Wyr {

    default WyrType getWyrType() {
        return WyrType.AGNOSTIC;
    }
}
