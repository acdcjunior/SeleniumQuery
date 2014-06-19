package io.github.seleniumquery.selector.conditionals.pseudoclasses;

import static io.github.seleniumquery.SeleniumQuery.$;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import io.github.seleniumquery.SetUpAndTearDownDriver;

import org.junit.Rule;
import org.junit.Test;

public class CheckedPseudoClassTest {
	
	@Rule
	public SetUpAndTearDownDriver setUpAndTearDownDriverRule = new SetUpAndTearDownDriver();
	
	@Test
	public void checkedPseudo() {
		assertThat($("*").size(), is(14+7+3+7));
		assertThat($(":checked").size(), is(4+7+7));
	}
	
	@Test
	public void checkedPseudo_with_tag_option() {
		assertThat($("option:checked").size(), is(2));
	}
	
	@Test
	public void checkedPseudo_with_tag_input() {
		assertThat($("input:checked").size(), is(2+7+7));
	}
	
	@Test
	public void checkedPseudo_with_tag_input_checkbox() {
		assertThat($("input[type=checkbox]:checked").size(), is(1+7));
	}
	
	@Test
	public void checkedPseudo_with_tag_input_radio() {
		assertThat($("input[type=radio]:checked").size(), is(1+7));
	}
    
    @Test
    public void  checked_selector_with_not() {
    	assertThat($(":not(:checked)").size(), is(10+3));
    }

    @Test
    public void  checked_selector_with_IS() {
    	assertThat($("#chk1").is(":checked"), is(true));
    	assertThat($("#chk2").is(":checked"), is(false));
    	
    	assertThat($("#rad1").is(":checked"), is(true));
    	assertThat($("#rad2").is(":checked"), is(false));
    	
    	assertThat($("#opt1").is(":checked"), is(true));
    	assertThat($("#opt2").is(":checked"), is(false));
    }

	@Test
	public void checkedPseudo__must_be_aware_of_input_type_but_not_checked_value() {
		assertThat($(".c").size(), is(17));
		assertThat($(".c:checked").size(), is(14));
	}
	
}