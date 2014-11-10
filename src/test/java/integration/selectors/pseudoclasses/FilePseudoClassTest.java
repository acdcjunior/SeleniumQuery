package integration.selectors.pseudoclasses;

import static io.github.seleniumquery.SeleniumQuery.$;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import infrastructure.junitrule.SetUpAndTearDownGivenDriver;

import org.junit.Rule;
import org.junit.Test;

public class FilePseudoClassTest {

	@Rule
	public SetUpAndTearDownGivenDriver setUpAndTearDownGivenDriverRule = new SetUpAndTearDownGivenDriver(getClass());

	@Test
	public void filePseudoClass() {
		assertThat($("[type='file']").size(), is(4));
		assertThat($(":file").size(), is(1));
		assertThat($("*:file").size(), is(1));
		assertThat($("input:file").size(), is(1));
		assertThat($("div:file").size(), is(0));
		assertThat($("span:file").size(), is(0));

		assertThat($("#i1").is(":file"), is(true));
		assertThat($("#i1").is("*:file"), is(true));
		assertThat($("#i1").is("input:file"), is(true));

		assertThat($("#i2").is(":file"), is(false));
		assertThat($("#i3").is(":file"), is(false));
		assertThat($("#i4").is(":file"), is(false));
	}

}