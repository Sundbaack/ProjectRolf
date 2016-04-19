package com.jumpydash;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player {

    private Body body;
    private FixtureDef fixtureDef;
    private float impulse;

    public Player(Body body) {

        this.body = body;

        // Create a fixture definition to apply our shape to
        fixtureDef = new FixtureDef();
        fixtureDef.shape = new PolygonShape();

        // Create our fixture and attach it to the body
        this.body.createFixture(fixtureDef);

        this.impulse = this.body.getMass() * 200;
    }

    public void jump() {
        getBody().applyLinearImpulse(new Vector2(0, getImpulse()), getBody().getWorldCenter(), true);
    }

    public void move() {
        getBody().applyLinearImpulse(new Vector2(100f, 0), getBody().getWorldCenter(), true);
    }


    public float getImpulse() {
        return this.impulse;
    }

    public Body getBody() {
        return this.body;
    }

    public Vector2 getPosition() {
        return this.body.getPosition();
    }
}

