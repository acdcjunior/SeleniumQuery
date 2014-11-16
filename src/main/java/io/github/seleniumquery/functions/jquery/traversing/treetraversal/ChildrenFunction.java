package io.github.seleniumquery.functions.jquery.traversing.treetraversal;

import io.github.seleniumquery.SQLocalFactory;
import io.github.seleniumquery.SeleniumQueryObject;
import io.github.seleniumquery.by.selector.SelectorUtils;
import io.github.seleniumquery.by.css.CssSelectorMatcherService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * $("selector").children()
 *
 * @author acdcjunior
 * @since 1.0.0
 */
public class ChildrenFunction {

	public static SeleniumQueryObject children(SeleniumQueryObject caller, List<WebElement> elements) {
		List<WebElement> children = getChildren(elements);
		return SQLocalFactory.createWithInvalidSelector(caller.getWebDriver(), children, caller);
	}
	
	public static SeleniumQueryObject children(SeleniumQueryObject caller, List<WebElement> elements, String selector) {
		WebDriver webDriver = caller.getWebDriver();
		List<WebElement> children = getChildren(elements);
		for (Iterator<WebElement> iterator = children.iterator(); iterator.hasNext();) {
			WebElement child = iterator.next();
			if (!CssSelectorMatcherService.elementMatchesStringSelector(webDriver, child, selector)) {
				iterator.remove();
			}
		}
		return SQLocalFactory.createWithInvalidSelector(webDriver, children, caller);
	}
	
	private static List<WebElement> getChildren(List<WebElement> elements) {
		List<WebElement> children = new LinkedList<WebElement>();
		for (WebElement element : elements) {
			children.addAll(SelectorUtils.getDirectChildren(element));
		}
		return new ArrayList<WebElement>(children);
	}

}