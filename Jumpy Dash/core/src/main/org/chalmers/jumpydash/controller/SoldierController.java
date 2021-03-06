package org.chalmers.jumpydash.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.chalmers.jumpydash.model.Soldier;
import org.chalmers.jumpydash.physics.BodyType;
import org.chalmers.jumpydash.physics.IBox2D;
import org.chalmers.jumpydash.view.JDView;
import org.chalmers.jumpydash.view.SoldierView;
import org.chalmers.jumpydash.physics.Box2D;

import java.util.Timer;
import java.util.TimerTask;

public class SoldierController extends JDController {

    private Soldier soldier;
    private JDView soldierView;

    private Timer time;

    public SoldierController(IBox2D box2D, int x, int y, int mapHeight) {
        soldier = new Soldier();
        soldier.setJDBody(box2D.newBody(x, y, mapHeight, BodyType.DYNAMIC, false,false));
        soldier.getJDBody().setUserData(soldier);
        soldierView = new SoldierView();

        //Random randomGenerator = new Random();
        //long random = ((long) randomGenerator.nextDouble()*(10000-1000));
        time = new Timer();
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (soldier.getJDBody().isAwake()) {
                    soldier.jump();
                }
            }
        },0,3000);
    }

    @Override
    public void act(float Delta){
        if (soldier.getJDBody().isAwake()) {
            soldier.move();
        }
        if (!soldier.getJDBody().isActive()) {
            this.remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
       soldierView.render(batch, soldier.getPosition().x * Box2D.PIXELS_TO_METERS, soldier.getPosition().y * Box2D.PIXELS_TO_METERS);

    }

    public Soldier getSoldier(){
        return soldier;
    }

    @Override
    public void dispose() {
        soldierView.dispose();
    }
}
