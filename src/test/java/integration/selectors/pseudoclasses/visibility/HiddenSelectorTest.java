package integration.selectors.pseudoclasses.visibility;

import infrastructure.junitrule.SetUpAndTearDownDriver;
import org.junit.ClassRule;
import org.junit.Test;

import static io.github.seleniumquery.SeleniumQuery.$;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HiddenSelectorTest {
	
	@ClassRule
	public static SetUpAndTearDownDriver setUpAndTearDownDriverRule = new SetUpAndTearDownDriver(HiddenSelectorTest.class);

    @Test
    public void hiddenPseudoClass() throws Exception {
    	assertThat($("#visibleDiv").is(":hidden"), is(false));
    	assertThat($("#visibleDiv2").is(":hidden"), is(false));
    	assertThat($("#invisibleDiv").is(":hidden"), is(true));
    	
    	assertThat($("#invisibleParentDiv").is(":hidden"), is(true));
    	assertThat($("#almostVisibleDiv").is(":hidden"), is(true));
    }

}