package io.github.seleniumquery.functions.jquery.traversing.treetraversal;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebElement;
import io.github.seleniumquery.ObjectLocalFactory;
import io.github.seleniumquery.SeleniumQueryObject;
import io.github.seleniumquery.by.SeleniumQueryBy;

/**
 * $("selector").find("selector")
 *
 * @author acdcjunior
 * @since 0.9.0
 */
public class FindFunction {

	public static SeleniumQueryObject find(SeleniumQueryObject seleniumQueryObject, List<WebElement> elements, String selector) {
		List<WebElement> allElementsBelow = new LinkedList<WebElement>();
		SeleniumQueryBy by = SeleniumQueryBy.byEnhancedSelector(selector);
		for (WebElement webElement : elements) {
			List<WebElement> elementsBelowThisElement = webElement.findElements(by);
			allElementsBelow.addAll(elementsBelowThisElement);
		}
		return ObjectLocalFactory.createWithInvalidSelector(seleniumQueryObject.getWebDriver(), allElementsBelow, seleniumQueryObject);
	}

}