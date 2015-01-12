package io.github.seleniumquery.by.parser.translator.selector;

import io.github.seleniumquery.by.parser.translator.condition.SQCssConditionTranslator;
import io.github.seleniumquery.by.parser.parsetree.condition.SQCssCondition;
import io.github.seleniumquery.by.parser.parsetree.selector.SQCssConditionalSelector;
import io.github.seleniumquery.by.parser.parsetree.selector.SQCssSelector;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.SimpleSelector;

import java.util.Map;

public class SQCssConditionalSelectorTranslator {

	private final SQCssSelectorTranslator sqCssSelectorTranslator;
	private final SQCssConditionTranslator sqCssConditionTranslator = new SQCssConditionTranslator(this);

	public SQCssConditionalSelectorTranslator(SQCssSelectorTranslator sqCssSelectorTranslator) {
		this.sqCssSelectorTranslator = sqCssSelectorTranslator;
	}


	public SQCssConditionalSelector translate(Map<String, String> stringMap, ConditionalSelector conditionalSelector) {
		Condition condition = conditionalSelector.getCondition();
		SimpleSelector simpleSelector = conditionalSelector.getSimpleSelector();

		SQCssSelector sqCssSelector = sqCssSelectorTranslator.translate(stringMap, simpleSelector);

		SQCssCondition sqCssCondition = translate(simpleSelector, stringMap, condition);
		return new SQCssConditionalSelector(sqCssSelector, sqCssCondition);
	}
	
	SQCssCondition translate(SimpleSelector simpleSelector, Map<String, String> stringMap, Condition condition) {
		return sqCssConditionTranslator.translate(simpleSelector, stringMap, condition);
	}

}