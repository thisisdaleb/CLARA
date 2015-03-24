package greenpumpkin.artemis.systems;

import greenpumpkin.artemis.entities.BackgroundFactory;
import greenpumpkin.clara.ClaraAnimationSystem;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntitySystem;
import com.artemis.utils.ImmutableBag;

import java.io.*;

public class ReadScriptS extends IntervalEntitySystem {
	public static int scriptTimer = 16;
	private boolean initialLoad=true;
	private FileInputStream scriptInput;
	private InputStreamReader scriptReader;
	protected BufferedReader bufferedScript;
	protected String currentLine = "";
	public ClaraAnimationSystem Clara = new ClaraAnimationSystem();

	public ReadScriptS() {
		super(Aspect.getEmpty(), 1/10f);
	}
	
	@Override
	protected void initialize() {
		world.addEntity(BackgroundFactory.createLogo(world));
	}

	@Override 
	protected void processEntities(ImmutableBag<Entity> entities) {
		if(scriptTimer<=0){
			processScript();
		}
		else{
			scriptTimer-=1;
		}
	}
	
	private void processScript() {
		if(initialLoad==true){
			world.addEntity(BackgroundFactory.createDialogBox(world));
			scriptTimer=1;
			initialLoad=false;					
			createInputSystem();
		}
		readScriptLine(); 
	}
	
	private void readScriptLine() {
		try {
			currentLine = bufferedScript.readLine();
			sendToClara();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//
	//This is the main method for interaction with Clara!!!
	private void sendToClara() {
		Clara.receiveFromScript(currentLine, world);
		scriptTimer = Clara.returnTime();
		if(Clara.returnMultiLine()>0)
			readScriptLine();
	}
	////
	//
	
	//
	//Grabs the script text file. Why is Java input so messy?
	private void createInputSystem() {
		try {
			scriptInput = new FileInputStream("assets/script.txt");
			scriptReader = new InputStreamReader(scriptInput);
			bufferedScript = new BufferedReader(scriptReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
