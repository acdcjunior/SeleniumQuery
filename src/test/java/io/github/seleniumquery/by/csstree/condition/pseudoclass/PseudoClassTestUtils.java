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

package io.github.seleniumquery.by.csstree.condition.pseudoclass;

import io.github.seleniumquery.by.css.pseudoclasses.PseudoClassSelector;
import io.github.seleniumquery.by.csstree.condition.SQCssCondition;
import io.github.seleniumquery.by.filter.ElementFilter;
import io.github.seleniumquery.by.locator.SQLocator;
import io.github.seleniumquery.by.locator.SQLocatorUtilsTest;
import io.github.seleniumquery.by.preparser.CSSParsedSelectorList;
import io.github.seleniumquery.by.preparser.CSSSelectorParser;
import org.w3c.css.sac.Selector;

import java.util.HashMap;
import java.util.Map;

import static io.github.seleniumquery.by.parser.translator.condition.attribute.TranslatorsTestUtils.parseAndAssertFirstCssCondition;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class PseudoClassTestUtils {

    public static final PseudoClassSelector EMPTY = new PseudoClassSelector(null, null, "") {
        @Override
        public String getPseudoClassContent() {
            return "";
        }
    };

    public static <T extends SQCssCondition> void assertPseudo(String selector, Class<T> pseudoClassClass) {
        // given
        // selector
        // when
        T cssCondition = parseAndAssertFirstCssCondition(selector, pseudoClassClass);
        // then
        assertThat(cssCondition, is(notNullValue()));
    }

    public static <T extends SQCssFunctionalPseudoClassCondition> void assertFunctionalPseudo(String selector,
                                                                                              Class<T> pseudoClassClass) {
        assertSelectorTranslatesArgument(selector, pseudoClassClass, "(0)", "0");
        assertSelectorTranslatesArgument(selector, pseudoClassClass, "(-0)", "-0");
        assertSelectorTranslatesArgument(selector, pseudoClassClass, "(+0)", "+0");
        assertSelectorTranslatesArgument(selector, pseudoClassClass, "(1)", "1");
        assertSelectorTranslatesArgument(selector, pseudoClassClass, "(-1)", "-1");
        assertSelectorTranslatesArgument(selector, pseudoClassClass, "(+1)", "+1");
        assertSelectorTranslatesArgument(selector, pseudoClassClass, "(999999)", "999999");
        assertSelectorTranslatesArgument(selector, pseudoClassClass, "(-999999)", "-999999");

        assertSelectorTranslatesArgument(selector, pseudoClassClass, "", null);
        assertSelectorTranslatesArgument(selector, pseudoClassClass, "()", "");
        assertSelectorTranslatesArgument(selector, pseudoClassClass, "( )", " ");
        assertSelectorTranslatesArgument(selector, pseudoClassClass, "(     )", "     ");

        assertSelectorTranslatesArgument(selector, pseudoClassClass, "(a)", "a");
        assertSelectorTranslatesArgument(selector, pseudoClassClass, "('a')", "'a'");
        assertSelectorTranslatesArgument(selector, pseudoClassClass, "(\"a\")", "\"a\"");

        assertSelectorTranslatesArgument(selector, pseudoClassClass, "(a b c d e)", "a b c d e");
        assertSelectorTranslatesArgument(selector, pseudoClassClass, "(\"a b c d e\")", "\"a b c d e\"");
    }

    private static <T extends SQCssFunctionalPseudoClassCondition> void assertSelectorTranslatesArgument(String selector,
                                                                                                         Class<T> pseudoClassClass,
                                                                                                         String selectorSuffix,
                                                                                                         String expectedArgument) {
        // given
        // selector
        // when
        T cssCondition = parseAndAssertFirstCssCondition(selector + selectorSuffix, pseudoClassClass);
        // then
        if (expectedArgument != null) {
            assertThat(cssCondition.getArgument(), is(expectedArgument));
        } else {
            assertThat(cssCondition.getArgument(), is(nullValue()));
        }
    }

    public static void assertFilterOnlyPseudoGeneratesFilter(SQCssPseudoClassCondition pseudoClassCondition, ElementFilter pseudoClassFilter) {
        SQLocator previous = SQLocatorUtilsTest.universalSelectorLocator(SQLocatorUtilsTest.createMockDriverWithoutNativeSupportFor(getSelectorForPseudoClass(pseudoClassCondition)));
        // when
        SQLocator locator = pseudoClassCondition.toSQLocator(previous);
        // then
        assertThat(locator.getCSSLocator().toString(), is(previous.getCSSLocator().toString()));
        assertThat(locator.canFetchThroughCssAlone(), is(false));
        assertThat(locator.getXPathExpression(), is(previous.getXPathExpression()));
        assertThat(locator.getElementFilterList().getElementFilters(), contains(pseudoClassFilter));
    }

    private static String getSelectorForPseudoClass(SQCssPseudoClassCondition pseudoClassCondition) {
        try {
            return ":" + pseudoClassCondition.getClass().getDeclaredField("PSEUDO").get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method creates a {@link PseudoClassSelector} equivalent to {@code *:the-pseudo-class(ARGUMENT)}.
     *
     * @param functionalPseudoClassArgument the ARGUMENT.
     */
    public static PseudoClassSelector createPseudoClassSelectorAppliedToUniversalSelector(String functionalPseudoClassArgument) {
        CSSParsedSelectorList cssParsedSelectorList = CSSSelectorParser.parseSelector("*");
        Selector universalSelector = cssParsedSelectorList.getSelectorList().item(0);
        Map<String, String> stringMap = new HashMap<String, String>();
        stringMap.put("1", functionalPseudoClassArgument);
        return new PseudoClassSelector(stringMap, universalSelector, "(1)");
    }

}