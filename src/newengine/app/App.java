package newengine.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import bus.EventBus;
import commons.point.GamePoint;
import data.SerializableDeveloperData;
import data.SpriteMakerModel;
import gameDevelopmentInterface.Path;
import gamedata.AuthDataTranslator;
import javafx.application.Application;
import javafx.stage.Stage;
import newengine.events.GameInitializationEvent;
import newengine.events.SpriteModelEvent;
import newengine.events.conditions.SetEndConditionEvent;
import newengine.events.game.StartLevelEvent;
import newengine.events.player.MainPlayerEvent;
import newengine.events.stats.ChangeLivesEvent;
import newengine.events.stats.ChangeWealthEvent;
import newengine.events.timer.PeriodicEvent;
import newengine.managers.conditions.GoldMinimumCondition;
import newengine.managers.conditions.NoLivesCondition;
import newengine.model.PlayerStatsModel.WealthType;
import newengine.player.Player;
import newengine.skill.Skill;
import newengine.skill.SkillType;
import newengine.skill.skills.BuildSkill;
//import newengine.skill.skills.BuildSkillFactory;
import newengine.skill.skills.FireProjectileSkill;
import newengine.skill.skills.MoveSkill;
import newengine.sprite.Sprite;
import newengine.sprite.components.Cooldown;
import newengine.sprite.components.EventQueue;
import newengine.sprite.components.GameBus;
import newengine.sprite.components.Health;
import newengine.sprite.components.Images;
import newengine.sprite.components.Owner;
import newengine.sprite.components.PathFollower;
import newengine.sprite.components.Position;
import newengine.sprite.components.Selectable;
import newengine.sprite.components.Selectable.SelectionBoundType;
import newengine.sprite.components.SkillSet;
import newengine.sprite.components.Spawner;
import newengine.sprite.components.Speed;
import utilities.XStreamHandler;

public class App extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		

		Player player1 = new Player("Player 1");
		//Player player1 = new Player("Player 1");


		XStreamHandler xHandler = new XStreamHandler();
		XStream xStream = new XStream(new DomDriver());
		List<Sprite> listSprites = new ArrayList<Sprite>();// = translator.getSprites();
		SerializableDeveloperData sdd = (SerializableDeveloperData) xHandler.getObjectFromFile();
		AuthDataTranslator translator = new AuthDataTranslator(sdd.getLevels().get(0).getSpawners().get(0));
		//listSprites.add(translator.getSprite());
		System.out.println(sdd.getLevels().get(0).getSpawners().get(0) != null);
		Game game = new Game(); 
		EventBus bus = game.getBus();
		//bus.emit(new SpriteModelEvent(SpriteModelEvent.ADD, createdSprites));
		//List<SpriteMakerModel> spriteModelsFromFile = xHandler.getScreenModelFile();
