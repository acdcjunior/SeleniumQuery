package io.github.seleniumquery.selectorcss;

import io.github.seleniumquery.selectorxpath.XPathExpression;

import java.util.Map;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.Selector;

public interface CssConditionalSelector<T extends Condition> {

	boolean isCondition(WebDriver driver, WebElement element, Map<String, String> stringMap, Selector selector, T condtition);

	CompiledCssSelector compileCondition(WebDriver driver, Map<String, String> stringMap, Selector selector, T condition);

	XPathExpression conditionToXPath(Map<String, String> stringMap, Selector simpleSelector, T condition);
	
}