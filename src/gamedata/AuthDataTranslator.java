package gamedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bus.BasicEventBus;
import data.SpriteMakerModel;
import javafx.collections.ObservableList;
import newengine.events.skill.AddSkillEvent;
import newengine.skill.Skill;
import newengine.sprite.Sprite;
import newengine.sprite.component.Component;
import newengine.sprite.component.ComponentType;
import newengine.sprite.components.Cooldown;
import newengine.sprite.components.EventQueue;
import newengine.sprite.components.GameBus;
import newengine.sprite.components.Images;
import newengine.sprite.components.Position;
import newengine.sprite.components.SkillSet;

/**
 * @author tahiaemran
 * 
 *         Class used to translate data from the authoring environment to the
 *         game environment Takes in a list of sprite maker models and
 *         translates them into sprites
 *
 */
public class AuthDataTranslator implements Translator<Sprite> {

	// TODO: make a single translator for single sprite translatino
	// TODO: instantiate skills

	private List<SpriteMakerModel> spritesToMake;
	private BasicEventBus gameBus = new BasicEventBus();
	private int numRows = 8;
	private int numCols = 8;
	private boolean toAdd = true;

	private List<Sprite> constructedSprites = new ArrayList<Sprite>();

	private Sprite constructed;

	@Deprecated
	public AuthDataTranslator(ObservableList<SpriteMakerModel> allObjectsOnScreen) {
		spritesToMake = new ArrayList<SpriteMakerModel>(allObjectsOnScreen);
	}

	@Deprecated
	public AuthDataTranslator(List<SpriteMakerModel> allObjectsOnScreen) {
		spritesToMake = new ArrayList<SpriteMakerModel>(allObjectsOnScreen);
	}

	@Deprecated
	public AuthDataTranslator(List<SpriteMakerModel> spriteData, int numCols, int numRows) {
		this.numRows = numRows;
		this.numCols = numCols;
		spritesToMake = spriteData;
	}

	public AuthDataTranslator(SpriteMakerModel spriteToMake, int numCols, int numRows) {
		this.numRows = numRows;
		this.numCols = numCols;
		spritesToMake = new ArrayList<SpriteMakerModel>();
		spritesToMake.add(spriteToMake);
		makeSingleSprite(spriteToMake);
	}

	public AuthDataTranslator(SpriteMakerModel spriteToMake) {
		spritesToMake = new ArrayList<SpriteMakerModel>();
		spritesToMake.add(spriteToMake);
		makeSingleSprite(spriteToMake);
	}

	private void makeSingleSprite(SpriteMakerModel spriteToMake) {
		spriteToMake.getActualComponents().stream().forEach(e -> {
			System.out.println("WHAT THE FUCK IS IN THIS LIST!! " + e.getType().getType());
		});
		constructed = handleComponents(spriteToMake.getActualComponents());
		constructed.addComponent(new GameBus());
		//		constructed.addComponent(new EventQueue(new LinkedList<>()));
		constructed.addComponent(new Cooldown());

	}

	public Sprite getSprite() {
		System.out.println("constructed: " + constructed);
		return constructed;
	}

	@Override
	public void translate() {
		spritesToMake.stream().forEach(model -> {
			Sprite newSprite = handleComponents(model.getActualComponents());
			// skills
			Sprite skilledSprite = handleSkills(newSprite, model.getSkills());
			constructedSprites.add(skilledSprite);
			/// triggers
			// constructedSprites.add(handleEventHandlers(skilledSprite,
			/// model.getScriptMap()));
		});
	}

	private Sprite handleSkills(Sprite sprite, List<Skill> skills) {
		skills.stream().forEach(skill -> sprite.emit(new AddSkillEvent(AddSkillEvent.TYPE, skill)));
		return sprite;
	}



	public List<Sprite> getSprites() {
		return constructedSprites;
	}

	private Sprite handleComponents(List<Component> transferComponents) {
		
		Sprite sprite = new Sprite(); 
		transferComponents.add(0, new EventQueue());
		System.out.println("constructed in translate: " + sprite);
		sprite.addComponent(new Position(100,200,0));
		sprite.addComponent(new EventQueue());
		transferComponents.stream().forEach( c->{
			if(c.getType().equals(Position.TYPE)){
				Position position = (Position)c;
				double xPerc = position.pos().x();
				double yPerc = position.pos().y();
				double xPixel = xPerc * 100 * numCols;
				double yPixel = yPerc * 100 * numRows;
				Position newPosition = new Position(xPixel, yPixel, position.heading());
				sprite.addComponent(newPosition);
			}
		});
		for (Component comp: transferComponents){
			//System.out.println(comp.getType().getType());
			if (comp.getType().equals(Images.TYPE)){
				sprite.addComponent(comp);
			}
		}
		
		Map<ComponentType<? extends Component>, Component> checked = new HashMap<ComponentType<? extends Component>, Component>();
		
		transferComponents.stream().forEach(e ->{
			checked.put(e.getType(), e);
		});
		
		
		transferComponents.stream().forEach(component -> {			
//			if (component.getType() == SkillSet.TYPE && toAdd){
//				toAdd = false;
//				sprite.addComponent(component);
//			} else if(component.getType() != SkillSet.TYPE){
			
			sprite.addComponent(component);
//			}
			
		});

		checked.values().stream().forEach(e -> {
			System.out.println(e + " COMPONENTS!!!!!" + sprite.getID());
		});
		
		return sprite;

	}

	@Override
	public List<Sprite> getTranslated() {
		return constructedSprites;
	}

}