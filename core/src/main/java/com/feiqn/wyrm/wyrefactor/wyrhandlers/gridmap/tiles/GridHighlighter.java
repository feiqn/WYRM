package com.feiqn.wyrm.wyrefactor.wyrhandlers.gridmap.tiles;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;

public class GridHighlighter extends Image {

    public GridHighlighter

    protected final WYRMGame root;

    public GridHighlighter(WYRMGame root) {
        super(root.assetHandler.solidBlueTexture);
        this.root = root;

        this.setSize(1,1);
    }
}
