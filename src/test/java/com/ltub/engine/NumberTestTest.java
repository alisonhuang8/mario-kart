package com.ltub.engine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ltub.engine.NumberTest;

public class NumberTestTest {

	private NumberTest numberTest;

	@Before
	public void init() {
		numberTest = new NumberTest();
	}

	@Test
	public void test1() {
		assertEquals(3, numberTest.getNumber3());
	}

}
