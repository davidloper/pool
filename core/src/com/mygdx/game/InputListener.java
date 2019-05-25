package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.mygdx.game.Pool;
import com.mygdx.game.bodies.Stick;

import utils.MyMath;

public class InputListener implements InputProcessor {

    private Vector3 touchLocation;
    private Body draggedBody;
    private Pool game;
    private Stick stick;

    public InputListener(Pool game){
        this.game = game;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }



    public QueryCallback queryCallback = new QueryCallback(){
        @Override
        public boolean reportFixture(Fixture fixture) {
            if(!fixture.testPoint(touchLocation.x,touchLocation.y))return false;
            draggedBody = fixture.getBody();
            stick = new Stick(game,draggedBody.getPosition(),new Vector2(touchLocation.x,touchLocation.y));
            return false;
        }
    };

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchLocation = new Vector3(screenX,screenY,0);
        game.cam.unproject(touchLocation);
        game.world.QueryAABB(queryCallback,touchLocation.x,touchLocation.y,touchLocation.x,touchLocation.y);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(draggedBody == null) return false;
        if(stick != null){
            touchLocation = new Vector3(screenX,screenY,0);
            game.cam.unproject(touchLocation);
            stick.transform(new Vector2(touchLocation.x,touchLocation.y));
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(draggedBody == null) return false;

        //get angle
        Vector2 bodyLocation =  draggedBody.getPosition();
        touchLocation = new Vector3(screenX,screenY,0);
        game.cam.unproject(touchLocation);

        double radian = Math.toRadians(MyMath.angle(bodyLocation,new Vector2(touchLocation.x,touchLocation.y)));
//        double theta = 180 / Math.PI * Math.atan2(touchLocation.y - bodyLocation.y, touchLocation.x - bodyLocation.x);
//        if(theta >= 0)
//            theta -= 180;
//        else
//            theta += 180;

        //get maginute
        float magnitude = 0;
        float maxMagnitude = 10;
//        System.out.println(touchLocation);
//        System.out.println(bodyLocation);
        double distance = Math.sqrt( Math.pow((touchLocation.x - bodyLocation.x),2) + Math.pow((touchLocation.y - bodyLocation.y),2));
        magnitude = (float)(distance > maxMagnitude ? maxMagnitude : distance);
        //get impulse
        Vector2  impulse = new Vector2((float)(Math.cos(radian) * magnitude),(float) (Math.sin(radian) * magnitude));
        draggedBody.applyLinearImpulse(impulse,draggedBody.getWorldCenter(),true);

        draggedBody = null;

        if(!stick.isDestroyed())
            stick.destroy();

        return false;
    }
}
