package com.feiqn.wyrm.wyrefactor.helpers.interfaces;

public interface Examinable {

    default String getName() {
        return "What is this?";
    }

    default String getExamine() {
        return "What could it be?";
    }
}
