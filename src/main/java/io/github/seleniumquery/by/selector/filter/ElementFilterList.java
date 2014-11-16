package io.github.seleniumquery.by.selector.filter;

import io.github.seleniumquery.selectors.pseudoclasses.UnsupportedPseudoClassException;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementFilterList {
	
	private List<ElementFilter> elementFilters;
	
	public ElementFilterList(List<ElementFilter> elementFilters) {
		this.elementFilters = elementFilters;
	}

	public List<WebElement> filter(WebDriver driver, List<WebElement> elements) {
		if (this.elementFilters.size() > 0) {
			// we are currently disabling the filter support
			// we will only take it back when the system is stable
			throw new UnsupportedPseudoClassException("The current selector is not yet supported. Please try a simpler one.");
		}
		for (ElementFilter elementFilter : elementFilters) {
			elements = elementFilter.filterElements(driver, elements);
		}
		return elements;
	}

}