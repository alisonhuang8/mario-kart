package gamedata;

import data.SpriteMakerModel;
import newengine.sprite.Sprite;
import newengine.sprite.components.Cooldown;
import newengine.sprite.components.GameBus;

public class SpriteConstructor {
	
	private Sprite constructedSprite; 
	
	public SpriteConstructor(SpriteMakerModel spriteModel){
		construct(spriteModel);
	}

	private void construct(SpriteMakerModel model) {
		constructedSprite = new Sprite();
		model.getActualComponents().forEach(component -> constructedSprite.addComponent(component));
		constructedSprite.addComponent(new GameBus());
		constructedSprite.addComponent(new Cooldown());
		
	}

}
