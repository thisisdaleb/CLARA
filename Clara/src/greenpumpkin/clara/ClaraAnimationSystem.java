package greenpumpkin.clara;

import java.util.ArrayList;

import greenpumpkin.artemis.TreasuremonWorld;
import greenpumpkin.artemis.entities.CharacterFactory;
import greenpumpkin.artemis.entities.LightFactory;
import greenpumpkin.artemis.systems.BatchRendererS;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;

public class ClaraAnimationSystem {
	public String currentLine = "";
	public ArrayList<CharacterManipulator> characterManipulator = new ArrayList<CharacterManipulator>();
	private int multiLine = 0;
	private int time = 0;
	private Music music;
	private float volume = 0.2f;
	
	public ClaraAnimationSystem(){
		
	}
	
	public ClaraAnimationSystem(TreasuremonWorld world){
		
	}
	
	//features needed:
	//animated sprites
	//background transistions
	
	public void receiveFromScript(String scriptString, World world) {
		currentLine = scriptString;
		BatchRendererS.clearDialog();
		if(multiLine>0)
			multiLine--;
		if(currentLine==null)
			finale(world);
		else if(currentLine.substring(0,1).equals("-"))
			commandHandler(world);
		else if(currentLine.substring(0,1).equals("/"))
			scaleHandler(world);
		else if(!currentLine.substring(0,1).equals("+"))
			dialogHandler(world);
		else if(currentLine.substring(0,1).equals("+"))
			characterHandler(world);
	}
	
	private void scaleHandler(World world) {
		currentLine=currentLine.substring(1);
		if(currentLine.charAt(0)=='s'){
			characterManipulator.add(new CharacterManipulator(currentLine.charAt(1),
					Integer.parseInt(currentLine.substring(2)), true));
		}
		else if(currentLine.charAt(0)=='b'){
			if(!world.getSystem(BatchRendererS.class).scroll)
				world.getSystem(BatchRendererS.class).scroll=true;
			else
				world.getSystem(BatchRendererS.class).scroll=false;
		}
	}

	private void commandHandler(World world) {
		time=1;
		currentLine=currentLine.substring(1);
		processCommandList(world);
	}

	private void dialogHandler(World world) {
		BatchRendererS.setDialog(currentLine);
		if(currentLine.length()>80)
			time=60;
		else if(currentLine.length()>40)
			time=50;
		else
			time=40;
	}
	
	private void characterHandler(World world) {
		TreasuremonWorld.setCharacters(currentLine.substring(1));
		currentLine = currentLine.substring(1);
		for(int k = 0; k<currentLine.length();k++)
			world.addEntity(CharacterFactory.create(world, currentLine.charAt(k)));
	}

