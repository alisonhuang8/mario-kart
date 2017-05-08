// This entire class is part of my masterpiece.
// DANIEL LI

package gameDevelopmentInterface.spriteCreator.variableSetters;

import exception.UnsupportedTypeException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * 
 * @author Daniel
 * A GUI component that sets the value of a variable of form String or primitive based on the user's 
 * text inputs.
 * I believe this class is well written because it demonstrates the flexibility of the ObjectSetter.
 * 
 * @param <T>
 */
public class SimpleVariableSetter<T> extends VariableSetter<T>{
	private TextField value;
	Class<T> type;
	
	public SimpleVariableSetter(Class<T> type,String descriptor) throws UnsupportedTypeException{
		super(type, descriptor);
		HBox box =new HBox();
		this.type=type;
		value=new TextField();
		this.getChildren().add(box);
		box.getChildren().addAll(value);
		box.setSpacing(50);
	}
	
	public T getValue() throws UnsupportedTypeException{
		PrimitiveConverter<T> converter=new PrimitiveConverter<T>();
		return converter.convertString(type, value.getText());
	}

	@Override
	public void setField(T initialValue) {
		value.setText(initialValue.toString());
	}
	
	public void setText(String test){
		value.setText(test);
	}

}

