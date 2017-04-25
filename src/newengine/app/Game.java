package newengine.app;
import bus.BasicEventBus;
import bus.EventBus;
import javafx.scene.Scene;
import newengine.events.GameInitializationEvent;
import newengine.events.trigger.AddTriggerEvent;
import newengine.gameloop.FXGameLoop;
import newengine.gameloop.GameLoop;
import newengine.managers.collision.CollisionManager;
import newengine.managers.conditions.ConditionManager;
import newengine.managers.debug.DebugManager;
import newengine.managers.input.InputManager;
import newengine.managers.range.RangeManager;
import newengine.managers.sound.SoundManager;
import newengine.managers.timer.TimerManager;
import newengine.model.Models;
import newengine.model.PlayerRelationModel;
import newengine.model.PlayerStatsModel;
import newengine.model.SelectionModel;
import newengine.model.SpriteModel;
import newengine.trigger.Trigger;
import newengine.trigger.TriggerManager;
import newengine.view.View;
import newengine.view.camera.Camera;
public class Game {
	private EventBus bus = new BasicEventBus();
	private GameLoop gameLoop;
	private View view;
	private boolean mapInitialized = false;
	 
	public Game() {
		SpriteModel spriteModel = new SpriteModel(bus);
		PlayerStatsModel playerStatsModel = new PlayerStatsModel(bus); // TODO CONNECT PLAYER AND PLAYERSTATSMODEL
		PlayerRelationModel playerRelationModel = new PlayerRelationModel(bus);
		SelectionModel selectionModel = new SelectionModel(bus);
		Models models = new Models(bus, spriteModel, playerStatsModel, playerRelationModel, selectionModel);
		
		view = new View(bus);
		
		gameLoop = new FXGameLoop(bus);
		
		CollisionManager collisionManager = new CollisionManager(bus); // TODO
		RangeManager rangeManager = new RangeManager(bus); // TODO
		InputManager inputManager = new InputManager(bus, spriteModel, playerStatsModel, selectionModel);
		SoundManager soundManager = new SoundManager(bus);
		DebugManager debugManager = new DebugManager(bus);
		TriggerManager triggerManager = new TriggerManager(bus, models);
		TimerManager timerManager = new TimerManager(bus);
		ConditionManager conditionManager = new ConditionManager(bus,spriteModel, playerStatsModel);
		
		gameLoop.addLoopComponent((dt) -> view.clear());
		gameLoop.addLoopComponent((dt) -> collisionManager.checkCollisions(spriteModel.getSprites()));
		gameLoop.addLoopComponent((dt) -> rangeManager.checkRanges(spriteModel.getSprites()));
		gameLoop.addLoopComponent((dt) -> spriteModel.update(dt));
		gameLoop.addLoopComponent((dt) -> view.render(models));
		gameLoop.addLoopComponent((dt) -> conditionManager.checkConditions());
		gameLoop.addLoopComponent((dt) -> timerManager.update(dt));
		gameLoop.addLoopComponent((dt) -> inputManager.update(dt));

	}
	
	public void addTrigger(Trigger trigger) {
		bus.emit(new AddTriggerEvent(trigger));
	}
	
	public EventBus getBus() {
		return bus;
	}
	
	public void start() {
		if (!mapInitialized) {
			bus.emit(new GameInitializationEvent());
			mapInitialized = true;
		}
		gameLoop.start();
	}
	public void pause() {
		gameLoop.pause();
	}
	public Scene getScene() {
		return view.getScene();
	}
	
} 