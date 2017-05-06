// This entire file is part of my masterpiece.
// Jake Conroy
package gameDevelopmentInterface;

import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import newengine.sprite.components.Images;
/**
 * This code's purpose is to show the currently selected monster
 * that the user could put into a spawner. A simple piece of code, 
 * I believe it is well designed because it extends BorderPane and
 * makes use of its methods and because I have eliminated duplicate
 * code with the method refresh.
 * 
 * @author Jake Conroy (jrc63) Started April 25, 2017
 *
 */
public class SpawnerInfoPane extends BorderPane {
	private ImageView currentMonsterPicture = new ImageView();

	public SpawnerInfoPane() {
		refresh();
	}
	/**
	 * 
	 * @param monsterImages the images component of a sprite that tells
	 * one what type of image it should appear as on the screen
	 */
	public void setImage(Images monsterImages) {
		currentMonsterPicture = new ImageView(monsterImages.image().getFXImage());
		currentMonsterPicture.setFitHeight(getHeight());
		currentMonsterPicture.setFitWidth(getWidth());
		refresh();
	}
	/**
	 * When a new image is clicked, reset the center of the screen to show
	 * this image
	 */
	private void refresh() {
		this.setCenter(currentMonsterPicture);
	}
}
