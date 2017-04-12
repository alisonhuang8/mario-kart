package engine.spritecreation.keping;

public class HowToUseGameBuilder {
	
	public static void main(String[] args) {
		
		GameBuilder gameBuilder = new GameBuilder();
		Game game = gameBuilder.buildGame(new ConfigFile());
		
		// then you can deal with the "Game" object and pass it around.
		// you can start the game, pause the game, and give the JavaFX Scene
		// to any JavaFX stage to display the game.
//		game.start();
//		game.pause();
//		stage.setScene(game.getScene());
//		stage.show();
		
	}

}
