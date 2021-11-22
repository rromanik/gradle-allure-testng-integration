package nik.roma;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SecondSimpleTest {

    @Test
    public void verifyEquality1() {
        Assert.assertEquals(2, 2, "Numbers should be equal too");
    }
}
