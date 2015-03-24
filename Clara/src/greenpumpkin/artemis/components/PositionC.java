package greenpumpkin.artemis.components;

import com.artemis.Component;

////////////////////////////////////////////////////////////
//This is the component for position.///////////////////////
//This is a universal component for anything on screen./////
////////////////////////////////////////////////////////////

public class PositionC extends Component {
	public float x, y;
	
	public void addX(float x) {
		this.x += x;
	}
	
	public void addY(float y) {
		this.y += y;
	}
}
