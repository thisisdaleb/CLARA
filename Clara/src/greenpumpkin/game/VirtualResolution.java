package greenpumpkin.game;

import com.badlogic.gdx.graphics.OrthographicCamera;

////////////////////////////////////////////////////////////
//This sets the virtual resolution camera.//////////////////
//By using this, the game can run at any resolution.////////
////////////////////////////////////////////////////////////

public class VirtualResolution extends OrthographicCamera {

    public VirtualResolution(int width, int height){
        super(width, height);
        this.position.x=width/2;
        this.position.y=height/2;
    }
}
