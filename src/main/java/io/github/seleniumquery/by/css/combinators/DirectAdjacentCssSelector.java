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

package io.github.seleniumquery.by.css.combinators;

import io.github.seleniumquery.by.SelectorUtils;
import io.github.seleniumquery.by.css.CssSelector;
import io.github.seleniumquery.by.css.CssSelectorMatcherService;
import io.github.seleniumquery.by.preparser.ArgumentMap;
import io.github.seleniumquery.by.xpath.XPathComponentCompilerService;
import io.github.seleniumquery.by.xpath.component.AdjacentComponent;
import io.github.seleniumquery.by.xpath.component.ConditionSimpleComponent;
import io.github.seleniumquery.by.xpath.component.TagComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.css.sac.SiblingSelector;

/**
 * E + F
 *
 * @author acdcjunior
 * @since 0.9.0
 */
public class DirectAdjacentCssSelector implements CssSelector<SiblingSelector, TagComponent> {

	@Override
	public boolean is(WebDriver driver, WebElement element, ArgumentMap stringMap, SiblingSelector siblingSelector) {
		WebElement previousElement = SelectorUtils.getPreviousSibling(element);
		return CssSelectorMatcherService.elementMatchesSelector(driver, previousElement, stringMap, siblingSelector.getSelector())
				&& CssSelectorMatcherService.elementMatchesSelector(driver, element, stringMap, siblingSelector.getSiblingSelector());
	}

	@Override
	public TagComponent toXPath(ArgumentMap stringMap, SiblingSelector siblingSelector) {
		TagComponent previousCompiledExpression = XPathComponentCompilerService.compileSelector(stringMap, siblingSelector.getSelector());
		TagComponent siblingSelectorCompiledAdjacentExpression = XPathComponentCompilerService.compileSelector(stringMap, siblingSelector.getSiblingSelector());

		ConditionSimpleComponent positionOne = new ConditionSimpleComponent("[position() = 1]");
		TagComponent siblingAtPositionOne = siblingSelectorCompiledAdjacentExpression.cloneAndCombineTo(positionOne);
		
		return AdjacentComponent.combine(previousCompiledExpression, siblingAtPositionOne);
	}

}