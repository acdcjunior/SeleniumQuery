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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.github.seleniumquery.internal.fluentfunctions.FluentBehaviorModifier;
import io.github.seleniumquery.internal.fluentfunctions.getters.Getter;

public class MatchesHamcrestMatcherEvaluator<T> implements Evaluator<Matcher<T>> {

	private static final Log LOGGER = LogFactory.getLog(MatchesHamcrestMatcherEvaluator.class);

	private Getter<T> getter;

	public MatchesHamcrestMatcherEvaluator(Getter<T> getter) {
		this.getter = getter;
	}

	@Override
	public boolean evaluate(WebDriver driver, List<WebElement> elements, Matcher<T> matcher) {
		LOGGER.debug("Evaluating .matches(<Matcher>)...");
		final T gotValue = getter.get(driver, elements);
		LOGGER.debug("Evaluating .matches(<Matcher>)... got "+getter+": \""+gotValue+"\". Wanted: <"+matcher+">.");

		return matcher.matches(gotValue);
	}

	@Override
	public String stringFor(Matcher<T> matcher, FluentBehaviorModifier fluentBehaviorModifier) {
        return getter.toString() + fluentBehaviorModifier + ".matches(<" + matcher + ">)";
	}

}
