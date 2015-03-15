/*
 * Copyright (c) 2015 seleniumQuery authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.seleniumquery.functions.jquery.traversing.filtering;

import io.github.seleniumquery.SeleniumQueryObject;
import io.github.seleniumquery.by.preparser.CSSParsedSelectorList;
import io.github.seleniumquery.by.preparser.CSSSelectorParser;
import io.github.seleniumquery.by.css.CssSelector;
import io.github.seleniumquery.by.css.CssSelectorFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.seleniumquery.by.xpath.component.TagComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorList;

/**
 * $("selector").is("selector")
 *
 * @author acdcjunior
 * @author ricardo-sc
 *
 * @since 0.9.0
 */
public class IsFunction {

	private static final Pattern NOT_SQ_PATTERN = Pattern.compile("not-sq\\((\\d+)\\)");

	public static boolean is(SeleniumQueryObject seleniumQueryObject, List<WebElement> elements, String selector) {
		return is(seleniumQueryObject.getWebDriver(), elements, selector);
	}
	
	public static boolean is(WebDriver driver, List<WebElement> elements, String selector) {
		if (selector.trim().isEmpty()) {
			return false;
		}
		CSSParsedSelectorList CSSParsedSelectorList = CSSSelectorParser.parseSelector(selector);
		SelectorList selectorList = CSSParsedSelectorList.getSelectorList();
		Map<String, String> stringMap = CSSParsedSelectorList.getStringMap();

		for (int i = 0; i < selectorList.getLength(); i++) {
    		Selector parsedSimpleSelector = selectorList.item(i);
    		
    		// If there is a :not(:present), then having no element is a expected status!
    		if (hasNegatedPresent(stringMap, parsedSimpleSelector.toString()) && elements.isEmpty()) {
    			return true;
    		}

    		CssSelector<Selector, TagComponent> cssSelector = CssSelectorFactory.parsedSelectorToCssSelector(parsedSimpleSelector);
			for (WebElement webElement : elements) {
    			// if any matches, then it returns true
				if (cssSelector.is(driver, webElement, stringMap, parsedSimpleSelector)) {
					return true;
				}
    		}
    	}
		return false;
	}

	private static boolean hasNegatedPresent(Map<String, String> stringMap, String parsedSimpleSelector) {
		Matcher m = NOT_SQ_PATTERN.matcher(parsedSimpleSelector);
		while (m.find()) {
		    String stringMapId = m.group(1);
		    String notPseudoClassContent = stringMap.get(stringMapId);
		    
			CSSParsedSelectorList parsedPseudoClassContent = CSSSelectorParser.parseSelector(notPseudoClassContent);
			SelectorList parsedPseudoClassContentSelectorList = parsedPseudoClassContent.getSelectorList();
			Map<String, String> parsedPseudoClassContentStringMap = parsedPseudoClassContent.getStringMap();
			for (int i = 0; i < parsedPseudoClassContentSelectorList.getLength(); i++) {
				Selector parsedPseudoClassContentSimpleSelector = parsedPseudoClassContentSelectorList.item(i);
				if (!hasNegatedPresent(parsedPseudoClassContentStringMap, parsedPseudoClassContentSimpleSelector.toString())) {
					return true;
				}
			}
		}
		return false;
	}
	
}