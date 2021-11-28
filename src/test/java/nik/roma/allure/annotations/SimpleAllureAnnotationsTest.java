package nik.roma.allure.annotations;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Allure Annotations")
@Feature("Basic Annotations")
@Story("Basic Annotations: Step, Attachment, Epic, Story")
public class SimpleAllureAnnotationsTest {

    @Test
    public void verifyStepAnnotationIsProcessed() {
        performAction();
        Assert.assertEquals(10, 10, "Tens should be equal");
    }

    @Test
    public void verifyStepAnnotationGraspsArguments() {
        performAction("First", 2);
        Assert.assertTrue(true);
    }

    @Test
    public void verifyAttachmentAnnotationIsProcessed() {
        attachResult();
        Assert.assertEquals(100, 100, "Hundreds should be equal");
    }

    @Test
    public void verifyAttachmentIsProcessedAsJson() {
        attachJson();
        Assert.assertFalse(false);
    }

    @Step
    private void performAction() {
        System.out.println("Action is performed!");
    }

    @Step("Step with args: {0}, {1}")
    private void performAction(String agr1, int arg2) {
    }

    @Attachment
    private String attachResult() {
        return "Text to be attached as a file";
    }

    @Attachment(type = "application/json", fileExtension = ".json")
    private String attachJson() {
        return "{\"key\":\"value\"}";
    }


}
