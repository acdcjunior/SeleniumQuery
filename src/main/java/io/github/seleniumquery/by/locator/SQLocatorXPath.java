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

package io.github.seleniumquery.by.locator;

import io.github.seleniumquery.by.csstree.condition.pseudoclass.SQCssPseudoMaybeNativelySupported;
import io.github.seleniumquery.by.filter.ElementFilter;
import io.github.seleniumquery.by.filter.ElementFilterList;

import static io.github.seleniumquery.by.filter.ElementFilter.FILTER_NOTHING;
import static java.util.Arrays.asList;

/**
 * @author acdcjunior
 * @since 0.10.0
 */
public class SQLocatorXPath {

    public static ElementFilterList mergeFilter(SQCssPseudoMaybeNativelySupported pseudo, SQLocator leftLocator) {
        // If this locator can be evaluated by pure XPath, then it won't have any filters
        if (pseudo.canPureXPath()) {
            return leftLocator.getElementFilterList();
        }
        ElementFilter filter = pseudo.toElementFilter();
        ElementFilterList filterList = leftLocator.getElementFilterList();
        return SQLocatorFactory.mergeFilterIntoFilterList(filterList, filter);
    }

    public static SQLocatorXPath pureXPath(String xPathExpression) {
        return new SQLocatorXPath(xPathExpression, true, new ElementFilterList(asList(FILTER_NOTHING)));
    }

    private String xPathExpression;

    private boolean canPureXPath;
    private ElementFilterList elementFilterList;

    public SQLocatorXPath(String xPathExpression, boolean canPureXPath, ElementFilterList elementFilterList) {
        this.xPathExpression = xPathExpression;
        this.canPureXPath = canPureXPath;
        this.elementFilterList = elementFilterList;
    }

    public String getXPathExpression() {
        return xPathExpression;
    }

    public boolean isCanPureXPath() {
        return canPureXPath;
    }

    public ElementFilterList getElementFilterList() {
        return elementFilterList;
    }

    public String getFinalXPathExpression() {
        return "(" + this.xPathExpression + ")";
    }

    public SQLocatorXPath newXPathExpressionKeepingEverythingElse(String newXPathExpression) {
        return new SQLocatorXPath(newXPathExpression, this.isCanPureXPath(), this.getElementFilterList());
    }

}