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

package io.github.seleniumquery.by.secondgen.csstree.condition;

import io.github.seleniumquery.by.secondgen.finder.ElementFinder;

/**
 * This is temporary.
 *
 * While I don't implement all SQCssConditions, I go applying this interface to those
 * that I implement the toElementFinder() method.
 *
 * After all of them are complete, I move this method to CssCondition and remove this interface.
 *
 * @deprecated temporary
 */
@Deprecated
public interface CssConditionImplementedFinders extends CssCondition {

    /**
     * We have to document this better!
     *
     * This method turns a CSS Condition into an {@link ElementFinder}.
     *
     * The above is obvious... but why "LEFT" finder as argument??
     *
     * @param leftFinder The expression left-side finder
     * @return The condition as an element finder.
     */
    ElementFinder toElementFinder(ElementFinder leftFinder);

}
