package io.github.seleniumquery.selectors.pseudoclasses;

import io.github.seleniumquery.selectorxpath.XPathExpression;
import io.github.seleniumquery.selectorxpath.XPathSelectorFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LastPseudoClass implements PseudoClass {

	private static final LastPseudoClass instance = new LastPseudoClass();
	public static LastPseudoClass getInstance() {
		return instance;
	}
	private LastPseudoClass() { }

	private static final String LAST_PSEUDO_CLASS_NO_COLON = "last";
	
	@Override
	public boolean isApplicable(String pseudoClassValue) {
		return LAST_PSEUDO_CLASS_NO_COLON.equals(pseudoClassValue);
	}
	
	@Override
	public boolean isPseudoClass(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector) {
		return EqPseudoClass.isEq(driver, element, pseudoClassSelector, -1);
	}

	@Override
	public XPathExpression pseudoClassToXPath(PseudoClassSelector pseudoClassSelector) {
		return XPathSelectorFactory.createNoFilterSelectorAppliedToAll("[position() = last()]");
	}

}