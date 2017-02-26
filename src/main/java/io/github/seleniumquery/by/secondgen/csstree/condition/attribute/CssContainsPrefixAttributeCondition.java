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

package io.github.seleniumquery.by.secondgen.csstree.condition.attribute;

import io.github.seleniumquery.by.common.AttributeEvaluatorUtils;
import io.github.seleniumquery.utils.SelectorUtils;

/**
 * [hreflang|="en"]
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class CssContainsPrefixAttributeCondition extends CssAttributeCondition {

    public CssContainsPrefixAttributeCondition(String attributeName, String wantedValue) {
        super(attributeName, wantedValue);
    }

    @Override
    protected String symbol() {
        return "|=";
    }

    protected String toXPath() {
        String attrName = AttributeEvaluatorUtils.toXPathAttribute(this.attributeName);
        String attrValue = SelectorUtils.intoEscapedXPathString(this.wantedValue);
        String attrValueWithSuffix = SelectorUtils.intoEscapedXPathString(this.wantedValue + "-");
        return "("+attrName+" = "+attrValue+" or starts-with("+ attrName+", "+attrValueWithSuffix+"))";
    }

}
