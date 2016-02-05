package greenpumpkin.clara;

import java.util.ArrayList;

public class Progression {
	private SongInfo songInfo = new SongInfo();
	private ScaleList scale;
	public ArrayList<Integer> progression = new ArrayList<Integer>();
	
	
	public Progression(ScaleList usedScale) {
		scale=usedScale;
	}

	public void create() {
		if(scale.isMajor())
			createMajorProgression();
		else
			createMinorProgression();
	}
	
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
		while(progression.size()<63){
			float newChord = (float) Math.random();
			for(int k=0;k<songInfo.majorProbabilities.length;k++){
				if(newChord<songInfo.majorProbabilities[k] && (progression.get(progression.size()-1)!=k))
				{
					if(!(progression.get(progression.size()-1)!=k && newChord==2))
						progression.add(k);
					break;
				}
			}
		}
		progression.add(4);
		progression.add(0);
		
	}

	private void createMinorProgression() {
		//first chord always root
				progression.add(0);
				while(progression.size()<63){
					float newChord = (float) Math.random();
					for(int k=0;k<songInfo.minorProbabilities.length;k++){
						if(newChord<songInfo.minorProbabilities[k] && progression.get(progression.size()-1)!=k)
						{
							progression.add(k);
							break;
						}
					}
				}
				progression.add(4);
				progression.add(0);
	}
}
