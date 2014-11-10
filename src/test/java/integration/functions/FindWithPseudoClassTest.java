package integration.functions;

import static io.github.seleniumquery.SeleniumQuery.$;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import infrastructure.junitrule.SetUpAndTearDownGivenDriver;
import io.github.seleniumquery.selector.xpath.XPathExpressionList;
import io.github.seleniumquery.selector.xpath.XPathSelectorCompilerService;

import org.junit.Rule;
import org.junit.Test;

public class FindWithPseudoClassTest {

	@Rule
	public SetUpAndTearDownGivenDriver setUpAndTearDownGivenDriverRule = new SetUpAndTearDownGivenDriver(getClass());
	
    @Test
    public void find_function_with_pseudo_optionChecked() {
        assertThat($("#ano").size(), is(1));
        assertThat($("#ano").find("option:checked").text().trim(), is("(XYZ)"));
    }
    
	@Test
	public void find_function_with_pseudo_optionChecked_COMPILATION() {
		XPathExpressionList compileSelectorList = XPathSelectorCompilerService.compileSelectorList("option:checked");
		String x = "(.//option[((local-name() = 'input' and (@type = 'radio' or @type = 'checkbox') and @checked) or (local-name() = 'option' and @selected))])";
		assertThat(compileSelectorList.toXPath(), is(x));
	}

}