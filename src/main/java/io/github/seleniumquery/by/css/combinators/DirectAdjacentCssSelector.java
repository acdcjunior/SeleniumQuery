package io.github.seleniumquery.by.css.combinators;

import io.github.seleniumquery.by.SelectorUtils;
import io.github.seleniumquery.by.css.CssSelector;
import io.github.seleniumquery.by.css.CssSelectorMatcherService;
import io.github.seleniumquery.by.xpath.XPathSelectorCompilerService;
import io.github.seleniumquery.by.xpath.component.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.css.sac.SiblingSelector;

import java.util.Map;

/**
 * E + F
 *
 * @author acdcjunior
 * @since 0.9.0
 */
public class DirectAdjacentCssSelector implements CssSelector<SiblingSelector> {

	@Override
	public boolean is(WebDriver driver, WebElement element, Map<String, String> stringMap, SiblingSelector siblingSelector) {
		WebElement previousElement = SelectorUtils.getPreviousSibling(element);
		return CssSelectorMatcherService.elementMatchesSelector(driver, previousElement, stringMap, siblingSelector.getSelector())
				&& CssSelectorMatcherService.elementMatchesSelector(driver, element, stringMap, siblingSelector.getSiblingSelector());
	}

	@Override
	public XPathComponent toXPath(Map<String, String> stringMap, SiblingSelector siblingSelector) {
		XPathComponent previousCompiledExpression = XPathSelectorCompilerService.compileSelector(stringMap, siblingSelector.getSelector());
		XPathComponent siblingSelectorCompiledAdjacentExpression = XPathSelectorCompilerService.compileSelector(stringMap, siblingSelector.getSiblingSelector());

		XPathComponent positionOne = new SimpleConditionalComponent("[position() = 1]");
		XPathComponent siblingAtPositionOne = KeepTypeComponent.createKeepingType(siblingSelectorCompiledAdjacentExpression, positionOne);
		
		return AdjacentComponent.create(previousCompiledExpression, siblingAtPositionOne);
	}

}