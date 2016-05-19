package org.chalmers.jumpydash.model;

public class Cannon extends Enemy {

    private long previousFireTime = 0;

    public Cannon() {
        super(1,1);
    }

    public boolean allowedToFire(){
        long fireCooldown = 5000;
        if (System.currentTimeMillis() - previousFireTime >= fireCooldown) {
            previousFireTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
