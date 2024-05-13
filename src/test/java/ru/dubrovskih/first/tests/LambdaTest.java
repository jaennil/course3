package ru.dubrovskih.first.tests;

import org.junit.jupiter.api.Test;
import ru.dubrovskih.first.BaseTests;

public class LambdaTest extends BaseTests {
	@Test
	public void test() {
		pageManager.getHomePage()
				.checkHeaderIsPresent();
	}
}
