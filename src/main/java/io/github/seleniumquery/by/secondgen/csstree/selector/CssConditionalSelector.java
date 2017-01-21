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

package io.github.seleniumquery.by.secondgen.csstree.selector;

import io.github.seleniumquery.by.secondgen.csstree.condition.SQCssCondition;
import io.github.seleniumquery.by.secondgen.csstree.condition.SQCssConditionImplementedFinders;
import io.github.seleniumquery.by.secondgen.finder.ElementFinder;
import org.openqa.selenium.WebDriver;

/**
 * Conditional selector, simply an union of a selector and a condition.
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class CssConditionalSelector implements CssSelector {

    private final CssSelector cssSelector;
    private final SQCssCondition sqCssCondition;

    public CssConditionalSelector(CssSelector cssSelector, SQCssCondition sqCssCondition) {
        this.cssSelector = cssSelector;
        this.sqCssCondition = sqCssCondition;
    }

    public CssSelector getCssSelector() {
        return cssSelector;
    }

    public SQCssCondition getSqCssCondition() {
        return sqCssCondition;
    }

    @Override
    public ElementFinder toElementFinder(WebDriver webDriver) {
        ElementFinder elementFinder = cssSelector.toElementFinder(webDriver);
        return ((SQCssConditionImplementedFinders) sqCssCondition).toElementFinder(elementFinder);
    }

    @Override
    public ElementFinder toElementFinder(ElementFinder leftFinder) {
        ElementFinder elementFinder = cssSelector.toElementFinder(leftFinder);
        return ((SQCssConditionImplementedFinders) sqCssCondition).toElementFinder(elementFinder);
    }

}
