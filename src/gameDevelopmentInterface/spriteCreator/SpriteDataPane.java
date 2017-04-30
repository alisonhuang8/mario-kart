package gameDevelopmentInterface.spriteCreator;


import java.util.ArrayList;
import java.util.List;

import data.DeveloperData;
import data.SpriteMakerModel;
import exception.UnsupportedTypeException;
import gameDevelopmentInterface.developerdata.ComponentSetter;
import gameDevelopmentInterface.spriteCreator.variableSetters.SimpleVariableSetter;
import gameauthorgui.inputhelpers.StringParameterInput;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import newengine.sprite.component.Component;
import utilities.AlertHandler;

/**
 * 
 * @author Daniel
 * Displays what components a sprite possesses and allows them to be set.
 *
 */
public class SpriteDataPane extends ScrollPane{
	private VBox myPane;
	private SpriteMakerModel spriteData;
	private SpriteDescriptor descriptor;
	private ComponentLister lister;
	private DeveloperData developerData;
	private double PREF_WIDTH=600;
	private SimpleVariableSetter<String> nameSetter;
	private SimpleVariableSetter<String> descriptionSetter;
	
	public SpriteDataPane(SpriteMakerModel spriteData, DeveloperData developerData){
		instantiate(spriteData, developerData);
		for(Component component:spriteData.getActualComponents()){
			addComponent(component,true);
		}
	}
	
	public SpriteDataPane(SpriteMakerModel spriteData, DeveloperData developerData, boolean removableComponents){
		this.developerData=developerData;
		myPane=new VBox();
		this.spriteData=new SpriteMakerModel();
		descriptor=new SpriteDescriptor();
		lister=new ComponentLister();
		myPane.getChildren().addAll(descriptor,lister);
		this.setContent(myPane);
		this.setPrefWidth(PREF_WIDTH);
	}
	
	private void instantiate(SpriteMakerModel spriteData, DeveloperData developerData){
		this.developerData=developerData;
		myPane=new VBox();
		this.spriteData=spriteData;
		descriptor=new SpriteDescriptor();
		lister=new ComponentLister();
		myPane.getChildren().addAll(descriptor,lister);
		this.setContent(myPane);
		this.setPrefWidth(PREF_WIDTH);
		
	}
	
	public SimpleVariableSetter<String> getNameSetter(){
		return nameSetter;
	}
	
	public SimpleVariableSetter<String> getDescriptionSetter(){
		return descriptionSetter;
	}
	
	public <T extends Component> void addComponent(T component, boolean removable){
		try{
			ComponentSetter<T> setter=new DefaultComponentSetter<T>(component,developerData);
			if(!removable){
				lister.addComponentView(setter);
			}else{
				lister.addComponentView(lister.new RemovableComponentViewer<T>(setter));
			}
		}
		catch(UnsupportedTypeException e){
			e.printStackTrace();
		}
	}
	
	public <T extends Component> void  addComponent(Class<T> clazz){
		try {
			ComponentSetter<T> setter=new DefaultComponentSetter<T>(clazz,developerData);
			lister.addComponentView(lister.new RemovableComponentViewer<T>(setter));
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}	
	}
	
	public void updateSpriteData() throws Exception{
		spriteData.setName(nameSetter.getValue());
		spriteData.setDescription(descriptionSetter.getValue());
		lister.updateSpriteModel();		
	}
	
	private class SpriteDescriptor extends VBox{	
		private SpriteDescriptor(){
			try{
				nameSetter=new SimpleVariableSetter<String>(String.class, "Sprite name:");
				descriptionSetter= new SimpleVariableSetter<String>(String.class, "Description:");
				this.getChildren().addAll(new Label("Add Sprite Components"),nameSetter, descriptionSetter);
			}
			catch(Exception e){
				AlertHandler.errorPopUp(e.getMessage());
			}
		}
	}
	
	private class ComponentLister extends VBox{
		private List<ComponentSetter<? extends Component>> componentViews;
		
		public ComponentLister(){
			componentViews=new ArrayList<>();
			this.prefWidthProperty().bind(SpriteDataPane.this.prefWidthProperty());
		}
		
		private void removeComponentView(ComponentSetter<? extends Component> view){
			componentViews.remove(view);
			this.getChildren().remove(view);
		}
		
		private void addComponentView(ComponentSetter<? extends Component> view){
			for(ComponentSetter<? extends Component> viewer: componentViews){
				if(view.getComponentType().equals(viewer.getComponentType())){
					return;
				}
			}
			componentViews.add(view);
			view.setStyle("-fx-border-color:black");
			this.getChildren().add(view);
		}
		
		/**
		 * ONLY CALL WHEN DONE
		 * @throws Exception
		 */
		private void updateSpriteModel(){
			try {
				spriteData.setName(nameSetter.getValue());
				spriteData.clearComponents();
				for(ComponentSetter<? extends Component> component: componentViews){
					spriteData.addComponent(component.produceComponent());
				}
			} catch (Exception e) {
				e.printStackTrace();
				AlertHandler.errorPopUp(e.getMessage());
			}
			
		}
		
		private class RemovableComponentViewer<T extends Component> extends ComponentSetter<T>{
			private ComponentSetter<T> mySetter;
			private RemovableComponentViewer(ComponentSetter<T> mySetter){
				super(mySetter.getComponentType());
				//this.setStyle("-fx-background-color: cyan;-fx-border-color:red");
				this.mySetter=mySetter;
				this.getChildren().addAll(mySetter);
				Button removeButton =new Button("Remove component");
				removeButton.setOnAction((click)->{
					removeMe();
				});
				this.getChildren().add(removeButton);
				this.prefWidthProperty().bind(ComponentLister.this.prefWidthProperty());
			}
			
			private void removeMe(){
				ComponentLister.this.removeComponentView(this);
			}

			public T produceComponent() throws Exception{
				try {
					return mySetter.produceComponent();
				} catch (UnsupportedTypeException e) {
					e.printStackTrace();
				}
				return null;
			}
		}
	}	
}