//		
//
////		for (SpriteMakerModel smm : spriteModelsFromFile) {
////			Position imageComponent = (Position) smm.getComponentByType(Position.TYPE);
////			//System.out.println(imageComponent.pos().x() + " " + imageComponent.pos().y());
////		}
////		
////		AuthDataTranslator translator = new AuthDataTranslator(spriteModelsFromFile);
//		//translator.translate();
//		List<Sprite> listSprites = new ArrayList<Sprite>();// = translator.getSprites();
//
//		Game game = new Game();
//		EventBus bus = game.getBus();
//		//List<Sprite> listSprites = spriteModel.getSprites();
//		//System.out.println(listSprites.size());
//		for (Sprite s : listSprites) {
//			//System.out.println(s.getComponent(SkillSet.TYPE).get().skills());
//		}
//		
//		Sprite towerBuilder = new Sprite(new SpriteID("__TOWER_BUILDER"));
//		towerBuilder.addComponent(new GameBus());
//		towerBuilder.addComponent(new Owner(player1));
//		towerBuilder.addComponent(createTowerBuilderSkillSet(player1));
//		towerBuilder.addComponent(new Images("images/characters/bahamut_right.png"));
//		listSprites.add(towerBuilder);
//		
//		
//		Sprite spawner = new Sprite();
//		Map<SkillType<? extends Skill>, Skill> skillMap1 = new HashMap<>();
//		skillMap1.put(MoveSkill.TYPE, new MoveSkill());
//		//skillMap1.put(BuildSkill.TYPE, new BuildSkill(building));
//		FireProjectileSkill fireSkill1 = new FireProjectileSkill();
//		fireSkill1.setCooldown(3); // add cooldown to the fireProjectilSkill
//		spawner.addComponent(new Cooldown());
//		skillMap1.put(FireProjectileSkill.TYPE, fireSkill1);
//		spawner.addComponent(new GameBus());
//		spawner.addComponent(new Owner(player1));
//		spawner.addComponent(new Position(new GamePoint(200, 100), 0));
//		spawner.addComponent(new SoundEffect("data/sounds/Psyessr4.wav"));
//		spawner.addComponent(new Images("images/characters/tower2_resized.gif"));
//		spawner.addComponent(new Speed(200));
//		spawner.addComponent(new Collidable(CollisionBoundType.IMAGE));
//		spawner.addComponent(new Selectable(SelectionBoundType.IMAGE));
//		spawner.addComponent(new Range(200));
//		spawner.addComponent(new Attacker());
//		spawner.addComponent(new Health(200));
//		spawner.addComponent(new EventQueue(new LinkedList<>()));
//		spawner.addComponent(new RangeShootingAI());
//		Queue<SpriteMakerModel> toAdd = new LinkedList<SpriteMakerModel>();
//		//toAdd.add(building);
//		spawner.addComponent(new SkillSet(skillMap1));
//
//		SpriteMakerModel building = new SpriteMakerModel();
//		Map<SkillType<? extends Skill>, Skill> monsterMap = new HashMap<>();
//		monsterMap.put(MoveSkill.TYPE, new MoveSkill());
//		building.addComponent(new GameBus());
//		building.addComponent(new SkillSet(monsterMap));
//		building.addComponent(new Owner(player1));
//		building.addComponent(new Images("images/characters/bahamut_right.png"));
//		building.addComponent(new Speed(100));
//		building.addComponent(new Health(100));
//		building.addComponent(new EventQueue(new LinkedList<>()));
//		building.addComponent(new PathFollower(new Path()));
//		
//		
		SpriteMakerModel child = new SpriteMakerModel();
		Map<SkillType<? extends Skill>, Skill> childSkillMap = new HashMap<>();
		childSkillMap.put(MoveSkill.TYPE, new MoveSkill());
		child.addComponent(new GameBus());
		child.addComponent(new SkillSet(childSkillMap));
		child.addComponent(new Owner(player1));
		child.addComponent(new Images("images/skills/build.png"));
		child.addComponent(new Speed(500));
		child.addComponent(new Health(100));
		child.addComponent(new EventQueue(new LinkedList<>()));
		child.addComponent(new PathFollower(new Path()));
		child.addComponent(new Position(0.1,0.1, 0));

		
		
		Sprite sprite2 = new Sprite();
		Map<SkillType<? extends Skill>, Skill> skillMap2 = new HashMap<>();
		skillMap2.put(MoveSkill.TYPE, new MoveSkill());
		skillMap2.put(BuildSkill.TYPE, new BuildSkill(child));
		FireProjectileSkill fireSkill2 = new FireProjectileSkill();
		fireSkill2.setCooldown(3); // add cooldown to the fireProjectilSkill
		sprite2.addComponent(new Cooldown());
		skillMap2.put(FireProjectileSkill.TYPE, fireSkill2);
		sprite2.addComponent(new GameBus());
		sprite2.addComponent(new SkillSet(skillMap2));
		sprite2.addComponent(new Owner(player1));
		sprite2.addComponent(new Position(new GamePoint(100, 100), 0));
		sprite2.addComponent(new Spawner(10, new Path(), 0.1));


		
		
		listSprites.add(sprite2);
		
		
		bus.on(GameInitializationEvent.ANY, (e) -> {
			//bus.emit(new SoundEvent(SoundEvent.BACKGROUND_MUSIC, "data/sounds/01-dark-covenant.mp3"));
			bus.emit(new SpriteModelEvent(SpriteModelEvent.ADD, listSprites));
			bus.emit(new MainPlayerEvent(player1));
			bus.emit(new ChangeLivesEvent(ChangeLivesEvent.SET, player1, 3));
			bus.emit(new ChangeWealthEvent(ChangeWealthEvent.CHANGE, player1, WealthType.GOLD, 100));
			bus.emit(new SetEndConditionEvent(SetEndConditionEvent.SETWIN, new GoldMinimumCondition(1000)));
			bus.emit(new SetEndConditionEvent(SetEndConditionEvent.SETLOSE, new NoLivesCondition()));
			bus.emit(new PeriodicEvent(10, 5.0, () -> new StartLevelEvent(StartLevelEvent.START)));
		});
		
		stage.setScene(game.getScene());
		game.start();
		stage.show();
		
	}
	
	
	private SpriteMakerModel createSampleSpriteMakerModel(Player player, String imageURL) {
		SpriteMakerModel building = new SpriteMakerModel();
		Map<SkillType<? extends Skill>, Skill> monsterMap = new HashMap<>();
		monsterMap.put(MoveSkill.TYPE, new MoveSkill());
		building.addComponent(new GameBus());
		building.addComponent(new SkillSet(monsterMap));
		building.addComponent(new Owner(player));
		building.addComponent(new Position(new GamePoint(200, 100), 0));
		building.addComponent(new Images(imageURL));
		building.addComponent(new Speed(100));
		building.addComponent(new Health(100));
		building.addComponent(new EventQueue(new LinkedList<>()));
		building.addComponent(new Selectable(SelectionBoundType.IMAGE));
		return building;
	}
	
	private SkillSet createTowerBuilderSkillSet(Player player) {
		Map<SkillType<? extends Skill>, Skill> skillMap = new HashMap<>();
		
		SpriteMakerModel[] spriteMakerModels = new SpriteMakerModel[] {
				createSampleSpriteMakerModel(player, "images/characters/bahamut_right.png"),
				createSampleSpriteMakerModel(player, "images/characters/bahamut_left.png"),
				createSampleSpriteMakerModel(player, "images/characters/tower2_resized.gif")
		};
		for (SpriteMakerModel spriteMakerModel : spriteMakerModels) {
			//BuildSkill build = BuildSkillFactory.createBuildSkill(spriteMakerModel);
			//skillMap.put(build.getType(), build);
		}
		
		return new SkillSet(skillMap);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}



}