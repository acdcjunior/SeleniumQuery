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

package io.github.seleniumquery.by2.parser.translator.selector;

import io.github.seleniumquery.by2.csstree.condition.SQCssAndCondition;
import io.github.seleniumquery.by2.csstree.condition.SQCssCondition;
import io.github.seleniumquery.by2.csstree.condition.attribute.SQCssClassAttributeCondition;
import io.github.seleniumquery.by2.csstree.selector.SQCssConditionalSelector;
import io.github.seleniumquery.by2.csstree.selector.SQCssSelector;
import io.github.seleniumquery.by2.csstree.selector.SQCssTagNameSelector;
import io.github.seleniumquery.by2.parser.SQParseTreeBuilder;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SQCssConditionalSelectorTranslatorTest {

    @Test
    public void translate__simple_condition() {
        // given
        String simpleConditionSelector = "a.condition";
        // when
        SQCssSelector cssSelector = SQParseTreeBuilder.parse(simpleConditionSelector).firstSelector();
        // then
        assertThat(cssSelector, instanceOf(SQCssConditionalSelector.class));
        SQCssSelector sqCssSelector = ((SQCssConditionalSelector) cssSelector).getSqCssSelector();
        SQCssCondition sqCssCondition = ((SQCssConditionalSelector) cssSelector).getSqCssCondition();

        assertThat(sqCssSelector, instanceOf(SQCssTagNameSelector.class));
        assertThat(((SQCssTagNameSelector) sqCssSelector).getTagName(), is("a"));

        assertThat(sqCssCondition, instanceOf(SQCssClassAttributeCondition.class));
        assertThat(((SQCssClassAttributeCondition) sqCssCondition).getClassName(), is("condition"));
    }

    @Test
    public void translate__compound_condition() {
        // given
        String compoundConditionSelector = "a.conditionA.conditionB";
        // when
        SQCssSelector cssSelector = SQParseTreeBuilder.parse(compoundConditionSelector).firstSelector();
        // then
        assertThat(cssSelector, instanceOf(SQCssConditionalSelector.class));

        SQCssSelector sqCssSelector = ((SQCssConditionalSelector) cssSelector).getSqCssSelector();
        SQCssCondition sqCssCondition = ((SQCssConditionalSelector) cssSelector).getSqCssCondition();

        assertThat(sqCssSelector, instanceOf(SQCssTagNameSelector.class));
        assertThat(((SQCssTagNameSelector) sqCssSelector).getTagName(), is("a"));

        assertThat(sqCssCondition, instanceOf(SQCssAndCondition.class));
        SQCssCondition sqCssFirstCondition = ((SQCssAndCondition) sqCssCondition).getFirstCondition();
        SQCssCondition sqCssSecondCondition = ((SQCssAndCondition) sqCssCondition).getSecondCondition();

        assertThat(sqCssFirstCondition, instanceOf(SQCssClassAttributeCondition.class));
        assertThat(((SQCssClassAttributeCondition) sqCssFirstCondition).getClassName(), is("conditionA"));

        assertThat(sqCssSecondCondition, instanceOf(SQCssClassAttributeCondition.class));
        assertThat(((SQCssClassAttributeCondition) sqCssSecondCondition).getClassName(), is("conditionB"));
    }

}