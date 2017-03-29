package eventbus;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public interface IEventBus {

	<T extends Event> void on(EventType<T> eventType, EventHandler<? super T> eventHandler);
	
	<T extends Event> void off(EventType<T> eventType, EventHandler<? super T> eventHandler);
	
	public void emit(Event event);
	
}
