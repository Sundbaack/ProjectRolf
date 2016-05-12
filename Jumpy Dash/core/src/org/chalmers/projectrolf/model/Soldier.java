package org.chalmers.projectrolf.model;

import org.chalmers.projectrolf.physics.IJDBody;
import javax.vecmath.Vector2f;

public class Soldier extends Enemy {

    private IJDBody jdBody;
    private boolean directionFlag;

    public Soldier(IJDBody jdBody) {
        this.jdBody = jdBody;
        this.jdBody.setUserData(this);
        directionFlag=true;
    }

    public void setDirectionFlag(){
        directionFlag = !directionFlag;
    }

    public boolean getDirectionFlag(){
        return directionFlag;
    }

    public void move() {
        // Checks in what direction the soldier should move
        if(getDirectionFlag()){
            jdBody.applyForceToCenter(jdBody.toVector2(new Vector2f(50f,0)), true);
        }
        else{
            jdBody.applyForceToCenter(jdBody.toVector2(new Vector2f(-50f,0)), true);
        }
    }

    public Vector2f getPosition(){
        return jdBody.toVector2f(jdBody.getPosition());
    }

}
