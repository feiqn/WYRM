package com.feiqn.wyrm.wyrefactor;

public interface Wyr {

    default WyrType wyrType() {
        return WyrType.AGNOSTIC;
    }
}
