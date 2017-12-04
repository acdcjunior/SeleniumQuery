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

package io.github.seleniumquery.internal.fluentfunctions.evaluators;

import io.github.seleniumquery.internal.fluentfunctions.FluentBehaviorModifier;
import io.github.seleniumquery.internal.fluentfunctions.getters.Getter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class EqualsEvaluator<T> implements Evaluator<T> {

	private static final Log LOGGER = LogFactory.getLog(EqualsEvaluator.class);

	private Getter<T> getter;

	public EqualsEvaluator(Getter<T> getter) {
		this.getter = getter;
	}

	@Override
	public boolean evaluate(WebDriver driver, List<WebElement> elements, T valueToEqual) {
		LOGGER.debug("Evaluating isEqualTo()...");
		final T gotValue = getter.get(driver, elements);
		LOGGER.debug("Evaluating isEqualTo()... got "+getter+": \""+gotValue+"\". Wanted: \""+valueToEqual+"\".");
		return gotValue.equals(valueToEqual);
	}

	@Override
	public String stringFor(T valueToEqual, FluentBehaviorModifier fluentBehaviorModifier) {
        String valueAsString = "\"" + valueToEqual + "\"";
        if (valueToEqual instanceof Number) {
            valueAsString = valueToEqual.toString();
        }
        return getter.toString() + fluentBehaviorModifier + ".isEqualTo(" + valueAsString + ")";
	}

}
