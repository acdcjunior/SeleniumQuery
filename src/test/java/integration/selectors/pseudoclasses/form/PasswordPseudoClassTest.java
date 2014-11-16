package integration.selectors.pseudoclasses.form;

import static io.github.seleniumquery.SeleniumQuery.$;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import infrastructure.junitrule.SetUpAndTearDownDriver;

import org.junit.Rule;
import org.junit.Test;

public class PasswordPseudoClassTest {

	@Rule
	public SetUpAndTearDownDriver setUpAndTearDownDriverRule = new SetUpAndTearDownDriver(getClass());

	@Test
	public void passwordPseudoClass() {
		assertThat($("[type='password']").size(), is(4));
		assertThat($(":password").size(), is(1));
		assertThat($("*:password").size(), is(1));
		assertThat($("input:password").size(), is(1));
		assertThat($("div:password").size(), is(0));
		assertThat($("span:password").size(), is(0));

		assertThat($("#i1").is(":password"), is(true));
		assertThat($("#i1").is("*:password"), is(true));
		assertThat($("#i1").is("input:password"), is(true));

		assertThat($("#i2").is(":password"), is(false));
		assertThat($("#i3").is(":password"), is(false));
		assertThat($("#i4").is(":password"), is(false));
	}

}