package gameauthorgui.inputhelpers;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ButtonStringParameterInput extends HBox{
	private StringParameterInput text;
	private Button done;
	

	public ButtonStringParameterInput(String varName) {
		super();
		this.text = new StringParameterInput(varName);
		//TODO: ADD TO RESOURCE FILE
		this.done = new Button("Done");
		this.getChildren().addAll(text, done);
	}
	
	public Button getDoneButton(){
		return done;
	}
	
	public String getText(){
		return text.getValue();
	}
}
