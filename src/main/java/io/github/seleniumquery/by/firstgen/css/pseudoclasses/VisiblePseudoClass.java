/*
 * Copyright (c) 2016 seleniumQuery authors
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

package io.github.seleniumquery.by.firstgen.css.pseudoclasses;

import io.github.seleniumquery.by.common.elementfilter.ElementFilter;
import io.github.seleniumquery.by.firstgen.xpath.component.ConditionSimpleComponent;
import io.github.seleniumquery.utils.SelectorUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static io.github.seleniumquery.by.firstgen.css.pseudoclasses.PseudoClassOnlySupportedThroughIsOrFilterException.pseudoClassNotSupportedWhenUsedDirectly;

/**
 * :visible
 *
 * @author acdcjunior
 * @since 0.9.0
 */
public class VisiblePseudoClass implements PseudoClass<ConditionSimpleComponent> {

	private static final String VISIBLE_PSEUDO_CLASS_NO_COLON = "visible";

    public static final ElementFilter VISIBLE_FILTER = new PseudoClassFilter(new VisiblePseudoClass());

	@Override
	public boolean isApplicable(String pseudoClassValue) {
		return VISIBLE_PSEUDO_CLASS_NO_COLON.equals(pseudoClassValue);
	}

	@Override
	public boolean isPseudoClass(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector) {
		return SelectorUtils.isVisible(element);
	}

	@Override
	public ConditionSimpleComponent pseudoClassToXPath(PseudoClassSelector pseudoClassSelector) {
		pseudoClassNotSupportedWhenUsedDirectly(VISIBLE_PSEUDO_CLASS_NO_COLON);
		
		// we can't use XPath because it can't see the styles affecting the element's classes, which can pretty much
		// turn any element, including <html> itself or <head>, visible.
		return new ConditionSimpleComponent(VISIBLE_FILTER);
	}
	
}