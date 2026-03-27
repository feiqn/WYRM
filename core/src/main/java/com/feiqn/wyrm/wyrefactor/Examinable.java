package com.feiqn.wyrm.wyrefactor;

public interface Examinable {

    default String getName() {
        return "What is this?";
    }

    default String getDescription() {
        return "What could it be?";
    }
}
