package org.chalmers.jumpydash.controller.collision;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import org.chalmers.jumpydash.controller.PlayerController;
import org.chalmers.jumpydash.model.*;

public class PlayerCollisionListener extends JDCollision {

    private boolean playerA;
    private boolean coinB;
    private boolean playerB;
    private boolean coinA;
    private boolean platformA;
    private boolean platformB;
    private boolean soldierA;
    private boolean soldierB;
    private boolean trampolineA;
    private boolean trampolineB;
    private boolean spikeA;
    private boolean spikeB;
    private boolean speedUpA;
    private boolean speedUpB;
    private boolean sensorA;
    private boolean sensorB;
    private Sound powerUpSound;

    // Determine type of the two colliding bodies
    private void checkInstance(Body a, Body b) {
        playerA = a.getUserData() instanceof Player;
        coinB = b.getUserData() instanceof Coin;
        playerB = b.getUserData() instanceof Player;
        coinA = a.getUserData() instanceof Coin;
        platformA = a.getUserData() instanceof Platform;
        platformB = b.getUserData() instanceof Platform;
        soldierA = a.getUserData() instanceof Soldier;
        soldierB = b.getUserData() instanceof Soldier;
        trampolineA = a.getUserData() instanceof Trampoline;
        trampolineB = b.getUserData() instanceof Trampoline;
        spikeA = a.getUserData() instanceof Spike;
        spikeB = b.getUserData() instanceof Spike;
        speedUpA = a.getUserData() instanceof SpeedUp;
        speedUpB = b.getUserData() instanceof SpeedUp;
        sensorA = a.getUserData() instanceof  Sensor;
        sensorB = b.getUserData() instanceof  Sensor;
    }

    // Determine who is colliding with who
    private void checkCollision(Body a, Body b) {
        if ((playerA && platformB) ||
                (platformA && playerB)) {
            if (!PlayerController.getPlayer().getJumpState()) {
                PlayerController.getPlayer().setJumpState();
            }
        }
        //Check collision between player and coin
        if ((playerA && coinB)) {
            PlayerController.getPlayer().setPoints(Coin.getValue());
            b.setUserData(null);
        } else if ((coinA && playerB)) {
            PlayerController.getPlayer().setPoints(Coin.getValue());
            a.setUserData(null);
        }

        //Check collision between player and soldier
        if ((playerA && soldierB) || (soldierA && playerB)) {
            PlayerController.getPlayer().setDamage(1);
            PlayerController.getPlayer().applySoldierImpulse();
        }
        //Check collision between player and trampoline
        if (playerA && trampolineB || trampolineA && playerB) {
            if (!PlayerController.getPlayer().getJumpState()) {
                PlayerController.getPlayer().setJumpState();
            }
            PlayerController.getPlayer().applyTrampolineImpulse();
        }

        //Check collision between player and spike
        if (playerA && spikeB || spikeA && playerB) {
            PlayerController.getPlayer().setDamage(PlayerController.getPlayer().getHealth());
        }


        if((playerA && sensorB) || (sensorA && playerB)){
            MovingPlatform.movePlatforms = !MovingPlatform.movePlatforms;
        }
        //Check collision between player and SpeedUp
        if ((playerA && speedUpB)) {
            PlayerController.getPlayer().playerSpeedUp();
            b.setUserData(null);
            powerUpSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/powerup.wav"));
            powerUpSound.play(1);
        } else if ((speedUpA && playerB)) {
            PlayerController.getPlayer().playerSpeedUp();
            a.setUserData(null);
            powerUpSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/powerup.wav"));
            powerUpSound.play(1);
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        checkInstance(a,b);
        checkCollision(a,b);
    }
}
