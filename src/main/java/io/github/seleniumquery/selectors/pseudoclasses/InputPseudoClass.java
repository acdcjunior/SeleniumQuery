package io.github.seleniumquery.selectors.pseudoclasses;

import io.github.seleniumquery.by.selector.xpath.XPathExpression;
import io.github.seleniumquery.by.selector.xpath.XPathExpressionFactory;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * http://api.jquery.com/input-selector/
 * 
 * @author acdcjunior
 * @since 1.0.0
 */
public class InputPseudoClass implements PseudoClass {
	
	private static final List<String> FORM_ELEMENT_TAGS = Arrays.asList("input", "button", "select", "textarea");
	
	private static final String INPUT = "input";
	
	@Override
	public boolean isApplicable(String pseudoClassValue) {
		return INPUT.equals(pseudoClassValue);
	}
	
	@Override
	public boolean isPseudoClass(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector) {
		return FORM_ELEMENT_TAGS.contains(element.getTagName());
	}
	
	@Override
	public XPathExpression pseudoClassToXPath(PseudoClassSelector pseudoClassSelector) {
		return XPathExpressionFactory.createNoFilterSelector("[(local-name() = 'input' or local-name() = 'button' or local-name() = 'select' or local-name() = 'textarea')]");
	}
	
}