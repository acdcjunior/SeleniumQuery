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

package io.github.seleniumquery.by.csstree.condition.pseudoclass.visibility;

import io.github.seleniumquery.by.css.pseudoclasses.VisiblePseudoClass;
import io.github.seleniumquery.by.csstree.condition.pseudoclass.SQCssPseudoClassCondition;
import io.github.seleniumquery.by.csstree.condition.pseudoclass.locatorgenerationstrategy.NeverNativelySupportedPseudoClass;
import io.github.seleniumquery.by.locator.XPathLocator;

/**
 * :visible
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class SQCssVisiblePseudoClass extends SQCssPseudoClassCondition {

    public static final String PSEUDO = "visible";

    public NeverNativelySupportedPseudoClass visiblePseudoClassLocatorGenerationStrategy = new NeverNativelySupportedPseudoClass() {
        @Override
        public XPathLocator toXPath() {
            return XPathLocator.filterOnly(VisiblePseudoClass.VISIBLE_FILTER);
        }
    };

    @Override
    public NeverNativelySupportedPseudoClass getSQCssLocatorGenerationStrategy() {
        return visiblePseudoClassLocatorGenerationStrategy;
    }

}