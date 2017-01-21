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

package io.github.seleniumquery.by.secondgen.parser.translator.condition.attribute;

import io.github.seleniumquery.by.secondgen.csstree.condition.SQCssCondition;
import io.github.seleniumquery.by.secondgen.csstree.condition.SQCssConditionImplementedFinders;
import io.github.seleniumquery.by.secondgen.csstree.selector.CssConditionalSelector;
import io.github.seleniumquery.by.secondgen.csstree.selector.CssSelector;
import io.github.seleniumquery.by.secondgen.csstree.selector.CssTagNameSelector;
import io.github.seleniumquery.by.secondgen.finder.ElementFinderUtilsTest;
import io.github.seleniumquery.by.secondgen.parser.SQParseTreeBuilder;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TranslatorsTestUtils {

    /**
     * Parses {@code selector} and checks if it yields a CSS condition that is an instance of {@code conditionClass}.
     * If so, returns it.
     *
     * @param selector The string selector to be parsed.
     * @param conditionClass The class the condition is expected to be.
     * @param <T> The type of the condition.
     * @return The condition instance, after parse.
     */
    @SuppressWarnings("unchecked")
    public static <T extends SQCssCondition> T parseAndAssertFirstCssCondition(String selector, Class<T> conditionClass) {
        CssSelector cssSelector = SQParseTreeBuilder.parse(selector).firstSelector();
        assertThat(cssSelector, instanceOf(CssConditionalSelector.class));
        // when
        CssSelector sqCssSelector = ((CssConditionalSelector) cssSelector).getCssSelector();
        SQCssCondition sqCssCondition = ((CssConditionalSelector) cssSelector).getSqCssCondition();
        // then
        assertThat(sqCssSelector, instanceOf(CssTagNameSelector.class));
        assertThat(sqCssCondition, instanceOf(conditionClass));
        assertThat(((CssTagNameSelector) sqCssSelector).getTagName(), is("*"));
        return (T) sqCssCondition;
    }

    @SuppressWarnings("deprecation")
    public static String getCssStringGeneratedByCondition(SQCssConditionImplementedFinders condition) {
        return condition.toElementFinder(ElementFinderUtilsTest.UNIVERSAL_SELECTOR_FINDER).toCssString();
    }

}