	private void finale(World world) {
		Gdx.app.exit();
	}
	//////
	//////
	//////
	//////
	//////
	//////
	//////
	//////
	//////
	//////
	//////
	//////I almost feel like putting this part in its own class, it is going to be so long.
	private void processCommandList(World world) {
		if(currentLine.substring(0, 1).equals("b")){
			TreasuremonWorld.setBackground(currentLine.substring(1));
		}
		else if(currentLine.substring(0, 1).equals("c")){
			characterManipulator.add(new CharacterManipulator(currentLine.charAt(1),Integer.parseInt(currentLine.substring(2)), false));
		}
		else if(currentLine.substring(0, 1).equals("m")){
			String[] placer = currentLine.substring(1).split("\\s+");
			characterManipulator.add(new CharacterManipulator(placer[0].charAt(0),
				Integer.parseInt(placer[1]),Integer.parseInt(placer[2]), false));
			time=10;
		}
		else if(currentLine.substring(0, 1).equals("p")){
			String[] placer = currentLine.substring(1).split("\\s+");
			if(placer.length>4)
				characterManipulator.add(new CharacterManipulator(placer[0].charAt(0),
						Integer.parseInt(placer[1]),Integer.parseInt(placer[2]),
						Integer.parseInt(placer[3]),Integer.parseInt(placer[4])));
			else
				characterManipulator.add(new CharacterManipulator(placer[0].charAt(0),
					Integer.parseInt(placer[1]),Integer.parseInt(placer[2]), true));
		}
		else if(currentLine.substring(0, 1).equals("w")){
			time=Integer.parseInt(currentLine.substring(1));
		}
		//
		else if(currentLine.substring(0, 1).equals("S")){
			if(currentLine.substring(1).equals("stop")){
				music.pause();
			}
			else if(currentLine.substring(1).equals("resume")){
				music.play();
			}
			else if(currentLine.substring(1).equals("lower")){
				if(volume>=0.1f){
					volume-=0.1f;
					music.setVolume(volume);
				}
			}
			else if(currentLine.substring(1).equals("raise")){
				if(volume<=0.6f){
					volume+=0.1f;
					music.setVolume(volume);
				}
			}
			else{
			music = Gdx.audio.newMusic(Gdx.files.internal("music/"+ currentLine.substring(1) +".mp3"));
			music.play();
			}
		}
		//
		else if(currentLine.substring(0, 1).equals("s")){
			Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/" + currentLine.substring(1) +".mp3"));
			sound.play(0.25f);
		}
		else if(currentLine.substring(0, 1).equals("L")){
			if(currentLine.substring(1).equals("1")){
				TreasuremonWorld.flushRayHandler();
				world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*3, 20f, 750f, 7.7f, 1.875f*4f));
				world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*3, 640f, 1000f, 7.7f, 1.875f*4f));
				world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*3, 1260f, 750f, 7.7f, 1.875f*4f));
				world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance, 20f, -100f, 7.7f, 1.875f*4f));
				world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*2, 640f, -200f, 7.7f, 1.875f*4f));
				world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance, 1260f, -100f, 7.7f, 1.875f*4f));
			}
			else if(currentLine.substring(1).equals("2")){
				TreasuremonWorld.flushRayHandler();	
				world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*4, 20f, 750f, 7.7f, 1.875f*4f));
				world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*4, 640f, 1000f, 7.7f, 1.875f*4f));
				world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*4, 1260f, 750f, 7.7f, 1.875f*4f));
				world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*3, 20f, -100f, 7.7f, 1.875f*4f));
				world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*3, 640f, -200f, 7.7f, 1.875f*4f));
				world.addEntity(LightFactory.createCyclePoint(world, TreasuremonWorld.rayHandler,  TreasuremonWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f), TreasuremonWorld.lightDistance*3, 1260f, -100f, 7.7f, 1.875f*4f));
			}
		}
		else if(currentLine.substring(0, 1).equals("b")){
			
		}
		else if(currentLine.substring(0, 1).equals("b")){
			
		}
		else if(currentLine.substring(0, 1).equals("b")){
			
		}
		else if(currentLine.substring(0, 1).equals("b")){
			
		}
		else if(currentLine.substring(0, 1).equals("b")){
			
		}
		else if(currentLine.substring(0).matches("-?\\d+(\\.\\d+)?")){
			multiLine=Integer.parseInt(currentLine.substring(0));
		}
			
	}
	//////
	//////
	//////
	//////
	//////
	//////
	
	public int returnTime () {
		return time;
	}
	
	public int returnMultiLine () {
		return multiLine;
	}
	
	public class CharacterManipulator {
		public char character = '0';
		public int characterFace = 0;
		public int scale = 0;
		public float moveCharacter[] = new float[]{0,0};
		public int contMove = 30;
		public float autoPlace[] = new float[]{0,0};
		public float rand[][] = new float[][]{{0,0},{0,0}};
		
		CharacterManipulator(char charc, int charFace, boolean scaler) {
			if(!scaler){
				character=charc;
				characterFace=charFace;
			}
			else{
				character=charc;
				scale=charFace;
			}
		}
		CharacterManipulator(char charc, int x, int y, boolean instant) {
			character=charc;
			if(instant==true){
				
				autoPlace[0]=x;
				autoPlace[1]=y;
			}
			if(instant==false){
				moveCharacter[0]=x;
				moveCharacter[1]=y;
			}
		}
		public CharacterManipulator(char charc, int x1, int x2, int y1, int y2) {
			character=charc;
			rand[0][0]=x1;
			rand[0][1]=x2;
			rand[1][0]=y1;
			rand[1][1]=y2;
		}
	}
}
