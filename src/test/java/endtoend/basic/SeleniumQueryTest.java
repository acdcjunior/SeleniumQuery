package endtoend.basic;

import io.github.seleniumquery.SeleniumQuery;
import io.github.seleniumquery.SeleniumQueryObject;
import io.github.seleniumquery.browser.BrowserFunctionsWithDeprecatedFunctions;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import testinfrastructure.EndToEndTestUtils;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static testinfrastructure.testutils.SeleniumQueryObjectTestUtils.verifySeleniumQueryObjectHasElements;

public class SeleniumQueryTest {

    String testPageUrl = EndToEndTestUtils.classNameToTestFileUrl(SeleniumQueryTest.class);
    WebElement d1;
    WebElement d2;

    @Test
    public void the_SeleniumQuery_constructor_should_not_throw_exception_or_do_anything_noticeable() {
        new SeleniumQuery();
    }

    @Test
    public void $_field() {
        verifySeleniumQueryFieldAliasWorks(SeleniumQuery.$);
    }

    @Test
    public void sQ_field() {
        verifySeleniumQueryFieldAliasWorks(SeleniumQuery.sQ);
    }

    @Test
    public void jQuery_field() {
        verifySeleniumQueryFieldAliasWorks(SeleniumQuery.jQuery);
    }

    private void verifySeleniumQueryFieldAliasWorks(BrowserFunctionsWithDeprecatedFunctions seleniumQueryField) {
        // given
        HtmlUnitDriver driver = new HtmlUnitDriver();
        // when
        seleniumQueryField.driver().use(driver);
        seleniumQueryField.url(testPageUrl);
        // then
        assertThat(driver.findElementById("d1").getText(), is("d1-text"));
    }

    @Test
    public void $_selector_function() {
        // given
        openSeleniumQueryWithHtmlUnitDriverAtTestPageAndLoadD1AndD2Fields();
        // when
        WebElement webElement = SeleniumQuery.$("#d1").get(0);
        // then
        assertThat(webElement, is(d1));
    }

    @Test
    public void sQ_selector_function() {
        // given
        openSeleniumQueryWithHtmlUnitDriverAtTestPageAndLoadD1AndD2Fields();
        // when
        WebElement webElement = SeleniumQuery.sQ("#d1").get(0);
        // then
        assertThat(webElement, is(d1));
    }

    @Test
    public void jQuery_selector_function() {
        // given
        openSeleniumQueryWithHtmlUnitDriverAtTestPageAndLoadD1AndD2Fields();
        // when
        WebElement webElement = SeleniumQuery.jQuery("#d1").get(0);
        // then
        assertThat(webElement, is(d1));
    }

    @Test
    public void $_WebElementArray_function() {
        // given
        openSeleniumQueryWithHtmlUnitDriverAtTestPageAndLoadD1AndD2Fields();
        // when
        SeleniumQueryObject seleniumQueryObject = SeleniumQuery.$(d1, d2);
        // then
        verifySeleniumQueryObjectHasElements(seleniumQueryObject, d1, d2);
    }

    @Test
    public void sQ_WebElementArray_function() {
        // given
        openSeleniumQueryWithHtmlUnitDriverAtTestPageAndLoadD1AndD2Fields();
        // when
        SeleniumQueryObject seleniumQueryObject = SeleniumQuery.sQ(d1, d2);
        // then
        verifySeleniumQueryObjectHasElements(seleniumQueryObject, d1, d2);
    }

    @Test
    public void jQuery_WebElementArray_function() {
        // given
        openSeleniumQueryWithHtmlUnitDriverAtTestPageAndLoadD1AndD2Fields();
        // when
        SeleniumQueryObject seleniumQueryObject = SeleniumQuery.jQuery(d1, d2);
        // then
        verifySeleniumQueryObjectHasElements(seleniumQueryObject, d1, d2);
    }

    @Test
    public void $_WebElementList_function() {
        // given
        openSeleniumQueryWithHtmlUnitDriverAtTestPageAndLoadD1AndD2Fields();
        // when
        SeleniumQueryObject seleniumQueryObject = SeleniumQuery.$(asList(d1, d2));
        // then
        verifySeleniumQueryObjectHasElements(seleniumQueryObject, d1, d2);
    }

    @Test
    public void sQ_WebElementList_function() {
        // given
        openSeleniumQueryWithHtmlUnitDriverAtTestPageAndLoadD1AndD2Fields();
        // when
        SeleniumQueryObject seleniumQueryObject = SeleniumQuery.sQ(asList(d1, d2));
        // then
        verifySeleniumQueryObjectHasElements(seleniumQueryObject, d1, d2);
    }

    @Test
    public void jQuery_WebElementList_function() {
        // given
        openSeleniumQueryWithHtmlUnitDriverAtTestPageAndLoadD1AndD2Fields();
        // when
        SeleniumQueryObject seleniumQueryObject = SeleniumQuery.jQuery(asList(d1, d2));
        // then
        verifySeleniumQueryObjectHasElements(seleniumQueryObject, d1, d2);
    }

    private void openSeleniumQueryWithHtmlUnitDriverAtTestPageAndLoadD1AndD2Fields() {
        HtmlUnitDriver driver = new HtmlUnitDriver();
        SeleniumQuery.$.driver().use(driver);
        SeleniumQuery.$.url(testPageUrl);
        this.d1 =  SeleniumQuery.$.driver().get().findElement(By.id("d1"));
        this.d2 =  SeleniumQuery.$.driver().get().findElement(By.id("d2"));
    }

}