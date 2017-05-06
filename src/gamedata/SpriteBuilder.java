package gamedata;

import java.util.List;

import data.SpriteMakerModel;
import newengine.sprite.Sprite;
import newengine.sprite.component.Component;
import newengine.sprite.components.Cooldown;
import newengine.sprite.components.EventQueue;
import newengine.sprite.components.GameBus;
import newengine.sprite.components.Images;
import newengine.sprite.components.Position;

/**
 * @author tahiaemran
 *
 *This class is part of my masterpiece. 
 *
 *This code uses the Builder design pattern to construct sprites from the provided sprite maker model. It is the concrete implementation 
 *of the SpriteConstructor interface, which defines the API for Sprite Construction. 
 *
 * This class is used to construct a single sprite from a SpriteMakerModel, but one instance of it can be used to construct as 
 * many sprites as needed by passing in different SpriteMakerModels to the getConstructedSprite() method. 
 * 
 *
 */
public class SpriteBuilder implements SpriteConstructor{

	private int numRows; 
	private int numCols; 

	/**
	 * @param numRows - number of rows defined by the user, needed to scale the position of the sprite being created 
	 * @param numCols - number of columns defined by the user, needed to scale the position of the sprite being created
	 * 
	 */
	public SpriteBuilder(int numRows, int numCols) {
		this.numRows = numRows; 
		this.numCols = numCols;
	}

	/**
	 * @param model - the SpriteMakerModel used to construct a given sprite
	 * @return the Sprite object constructed from the model
	 */
	/* (non-Javadoc)
	 * @see gamedata.SpriteConstructor#getConstructedSprite(data.SpriteMakerModel)
	 */
	@Override
	public Sprite getConstructedSprite(SpriteMakerModel model){
		return construct(model);
	}

	
	private Sprite construct(SpriteMakerModel model) {
		Sprite constructedSprite = new Sprite(); 
		constructedSprite = handleComponents(model.getActualComponents());
		constructedSprite.addComponent(new GameBus());
		constructedSprite.addComponent(new Cooldown());
		return constructedSprite;
	}


	private Sprite handleComponents(List<Component> transferComponents) {
		Sprite sprite = new Sprite(); 
		transferComponents.add(0, new EventQueue());
		sprite.addComponent(new Position(100,200,0));
		sprite.addComponent(new EventQueue());
		transferComponents.stream().forEach( c->{
			if(c.getType().equals(Position.TYPE)){
				Position newPos = scalePosition((Position)c);
				sprite.addComponent(newPos);
			}

		});
		transferComponents.stream().forEach(comp-> {
			if (comp.getType().equals(Images.TYPE)){
				sprite.addComponent(comp);
			}
		});

		transferComponents.stream().forEach(component ->{
			sprite.addComponent(component);
		});

		return sprite;

	}

	private Position scalePosition(Position position) {
		double xPerc = position.pos().x();
		double yPerc = position.pos().y();
		double xPixel = xPerc * 100 * numCols;
		double yPixel = yPerc * 100 * numRows;
		return new Position(xPixel, yPixel, position.heading());

	}

}
