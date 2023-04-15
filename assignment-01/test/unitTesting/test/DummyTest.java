package unitTesting.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import unitTesting.Dummy;

public class DummyTest {

	private Dummy _dummy;
	
	@Before
	public void before() {
		_dummy = new Dummy();
	}
	
	@Test
	public void returnsOne() {
		Assert.assertEquals(1, _dummy.returnsOne());
	}
}