package com.feiqn.wyrm.wyrefactor.helpers.interfaces;

public interface Examinable {

    default String getExamine() {
        return "What could it be?";
    }
}
