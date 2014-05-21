package io.github.seleniumquery.selectors.conditionals;

import java.util.Map;

import io.github.seleniumquery.selector.CompiledCssSelector;
import io.github.seleniumquery.selector.CssConditionalSelector;
import io.github.seleniumquery.selector.CssFilterUtils;
import io.github.seleniumquery.selector.CssSelector;
import io.github.seleniumquery.selector.CssSelectorCompilerService;
import io.github.seleniumquery.selector.CssSelectorMatcherService;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SimpleSelector;

public class ConditionalCssSelector implements CssSelector<ConditionalSelector> {
	
	private static final ConditionalCssSelector instance = new ConditionalCssSelector();
	public static ConditionalCssSelector getInstance() {
		return instance;
	}
	
	@Override
	public boolean is(WebDriver driver, WebElement element, Map<String, String> stringMap, ConditionalSelector conditionalSelector) {
		Condition condition = conditionalSelector.getCondition();
		SimpleSelector simpleSelector = conditionalSelector.getSimpleSelector();
		return CssSelectorMatcherService.elementMatchesSelector(driver, element, stringMap, simpleSelector)
				&& isCondition(driver, element, stringMap, simpleSelector, condition);
	}
	
	@Override
	public CompiledCssSelector compile(WebDriver driver, Map<String, String> stringMap, ConditionalSelector conditionalSelector) {
		Condition condition = conditionalSelector.getCondition();
		SimpleSelector simpleSelector = conditionalSelector.getSimpleSelector();
		CompiledCssSelector compiledSelector = CssSelectorCompilerService.compileSelector(driver, stringMap, simpleSelector);
		CompiledCssSelector compiledCondition = compileCondition(driver, stringMap, simpleSelector, condition);
		return CssFilterUtils.combine(compiledSelector, compiledCondition);
	}
	
	/**
	 * Gets the given condition's CssSelector and tests if the element matches it. 
	 */
	boolean isCondition(WebDriver driver, WebElement element, Map<String, String> stringMap, Selector simpleSelector, Condition condition) {
		@SuppressWarnings("unchecked")
		CssConditionalSelector<Condition> evaluator = (CssConditionalSelector<Condition>) ConditionalCssSelectorFactory.getInstance().getSelector(condition);
		return evaluator.isCondition(driver, element, stringMap, simpleSelector, condition);
	}

	CompiledCssSelector compileCondition(WebDriver driver, Map<String, String> stringMap, Selector simpleSelector, Condition condition) {
		@SuppressWarnings("unchecked")
		CssConditionalSelector<Condition> evaluator = (CssConditionalSelector<Condition>) ConditionalCssSelectorFactory.getInstance().getSelector(condition);
		return evaluator.compileCondition(driver, stringMap, simpleSelector, condition);
	}

}