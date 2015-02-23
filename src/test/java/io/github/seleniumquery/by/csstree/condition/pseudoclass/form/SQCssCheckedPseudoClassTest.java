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

package io.github.seleniumquery.by.csstree.condition.pseudoclass.form;

import io.github.seleniumquery.by.css.pseudoclasses.CheckedPseudoClass;
import io.github.seleniumquery.by.locator.SQLocator;
import io.github.seleniumquery.by.locator.SQLocatorUtilsTest;
import org.junit.Test;

import static io.github.seleniumquery.by.csstree.condition.pseudoclass.PseudoClassAssertLocatorUtils.*;
import static io.github.seleniumquery.by.csstree.condition.pseudoclass.PseudoClassTestUtils.assertPseudo;
import static io.github.seleniumquery.by.locator.SQLocatorUtilsTest.createMockDriverWithNativeSupporForSelectorAndEmulatingPhantomJS;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class SQCssCheckedPseudoClassTest {

    public static final String CHECKED_PSEUDO = ":checked";
    public static final String CHECKED_XPATH_EXPRESSION = ".//*[" +
            "((self::input and (@type = 'radio' or @type = 'checkbox')) or self::option)" +
            "]";

    @Test
    public void translate() {
        assertPseudo(CHECKED_PSEUDO, SQCssCheckedPseudoClass.class);
    }

    @Test
    public void toSQLocator__when_driver_has_native_support() {
        assertPseudoSupportsOnlyPureCssAndNotPureXPathWhenNativelySupported(
                new SQCssCheckedPseudoClass(),
                CHECKED_PSEUDO,
                CHECKED_XPATH_EXPRESSION,
                CheckedPseudoClass.CHECKED_FILTER
        );
    }

    @Test
    public void toSQLocator__when_driver_does_NOT_have_native_support() {
        assertPseudoClassDoesNotSupportAnythingPurelyWhenNotNativelySupported(
                new SQCssCheckedPseudoClass(),
                CHECKED_PSEUDO,
                CHECKED_XPATH_EXPRESSION,
                CheckedPseudoClass.CHECKED_FILTER
        );
    }

    /**
     * #Cross-Driver
     * PhantomJS's :checked has bugs!
     *
     * https://github.com/ariya/phantomjs/issues/11550
     */
    @Test
    public void toSQLocator__when_driver_is_PHANTOMJS_it_behaves_like_it_does_NOT_have_native_support() {
        SQLocator previousLocator = SQLocatorUtilsTest.tagAsterisk(createMockDriverWithNativeSupporForSelectorAndEmulatingPhantomJS(CHECKED_PSEUDO));
        assertPseudoClassHasLocator(
                new SQCssCheckedPseudoClass(),
                previousLocator,
                PURE_CSS_IS_NOT_SUPPORTED,
                CSS_ALL_TAGS_SELECTOR,
                PURE_XPATH_IS_NOT_SUPPORTED,
                CHECKED_XPATH_EXPRESSION,
                contains(CheckedPseudoClass.CHECKED_FILTER)
        );
    }

}