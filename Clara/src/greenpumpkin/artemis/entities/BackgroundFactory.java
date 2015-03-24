package greenpumpkin.artemis.entities;

import greenpumpkin.artemis.components.BackgroundC;
import greenpumpkin.artemis.components.DialogC;
import greenpumpkin.artemis.components.PositionC;
import greenpumpkin.artemis.components.SpriteC;
import greenpumpkin.artemis.components.VelocityC;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BackgroundFactory {

	public static Entity create(World world, String createbg) {
		Entity e = world.createEntity();
		
		PositionC position = new PositionC();
		position.x = 0;
		position.y = 0;
		e.addComponent(position);

		VelocityC velocity = new VelocityC();
		velocity.velX = 0;
		velocity.velY = 0;
		e.addComponent(velocity);

		Texture texture = new Texture(Gdx.files.internal(createbg));
		
		SpriteC sprite = new SpriteC();
		sprite.sprite = new Sprite(texture);
		sprite.sprite.setOrigin(0, 0);
		e.addComponent(sprite);
		
		e.addComponent(new BackgroundC());
		
		return e;
	}
	
	public static Entity createDefaultTest(World world) {
		Entity e = world.createEntity();
		
		PositionC position = new PositionC();
		position.x = 0;
		position.y = 0;
		e.addComponent(position);

		VelocityC velocity = new VelocityC();
		velocity.velX = 0;
		velocity.velY = 0;
		e.addComponent(velocity);

		Texture texture = new Texture(Gdx.files.internal("backgrounds/test.png"));
		
		SpriteC sprite = new SpriteC();
		sprite.sprite = new Sprite(texture);
		sprite.sprite.setOrigin(0, 0);
		e.addComponent(sprite);
		
		e.addComponent(new BackgroundC());
		
		return e;
	}
	

	public static Entity createLogo(World world) {
		Entity e = world.createEntity();
		
		PositionC position = new PositionC();
		position.x = 0;
		position.y = 0;
		e.addComponent(position);

		VelocityC velocity = new VelocityC();
		velocity.velX = 0;
		velocity.velY = 0;
		e.addComponent(velocity);

		Texture texture = new Texture(Gdx.files.internal("backgrounds/logo8bit.png"));
		
		SpriteC sprite = new SpriteC();
		sprite.sprite = new Sprite(texture);
		sprite.sprite.setOrigin(0, 0);
		e.addComponent(sprite);
		
		e.addComponent(new BackgroundC());
		
		return e;
	}
	
	public static Entity createDialogBox(World world) {
		Entity e = world.createEntity();
		
		PositionC position = new PositionC();
		position.x = 0;
		position.y = 0;
		e.addComponent(position);

		VelocityC velocity = new VelocityC();
		velocity.velX = 0;
		velocity.velY = 0;
		e.addComponent(velocity);

		Texture texture = new Texture(Gdx.files.internal("backgrounds/dialog.png"));
		
		SpriteC sprite = new SpriteC();
		sprite.sprite = new Sprite(texture);
		sprite.sprite.setOrigin(0, 0);
		e.addComponent(sprite);
		
		DialogC dialog = new DialogC();
		e.addComponent(dialog);
		
		return e;
	}
}
