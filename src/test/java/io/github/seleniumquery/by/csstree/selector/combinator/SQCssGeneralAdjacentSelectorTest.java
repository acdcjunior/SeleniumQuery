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

package io.github.seleniumquery.by.csstree.selector.combinator;

import io.github.seleniumquery.by.csstree.selector.SQCssTagNameSelector;
import io.github.seleniumquery.by.locator.ElementFinder;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class SQCssGeneralAdjacentSelectorTest {

    @Test
    public void toSQLocator() {
        // given
        SQCssTagNameSelector aTagSelector = new SQCssTagNameSelector("a");
        SQCssTagNameSelector bTagSelector = new SQCssTagNameSelector("b");
        SQCssGeneralAdjacentSelector generalAdjacentSelector = new SQCssGeneralAdjacentSelector(aTagSelector, bTagSelector);
        // when
        ElementFinder locator = generalAdjacentSelector.toSQLocator(mock(WebDriver.class));
        // then
        assertThat(locator.getCssFinder().toString(), is("a~b"));
        assertThat(locator.canFetchThroughCssAlone(), is(true));
        assertThat(locator.getXPathExpression(), is(".//*[self::a]/following-sibling::*[self::b]"));
        assertThat(locator.getElementFilterList().getElementFilters(), empty());
    }

}