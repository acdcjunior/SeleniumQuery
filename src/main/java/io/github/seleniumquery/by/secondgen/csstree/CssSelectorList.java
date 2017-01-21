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

package io.github.seleniumquery.by.secondgen.csstree;

import io.github.seleniumquery.by.secondgen.csstree.selector.SQCssSelector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A list of CSS Selectors (objects that represent parsed CSS selectors).
 *
 * Each {@link SQCssSelector} ia a root of a CSS selector object tree.
 */
public class CssSelectorList implements Iterable<SQCssSelector> {

    private List<SQCssSelector> sqCssSelectors;

    public CssSelectorList(List<SQCssSelector> sqCssSelectors) {
        this.sqCssSelectors = Collections.unmodifiableList(new ArrayList<>(sqCssSelectors));
    }

    public SQCssSelector selector(int i) {
        return sqCssSelectors.get(i);
    }

    public SQCssSelector firstSelector() {
        return selector(0);
    }

    @Override
    public Iterator<SQCssSelector> iterator() {
        return sqCssSelectors.iterator();
    }

}
