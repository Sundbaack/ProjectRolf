package org.chalmers.jumpydash.model;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Johannes on 2016-05-28.
 */
public class PlayerTest {

    private static Player player;

    @BeforeClass
    public static void beforeClass(){
        //IBox2D box2D = new Box2D();


        player= new Player();

       /* player.setJDBody(box2D.newBody(0,0,50,"dynamic", false,false));
        player.setJDBody(new JDBody());*/
    }

    @Test
    public void jump() throws Exception {
        /*Vector2f positionBefore = player.getPosition();
        player.jump();
        player.move();
        Vector2f positionAfter = player.getPosition();
        assertNotEquals(positionAfter,positionBefore);*/
    }

    @Test
    public void checkCollision() throws Exception {

    }

    @Test
    public void getInvinciblePickUpTime() throws Exception {
        assertEquals(player.getInvinciblePickUpTime(),0);
        player.setInvincible();
    }

    @Test
    public void isInvincible() throws Exception {
        assertFalse(player.isInvincible());
        player.setInvincible();
        assertTrue(player.isInvincible());
    }

    @Test
    public void allowedToFire() throws Exception {
        assertEquals(player.allowedToFire(),true);
        assertEquals(player.allowedToFire(),false);

        player.setPreviousFireTime(0);
        assertEquals(player.allowedToFire(),true);
    }

    @Test
    public void move() throws Exception {

    }

    @Test
    public void applyTrampolineImpulse() throws Exception {

    }

    @Test
    public void applySoldierImpulse() throws Exception {

    }

    @Test
    public void getState() throws Exception {

    }

    @Test
    public void isDead() throws Exception {
        assertFalse(player.isDead());
        player.setDamage(3);
        assertTrue(player.isDead());
    }

    @Test
    public void playerSpeedUp() throws Exception {

    }

    @Test
    public void setImpulse() throws Exception {

    }

    @Test
    public void getPoints() throws Exception {

    }

    @Test
    public void setPoints() throws Exception {

    }

    @Test
    public void getHealth() throws Exception {

    }

    @Test
    public void setDamage() throws Exception {

    }

    @Test
    public void setPreviousFireTime() throws Exception {

    }

    @Test
    public void setSound() throws Exception {

    }

    @Test
    public void dispose() throws Exception {

    }

}