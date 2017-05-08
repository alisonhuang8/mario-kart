package gameDevelopmentInterface.spriteCreator.variableSetters;

import java.util.ArrayList;
import java.util.List;

import exception.UnsupportedTypeException;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;

/**
 * 
 * @author Daniel
 * A GUI component that allows the developer to get and set an enum.
 * @param <T>
 */
public class EnumSetter<T extends Enum<?>> extends VariableSetter<T>{
	private ChoiceBox<T> enumChoices;
	private HBox myItems;
	
	public EnumSetter(Class<T> clazz,String description){
		super(clazz,description);
		myItems=new HBox();
		this.getChildren().add(myItems);
		
		T[] enumConstants=clazz.getEnumConstants();
		List<T> list=new ArrayList<>();
		for(int i=0; i< enumConstants.length;i++){
			list.add(enumConstants[i]);
		}
	
		enumChoices=new ChoiceBox<>();		
		enumChoices.setItems(FXCollections.observableList(list));
		this.getChildren().add(enumChoices);
	}
	
	@Override
	public T getValue() {
		return enumChoices.getValue();
	}

	@Override
	public void setField(T initialValue) {
		enumChoices.getSelectionModel().select(initialValue);		
	}

}
