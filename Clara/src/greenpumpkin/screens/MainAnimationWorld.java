package greenpumpkin.screens;

import greenpumpkin.artemis.TreasuremonWorld;
import greenpumpkin.artemis.entities.LightFactory;
import greenpumpkin.artemis.systems.*;
import greenpumpkin.game.*;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

////////////////////////////////////////////////////////////
//This is the Artemis test screen. It is useful for/////////
//learning how Artemis works.///////////////////////////////
////////////////////////////////////////////////////////////

public class MainAnimationWorld implements Screen {
	private Stage stage = new Stage();
	private TreasuremonWorld world;
	
	@Override
	public void show() {
		world = new TreasuremonWorld();

		TreasuremonWorld.init();
		
		world.setManager(new GroupManager());
		world.setSystem(new ReadScriptS());
		world.setSystem(new BackgroundAndCharacterS());
		world.setSystem(new LightCycleS());
		world.setSystem(new BatchRendererS());
		world.setSystem(new LightS());
		world.initialize();

		world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*4, 20f, 750f, 7.7f, 1.875f*4f));
		world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*4, 640f, 1000f, 7.7f, 1.875f*4f));
		world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*4, 1260f, 750f, 7.7f, 1.875f*4f));
		world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*3, 20f, -100f, 7.7f, 1.875f*4f));
		world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*3, 640f, -200f, 7.7f, 1.875f*4f));
		world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*3, 1260f, -100f, 7.7f, 1.875f*4f));
		
	}

	@Override
	public void render(float delta) {
		if(delta<0.2f){
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			TreasuremonWorld.camera.update();
			world.setDelta(delta);
			world.process();
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setCamera(new VirtualResolution(Treasuremon.WIDTH, Treasuremon.HEIGHT));
		 TreasuremonWorld.batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {		
	}

	@Override
	public void resume() {		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
