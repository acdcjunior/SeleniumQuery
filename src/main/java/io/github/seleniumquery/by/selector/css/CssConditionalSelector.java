package io.github.seleniumquery.by.selector.css;

import io.github.seleniumquery.by.selector.xpath.XPathExpression;

import java.util.Map;




import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.Selector;

public interface CssConditionalSelector<T extends Condition> {

	boolean isCondition(WebDriver driver, WebElement element, Map<String, String> stringMap, Selector selector, T condtition);

	XPathExpression conditionToXPath(Map<String, String> stringMap, Selector simpleSelector, T condition);
	
}