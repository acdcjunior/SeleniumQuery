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

import io.github.seleniumquery.by.secondgen.csstree.condition.CssCondition;
import io.github.seleniumquery.by.secondgen.csstree.condition.CssConditionImplementedFinders;
import io.github.seleniumquery.by.secondgen.finder.CssFinder;
import io.github.seleniumquery.by.secondgen.finder.ElementFinder;
import io.github.seleniumquery.by.secondgen.finder.ElementFinderUtils;
import org.unbescape.css.CssEscape;

/**
 * #id
 *
 * CASE SENSITIVE!
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class CssIdAttributeCondition implements CssCondition, CssConditionImplementedFinders {

    private String id;

    public CssIdAttributeCondition(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public ElementFinder toElementFinder(ElementFinder leftFinder) {
        CssFinder newCssSelector = leftFinder.getCssFinder().merge(toCSS());
        String newXPathExpression = ElementFinderUtils.conditionalSimpleXPathMerge(leftFinder.getXPathExpression(), toXPath());
        return new ElementFinder(newCssSelector, newXPathExpression, leftFinder);
    }

    private CssFinder toCSS() {
        return new CssFinder("#" + CssEscape.escapeCssIdentifier(this.id));
    }

    private String toXPath() {
        return "@id = '" + id + "'";
    }

}
