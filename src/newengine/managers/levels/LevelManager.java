package newengine.managers.levels;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import bus.EventBus;
import data.SpriteMakerModel;
import gamecreation.level.ILevelData;
import gamedata.AuthDataTranslator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import newengine.events.SpriteModelEvent;
import newengine.events.conditions.EndConditionTriggeredEvent;
import newengine.events.conditions.SetEndConditionEvent;
import newengine.events.game.GamePauseResumeEvent;
import newengine.events.levels.InitILevelsEvent;
import newengine.events.levels.SetLevelEvent;
import newengine.events.levels.WinGameEvent;
import newengine.sprite.Sprite;
import player.winnerorloser.LosePresentation;
import player.winnerorloser.ResultAccessor;
import player.winnerorloser.WinPresentation;
import utilities.CustomAlert;

//This entire file is part of my masterpiece.
//Matthew Tribby

/**
 * The goal of this class is to act as the hub for switching levels in any genre of game. It was intentionally built
 * to not be tied to any genre. In order to work it needs to take in ILevelData which is an interface defining the
 * characteristics of a level. This level manager is well designed because it has a very clean API and all the work
 * it does to load the levels are completely encapsulated. It can also act without anyone having a reference to it
 * because of our event-based system. 
 * @author Matthew Tribby
 */
public class LevelManager implements ILevelManager{
	private EventBus bus;
	private List<ILevelData> data;
	private int numLevels;
	private int currentLevel;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	public static final String RESOURCE_FILE_NAME = "engine";
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + RESOURCE_FILE_NAME);

	
	/**
	 * Creates a LevelManager with an event bus used to connect to event based system. Also the constructor calls
	 * to initializes the handlers so it is in sync with the event-based system.
	 * @param bus
	 */
	public LevelManager(EventBus bus){
		this.bus = bus;
		initHandlers();
	}
	
	private void initLevels(List<ILevelData> levelDataList) {
		this.data = levelDataList;
		this.currentLevel = 1;
		if(data != null){
			this.numLevels = data.size();
			loadLevel(data.get(0));
		}
	}
	
	private void initHandlers() {
		bus.on(InitILevelsEvent.ANY, e -> {
			initLevels(e.getLevelDataList());
		});
		bus.on(EndConditionTriggeredEvent.WIN, e -> {if(data!= null) nextLevel();});
		bus.on(EndConditionTriggeredEvent.LOSE, e -> {
			new LosePresentation().show(new ResultAccessor());
			bus.emit(new GamePauseResumeEvent());
			});
		bus.on(SetLevelEvent.SET, e -> {if(data!= null) setLevel(e.getLevelNumber());});
	}

	/**
	 * Increases the level by one and makes a call to end game if there are no more levels. If there are more
	 * levels it will call to load in the next level
	 */
	public void nextLevel(){
		if(!(currentLevel == numLevels)){
			Alert winAlert = new CustomAlert(AlertType.CONFIRMATION, myResources.getString("beatLevel"));
			winAlert.setOnCloseRequest(e -> bus.emit(new GamePauseResumeEvent()));
			winAlert.show();
			bus.emit(new GamePauseResumeEvent());
			currentLevel++;
			loadLevel(data.get(currentLevel-1));
			return;
		}
			new WinPresentation().show(new ResultAccessor());;
			bus.emit(new WinGameEvent(WinGameEvent.WIN));
			bus.emit(new GamePauseResumeEvent());
	}
	
	/**
	 * Loads in the current level again, thus resetting this level
	 */
	public void resetLevel(){
		loadLevel(data.get(currentLevel-1));
	}
	
	/**
	 * Sets the current level to the level corresponding to the integer passed in if that level is present
	 */
	public void setLevel(int levelNumber){
		if(levelNumber > 0 && levelNumber <= numLevels){
			loadLevel(data.get(levelNumber-1));
		}
		new CustomAlert(AlertType.ERROR, myResources.getString("levelNum") + levelNumber + myResources.getString("notPresent")).show();
	}
	
	private void loadLevel(ILevelData newLevel){
		bus.emit(new SetEndConditionEvent(SetEndConditionEvent.SETWIN, newLevel.getWinningCondition()));
		bus.emit(new SetEndConditionEvent(SetEndConditionEvent.SETLOSE, newLevel.getLosingCondition()));
		
		List<SpriteMakerModel> spriteMakerModels = newLevel.getSpawners();
		List<Sprite> sprites = new ArrayList<>();
		sprites.addAll(spriteMakerModels.stream().map((spriteMakerModel) -> {
			return (new AuthDataTranslator(spriteMakerModel)).getSprite();
		}).collect(Collectors.toList()));
		bus.emit(new SpriteModelEvent(SpriteModelEvent.ADD, sprites));
		
		if(newLevel.getShowPath()){
			PathCreator pathCreator = new SimplePathCreator(bus);
			pathCreator.createPath(spriteMakerModels);
		}
	}
}
