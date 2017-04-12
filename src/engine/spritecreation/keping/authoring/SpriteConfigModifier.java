package engine.spritecreation.keping.authoring;

import engine.spritecreation.keping.SpriteConfig;

public class SpriteConfigModifier {

	private SpriteConfig spriteConfig;
	
	public SpriteConfigModifier(SpriteConfig spriteConfig) {
		this.spriteConfig = spriteConfig;
	}
	
	public void popUp() {
		
	}
	
	public void save() {}
	public void saveToPreset(String presetName) {}

	// TODO: here the user could change the attributes too
	// TODO: we want to distinguish the changes on preset and
	// on the instances
	
}
