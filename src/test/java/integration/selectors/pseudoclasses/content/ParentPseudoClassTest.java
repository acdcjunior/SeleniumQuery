package integration.selectors.pseudoclasses.content;

import static io.github.seleniumquery.SeleniumQuery.$;
import static io.github.seleniumquery.selector.DriverSupportService.isHtmlUnitDriverEmulatingIE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import infrastructure.junitrule.SetUpAndTearDownGivenDriver;

import org.junit.Rule;
import org.junit.Test;

public class ParentPseudoClassTest {
	
	@Rule
	public SetUpAndTearDownGivenDriver setUpAndTearDownGivenDriverRule = new SetUpAndTearDownGivenDriver(getClass());
	
	// http://jsbin.com/lutim/6/edit
	@Test
	public void parentPseudoClass() {
		assertThat($("#d1").is(":parent"), is(true));
		assertThat($("#d2").is(":parent"), is(false));
		
		if (isHtmlUnitDriverEmulatingIE($.browser.getDefaultDriver())) {
			assertThat($("#d3").is(":parent"), is(false));
			assertThat($("#d4").is(":parent"), is(false));
		} else {
			assertThat($("#d3").is(":parent"), is(true));
			assertThat($("#d4").is(":parent"), is(true));
		}
		assertThat($("#d5").is(":parent"), is(true));

		assertThat($("#d10").is(":parent"), is(true));
		assertThat($("#d11").is(":parent"), is(false));
		assertThat($("#d12").is(":parent"), is(true));
		assertThat($("#d13").is(":parent"), is(false));
		
		if (isHtmlUnitDriverEmulatingIE($.browser.getDefaultDriver())) {
			assertThat($("#d14").is(":parent"), is(false));
		} else {
			assertThat($("#d14").is(":parent"), is(true));
		}
	}
	
}