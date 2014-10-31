package io.github.seleniumquery.selectors.pseudoclasses;

import io.github.seleniumquery.selector.filter.ElementFilter;
import io.github.seleniumquery.selector.xpath.XPathExpression;
import io.github.seleniumquery.selector.xpath.XPathSelectorFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * https://developer.mozilla.org/en-US/docs/Web/CSS/:only-of-type
 * 
 * @author acdcjunior
 * @since 1.0.0
 */
public class OnlyOfTypePseudoClass implements PseudoClass {
	
	private static final String ONLY_OF_TYPE_PSEUDO_CLASS_NO_COLON = "only-of-type";

	private final ElementFilter onlyOfTypePseudoClassFilter = new PseudoClassFilter(this);

	@Override
	public boolean isApplicable(String pseudoClassValue) {
		return ONLY_OF_TYPE_PSEUDO_CLASS_NO_COLON.equals(pseudoClassValue);
	}

	@Override
	public boolean isPseudoClass(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector) {
		String tagName = element.getTagName();
		return driver.findElements(By.tagName(tagName)).size() == 1;
	}

	@Override
	public XPathExpression pseudoClassToXPath(PseudoClassSelector pseudoClassSelector) {
		UnsupportedXPathPseudoClassException.xPathFiltersAreNotImplementedYed(":only-of-type");
		
		// #no-xpath
		return XPathSelectorFactory.createFilterOnlySelector(onlyOfTypePseudoClassFilter);
	}
	
}