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

package io.github.seleniumquery.functions.jquery.events;

import io.github.seleniumquery.SeleniumQueryObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.github.seleniumquery.functions.jquery.events.ClickFunctionUtils.reportIfThereWasAnyElementNotClicked;

/**
 * Clicks on a bunch of elements, complaining when needed.
 *
 * $("#element").click(); internals
 *
 * @author acdcjunior
 * @since 0.9.0
 */
public class ClickFunction {

    private static final Log LOGGER = LogFactory.getLog(ClickFunction.class);
    
    private ClickFunction() {}

    public static SeleniumQueryObject click(SeleniumQueryObject seleniumQueryObject) {
        LOGGER.debug("Clicking "+seleniumQueryObject+".");
        List<WebElement> elements = seleniumQueryObject.get();

        int numberOfNotClickedElements = 0;
        Exception lastCaughtException = null;
        WebElement elementThatThrewLastCaughtException = null;

        for (WebElement element : elements) {
            try {
                element.click();
            } catch (ElementNotVisibleException e) {
                numberOfNotClickedElements++;
                lastCaughtException = e;
                elementThatThrewLastCaughtException = element;
            }
        }

        reportIfThereWasAnyElementNotClicked(LOGGER, seleniumQueryObject, elements, numberOfNotClickedElements, lastCaughtException, elementThatThrewLastCaughtException);
        return seleniumQueryObject;
    }

}