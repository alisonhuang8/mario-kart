package gameDevelopmentInterface;

import java.util.ArrayList;
import java.util.List;

import data.DeveloperData;
import data.SpriteMakerModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import newengine.sprite.components.EventQueue;
import newengine.sprite.components.GameBus;
import newengine.sprite.components.Health;
import newengine.sprite.components.Images;
import newengine.sprite.components.PathFollower;
import newengine.sprite.components.SkillSet;
import newengine.sprite.components.Speed;
import utilities.AlertHandler;

public class AllPossibleMonsters extends ScrollPane {
	private static final int IMAGE_SIZE = 100;
	private DeveloperData myData;
	private SpawnerCreation mySpawnerInfo;
	private SpawnerInfoPane mySpawnerInfoPane;
	private VBox monsterImages;

	public AllPossibleMonsters(SpawnerCreation spawnerInfo, DeveloperData data, SpawnerInfoPane spawnerInfoPane) {
		mySpawnerInfoPane = spawnerInfoPane;
		mySpawnerInfo = spawnerInfo;
		myData = data;
		monsterImages = new VBox();
		this.setContent(monsterImages);
	}

	public void getMonstersOnScreen() {
		List<SpriteMakerModel> allSprites = new ArrayList<SpriteMakerModel>(myData.getSprites());
		System.out.println("size: " + allSprites.size());
		System.out.println("images: " + allSprites.get(0).getComponentByType(Images.TYPE));
		List<SpriteMakerModel> onlyMonsters = new ArrayList<>();
		for (SpriteMakerModel possibleMonster : allSprites) {
			if (isMonster(possibleMonster)) {
				onlyMonsters.add(possibleMonster);
			}
		}
		for (SpriteMakerModel monster : onlyMonsters) {
			Images imageComp = (Images) monster.getComponentByType(Images.TYPE);
			ImageView iv = new ImageView(imageComp.image().getFXImage());
			iv.setFitWidth(100);
			iv.setFitHeight(100);
			iv.setOnMouseClicked(click -> {
				setCurrentMonster(monster, new ImageView(imageComp.image().getFXImage()));
				mySpawnerInfo.setCurrentMonsterToSpawn(monster);
			});
			monsterImages.getChildren().add(iv);
		}
	}

	public void loadFromFile(SpriteMakerModel monster) {
		if (isMonster(monster)) {
			Images imageComp = (Images) monster.getComponentByType(Images.TYPE);
			System.out.println(imageComp.image().getFileName());
			ImageView iv = new ImageView(imageComp.image().getFXImage());
			iv.setFitWidth(IMAGE_SIZE);
			iv.setFitHeight(IMAGE_SIZE);
			iv.setOnMouseClicked(click -> {
				setCurrentMonster(monster, new ImageView(imageComp.image().getFXImage()));
				mySpawnerInfo.setCurrentMonsterToSpawn(monster);
			});
			monsterImages.getChildren().add(iv);
		}
	}

	private void setCurrentMonster(SpriteMakerModel monster, ImageView iv) {
		mySpawnerInfo.setCurrentMonsterToSpawn(monster);
		mySpawnerInfoPane.setImage((Images) monster.getComponentByType(Images.TYPE));
	}

	private boolean isMonster(SpriteMakerModel possibleMonster) {
		return (possibleMonster.getComponentByType(Images.TYPE) != null);// &&
		// possibleMonster.getComponentByType(Speed.TYPE) != null &&
		// possibleMonster.getComponentByType(Health.TYPE) != null &&
		// possibleMonster.getComponentByType(PathFollower.TYPE) != null &&
		// possibleMonster.getComponentByType(EventQueue.TYPE) != null);
		// possibleMonster.getComponentByType(GameBus.TYPE) != null);
	}
}
