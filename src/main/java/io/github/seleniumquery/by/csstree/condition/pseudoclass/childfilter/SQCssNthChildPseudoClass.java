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
import io.github.seleniumquery.by.locator.CSSFinder;
import io.github.seleniumquery.by.locator.XPathLocator;
import org.openqa.selenium.WebDriver;

/**
 * :nth-child()
 * https://api.jquery.com/nth-child-selector/
 * https://developer.mozilla.org/pt-BR/docs/Web/CSS/:nth-child
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class SQCssNthChildPseudoClass extends SQCssFunctionalPseudoClassCondition {

    public static final String PSEUDO = "nth-child";

    public MaybeNativelySupportedPseudoClass nthChildPseudoClassLocatorGenerationStrategy = new MaybeNativelySupportedPseudoClass() {
        @Override
        public String pseudoClassForCSSNativeSupportCheck(WebDriver webDriver) {
            return ":"+PSEUDO+"(1)";
        }

        @Override
        public CSSFinder toCssWhenNativelySupported(WebDriver webDriver) {
            NthArgument nthArgument = getNthChildArgument();
            return new CSSFinder(":"+PSEUDO+"("+nthArgument.toCSS()+")");
        }

        @Override
        public XPathLocator toXPath(WebDriver webDriver) {
            NthArgument nthArgument = getNthChildArgument();
            return XPathLocator.pureXPath(nthArgument.toXPath("position()"));
        }
    };

    public SQCssNthChildPseudoClass(PseudoClassSelector pseudoClassSelector) {
        super(pseudoClassSelector);
    }

    @Override
    public MaybeNativelySupportedPseudoClass getSQCssLocatorGenerationStrategy() {
        return nthChildPseudoClassLocatorGenerationStrategy;
    }

    private NthArgument getNthChildArgument() {
        return new NthArgument(getArgument());
    }

}