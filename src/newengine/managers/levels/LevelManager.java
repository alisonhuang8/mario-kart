package newengine.managers.levels;

import java.util.ArrayList;
import java.util.List;

import bus.EventBus;
import gamecreation.level.ILevelData;
import gamedata.AuthDataTranslator;
import newengine.events.SpriteModelEvent;
import newengine.events.conditions.EndConditionTriggeredEvent;
import newengine.events.conditions.SetEndConditionEvent;
import newengine.sprite.Sprite;
import newengine.sprite.components.Spawner;

public class LevelManager{
	private EventBus bus;
	private List<ILevelData> data;
	private int numLevels;
	private int currentLevel;
	
	public LevelManager(EventBus bus, List<ILevelData> data){
		this.bus = bus;
		this.data = data;
		this.currentLevel = 1;
		if(data != null){
			this.numLevels = data.size();
			loadLevel(data.get(0));
		}
		initHandlers();
	}
	
	private void initHandlers() {
		bus.on(EndConditionTriggeredEvent.WIN, e -> {if(data!= null) nextLevel();});
		bus.on(EndConditionTriggeredEvent.LOSE, e -> System.out.println("loser"));
		bus.on(SetLevelEvent.SET, e -> {if(data!= null) setLevel(e.getLevelNumber());});
	}

	public void nextLevel(){
		if(!(currentLevel == numLevels)){
			currentLevel++;
			System.out.println("Current level: " + currentLevel);
			loadLevel(data.get(currentLevel-1));
			return;
		}
			bus.emit(new WinGameEvent(WinGameEvent.WIN));
	}
	
	public void resetLevel(){
		loadLevel(data.get(currentLevel-1));
	}
	
	public void setLevel(int levelNumber){
		if(levelNumber > 0 && levelNumber <= numLevels){
			loadLevel(data.get(levelNumber-1));
		}
	}
	
	private void loadLevel(ILevelData newLevel){
		bus.emit(new SetEndConditionEvent(SetEndConditionEvent.SETWIN, newLevel.getWinningCondition()));
		bus.emit(new SetEndConditionEvent(SetEndConditionEvent.SETLOSE, newLevel.getLosingCondition()));
		
		List<Sprite> spritesToAdd = new ArrayList<Sprite>();
		newLevel.getSpawners().stream().forEach(e -> {
			AuthDataTranslator translator = new AuthDataTranslator(e);
			System.out.println("SPAWNER" + translator.getSprite().getID());
			spritesToAdd.add(translator.getSprite());
		});
		
		spritesToAdd.stream().forEach(e -> e.getComponent(Spawner.TYPE).get().setSpawnTime(newLevel.getSpawnTime()));
		bus.emit(new SpriteModelEvent(SpriteModelEvent.ADD, spritesToAdd));
	}

}
