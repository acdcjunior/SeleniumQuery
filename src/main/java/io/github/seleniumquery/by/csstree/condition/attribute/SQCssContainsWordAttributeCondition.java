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

package io.github.seleniumquery.by.csstree.condition.attribute;

import io.github.seleniumquery.by.SelectorUtils;
import io.github.seleniumquery.by.css.attributes.AttributeEvaluatorUtils;
import io.github.seleniumquery.by.locator.CSSLocator;

/**
 * [values~="10"]
 *
 * Case INsensitive!
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class SQCssContainsWordAttributeCondition extends SQCssAttributeCondition {

    public SQCssContainsWordAttributeCondition(String attributeName, String wantedValue) {
        super(attributeName, wantedValue);
    }

    protected CSSLocator toCSS() {
        return new CSSLocator("[" + this.attributeName + "~='" + this.wantedValue + "']");
    }

    protected String toXPath() {
        String escapedAttributeName = AttributeEvaluatorUtils.toXPathAttribute(this.attributeName);
        String escapedWantedValueSurroundedBySpaces = SelectorUtils.intoEscapedXPathString(" " + this.wantedValue + " ");
        return "contains(concat(' ', normalize-space(" + escapedAttributeName + "), ' '), " + escapedWantedValueSurroundedBySpaces + ")";
    }

}