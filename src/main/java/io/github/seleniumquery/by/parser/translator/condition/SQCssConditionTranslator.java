package io.github.seleniumquery.by.parser.translator.condition;

import io.github.seleniumquery.by.csstree.condition.SQCssCondition;
import io.github.seleniumquery.by.csstree.condition.SQCssUnknownConditionException;
import io.github.seleniumquery.by.parser.translator.condition.attribute.*;
import io.github.seleniumquery.by.parser.translator.condition.pseudoclass.SQCssPseudoClassConditionTranslator;
import org.w3c.css.sac.AttributeCondition;
import org.w3c.css.sac.CombinatorCondition;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.SimpleSelector;

import java.util.Map;

/**
 * Translates a SAC {@link Condition} selector into a {@link SQCssCondition}.
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class SQCssConditionTranslator {

    private final SQCssAndConditionTranslator andConditionTranslator = new SQCssAndConditionTranslator(this);
    private final SQCssStartsWithAttributeConditionTranslator startsWithAttributeConditionTranslator = new SQCssStartsWithAttributeConditionTranslator();
    private final SQCssEndsWithAttributeConditionTranslator endsWithAttributeConditionTranslator = new SQCssEndsWithAttributeConditionTranslator();
    private final SQCssContainsSubstringAttributeConditionTranslator containsSubstringAttributeConditionTranslator = new SQCssContainsSubstringAttributeConditionTranslator();
    private final SQCssEqualsOrHasAttributeConditionTranslator equalsOrHasAttributeConditionTranslator = new SQCssEqualsOrHasAttributeConditionTranslator();
    private final SQCssIdAttributeConditionTranslator idAttridAttributeConditionTranslatorbuteCssSelector = new SQCssIdAttributeConditionTranslator();
    private final SQCssContainsWordAttributeConditionTranslator containsWordAttributeConditionTranslator = new SQCssContainsWordAttributeConditionTranslator();
    private final SQCssContainsPrefixAttributeConditionTranslator containsPrefixAttributeConditionTranslator = new SQCssContainsPrefixAttributeConditionTranslator();
    private final SQCssClassAttributeConditionTranslator classAttributeConditionTranslator = new SQCssClassAttributeConditionTranslator();
    private final SQCssPseudoClassConditionTranslator pseudoClassCssSelector = new SQCssPseudoClassConditionTranslator();

	public SQCssCondition translate(SimpleSelector simpleSelector, Map<String, String> stringMap, Condition condition) {
	    switch (condition.getConditionType()) {
		    case Condition.SAC_AND_CONDITION:
		    	return andConditionTranslator.translate(simpleSelector, stringMap, (CombinatorCondition) condition);
		    case Condition.SAC_OR_CONDITION:
				return incompatible("Condition.SAC_OR_CONDITION");

			case Condition.SAC_ATTRIBUTE_CONDITION:
				if (condition instanceof com.steadystate.css.parser.selectors.PrefixAttributeConditionImpl) {
		    		return startsWithAttributeConditionTranslator.translate((AttributeCondition) condition);
		    	}
		    	if (condition instanceof com.steadystate.css.parser.selectors.SuffixAttributeConditionImpl) {
		    		return endsWithAttributeConditionTranslator.translate((AttributeCondition) condition);
		    	}
		    	if (condition instanceof com.steadystate.css.parser.selectors.SubstringAttributeConditionImpl) {
		    		return containsSubstringAttributeConditionTranslator.translate((AttributeCondition) condition);
		    	}
		    	// else: condition is most probably a instance of com.steadystate.css.parser.selectors.AttributeConditionImpl
		    	return equalsOrHasAttributeConditionTranslator.translate((AttributeCondition) condition);
	        case Condition.SAC_ID_CONDITION:
				return idAttridAttributeConditionTranslatorbuteCssSelector.translate((AttributeCondition) condition);
	        case Condition.SAC_ONE_OF_ATTRIBUTE_CONDITION:
	        	return containsWordAttributeConditionTranslator.translate((AttributeCondition) condition);
	        case Condition.SAC_BEGIN_HYPHEN_ATTRIBUTE_CONDITION:
	        	return containsPrefixAttributeConditionTranslator.translate((AttributeCondition) condition);
	        case Condition.SAC_CLASS_CONDITION:
	        	return classAttributeConditionTranslator.translate((AttributeCondition) condition);
				
	        case Condition.SAC_PSEUDO_CLASS_CONDITION:
	        	return pseudoClassCssSelector.translate(simpleSelector, stringMap, (AttributeCondition) condition);
	        case Condition.SAC_LANG_CONDITION:
				return incompatible("Condition.SAC_LANG_CONDITION");
	            
	        default:
				throw new SQCssUnknownConditionException(condition);
		}
	}

	private SQCssCondition incompatible(String s) {
		// if the exception below gets thrown, this means the CSS Parser has changed and
		// we must update our code as well.
		throw new RuntimeException("The condition "+s+" is not used by the CSS Parser. " +
                "This version of seleniumQuery is not compatible with the CSS Parser present in the" +
                "classpath.");
	}

}