package greenpumpkin.clara;

import java.util.ArrayList;

public class Progression {
	private SongInfo songInfo = new SongInfo(); //contains percentages
	private ScaleList scale;
	public ArrayList<Integer> progression = new ArrayList<Integer>();
	
	
	public Progression(ScaleList usedScale) {
		scale=usedScale;//sets scale used for this song
	}

	public void create() {
		if(scale.isMajor())
			createMajorProgression();
		else
			createMinorProgression();
	}
	
	@SuppressWarnings("unused")
	private void createSimpleProgression() {
		while(progression.size()<63){
			progression.add(0);
			progression.add(4);
			progression.add(5);
			progression.add(3);
		}
		progression.add(4);
		progression.add(0);
	}

	private void createMajorProgression() {
		//first chord always root
		progression.add(0);
		while(progression.size()<63) //creates all chords in a loop
		{
			float newChord = (float) Math.random();
			System.out.println(newChord);
			for(int k=0;k<songInfo.majorProbabilities.length;k++) //loops through array to check if under probability
			{
				if(newChord<songInfo.majorProbabilities[k] && (progression.get(progression.size()-1)!=k)) 
				{ //if number is good on probability for this note AND is not the same as the last chord
					if(!(progression.get(progression.size()-1)!=k && newChord==2))
						progression.add(k);
					break;
				}
			}
			
		}
		progression.add(4); //Chord V
		progression.add(0); //Chord I
		
	}

	private void createMinorProgression() {
		//first chord always root
				progression.add(0);
				while(progression.size()<14){
					float newChord = (float) Math.random();
					for(int k=0;k<songInfo.minorProbabilities.length;k++){
						if(newChord<songInfo.minorProbabilities[k] && progression.get(progression.size()-1)!=k)
						{
							progression.add(k);
							break; 
						}
					}
				}
				progression.add(5);
				progression.add(6);
				progression.add(0);
	}
}
