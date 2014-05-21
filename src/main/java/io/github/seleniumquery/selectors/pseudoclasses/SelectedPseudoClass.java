package io.github.seleniumquery.selectors.pseudoclasses;

import io.github.seleniumquery.selector.CompiledCssSelector;
import io.github.seleniumquery.selector.CssFilter;
import io.github.seleniumquery.selector.DriverSupportService;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SelectedPseudoClass implements PseudoClass {
	
	private static final SelectedPseudoClass instance = new SelectedPseudoClass();
	public static SelectedPseudoClass getInstance() {
		return instance;
	}
	private SelectedPseudoClass() { }
	
	private static final String OPTION_TAG = "option";
	
	private static final String SELECTED_PSEUDO_CLASS_NO_COLON = "selected";
	private static final String SELECTED_PSEUDO_CLASS = ":"+SELECTED_PSEUDO_CLASS_NO_COLON;

	@Override
	public boolean isApplicable(String pseudoClassValue) {
		return SELECTED_PSEUDO_CLASS_NO_COLON.equals(pseudoClassValue);
	}
	
	@Override
	public boolean isPseudoClass(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector) {
		return isSelected(element);
	}
	
	public boolean isSelected(WebElement element) {
		return element.getTagName().equals(OPTION_TAG) && element.isSelected();
	}
	
	private static final CssFilter selectedPseudoClassFilter = new PseudoClassFilter(getInstance());
	@Override
	public CompiledCssSelector compilePseudoClass(WebDriver driver, PseudoClassSelector pseudoClassSelector) {
		// https://developer.mozilla.org/en-US/docs/Web/CSS/:selected
		if (DriverSupportService.getInstance().supportsNatively(driver, SELECTED_PSEUDO_CLASS)) {
			return CompiledCssSelector.createNoFilterSelector(SELECTED_PSEUDO_CLASS);
		}
		return CompiledCssSelector.createFilterOnlySelector(selectedPseudoClassFilter);
	}

}