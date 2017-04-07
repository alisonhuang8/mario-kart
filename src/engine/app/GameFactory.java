package engine.app;

import bus.BasicEventBus;
import bus.EventBus;
import engine.action.ActionFilter;
import engine.action.ActionManager;
import engine.camera.Camera;
import engine.gameloop.FXGameLoop;
import engine.gameloop.GameLoop;
import engine.input.InputManager;
import engine.model.BasicModel;
import engine.model.Model;
import engine.model.PlayerLocalModel;
import engine.player.Player;
import engine.playerstate.PlayerSelectionState;
import engine.playerstate.PlayerSkillState;
import engine.sound.FXSoundManager;
import engine.sound.SoundManager;
import engine.sprite.collision.CollisionChecker;
import engine.sprite.collision.CollisionManager;
import engine.sprite.range.InRangeChecker;
import engine.sprite.range.InRangeManager;
import engine.view.FXView;
import engine.view.View;

public class GameFactory {

	private final EventBus bus;
	
	/**
	 * Each {@link GameFactory} has a unique {@link EventBus}.
	 */
	public GameFactory() {
		bus = new BasicEventBus();
	}
	
	public Model createModel(Player player) {
		return new BasicModel(bus, player);
	}
	
	public View createView(Camera camera) {
		return new FXView(bus, camera);
	}
	
	public GameLoop createGameLoop() {
		return new FXGameLoop();
	}
	
	public Camera createCamera() {
		return new Camera();
	}
	
	public SoundManager createSoundManager() {
		return new FXSoundManager(bus);
	}
	
	public InputManager createInputManager(Model model, Camera camera) {
		return new InputManager(bus, model, camera);
	}

	public ActionFilter createActionFilter() {
		return new ActionFilter(bus);
	}
	public ActionManager createActionManager() {
		return new ActionManager(bus);
	}
	

	public CollisionChecker createCollisionChecker() {
		return new CollisionChecker(bus);
	}
	
	public CollisionManager createCollisionManager() {
		return new CollisionManager(bus);
	}
	
	public InRangeChecker createInRangeChecker() {
		return new InRangeChecker(bus);
	}
	
	public InRangeManager createInRangeManager() {
		return new InRangeManager(bus);
	}
	
	private PlayerSelectionState createPlayerSelectionState() {
		return new PlayerSelectionState(bus);
	}
	
	private PlayerSkillState createPlayerSkillState() {
		return new PlayerSkillState(bus);
	}
	
	public PlayerLocalModel createPlayerLocalModel() {
		PlayerLocalModel localModel = new PlayerLocalModel(bus);
		localModel.setPlayerSelectionState(createPlayerSelectionState());
		localModel.setPlayerSkillState(createPlayerSkillState());
		return localModel;
	}
}
