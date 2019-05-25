package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.InputListener;
import com.mygdx.game.bodies.Ball;
import com.mygdx.game.bodies.Block;

public class Pool extends ApplicationAdapter {

	public static final float PPM = 50;
	public static final float TILE_SCALE = 3;

	public TiledMap map;
	private OrthogonalTiledMapRenderer tmr;

	public OrthographicCamera cam;

	public World world;
	private Box2DDebugRenderer b2dr;

	private Ball ball;

	@Override
	public void create () {

		//cam
		cam = new OrthographicCamera(Gdx.graphics.getWidth() / PPM,Gdx.graphics.getHeight() /PPM);
		cam.position.set(Gdx.graphics.getWidth() / 2 /PPM,Gdx.graphics.getHeight() / 2/ PPM,0);
		cam.update();

		//box2d
		world = new World(new Vector2(0,0),true);
		b2dr = new Box2DDebugRenderer();

		//tileMap
		map = new TmxMapLoader().load("pool.tmx");
		tmr = new OrthogonalTiledMapRenderer(map,TILE_SCALE / PPM);
		tmr.setView(cam);

		//bodies
		ball = new Ball(this,cam.viewportWidth / 4,cam.viewportHeight / 2);
		new Block(this);
		new Ball(this,cam.viewportWidth /2,cam.viewportHeight / 2);

		//input processor
		Gdx.input.setInputProcessor( new InputListener(this));

	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tmr.render();
		b2dr.render(world,cam.combined);
		world.step(1/60f,5,2);
	}


	@Override
	public void dispose () {

	}

}
