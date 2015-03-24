package greenpumpkin.artemis.entities;

import greenpumpkin.artemis.components.CharacterC;
import greenpumpkin.artemis.components.PositionC;
import greenpumpkin.artemis.components.SpriteC;
import greenpumpkin.artemis.components.VelocityC;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class CharacterFactory {

	public static Entity create(World world, char character) {
		Entity e = world.createEntity();

		PositionC position = new PositionC();
		position.x = 2000;
		position.y = 2000;
		e.addComponent(position);

		VelocityC velocity = new VelocityC();
		velocity.velX = 0;
		velocity.velY = 0;
		e.addComponent(velocity);

		Texture texture = new Texture(Gdx.files.internal("chars/" + character + "/" + character + "1.png"));
		
		SpriteC sprite = new SpriteC();
		sprite.sprite = new Sprite(texture);
		sprite.sprite.setOrigin(0, 0);
		e.addComponent(sprite);
		
		CharacterC characterc = new CharacterC();
		characterc.letter = character;
		e.addComponent(characterc);

		return e;
	}
}
