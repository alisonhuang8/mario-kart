package engine.spritecreation;

import bus.BasicEventBus;
import bus.EventBus;
import data.AttributeData;
import engine.model.SpriteModelEvent;
import engine.model.TileModelEvent;
import engine.sprite.Sprite;

/*
 * Why do you need a "SpriteBuilder" and a "SriteBuildingManager"?
 * Can you make them one thing, or name them more sensibly so that
 * people could know what to use when creating a sprite only from 
 * the class names (and public methods).
 */

public class SpriteBuildingManager {
	private EventBus bus;

	public SpriteBuildingManager(EventBus bus) {
		this.bus = bus;
	}
	
	public SpriteBuildingManager(){
		this.bus = new BasicEventBus();
	}

	public void createSprite(AttributeData spriteData) {
		SpriteBuilder SB = new SpriteBuilder(spriteData);
		Sprite s = SB.getSprite();
		
		if(checkIfTile(spriteData))
			addTileToModel(s);
		else{
			System.out.println(s.getHealthHolder().isPresent() + " for HealthHolder");
			System.out.println(s.getAttacker().isPresent() + " for Attacker");
			System.out.println(s.getTeamMember().isPresent() + " for TeamMember");
			addSpriteToModel(s);
		}
	}
	
	private boolean checkIfTile(AttributeData data){
		return data.getAttributes().size() == 1;
	}
	
	private void addTileToModel(Sprite sprite) {
		bus.emit(new TileModelEvent(TileModelEvent.ADD, sprite));
	}

	private void addSpriteToModel(Sprite sprite){
		bus.emit(new SpriteModelEvent(SpriteModelEvent.ADD, sprite));
	}
}
