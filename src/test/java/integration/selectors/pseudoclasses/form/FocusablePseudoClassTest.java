package integration.selectors.pseudoclasses.form;

import static io.github.seleniumquery.SeleniumQuery.$;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import infrastructure.junitrule.SetUpAndTearDownGivenDriver;
import io.github.seleniumquery.selectors.pseudoclasses.UnsupportedXPathPseudoClassException;

import org.junit.Rule;
import org.junit.Test;

public class FocusablePseudoClassTest {
	
	@Rule
	public SetUpAndTearDownGivenDriver setUpAndTearDownGivenDriverRule = new SetUpAndTearDownGivenDriver(getClass());
	
	@Test(expected = UnsupportedXPathPseudoClassException.class)
	public void focusablePseudoClass() {
		assertThat($("body > *").size(), is(6));
		assertThat($(":focusable").size(), is(3));
		
		assertThat($("input:focusable").size(), is(1));
		assertThat($("a:focusable").size(), is(1));
		assertThat($("p:focusable").size(), is(1));
		
		assertThat($("#i1").is(":focusable"), is(false));
		assertThat($("#i2").is(":focusable"), is(true));
		assertThat($("#a1").is(":focusable"), is(false));
		assertThat($("#a2").is(":focusable"), is(true));
		assertThat($("#p1").is(":focusable"), is(false));
		assertThat($("#p2").is(":focusable"), is(true));
	}
	
}