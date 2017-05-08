package newengine.view;
import java.util.ArrayList;
import java.util.List;

import bus.EventBus;
import commons.point.GamePoint;
import commons.point.ViewPoint;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import newengine.events.camera.CameraEvent;
import newengine.events.game.GamePauseResumeEvent;
import newengine.events.input.KeyEvent;
import newengine.events.input.MouseEvent;
import newengine.model.Models;
import newengine.model.PlayerRelationModel;
import newengine.model.PlayerStatsModel;
import newengine.model.PlayerStatsModel.WealthType;
import newengine.model.SelectionModel;
import newengine.model.SpriteModel;
import newengine.player.Player;
import newengine.skill.Skill;
import newengine.sprite.Sprite;
import newengine.sprite.components.Images;
import newengine.sprite.components.Owner;
import newengine.sprite.components.Position;
import newengine.sprite.components.SkillSet;
import newengine.sprite.components.Upgrade;
import newengine.utils.image.LtubImage;
import newengine.view.camera.Camera;
import newengine.view.subview.SkillBox;
import newengine.view.subview.StateDisplay;
import newengine.view.subview.UpgradeBtn;


public class View {
	private static final String LIVES = "Lives:";

	private static final String PAUSE2 = "Pause";

	private static final String CSS_LOCATION = "/styleSheets/engine-view.css";
	
	public static final Paint BACKGROUND = Color.BISQUE; // Paint is the super class
	public static final double INIT_WIDTH = 600;
	public static final double INIT_HEIGHT = 500;
	public static final double INIT_CANVAS_WIDTH = 600;
	public static final double INIT_CANVAS_HEIGHT = 300;
	public static final double INIT_STAT_HEIGHT = 100;
	public static final double INIT_SELECTION_HEIGHT = 200;

	private double width = INIT_WIDTH;
	private double height = INIT_HEIGHT;
	private double canvasWidth = INIT_CANVAS_WIDTH;
	private double canvasHeight = INIT_CANVAS_HEIGHT;
	private double statHeight = INIT_STAT_HEIGHT;
	private double selectionWidth = width / 2;
	private double selectionHeight = INIT_SELECTION_HEIGHT;

	private EventBus bus;
	private Camera camera;
	private Scene scene;
	private Canvas gameWorldCanvas;
	private GraphicsContext gc;
	private HBox bottomPane;
	private HBox statsPanel;
	private GraphicsContext gcSelected;
	private SkillBox skillBox;
	private StateDisplay stateDisplay;
	private UpgradeBtn upgradeBtn;
	private Canvas selectionCanvas; 

	public View(EventBus bus, Camera camera) {		
		this.bus = bus;
		this.camera = camera;
		VBox root = new VBox();
		scene = new Scene(root, width, height, BACKGROUND);		
		statsPanel = new HBox();
		selectionCanvas = new Canvas(selectionWidth, selectionHeight);
		StackPane holder = new StackPane();
		gameWorldCanvas = new Canvas(canvasWidth, canvasHeight);
		holder.getChildren().add(gameWorldCanvas);
		holder.setStyle("-fx-background-color: darkgreen");
		bottomPane = new HBox();
		root.getChildren().addAll(
				statsPanel, 
				//gameWorldCanvas, 
				holder,
				splittingRule(),
				bottomPane);
		// selected sprite
		Canvas selectionCanvas = new Canvas(selectionWidth, selectionHeight);
		bottomPane.getChildren().add(selectionCanvas);
		gc = gameWorldCanvas.getGraphicsContext2D();
		gcSelected = selectionCanvas.getGraphicsContext2D();
		// skill box
		stateDisplay = new StateDisplay();
		skillBox = new SkillBox(bus);
		upgradeBtn = new UpgradeBtn();
		Button pause = new Button(PAUSE2);
		pause.setOnAction(e -> bus.emit(new GamePauseResumeEvent()));
		bottomPane.getChildren().addAll(upgradeBtn.getBox(), stateDisplay.getBox(), new TowersButton(bus).getNode(),skillBox.getBox(), pause);
		bottomPane.getStyleClass().add("bottom-pane");

		this.camera = new Camera(bus);

		scene.getStylesheets().setAll(CSS_LOCATION);
		initHandlers();
	}

	private Node splittingRule() {
		VBox rule = new VBox();
//		rule.getChildren().add(new Button("rule"));
		rule.getStyleClass().add("rule");
		return rule;
	}
	
	private void initHandlers() {
		gameWorldCanvas.setOnMouseClicked(e -> {
			ViewPoint viewPos = new ViewPoint(e.getX(), e.getY());
			if (e.getButton() == MouseButton.PRIMARY) {
				bus.emit(new MouseEvent(MouseEvent.LEFT, camera.viewToGame(viewPos)));
			} else if (e.getButton() == MouseButton.SECONDARY) {
				bus.emit(new MouseEvent(MouseEvent.RIGHT, camera.viewToGame(viewPos)));
			}
		});
		gameWorldCanvas.setOnScroll((e) -> {
			// for my mouse, each scroll is 40 pixels
			// e.getDeltaY() is negative when scroll down (zoom in, increase zoom factor)
			bus.emit(new CameraEvent(CameraEvent.ZOOM, e.getDeltaY() / 400));
		});
		scene.setOnKeyPressed(e -> {
			bus.emit(new KeyEvent(KeyEvent.PRESS, e.getCode()));
		});
		scene.setOnKeyReleased(e -> {
			bus.emit(new KeyEvent(KeyEvent.RELEASE, e.getCode()));
		});
		scene.setOnKeyTyped(e -> {
			bus.emit(new KeyEvent(KeyEvent.TYPE, e.getCode()));
		});
	}

