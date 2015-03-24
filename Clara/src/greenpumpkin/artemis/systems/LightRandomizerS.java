package greenpumpkin.artemis.systems;

import greenpumpkin.artemis.components.LightC;
import greenpumpkin.artemis.components.PositionC;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;

public class LightRandomizerS extends IntervalEntityProcessingSystem {
	@Mapper ComponentMapper<PositionC> positionMap;

	@SuppressWarnings("unchecked")
	public LightRandomizerS() {
		super(Aspect.getAspectForAll(LightC.class, PositionC.class),30/128f);
	}

	@Override
	protected void process(Entity e) {
		PositionC newPos = positionMap.get(e);
		if(newPos.x<0)
			newPos.x=(float) (Math.random()*25+3);
		else
			newPos.x=(float) (-20);
		newPos.y=(float) (Math.random()*15+2);
	}

}
