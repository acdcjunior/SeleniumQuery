package io.github.seleniumquery.selectors.pseudoclasses;

import io.github.seleniumquery.by.selector.xpath.XPathExpression;
import io.github.seleniumquery.by.selector.xpath.XPathExpressionFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * http://api.jquery.com/button-selector/
 * 
 * @author acdcjunior
 * @since 1.0.0
 */
public class ButtonPseudoClass implements PseudoClass {
	
	private static final String INPUT = "input";
	private static final String BUTTON = "button";
	
	@Override
	public boolean isApplicable(String pseudoClassValue) {
		return BUTTON.equals(pseudoClassValue);
	}
	
	@Override
	public boolean isPseudoClass(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector) {
		return (INPUT.equals(element.getTagName()) && BUTTON.equalsIgnoreCase(element.getAttribute("type")))
				||
			   BUTTON.equals(element.getTagName());
	}
	
	@Override
	public XPathExpression pseudoClassToXPath(PseudoClassSelector pseudoClassSelector) {
		return XPathExpressionFactory.createNoFilterSelector("[(local-name() = 'input' and @type = 'button') or local-name() = 'button']");
	}
	
}