	public Scene getScene() {
		return scene;
	}

	public void render(Models models) {
		//bus.emit(new ChangeWealthEvent(ChangeWealthEvent.CHANGE, models.playerRelationModel().getMainPlayer(), WealthType.GOLD, 100 ));
		//bus.emit(new ChangeLivesEvent(ChangeLivesEvent.CHANGE, models.playerRelationModel().getMainPlayer(),-1));
		renderSprites(models.spriteModel());
		renderStats(models.playerStatsModel(), models.playerRelationModel(), models.selectionModel());
		renderBottomPane(models.selectionModel(), models.playerRelationModel());
	}


	private void renderStats(PlayerStatsModel playerStatsModel, 
			PlayerRelationModel playerRelationModel, SelectionModel selectionModel) {
		this.statsPanel.getChildren().clear();
		statsPanel.setSpacing(10);
		statsPanel.maxHeight(100);
		statsPanel.getChildren().add(new Text("Game Stats: "));
		Player mainPlayer = playerRelationModel.getMainPlayer();
		createText(playerStatsModel, mainPlayer).stream().forEach(e -> statsPanel.getChildren().add(e));
	}

	private List<Text> createText(PlayerStatsModel playerStatsModel, Player player) {
		List<Text> statsLabels = new ArrayList<Text>();
		playerStatsModel.getWealth(player).ifPresent((wealthMap) -> {
			for (WealthType type: wealthMap.keySet()) {
				statsLabels.add(new Text(type + ": " + wealthMap.get(type)));
			}
		});
		//TODO map to resource file
		playerStatsModel.getLives(player).ifPresent((life) -> {
			statsLabels.add(new Text(LIVES + life));
		});
//		playerStatsModel.getScore(player).ifPresent((score) -> {
//			statsLabels.add(new Text("Scores:" + score));
//		});
		return statsLabels;
	}

	private void renderSprites(SpriteModel model) {
		gc.clearRect(0, 0, canvasWidth, canvasHeight);
		for (Sprite sprite : model.getSprites()) {
			if (!sprite.getComponent(Position.TYPE).isPresent() ||
					!sprite.getComponent(Images.TYPE).isPresent()) {
				continue;
			}
			GamePoint spritePos = sprite.getComponent(Position.TYPE).get().pos();

			sprite.getComponent(Images.TYPE).ifPresent((imagesComponent) -> {
				LtubImage image = imagesComponent.image();
				GamePoint gamePos = new GamePoint(spritePos.x() - image.getImagePivot().x(), 
						spritePos.y() - image.getImagePivot().y());
				ViewPoint viewPos = camera.gameToView(gamePos);
				gc.drawImage(image.getFXImage(), viewPos.x(), viewPos.y(), 
						image.width() * camera.getScaleFactor(), 
						image.height() * camera.getScaleFactor());
			});

		}
	}

	private void renderBottomPane(SelectionModel selectionModel, PlayerRelationModel playerRelationModel) {
		// render the selected skill
		if (selectionModel.getSelectedSkill().isPresent()) {
			Skill skill = selectionModel.getSelectedSkill().get();
			if (skill.getIcon().isPresent()) {
				LtubImage skillImage = skill.getIcon().get();
				ImageCursor imageCursor = new ImageCursor(
						skillImage.getFXImage(), 
						skillImage.width() / 2,
						skillImage.height() / 2);
				scene.setCursor(imageCursor);
			}
		} else {
			scene.setCursor(Cursor.DEFAULT);
		}

		// render skill box of the selected sprite
		if (selectionModel.getSelectedSprite().isPresent()) {
			Sprite sprite = selectionModel.getSelectedSprite().get();
			//display sprite in selected canvas
			if (sprite.getComponent(Images.TYPE).isPresent()) {
				clearSelectionCanvas();
				gcSelected.drawImage(sprite.getComponent(Images.TYPE).get().image().getFXImage(), 20, 0,
						sprite.getComponent(Images.TYPE).get().image().width(), 
						sprite.getComponent(Images.TYPE).get().image().height());
			}
			//fill stats box with stats of selected sprite
			
			if (sprite.getComponent(Upgrade.TYPE).isPresent() && 
					sprite.getComponent(Owner.TYPE).get().player().getName().equals(playerRelationModel.getMainPlayer().getName())){
				upgradeBtn.render(sprite);
			}
			stateDisplay.render(sprite);

			if (sprite.getComponent(Owner.TYPE).isPresent()) {
				Player player = sprite.getComponent(Owner.TYPE).get().player();
				Player mainPlayer = playerRelationModel.getMainPlayer();
				
				if (player.equals(mainPlayer) && sprite.getComponent(SkillSet.TYPE).isPresent()) {
					skillBox.render(sprite.getComponent(SkillSet.TYPE).get().skills());
					return;
				}
				skillBox.clear();
			}
		}
		else {
			clearSelectionCanvas();
			skillBox.clear();
		}
	}


	private void clearSelectionCanvas() {
		gcSelected.clearRect(0, 0, width / 2, selectionHeight);
	}
}
