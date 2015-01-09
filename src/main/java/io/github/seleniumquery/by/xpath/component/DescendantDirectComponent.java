package io.github.seleniumquery.by.xpath.component;

import io.github.seleniumquery.by.xpath.UnsupportedConditionalSelector;

/**
 * cssA > cssB -> "/" -> xpathA/xpathB
 */
public class DescendantDirectComponent extends XPathComponent {

    public static TagComponent combine(TagComponent one, TagComponent other) {
        DescendantDirectComponent otherCopyWithModifiedType = new DescendantDirectComponent(other);
        return new TagComponent(one.xPathExpression,
                                ComponentUtils.joinComponents(one.combinatedComponents, otherCopyWithModifiedType),
                                ComponentUtils.joinFilters(one.elementFilterList, otherCopyWithModifiedType));
    }

    private DescendantDirectComponent(XPathComponent other) {
        super(other.xPathExpression, other.combinatedComponents, other.elementFilterList);
    }

    @Override
    public String mergeIntoExpression(String sourceXPathExpression) {
        if ("*".equals(this.xPathExpression)) {
            return sourceXPathExpression + "/*";
        }
        return sourceXPathExpression + "/*[self::" + this.xPathExpression + "]";
    }

    @Override
    public String mergeExpressionAsCondition(String sourceXPathExpression) {
        throw new UnsupportedConditionalSelector("The 'direct descendant' (>) selector is not supported as condition inside other selectors.");
    }

}