package io.github.seleniumquery.by.xpath;

import io.github.seleniumquery.by.preparser.CSSParsedSelectorList;
import io.github.seleniumquery.by.preparser.SelectorParser;
import io.github.seleniumquery.by.xpath.component.TagComponent;
import org.junit.Test;
import org.w3c.css.sac.SelectorList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class XPathComponentTest {

    @Test
    public void toXPath__id() {
        TagComponentList compileSelectorList = XPathSelectorCompilerService.compileSelectorList("#ball");
        assertThat(compileSelectorList.toXPath(), is("(.//*[@id = 'ball'])"));
    }

    @Test
    public void toXPath__and_conditional() {
        TagComponentList compileSelectorList = XPathSelectorCompilerService.compileSelectorList(".a.b");
        assertThat(compileSelectorList.toXPath(), is("(.//*[contains(concat(' ', normalize-space(@class), ' '), ' a ') and contains(concat(' ', normalize-space(@class), ' '), ' b ')])"));
    }

    @Test(expected = UnsupportedConditionalSelector.class)
    public void toXPath__not_body_div_span() {
        TagComponent xPathExpr = selectorToExpression("span:not(body div span)");
        String xPath = xPathExpr.toXPath();
        assertThat(xPath, is("(.//span[not(local-name() = 'span' and ancestor::div[ancestor::body])])"));
    }

    @Test
    public void toXPath__id_text() {
        TagComponent xPathExpr = selectorToExpression("#myID :text");
        String xPath = xPathExpr.toXPath();
        assertThat(xPath, is("(.//*[@id = 'myID']//*[self::input and (translate(@type,'TEXT','text') = 'text' or not(@type))])"));
    }

    @Test
    public void toXPath__only_child() {
        TagComponent xPathExpr = selectorToExpression("#idz a:only-child");
        String xPath = xPathExpr.toXPath();
        assertThat(xPath, is("(.//*[@id = 'idz']//*[self::a and ../*[last() = 1]])"));
    }

    public static TagComponent selectorToExpression(String selector) {
        CSSParsedSelectorList CSSParsedSelectorList = SelectorParser.parseSelector(selector);
        SelectorList selectorList = CSSParsedSelectorList.getSelectorList();
        return XPathSelectorCompilerService.compileSelector(CSSParsedSelectorList.getStringMap(), selectorList.item(0));
    }

    @Test
    public void toXPathCondition__first() {
        TagComponent xPathExpr = selectorToExpression(":first");
        String xPathCondition = xPathExpr.toXPathCondition();
        assertThat(xPathCondition, is("position() = 1"));
    }

    @Test
    public void toXPathCondition__div_first() {
        TagComponent xPathExpr = selectorToExpression("div:first");
        String xPathCondition = xPathExpr.toXPathCondition();
        assertThat(xPathCondition, is("local-name() = 'div' and position() = 1"));
    }

    @Test
    public void toXPathCondition__class() {
        TagComponent xPathExpr = selectorToExpression(".cls");
        String xPathCondition = xPathExpr.toXPathCondition();
        assertThat(xPathCondition, is("contains(concat(' ', normalize-space(@class), ' '), ' cls ')"));
    }

    @Test
    public void toXPathCondition__escaped_class() {
        TagComponent xPathExpr = selectorToExpression(".foo\\:bar");
        String xPathCondition = xPathExpr.toXPathCondition();
        assertThat(xPathCondition, is("contains(concat(' ', normalize-space(@class), ' '), ' foo:bar ')"));
    }

}