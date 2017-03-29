package com.ltub.engine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ltub.engine.Number3;

public class Number3Test {

	private Number3 number3;

	@Before
	public void init() {
		number3 = new Number3();
	}

	@Test
	public void test1() {
		assertEquals(3, number3.getNumber3());
	}

}
