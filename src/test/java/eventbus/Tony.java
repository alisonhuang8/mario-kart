package eventbus;

import javafx.event.EventHandler;

public class Tony {

	private String message = "unreceived";
	private Box box = null;
	
	private EventHandler<HelloEvent> helloHandler = (e) -> message = e.getMessage();

	public Tony() {
		initEventHandlers();
	}
	
	public String getMessage() {
		return message; 
	}
	public void setBox(Box box) {
		this.box = box; 
	}
	public Box getBox() { 
		return box;
	}
	
	private void initEventHandlers() {
		Bus.bus().on(HelloEvent.ANY, helloHandler);
		Bus.bus().on(UncloneableBoxEvent.ANY, (e) -> box = e.getBox());
	}
	
	/**
	 * Called when destroying this object.
	 */
	private void removeEventHandlders() {
		Bus.bus().off(HelloEvent.ANY, helloHandler);
	}
	
}
