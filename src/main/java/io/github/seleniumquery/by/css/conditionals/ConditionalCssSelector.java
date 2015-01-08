package io.github.seleniumquery.by.css.conditionals;

import io.github.seleniumquery.by.xpath.component.ComponentUtils;
import io.github.seleniumquery.by.xpath.component.TagComponent;
import io.github.seleniumquery.by.xpath.component.XPathComponent;
import io.github.seleniumquery.by.xpath.XPathSelectorCompilerService;
import io.github.seleniumquery.by.css.CssConditionalSelector;
import io.github.seleniumquery.by.css.CssSelector;
import io.github.seleniumquery.by.css.CssSelectorMatcherService;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SimpleSelector;


public class ConditionalCssSelector implements CssSelector<ConditionalSelector, TagComponent> {

    private final ConditionalCssSelectorFactory conditionalCssSelectorFactory = new ConditionalCssSelectorFactory(this);

    @Override
	public boolean is(WebDriver driver, WebElement element, Map<String, String> stringMap, ConditionalSelector conditionalSelector) {
		Condition condition = conditionalSelector.getCondition();
		SimpleSelector simpleSelector = conditionalSelector.getSimpleSelector();
		return CssSelectorMatcherService.elementMatchesSelector(driver, element, stringMap, simpleSelector)
				&& isCondition(driver, element, stringMap, simpleSelector, condition);
	}
	
	@Override
	public TagComponent toXPath(Map<String, String> stringMap, ConditionalSelector conditionalSelector) {
		Condition condition = conditionalSelector.getCondition();
		SimpleSelector simpleSelector = conditionalSelector.getSimpleSelector();
		TagComponent tagComponent = XPathSelectorCompilerService.compileSelector(stringMap, simpleSelector);
		XPathComponent compiledCondition = conditionToXPath(stringMap, simpleSelector, condition);
		return ComponentUtils.combineKeepingTypeOfFirstArg(tagComponent, compiledCondition);
	}
	
	/**
	 * Gets the given condition's CssSelector and tests if the element matches it. 
	 */
	boolean isCondition(WebDriver driver, WebElement element, Map<String, String> stringMap, Selector simpleSelector, Condition condition) {
		@SuppressWarnings("unchecked")
		CssConditionalSelector<Condition> evaluator = (CssConditionalSelector<Condition>) conditionalCssSelectorFactory.getSelector(condition);
		return evaluator.isCondition(driver, element, stringMap, simpleSelector, condition);
	}

	XPathComponent conditionToXPath(Map<String, String> stringMap, Selector simpleSelector, Condition condition) {
		@SuppressWarnings("unchecked")
		CssConditionalSelector<Condition> evaluator = (CssConditionalSelector<Condition>) conditionalCssSelectorFactory.getSelector(condition);
		return evaluator.conditionToXPath(stringMap, simpleSelector, condition);
	}

}