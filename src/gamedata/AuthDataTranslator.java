package gamedata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import data.SpriteMakerModel;
import javafx.collections.ObservableList;
import newengine.sprite.Sprite;


/**
 * @author tahiaemran
 * 
 * This class is part of my masterpiece. 
 * 
 * This class is the concrete implementation of the Translator interface used to make Sprites from 
 * SpriteMaker models within the TranslationController. It is the vehicle through which the functionality of 
 * the actual process proceeds. This class also serves as the Director class within the Builder pattern that 
 * is used in the creation of Sprites. 
 * 
 * 
 * Class used to translate data from the authoring environment to the game environment 
 * Takes in a list of sprite maker models and translates them into sprites
 * Can also take in a single sprite maker model and translate it to a sprite
 *  To use this class: instantiate the class and either set it as a translator in the TranslationController or use it alone 
 *  and call the .translate() and .getTranslated() methods. 
 *
 */
public class AuthDataTranslator implements Translator<Sprite> {

	private List<SpriteMakerModel> spritesToMake;
	private SpriteBuilder spriteConstructor; 
	
	private int numRows = 8;
	private int numCols = 8;

	private List<Sprite> constructedSprites = new ArrayList<Sprite>();

	@Deprecated
	public AuthDataTranslator(ObservableList<SpriteMakerModel> allObjectsOnScreen) {
		spritesToMake = new ArrayList<SpriteMakerModel>(allObjectsOnScreen);
	}

	public AuthDataTranslator(List<SpriteMakerModel> spriteData) {
		spritesToMake = spriteData;
	}

	public AuthDataTranslator(SpriteMakerModel spriteToMake, int numCols, int numRows) {
		this.numRows = numRows;
		this.numCols = numCols;
		spritesToMake = new ArrayList<SpriteMakerModel>();
		spritesToMake.add(spriteToMake);	
		spriteConstructor = new SpriteBuilder(numRows, numCols);
		}

	public AuthDataTranslator(SpriteMakerModel spriteToMake) {
		spritesToMake = new ArrayList<SpriteMakerModel>();
		spritesToMake.add(spriteToMake);
		spriteConstructor = new SpriteBuilder(numRows, numCols);

	}

	/* (non-Javadoc)
	 * @see gamedata.Translator#translate()
	 */
	@Override
	public void translate() {
		spritesToMake.stream().forEach(model -> {
			constructedSprites.add(spriteConstructor.getConstructedSprite(model));
		});
	}

	/* (non-Javadoc)
	 * @see gamedata.Translator#getTranslated()
	 */
	@Override
	public List<Sprite> getTranslated() {
		return Collections.unmodifiableList(constructedSprites);
	}

}