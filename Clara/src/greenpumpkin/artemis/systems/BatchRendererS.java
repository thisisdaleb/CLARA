package greenpumpkin.artemis.systems;

import java.util.ArrayList;

import greenpumpkin.artemis.TreasuremonWorld;
import greenpumpkin.artemis.components.BackgroundC;
import greenpumpkin.artemis.components.CharacterC;
import greenpumpkin.artemis.components.DialogC;
import greenpumpkin.artemis.components.PositionC;
import greenpumpkin.artemis.components.SpriteC;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

//DRAWS ALL SPRITES AND BACKGROUNDS, THEN DRAWS THE DIALOG
public class BatchRendererS extends EntitySystem {
	@Mapper ComponentMapper<SpriteC> spriteMap;
	@Mapper ComponentMapper<PositionC> posMap;
	@Mapper ComponentMapper<DialogC> dialogMap;
	@Mapper ComponentMapper<CharacterC> charMap;
	@Mapper ComponentMapper<BackgroundC> bgMap;
	static String dialogLine = "";
	static String displayedLine = "";
	private BitmapFont font;
	public boolean scroll = false;
	private ArrayList<SpriteList> backgroundList = new ArrayList<SpriteList>();
	private ArrayList<SpriteList> characterList = new ArrayList<SpriteList>();
	private SpriteList dialogBox = new SpriteList();
	
	private static Sound bleep = Gdx.audio.newSound(Gdx.files.internal("sounds/bleeeep.mp3"));
	
	@SuppressWarnings("unchecked")
	public BatchRendererS() {
		super(Aspect.getAspectForAll(SpriteC.class, PositionC.class));
	}
	
	protected void initialize() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 24;
		font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		font.setColor(Color.BLACK);
	}
	
	@Override
	protected void inserted(Entity e) {
		if(bgMap.has(e))
		{
			backgroundList.add(new SpriteList(e.getComponent(PositionC.class).x,e.getComponent(PositionC.class).y,e.getComponent(SpriteC.class).sprite));
			if(backgroundList.size()>=3){
				backgroundList.remove(0);
			}
		}
		if(charMap.has(e))
		{
			characterList.add(new SpriteList(e.getComponent(PositionC.class).x,e.getComponent(PositionC.class).y,e.getComponent(SpriteC.class).sprite));
		}
		if(dialogMap.has(e))
		{
			dialogBox.locationX=e.getComponent(PositionC.class).x;
			dialogBox.locationY=e.getComponent(PositionC.class).y;
			dialogBox.sprite=e.getComponent(SpriteC.class).sprite;
		}
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		TreasuremonWorld.batch.setProjectionMatrix(TreasuremonWorld.camera.combined);
		TreasuremonWorld.batch.begin();
		//draws the backgrounds
		for (int i = 0; i < backgroundList.size()-1;i++)
		{
			backgroundList.get(i).sprite.setPosition(backgroundList.get(i).locationX, backgroundList.get(i).locationY);
			backgroundList.get(i).sprite.draw(TreasuremonWorld.batch);
		}
		backgroundList.get(backgroundList.size()-1).sprite.setPosition(backgroundList.get(backgroundList.size()-1).locationX,
				backgroundList.get(backgroundList.size()-1).locationY);
		backgroundList.get(backgroundList.size()-1).sprite.draw(TreasuremonWorld.batch);
		if(scroll==true){
			backgroundList.get(backgroundList.size()-1).sprite.setPosition(backgroundList.get(backgroundList.size()-1).locationX-1280f,
				backgroundList.get(backgroundList.size()-1).locationY);
			backgroundList.get(backgroundList.size()-1).sprite.draw(TreasuremonWorld.batch);
			backgroundList.get(backgroundList.size()-1).sprite.setPosition(backgroundList.get(backgroundList.size()-1).locationX+1280f,
				backgroundList.get(backgroundList.size()-1).locationY);
			backgroundList.get(backgroundList.size()-1).sprite.draw(TreasuremonWorld.batch);
			backgroundList.get(backgroundList.size()-1).locationX-=10;
			if(backgroundList.get(backgroundList.size()-1).locationX<=-1280)
				backgroundList.get(backgroundList.size()-1).locationX=0;
		}
		//draws the characters (deprecated)
		//for (int i = 0; i < characterList.size();i++)
		//{	
		//	characterList.get(i).sprite.setPosition(characterList.get(i).locationX, characterList.get(i).locationY);
		//	characterList.get(i).sprite.draw(TreasuremonWorld.batch);
		//}
		
		//draws the characters for real
		for (int i = 0, s = entities.size(); s > i; i++) {
			process(entities.get(i));
		}
		if(!dialogLine.equals(""))
			dialogBox.sprite.draw(TreasuremonWorld.batch);
		drawDialog();
		TreasuremonWorld.batch.end();
	}
	
	private void process(Entity e) {
		if(charMap.has(e))
		{
			e.getComponent(SpriteC.class).sprite.setPosition(e.getComponent(PositionC.class).x, e.getComponent(PositionC.class).y);
			e.getComponent(SpriteC.class).sprite.draw(TreasuremonWorld.batch);
		}
	}

	private void drawDialog() {
		if(displayedLine.length() < dialogLine.length()){
			displayedLine=dialogLine.substring(0, displayedLine.length()+1);
		}
		font.drawMultiLine(TreasuremonWorld.batch, displayedLine, 80, 160);
	}

	//dealing with dialog and stuff
	public static void clearDialog(){
		dialogLine="";
		displayedLine="";
	}

	public static void setDialog(String newDialog){
		dialogLine=newDialog;
		addNewLines();
		bleep.play(0.3f);
	}

	public static void addNewLines(){
		if(dialogLine.length() >48){
			dialogLine = dialogLine.substring(0,dialogLine.substring(0,48).lastIndexOf(" "))
					+ "\n" + (dialogLine.substring(dialogLine.substring(0,48).lastIndexOf(" ")+1));
			if(dialogLine.length() >94){
				dialogLine = dialogLine.substring(0,dialogLine.substring(0,94).lastIndexOf(" "))
						+ "\n" + (dialogLine.substring(dialogLine.substring(0,94).lastIndexOf(" ")+1));
				if(dialogLine.length() >140){
					dialogLine = dialogLine.substring(0,dialogLine.substring(0,140).lastIndexOf(" "))
							+ "\n" + (dialogLine.substring(dialogLine.substring(0,140).lastIndexOf(" ")+1));
				}
			}
		}
	}

	//pls ignore
	@Override
	protected boolean checkProcessing() {
		return true;
	}
	
	//For handling the order of characters
	class SpriteList {
		float locationX;
		float locationY;
		Sprite sprite;
		
		public SpriteList(float x, float y, Sprite newSprite) {
			locationX = x;
			locationY = y;
			sprite = newSprite;
		}

		public SpriteList() {
			// TODO Auto-generated constructor stub
		}
	}
}
