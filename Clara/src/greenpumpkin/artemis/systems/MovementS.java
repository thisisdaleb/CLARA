package greenpumpkin.artemis.systems;

import greenpumpkin.artemis.components.PositionC;
import greenpumpkin.artemis.components.VelocityC;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;

////////////////////////////////////////////////////////////
//This is the Artemis-based System for Movement. This///////
//is used for anything on the map that physically moves.////
////////////////////////////////////////////////////////////

public class MovementS extends IntervalEntityProcessingSystem {
	@Mapper ComponentMapper<PositionC> posMap;
	@Mapper ComponentMapper<VelocityC> velMap;

	@SuppressWarnings("unchecked")
	public MovementS() {
		super(Aspect.getAspectForAll(PositionC.class, VelocityC.class), 1/60f);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void process(Entity e) {
		//These grab the components from the entities.
		PositionC position = posMap.get(e);
		VelocityC velocity = velMap.get(e);
		
		position.addX( velocity.velX * (1/60f));
		position.addY( velocity.velY * (1/60f));
		
		if(position.y<0)
			position.y=0;
	}
	
	@Override
	protected boolean checkProcessing() {
		return true;
	}
}
