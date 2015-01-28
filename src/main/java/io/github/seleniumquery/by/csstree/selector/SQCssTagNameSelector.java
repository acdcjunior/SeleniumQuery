package io.github.seleniumquery.by.csstree.selector;

import io.github.seleniumquery.by.locator.SQLocator;
import io.github.seleniumquery.by.locator.SQLocatorUtils;

/**
 * Element or tag selector. Example: {@code "div"}.
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class SQCssTagNameSelector implements SQCssSelector {

    private String tagName;

    public SQCssTagNameSelector(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    @Override
    public SQLocator toSQLocator() {
        return new SQLocator(toCSS(), ".//*[" + toXPath() + "]");
    }

    @Override
    public SQLocator toSQLocator(SQLocator leftLocator) {
        String newCssSelector = SQLocatorUtils.cssMerge(leftLocator.getCssSelector(), toCSS());
        String newXPathExpression = SQLocatorUtils.conditionalSimpleXPathMerge(leftLocator.getXPathExpression(), toXPath());
        return new SQLocator(newCssSelector, newXPathExpression, leftLocator);
    }

    private String toXPath() {
        return "*".equals(this.tagName) ? "true()" : "self::"+tagName;
    }

    private String toCSS() {
        return this.tagName;
    }

}