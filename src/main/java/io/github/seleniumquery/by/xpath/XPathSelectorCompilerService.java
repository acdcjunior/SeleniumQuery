package io.github.seleniumquery.by.xpath;

import io.github.seleniumquery.by.preparser.ParsedSelector;
import io.github.seleniumquery.by.preparser.SelectorParser;
import io.github.seleniumquery.by.css.CssSelector;
import io.github.seleniumquery.by.css.CssSelectorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorList;

public class XPathSelectorCompilerService {
	
	public static XPathExpressionList compileSelectorList(String selector) {
		ParsedSelector<SelectorList> parsedSelector = SelectorParser.parseSelector(selector);
		SelectorList selectorList = parsedSelector.getSelector();

    	List<XPathExpression> css = new ArrayList<XPathExpression>(selectorList.getLength()); 
    	for (int i = 0; i < selectorList.getLength(); i++) {
    		XPathExpression cs = compileSelector(parsedSelector.getStringMap(), selectorList.item(i));
    		css.add(cs);
    	}
    	
    	return new XPathExpressionList(css);
	}
    
	public static XPathExpression compileSelector(Map<String, String> stringMap, Selector selector) {
		CssSelector<Selector> cssSelector = CssSelectorFactory.parsedSelectorToCssSelector(selector);
		return cssSelector.toXPath(stringMap, selector);
	}

	public static XPathExpression compileToDescendantGeneralExpression(Map<String, String> stringMap, Selector cssSelector) {
		return compileAndOverrideCssSelectorType(stringMap, cssSelector, CssSelectorType.DESCENDANT_GENERAL);
	}

	public static XPathExpression compileToDescendantDirectExpression(Map<String, String> stringMap, Selector cssSelector) {
		return compileAndOverrideCssSelectorType(stringMap, cssSelector, CssSelectorType.DESCENDANT_DIRECT);
	}

	public static XPathExpression compileToAdjacentExpression(Map<String, String> stringMap, Selector cssSelector) {
		return compileAndOverrideCssSelectorType(stringMap, cssSelector, CssSelectorType.ADJACENT);
	}

	private static XPathExpression compileAndOverrideCssSelectorType(Map<String, String> stringMap, Selector cssSelector, CssSelectorType cssSelectorType) {
		XPathExpression xPathExpression = compileSelector(stringMap, cssSelector);
		xPathExpression.setCssSelectorType(cssSelectorType);
		return xPathExpression;
	}

}