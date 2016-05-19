package org.chalmers.jumpydash.physics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.*;

public class Box2D implements IBox2D {

    private OrthographicCamera camera;
    private World world;
    private final List<Body> bodiesToBeDestroyed;

    public static final int TILE_SIZE = 32;
    private static final float GRAVITY = -10f;
    public static final float PIXELS_TO_METERS = 100f;
    private static final float TIME_STEP = 1 / 60f;
    private static final int VEL_ITERATIONS = 8;
    private static final int POS_ITERATIONS = 3;
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 736;

    public Box2D() {
        world = new World(new Vector2(0, GRAVITY), true); //Create a world object with a gravity vector

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        bodiesToBeDestroyed = Collections.synchronizedList(new ArrayList<Body>());
    }

    public synchronized List<Body> getBodiesToBeDestroyed() {
        return this.bodiesToBeDestroyed;
    }

    public World getWorld() {
        return this.world;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public JDBody newBody(float x, float y, int mapHeight, String type, boolean ghost,boolean sensor) {
        BodyDef bodyDef = new BodyDef();

        if (type.equalsIgnoreCase("kinematic")) {
            bodyDef.type = BodyDef.BodyType.KinematicBody;
        } else if (type.equalsIgnoreCase("dynamic")) {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        } else if (type.equalsIgnoreCase("static")) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        }

        bodyDef.position.set((x * TILE_SIZE) / PIXELS_TO_METERS, ((mapHeight - 1 - y) * TILE_SIZE) / PIXELS_TO_METERS);
        JDBody jdBody = new JDBody();
        jdBody.body = world.createBody(bodyDef);
        if (sensor) {
          setSensor(jdBody);
        } else {
            if (ghost) {
                setGhost(jdBody);
            } else {
                Vector2 vCenter = new Vector2((TILE_SIZE / 2) / PIXELS_TO_METERS, (TILE_SIZE / 2) / PIXELS_TO_METERS);

                // Create a polygon and apply it to a fixture
                PolygonShape polygon = new PolygonShape();
                polygon.setAsBox((TILE_SIZE / 2) / PIXELS_TO_METERS, (TILE_SIZE / 2) / PIXELS_TO_METERS, vCenter, 0);
                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = polygon;
                fixtureDef.friction = 0;

                // Attach fixture to the body
                jdBody.body.createFixture(fixtureDef);
            }
        }
        return jdBody;
    }

    public void setGhost(JDBody jdBody) {
        //Ghost vertices
        Vector2 v1 = new Vector2(0, TILE_SIZE / PIXELS_TO_METERS);
        Vector2 v2 = new Vector2(TILE_SIZE / PIXELS_TO_METERS, TILE_SIZE / PIXELS_TO_METERS);
        Vector2 v0 = new Vector2(0, 0);
        Vector2 v3 = new Vector2(TILE_SIZE / PIXELS_TO_METERS, 0);

        // Create a EdgeShape and apply it to a fixture
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(v1, v2);
        edgeShape.setVertex0(v0);
        edgeShape.setVertex3(v3);
        edgeShape.setHasVertex0(true);
        edgeShape.setHasVertex3(true);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edgeShape;

        // Attach fixture to the body
        jdBody.body.createFixture(fixtureDef);
    }

    public void setSensor(JDBody jdBody) {
        Vector2 vCenter = new Vector2((TILE_SIZE / 2) / PIXELS_TO_METERS, (TILE_SIZE / 2) / PIXELS_TO_METERS);

        // Create a polygon and apply it to a fixture
        PolygonShape polygon = new PolygonShape();
        polygon.setAsBox((TILE_SIZE / 2) / PIXELS_TO_METERS, (TILE_SIZE / 2) / PIXELS_TO_METERS, vCenter, 0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygon;
        fixtureDef.isSensor = true;
        fixtureDef.friction = 0;

        // Attach fixture to the body
        jdBody.body.createFixture(fixtureDef);
    }

    public JDBody newBullet(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x + ((TILE_SIZE + 8) / PIXELS_TO_METERS),  y + (((TILE_SIZE / 2) / 2) / PIXELS_TO_METERS));
        JDBody jdBody = new JDBody();
        jdBody.body = world.createBody(bodyDef);

        Vector2 vCenter = new Vector2((TILE_SIZE / 2) / PIXELS_TO_METERS, (TILE_SIZE / 2) / PIXELS_TO_METERS);

        // Create a polygon and apply it to a fixture
        PolygonShape polygon = new PolygonShape();
        polygon.setAsBox((TILE_SIZE / 2) / PIXELS_TO_METERS, (TILE_SIZE / 2) / PIXELS_TO_METERS, vCenter, 0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygon;
        jdBody.body.setBullet(true);

        // Attach fixture to the body
        jdBody.body.createFixture(fixtureDef);

        return jdBody;
    }

    public void update() {
        world.step(TIME_STEP, VEL_ITERATIONS, POS_ITERATIONS);
        world.clearForces();

        // Destroy bodies who are marked for destruction
        synchronized (bodiesToBeDestroyed) {
            Set<Body> set = new HashSet<Body>(bodiesToBeDestroyed);
            set.addAll(bodiesToBeDestroyed);
            if(set.size() < bodiesToBeDestroyed.size()) {
            System.out.println("Duplicates!");
            }
            for (Body b : bodiesToBeDestroyed) {
                System.out.println(b.getUserData());
                Array<JointEdge> list = b.getJointList();

                while (list.size > 0) {
                    world.destroyJoint(list.get(0).joint);
                }
                world.destroyBody(b);
                b = null;
                //b.setActive(false);
                //b.setUserData(null);
            }
        }

        bodiesToBeDestroyed.clear();
        camera.update();
    }
}
