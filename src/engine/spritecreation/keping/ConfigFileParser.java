package engine.spritecreation.keping;

public class ConfigFileParser {
	
	public GameConfig parse(ConfigFile configFile) {
		// TODO PLEASE READ:
		// "ConfigFile" class could just actually just be a file name.
		// A good thing about using XStream is that we can write object
		// to file and read file to construct object without specifying 
		// the specific form of the file.
		// So that as long as this "GameBuidler" and our game authoring 
		// environment is using the same "...Config" classes, we will be
		// able to modify "...Config" using game authoring environment,
		// then save it and later load it.
		// Inside this ConfigFile we should have a hierarchical layout of
		// different config files.
		return null;
	}

}
