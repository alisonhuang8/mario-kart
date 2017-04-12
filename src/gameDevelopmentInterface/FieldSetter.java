package gameDevelopmentInterface;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FieldSetter extends HBox{
	private String descriptor;
	private String initialName;
	private TextField nameStorer;
	public FieldSetter(String descriptor,String initialName, boolean modifiable){
		this.initialName=initialName;
		this.getChildren().add(new Label(descriptor));
		if(modifiable){
			nameStorer=new TextField(this.initialName);
			this.getChildren().add(nameStorer);
		}
		else{
			this.getChildren().add(new Label(this.initialName));
		}
	}
	
	public String getString(){
		if(nameStorer!=null){
			return nameStorer.getText();
		}
		return initialName;
	}

}
