package engine.spritecreation.keping;

import bus.EventBus;
import engine.app.GameFactory;
import engine.camera.Camera;
import engine.gameloop.GameLoop;
import engine.model.Model;
import engine.model.PlayerLocalModel;
import engine.sprite.Sprite;
import engine.sprite.collidable.CollisionChecker;
import engine.sprite.range.InRangeChecker;
import engine.view.View;

/**
 * {@link engine.app.App} Is an example of how a game is created.
 * The game builder should do something similar, only reading from files
 * instead of hard-coding everything.
 * @author keping
 *
 */
public class GameBuilder {
	
	SpriteBuilder spriteBuilder = new SpriteBuilder();	
	// note that we can omit the empty constructor just to make the code cleaner
	
	public Game buildGame(ConfigFile configFile) {
		ConfigFileParser parser = new ConfigFileParser();
		return buildGame(parser.parse(configFile));
	}
	

	private Sprite buildSprite(EventBus bus, SpriteConfig spriteConfig) {
		// use a separate class so as to avoid a very huge GameBuilder
		return spriteBuilder.buildSprite(bus, spriteConfig);
	}
	
	/*
	 * Note that this GameBuilder utilizes the engine.app.GameFactory.
	 * But they should actually be the same class. 
	 */
	
	public Game buildGame(GameConfig gameConfig) {
		
		GameFactory gameFactory = new GameFactory();
		
		// model and view
		Model model = gameFactory.createModel(null); // TODO: this model constructor is to be changed. 
		PlayerLocalModel localModel = gameFactory.createPlayerLocalModel();
		Camera camera = gameFactory.createCamera();
		View view = gameFactory.createView(camera);

		// add sprites to the model
		for (SpriteConfig spriteConfig : gameConfig.getSpriteConfigs()) {
			model.addSprite(buildSprite(gameFactory.getBus(), spriteConfig));
		}
		
		// game loop 
		GameLoop gameLoop = gameFactory.createGameLoop();
		CollisionChecker collisionChecker = gameFactory.createCollisionChecker();
		InRangeChecker inRangeChecker = gameFactory.createInRangeChecker();
		gameLoop.addLoopComponent((dt) -> model.refreshSprites());
		gameLoop.addLoopComponent((dt) -> model.updatePositions(dt)); //updates any sprite with movable attribute including weapons
		gameLoop.addLoopComponent((dt) -> inRangeChecker.checkInRange(model.getSprites(), 1, 2)); //TODO which teams?
		gameLoop.addLoopComponent((dt) -> collisionChecker.checkCollision(model.getSprites()));
		gameLoop.addLoopComponent((dt) -> view.render(model));
		gameLoop.addLoopComponent((dt) -> view.render(localModel));
		
		
		// create other event managers (they are automatically linked by the bus)
		gameFactory.createCollisionManager();
		gameFactory.createInRangeManager();
		gameFactory.createSoundManager();
		gameFactory.createInputManager(model, camera);
		gameFactory.createActionFilter();
		gameFactory.createActionManager();
		gameFactory.createSkillManager();
		gameFactory.createAttackManager();

		Game game = new Game(gameLoop, view.getScene());
		return game;
	}

}
