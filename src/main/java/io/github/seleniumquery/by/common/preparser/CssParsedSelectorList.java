/*
 * Copyright (c) 2017 seleniumQuery authors
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

package io.github.seleniumquery.by.common.preparser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.w3c.css.sac.SelectorList;

public class CssParsedSelectorList implements Iterable<CssParsedSelector> {

	private final SelectorList selectorList;
	private final ArgumentMap argumentMap;
	private final List<CssParsedSelector> cssParsedSelectors;

	public CssParsedSelectorList(SelectorList selectorList, ArgumentMap argumentMap) {
		this.selectorList = selectorList;
		this.argumentMap = argumentMap;
		this.cssParsedSelectors = createParsedSelectorList();
	}

	private List<CssParsedSelector> createParsedSelectorList() {
        List<CssParsedSelector> cssParsedSelectorList = new LinkedList<>();
		for (int i = 0; i < selectorList.getLength(); i++) {
			CssParsedSelector cssParsedSelector = new CssParsedSelector(selectorList.item(i), this.argumentMap);
            cssParsedSelectorList.add(cssParsedSelector);
		}
        return cssParsedSelectorList;
	}

	public SelectorList getSelectorList() {
		return this.selectorList;
	}

	public ArgumentMap getArgumentMap() {
		return this.argumentMap;
	}

    public int size() {
        return cssParsedSelectors.size();
    }

    @Override
    public Iterator<CssParsedSelector> iterator() {
        return cssParsedSelectors.iterator();
    }

    public Stream<CssParsedSelector> stream() {
        return cssParsedSelectors.stream();
    }

    public CssParsedSelector get(int index) {
        return cssParsedSelectors.get(index);
    }

}
