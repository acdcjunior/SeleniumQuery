package io.github.seleniumquery.selectors.pseudoclasses;

import io.github.seleniumquery.locator.ElementFilter;
import io.github.seleniumquery.selectorcss.CompiledCssSelector;
import io.github.seleniumquery.selectorcss.CssSelectorMatcherService;
import io.github.seleniumquery.selectorxpath.XPathExpression;
import io.github.seleniumquery.selectorxpath.XPathSelectorCompilerService;
import io.github.seleniumquery.selectorxpath.XPathSelectorFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NotPseudoClass implements PseudoClass {
	
	private static final NotPseudoClass instance = new NotPseudoClass();
	public static NotPseudoClass getInstance() {
		return instance;
	}
	private NotPseudoClass() { }
	
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
	public CompiledCssSelector compilePseudoClass(WebDriver driver, PseudoClassSelector pseudoClassSelector) {
		// https://developer.mozilla.org/en-US/docs/Web/CSS/:not

		// we never consider not to be supported natively, as it may contains not supported selectors in it
		// until we find a way to check if those selectors inside it are supported, we filter.
		
//		if (DriverSupportMap.getInstance().supportsNatively(driver, ":not(div)")) {
//			return CompiledSelector.createNoFilterSelector(":not("+pseudoClassValue+")");
//		}
		ElementFilter notPseudoClassFilter = new PseudoClassFilter(getInstance(), pseudoClassSelector);
		return CompiledCssSelector.createFilterOnlySelector(notPseudoClassFilter);
	}
	
	@Override
	public XPathExpression pseudoClassToXPath(PseudoClassSelector pseudoClassSelector) {
		String notSelector = pseudoClassSelector.getPseudoClassContent();
		String insideNotXPath = XPathSelectorCompilerService.compileSelectorList(notSelector).toXPathCondition();
		return XPathSelectorFactory.createNoFilterSelector("[not("+insideNotXPath+")]");
	}
	
}