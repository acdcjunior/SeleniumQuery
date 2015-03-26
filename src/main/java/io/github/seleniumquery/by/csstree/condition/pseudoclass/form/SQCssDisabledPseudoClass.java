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

package io.github.seleniumquery.by.csstree.condition.pseudoclass.form;

import io.github.seleniumquery.by.css.pseudoclasses.DisabledPseudoClass;
import io.github.seleniumquery.by.csstree.condition.pseudoclass.SQCssPseudoClassCondition;
import io.github.seleniumquery.by.csstree.condition.pseudoclass.locatorgenerationstrategy.MaybeNativelySupportedPseudoClass;
import io.github.seleniumquery.by.locator.SQLocatorCss;
import io.github.seleniumquery.by.locator.SQLocatorXPath;

/**
 * :disabled
 * https://api.jquery.com/disabled-selector/
 * https://developer.mozilla.org/en-US/docs/Web/CSS/:disabled
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class SQCssDisabledPseudoClass extends SQCssPseudoClassCondition {

    public static final String PSEUDO = "disabled";
    public static final String DISABLED_PSEUDO = ":" + PSEUDO;

    public MaybeNativelySupportedPseudoClass disabledPseudoClassLocatorGenerationStrategy = new MaybeNativelySupportedPseudoClass() {
        @Override
        public SQLocatorCss toCssWhenNativelySupported() {
            return new SQLocatorCss(DISABLED_PSEUDO);
        }

        @Override
        public SQLocatorXPath toXPath() {
            return SQLocatorXPath.pureXPath("(@disabled and " + DisabledPseudoClass.DISABLEABLE_TAGS_XPATH + ")");
        }
    };

    @Override
    public MaybeNativelySupportedPseudoClass getSQCssLocatorGenerationStrategy() {
        return disabledPseudoClassLocatorGenerationStrategy;
    }

}