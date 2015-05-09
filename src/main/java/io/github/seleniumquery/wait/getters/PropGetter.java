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

package io.github.seleniumquery.wait.getters;

import io.github.seleniumquery.functions.jquery.attributes.PropFunction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PropGetter<T> implements Getter<T> {
	
	private String propertyName;
	public PropGetter(String propertyName) { this.propertyName = propertyName; }
	
	@Override
	public T get(WebDriver driver, List<WebElement> elements) {
		return PropFunction.prop(driver, elements, this.propertyName);
	}
	
	@Override
	public String toString() {
		return "prop(\""+this.propertyName+"\")";
	}
	
}