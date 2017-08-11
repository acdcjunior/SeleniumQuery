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

package io.github.seleniumquery.by.secondgen.csstree.condition.pseudoclass;

import io.github.seleniumquery.by.common.pseudoclass.PseudoClass;

public class CssFunctionalPseudoClassCondition implements CssPseudoClassCondition {

    private final PseudoClass pseudoClassSelector;
    private SqCssFunctionalPseudoClassArgument argument;

    public CssFunctionalPseudoClassCondition(PseudoClass pseudoClassSelector) {
        this.pseudoClassSelector = pseudoClassSelector;
    }

    public SqCssFunctionalPseudoClassArgument getArgument() {
        if (this.argument == null) {
            this.argument = new SqCssFunctionalPseudoClassArgument(pseudoClassSelector.getPseudoClassContent());
        }
        return argument;
    }

}
