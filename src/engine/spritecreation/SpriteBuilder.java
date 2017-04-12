package engine.spritecreation;

import java.util.List;

import engine.sprite.Sprite;
import engine.sprite.images.ImageSet;
import engine.sprite.images.LtubImage;
import data.AttributeData;

/*
 * I just made a keping.SpriteBuilder without reflection. 
 * Compare to see whether we should use reflection or not.
 * 
 * The pro(s) of reflection:
 * 1) We don't have to modify the SpriteBuilder when we 
 * have new attributes.
 * The cons of reflection:
 * 1) It renders the code unreadable.
 * 2) More error-prone and harder to debug. Basically we
 * have to deal with string a lot, and we have to be very 
 * careful about all file paths (when we move things around,
 * these hard coded paths don't get changed by eclipse).
 * 3) Unsafe. You could try to set an attribute that is 
 * not existing in sprite. Yes we can catch run time exceptions,
 * but isn't it better if we avoid the problem at compile time.
 * 
 * The advantage of reflection will only show up when we 
 * have an enormous amount of attributes. But we won't and shouldn't
 * have an enormous amount of attributes:
 * 1) I don't want the sprite to have an enormous amount of set 
 * and get methods.
 * 2) The attributes should be organized hierarchically and one 
 * attribute could contain much information.
 * 3) The fun of a real game is not about the number 
 * of attributes, but the multiple choices within each attribute,
 * and the combination of the options from different attributes.
 *  
 */

/**
 * CITE CODE
 * @author Tahia Emran (tse5) Matthew Tribby (mrt28)
 */
public class SpriteBuilder {
	private AttributeData myData; 
	private Sprite mySprite; 

	public SpriteBuilder(AttributeData spriteData){
		this.myData = spriteData; 
		mySprite = new Sprite(); 
		createAttributes();
	}

	private void createAttributes() {		  
		List<AttributeData> compositionAttributes = myData.getAttributes(); 
		
		for (AttributeData att : compositionAttributes){
			if(!att.getName().equals("NodeHolder")){
				AttributeBuilder AB = new AttributeBuilder(att);
				AB.configSprite(mySprite);
			}else{ // TODO: temporary, need to better integrate
				System.out.println("image set");
				mySprite.setImageSet(new ImageSet(new LtubImage("images/characters/" + att.getVariable("filepath"))));
			}
		}
	}
	
	/*
	 * If you want to build a sprite from AttributeData,
	 * you might want something like the following:
	 */
//	public SpriteBuilder() { }
//	public Sprite buildSprite(AttributeData spriteData) {
//		Sprite sprite = new Sprite();
//		configureAttributes(sprite);
//		return sprite;
//	}
	
	public Sprite getSprite(){
		return mySprite; 
	}



}

