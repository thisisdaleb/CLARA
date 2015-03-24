package greenpumpkin.artemis;

import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import box2dLight.RayHandler;

public class TreasuremonWorld extends World {
	public static final int numRays = 16; //how many rays are emitted for shadow casting
	public static final float lightDistance = 256f; // distance light goes
	public static RayHandler rayHandler; //the main object of light2d, heavily important
	public static OrthographicCamera camera;
	public static SpriteBatch batch;
	public static String characterList = "";
	public static String background = "";
	public static char charChange = '0';
	public static int charChangeInt = 0;
	
	public static void init() {
		initCamera();
		initRayHandler();
		initBatch();	
	}
	
	private static void initCamera() {
		camera = new OrthographicCamera(1280,720);
		camera.position.set(640, 360, 0);
		camera.update(true);
	}
	
	private static void initRayHandler() {
		RayHandler.useDiffuseLight(true);
		rayHandler = new RayHandler(null);
		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.setAmbientLight(0.3f, 0.3f, 0.3f, 1f);
		rayHandler.setCulling(true);
		rayHandler.setBlurNum(1);
		rayHandler.setShadows(true);
	}
	
	private static void initBatch() {
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
	}
	
	public static void flushRayHandler() {
		rayHandler.removeAll();
	}
	
	public static void setCharacters(String chars) {
		characterList = chars;
	}
	
	public static void changeCharacter(char cha, int num){
		charChange=cha;
		charChangeInt=num;
	}
	
	public static void setBackground(String bg) {
		background = bg;
	}
	
	public static void ClearUpdaters() {
		background="";
		charChange='0';
	}
}
