//This entire file is part of my masterpiece.
//DANIEL LI

package gameDevelopmentInterface.spriteCreator.variableSetters;

import exception.UnsupportedTypeException;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * @author Daniel
 * An GUI object designed to produce and return a generic variable.
 * 
 * I believe this class is well designed because this abstract class provides the interface needed
 * to produce arbitrary variables
 * @param <T>
 */
public abstract class VariableSetter<T> extends HBox{
	private double PREF_LABEL_WIDTH=150;
	private double PREF_LABEL_HEIGHT=50;
	
	public VariableSetter(String variableName){
		Label descriptorLabel=produceLabel(variableName);
		this.getChildren().addAll(descriptorLabel);
	}

	public VariableSetter(Class<T> clazz,String variableName){
		Label descriptorLabel=produceLabel(variableName);
		this.getChildren().addAll(descriptorLabel);
	}
	
	public abstract T getValue() throws UnsupportedTypeException, Exception;
	
	public abstract void setField(T initialValue);

	private Label produceLabel(String name){
		Label label=new Label(name);
		label.setPrefSize(PREF_LABEL_WIDTH, PREF_LABEL_HEIGHT);
		return label;
	}
}
