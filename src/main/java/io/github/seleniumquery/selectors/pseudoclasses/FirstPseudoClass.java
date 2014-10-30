package io.github.seleniumquery.selectors.pseudoclasses;

import io.github.seleniumquery.selectorxpath.XPathExpression;
import io.github.seleniumquery.selectorxpath.XPathSelectorFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FirstPseudoClass implements PseudoClass {

	private static final FirstPseudoClass instance = new FirstPseudoClass();
	public static FirstPseudoClass getInstance() {
		return instance;
	}
	private FirstPseudoClass() { }

	private static final String FIRST_PSEUDO_CLASS_NO_COLON = "first";
	
	
	@Override
	public boolean isApplicable(String pseudoClassValue) {
		return FIRST_PSEUDO_CLASS_NO_COLON.equals(pseudoClassValue);
	}
	
	@Override
	public boolean isPseudoClass(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector) {
		return EqPseudoClass.isEq(driver, element, pseudoClassSelector, 0);
	}

	@Override
	public XPathExpression pseudoClassToXPath(PseudoClassSelector pseudoClassSelector) {
		return XPathSelectorFactory.createNoFilterSelectorAppliedToAll("[position() = 1]");
	}

}