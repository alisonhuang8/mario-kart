package gameDevelopmentInterface;

import java.util.ResourceBundle;

import data.AttributesForScreenUse;
import gameDevelopmentInterface.attributeCreator.AttributeHolderCreator;
import gameDevelopmentInterface.attributeCreator.GroundUpAttributeCreator;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * 
 * @author Jake, Daniel Holds three tabs: the ClassCreator user interface, the
 *         ScreenModelCreator interface, and the GeneralDataModel creator
 *         interface
 *
 *         Has a save button that takes the GeneralModelData, InterfaceData,
 *         ScreenModelData and produces XML files from them to be read from.
 * 
 *         No public methods because they most of its actions are event based.
 */
public class MasterDeveloperInterface {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private static final String RESOURCE_FILE_NAME = "gameAuthoringEnvironment";
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + RESOURCE_FILE_NAME);
	private static final String SCREEN_SETTING = "SCREEN_SETTING";
	private static final String GENERAL_DATA = "GENERAL_DATA";
	private static final String CREATE_ATTRIBUTE_HOLDER = "CREATE_ATTRIBUTE_HOLDER";
	private static final String PATH_TO_STYLE_SHEETS = "/styleSheets/MainStyle.css";
	private Scene developerScene;
	private BorderPane view;
	private TabPane developerTabs;
	private AttributesForScreenUse attributesModel = new AttributesForScreenUse();
	private AttributeHolderCreator myAttributeHolderCreator = new AttributeHolderCreator(attributesModel);
	private GeneralDataCreator myGeneralDataCreator = new GeneralDataCreator();
	private ScreenModelCreator myScreenModelCreator = new ScreenModelCreator(attributesModel, myGeneralDataCreator);

	public MasterDeveloperInterface() {
		instantiate();
		developerScene = new Scene(view);
	//	developerScene.getStylesheets().setAll(PATH_TO_STYLE_SHEETS);
	}

	private void instantiateTabs() {
		developerTabs = new TabPane();
		Tab classCreatorTab = new Tab(myResources.getString(CREATE_ATTRIBUTE_HOLDER), myAttributeHolderCreator);
		Tab GeneralDataTab = new Tab(myResources.getString(GENERAL_DATA), myGeneralDataCreator);
		Tab ScreenSettingView = new Tab(myResources.getString(SCREEN_SETTING), myScreenModelCreator);
		ObservableList<Tab> myTabs = developerTabs.getTabs();
		myTabs.addAll(classCreatorTab, GeneralDataTab, ScreenSettingView);
	}

	private void instantiate() {
		view = new BorderPane();
		instantiateTabs();
		view.setTop(new TabAdder());
		view.setCenter(developerTabs);
	}

	public Scene getScene() {
		return developerScene;
	}

	class TabAdder extends HBox {
<<<<<<< HEAD
		private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
		private static final String RESOURCE_FILE_NAME = "gameAuthoringEnvironment";
		private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + RESOURCE_FILE_NAME);
		private static final String CREATE_SPRITE = "CREATE_SPRITE";
		private static final String CREATE_NEW_SCREEN = "CREATE_NEW_SCREEN";
		private static final String CREATE_NEW_SPRITE = "CREATE_NEW_SPRITE";
		private static final String CREATE_NEW_ATTRIBUTE= "CREATE_NEW_ATTRIBUTE";
=======
		private static final String CREATE_SPRITE = "Create Sprite";
		private static final String CREATE_NEW_SCREEN = "Create new Screen";
		private static final String CREATE_NEW_SPRITE = "Create new Sprite";
		private static final String CREATE_NEW_ATTRIBUTE= "Create new Attribute";
>>>>>>> 1d82dcde32665046f55e0c111f42231c691f6993

		private TabAdder() {
			instantiate();
		}


		private void instantiate() {
<<<<<<< HEAD
			Button spriteButton = new Button(myResources.getString(CREATE_NEW_SPRITE));
			Button screenButton = new Button(myResources.getString(CREATE_NEW_SCREEN));
			Button attributeButton= new Button(myResources.getString(CREATE_NEW_ATTRIBUTE));
=======
			Button spriteButton = new Button(CREATE_NEW_SPRITE);
			Button screenButton = new Button(CREATE_NEW_SCREEN);
			Button attributeButton= new Button(CREATE_NEW_ATTRIBUTE);
>>>>>>> 1d82dcde32665046f55e0c111f42231c691f6993
			spriteButton.setOnAction((clicked) -> {
				Tab spriteTab = new Tab(myResources.getString(CREATE_NEW_SPRITE), new AttributeHolderCreator(attributesModel));
				developerTabs.getTabs().add(spriteTab);
			});
			screenButton.setOnAction((clicked) -> {
				Tab screenTab = new Tab(myResources.getString(CREATE_NEW_SCREEN),
						new ScreenModelCreator(attributesModel, myGeneralDataCreator));
				developerTabs.getTabs().add(screenTab);
			});
			attributeButton.setOnAction((clicked)->{
<<<<<<< HEAD
				Tab attributeTab=new Tab(myResources.getString(CREATE_NEW_ATTRIBUTE),
=======
				Tab attributeTab=new Tab(CREATE_NEW_ATTRIBUTE,
>>>>>>> 1d82dcde32665046f55e0c111f42231c691f6993
						new GroundUpAttributeCreator());
				developerTabs.getTabs().add(attributeTab);
			});
			this.getChildren().addAll(spriteButton, screenButton,attributeButton);
		}	

	}
}