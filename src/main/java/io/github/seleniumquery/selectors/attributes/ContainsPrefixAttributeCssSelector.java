package io.github.seleniumquery.selectors.attributes;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;
import io.github.seleniumquery.selector.SelectorUtils;
import io.github.seleniumquery.selectorcss.CompiledCssSelector;
import io.github.seleniumquery.selectorcss.CssConditionalSelector;
import io.github.seleniumquery.selectorxpath.SqXPathSelector;
import io.github.seleniumquery.selectorxpath.XPathSelectorFactory;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.css.sac.AttributeCondition;
import org.w3c.css.sac.Selector;

public class ContainsPrefixAttributeCssSelector implements CssConditionalSelector<AttributeCondition> {

	private static final ContainsPrefixAttributeCssSelector instance = new ContainsPrefixAttributeCssSelector();

	public static ContainsPrefixAttributeCssSelector getInstance() {
		return instance;
	}
	
	private ContainsPrefixAttributeCssSelector() { }

	/**
	 * @see {@link org.w3c.css.sac.Condition#SAC_BEGIN_HYPHEN_ATTRIBUTE_CONDITION}
	 *
	 * This condition checks if the value is in a hypen-separated list of values in a specified attribute. example:
	 * [languages|="fr"]
	 * 
	 * Case INsensitve
	 */
	@Override
	public boolean isCondition(WebDriver driver, WebElement element, Map<String, String> stringMap, Selector selectorUpToThisPoint, AttributeCondition attributeCondition) {
		String wantedValue = attributeCondition.getValue();
		String actualValue = element.getAttribute(attributeCondition.getLocalName());
		return equalsIgnoreCase(actualValue, wantedValue) || startsWithIgnoreCase(actualValue, wantedValue+'-');
	}

	@Override
	public CompiledCssSelector compileCondition(WebDriver driver, Map<String, String> stringMap, Selector simpleSelector, AttributeCondition attributeCondition) {
		String attributeSelectorSymbol = "|=";
		// nothing to do, everyone supports this selector
		return AttributeEvaluatorUtils.createAttributeNoFilterCompiledSelector(attributeCondition, attributeSelectorSymbol);
	}

	@Override
	public SqXPathSelector conditionToXPath(WebDriver driver, Map<String, String> stringMap, Selector simpleSelector, AttributeCondition attributeCondition) {
		String attributeName = AttributeEvaluatorUtils.getXPathAttribute(attributeCondition);
		String wantedValue = SelectorUtils.intoEscapedXPathString(attributeCondition.getValue());
		String wantedValueWithSuffix = SelectorUtils.intoEscapedXPathString(attributeCondition.getValue() + "-");
		return XPathSelectorFactory.createNoFilterSelector("[("+attributeName+" = "+wantedValue+" or starts-with("+attributeName+", "+wantedValueWithSuffix+"))]");
	}
	
}