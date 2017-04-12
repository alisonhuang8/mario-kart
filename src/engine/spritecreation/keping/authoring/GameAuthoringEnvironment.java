package engine.spritecreation.keping.authoring;

import java.util.ArrayList;
import java.util.List;

import engine.spritecreation.keping.GameConfig;
import engine.spritecreation.keping.PlayerConfig;
import engine.spritecreation.keping.SpriteConfig;

public class GameAuthoringEnvironment {
	
	public List<SpriteConfig> spriteConfigs = new ArrayList<>();
	
	private PlayerConfig playerConfig;
	

	public SpriteConfig loadSpritePreset(String spritePresetName)  {
		return null;
	}
	public void setSpritePreset(String spritePresetName, SpriteConfig spriteConfig) {
		
	}
	
	public void saveSpriteConfig(SpriteConfig spriteConfig, String filename) {
		
	}
	
	public GameConfig getGameConfig() {
		return new GameConfig(playerConfig, spriteConfigs);
	}
	
	public void saveConfigToFile(String filename) {
		// save(filename, getGameConfig())
	}
	
	
	// change the game configs
	public void onClickSprite(SpriteConfig spriteConfig) {
		(new SpriteConfigModifier(spriteConfig)).popUp();
	}
	public void onPutSpriteOnMap(String spritePresetName) {
		spriteConfigs.add(loadSpritePreset(spritePresetName));
	}
		
	public static void main(String[] args) {
		GameAuthoringEnvironment author = new GameAuthoringEnvironment();
		// when user click to put a sprite on the map
		author.onPutSpriteOnMap("sprite preset 1");
		// when user click an existing spirte to modify its attributes
		author.onClickSprite(null);
		// when every thing finishes and the whole game config gets saved
		author.saveConfigToFile("mapConfig.ltub");
	}

}
