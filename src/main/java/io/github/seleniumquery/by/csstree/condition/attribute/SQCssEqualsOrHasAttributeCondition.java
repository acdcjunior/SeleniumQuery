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
import io.github.seleniumquery.by.csstree.condition.SQCssConditionImplementedNotYet;
import io.github.seleniumquery.by.locator.SQLocatorCss;

/**
 * [simple]
 * [restart="never"]
 *
 * Case INsensitive!
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class SQCssEqualsOrHasAttributeCondition extends SQCssAttributeCondition implements SQCssConditionImplementedNotYet {

    /**
     * [simple]
     * Attribute value is null in this case.
     */
    public SQCssEqualsOrHasAttributeCondition(String attributeName) {
        super(attributeName, null);
    }

    /**
     * [restart="never"]
     */
    public SQCssEqualsOrHasAttributeCondition(String attributeName, String wantedValue) {
        super(attributeName, wantedValue);
    }

    protected SQLocatorCss toCSS() {
        if (this.wantedValue != null) {
            return new SQLocatorCss("[" + this.attributeName + "=" + this.wantedValue + "]", SQLocatorCss.CanFetchAllElementsOfTheQueryByItself.YES);
        }
        return new SQLocatorCss("[" + this.attributeName + "]", SQLocatorCss.CanFetchAllElementsOfTheQueryByItself.YES);
    }

    protected String toXPath() {
        if (this.wantedValue != null) {
            String escapedWantedValue = SelectorUtils.intoEscapedXPathString(this.wantedValue);
            return AttributeEvaluatorUtils.toXPathAttribute(this.attributeName) + "=" + escapedWantedValue;
        }
        return AttributeEvaluatorUtils.toXPathAttribute(this.attributeName);
    }

}