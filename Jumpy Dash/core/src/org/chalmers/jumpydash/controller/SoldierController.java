package org.chalmers.jumpydash.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.chalmers.jumpydash.physics.IBox2D;
import org.chalmers.jumpydash.model.Soldier;
import org.chalmers.jumpydash.view.SoldierView;
import static org.chalmers.jumpydash.util.Constants.*;

public class SoldierController extends Actor {

    private Soldier soldier;
    private SoldierView soldierView;
    private IBox2D box2D;

    public SoldierController(IBox2D box2D, int x, int y, int mapHeight) {
        this.box2D = box2D;
        soldier = new Soldier(box2D.newBody(x, y, mapHeight, "dynamic", false));
        soldierView = new SoldierView();
    }

    @Override
    public void act(float Delta){
        soldier.move();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
       soldierView.render(batch, soldier.getPosition().x * PIXELS_TO_METERS, soldier.getPosition().y * PIXELS_TO_METERS);

    }

    public void dispose() {
        soldierView.dispose();
    }
}
