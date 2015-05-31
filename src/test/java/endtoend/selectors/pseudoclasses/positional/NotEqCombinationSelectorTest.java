package endtoend.selectors.pseudoclasses.positional;

import static io.github.seleniumquery.SeleniumQuery.$;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import infrastructure.junitrule.SetUpAndTearDownDriver;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

public class NotEqCombinationSelectorTest {

	@ClassRule public static SetUpAndTearDownDriver setUpAndTearDownDriverRule = new SetUpAndTearDownDriver(EqPseudoClassTest.class);
	@Rule public SetUpAndTearDownDriver setUpAndTearDownDriverRuleInstance = setUpAndTearDownDriverRule;
	
	@Test
	public void not_eq__should_return_the_element_where_eq_is_applied_to_the_result_of_not() throws Exception {
		// given
		// when
		String text = $("div:not(.w00t):eq(0)").text();
		// then
		assertThat(text, is("Spider Man"));
	}
	
    @Test
    public void not_eq__should_return_the_text_when_not_does_not_change_the_result_and_eq_is_applied_to_valid_index() throws Exception {
    	// given
    	// when
        String text = $("div:not(span):eq(0)").text();
        // then
		assertThat(text, is("Batman"));
    }
    
    @Test
    public void not_eq__should_return_null_when_result_of_not_is_empty() throws Exception {
    	// given
    	// when
    	String text = $("div:not(div):eq(0)").text();
    	// then
		assertThat(text, is(""));
    }
    
    @Test
    public void not_eq__should_return_null_when_result_of_not_is_empty_no_matter_what_index_eq_is_applied_to() throws Exception {
    	// given
    	// when
    	String text = $("div:not(div):eq(1)").text();
    	// then
		assertThat(text, is(""));
    }
    
    @Test
    public void not_eq__should_return_null_when_eq_is_applied_to_index_outside_not_result_size() throws Exception {
    	// given
    	// when
    	String text = $("div:not(.w00t):eq(1)").text();
    	// then
		assertThat(text, is(""));
    }

}