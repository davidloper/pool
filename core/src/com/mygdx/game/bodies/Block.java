package com.mygdx.game.bodies;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.Pool;

public class Block {

    private Pool game;

    public Block(Pool game){
        MapObjects objects = game.map.getLayers().get("object").getObjects();
        for(MapObject object : objects){
            Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
            PolygonShape polygonShape = new PolygonShape();
            Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) * Pool.TILE_SCALE / Pool.PPM,(rectangle.y + rectangle.height * 0.5f) * Pool.TILE_SCALE / Pool.PPM);
            polygonShape.setAsBox(rectangle.width * 0.5f * Pool.TILE_SCALE / Pool.PPM, rectangle.height * 0.5f * Pool.TILE_SCALE / Pool.PPM,size,0.0f);

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            Body body = game.world.createBody(bodyDef);
            body.createFixture(polygonShape,1);
        }
    }
}
