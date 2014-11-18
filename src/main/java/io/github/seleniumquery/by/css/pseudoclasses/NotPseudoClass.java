package io.github.seleniumquery.by.css.pseudoclasses;

import io.github.seleniumquery.by.xpath.XPathExpression;
import io.github.seleniumquery.by.xpath.XPathSelectorCompilerService;
import io.github.seleniumquery.by.xpath.XPathExpressionFactory;
import io.github.seleniumquery.by.css.CssSelectorMatcherService;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * https://developer.mozilla.org/en-US/docs/Web/CSS/:not
 * 
 * @author acdcjunior
 * @since 0.9.0
 */
public class NotPseudoClass implements PseudoClass {
	
	@Override
	public boolean isApplicable(String pseudoClassValue) {
		return pseudoClassValue.matches("not-sq\\(.*\\)");
	}
	
	@Override
	public boolean isPseudoClass(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector) {
		String notSelector = pseudoClassSelector.getPseudoClassContent();
		return !CssSelectorMatcherService.elementMatchesStringSelector(driver, element, notSelector);
	}
	
	@Override
	public XPathExpression pseudoClassToXPath(PseudoClassSelector pseudoClassSelector) {
		String notSelector = pseudoClassSelector.getPseudoClassContent();
		String insideNotXPath = XPathSelectorCompilerService.compileSelectorList(notSelector).toXPathCondition();
		return XPathExpressionFactory.createNoFilterSelector("[not(" + insideNotXPath + ")]");
	}
	
}