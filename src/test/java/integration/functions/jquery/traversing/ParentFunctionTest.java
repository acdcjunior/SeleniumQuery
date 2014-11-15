package integration.functions.jquery.traversing;

import infrastructure.junitrule.SetUpAndTearDownGivenDriver;
import org.junit.Rule;
import org.junit.Test;

import static infrastructure.IntegrationTestUtils.*;
import static io.github.seleniumquery.SeleniumQuery.jQuery;
import static java.util.Arrays.asList;

public class ParentFunctionTest {
	
	@Rule
	public SetUpAndTearDownGivenDriver setUpAndTearDownGivenDriverRule = new SetUpAndTearDownGivenDriver(getClass());
	
    @Test
    public void parent_function() {
		equal(id(jQuery("#groups").parent()), "ap", "Simple parent check");
		equal(id(jQuery("#groups").parent("p")), "ap", "Filtered parent check");
		equal(jQuery("#groups").parent("div").size(), 0, "Filtered parent check, no match");
		equal(id(jQuery("#groups").parent("div, p")), "ap", "Check for multiple filters");
		equal(jQuery("#en, #sndp").parent().get(), jQuery("#foo").get(), "Check for unique results from parent");

		equal(ids(jQuery(".multiple").parent()), asList("div_a", "div_b", "div_c", "div_d"), "Several children, several parents");
		equal(ids(jQuery(".multiple").parent("div")), asList("div_a", "div_b", "div_c", "div_d"), "Several children, every parent matches selector");
		equal(ids(jQuery(".multiple").parent("#div_a, #div_d")), asList("div_a", "div_d"), "Several children, not every parent matches selector");
		equal(jQuery(".multiple").parent("iv").size(), 0, "Several children, no parent matches selector");
		equal(ids(jQuery(".multiple").parent("#div_a")), asList("div_a"), "Several children, only one parent matches selector");
		equal(ids(jQuery(".multiple").parent("#div_b")), asList("div_b"), "Several children, only one parent matches selector");
		equal(ids(jQuery(".multiple").parent("#div_c")), asList("div_c"), "Several children, only one parent matches selector");
		equal(ids(jQuery(".multiple").parent("#div_d")), asList("div_d"), "Several children, only one parent matches selector");
    }

}