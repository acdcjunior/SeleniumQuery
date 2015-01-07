package io.github.seleniumquery.by.css.attributes;

import static org.apache.commons.lang3.StringUtils.endsWith;
import io.github.seleniumquery.by.SelectorUtils;
import io.github.seleniumquery.by.xpath.component.SimpleConditionalComponent;
import io.github.seleniumquery.by.xpath.component.XPathComponent;
import io.github.seleniumquery.by.css.CssConditionalSelector;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.css.sac.AttributeCondition;
import org.w3c.css.sac.Selector;

/**
 * [attribute$=stringToEnd]
 *
 * @author acdcjunior
 * @since 0.9.0
 */
public class EndsWithAttributeCssSelector implements CssConditionalSelector<AttributeCondition> {

	public static final String ENDS_WITH_ATTRIBUTE_SELECTOR_SYMBOL = "$=";

	/**
	 * Currently it is (mistakenly?) mapped to the type {@link org.w3c.css.sac.Condition#SAC_ATTRIBUTE_CONDITION}.
	 * The factory then inspects the actual type and redirects here.
	 * 
	 * This selector is:
	 * [attribute$=stringToEnd]
	 * 
	 * CASE SENSITIVE! http://api.jquery.com/attribute-ends-with-selector/
	 */
	@Override
	public boolean isCondition(WebDriver driver, WebElement element, Map<String, String> stringMap, Selector selectorUpToThisPoint, AttributeCondition attributeCondition) {
		String wantedValue = attributeCondition.getValue();
		String actualValue = element.getAttribute(attributeCondition.getLocalName());
		return endsWith(actualValue, wantedValue);
	}

	@Override
	public XPathComponent conditionToXPath(Map<String, String> stringMap, Selector simpleSelector, AttributeCondition attributeCondition) {
		String attributeName = AttributeEvaluatorUtils.getXPathAttribute(attributeCondition);
		String attrValue = attributeCondition.getValue();
		String wantedValue = SelectorUtils.intoEscapedXPathString(attrValue);
		return new SimpleConditionalComponent("[substring(" + attributeName + ", string-length(" + attributeName + ")-" + (attrValue.length() - 1) + ") = " + wantedValue + "]");
	}

}