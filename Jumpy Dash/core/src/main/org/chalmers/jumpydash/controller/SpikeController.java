package org.chalmers.jumpydash.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.chalmers.jumpydash.model.Spike;
import org.chalmers.jumpydash.physics.BodyType;
import org.chalmers.jumpydash.physics.IBox2D;
import org.chalmers.jumpydash.view.JDView;
import org.chalmers.jumpydash.view.SpikeView;
import org.chalmers.jumpydash.physics.Box2D;

public class SpikeController extends JDController {

    private JDView spikeView;
    private Spike spike;

    public SpikeController(IBox2D box2D, int x, int y, int mapHeight) {
        spikeView = new SpikeView();
        spike = new Spike();
        spike.setJDBody(box2D.newBody(x, y, mapHeight, BodyType.STATIC, false,false));
        spike.getJDBody().setUserData(spike);
    }

    @Override
    public void act(float Delta) {
        if (!spike.getJDBody().isActive()) {
            this.remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        spikeView.render(batch, spike.getPosition().x * Box2D.PIXELS_TO_METERS, spike.getPosition().y * Box2D.PIXELS_TO_METERS);
    }

    @Override
    public void dispose() {
        spikeView.dispose();
    }

}
