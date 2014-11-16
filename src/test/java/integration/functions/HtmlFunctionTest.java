package integration.functions;

import static io.github.seleniumquery.SeleniumQuery.$;
import static io.github.seleniumquery.by.selector.DriverVersionUtils.isHtmlUnitDriverEmulatingIE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import infrastructure.junitrule.SetUpAndTearDownDriver;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

public class HtmlFunctionTest {
	
	@Rule
	public SetUpAndTearDownDriver setUpAndTearDownDriverRule = new SetUpAndTearDownDriver(getClass());

    @Test
    public void html_function__simple_element() {
    	assertThat($("span").html(), is("yo"));
    }
    
    @Test
    public void html_function__element_with_nested_element_with_no_line_breaks() {
    	if (isHtmlUnitDriverEmulatingIE($.browser.getDefaultDriver())) {
    		assertThat($("div:eq(1)").html(), equalsIgnoringCaseWhiteSpaceAndQuotes("Spider <div>\"The Spidey\"</div> Man"));
    	} else {
    		assertThat($("div:eq(1)").html(), is("Spider <div>\"The Spidey\"</div> Man"));
    	}
    }
    
    @Test
    public void html_function__element_with_nested_element_with_line_breaks() {
    	if (isHtmlUnitDriverEmulatingIE($.browser.getDefaultDriver())) {
	    	assertThat($("#nestedMultiLine").html(), equalsIgnoringCaseWhiteSpaceAndQuotes("ABC\n"+
	    			"    	<div>DEF</div>\n"+
	    			"    	<div>GHI</div>\n"+
	    			"    	JKY\n"+
	    			"    "));
    	} else {
	    	assertThat($("#nestedMultiLine").html(), is("ABC\n"+
			"    	<div>DEF</div>\n"+
			"    	<div>GHI</div>\n"+
			"    	JKY\n"+
			"    "));
    	}
    }
    
    @Test
    public void html_function__body() {
    	if (isHtmlUnitDriverEmulatingIE($.browser.getDefaultDriver())) {
	        assertThat($("body").html(),
    		equalsIgnoringCaseWhiteSpaceAndQuotes("\n"+
			"    <div>Batman</div>\n"+
			"    <div>Spider <div>\"The Spidey\"</div> Man</div>\n"+
			"    <div>Hulk</div>\n"+
			"    <span>yo</span>\n"+
			"    <div id=\"nestedMultiLine\">ABC\n"+
			"    	<div>DEF</div>\n"+
			"    	<div>GHI</div>\n"+
			"    	JKY\n"+
			"    </div>\n"));
    	} else {
	        assertThat($("body").html(),
	        is("\n"+
			"    <div>Batman</div>\n"+
			"    <div>Spider <div>\"The Spidey\"</div> Man</div>\n"+
			"    <div>Hulk</div>\n"+
			"    <span>yo</span>\n"+
			"    <div id=\"nestedMultiLine\">ABC\n"+
			"    	<div>DEF</div>\n"+
			"    	<div>GHI</div>\n"+
			"    	JKY\n"+
			"    </div>\n"));
    	}
    }
    
    
    /*
     * #Cross-Driver
     * html() function has different output in <=IE8 and HtmlUnit(IE)
     * This is acceptable as this is the way jQuery does it.
     */
    /**
     * This matcher, before comparing the strings:
     * - Converts them to lowercase
     * - Remove all their spaces
     * - Remove all their double quotes (")
     */
	private Matcher<String> equalsIgnoringCaseWhiteSpaceAndQuotes(final String strToMatch) {
		return new BaseMatcher<String>() {
			@Override
			public boolean matches(final Object item) {
				return fix(item.toString()).equals(fix(strToMatch));
			}
			@Override
			public void describeTo(final Description description) {
				description.appendText("String should equal \"").appendText(fix(strToMatch)).appendText("\"");
			}
			@Override
			public void describeMismatch(final Object item, final Description description) {
				description.appendText("was \"").appendText(fix(item.toString())).appendText("\"");
			}
		    private String fix(String htmlToFix) {
		    	String lowerCase = htmlToFix.toLowerCase();
		    	String noSpaces = lowerCase.replaceAll("\\s+|\"", "");
		    	return noSpaces.trim();
		    }
		};
	}

}