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

package io.github.seleniumquery.by.csstree.condition.pseudoclass.childfilter;

import io.github.seleniumquery.by.locator.SQLocator;
import org.junit.Test;
import org.openqa.selenium.InvalidSelectorException;

import static io.github.seleniumquery.by.csstree.condition.pseudoclass.PseudoClassAssertLocatorUtils.assertPseudoClassOnlySupportsPureXPathWhenNotNativelySupported;
import static io.github.seleniumquery.by.csstree.condition.pseudoclass.PseudoClassAssertLocatorUtils.assertPseudoSupportsBothPureCssAndPureXPathWhenNativelySupported;
import static io.github.seleniumquery.by.csstree.condition.pseudoclass.PseudoClassTestUtils.assertFunctionalPseudo;
import static io.github.seleniumquery.by.csstree.condition.pseudoclass.PseudoClassTestUtils.createPseudoClassSelectorAppliedToUniversalSelector;
import static io.github.seleniumquery.by.locator.SQLocatorUtilsTest.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class SQCssNthChildPseudoClassTest {

    private static final String NTH_CHILD_PSEUDO = ":nth-child";

    private static final String NTH_CHILD_PSEUDO_USED_IN_NATIVE_SUPPORT_CHECK = NTH_CHILD_PSEUDO+"(1)";
    private static final SQLocator UNIVERSAL_SELECTOR_LOCATOR_SUPPORTING_NTHCHILD_NATIVELY = universalSelectorLocator(
            createMockDriverWithNativeSupportFor(NTH_CHILD_PSEUDO_USED_IN_NATIVE_SUPPORT_CHECK)
    );
    private static final SQLocator UNIVERSAL_SELECTOR_LOCATOR_NOT_SUPPORTING_NTHCHILD_NATIVELY = UNIVERSAL_SELECTOR_LOCATOR;

    @Test
    public void translate() {
        assertFunctionalPseudo(NTH_CHILD_PSEUDO, SQCssNthChildPseudoClass.class);
    }

    @Test
    public void toSQLocator__nthChild_should_throw_exception_if_argument_is_not_valid() {
        assertNthChildArgumentIsNotValid("a");
        assertNthChildArgumentIsNotValid("");
        assertNthChildArgumentIsNotValid("+");
        assertNthChildArgumentIsNotValid("-");
        assertNthChildArgumentIsNotValid("+ 1");
        assertNthChildArgumentIsNotValid("- 1");
        assertNthChildArgumentIsNotValid("+ n");
        assertNthChildArgumentIsNotValid("- n");
        assertNthChildArgumentIsNotValid("+ 3n");
        assertNthChildArgumentIsNotValid("- 3n");
        assertNthChildArgumentIsNotValid(" ");
        assertNthChildArgumentIsNotValid("oddy");
        assertNthChildArgumentIsNotValid("eveny");
        assertNthChildArgumentIsNotValid("1n-");
        assertNthChildArgumentIsNotValid("1n+");
        assertNthChildArgumentIsNotValid("1 n");
        assertNthChildArgumentIsNotValid("1n+1 1");
    }

    private void assertNthChildArgumentIsNotValid(String nthChildArgument) {
        assertNthChildArgumentIsNotValidOnLocator(nthChildArgument, UNIVERSAL_SELECTOR_LOCATOR_SUPPORTING_NTHCHILD_NATIVELY);
        assertNthChildArgumentIsNotValidOnLocator(nthChildArgument, UNIVERSAL_SELECTOR_LOCATOR_NOT_SUPPORTING_NTHCHILD_NATIVELY);
    }

    private void assertNthChildArgumentIsNotValidOnLocator(String nthChildArgument, SQLocator universalSelectorLocator) {
        try {
            nthChild(nthChildArgument).toSQLocator(universalSelectorLocator);
            fail("Should consider *:nth-child("+nthChildArgument+") to be invalid.");
        } catch (InvalidSelectorException e) {
            assertThat(e.getMessage(), containsString(":nth-child()"));
            assertThat(e.getMessage(), containsString(nthChildArgument));
        }
    }

    private SQCssNthChildPseudoClass nthChild(String nthChildArgument) {
        return new SQCssNthChildPseudoClass(createPseudoClassSelectorAppliedToUniversalSelector(nthChildArgument));
    }

    @Test
    public void toSQLocator__b_only_arguments() {
        assertNthChildArgumentYieldsWithAndWithoutSpaces("1", ":nth-child(1)", ".//*[position() = 1]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("2", ":nth-child(2)", ".//*[position() = 2]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("+1", ":nth-child(1)", ".//*[position() = 1]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-1", ":nth-child(-1)", ".//*[position() = -1]");
    }

    @Test
    public void toSQLocator__a_only_arguments() {
        assertNthChildArgumentYieldsWithAndWithoutSpaces("1n", ":nth-child(1n)", ".//*[(position() - 0) mod 1 = 0 and position() >= 0]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("3n", ":nth-child(3n)", ".//*[(position() - 0) mod 3 = 0 and position() >= 0]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("n", ":nth-child(1n)", ".//*[(position() - 0) mod 1 = 0 and position() >= 0]");

        assertNthChildArgumentYieldsWithAndWithoutSpaces("+1n", ":nth-child(1n)", ".//*[(position() - 0) mod 1 = 0 and position() >= 0]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("+3n", ":nth-child(3n)", ".//*[(position() - 0) mod 3 = 0 and position() >= 0]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("+n", ":nth-child(1n)", ".//*[(position() - 0) mod 1 = 0 and position() >= 0]");

        assertNthChildArgumentYieldsWithAndWithoutSpaces("-1n", ":nth-child(-1n)", ".//*[(position() - 0) mod -1 = 0 and position() <= 0]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-n", ":nth-child(-1n)", ".//*[(position() - 0) mod -1 = 0 and position() <= 0]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-3n", ":nth-child(-3n)", ".//*[(position() - 0) mod -3 = 0 and position() <= 0]");
    }

    @Test
    public void toSQLocator__a_and_b_arguments__WITH_BOTH_POSITIVE() {
        assertNthChildArgumentYieldsWithAndWithoutSpaces("1n+1", ":nth-child(1n+1)", ".//*[(position() - 1) mod 1 = 0 and position() >= 1]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("2n+2", ":nth-child(2n+2)", ".//*[(position() - 2) mod 2 = 0 and position() >= 2]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("1n+2", ":nth-child(1n+2)", ".//*[(position() - 2) mod 1 = 0 and position() >= 2]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("2n+1", ":nth-child(2n+1)", ".//*[(position() - 1) mod 2 = 0 and position() >= 1]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("2n+0", ":nth-child(2n)", ".//*[(position() - 0) mod 2 = 0 and position() >= 0]");
        String nPlusFiveCSS = ":nth-child(1n+5)";
        String nPlusFiveXPath = ".//*[(position() - 5) mod 1 = 0 and position() >= 5]";
        assertNthChildArgumentYieldsWithAndWithoutSpaces("n+5", nPlusFiveCSS, nPlusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("n +5", nPlusFiveCSS, nPlusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("n+ 5", nPlusFiveCSS, nPlusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("n + 5", nPlusFiveCSS, nPlusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("1n +5", nPlusFiveCSS, nPlusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("1n+ 5", nPlusFiveCSS, nPlusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("1n + 5", nPlusFiveCSS, nPlusFiveXPath);
    }

    @Test
    public void toSQLocator__a_and_b_arguments__WITH_b_NEGATIVE() {
        assertNthChildArgumentYieldsWithAndWithoutSpaces("1n-1", ":nth-child(1n-1)", ".//*[(position() - -1) mod 1 = 0 and position() >= -1]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("2n-2", ":nth-child(2n-2)", ".//*[(position() - -2) mod 2 = 0 and position() >= -2]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("1n-2", ":nth-child(1n-2)", ".//*[(position() - -2) mod 1 = 0 and position() >= -2]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("2n-1", ":nth-child(2n-1)", ".//*[(position() - -1) mod 2 = 0 and position() >= -1]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("2n-0", ":nth-child(2n)", ".//*[(position() - 0) mod 2 = 0 and position() >= 0]");
        String nMinusFiveCSS = ":nth-child(1n-5)";
        String nMinusFiveXPath = ".//*[(position() - -5) mod 1 = 0 and position() >= -5]";
        assertNthChildArgumentYieldsWithAndWithoutSpaces("n-5", nMinusFiveCSS, nMinusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("n -5", nMinusFiveCSS, nMinusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("n- 5", nMinusFiveCSS, nMinusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("n - 5", nMinusFiveCSS, nMinusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("1n -5", nMinusFiveCSS, nMinusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("1n- 5", nMinusFiveCSS, nMinusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("1n - 5", nMinusFiveCSS, nMinusFiveXPath);
    }

    /**
     * IMPORTANT: This test method was not necessary, that is, adding it did not provoke any code addition,
     * but they are important cases. Anyway, if ever editing hard, you may disable this one until you finish
     * or whatever -- just thought it was important to write this here.
     */
    @Test
    public void toSQLocator__a_and_b_arguments__WITH_a_NEGATIVE() {
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-1n+1", ":nth-child(-1n+1)", ".//*[(position() - 1) mod -1 = 0 and position() <= 1]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-2n+2", ":nth-child(-2n+2)", ".//*[(position() - 2) mod -2 = 0 and position() <= 2]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-1n+2", ":nth-child(-1n+2)", ".//*[(position() - 2) mod -1 = 0 and position() <= 2]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-2n+1", ":nth-child(-2n+1)", ".//*[(position() - 1) mod -2 = 0 and position() <= 1]");
        String minusNPlusFiveCSS = ":nth-child(-1n+5)";
        String minusNPlusFiveXPath = ".//*[(position() - 5) mod -1 = 0 and position() <= 5]";
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-n+5", minusNPlusFiveCSS, minusNPlusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-n +5", minusNPlusFiveCSS, minusNPlusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-n+ 5", minusNPlusFiveCSS, minusNPlusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-n + 5", minusNPlusFiveCSS, minusNPlusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-1n +5", minusNPlusFiveCSS, minusNPlusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-1n+ 5", minusNPlusFiveCSS, minusNPlusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-1n + 5", minusNPlusFiveCSS, minusNPlusFiveXPath);
    }

    /**
     * IMPORTANT: This test method was not necessary, that is, adding it did not provoke any code addition,
     * but they are important cases. Anyway, if ever editing hard, you may disable this one until you finish
     * or whatever -- just thought it was important to write this here.
     */
    @Test
    public void toSQLocator__a_and_b_arguments__WITH_BOTH_NEGATIVE() {
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-1n-1", ":nth-child(-1n-1)", ".//*[(position() - -1) mod -1 = 0 and position() <= -1]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-2n-2", ":nth-child(-2n-2)", ".//*[(position() - -2) mod -2 = 0 and position() <= -2]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-1n-2", ":nth-child(-1n-2)", ".//*[(position() - -2) mod -1 = 0 and position() <= -2]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-2n-1", ":nth-child(-2n-1)", ".//*[(position() - -1) mod -2 = 0 and position() <= -1]");
        String minusNMinusFiveCSS = ":nth-child(-1n-5)";
        String minusNMinusFiveXPath = ".//*[(position() - -5) mod -1 = 0 and position() <= -5]";
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-n-5", minusNMinusFiveCSS, minusNMinusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-n -5", minusNMinusFiveCSS, minusNMinusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-n- 5", minusNMinusFiveCSS, minusNMinusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-n - 5", minusNMinusFiveCSS, minusNMinusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-1n -5", minusNMinusFiveCSS, minusNMinusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-1n- 5", minusNMinusFiveCSS, minusNMinusFiveXPath);
        assertNthChildArgumentYieldsWithAndWithoutSpaces("-1n - 5", minusNMinusFiveCSS, minusNMinusFiveXPath);
    }

    @Test
    public void toSQLocator__even_and_odd_special_arguments() {
        assertNthChildArgumentYieldsWithAndWithoutSpaces("even", ":nth-child(2n)", ".//*[(position() - 0) mod 2 = 0 and position() >= 0]");
        assertNthChildArgumentYieldsWithAndWithoutSpaces("odd", ":nth-child(2n+1)", ".//*[(position() - 1) mod 2 = 0 and position() >= 1]");
    }

    private void assertNthChildArgumentYieldsWithAndWithoutSpaces(String nthChildArgument, String expectedCSS, String expectedXPath) {
        assertNthChildArgumentYields(nthChildArgument, expectedCSS, expectedXPath);
        assertNthChildArgumentYields(" " + nthChildArgument + " ", expectedCSS, expectedXPath);
    }

    private void assertNthChildArgumentYields(String nthChildArgument, String expectedCSS, String expectedXPath) {
        String pseudoThatTheDriverWillTestForNativeSupport = NTH_CHILD_PSEUDO_USED_IN_NATIVE_SUPPORT_CHECK;
        assertPseudoSupportsBothPureCssAndPureXPathWhenNativelySupported(
                pseudoThatTheDriverWillTestForNativeSupport,
                nthChild(nthChildArgument),
                expectedCSS,
                expectedXPath
        );
        assertPseudoClassOnlySupportsPureXPathWhenNotNativelySupported(
                nthChild(nthChildArgument),
                pseudoThatTheDriverWillTestForNativeSupport,
                expectedXPath
        );
    }

}