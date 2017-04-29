package gamedata;

import data.DeveloperData;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameTest extends Application {
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// make a file chooser?
		// create game from file 
		
		DeveloperData data = new DeveloperData();
		
		
			
//		String filepath = "data/XMLfiles/game_test.xml";
//		GameCreator creator = new GameCreator(filepath);
//		Game game = creator.getGame();
//		
//		primaryStage.setScene(game.getScene());
//		game.start();
//		
//		primaryStage.show();
	}
	
	public static void main (String[] args){
		launch(args);
	}
}
