package greenpumpkin.game;


import greenpumpkin.clara.MusicGenerator;

////////////////////////////////////////////////////////////
//This is the main class.It sets the default settings///////
//for the game and loads the first area.////////////////////
//At least in the other project.////////////////////////////
//This is just for music generation.////////////////////////
////////////////////////////////////////////////////////////

public class Treasuremon{
	
	public static void main (String[] arg) {
		MusicGenerator musicCreation = new MusicGenerator(); //creates list of major and minor scales
		musicCreation.createMusic(); //creates song
	}
}
