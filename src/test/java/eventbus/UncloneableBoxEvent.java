package eventbus;

import javafx.event.Event;
import javafx.event.EventType;

public class UncloneableBoxEvent extends Event {
	
	private static final long serialVersionUID = 8547122550045665277L;

	public static final EventType<UncloneableBoxEvent> ANY = new EventType<>(Event.ANY, "UNCLONE_EVENT");
	
	private Box box;

	public UncloneableBoxEvent(Box box) {
		super(UncloneableBoxEvent.ANY);
		this.box = box;
	}

	public Box getBox() {
		return box;
	}
	
	@Override
	public Object clone() {
		return new UncloneableBoxEvent(new Box("Something when wrong during event cloning"));
	}
	
}
