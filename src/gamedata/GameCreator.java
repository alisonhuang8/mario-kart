package gamedata;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import bus.EventBus;
import data.SerializableDeveloperData;
import data.SpriteMakerModel;
import gamecreation.level.ILevelData;
import newengine.app.Game;
import newengine.events.SpriteModelEvent;
import newengine.events.player.MainPlayerEvent;
import newengine.events.stats.ChangeLivesEvent;
import newengine.events.stats.ChangeWealthEvent;
import newengine.model.PlayerStatsModel.WealthType;
import newengine.skill.skills.BuildSkill;
import newengine.sprite.Sprite;
import newengine.sprite.components.Owner;
import newengine.sprite.components.SkillSet;
import player.helpers.GameLoadException;
import utilities.XStreamHandler;

/**
 * @author tahiaemran, Keping
 *
 * class that creates a game from an XML file of translated GameData 
 *
 */
public class GameCreator {
	File fileToRead; 
	XStreamHandler XSH; 
	//XStream xstream; 
	
	
	//TODO: jake and matt, resource file if possible!!!
	
	private static final String NUMBER_OF_LIVES = "NUM_LIVES";
	private static final String BUILD_TOWER = "BUILD_IN_GAME";
	private static final String NUMBER_OF_STARTING_GOLD = "NUM_GOLD";
	private static final String LEVEL_COMPLETION_BONUS = "LEVEL_COMPLETION";
	private static final String GAME_NAME = "GAME_NAME";
	private static final String GAME_ICON = "GAME_ICON";
	
	
	private Game game; 

	private XStream xstream; 
	
	public GameCreator() {
		xstream = new XStream(new DomDriver());
	}
	
	public Game createGame(String gameFilePath) throws GameLoadException {
		return createGame(new File(gameFilePath));
	}
	public Game createGame(File gameFile) throws GameLoadException {
		try {

			// Read out the game 
			SerializableDeveloperData gameData = (SerializableDeveloperData) xstream.fromXML(gameFile);
			SkillSet skillSet = (SkillSet) gameData.getLevels().get(0).getSpawners().get(0).getComponentByType(SkillSet.TYPE);
			BuildSkill bSkill = (BuildSkill) skillSet.getSkill(BuildSkill.TYPE);
			System.out.println(bSkill.getModel().getName());
			//Process Levels
			List<ILevelData> levels = gameData.getLevels();
			System.out.println(levels.get(0).getName());
			//Process Sprites 
			List<SpriteMakerModel> initialSpriteModels =  gameData.getSprites();
			System.out.println(initialSpriteModels);
			TranslationController translator = new TranslationController(initialSpriteModels); 
			translator.translate();
			List<Sprite> createdSprites = (List<Sprite>) translator.getTranslated();  
			
			Game game = new Game(); 
			EventBus bus = game.getBus();
			bus.emit(new SpriteModelEvent(SpriteModelEvent.ADD, createdSprites));
			
			
//			// TODO: figure this part out 
//			bus.emit(new MainPlayerEvent(createdSprites.get(0).getComponent(Owner.TYPE).get().player()));
//			bus.emit(new ChangeLivesEvent(ChangeLivesEvent.SET, 
//					createdSprites.get(0).getComponent(Owner.TYPE).get().player(), 
//					gameData.getStartingLives()));
//			bus.emit(new ChangeWealthEvent(ChangeWealthEvent.CHANGE,
//					createdSprites.get(0).getComponent(Owner.TYPE).get().player(),
//					WealthType.GOLD, gameData.getStartingGold()));
//			
			return game;
		} catch (Exception e) {
			throw new GameLoadException("Fail to load game: "+gameFile);
		}
	}

	private void createGame() {
		// Read out the game 
		SerializableDeveloperData gameData = (SerializableDeveloperData) xstream.fromXML(fileToRead);
		System.out.println(gameData.getSprites().size());
		//Process Levels
		List<ILevelData> levels = gameData.getLevels();
		System.out.println(levels.get(0).getName());
		
		game = new Game(gameData.getLevels()); 
		EventBus bus = game.getBus();
		//Process Sprites 
		List<SpriteMakerModel> initialSpriteModels =  gameData.getSprites();
		AuthDataTranslator translator =new AuthDataTranslator(initialSpriteModels, gameData.getNumCols(), gameData.getNumRows());
		translator.translate();
		List<Sprite> createdSprites = (List<Sprite>) translator.getTranslated();  
	
		bus.emit(new SpriteModelEvent(SpriteModelEvent.ADD, createdSprites));

		
		Map<String,String> generalData = gameData.getGameData();
		
		
		// TODO: figure this part out 
		bus.emit(new MainPlayerEvent(createdSprites.get(0).getComponent(Owner.TYPE).get().player()));
		bus.emit(new ChangeLivesEvent(ChangeLivesEvent.SET, 
				createdSprites.get(0).getComponent(Owner.TYPE).get().player(), 
				Integer.parseInt(generalData.get(NUMBER_OF_LIVES))));
		bus.emit(new ChangeWealthEvent(ChangeWealthEvent.CHANGE,
				createdSprites.get(0).getComponent(Owner.TYPE).get().player(),
				WealthType.GOLD, Integer.parseInt(generalData.get(NUMBER_OF_STARTING_GOLD))));
		
	}

}
