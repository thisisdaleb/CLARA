package greenpumpkin.clara;

import java.util.ArrayList;

public class ScaleList {
	ArrayList<Integer> notes = new ArrayList<Integer>();
	ArrayList<ChordList> chords = new ArrayList<ChordList>();
	private int[] major = new int[]{0,2,4,5,7,9,11};
	private int[] minor = new int[]{0,2,3,5,7,8,10};
	private boolean isItMajor= false;

	public ScaleList(int k, boolean isMajor) {
		isItMajor = isMajor;
		if(isMajor)
			createMajorScale(k);
		else
			createMinorScale(k);
	}

	private void createMajorScale(int k) {
		for(int z=0;z<major.length;z++){
			notes.add(major[z]+k);
			if(z==(0 | 3 | 4))
				chords.add(new ChordList(major[z]+k, "Major"));
			else if (z==(1 | 2 | 5))
				chords.add(new ChordList(major[z]+k, "minor"));
			else
				chords.add(new ChordList(major[z]+k, "minor"));
		}
	}

	private void createMinorScale(int k) {
		for(int z=0;z<major.length;z++){
			notes.add(minor[z]+k);
		}
	}

	public boolean isMajor(){
		return isItMajor;
	}

	class ChordList {
		int[] note = new int[3];

		public ChordList(int k, String type) {
			note[0] = k;
			if(type.equals("Major")){
				note[1] = k+4;
				note[2] = k+7;
			}
			else if(type.equals("minor")){
				note[1] = k+3;
				note[2] = k+7;
			}
			else {
				note[1] = k+3;
				note[2] = k+6;

			}
		}
	}
}
