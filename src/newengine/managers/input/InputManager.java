package newengine.managers.input;

import bus.EventBus;
import commons.point.GamePoint;
import javafx.scene.input.KeyCode;
import newengine.events.QueueEvent;
import newengine.events.camera.CameraEvent;
import newengine.events.game.GamePauseResumeEvent;
import newengine.events.input.KeyEvent;
import newengine.events.input.MouseEvent;
import newengine.events.selection.SelectSkillEvent;
import newengine.events.selection.SelectSpriteEvent;
import newengine.events.skill.TriggerSkillEvent;
import newengine.model.PlayerStatsModel;
import newengine.model.SelectionModel;
import newengine.model.SpriteModel;
import newengine.player.Player;
import newengine.skill.Skill;
import newengine.skill.skills.MoveSkill;
import newengine.sprite.Sprite;
import newengine.utils.ActionMode;
import newengine.utils.Target;
import newengine.utils.checker.SelectionChecker;
import newengine.view.camera.Camera;

public class InputManager {

	private EventBus bus;
	private SpriteModel spriteModel;
	private PlayerStatsModel playerStatsModel;
	private SelectionModel selectionModel;
	private KeyInputState keyInputState;

	public InputManager(EventBus bus, SpriteModel spriteModel,
			PlayerStatsModel playerStatsModel, SelectionModel selectionModel) {
		this.bus = bus;
		this.spriteModel = spriteModel;
		this.playerStatsModel = playerStatsModel;
		this.selectionModel = selectionModel;
		keyInputState = new KeyInputState(bus);
		initHandlers();
	}

	private Target target(GamePoint gamePoint) {
		Sprite selected = SelectionChecker.getSelection(spriteModel.getSprites(), gamePoint);
		return new Target(gamePoint, selected);
	}
	private ActionMode actionMode() {
		ActionMode actionMode = keyInputState.isKeyPressed(KeyCode.SHIFT) ? ActionMode.QUEUE
				: ActionMode.INSTANT;
		return actionMode;
	}

	private Player player() { // TODO
		return Player.DEFAULT;
	}

	private void initHandlers() {
		bus.on(KeyEvent.PRESS, e -> {
			keyInputState.pressKey(e.getCode());
			if (keyInputState.isKeyPressed(KeyCode.P)) {
				bus.emit(new GamePauseResumeEvent());
			}
			else if (keyInputState.isKeyPressed(KeyCode.R)) {
				bus.emit(new CameraEvent(CameraEvent.RESET));
			}
		});
		bus.on(KeyEvent.RELEASE, e -> {
			keyInputState.releaseKey(e.getCode());
		});
		bus.on(MouseEvent.LEFT, e -> {
			if (selectionModel.getSelectedSkill().isPresent()) {
				Sprite sprite = selectionModel.getSelectedSprite().get();
				Skill skill = selectionModel.getSelectedSkill().get();
				
				if (actionMode() == ActionMode.INSTANT) {
					//sprite.emit(new TriggerSkillEvent(skill.getType(), target(e.getPos())));
					bus.emit(new SelectSkillEvent(SelectSkillEvent.CANCEL, skill));
				}
				else {
					//sprite.emit(new QueueEvent(QueueEvent.ADD, new TriggerSkillEvent(skill.getType(), target(e.getPos()))));
				}
			}
			else if (target(e.getPos()).getSprite().isPresent()) {
				bus.emit(new SelectSpriteEvent(SelectSpriteEvent.SINGLE, target(e.getPos()).getSprite().get()));
			}
		});
		bus.on(MouseEvent.RIGHT, e -> {
			// right click: cancel selected skill if present. And let the sprite move (or move & attack).
			if (selectionModel.getSelectedSkill().isPresent()) {
				bus.emit(new SelectSkillEvent(SelectSkillEvent.CANCEL, selectionModel.getSelectedSkill().get()));
			}
			else if (selectionModel.getSelectedSprite().isPresent()){
				Sprite sprite = selectionModel.getSelectedSprite().get();
				TriggerSkillEvent event = new TriggerSkillEvent(MoveSkill.TYPE, new Target(e.getPos()));
				if (actionMode() == ActionMode.INSTANT) {
					sprite.emit(event);					
				}
				else {
					sprite.emit(new QueueEvent(QueueEvent.ADD, event));
				}
			}
		});
	}

	public void update(double dt) {
		if (keyInputState.isKeyPressed(KeyCode.LEFT)) {
			bus.emit(new CameraEvent(CameraEvent.MOVE, Camera.MOVE_SPEED_PER_FRAME * dt, 0));
		}
		else if (keyInputState.isKeyPressed(KeyCode.RIGHT)) {
			bus.emit(new CameraEvent(CameraEvent.MOVE, -Camera.MOVE_SPEED_PER_FRAME * dt, 0));
		}
		else if (keyInputState.isKeyPressed(KeyCode.UP)) {
			bus.emit(new CameraEvent(CameraEvent.MOVE, 0, Camera.MOVE_SPEED_PER_FRAME * dt));
		}
		else if (keyInputState.isKeyPressed(KeyCode.DOWN)) {
			bus.emit(new CameraEvent(CameraEvent.MOVE, 0, -Camera.MOVE_SPEED_PER_FRAME * dt));
		}
	}
}
