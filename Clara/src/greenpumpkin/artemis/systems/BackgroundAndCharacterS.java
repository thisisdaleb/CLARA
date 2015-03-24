package greenpumpkin.artemis.systems;

import java.util.Random;

import greenpumpkin.artemis.TreasuremonWorld;
import greenpumpkin.artemis.components.BackgroundC;
import greenpumpkin.artemis.components.CharacterC;
import greenpumpkin.artemis.components.PositionC;
import greenpumpkin.artemis.components.SpriteC;
import greenpumpkin.artemis.entities.BackgroundFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BackgroundAndCharacterS extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<CharacterC> charMap;
	@Mapper
	ComponentMapper<BackgroundC> bgMap;

	@SuppressWarnings("unchecked")
	public BackgroundAndCharacterS() {
		super(Aspect.getAspectForOne(CharacterC.class, BackgroundC.class));
	}

	@Override
	protected void process(Entity e) {
		if (bgMap.has(e)) {
			changeMap(e);
		}
		if (charMap.has(e)) {
			changeChar(e);
		}
		TreasuremonWorld.ClearUpdaters();
	}

	private void changeMap(Entity e) {
		if (!TreasuremonWorld.background.equals("")) {
			world.addEntity(BackgroundFactory.create(world, "backgrounds/"
					+ TreasuremonWorld.background + ".png"));
		}
	}

	private void changeChar(Entity e) {
		if(!world.getSystem(ReadScriptS.class).Clara.characterManipulator
				.isEmpty()) {
			for (int k = 0; k < world.getSystem(ReadScriptS.class).Clara.characterManipulator
					.size(); k++) {
				if(e.getComponent(CharacterC.class).letter == world
						.getSystem(ReadScriptS.class).Clara.characterManipulator
						.get(k).character)
				{
					if(world.getSystem(ReadScriptS.class).Clara.characterManipulator
							.get(k).characterFace > 0)
					{
						changeCharFace(e, k);
					}
					else if(world.getSystem(ReadScriptS.class).Clara.characterManipulator
							.get(k).moveCharacter[0] != 0
							|| world.getSystem(ReadScriptS.class).Clara.characterManipulator
									.get(k).moveCharacter[1] != 0)
					{
						moveChar(e, k);
					}
					else if(world.getSystem(ReadScriptS.class).Clara.characterManipulator
							.get(k).autoPlace[0] != 0
							|| world.getSystem(ReadScriptS.class).Clara.characterManipulator
									.get(k).autoPlace[1] != 0)
					{
						changeCharSpot(e, k);
					}
					else if(world.getSystem(ReadScriptS.class).Clara.characterManipulator
							.get(k).scale != 0)
					{
						changeCharSize(e, k);
					}
					else if(world.getSystem(ReadScriptS.class).Clara.characterManipulator
									.get(k).rand[0][0] != 0
							|| world.getSystem(ReadScriptS.class).Clara.characterManipulator
									.get(k).rand[0][1] != 0
							|| world.getSystem(ReadScriptS.class).Clara.characterManipulator
									.get(k).rand[1][0] != 0
							|| world.getSystem(ReadScriptS.class).Clara.characterManipulator
									.get(k).rand[1][1] != 0){
						randomizePosition(e, k);
					}
				}
			}
		}
	}

	private void changeCharSize(Entity e, int k) {
		e.getComponent(SpriteC.class).sprite.setScale(world.getSystem(ReadScriptS.class).Clara.characterManipulator
				.get(k).scale/10f);
		world.getSystem(ReadScriptS.class).Clara.characterManipulator.remove(k);
	}

	private void changeCharFace(Entity e, int k) {
		e.getComponent(SpriteC.class).sprite
				.setTexture(new Texture(
						Gdx.files.internal("chars/"
								+ world.getSystem(ReadScriptS.class).Clara.characterManipulator
										.get(k).character
								+ "/"
								+ world.getSystem(ReadScriptS.class).Clara.characterManipulator
										.get(k).character
								+ world.getSystem(ReadScriptS.class).Clara.characterManipulator
										.get(k).characterFace + ".png")));
		world.getSystem(ReadScriptS.class).Clara.characterManipulator.remove(k);
	}

	private void changeCharSpot(Entity e, int k) {
		e.getComponent(PositionC.class).x = world.getSystem(ReadScriptS.class).Clara.characterManipulator
				.get(k).autoPlace[0];
		e.getComponent(PositionC.class).y = world.getSystem(ReadScriptS.class).Clara.characterManipulator
				.get(k).autoPlace[1];
		world.getSystem(ReadScriptS.class).Clara.characterManipulator.remove(k);
	}

	//this changes the position over time, instead of instantaneously
	private void moveChar(Entity e, int k) {
		float xMove = setMovement(e, k, 0);
		float yMove = setMovement(e, k, 1);
		e.getComponent(PositionC.class).x += xMove;
		e.getComponent(PositionC.class).y += yMove;
		if(world.getSystem(ReadScriptS.class).Clara.characterManipulator.get(k).contMove<=0){
			System.out.println(e.getComponent(PositionC.class).x + " " + e.getComponent(PositionC.class).y);
			world.getSystem(ReadScriptS.class).Clara.characterManipulator.remove(k);
		}
		else
			world.getSystem(ReadScriptS.class).Clara.characterManipulator.get(k).contMove--;
	}
	
	private float setMovement(Entity e, int k, int z){
		float y=world.getSystem(ReadScriptS.class).Clara.characterManipulator
				.get(k).moveCharacter[z];
		float x=30-world.getSystem(ReadScriptS.class).Clara.characterManipulator
				.get(k).contMove;
		//return (float) ((y)*(Math.sin( Math.toRadians(
		//		(1/(3*Math.PI))*x
		//		) ) ));
		return (float) (y*0.0524078*Math.sin(x*Math.PI/30));
		//return (float) (world.getSystem(ReadScriptS.class).Clara.characterManipulator
		//		.get(k).moveCharacter[z]*Math.sin(Math.toRadians((33/(100*Math.PI))*
		//				(31-world.getSystem(ReadScriptS.class).Clara.characterManipulator
		//				.get(k).contMove)))/0.85227805);
	}
	
	////
	//Okay. notes time. What formula for a single sine wave hump would be equal in area to a single rectangle*30
	//Okay, using wolfram:
	//=sin((33/(100*pi))x)
	//That is very close to ending exactly at 30
	//=(y/19.040)*sin((33/(100*pi))x)
	//where x is the frame this is set to, and y is the amount of pixels that need to be offset
	////
	
	
	private void randomizePosition(Entity e, int k) {
		Random rand = new Random();
		rand.nextFloat();
		int randx = rand.nextInt((int) (world.getSystem(ReadScriptS.class).Clara.characterManipulator.get(k).rand[0][1]
				- world.getSystem(ReadScriptS.class).Clara.characterManipulator.get(k).rand[0][0]));
		int randy = rand.nextInt((int) (world.getSystem(ReadScriptS.class).Clara.characterManipulator.get(k).rand[1][1]
				- world.getSystem(ReadScriptS.class).Clara.characterManipulator.get(k).rand[1][0]));
		e.getComponent(PositionC.class).x = randx
				+ world.getSystem(ReadScriptS.class).Clara.characterManipulator.get(k).rand[0][0]; 
		e.getComponent(PositionC.class).y =  randy 
				+ world.getSystem(ReadScriptS.class).Clara.characterManipulator
				.get(k).rand[1][0];
		
		world.getSystem(ReadScriptS.class).Clara.characterManipulator.remove(k);
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}
