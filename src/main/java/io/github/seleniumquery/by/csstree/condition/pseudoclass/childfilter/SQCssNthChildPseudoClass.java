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

package io.github.seleniumquery.by.csstree.condition.pseudoclass.childfilter;

import io.github.seleniumquery.by.css.pseudoclasses.PseudoClassSelector;
import io.github.seleniumquery.by.csstree.condition.pseudoclass.SQCssFunctionalPseudoClassCondition;
import io.github.seleniumquery.by.csstree.condition.pseudoclass.locatorgenerationstrategy.MaybeNativelySupportedPseudoClass;
import io.github.seleniumquery.by.locator.CSSLocator;
import io.github.seleniumquery.by.locator.XPathLocator;

/**
 * :nth-child()
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class SQCssNthChildPseudoClass extends SQCssFunctionalPseudoClassCondition {

    public static final String PSEUDO = "nth-child";

    public MaybeNativelySupportedPseudoClass nthChildPseudoClassLocatorGenerationStrategy = new MaybeNativelySupportedPseudoClass() {
        @Override
        public String pseudoClassForCSSNativeSupportCheck() {
            return ":"+PSEUDO+"(1)";
        }

        @Override
        public CSSLocator toCssWhenNativelySupported() {
            NthChildArgument nthChildArgument = getNthChildArgument();
            return new CSSLocator(nthChildArgument.toCSS());
        }

        @Override
        public XPathLocator toXPath() {
            NthChildArgument nthChildArgument = getNthChildArgument();
            return XPathLocator.pureXPath(nthChildArgument.toXPath());
        }
    };

    public SQCssNthChildPseudoClass(PseudoClassSelector pseudoClassSelector) {
        super(pseudoClassSelector);
    }

    @Override
    public MaybeNativelySupportedPseudoClass getSQCssLocatorGenerationStrategy() {
        return nthChildPseudoClassLocatorGenerationStrategy;
    }

    private NthChildArgument getNthChildArgument() {
        return new NthChildArgument(getArgument());
    }

}