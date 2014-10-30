package io.github.seleniumquery.selectors.pseudoclasses;

import io.github.seleniumquery.selector.DriverSupportService;
import io.github.seleniumquery.selector.SelectorUtils;
import io.github.seleniumquery.selectorxpath.XPathExpression;
import io.github.seleniumquery.selectorxpath.XPathSelectorFactory;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * https://developer.mozilla.org/en-US/docs/Web/CSS/:disabled
 * 
 * #Cross-Driver
 * HtmlUnitDriver has problems with :disabled, so we consider it can never be handler by the browser
 * by "problems" we mean it is inconsistent, changing depending on what browser it is attempting to emulate
 */
public class DisabledPseudoClass implements PseudoClass {

	private static final DisabledPseudoClass instance = new DisabledPseudoClass();
	public static DisabledPseudoClass getInstance() {
		return instance;
	}
	private DisabledPseudoClass() { }
	
	private static final String DISABLED_PSEUDO_CLASS_NO_COLON = "disabled";
	
	private static final String OPTGROUP = "optgroup";
	private static final String OPTION = "option";
	public static final List<String> DISABLEABLE_TAGS = Arrays.asList("input", "button", OPTGROUP, OPTION, "select", "textarea");

	@Override
	public boolean isApplicable(String pseudoClassValue) {
		return DISABLED_PSEUDO_CLASS_NO_COLON.equals(pseudoClassValue);
	}

	@Override
	public boolean isPseudoClass(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector) {
		// #Cross-Driver
		// When there is a not disabled <option> under a disabled <optgroup>, HtmlUnitDriver considers
		// the <option> to be enabled, when it is not
		if (DriverSupportService.isHtmlUnitDriver(driver) && OPTION.equals(element.getTagName())) {
			WebElement optionParent = SelectorUtils.parent(element);
			if (OPTGROUP.equals(optionParent.getTagName()) && !optionParent.isEnabled()) {
				return true;
			}
		}
		return !element.isEnabled() && DISABLEABLE_TAGS.contains(element.getTagName());
	}

	@Override
	public XPathExpression pseudoClassToXPath(PseudoClassSelector pseudoClassSelector) {
		return XPathSelectorFactory.createNoFilterSelector("[(@disabled and "
				+ "(local-name() = 'input' or "
				+ "local-name() = 'button' or "
				+ "local-name() = 'optgroup' or "
				+ "local-name() = 'option' or "
				+ "local-name() = 'select' or "
				+ "local-name() = 'textarea')"
				+ ") "
				+ " or (local-name() = 'option' and ancestor::select[@disabled])]");
	}

}