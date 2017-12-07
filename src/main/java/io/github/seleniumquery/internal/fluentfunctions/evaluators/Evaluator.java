/*
 * Copyright (c) 2017 seleniumQuery authors
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

package io.github.seleniumquery.internal.fluentfunctions.evaluators;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.github.seleniumquery.SeleniumQueryObject;
import io.github.seleniumquery.internal.fluentfunctions.FluentBehaviorModifier;

public interface Evaluator<T> {

	default boolean evaluate(SeleniumQueryObject seleniumQueryObject, T valueArgument) {
	    return evaluate(seleniumQueryObject.getWebDriver(), seleniumQueryObject.get(), valueArgument);
    }

    @Deprecated
	default boolean evaluate(WebDriver driver, List<WebElement> elements, T valueArgument) {
	    throw new NotImplementedException("Temporary. Don't use this.");
    }

	String stringFor(T valueArgument, FluentBehaviorModifier fluentBehaviorModifier);

}
