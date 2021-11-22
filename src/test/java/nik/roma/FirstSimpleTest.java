package nik.roma;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstSimpleTest {

    @Test
    public void verifyIntegerEquality1() {
        Assert.assertEquals(1, 1, "Numbers should be equal.");
    }

    @Test
    public void verifyStringEquality2() {
        Assert.assertEquals("a", "a", "Strings should be equal");
    }
}
