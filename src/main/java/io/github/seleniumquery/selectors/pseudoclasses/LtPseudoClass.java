package io.github.seleniumquery.selectors.pseudoclasses;

import io.github.seleniumquery.locator.ElementFilter;
import io.github.seleniumquery.selectorcss.CompiledCssSelector;
import io.github.seleniumquery.selectorcss.CssSelectorCompilerService;
import io.github.seleniumquery.selectorxpath.XPathExpression;
import io.github.seleniumquery.selectorxpath.XPathSelectorFactory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LtPseudoClass implements PseudoClass {

	private static final LtPseudoClass instance = new LtPseudoClass();
	public static LtPseudoClass getInstance() {
		return instance;
	}
	private LtPseudoClass() { }

	@Override
	public boolean isApplicable(String pseudoClassValue) {
		return pseudoClassValue.matches("lt\\(.*\\)");
	}

	@Override
	public boolean isPseudoClass(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector) {
		String ltIndex = pseudoClassSelector.getPseudoClassContent();
		if (!ltIndex.matches("[+-]?\\d+")) {
			throw new RuntimeException("The :lt() pseudo-class requires an integer but got: " + ltIndex);
		}
		if (ltIndex.charAt(0) == '+') {
			ltIndex = ltIndex.substring(1);
		}
		int index = Integer.valueOf(ltIndex);
		
		return LtPseudoClass.isLt(driver, element, pseudoClassSelector, index);
	}
	
	private static boolean isLt(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector, int wantedIndex) {
		if (wantedIndex == 0) {
			return false;
		}
		CompiledCssSelector compileSelector = CssSelectorCompilerService.compileSelector(driver, pseudoClassSelector.getStringMap(), pseudoClassSelector.getSelector());
		List<WebElement> elements = compileSelector.execute(driver);
		if (elements.isEmpty()) {
			return false;
		}
		int actuallyWantedIndex = wantedIndex;
		if (wantedIndex < 0) {
			actuallyWantedIndex = elements.size() + wantedIndex;
		}
		
		if (elements.size() <= actuallyWantedIndex) {
			return true;
		}
		int indexFound = elements.indexOf(element);
		if (indexFound == -1) {
			return false;
		}
		return indexFound < actuallyWantedIndex;
	}

	@Override
	public CompiledCssSelector compilePseudoClass(WebDriver driver, PseudoClassSelector pseudoClassSelector) {
		// :lt() is an extension selector, no browser implements it natively
		ElementFilter ltPseudoClassFilter = new PseudoClassFilter(getInstance(), pseudoClassSelector);
		return CompiledCssSelector.createFilterOnlySelector(ltPseudoClassFilter);
	}
	
	@Override
	public XPathExpression pseudoClassToXPath(WebDriver driver, PseudoClassSelector pseudoClassSelector) {
		String eqIndex = pseudoClassSelector.getPseudoClassContent();
		if (!eqIndex.matches("[+-]?\\d+")) {
			throw new RuntimeException("The :lt() pseudo-class requires an integer but got: " + eqIndex);
		}
		if (eqIndex.charAt(0) == '+') {
			eqIndex = eqIndex.substring(1);
		}
		int index = Integer.valueOf(eqIndex);
		
		if (index >= 0) {
			return XPathSelectorFactory.createNoFilterSelectorAppliedToAll("[position() < "+(index+1)+"]");
		}
		return XPathSelectorFactory.createNoFilterSelectorAppliedToAll("[position() < (last()-"+ (-index-1) +")]");
	}

}