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

package io.github.seleniumquery.by.secondgen.csstree.condition.pseudoclass.form;

import io.github.seleniumquery.by.secondgen.csstree.condition.pseudoclass.CssPseudoClassCondition;
import io.github.seleniumquery.by.secondgen.csstree.condition.pseudoclass.finderfactorystrategy.MaybeNativelySupportedPseudoClass;
import io.github.seleniumquery.by.secondgen.finder.CssFinder;
import io.github.seleniumquery.by.secondgen.finder.XPathAndFilterFinder;
import org.openqa.selenium.WebDriver;

import static io.github.seleniumquery.by.firstgen.css.pseudoclasses.EnabledPseudoClass.ENABLED_XPATH;

/**
 * :enabled
 * https://api.jquery.com/enabled-selector/
 * https://developer.mozilla.org/en-US/docs/Web/CSS/:enabled
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class CssEnabledPseudoClass extends CssPseudoClassCondition {

    public static final String PSEUDO = "enabled";
    private static final String ENABLED_PSEUDO = ":" + PSEUDO;

    private MaybeNativelySupportedPseudoClass enabledPseudoClassFinderFactoryStrategy = new MaybeNativelySupportedPseudoClass() {
        @Override
        public CssFinder toCssWhenNativelySupported(WebDriver webDriver) {
            return new CssFinder(ENABLED_PSEUDO);
        }

        @Override
        public XPathAndFilterFinder toXPath(WebDriver webDriver) {
            return XPathAndFilterFinder.pureXPath(ENABLED_XPATH);
        }
    };

    @Override
    public MaybeNativelySupportedPseudoClass getElementFinderFactoryStrategy() {
        return enabledPseudoClassFinderFactoryStrategy;
    }

}
