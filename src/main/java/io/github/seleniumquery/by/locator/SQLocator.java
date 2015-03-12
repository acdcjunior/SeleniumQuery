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

import io.github.seleniumquery.by.filter.ElementFilterList;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Cabaple of locating {@link WebElement}s on a given {@link SearchContext}.
 *
 * Attempts to find the elements by CSS if driver/browser supports.
 * If not, will use XPath. If the XPath expression is known NOT able of bringing all elements, then
 * the locator also filters the results found by XPath.
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class SQLocator {

    /**
     * The driver here is used to test for selector support. This is *not* the {@link SearchContext}
     * the elements will be searched on.
     */
    private WebDriver webDriver;
    private SQLocatorCss cssSelector;
    private SQLocatorXPath xPathLocator;

    public SQLocator(WebDriver webDriver, SQLocatorCss cssSelector, SQLocatorXPath xPathLocator) {
        this.webDriver = webDriver;
        this.cssSelector = cssSelector;
        this.xPathLocator = xPathLocator;
    }

    public SQLocator(SQLocatorCss newCssSelector, String newXPathExpression, SQLocator previous) {
        this(previous.webDriver, newCssSelector, previous.getSQLocatorXPath().newXPathExpressionKeepingEverythingElse(newXPathExpression));
    }

    public List<WebElement> findWebElements(SearchContext context) {
        if (canFetchThroughCssAlone()) {
            return findElementsByCss(context);
        }
        List<WebElement> elementsByXPath = findElementsByXPath(context);
        if (canFetchThroughXPathAlone()) {
            return elementsByXPath;
        }
        return xPathLocator.getElementFilterList().filter(context, elementsByXPath);
    }

    private List<WebElement> findElementsByCss(SearchContext context) {
        String finalCssSelector = this.cssSelector.toString();
        return new By.ByCssSelector(finalCssSelector).findElements(context);
    }

    private List<WebElement> findElementsByXPath(SearchContext context) {
        String finalXPathExpression = this.xPathLocator.getFinalXPathExpression();
        return new By.ByXPath(finalXPathExpression).findElements(context);
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public String getCssSelector() {
        return cssSelector.toString();
    }

    public SQLocatorCss getSQCssSelector() {
        return cssSelector;
    }

    public SQLocatorXPath getSQLocatorXPath() {
        return xPathLocator;
    }

    public String getXPathExpression() {
        return xPathLocator.getXPathExpression();
    }

    public boolean canFetchThroughCssAlone() {
        return cssSelector.canFetchAllElementsOfTheQueryByItself();
    }

    public boolean canFetchThroughXPathAlone() {
        return xPathLocator.isCanPureXPath();
    }

    public ElementFilterList getElementFilterList() {
        return xPathLocator.getElementFilterList();
    }

}