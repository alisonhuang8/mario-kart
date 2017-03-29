package com.ltub.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SerializerTest {

	private Serializer serializer;
	
	@Before
	public void setUp() throws Exception {
		serializer = new Serializer();
	}

	@Test
	public void testReconstruct() {
		APerson person = new APerson("Hello", "World");
		String serializedPerson = serializer.serialize(person);
		APerson reconstructedPerson = (APerson) serializer.deserialize(serializedPerson);
		assertEquals(person, reconstructedPerson);
	}
	
	@Test
	public void testChangeAfterSerialization() {
		APerson person = new APerson("Hello", "World");
		String serializedPerson = serializer.serialize(person);
		APerson reconstructedPerson = (APerson) serializer.deserialize(serializedPerson);
		person.setFirstName("Hello");	// change original person
		assertNotEquals(person, reconstructedPerson);
	}

}
