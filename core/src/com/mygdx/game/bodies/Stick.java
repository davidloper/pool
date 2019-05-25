package com.mygdx.game.bodies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.Pool;

import utils.MyMath;

public class Stick {

    private final float MIN_POINT_DISTANCE = 1.5f;
    private Pool game;
    public Body point;
    public Body body;


    private boolean destroyed;

    public Stick(Pool game, Vector2 ballLocation,Vector2 clickLocation){
        this.game = game;
        destroyed = false;
        createPoint(ballLocation);
        createStick(clickLocation);
    }

    public void createPoint(Vector2 ballPosition){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(ballPosition);

        point = game.world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(0.1f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.isSensor = true;
        point.createFixture(fixtureDef);
        circle.dispose();
    }

    public void createStick(Vector2 clickPosition){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        Vector2 v = new Vector2(clickPosition.x - point.getPosition().x,clickPosition.y - point.getPosition().y);
        double distance = MyMath.distance(clickPosition,point.getPosition());
        float scale = (float) (MIN_POINT_DISTANCE / distance);

        bodyDef.position.set(v.scl(scale).add(clickPosition));
        body = game.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        double angle = MyMath.angle(v,point.getPosition());
        shape.setAsBox(1f,0.05f,new Vector2(0,0),0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
        shape.dispose();

        body.setTransform(body.getPosition(),(float)Math.toRadians(angle));

    }

    public void transform(Vector2 mousePosition){

        Vector2 v = new Vector2(mousePosition.x - point.getPosition().x,mousePosition.y - point.getPosition().y);
        double distance = MyMath.distance(mousePosition,point.getPosition());
        float scale = 0;

        if(distance < MIN_POINT_DISTANCE)
            scale = (float) (MIN_POINT_DISTANCE / distance);

        v.scl(scale).add(mousePosition);
        double angle = MyMath.angle(v,point.getPosition());

        body.setTransform(v,(float) Math.toRadians(angle));
    }

    public void destroy(){
        game.world.destroyBody(point);
        point = null;
        game.world.destroyBody(body);
        body = null;
        destroyed = true;
    }


    public boolean isDestroyed() {
        return destroyed;
    }
}
