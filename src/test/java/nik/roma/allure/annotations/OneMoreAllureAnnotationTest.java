package nik.roma.allure.annotations;

import io.qameta.allure.Step;
import org.testng.annotations.Test;

public class OneMoreAllureAnnotationTest {

    @Test
    public void verifyStepAnnotation() {
        performAction("copy");
    }

    @Step("{action.value}")
    private void performAction(String action) {
        action.toUpperCase();
    }
}
