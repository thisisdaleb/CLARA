package greenpumpkin.artemis.systems;

import greenpumpkin.artemis.components.LightC;
import greenpumpkin.artemis.components.LightCycleC;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.artemis.utils.FastMath;

public class LightCycleS extends IntervalEntityProcessingSystem {
	@Mapper
	ComponentMapper<LightC> lightMap;
	@Mapper
	ComponentMapper<LightCycleC> cycleMap;
	
	@SuppressWarnings("unchecked")
	public LightCycleS() {
		super(Aspect.getAspectForAll(LightC.class, LightCycleC.class), 1 / 30f);
	}
	
	@Override
	protected void process(Entity e) {
		LightC newLight = lightMap.get(e);
		LightCycleC cycle = cycleMap.get(e);
		
		newLight.light.setDistance(setSize(cycle));
		cycle.currTime+=1/30f;
		
		if(cycle.currTime>cycle.time){
			cycle.currTime = 0;
			newLight.light.setDistance(cycle.minDist);
		}
	}
	
	protected float setSize(LightCycleC cycle) {
		float addedCurve;
		float curvedEquation;
		float size;
		size = cycle.size/2;
		curvedEquation = (float) (size-((size)*(FastMath.cos((2/cycle.time)*(cycle.currTime)*FastMath.PI))));
		addedCurve = (float) ((curvedEquation));
		return (float)(cycle.minDist + addedCurve );
	}
}
