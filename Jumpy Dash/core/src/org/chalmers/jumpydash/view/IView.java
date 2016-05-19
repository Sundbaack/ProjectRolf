package org.chalmers.jumpydash.view;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface IView {

    void render(Batch batch, float x, float y);

    void dispose();
}
