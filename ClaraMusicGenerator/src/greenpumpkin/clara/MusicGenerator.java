package greenpumpkin.clara;

import java.util.ArrayList;
import java.util.Arrays;

import org.jfugue.player.Player;

public class MusicGenerator {
	Player player = new Player();
	private ArrayList<ScaleList> majorScales = new ArrayList<ScaleList>(); //12 scales, starts at C
	private ArrayList<ScaleList> minorScales = new ArrayList<ScaleList>(); //12 scales, starts at C
	private ScaleList scale = null; //scale being used for song

	public MusicGenerator() {
		for (int k = 0; k < 12; k++) { //loop through C up to B
			majorScales.add(new ScaleList(k, true)); //create major scales
			minorScales.add(new ScaleList(k, false)); //create minor scales
		}
	}

	public void createMusic() {
		// These 2 if/else statements will decide what scale to use, whether it
		// be major or minor
		if (Math.random() < 0.5) //number is ratio of major to all
			scale = majorScales.get((int) (Math.random() * 11.4)); //picks major key
		else
			scale = minorScales.get((int) (Math.random() * 11.4)); //picks minor key
		System.out.println(scale.notes.get(0) + " " + scale.isMajor()); //prints the "scaleNumber isMajor"

		Progression chords = new Progression(scale); //sets scale for progression
		chords.create(); //creates progression

		System.out.println(chords.progression.size() + " "
				+ (chords.progression.toString())); //prints out chosen progression
		Harmony harmony = new Harmony(scale);
		harmony.createSimple(chords);
		harmony.createComplex(4, chords);//     /////POINT WHERE MINOR CRASHES/////
		harmony.play();//     /////SECONDARY CRASH WITH MINOR/////

		Melody melody = new Melody(scale);
		melody.create(chords);
	}

	/*
	 * public void createTest() {
	 * player.play("T120 V0 60w 55w 64w 60w V1 36w+48w 36w+48w 36w+48w 36w+48w"
	 * ); MidiParser parser = new MidiParser(); ParserListenerAdapter adapter =
	 * new ParserListenerAdapter(); parser.addParserListener(adapter); Pattern
	 * bob = new Pattern(); bob.add("C7w + C6w"); bob.setTempo(120);
	 * System.out.println(bob.toString()); OutputStream file = null; try {
	 * String filePath = new File("assets/bob.mid").getAbsolutePath(); file =
	 * new FileOutputStream(filePath); } catch (FileNotFoundException e1) {
	 * e1.printStackTrace(); } try {
	 * MidiFileManager.savePatternToMidi(bob.getPattern(), file); } catch
	 * (IOException e) { e.printStackTrace(); } }
	 */
	public void notes() {
		// "C4w C3w" = notes seperate, one after another
		// "C4w+C3w" = notes played at same time
		// scale.
	}

}
