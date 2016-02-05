package greenpumpkin.clara;

import java.util.ArrayList;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;

public class Harmony {
	SongInfo songInfo = new SongInfo();
	ArrayList<Integer> rhythm = new ArrayList<Integer>();
	ScaleList scale;
	Pattern simple = null;
	Pattern complex = null;
	Player player = null;

	public Harmony(ScaleList usedScale) {
		scale=usedScale;
	}

	public void createSimple(Progression progression) {
		String chordString = "T120 V0 ";
		for(int k = 0; k<progression.progression.size();k++){
			chordString+=(scale.notes.get((progression.progression.get(k)))+(12*3) + "w+" 
				+ (scale.notes.get(progression.progression.get(k))+(12*4)) + "w ");	
		}
		simple = new Pattern(chordString);
		System.out.println(simple.toString());
	}
	
	public void createComplex(int octave, Progression progression) {
		createRhythm();
		placeNotes(octave, progression);
	}

	private void createRhythm() {
		rhythm = new ArrayList<Integer>();
		boolean full = false;
		int sum = 0;
		int[] rand = new int[]{1,2,4};
		while(!full){
			int note = rand[(int)(Math.random()*2.2)];
			if((note+sum)<=8){
				rhythm.add(note);
				sum+=note;
			}
			if(sum==8)
				full=true;
		}
	}
	
	private void placeNotes(int octave, Progression progression) {
		String complexString = "T120 V1 ";
		for(int k=0;k<64;k++){ 
			if(k%4==0)
				createRhythm();
			for(int j = 0;j<rhythm.size();j++){
				String length = "";
				if(rhythm.get(j)==1)
					length = "ss";
				else if(rhythm.get(j)==2)
					length = "q";
				else
					length = "h";
				int note = createNote(octave, progression, k, j);
				complexString+= note + length + " ";
			}
		}
		complex = new Pattern(complexString);
	}

	private int createNote(int octave, Progression progression, int k, int j) {
		int note = 0;
		if(j%2==0)
			note = scale.chords.get(progression.progression.get(k)).note[0];
		else
			note = scale.chords.get(progression.progression.get(k)).note[2];
		return octave*12+note;
	}

	public void play() {
		player = new Player();
		player.play(complex, simple);
	}
}
