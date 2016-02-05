package greenpumpkin.clara;

import java.util.ArrayList;

public class Melody {
	SongInfo songInfo = new SongInfo();
	ScaleList scale;
	public ArrayList<Integer> notes = new ArrayList<Integer>();
	
	public Melody(ScaleList usedScale) {
		scale=usedScale;
	}

	public void create(Progression Progression) {
		
	}
	

	public class ProgList{
		int note = 0;
		int rhythm = 0;
	}
}
