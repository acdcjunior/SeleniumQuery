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

import io.github.seleniumquery.SeleniumQueryObject;
import io.github.seleniumquery.internal.fluentfunctions.FluentBehaviorModifier;

public class IsEvaluator implements Evaluator<String> {

	public static IsEvaluator IS_EVALUATOR = new IsEvaluator();

	private IsEvaluator() {	}

	@Override
	public boolean evaluate(SeleniumQueryObject seleniumQueryObject, String selector) {
        return seleniumQueryObject.is(selector);
	}

	@Override
	public String stringFor(String selector, FluentBehaviorModifier fluentBehaviorModifier) {
		return "is(\"" + selector + "\")";
	}

}
