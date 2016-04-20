package com.jumpydash;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

public class GameController extends ApplicationAdapter {

	private World world;
	private GameView gameView;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private BodyDef playerBodyDef;
	private Body playerBody;
	private Player player;
	private Texture playerTile;
	private BodyDef platformBodyDef;
	private Body platformBody;
	private Texture platformTile;
	private Platform platform;
	private BodyDef coinBodyDef;
	private Body coinBody;
	private BodyDef soldierBodyDef;
	private Body soldierBody;
	private Soldier soldier;
	private Texture coinTile;
	private Coin coin;
	private Texture background;
	public static final float Pixels_To_Meters = 100f;

	@Override
	public void create () {

		platformTile = new Texture(Gdx.files.internal("platform.png"));
		playerTile = new Texture(Gdx.files.internal("player.png"));
		coinTile = new Texture(Gdx.files.internal("coin.png"));
		background = new Texture(Gdx.files.internal("background_1.png"));
		background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);

		gameView = new GameView();
		world = new World(new Vector2(0, -10f), true); //Create a world object with a gravity vector

		// Player body Box2D

		playerBodyDef = new BodyDef();
		playerBodyDef.type = BodyDef.BodyType.DynamicBody;
		playerBodyDef.position.set(200/GameController.Pixels_To_Meters, 400/GameController.Pixels_To_Meters);
		playerBody = world.createBody(playerBodyDef);

		player = new Player(playerBody);

		// Platform body for Box2D

		platformBodyDef = new BodyDef();
		platformBodyDef.type = BodyDef.BodyType.StaticBody;
		platformBodyDef.position.set(200/GameController.Pixels_To_Meters, 100/GameController.Pixels_To_Meters);
		platformBody = world.createBody(platformBodyDef);

		platform = new Platform(platformBody);

		// Coin body for Box2D

		coinBodyDef = new BodyDef();
		coinBodyDef.type = BodyDef.BodyType.StaticBody;
		coinBodyDef.position.set(20/GameController.Pixels_To_Meters, 20/GameController.Pixels_To_Meters);
		coinBody = world.createBody(coinBodyDef);

		coin = new Coin(coinBody, 20);

		// Soldier body for Box2D
		soldierBodyDef = new BodyDef();
		soldierBodyDef.type = BodyDef.BodyType.DynamicBody;
		soldierBodyDef.position.set(25/GameController.Pixels_To_Meters,25/GameController.Pixels_To_Meters);
		soldierBody = world.createBody(soldierBodyDef);

		soldier = new Soldier(soldierBody);


		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				// Check to see if the collision is between the the player and a platform
				if ((contact.getFixtureA().getBody() == player.getBody() &&
						contact.getFixtureB().getBody() == platform.getBody())
						||
						(contact.getFixtureA().getBody() == platform.getBody() &&
								contact.getFixtureB().getBody() == player.getBody())) {
						player.setJumpState();
				}
				/*if ((contact.getFixtureA().getBody() == soldier.getBody() &&
						contact.getFixtureB().getBody() == enemyTurnTile.getBody())
						||
						(contact.getFixtureA().getBody() == enemyTurnTile.getBody() &&
								contact.getFixtureB().getBody() == soldier.getBody())) {
					soldier.setCollision();
				}*/
			}

			@Override
			public void endContact(Contact contact) {

			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {

			}
		});
	}

	public void handleInput() {

		// Jumping
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && player.getJumpState()) {
			player.setJumpState();
			player.jump();
		}
	}

	@Override
	public void render () {

		handleInput();

		//soldier.move();

		camera.position.set(player.getPosition().x * GameController.Pixels_To_Meters + (1280 / 2), 720 / 2, 0);

		// Enable the camera to follow the player
		/*if(player.getPosition().x > 500 / GameController.Pixels_To_Meters) {
			camera.position.set(player.getPosition().x * GameController.Pixels_To_Meters - 500 / GameController.Pixels_To_Meters, 720 / 2, 0);

			/*float lerp = 0.1f;
			Vector3 position = camera.position;
			position.x += ((player.getPosition().x * GameController.Pixels_To_Meters) - position.x) * lerp;

		}*/


			world.step(1 / 60f, 6, 3); // Step the physics simulation forward at a rate of 60hz

		player.move();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		camera.update();

		batch.begin();

		// Draw background
		batch.draw(background,0,0,0,0,10000,720);

		// Draw objects
		batch.draw(playerTile, player.getPosition().x*GameController.Pixels_To_Meters, player.getPosition().y*GameController.Pixels_To_Meters);
		batch.draw(platformTile,platform.getPosition().x*GameController.Pixels_To_Meters,platform.getPosition().y*GameController.Pixels_To_Meters);
		batch.draw(coinTile,coin.getPosition().x*GameController.Pixels_To_Meters,platform.getPosition().y*GameController.Pixels_To_Meters);

		batch.end();
	}

	@Override
	public void dispose() {
		coinTile.dispose();
		batch.dispose();
		playerTile.dispose();
		platformTile.dispose();
	}
}
