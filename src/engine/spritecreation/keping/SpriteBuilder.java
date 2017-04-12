package engine.spritecreation.keping;

import bus.EventBus;
import engine.sprite.Sprite;
import engine.spritecreation.keping.attributes.AttackerBuilder;
import engine.spritecreation.keping.attributes.MovableBuilder;


public class SpriteBuilder {

	// TODO: since these builders don't contain mutable state
	// we could just make them static instead of creating them multiple times;
	// or we can construct all builders once, just like in GameBuilder.
	private AttackerBuilder attackerBuilder = new AttackerBuilder();
	private MovableBuilder movableBuilder = new MovableBuilder();
	
	public Sprite buildSprite(EventBus bus, SpriteConfig config) {
		Sprite sprite = new Sprite();
		// we could set bus for the sprite. sprite.setBus(bus);
		sprite.setMovable(movableBuilder.buildMovable(config.getMovableConfig()));
		sprite.setAttacker(attackerBuilder.buildAttacker(config.getAttackerConfig()));
		return sprite;
	}
	
}
