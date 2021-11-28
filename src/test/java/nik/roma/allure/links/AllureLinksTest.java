package nik.roma.allure.links;

import io.qameta.allure.*;
import org.testng.annotations.Test;

@Feature("Allure Links")
public class AllureLinksTest {

    @Issue("JIRA-1")
    @Issue("JIRA-2")
    @Test
    public void verifyIssueAnnotation() {
    }

    @Issues({@Issue("Jira-3"), @Issue("Jira-4")})
    @Test
    public void verifyIssuesAnnotation() {
    }

    @TmsLink("TestRail-1")
    @TmsLink("TestRail-2")
    @Test
    public void verifyTmsLinkAnnotation() {
    }

    @TmsLinks({@TmsLink("TestRail-3"), @TmsLink("TestRail-4")})
    @Test
    public void verifyTmsLinksAnnotation() {
    }

    @Link("Link 1")
    @Link(type = "mylink", value = "Link 2")
    @Link(name = "Example 3", type = "mylink", value = "Link 3")
    @Link(type = "help", value = "Doc 4")
    @Test
    public void verifyLinkAnnotation() {
    }
}
