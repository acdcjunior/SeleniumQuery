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

package io.github.seleniumquery.by.csstree.condition.pseudoclass.basicfilter;

import org.junit.Test;
import org.openqa.selenium.InvalidSelectorException;

import static io.github.seleniumquery.by.csstree.condition.pseudoclass.PseudoClassAssertFinderUtils.assertPseudoClassOnlySupportsPureXPathRegardlessOfNativeSupport;
import static io.github.seleniumquery.by.csstree.condition.pseudoclass.PseudoClassTestUtils.assertFunctionalPseudo;
import static io.github.seleniumquery.by.csstree.condition.pseudoclass.PseudoClassTestUtils.createPseudoClassSelectorAppliedToUniversalSelector;
import static io.github.seleniumquery.by.finder.ElementFinderUtilsTest.UNIVERSAL_SELECTOR_LOCATOR;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class SQCssLtPseudoClassTest {

    public static final String LT_PSEUDO = ":lt";

    @Test
    public void translate() {
        assertFunctionalPseudo(LT_PSEUDO, SQCssLtPseudoClass.class);
    }

    @Test
    public void toElementFinder__lt_should_throw_exception_if_argument_is_not_an_integer() {
        assertLtArgumentIsNotValid("a");
        assertLtArgumentIsNotValid("");
        assertLtArgumentIsNotValid("+");
        assertLtArgumentIsNotValid("-");
        assertLtArgumentIsNotValid("+ 1");
        assertLtArgumentIsNotValid(" ");
    }

    private void assertLtArgumentIsNotValid(String ltArgument) {
        try {
            lt(ltArgument).toElementFinder(UNIVERSAL_SELECTOR_LOCATOR);
            fail("Should consider *:lt("+ltArgument+") to be invalid.");
        } catch (InvalidSelectorException e) {
            assertThat(e.getMessage(), containsString(":lt()"));
            assertThat(e.getMessage(), containsString(ltArgument));
        }
    }

    private SQCssLtPseudoClass lt(String ltArgument) {
        return new SQCssLtPseudoClass(createPseudoClassSelectorAppliedToUniversalSelector(ltArgument));
    }

    @Test
    public void toElementFinder__lt_0__only_generates_XPath_regardless_of_native_support() {
        String lt0XPathExpression = "(.//*)[position() < 1]";
        assertLtArgumentGeneratesXPath("0", lt0XPathExpression);
        assertLtArgumentGeneratesXPath("+0", lt0XPathExpression);
        assertLtArgumentGeneratesXPath("-0", lt0XPathExpression);
        assertLtArgumentGeneratesXPath(" +0", lt0XPathExpression);
        assertLtArgumentGeneratesXPath(" -0", lt0XPathExpression);
        assertLtArgumentGeneratesXPath("+0 ", lt0XPathExpression);
        assertLtArgumentGeneratesXPath("-0 ", lt0XPathExpression);
        assertLtArgumentGeneratesXPath("  +0   ", lt0XPathExpression);
        assertLtArgumentGeneratesXPath("  -0   ", lt0XPathExpression);
    }

    private void assertLtArgumentGeneratesXPath(String ltArgument, String ltXPathExpression) {
        assertPseudoClassOnlySupportsPureXPathRegardlessOfNativeSupport(lt(ltArgument), LT_PSEUDO, ltXPathExpression);
    }

    @Test
    public void toElementFinder__lt_1__only_generates_XPath_regardless_of_native_support() {
        String lt1XPathExpression = "(.//*)[position() < 2]";
        assertLtArgumentGeneratesXPath("1", lt1XPathExpression);
        assertLtArgumentGeneratesXPath("+1", lt1XPathExpression);
        assertLtArgumentGeneratesXPath("  +1", lt1XPathExpression);
        assertLtArgumentGeneratesXPath("+1  ", lt1XPathExpression);
        assertLtArgumentGeneratesXPath("      +1     ", lt1XPathExpression);
    }

    @Test
    public void toElementFinder__lt_2_NEGATIVE__only_generates_XPath_regardless_of_native_support() {
        String ltNegative2XPathExpression = "(.//*)[position() < (last()-1)]";
        assertLtArgumentGeneratesXPath("-2", ltNegative2XPathExpression);
        assertLtArgumentGeneratesXPath("-2", ltNegative2XPathExpression);
        assertLtArgumentGeneratesXPath("  -2", ltNegative2XPathExpression);
        assertLtArgumentGeneratesXPath("-2  ", ltNegative2XPathExpression);
        assertLtArgumentGeneratesXPath("      -2     ", ltNegative2XPathExpression);
    }

}