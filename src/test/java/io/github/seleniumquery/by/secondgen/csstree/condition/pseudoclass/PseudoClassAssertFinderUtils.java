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

package io.github.seleniumquery.by.secondgen.csstree.condition.pseudoclass;

import io.github.seleniumquery.by.common.elementfilter.ElementFilter;
import io.github.seleniumquery.by.secondgen.csstree.condition.SQCssConditionImplementedFinders;
import io.github.seleniumquery.by.secondgen.finder.ElementFinder;
import org.hamcrest.Matcher;

import java.util.List;

import static io.github.seleniumquery.by.secondgen.finder.ElementFinderUtilsTest.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author acdcjunior
 * @since 0.10.0
 */
@SuppressWarnings("deprecation")
public class PseudoClassAssertFinderUtils {

    public static final boolean PURE_CSS_IS_SUPPORTED = true;
    public static final boolean PURE_CSS_IS_NOT_SUPPORTED = false;
    public static final String CSS_UNIVERSAL_SELECTOR = "*";

    public static void assertPseudoClassHasFinder(SQCssConditionImplementedFinders pseudoClassObject,
                                                  ElementFinder previous,
                                                  String expectedCss, boolean canPureCss,
                                                  String expectedXPath, Matcher<? super List<ElementFilter>> elementFilterMatcher) {
        // given
        // arguments
        // when
        ElementFinder elementFinder = pseudoClassObject.toElementFinder(previous);
        // then
        assertEquals("CSS selector", expectedCss, elementFinder.toCssString());
        assertEquals("Can pure CSS?", canPureCss, elementFinder.canFetchThroughCssAlone());
        assertEquals("XPath Expression", expectedXPath, elementFinder.getXPathExpression());
        assertThat("ElementFilterList", elementFinder.getElementFilterList().getElementFilters(), elementFilterMatcher);
    }

    public static void assertPseudoClassHasElementFinderWhenNativelySupported(String pseudoExpressionThatShouldPassNativeSupportCheck,
                                                                              SQCssConditionImplementedFinders pseudoClassObject,
                                                                              String expectedCss, boolean canPureCss,
                                                                              String expectedXPath, Matcher<? super List<ElementFilter>> elementFilterMatcher) {
        ElementFinder previousFinder = universalSelectorFinder(
                createWebDriverWithNativeSupportForPseudo(pseudoExpressionThatShouldPassNativeSupportCheck)
        );
        assertPseudoClassHasFinder(
                pseudoClassObject,
                previousFinder,
                expectedCss,
                canPureCss,
                expectedXPath,
                elementFilterMatcher
        );
    }

    private static void assertPseudoClassHasFinderWhenNotNativelySupported(SQCssConditionImplementedFinders pseudoClassObject,
                                                                          String expectedCss,
                                                                          boolean canPureCss,
                                                                          String expectedXPath,
                                                                          Matcher<? super List<ElementFilter>> elementFilterMatcher) {
        ElementFinder previousFinder = universalSelectorFinder(createWebDriverWithNativeSupportForNoPseudoClass());
        assertPseudoClassHasFinder(
                pseudoClassObject,
                previousFinder,
                expectedCss, canPureCss,
                expectedXPath,
                elementFilterMatcher
        );
    }

    public static void assertPseudoSupportsOnlyPureCssAndNotPureXPathWhenNativelySupported(SQCssConditionImplementedFinders pseudoClassObject,
                                                                                           String pseudoClass,
                                                                                           String expectedXPath,
                                                                                           ElementFilter filter) {
        assertPseudoClassHasElementFinderWhenNativelySupported(
                pseudoClass,
                pseudoClassObject,
                pseudoClass, PURE_CSS_IS_SUPPORTED,
                expectedXPath, contains(filter)
        );
    }

    public static void assertPseudoSupportsBothPureCssAndPureXPathWhenNativelySupported(SQCssConditionImplementedFinders pseudoClassObject,
                                                                                        String pseudoClass,
                                                                                        String expectedXPath) {
        assertPseudoSupportsBothPureCssAndPureXPathWhenNativelySupported(pseudoClass, pseudoClassObject, pseudoClass, expectedXPath);
    }

    public static void assertPseudoSupportsBothPureCssAndPureXPathWhenNativelySupported(String pseudoClassThatShouldBeNativelySupported,
                                                                                        SQCssConditionImplementedFinders pseudoClassObject,
                                                                                        String expectedCSS,
                                                                                        String expectedXPath) {
        assertPseudoClassHasElementFinderWhenNativelySupported(
                pseudoClassThatShouldBeNativelySupported,
                pseudoClassObject,
                expectedCSS, PURE_CSS_IS_SUPPORTED,
                expectedXPath, empty()
        );
    }

    public static void assertPseudoClassDoesNotSupportAnythingPurelyWhenNotNativelySupported(SQCssConditionImplementedFinders pseudoClassObject,
                                                                                             String expectedXPath,
                                                                                             ElementFilter filter) {
        assertPseudoClassHasFinderWhenNotNativelySupported(
                pseudoClassObject,
                CSS_UNIVERSAL_SELECTOR, PURE_CSS_IS_NOT_SUPPORTED,
                expectedXPath, contains(filter)
        );
    }

    public static class AssertPseudoClass {
        public static AssertPseudoClass assertPseudoClass(SQCssConditionImplementedFinders pseudoClass) { return new AssertPseudoClass(pseudoClass); }

        private final SQCssConditionImplementedFinders pseudoClass;
        public AssertPseudoClass(SQCssConditionImplementedFinders pseudoClass) { this.pseudoClass = pseudoClass; }

        public AssertPseudoClassWithoutNativeSupport whenNotNativelySupported() { return new AssertPseudoClassWithoutNativeSupport(this.pseudoClass); }
    }
    public static class AssertPseudoClassWithoutNativeSupport {
        private final SQCssConditionImplementedFinders pseudoClass;
        public AssertPseudoClassWithoutNativeSupport(SQCssConditionImplementedFinders pseudoClass) { this.pseudoClass = pseudoClass; }

        public void translatesToPureXPath(String expectedXPath) {
            assertPseudoClassHasFinderWhenNotNativelySupported(this.pseudoClass, CSS_UNIVERSAL_SELECTOR, PURE_CSS_IS_NOT_SUPPORTED, expectedXPath, empty());
        }
    }

    public static void assertPseudoSupportsDifferentButPureCssAndPureXPathRegardlessOfNativeSupport(SQCssConditionImplementedFinders pseudoClassObject,
                                                                                                    String pseudoClass,
                                                                                                    String expectedCss,
                                                                                                    String expectedXPath) {
        assertPseudoClassHasElementFinderWhenNativelySupported(
                pseudoClass, pseudoClassObject,
                expectedCss, PURE_CSS_IS_SUPPORTED,
                expectedXPath, empty()
        );
        assertPseudoClassHasFinderWhenNotNativelySupported(
                pseudoClassObject,
                expectedCss, PURE_CSS_IS_SUPPORTED,
                expectedXPath, empty()
        );
    }

}
