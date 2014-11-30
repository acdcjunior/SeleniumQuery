package integration.functions.jquery.forms;

import infrastructure.junitrule.SetUpAndTearDownDriver;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static infrastructure.IntegrationTestUtils.equal;
import static infrastructure.IntegrationTestUtils.id;
import static infrastructure.IntegrationTestUtils.ids;
import static io.github.seleniumquery.SeleniumQuery.$;
import static io.github.seleniumquery.SeleniumQuery.jQuery;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SubmitFunctionTest {

    @ClassRule
    public static SetUpAndTearDownDriver setUpAndTearDownDriverRule = new SetUpAndTearDownDriver(SubmitFunctionTest.class);

    @Test
    public void submit_function() {
        assertOutput("");
        $("#input-a").submit();
        assertOutput("a");
        $("#div-a").submit();
        assertOutput("aa");
        $("#input-b").submit();
        assertOutput("aab");
        $("#div-b").submit();
        assertOutput("aabb");
        $("input").submit();
        assertOutput("aabbab");
        $("div").submit();
        assertOutput("aabbabab");
    }

    private void assertOutput(String value) {
        assertThat($("#out").text(), is(value));
    }

}