package eventbus;

import static org.junit.Assert.*;

import org.junit.Test;

public class EventBusTest {

	@Test
	public void testStringMessage() {
		Tony tony = new Tony();
		assertEquals("unreceived", tony.getMessage());
		String messageToSend = "WoW";
		Bus.bus().emit(new HelloEvent(messageToSend));
		assertEquals(messageToSend, tony.getMessage());
	}
	
	@Test
	public void failUncloneableEvent() {
		Tony tony = new Tony();
		Box box = new Box("I'm a beautiful box");
		Bus.bus().emit(new UncloneableBoxEvent(box));
		assertNotEquals(box, tony.getBox());
	}
	
	
	
}
