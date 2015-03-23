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

import static io.github.seleniumquery.by.csstree.condition.pseudoclass.PseudoClassAssertLocatorUtils.assertPseudoClassOnlySupportsPureXPathRegardlessOfNativeSupport;
import static io.github.seleniumquery.by.csstree.condition.pseudoclass.PseudoClassTestUtils.assertFunctionalPseudo;
import static io.github.seleniumquery.by.csstree.condition.pseudoclass.PseudoClassTestUtils.createPseudoClassSelectorAppliedToUniversalSelector;
import static io.github.seleniumquery.by.locator.SQLocatorUtilsTest.UNIVERSAL_SELECTOR_LOCATOR;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class SQCssEqPseudoClassTest {

    public static final String EQ_PSEUDO = ":eq";

    @Test
    public void translate() {
        assertFunctionalPseudo(EQ_PSEUDO, SQCssEqPseudoClass.class);
    }

    @Test
    public void toSQLocator__eq_should_throw_exception_if_argument_is_not_an_integer() {
        assertEqArgumentIsNotValid("a");
        assertEqArgumentIsNotValid("");
        assertEqArgumentIsNotValid("+");
        assertEqArgumentIsNotValid("-");
        assertEqArgumentIsNotValid("+ 1");
        assertEqArgumentIsNotValid(" ");
    }

    private void assertEqArgumentIsNotValid(String eqArgument) {
        try {
            eq(eqArgument).toSQLocator(UNIVERSAL_SELECTOR_LOCATOR);
            fail("Should consider *:eq("+eqArgument+") to be invalid.");
        } catch (InvalidSelectorException e) {
            assertThat(e.getMessage(), containsString(":eq()"));
            assertThat(e.getMessage(), containsString(eqArgument));
        }
    }

    private SQCssEqPseudoClass eq(String eqArgument) {
        return new SQCssEqPseudoClass(createPseudoClassSelectorAppliedToUniversalSelector(eqArgument));
    }

    @Test
    public void toSQLocator__eq_0__only_generates_XPath_regardless_of_native_support() {
        String eq0XPathExpression = "(.//*)[position() = 1]";
        assertEqArgumentGeneratesXPath("0", eq0XPathExpression);
        assertEqArgumentGeneratesXPath("+0", eq0XPathExpression);
        assertEqArgumentGeneratesXPath("-0", eq0XPathExpression);
        assertEqArgumentGeneratesXPath(" +0", eq0XPathExpression);
        assertEqArgumentGeneratesXPath(" -0", eq0XPathExpression);
        assertEqArgumentGeneratesXPath("+0 ", eq0XPathExpression);
        assertEqArgumentGeneratesXPath("-0 ", eq0XPathExpression);
        assertEqArgumentGeneratesXPath("  +0   ", eq0XPathExpression);
        assertEqArgumentGeneratesXPath("  -0   ", eq0XPathExpression);
    }

    private void assertEqArgumentGeneratesXPath(String eqArgument, String eqXPathExpression) {
        assertPseudoClassOnlySupportsPureXPathRegardlessOfNativeSupport(eq(eqArgument), EQ_PSEUDO, eqXPathExpression);
    }

    @Test
    public void toSQLocator__eq_1__only_generates_XPath_regardless_of_native_support() {
        String eq1XPathExpression = "(.//*)[position() = 2]";
        assertEqArgumentGeneratesXPath("1", eq1XPathExpression);
        assertEqArgumentGeneratesXPath("+1", eq1XPathExpression);
        assertEqArgumentGeneratesXPath("  +1", eq1XPathExpression);
        assertEqArgumentGeneratesXPath("+1  ", eq1XPathExpression);
        assertEqArgumentGeneratesXPath("      +1     ", eq1XPathExpression);
    }

    @Test
    public void toSQLocator__eq_2_NEGATIVE__only_generates_XPath_regardless_of_native_support() {
        String eqNegative2XPathExpression = "(.//*)[position() = (last()-1)]";
        assertEqArgumentGeneratesXPath("-2", eqNegative2XPathExpression);
        assertEqArgumentGeneratesXPath("-2", eqNegative2XPathExpression);
        assertEqArgumentGeneratesXPath("  -2", eqNegative2XPathExpression);
        assertEqArgumentGeneratesXPath("-2  ", eqNegative2XPathExpression);
        assertEqArgumentGeneratesXPath("      -2     ", eqNegative2XPathExpression);
    }

}