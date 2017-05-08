package gamedata;

import java.util.List;

import data.SpriteMakerModel;
import newengine.sprite.Sprite;

/**
 * @author tahiaemran
 *
 * Class used to translate data from the game engine to data for the authoring environment
 *
 */
public class EngineDataTranslator implements Translator{
	
	
	
	List<Sprite> spritesToBreak; 
	
	
	List<SpriteMakerModel> spriteBones; 
	
	public EngineDataTranslator(List<Sprite> sprites){
		spritesToBreak = sprites; 
	}

	@Override
	public void translate() {
		// for each sprite 
		// get its components 
		
		// for each component 
		// disassemble the component by into string (type) and get its constructor from the resource file 
		
	}

	@Override
	public List<?> getTranslated() {
		// TODO Auto-generated method stub
		return null;
	}


	}

